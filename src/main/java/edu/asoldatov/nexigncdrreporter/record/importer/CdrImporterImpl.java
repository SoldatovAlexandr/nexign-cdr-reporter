package edu.asoldatov.nexigncdrreporter.record.importer;

import edu.asoldatov.nexigncdrreporter.common.CallType;
import edu.asoldatov.nexigncdrreporter.common.Rate;
import edu.asoldatov.nexigncdrreporter.record.model.Call;
import edu.asoldatov.nexigncdrreporter.record.model.Subscriber;
import edu.asoldatov.nexigncdrreporter.record.model.CallDataRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CdrImporterImpl implements CdrImporter {

    private final static String DELIMITER = ",";
    private final static String DATA_TIME_PATTERN = "yyyyMMddHHmmss";
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATA_TIME_PATTERN);

    @Override
    public Collection<Subscriber> importInternal(String fileName) {

        Map<String, Subscriber> subscribersByNumber = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = split(line);
                CallDataRecord record = buildRecord(values);
                addToResults(subscribersByNumber, record);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("Not found file [%s].", fileName));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Сan not import file [%s].", fileName), e);
        }
        return subscribersByNumber.values();
    }

    private String[] split(String line) {
        return Arrays.stream(line.split(DELIMITER))
                .map(String::trim)
                .toArray(String[]::new);
    }

    private CallDataRecord buildRecord(String[] values) {
        return new CallDataRecord(
                values[1],
                CallType.getByCode(values[0]),
                parseDate(values[2]),
                parseDate(values[3]),
                Rate.getByIndex(values[4])
        );
    }

    private LocalDateTime parseDate(String value) {
        return LocalDateTime.parse(value, DATE_TIME_FORMATTER);
    }

    private void addToResults(Map<String, Subscriber> subscribersByNumber, CallDataRecord record) {
        Subscriber subscriber = getSubscriber(subscribersByNumber, record);
        subscriber.addCall(Call.of(record));
    }

    private Subscriber getSubscriber(Map<String, Subscriber> subscribersByNumber, CallDataRecord record) {
        if (subscribersByNumber.containsKey(record.getNumber())) {
            return subscribersByNumber.get(record.getNumber());
        }
        Subscriber subscriber = Subscriber.of(record);
        subscribersByNumber.put(record.getNumber(), subscriber);
        return subscriber;
    }
}

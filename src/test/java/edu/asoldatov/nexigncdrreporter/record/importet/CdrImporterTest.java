package edu.asoldatov.nexigncdrreporter.record.importet;

import edu.asoldatov.nexigncdrreporter.common.CallType;
import edu.asoldatov.nexigncdrreporter.common.Rate;
import edu.asoldatov.nexigncdrreporter.record.importer.CdrImporter;
import edu.asoldatov.nexigncdrreporter.record.importer.CdrImporterImpl;
import edu.asoldatov.nexigncdrreporter.record.model.Call;
import edu.asoldatov.nexigncdrreporter.record.model.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CdrImporterTest {

    private CdrImporter cdrImporter;

    private Collection<Subscriber> expectedResult;

    @BeforeEach
    void setUp() {

        Subscriber first = new Subscriber("73204411037", Rate.MINUTE);
        first.addCall(new Call(CallType.INCOMING, LocalDateTime.of(2023, 9, 24, 13, 31, 40), LocalDateTime.of(2023, 9, 24, 13, 31, 50)));

        Subscriber second = new Subscriber("73734435243", Rate.REGULAR);
        second.addCall(new Call(CallType.OUTGOING, LocalDateTime.of(2023, 7, 25, 14, 14, 48), LocalDateTime.of(2023, 7, 25, 14, 21, 10)));
        second.addCall(new Call(CallType.INCOMING, LocalDateTime.of(2023, 11, 7, 16, 10, 26), LocalDateTime.of(2023, 11, 7, 16, 19, 6)));

        expectedResult = List.of(first, second);

        cdrImporter = new CdrImporterImpl();
    }

    @Test
    void importInternal() {
        String fileName = "src/test/resources/cdr/data.txt";

        Collection<Subscriber> result = cdrImporter.importInternal(fileName);

        assertEquals(expectedResult, new ArrayList<>(result));
    }
}

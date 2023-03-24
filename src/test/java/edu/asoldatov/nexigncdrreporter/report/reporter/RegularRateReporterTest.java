package edu.asoldatov.nexigncdrreporter.report.reporter;

import edu.asoldatov.nexigncdrreporter.common.CallType;
import edu.asoldatov.nexigncdrreporter.common.Rate;
import edu.asoldatov.nexigncdrreporter.record.model.Call;
import edu.asoldatov.nexigncdrreporter.record.model.Subscriber;
import edu.asoldatov.nexigncdrreporter.report.model.Report;
import edu.asoldatov.nexigncdrreporter.report.model.ReportRow;
import edu.asoldatov.nexigncdrreporter.report.reporter.impl.RegularRateReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegularRateReporterTest {

    private RegularRateReporter regularTariffReporter;

    private final List<Call> calls = List.of(
            new Call(
                    CallType.OUTGOING,
                    LocalDateTime.of(2023, 9, 24, 13, 31, 40),
                    LocalDateTime.of(2023, 9, 24, 14, 51, 40)
            ),
            new Call(
                    CallType.OUTGOING,
                    LocalDateTime.of(2023, 9, 24, 15, 0, 0),
                    LocalDateTime.of(2023, 9, 24, 15, 30, 0)
            ),
            new Call(
                    CallType.OUTGOING,
                    LocalDateTime.of(2023, 9, 24, 15, 30, 0),
                    LocalDateTime.of(2023, 9, 24, 16, 0, 0)
            ),
            new Call(
                    CallType.INCOMING,
                    LocalDateTime.of(2023, 9, 24, 18, 35, 40),
                    LocalDateTime.of(2023, 9, 24, 18, 40, 40)
            )
    );
    private final List<ReportRow> rows = List.of(
            new ReportRow("01", "2023-09-24 13:31:40", "2023-09-24 14:51:40", "01:20:00", 40),
            new ReportRow("01", "2023-09-24 15:00:00", "2023-09-24 15:30:00", "00:30:00", 25),
            new ReportRow("01", "2023-09-24 15:30:00", "2023-09-24 16:00:00", "00:30:00", 45),
            new ReportRow("02", "2023-09-24 18:35:40", "2023-09-24 18:40:40", "00:05:00", 0)
    );
    private Subscriber subscriber;


    @BeforeEach
    void setUp() {
        subscriber = new Subscriber("73204411037", Rate.REGULAR);
        calls.forEach(subscriber::addCall);

        regularTariffReporter = new RegularRateReporter();
    }

    @Test
    void generate() {
        Report expectedResult = new Report("11", "73204411037", rows, 110.0);

        Report report = regularTariffReporter.generate(subscriber);

        assertEquals(expectedResult, report);
    }
}

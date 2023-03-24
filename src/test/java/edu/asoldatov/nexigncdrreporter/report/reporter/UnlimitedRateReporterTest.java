package edu.asoldatov.nexigncdrreporter.report.reporter;

import edu.asoldatov.nexigncdrreporter.common.CallType;
import edu.asoldatov.nexigncdrreporter.common.Rate;
import edu.asoldatov.nexigncdrreporter.record.model.Call;
import edu.asoldatov.nexigncdrreporter.record.model.Subscriber;
import edu.asoldatov.nexigncdrreporter.report.model.Report;
import edu.asoldatov.nexigncdrreporter.report.model.ReportRow;
import edu.asoldatov.nexigncdrreporter.report.reporter.impl.UnlimitedRateReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnlimitedRateReporterTest {

    private UnlimitedRateReporter unlimitedTariffReporter;

    private final List<Call> calls = List.of(
            new Call(
                    CallType.OUTGOING,
                    LocalDateTime.of(2023, 9, 24, 15, 0, 0),
                    LocalDateTime.of(2023, 9, 24, 19, 30, 0)
            ),
            new Call(
                    CallType.INCOMING,
                    LocalDateTime.of(2023, 9, 24, 19, 30, 0),
                    LocalDateTime.of(2023, 9, 24, 20, 30, 0)
            ),
            new Call(
                    CallType.INCOMING,
                    LocalDateTime.of(2023, 9, 24, 20, 30, 0),
                    LocalDateTime.of(2023, 9, 24, 21, 30, 0)
            )
    );
    private final List<ReportRow> rows = List.of(
            new ReportRow("01", "2023-09-24 15:00:00", "2023-09-24 19:30:00", "04:30:00", 0),
            new ReportRow("02", "2023-09-24 19:30:00", "2023-09-24 20:30:00", "01:00:00", 30),
            new ReportRow("02", "2023-09-24 20:30:00", "2023-09-24 21:30:00", "01:00:00", 60)
    );
    private Subscriber subscriber;

    @BeforeEach
    void setUp() {
        subscriber = new Subscriber("73204411037", Rate.UNLIMITED);
        calls.forEach(subscriber::addCall);

        unlimitedTariffReporter = new UnlimitedRateReporter();
    }

    @Test
    void generate() {
        Report expectedResult = new Report("06", "73204411037", rows, 190.0);

        Report report = unlimitedTariffReporter.generate(subscriber);

        assertEquals(expectedResult, report);
    }
}

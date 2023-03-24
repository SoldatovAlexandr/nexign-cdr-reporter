package edu.asoldatov.nexigncdrreporter.report.reporter;

import edu.asoldatov.nexigncdrreporter.common.CallType;
import edu.asoldatov.nexigncdrreporter.common.Rate;
import edu.asoldatov.nexigncdrreporter.record.model.Call;
import edu.asoldatov.nexigncdrreporter.record.model.Subscriber;
import edu.asoldatov.nexigncdrreporter.report.model.Report;
import edu.asoldatov.nexigncdrreporter.report.model.ReportRow;
import edu.asoldatov.nexigncdrreporter.report.reporter.impl.MinuteRateReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinuteRateReporterTest {

    private MinuteRateReporter minuteTariffReporter;

    private final Set<Call> calls = Set.of(
            new Call(
                    CallType.OUTGOING,
                    LocalDateTime.of(2023, 9, 24, 13, 31, 40),
                    LocalDateTime.of(2023, 9, 24, 13, 33, 50)
            ),
            new Call(
                    CallType.INCOMING,
                    LocalDateTime.of(2023, 9, 24, 13, 35, 40),
                    LocalDateTime.of(2023, 9, 24, 13, 40, 40)
            )
    );
    private final List<ReportRow> rows = List.of(
            new ReportRow("01", "2023-09-24 13:31:40", "2023-09-24 13:33:50", "00:02:10", 4.5),
            new ReportRow("02", "2023-09-24 13:35:40", "2023-09-24 13:40:40", "00:05:00", 7.5)
    );
    private Subscriber subscriber;


    @BeforeEach
    void setUp() {
        subscriber = new Subscriber("73204411037", Rate.MINUTE);
        calls.forEach(subscriber::addCall);

        minuteTariffReporter = new MinuteRateReporter();
    }

    @Test
    void generate() {
        Report expectedResult = new Report("03", "73204411037", rows, 12.0);

        Report report = minuteTariffReporter.generate(subscriber);

        assertEquals(expectedResult, report);
    }
}

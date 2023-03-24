package edu.asoldatov.nexigncdrreporter.report.reporter;

import edu.asoldatov.nexigncdrreporter.record.model.Call;
import edu.asoldatov.nexigncdrreporter.report.model.Report;
import edu.asoldatov.nexigncdrreporter.report.model.ReportRow;
import edu.asoldatov.nexigncdrreporter.record.model.Subscriber;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public abstract class RateReporter implements Reporter {

    public static final int SECONDS_IN_MINUTE = 60;

    @Override
    public Report generate(Subscriber subscriber) {
        List<ReportRow> rows = map(subscriber.getCalls());

        return new Report(
                subscriber.getRate().getIndex(),
                subscriber.getNumber(),
                rows,
                calculateTotalAmount(rows)
        );
    }

    protected abstract List<ReportRow> map(Set<Call> calls);

    protected double calculateTotalAmount(List<ReportRow> rows) {
        return rows.stream()
                .mapToDouble(ReportRow::getCost)
                .sum();
    }

    protected String format(LocalDateTime localDateTime) {
        return localDateTime.format(REPORT_FORMATTER);
    }

    protected String format(Duration duration) {
        return String.format(DURATION_PATTERN, duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
    }

    protected Duration calculateDuration(Call call) {
        return Duration.between(call.getStart(), call.getEnd());
    }

    protected long calculateCallMinutesDuration(Duration duration) {
        if (duration.getSeconds() % SECONDS_IN_MINUTE == 0) {
            return duration.getSeconds() / SECONDS_IN_MINUTE;
        }
        return duration.getSeconds() / SECONDS_IN_MINUTE + 1;
    }
}

package edu.asoldatov.nexigncdrreporter.report.reporter.impl;

import edu.asoldatov.nexigncdrreporter.common.Rate;
import edu.asoldatov.nexigncdrreporter.record.model.Call;
import edu.asoldatov.nexigncdrreporter.report.model.ReportRow;
import edu.asoldatov.nexigncdrreporter.report.reporter.RateReporter;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MinuteRateReporter extends RateReporter {

    private final static double COST_BY_MINUTE = 1.5;

    @Override
    public Rate getRate() {
        return Rate.MINUTE;
    }

    @Override
    protected List<ReportRow> map(Set<Call> calls) {
        return calls.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private ReportRow map(Call call) {
        Duration duration = calculateDuration(call);

        return new ReportRow(
                call.getCallType().getCode(),
                format(call.getStart()),
                format(call.getEnd()),
                format(duration),
                calculateCost(duration)
        );
    }

    private double calculateCost(Duration duration) {
        return calculateCallMinutesDuration(duration) * COST_BY_MINUTE;
    }
}

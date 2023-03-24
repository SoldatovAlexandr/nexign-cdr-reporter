package edu.asoldatov.nexigncdrreporter.report.reporter.impl;

import edu.asoldatov.nexigncdrreporter.common.Rate;
import edu.asoldatov.nexigncdrreporter.record.model.Call;
import edu.asoldatov.nexigncdrreporter.report.model.ReportRow;
import edu.asoldatov.nexigncdrreporter.report.reporter.RateReporter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UnlimitedRateReporter extends RateReporter {

    private final static int FREE_MINUTES_COUNT = 300;
    private final static int RATE_COST = 100;
    private final static double COST_BY_MINUTE = 1.0;

    @Override
    protected List<ReportRow> map(Set<Call> calls) {
        List<ReportRow> result = new ArrayList<>(calls.size());

        int remainingMinutes = FREE_MINUTES_COUNT;

        for (Call call : calls) {
            Duration duration = calculateDuration(call);

            long callMinutesDuration = calculateCallMinutesDuration(duration);

            long freeMinutes = calculateFreeMinutesInCall(remainingMinutes, callMinutesDuration);
            long expensiveMinutes = calculateExpensiveMinutesInCall(remainingMinutes, callMinutesDuration);
            remainingMinutes -= freeMinutes;
            double cost = expensiveMinutes * COST_BY_MINUTE;

            ReportRow row = new ReportRow(
                    call.getCallType().getCode(),
                    format(call.getStart()),
                    format(call.getEnd()),
                    format(duration),
                    cost
            );
            result.add(row);
        }
        return result;
    }

    @Override
    public Rate getRate() {
        return Rate.UNLIMITED;
    }

    @Override
    protected double calculateTotalAmount(List<ReportRow> rows) {
        return super.calculateTotalAmount(rows) + RATE_COST;
    }

    private long calculateFreeMinutesInCall(int remainingMinutes, long callMinutesDuration) {
        return remainingMinutes > callMinutesDuration ? callMinutesDuration : remainingMinutes;
    }

    private long calculateExpensiveMinutesInCall(int remainingMinutes, long callMinutesDuration) {
        return callMinutesDuration > remainingMinutes ? callMinutesDuration - remainingMinutes : 0;
    }
}

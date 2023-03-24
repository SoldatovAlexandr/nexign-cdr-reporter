package edu.asoldatov.nexigncdrreporter.report.reporter.impl;

import edu.asoldatov.nexigncdrreporter.common.CallType;
import edu.asoldatov.nexigncdrreporter.common.Rate;
import edu.asoldatov.nexigncdrreporter.record.model.Call;
import edu.asoldatov.nexigncdrreporter.report.model.ReportRow;
import edu.asoldatov.nexigncdrreporter.report.reporter.RateReporter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RegularRateReporter extends RateReporter {

    private final static double COST_BY_CHEAP_MINUTE = 0.5;
    private final static double COST_BY_EXPENSIVE_MINUTE = 1.5;
    private final static int FIRST_CHEAP_OUTGOING_MINUTES_COUNT = 100;

    @Override
    protected List<ReportRow> map(Set<Call> calls) {
        List<ReportRow> result = new ArrayList<>(calls.size());

        int remainingMinutes = FIRST_CHEAP_OUTGOING_MINUTES_COUNT;

        for (Call call : calls) {
            Duration duration = calculateDuration(call);
            long callMinutesDuration = calculateCallMinutesDuration(duration);

            double cost = 0;
            if (call.getCallType() == CallType.OUTGOING) {
                long cheapMinutes = calculateCheapMinutesInCall(remainingMinutes, callMinutesDuration);
                long expensiveMinutes = calculateExpensiveMinutesInCall(remainingMinutes, callMinutesDuration);
                remainingMinutes -= cheapMinutes;
                cost = cheapMinutes * COST_BY_CHEAP_MINUTE + expensiveMinutes * COST_BY_EXPENSIVE_MINUTE;
            }

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
        return Rate.REGULAR;
    }

    private long calculateCheapMinutesInCall(int remainingMinutes, long callMinutesDuration) {
        return remainingMinutes > callMinutesDuration ? callMinutesDuration : remainingMinutes;
    }

    private long calculateExpensiveMinutesInCall(int remainingMinutes, long callMinutesDuration) {
        return callMinutesDuration > remainingMinutes ? callMinutesDuration - remainingMinutes : 0;
    }
}

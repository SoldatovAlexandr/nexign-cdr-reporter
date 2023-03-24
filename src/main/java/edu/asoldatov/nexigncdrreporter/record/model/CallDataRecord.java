package edu.asoldatov.nexigncdrreporter.record.model;

import edu.asoldatov.nexigncdrreporter.common.CallType;
import edu.asoldatov.nexigncdrreporter.common.Rate;

import java.time.LocalDateTime;

public class CallDataRecord {

    private final String number;
    private final CallType callType;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final Rate rate;

    public CallDataRecord(String number, CallType callType, LocalDateTime start, LocalDateTime end, Rate rate) {
        this.number = number;
        this.callType = callType;
        this.start = start;
        this.end = end;
        this.rate = rate;
    }

    public String getNumber() {
        return number;
    }

    public CallType getCallType() {
        return callType;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public Rate getRate() {
        return rate;
    }
}

package edu.asoldatov.nexigncdrreporter.record.model;

import edu.asoldatov.nexigncdrreporter.common.CallType;

import java.time.LocalDateTime;
import java.util.Objects;

public class Call {

    private final CallType callType;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Call(CallType callType, LocalDateTime start, LocalDateTime end) {
        this.callType = callType;
        this.start = start;
        this.end = end;
    }

    public static Call of(CallDataRecord record) {
        return new Call(record.getCallType(), record.getStart(), record.getEnd());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Call call = (Call) o;
        return callType == call.callType && Objects.equals(start, call.start) && Objects.equals(end, call.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callType, start, end);
    }
}

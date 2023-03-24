package edu.asoldatov.nexigncdrreporter.record.model;

import edu.asoldatov.nexigncdrreporter.common.Rate;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Subscriber {

    private final String number;
    private final Rate rate;
    private final Set<Call> calls;

    public Subscriber(String number, Rate rate) {
        this.number = number;
        this.rate = rate;
        this.calls = new TreeSet<>(Comparator.comparing(Call::getStart));
    }

    public static Subscriber of(CallDataRecord record) {
        return new Subscriber(record.getNumber(), record.getRate());
    }

    public void addCall(Call call) {
        calls.add(call);
    }

    public String getNumber() {
        return number;
    }

    public Rate getRate() {
        return rate;
    }

    public Set<Call> getCalls() {
        return calls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(number, that.number) && rate == that.rate && Objects.equals(calls, that.calls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, rate, calls);
    }
}

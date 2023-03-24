package edu.asoldatov.nexigncdrreporter.report.model;

import java.util.Objects;

public class ReportRow {

    private final String type;
    private final String start;
    private final String end;
    private final String duration;
    private final double cost;

    public ReportRow(String type, String start, String end, String duration, double cost) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDuration() {
        return duration;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportRow reportRow = (ReportRow) o;
        return Double.compare(reportRow.cost, cost) == 0 && Objects.equals(type, reportRow.type)
                && Objects.equals(start, reportRow.start) && Objects.equals(end, reportRow.end)
                && Objects.equals(duration, reportRow.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, start, end, duration, cost);
    }
}

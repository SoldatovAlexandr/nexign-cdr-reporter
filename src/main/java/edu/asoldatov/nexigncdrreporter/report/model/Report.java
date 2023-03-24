package edu.asoldatov.nexigncdrreporter.report.model;

import java.util.List;
import java.util.Objects;

public class Report {

    private final String tariffIndex;
    private final String number;
    private final List<ReportRow> rows;
    private final double totalCost;

    public Report(String tariffIndex, String number, List<ReportRow> rows, double totalCost) {
        this.tariffIndex = tariffIndex;
        this.number = number;
        this.rows = rows;
        this.totalCost = totalCost;
    }

    public String getTariffIndex() {
        return tariffIndex;
    }

    public String getNumber() {
        return number;
    }

    public List<ReportRow> getRows() {
        return rows;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Double.compare(report.totalCost, totalCost) == 0 && Objects.equals(tariffIndex, report.tariffIndex)
                && Objects.equals(number, report.number) && Objects.equals(rows, report.rows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tariffIndex, number, rows, totalCost);
    }
}

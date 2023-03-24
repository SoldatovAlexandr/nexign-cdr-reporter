package edu.asoldatov.nexigncdrreporter.report.service.impl;

import edu.asoldatov.nexigncdrreporter.common.Rate;
import edu.asoldatov.nexigncdrreporter.record.model.Subscriber;
import edu.asoldatov.nexigncdrreporter.report.model.Report;
import edu.asoldatov.nexigncdrreporter.report.reporter.Reporter;
import edu.asoldatov.nexigncdrreporter.report.service.ReportExporter;
import edu.asoldatov.nexigncdrreporter.report.service.ReportService;

import java.util.EnumMap;
import java.util.List;

public class ReportServiceImpl implements ReportService {

    private final EnumMap<Rate, Reporter> reporters;
    private final ReportExporter reportExporter;

    public ReportServiceImpl(List<Reporter> reporters, ReportExporter reportExporter) {
        this.reportExporter = reportExporter;
        this.reporters = new EnumMap<>(Rate.class);
        reporters.forEach(reporter -> this.reporters.put(reporter.getRate(), reporter));
    }

    @Override
    public void doReport(Subscriber subscriber) {
        Reporter reporter = getReporterByRate(subscriber.getRate());
        Report report = reporter.generate(subscriber);
        reportExporter.save(report);
    }

    private Reporter getReporterByRate(Rate rate) {
        Reporter reporter = reporters.get(rate);
        if (reporter == null) {
            throw new RuntimeException(String.format("Unsupported rate [%s]", rate));
        }
        return reporter;
    }
}

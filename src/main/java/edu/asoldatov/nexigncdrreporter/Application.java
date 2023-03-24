package edu.asoldatov.nexigncdrreporter;

import edu.asoldatov.nexigncdrreporter.record.model.Subscriber;
import edu.asoldatov.nexigncdrreporter.record.importer.CdrImporter;
import edu.asoldatov.nexigncdrreporter.record.importer.CdrImporterImpl;
import edu.asoldatov.nexigncdrreporter.report.reporter.Reporter;
import edu.asoldatov.nexigncdrreporter.report.reporter.impl.MinuteRateReporter;
import edu.asoldatov.nexigncdrreporter.report.reporter.impl.RegularRateReporter;
import edu.asoldatov.nexigncdrreporter.report.reporter.impl.UnlimitedRateReporter;
import edu.asoldatov.nexigncdrreporter.report.service.ReportExporter;
import edu.asoldatov.nexigncdrreporter.report.service.ReportService;
import edu.asoldatov.nexigncdrreporter.report.service.impl.ReportExporterImpl;
import edu.asoldatov.nexigncdrreporter.report.service.impl.ReportServiceImpl;

import java.util.Collection;
import java.util.List;

public class Application {

    private final static String DEFAULT_FILE_NAME = "sdr.txt";

    public static void main(String[] args) {
        System.out.println("Start application");

        Application.start(DEFAULT_FILE_NAME);

        System.out.println("Finish application");
    }

    public static void start(String fileName) {
        List<Reporter> reporters = List.of(new MinuteRateReporter(), new RegularRateReporter(), new UnlimitedRateReporter());
        ReportExporter reportExporter = new ReportExporterImpl();
        CdrImporter importer = new CdrImporterImpl();
        ReportService reportService = new ReportServiceImpl(reporters, reportExporter);

        Collection<Subscriber> subscribers = importer.importInternal(fileName);
        subscribers.forEach(reportService::doReport);
    }
}

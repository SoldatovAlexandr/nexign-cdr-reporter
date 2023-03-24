package edu.asoldatov.nexigncdrreporter.report.service.impl;

import edu.asoldatov.nexigncdrreporter.report.model.Report;
import edu.asoldatov.nexigncdrreporter.report.model.ReportRow;
import edu.asoldatov.nexigncdrreporter.report.service.ReportExporter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportExporterImpl implements ReportExporter {

    private final static String PATH = "reports/";
    private final static String FILE_EXTENSION = ".txt";
    private final static String HEADER_FORMAT = "Tariff index: %s\n";
    private final static String NUMBER_FORMAT = "Report for phone number %s:\n";
    private final static String DELIMITER_LINE = "----------------------------------------------------------------------------";
    private final static String TABLE_HEADER_LINE = "| Call Type |   Start Time        |     End Time        | Duration | Cost  |";
    private final static String TABLE_ROW_FORMAT = "|     %s    | %s | %s | %s | %5.2f |\n";
    private final static String FOOTER_FORMAT = "|                                           Total Cost: |   %7.2f rubles |\n";

    @Override
    public void save(Report report) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(buildFileName(report)))) {
            printWriter.printf(HEADER_FORMAT, report.getTariffIndex());
            printWriter.println(DELIMITER_LINE);
            printWriter.printf(NUMBER_FORMAT, report.getNumber());

            printWriter.println(DELIMITER_LINE);

            printWriter.println(TABLE_HEADER_LINE);
            printWriter.println(DELIMITER_LINE);
            for (ReportRow row : report.getRows()) {
                printWriter.printf(TABLE_ROW_FORMAT, row.getType(), row.getStart(), row.getEnd(), row.getDuration(), row.getCost());
            }
            printWriter.println(DELIMITER_LINE);

            printWriter.printf(FOOTER_FORMAT, report.getTotalCost());
            printWriter.println(DELIMITER_LINE);

        } catch (IOException e) {
            throw new RuntimeException(String.format("Can not save report for subscriber number [%s]", report.getNumber()), e);
        }
    }

    private String buildFileName(Report report) {
        return PATH + report.getNumber() + FILE_EXTENSION;
    }
}

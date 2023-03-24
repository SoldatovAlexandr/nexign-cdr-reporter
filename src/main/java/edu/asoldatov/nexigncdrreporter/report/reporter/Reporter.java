package edu.asoldatov.nexigncdrreporter.report.reporter;

import edu.asoldatov.nexigncdrreporter.common.Rate;
import edu.asoldatov.nexigncdrreporter.report.model.Report;
import edu.asoldatov.nexigncdrreporter.record.model.Subscriber;

import java.time.format.DateTimeFormatter;

public interface Reporter {

    DateTimeFormatter REPORT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    String DURATION_PATTERN = "%02d:%02d:%02d";

    Report generate(Subscriber subscriber);

    Rate getRate();

}

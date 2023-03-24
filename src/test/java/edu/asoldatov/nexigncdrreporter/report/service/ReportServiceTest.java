package edu.asoldatov.nexigncdrreporter.report.service;

import edu.asoldatov.nexigncdrreporter.common.Rate;
import edu.asoldatov.nexigncdrreporter.record.model.Subscriber;
import edu.asoldatov.nexigncdrreporter.report.model.Report;
import edu.asoldatov.nexigncdrreporter.report.reporter.Reporter;
import edu.asoldatov.nexigncdrreporter.report.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class ReportServiceTest {

    private ReportService reportService;

    private final ReportExporter reportExporter = mock(ReportExporter.class);
    private final Reporter reporter = mock(Reporter.class);

    private final static Subscriber SUBSCRIBER = new Subscriber("73204411037", Rate.MINUTE);

    @BeforeEach
    void setUp() {
        when(reporter.getRate()).thenReturn(Rate.MINUTE);

        reportService = new ReportServiceImpl(List.of(reporter), reportExporter);
    }

    @Test
    void doReport() {
        Report report = mock(Report.class);
        when(reporter.generate(SUBSCRIBER)).thenReturn(report);

        reportService.doReport(SUBSCRIBER);

        verify(reporter, times(1)).generate(SUBSCRIBER);
        verify(reportExporter, times(1)).save(report);
    }
}

package application.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportFactory {

    public static final String PDF = "PDF";
    public static final String CSV = "CSV";

    private PDFReport pdfReport;
    private CSVReport csvReport;

    @Autowired
    public ReportFactory(PDFReport pdfReport, CSVReport csvReport) {
        this.pdfReport = pdfReport;
        this.csvReport = csvReport;
    }

    public IReport report(String format) {
        switch (format) {
            case PDF:
                return pdfReport;
            case CSV:
                return csvReport;
            default:
                return null;
        }
    }
}

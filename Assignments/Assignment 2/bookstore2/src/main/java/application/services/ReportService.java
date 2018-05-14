package application.services;

import application.model.Book;
import application.reports.IReport;
import application.reports.ReportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService implements IReportService {

    private ReportFactory reportFactory;
    private IBookService bookService;

    @Autowired
    public ReportService(ReportFactory reportFactory, IBookService bookService) {
        this.reportFactory = reportFactory;
        this.bookService = bookService;
    }

    @Override
    public String reportOutOfStock(String type) {
        IReport report = reportFactory.report(type);
        if (report != null) {
            List<Book> allBooks = bookService.findAll();
            ArrayList<Book> books = new ArrayList<>();
            for (Book book :allBooks) {
                if (book.getQuantity() == 0)
                {
                    books.add(book);
                }
            }
            String path = null;
            try {
                path = report.reportBooks(books);
            } catch (IOException e) {
                return null;
            }
            return path;
        }
        return null;
    }
}

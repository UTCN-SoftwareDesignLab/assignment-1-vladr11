package application.reports;

import application.model.Book;

import java.io.IOException;
import java.util.List;

public interface IReport {

    String reportBooks(List<Book> books) throws IOException;
}

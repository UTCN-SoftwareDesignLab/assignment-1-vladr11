package application.reports;

import application.model.Book;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class CSVReport implements IReport {

    private static final String CSV_PATH = "./report.csv";

    @Override
    public String reportBooks(List<Book> books) throws IOException {
        String path = CSV_PATH;
        try (
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Title", "Author", "Genre", "Quantity", "Price"));

        ) {
            for (Book book: books) {
                csvPrinter.printRecord(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getGenre().getName(),
                        book.getQuantity(),
                        book.getPrice()
                        );
            }
            csvPrinter.flush();
            return path;
        }
    }
}

package application.reports;

import application.model.Book;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class PDFReport implements IReport {

    private static final String PDF_PATH = "./report.pdf";

    @Override
    public String reportBooks(List<Book> books) throws IOException {
        PDDocument document = new PDDocument();
        PDPageContentStream contentStream=null;
        int bookNo=0;

        for (Book book : books) {
            if (bookNo%6==0){
                if (contentStream!=null){
                    finishCurrentContentStream(contentStream);
                }
                contentStream=intializeNewContentStream(document);
            }
            contentStream.showText("title = " + book.getTitle());
            contentStream.newLine();
            contentStream.showText("author = " + book.getAuthor().getName());
            contentStream.newLine();
            contentStream.showText("price = " + book.getPrice());
            contentStream.newLine();
            contentStream.showText("quantity = " + book.getQuantity());
            contentStream.newLine();
            contentStream.newLine();
            bookNo++;
        }
        if (contentStream!=null) finishCurrentContentStream(contentStream);

        String path = PDF_PATH;
        document.save(path);
        document.close();
        return path;
    }

    private PDPageContentStream intializeNewContentStream(PDDocument document) throws IOException {
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 18);
        contentStream.setLeading(16.f);
        contentStream.newLineAtOffset(25, 725);
        return contentStream;
    }

    private void finishCurrentContentStream(PDPageContentStream contentStream) throws IOException {
        contentStream.endText();
        contentStream.close();
    }
}

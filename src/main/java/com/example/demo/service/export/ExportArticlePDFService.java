package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExportArticlePDFService {

    @Autowired
    private ArticleRepository articleRepository;

    public void exportAll(OutputStream outputStream) throws IOException, DocumentException {
        List<Article> allArticles = articleRepository.findAll();

        // itext
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        String line = "Listes des articles :";
        document.add(new Paragraph(line));

        PdfPTable table = new PdfPTable(2);
        table.addCell("Libellé");
        table.addCell("Prix");

        for (Article article : allArticles) {
            table.addCell(article.getLibelle());
            table.addCell(Double.toString(article.getPrix()));
        }

        document.add(table);
        document.close();

    }
}

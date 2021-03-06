package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExportArticleExcelService {

    @Autowired
    private ArticleRepository articleRepository;

    public void exportAll(OutputStream outputStream) throws IOException {
        List<Article> allArticles = articleRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Articles");

        String value = "";
        for (int i = 0; i <= allArticles.size(); i++) {
            int indexArticle = i;
            if ( i > 0 ) {
                indexArticle = i -1;
            }
            Article article = allArticles.get(indexArticle);
            Row row = sheet.createRow(i);

            // boucle colonnes
            for (int j = 0; j < 2; j++) {
                Cell cell = row.createCell(j);

                if (j == 0 && i ==0) {
                    value = "Libelle";
                } else if (i == 0 && j == 1) {
                    value = "Prix";
                } else if (j == 0) {
                    value = article.getLibelle();
                } else if (j == 1) {
                    value = Double.toString(article.getPrix());
                }

                cell.setCellValue(value);
                sheet.autoSizeColumn(j);
            }
        }
        workbook.write(outputStream);
        workbook.close();
    }

}

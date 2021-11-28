package edu.phystech.jdbcdemo;

import edu.phystech.jdbcdemo.queries.reports.FormatofReport;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelLoader <T extends FormatofReport>  {
private XSSFCellStyle cellStyle;
private XSSFCellStyle headerCellStyle;


    public void buildExcelTable(ArrayList<T> data, String name, ArrayList<String> headers) throws IOException {
        System.out.println("exceltable");
        File newFile = new File(name);
        if (!newFile.getParentFile().exists())
            System.out.println("ParentFilenotExist");
            newFile.getParentFile().mkdirs();
        if (!newFile.exists())
            System.out.println("fileNotExist");
            newFile.createNewFile();
        try(
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        XSSFWorkbook report = new XSSFWorkbook()) {
            XSSFSheet sheet = report.createSheet("results");
            sheet.createFreezePane(0, 1);
            createCellTypes(report);


            int numColumns = headers.size();
            System.out.println("add headers");
            XSSFRow headRow = sheet.createRow(0);
            for (int i = 0; i < numColumns; i++) {
                Cell cell = headRow.createCell(i);
                cell.setCellValue(headers.get(i));
                cell.setCellStyle(headerCellStyle);
            }

            System.out.println("add data from query");
            for (int i = 0; i < data.size(); i++) {
                XSSFRow curRow = sheet.createRow(i + 1);
                System.out.println("row created");
                for (int j = 0; j < numColumns; j++) {
                    Cell cell = curRow.createCell(j);
                    System.out.println("cell created");
                    cell.setCellValue(data.get(i).getItems().get(j).toString());
                    System.out.println("sel valued");
                    cell.setCellStyle(cellStyle);
                    System.out.println("cell set styled");

                }
            }
            System.out.println("Added");
            // Resize columns to fit their data
            for (int x = 0; x < sheet.getRow(0).getPhysicalNumberOfCells(); x++) {
                sheet.autoSizeColumn(x);
            }
            System.out.println("Already finish");
            report.write(fileOutputStream);
        }

    }
    private void createCellTypes(XSSFWorkbook report) {
        cellStyle = report.createCellStyle();
        Font font = report.createFont();
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);

        headerCellStyle = report.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        font = report.createFont();
        font.setBold(true);
        font.setColor(HSSFColor.INDIGO.index);
        font.setFontHeightInPoints((short) 14);
        headerCellStyle.setFont(font);
    }
}

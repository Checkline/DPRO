package com.checkline.dpro;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class SpreadsheetCreator {

	private Workbook wb;
	private Map<String, CellStyle> cellStyles;

	public SpreadsheetCreator(DPro dpro) {
		this.wb = new HSSFWorkbook();
		this.cellStyles = new HashMap<String, CellStyle>();
		this.createStyles(wb);
		this.createResultSheet(wb);
		this.createRawDataSheet(wb, dpro.getReadings());

	}
	
	public void saveFile(String path) {
		String date = new SimpleDateFormat("yyyyMMdd'T'HHmmss").format(new Date());
        String file = Paths.get(path, "DPRO_"+date+".xls").toString();

        FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
        try {
			wb.write(out);
			out.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private void createResultSheet(Workbook wb) {
		Sheet sheet = wb.createSheet("Results");
		Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(12.75f);
        Cell cell = headerRow.createCell(0);
        cell.setCellValue("Test");
        cell.setCellStyle(this.cellStyles.get("header"));
	}
	
	private void createRawDataSheet(Workbook wb, ArrayList<ReadingSet> readings) {
		Sheet rawDataSheet = wb.createSheet("Raw Reading Data");
		int readingSetNumber = 1;
		int rowNumber = 0;
		
		for (ReadingSet readingSet : readings) {
			//create reading #
			Row readingNumberRow = rawDataSheet.createRow(rowNumber++);
	        readingNumberRow.setHeightInPoints(12.75f);
	        Cell readingSetNumberCell = readingNumberRow.createCell(0);
	        readingSetNumberCell.setCellValue("Reading #" + readingSetNumber);
	        readingSetNumberCell.setCellStyle(this.cellStyles.get("header"));
	        rawDataSheet.addMergedRegion(new CellRangeAddress(rowNumber-1,rowNumber-1,0,2));
	        Row headerRow = rawDataSheet.createRow(rowNumber++);
	        headerRow.setHeightInPoints(12.75f);
	        Cell headerNumberCell = headerRow.createCell(0);
	        Cell headerValueCell = headerRow.createCell(1);
	        Cell headerTimeCell = headerRow.createCell(2);
	    
	        headerNumberCell.setCellValue("#");
	        headerNumberCell.setCellStyle(this.cellStyles.get("rawHeaderReadingNumber"));
	        headerValueCell.setCellValue("Value");
	        headerTimeCell.setCellValue("Time (nanoseconds)");
	        
	        int readingNumber = 1;
	        for (Reading reading : readingSet.getReadings()) {
	        	Row readingRow = rawDataSheet.createRow(rowNumber++);
	        	readingRow.setHeightInPoints(12.75f);
	        	Cell readingNumberCell = readingRow.createCell(0);
	        	Cell readingValueCell = readingRow.createCell(1);
	        	Cell readingTimeCell = readingRow.createCell(2);
	        	readingNumberCell.setCellValue(readingNumber++);
	        	readingValueCell.setCellValue(reading.getValue());
	        	readingTimeCell.setCellValue(reading.getTimeTaken());
	        }
	        rawDataSheet.createRow(rowNumber++); //spacer row
	        readingSetNumber++;
		}
		
		rawDataSheet.setColumnWidth(0, 1500);
		rawDataSheet.setColumnWidth(2, 5000);
	}
	
    private void createStyles(Workbook wb){
        CellStyle style;
        Font headerFont = wb.createFont();
        System.out.println(headerFont.getFontName());
        headerFont.setBold(true);
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        this.cellStyles.put("header", style);
        
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyles.put("rawHeaderReadingNumber", style);

    }
    
    private CellStyle createBorderedStyle(Workbook wb){
        BorderStyle thin = BorderStyle.THIN;
        short black = IndexedColors.BLACK.getIndex();
        
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(thin);
        style.setRightBorderColor(black);
        style.setBorderBottom(thin);
        style.setBottomBorderColor(black);
        style.setBorderLeft(thin);
        style.setLeftBorderColor(black);
        style.setBorderTop(thin);
        style.setTopBorderColor(black);
        return style;
    }
    
}

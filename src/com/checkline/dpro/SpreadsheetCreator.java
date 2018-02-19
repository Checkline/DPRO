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
import org.apache.poi.ss.usermodel.CreationHelper;
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
		this.createResultSheet(wb, dpro);
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
	
	private void createResultSheet(Workbook wb, DPro dpro) {
		Sheet sheet = wb.createSheet("Results");
		int rowNumber = 0;
		
		Row dproHeaderRow = sheet.createRow(rowNumber++);
		Cell dproTitleCell = dproHeaderRow.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(rowNumber-1,rowNumber-1,0,2));
		dproTitleCell.setCellValue("DPRO Results");
		dproTitleCell.setCellStyle(this.cellStyles.get("header"));
		
		sheet.createRow(rowNumber++); //spacer
		
        Row dateRow = sheet.createRow(rowNumber++);
        dateRow.createCell(1);
        dateRow.createCell(2);
        dateRow.getCell(1).setCellValue("Date");
        dateRow.getCell(1).setCellStyle(this.cellStyles.get("alignRight"));
        dateRow.getCell(2).setCellValue(new Date());
        dateRow.getCell(2).setCellStyle(this.cellStyles.get("cell_date"));
        
        sheet.createRow(rowNumber++); //spacer
        
        Row amtRow = sheet.createRow(rowNumber++);
        Row delayRow = sheet.createRow(rowNumber++);
        Row highRow = sheet.createRow(rowNumber++);
        Row lowRow = sheet.createRow(rowNumber++);
        Row avgRow = sheet.createRow(rowNumber++);
        Row devRow = sheet.createRow(rowNumber++);
        
        amtRow.createCell(1);
        amtRow.createCell(2);
        amtRow.getCell(1).setCellValue("# of Readings");
        amtRow.getCell(1).setCellStyle(this.cellStyles.get("alignRight"));
        amtRow.getCell(2).setCellValue(dpro.getReadings().size());
        
        delayRow.createCell(1);
        delayRow.createCell(2);
        delayRow.getCell(1).setCellValue("Delay (seconds)");
        delayRow.getCell(1).setCellStyle(this.cellStyles.get("alignRight"));
        delayRow.getCell(2).setCellValue(dpro.getDelay());
        delayRow.getCell(2).setCellStyle(this.cellStyles.get("truncateNumberTwoDecimalPlaces"));
        
        highRow.createCell(1);
        highRow.createCell(2);
        highRow.getCell(1).setCellValue("MAX");
        highRow.getCell(1).setCellStyle(this.cellStyles.get("alignRight"));
        highRow.getCell(2).setCellValue(dpro.getHigh());
        highRow.getCell(2).setCellStyle(this.cellStyles.get("truncateNumberTwoDecimalPlaces"));
        
        lowRow.createCell(1);
        lowRow.createCell(2);
        lowRow.getCell(1).setCellValue("MIN");
        lowRow.getCell(1).setCellStyle(this.cellStyles.get("alignRight"));
        lowRow.getCell(2).setCellValue(dpro.getLow());
        lowRow.getCell(2).setCellStyle(this.cellStyles.get("truncateNumberTwoDecimalPlaces"));
        
        avgRow.createCell(1);
        avgRow.createCell(2);
        avgRow.getCell(1).setCellValue("AVG");
        avgRow.getCell(1).setCellStyle(this.cellStyles.get("alignRight"));
        avgRow.getCell(2).setCellValue(dpro.getAvg());
        avgRow.getCell(2).setCellStyle(this.cellStyles.get("truncateNumberTwoDecimalPlaces"));
        
        devRow.createCell(1);
        devRow.createCell(2);
        devRow.getCell(1).setCellValue("DEV");
        devRow.getCell(1).setCellStyle(this.cellStyles.get("alignRight"));
        devRow.getCell(2).setCellValue(dpro.getStddev());
        devRow.getCell(2).setCellStyle(this.cellStyles.get("truncateNumberTwoDecimalPlaces"));
        
        sheet.createRow(rowNumber++); //spacer
        

		int readingNumber = 1;
		
		
		Row readingNumberRow = sheet.createRow(rowNumber++);
        readingNumberRow.setHeightInPoints(12.75f);
        Cell readingSetNumberCell = readingNumberRow.createCell(0);
        readingSetNumberCell.setCellValue("Readings");
        readingSetNumberCell.setCellStyle(this.cellStyles.get("header"));
        sheet.addMergedRegion(new CellRangeAddress(rowNumber-1,rowNumber-1,0,2));
        Row readingHeaderRow = sheet.createRow(rowNumber++);
        readingHeaderRow.setHeightInPoints(12.75f);
        Cell headerNumberCell = readingHeaderRow.createCell(0);
        Cell headerPeakCell = readingHeaderRow.createCell(1);
        Cell headerFinalCell = readingHeaderRow.createCell(2);
        headerNumberCell.setCellValue("#");
        headerNumberCell.setCellStyle(this.cellStyles.get("alignRight"));
        headerPeakCell.setCellValue("Peak Value");
        headerPeakCell.setCellStyle(this.cellStyles.get("alignRight"));
        headerFinalCell.setCellValue("Final Value");
        headerFinalCell.setCellStyle(this.cellStyles.get("alignRight"));
		
		for (ReadingSet readingSet : dpro.getReadings()) {
			//create reading #
        	Row readingRow = sheet.createRow(rowNumber++);
        	readingRow.setHeightInPoints(12.75f);
        	Cell readingNumberCell = readingRow.createCell(0);
        	Cell readingPeakCell = readingRow.createCell(1);
        	Cell readingFinalCell = readingRow.createCell(2);
        	readingNumberCell.setCellValue(readingNumber++);
        	readingPeakCell.setCellValue(readingSet.getMaxValue());
        	readingFinalCell.setCellValue(readingSet.getFinalValue());
		}
		
		sheet.setColumnWidth(0, 1500);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
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
	        headerNumberCell.setCellStyle(this.cellStyles.get("alignRight"));
	        headerValueCell.setCellValue("Value");
	        headerValueCell.setCellStyle(this.cellStyles.get("alignRight"));
	        headerTimeCell.setCellValue("Time (nanoseconds)");
	        headerTimeCell.setCellStyle(this.cellStyles.get("alignRight"));
	        
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
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        this.cellStyles.put("header", style);

        style = this.createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        this.cellStyles.put("borderedHeader", style);
        
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyles.put("alignRight", style);
        
        style = wb.createCellStyle();
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        this.cellStyles.put("truncateNumberTwoDecimalPlaces", style);
        
        style = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        style.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));        
        this.cellStyles.put("cell_date", style);
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

package com.learning.sqlinjection.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.learning.weblogin.entity.UserDetail;
import com.learning.weblogin.service.ExcelGeneratorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExcelGeneratorServiceImpl implements ExcelGeneratorService {

	private static final String[] columns = new String[] { "Id", "User Id", "Address", "Credit Card No", "Comment" };

	@Override
	public Workbook generateExcel(List<UserDetail> userDetails) {
		Workbook workbook = new HSSFWorkbook();

		Sheet sheet = createSheet(workbook);

		Row header = sheet.createRow(0);

		CellStyle headerStyle = createHeaderStyle(workbook);

		setHeaderCells(header, headerStyle);

		setDataCells(workbook, userDetails, sheet);

		return workbook;
	}

	private Sheet createSheet(Workbook workbook) {
		Sheet sheet = workbook.createSheet("user_details");
		sheet.setColumnWidth(0, 10000);
		sheet.setColumnWidth(1, 2000);
		sheet.setColumnWidth(2, 9000);
		sheet.setColumnWidth(3, 6000);
		sheet.setColumnWidth(4, 15000);
		return sheet;
	}

	private CellStyle createHeaderStyle(Workbook workbook) {
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		HSSFFont font = ((HSSFWorkbook) workbook).createFont();
		font.setFontName("Arial");
		font.setBold(true);
		headerStyle.setFont(font);
		return headerStyle;
	}

	private void setHeaderCells(Row header, CellStyle headerStyle) {
		Cell cell;
		for (int i = 0; i < columns.length; i++) {
			cell = header.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerStyle);
		}
	}

	private void setDataCells(Workbook workbook, List<UserDetail> userDetails, Sheet sheet) {
		int rowCount = 1;
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);
		for (UserDetail userDetail : userDetails) {
			Row row = sheet.createRow(rowCount++);
			setDataToCell(row, cellStyle, userDetail.getId().toString(), 0);
			setDataToCell(row, cellStyle, userDetail.getUserId().toString(), 1);
			setDataToCell(row, cellStyle, userDetail.getAddress(), 2);
			setDataToCell(row, cellStyle, userDetail.getCreditCardNo(), 3);
			setDataToCell(row, cellStyle, userDetail.getComment(), 4);
		}
	}

	private void setDataToCell(Row row, CellStyle cellStyle, String data, int cellCount) {
		Cell cell = row.createCell(cellCount);
		if (data != null && data.startsWith("=")) {
			try {
				cell.setCellFormula(data.substring(1));
			} catch (FormulaParseException | IllegalStateException e) {
				log.error("Error setting formula definition!");
				cell.setCellValue("");
			}
		} else {
			cell.setCellValue(data);
		}
		cell.setCellStyle(cellStyle);
	}

}

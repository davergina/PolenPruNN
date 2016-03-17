package helper.file;

import java.io.File;
import java.io.IOException;

import constants.MyExcelWriterConstants;


import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class MyExcelWriter{

	private WritableWorkbook workbook;
	private WritableSheet mseSheet, accuracySheet, parametersSheet;
	
	private static final int CELL_WIDTH = 15;
	
	public MyExcelWriter(String fileName) {
		createAttributes(fileName);
	}

	private void createAttributes(String fileName) {
		try {
			workbook = Workbook.createWorkbook(new File(fileName + ".xls"));
			mseSheet = workbook.createSheet("MSE", 0);
			accuracySheet = workbook.createSheet("Accuracy", 1);
			parametersSheet = workbook.createSheet("Parameters", 2);
		} catch(IOException ioEx) {
			ioEx.printStackTrace();
		}
	}
	
	public boolean closeWorkbook() {
		try {
			workbook.write();
			workbook.close();
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			return false;
		} catch (WriteException wEx) {
			wEx.printStackTrace();
			return false;
		}
		return true;
	}
	
	//for numbers
	public boolean insertCellData(int col, int row, double value, int sheet){
		try {
			jxl.write.Number num = new jxl.write.Number(col, row, value);
			switch (sheet) {
				case MyExcelWriterConstants.MSE_SHEET: 
					mseSheet.setColumnView(col, CELL_WIDTH);
					mseSheet.addCell(num);
					break;
				case MyExcelWriterConstants.ACCURACY_SHEET:
					accuracySheet.setColumnView(col, CELL_WIDTH);
					accuracySheet.addCell(num);
					break;
				case MyExcelWriterConstants.PARAMETERS_SHEET:
					parametersSheet.setColumnView(col, CELL_WIDTH);
					parametersSheet.addCell(num);
					break;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	//for text
	public boolean insertCellData(int col, int row, String value, int sheet) {
		try {
			jxl.write.Label label = new jxl.write.Label(col, row, value);
			switch (sheet) {
				case MyExcelWriterConstants.MSE_SHEET: 
					mseSheet.setColumnView(col, CELL_WIDTH);
					mseSheet.addCell(label);
					break;
				case MyExcelWriterConstants.ACCURACY_SHEET:
					accuracySheet.setColumnView(col, CELL_WIDTH);
					accuracySheet.addCell(label);
					break;
				case MyExcelWriterConstants.PARAMETERS_SHEET:
					parametersSheet.setColumnView(col, CELL_WIDTH);
					parametersSheet.addCell(label);
					break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean insertFormula(int col, int row, String value, int sheet) {
		try{
			jxl.write.Formula formula = new jxl.write.Formula(col, row, value);
			switch (sheet) {
				case MyExcelWriterConstants.MSE_SHEET: 
					mseSheet.addCell(formula);
					break;
				case MyExcelWriterConstants.ACCURACY_SHEET:
					accuracySheet.addCell(formula);
					break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/* START for unit testing */
	public static void main(String[] args){
		MyExcelWriter myExcelWriter = new MyExcelWriter("myExcelWriterTest");
		String avgFormula = "";
		int row = 1;
		int col = 1;
		int value;
		
		for (row = 1, value = 1; row < 6; row++, value++) {
			for (col = 1; col < 6; col++, value++) {
				myExcelWriter.insertCellData(col, row, value, MyExcelWriterConstants.MSE_SHEET);
				myExcelWriter.insertCellData(col, row, value, MyExcelWriterConstants.ACCURACY_SHEET);
			}
			value--;
		}
		
		for (row = 1; row < 6; row++) {
			avgFormula = "AVERAGE(" + ExcelHelper.getHeaderAtColumn(1) + (row+1) + 
					":" + ExcelHelper.getHeaderAtColumn(col-1) + (row+1) + ")";
			myExcelWriter.insertFormula(col+1, row, avgFormula, MyExcelWriterConstants.MSE_SHEET);
			myExcelWriter.insertFormula(col+1, row, avgFormula, MyExcelWriterConstants.ACCURACY_SHEET);
		}
		
		myExcelWriter.closeWorkbook();
	}
	/* END for unit testing */
}

package helper.file;

import constants.MyExcelWriterConstants;

public class ExcelHelper {

	public static String getHeaderAtColumn(int col) {
		String columnLetters = "";
		
		int index = col + 1;
		while (index>0) {
			int c = index % MyExcelWriterConstants.ALPHABET.length();
			columnLetters = MyExcelWriterConstants.ALPHABET.charAt(c) + columnLetters;
			
			if (c==0) {
				index--;
			}
			
			index = index / MyExcelWriterConstants.ALPHABET.length();
		} 

		
		return columnLetters;
	}
	
	public static String createMeanFormula(int specificRow, int startCol, int endCol) {
		StringBuilder avgFormula = new StringBuilder();
		avgFormula.append("AVERAGE(");
		avgFormula.append(getHeaderAtColumn(startCol));
		avgFormula.append(specificRow);
		avgFormula.append(":");
		avgFormula.append(getHeaderAtColumn(endCol));
		avgFormula.append(specificRow);
		avgFormula.append(")");
		
		return avgFormula.toString();
	}
}

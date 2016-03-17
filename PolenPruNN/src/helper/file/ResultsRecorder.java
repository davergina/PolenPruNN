package helper.file;


import java.util.ArrayList;
import constants.MyExcelWriterConstants;

public class ResultsRecorder{

	private int row;
	private int col;
	private int numberOfRows;
	private MyExcelWriter myExcelWriter;
	
	public ResultsRecorder(String fileName, ArrayList<String> parameters, int numberOfRows) {
		this.numberOfRows = numberOfRows;
		initialize(fileName);
		recordParameters(parameters);
	}
	
	private void initialize(String fileName) {
		myExcelWriter = new MyExcelWriter(fileName);
		myExcelWriter.insertCellData(0, 0, "Cycle\\Run", MyExcelWriterConstants.MSE_SHEET);
		myExcelWriter.insertCellData(0, 0, "Cycle\\Run", MyExcelWriterConstants.ACCURACY_SHEET);
		
		row = 1;
		col = 1;
	}
	
	//assumes format: Parameter, Value
	private void recordParameters(ArrayList<String> parameters) {
		int numberOfParameters = parameters.size();
		for (int i = 0; i < numberOfParameters; i++) {
			myExcelWriter.insertCellData(0, i, parameters.get(i), MyExcelWriterConstants.PARAMETERS_SHEET);
		}
	}
	
	public void recordCycle(double mse, double accuracy) {
		myExcelWriter.insertCellData(col, row, mse, MyExcelWriterConstants.MSE_SHEET);
		myExcelWriter.insertCellData(col, row, accuracy, MyExcelWriterConstants.ACCURACY_SHEET);
		row++;
	}
	
	public void startNewRun(double timeElapsed) {
		myExcelWriter.insertCellData(col, row++, " ", MyExcelWriterConstants.MSE_SHEET);
		myExcelWriter.insertCellData(col, row, timeElapsed, MyExcelWriterConstants.MSE_SHEET);
		col++;
		row = 1;
	}
	
	public void finishRecording() {
		writeMeanColumn();
		myExcelWriter.closeWorkbook();
	}
	
	private void writeMeanColumn() {
		for (int r = 1; r < (numberOfRows+1); r++) {		
			myExcelWriter.insertFormula(col+1, r, ExcelHelper.createMeanFormula(r+1, 1, col-1), MyExcelWriterConstants.MSE_SHEET);
			myExcelWriter.insertFormula(col+1, r, ExcelHelper.createMeanFormula(r+1, 1, col-1), MyExcelWriterConstants.ACCURACY_SHEET);
		}
		myExcelWriter.insertFormula(col+1, numberOfRows+2, ExcelHelper.createMeanFormula(numberOfRows+3, 1, col-1), 
				MyExcelWriterConstants.MSE_SHEET);
	}
	
	/* START for unit testing */
	public static void main(String[] args){
		int numberOfCycles = 8;
		int numberOfGeneration = 10;
		ResultsRecorder resultsRecorder = new ResultsRecorder("resultsRecorderTest", new ArrayList<String>(), numberOfCycles);
		
		for (int c=0; c < numberOfGeneration; c++) {
			for (int r=0; r < numberOfCycles; r++) {
				resultsRecorder.recordCycle(r*c, r*c*10);
			}
			resultsRecorder.startNewRun(1);
		}
		
		resultsRecorder.finishRecording();
	}
	/* END for unit testing */
}

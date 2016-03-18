package controller.data;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import helper.file.FileHelper;
import image.ImageProcessor;
import ui.ProgressPane;

public class DataReader extends FileHelper
{
	private double[][] inputVector;
	private double[][] outputVector;
	private ArrayList<String> filename = new ArrayList<String>();
	
	private ArrayList<BufferedImage> input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> output_list = new ArrayList<Integer>();
	
	private ImageProcessor iProcessor;
	private File dataFile;
	private ProgressPane progressPane;
	private BufferedReader bufferedReader;
	
	
	
	public DataReader(File dataFile) 
	{
		this.dataFile = dataFile;
//		this.progressPane = progressPane;
		iProcessor = new ImageProcessor();
	}

	/**
	 * reads .data file containing absolute paths of images and extracts features from each image
	 * 
	 * @return Data 
	 */
	public boolean read() 
	{
		int count = FileHelper.countFiles(dataFile);
//		progressPane.reset(count*2);
		String fname = "";
		File baseFile = DataLocationHandler.getBaseFolder();
		File file = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(dataFile));
			int classNumber;
        	BufferedImage image = null;
			while( (fname = bufferedReader.readLine()) != null ) 
			{
				file = new File(baseFile.getAbsolutePath()+"/"+fname);
//				System.out.println(""+baseFile.getAbsolutePath()+"/"+fname);
				image = ImageIO.read(file);
//        		System.out.println("IO reading done");
    			
        		if(image == null) {
//        			new MessageDialog(" Can't convert file to image: "+baseFile.getAbsolutePath()+File.separator+fname).setVisible(true);;
        			System.out.println("can't convert file to image");
        			return false;
        		}
        		image = iProcessor.resizeImage(image, ImageProcessor.WIDTH, ImageProcessor.HEIGHT);
        		classNumber =  Integer.parseInt(file.getParentFile().getName());
        		
        		input_list.add(image);
	        	output_list.add(classNumber);
//	        	progressPane.incrementBar();
        		filename.add(baseFile.getAbsolutePath()+"/"+fname);
			}			
		} catch (IIOException e) {
//			new MessageDialog(" Can't read input file: "+baseFile.getAbsolutePath()+File.separator+fname).setVisible(true);
			System.out.println("Can't read input file: IIOException");
			return false;
		} catch (FileNotFoundException e) {
//			new MessageDialog(" Can't read input file: "+baseFile.getAbsolutePath()+File.separator+fname).setVisible(true);
			System.out.println("Can't read input file: FileNotFound");
			return false;
		} catch (IOException e) {			
//			new MessageDialog(" Can't read input file: "+baseFile.getAbsolutePath()+File.separator+fname).setVisible(true);
			System.out.println("Can't read input file: IOException");
			return false;
		} catch (NumberFormatException e) {
//			new MessageDialog(" Cannot convert to integer (class number): "+baseFile.getAbsolutePath()+File.separator+fname).setVisible(true);
			System.out.println("Cannot convert to integer");
			return false;
		} 
		inputVector = iProcessor.createInputVectorArray(input_list);
    	outputVector = iProcessor.createOutputVector(output_list);

		return true;
	}
	
	public Data getData()
	{
		return new Data(filename, inputVector, outputVector);
	}
	
}

package es.zopa.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class VariableUtils {
	/**
	 * Transform String to Long
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 */
	
	public static Long StringToNumeric(String value) throws NumberFormatException{
		Long amount = -1L;
		try{
			amount = Long.valueOf(value);
			return amount;
		}catch(NumberFormatException e1) {
			throw new NumberFormatException("amount value (" + value + ") cannot tranform to Long");
		}
	}
	
	/**
	 * Transform String path to File 
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws NullPointerException
	 * @throws FileNotFoundException
	 */
	
	public static File StringToFile(String path) throws IOException,NullPointerException,FileNotFoundException{
		
		File fil = null;
		
		if (path == null){
			throw new NullPointerException("path is null");
		}
		
		if (path.trim().equalsIgnoreCase("")){
			throw new NullPointerException("path is empty");
		}
		
		if (path.length() < 4){
			throw new FileNotFoundException("" + path + " name has not the correct format");
		}
			
		if (!path.substring(path.length()-3,path.length()).trim().equalsIgnoreCase(Constants.CSV)){
			throw new IOException("" + path + " name is not a CSV file");
		}
			
		fil = new File(path);
				
		if (!fil.exists()){
			throw new FileNotFoundException("" + path + " doesn't exist");
		}
		
		if(!fil.isFile()){
			throw new IOException("" + path + " is not a File");
		}
		
		 String type = Files.probeContentType(fil.toPath());
		 
		 if (type == null){
			 throw new IOException("" + path + " is a binary file");
		 }
		 
		 if (!type.startsWith(Constants.text) && !type.startsWith(Constants.excel)){
			 throw new IOException("" + path + " is not a csv file. is a (" + type + ") file");
		 }
		 	
		return fil;
	}
	
	
	
}

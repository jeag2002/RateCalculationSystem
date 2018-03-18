package es.zopa.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.zopa.exceptions.EmptyDataException;
import es.zopa.exceptions.FormatLineException;
import es.zopa.model.request.AmountBean;

public class FileDigester {
	
	private static final String TOKEN_1 = ",";
	private static final String TOKEN_2 = ";";
	private static final int SIZE_DATA = 3;
	
	/**
	 * Transform InputFile to ArrayList of AmountBean
	 * @param Fil
	 * @return
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws FormatLineException
	 * @throws EmptyDataException
	 * @throws Exception
	 */
	
	
	public static List<AmountBean> processFile(File Fil) throws IOException, NumberFormatException, FormatLineException, EmptyDataException, Exception{
		List<AmountBean> aBList = new ArrayList<AmountBean>(); 
	
		FileReader in = null;
		BufferedReader br = null;
		String line = "";
		
		try{
			
			in = new FileReader(Fil);
			br = new BufferedReader(in);
			
			//process header
			line = br.readLine();
		
			if (line != null){
				//process numeric data.
				while((line = br.readLine())!=null){
					aBList.add(stringToAmount(line));
				}
			}
			
			if (aBList.size() == 0){throw new EmptyDataException("No data");}
			
			return orderAmountBeanList(aBList);
		
		}catch(IOException ioException){
			throw new IOException("File (" + Fil.getPath() + ") doesnt exist");
		}catch(NumberFormatException nFException){
			throw nFException;
		}catch(FormatLineException fLException){
			throw fLException;
		}catch(EmptyDataException eDException){
			throw eDException;
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if (br != null){
					br.close();		
				}
			}catch(Exception e){}
			
			try{
				if (in != null){
					in.close();
				}
			}catch(Exception e){}
			
		}
	}
	
	/**
	 * Order Amount List Asc
	 * @param aBList
	 * @return
	 */
	
	public static List<AmountBean> orderAmountBeanList(List<AmountBean> aBList){
		return aBList.stream().sorted((e1,e2)->e1.getRate().compareTo(e2.getRate())).collect(Collectors.toList());
	}
	
	
	
	/**
	 * Transform String to AmountBean
	 * @param line
	 * @return
	 */
	
	public static AmountBean stringToAmount(String line) throws FormatLineException,NumberFormatException{
		AmountBean aB = new AmountBean();
		String data[] = stringToStringArray(line);
		
		//Rate Double data to 3 decimal with round-up
		Double rateData = Double.parseDouble(data[1]);
		BigDecimal rateDataBD = (new BigDecimal(rateData)).setScale(4, RoundingMode.HALF_UP);
		
		//Long cashAval 
		Long cashAvalData = Long.parseLong(data[2]);
		BigDecimal cashAvalDataBD = new BigDecimal(cashAvalData);
				
		aB.setRate(rateDataBD);	
		aB.setCashAvailable(cashAvalDataBD);
		
		return aB;
	}
	
	/**
	 * String data to Array Data
	 * @param line
	 * @return
	 * @throws FormatLineException
	 */
	public static String[] stringToStringArray(String line) throws FormatLineException{
		
		String data[] = line.split(TOKEN_1);
		if (data.length == SIZE_DATA){
			return data;
		}
		data = line.split(TOKEN_2);
		if (data.length == SIZE_DATA){
			return data;
		}
		throw new FormatLineException("Line (" + line + ") cannot be processed");
		
		
	}
	
	
	

}

package es.zopa.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.zopa.utils.VariableUtils;

public class VariableUtilTest {
	
	@Test
	public void stringToLong_OK(){
		String amount = "1000";
		assertEquals(1000,VariableUtils.StringToNumeric(amount).longValue());
	}

	@Test
	public void stringToLong_KO(){
		String amount = "pepe";
		try{
			VariableUtils.StringToNumeric(amount);
		}catch(Exception e){
			assertEquals(e.getMessage(),"amount value (pepe) cannot tranform to Long");
		}
	}
	
	@Test 
	public void stringToFile_1_KO(){
		String file = null;
		try{
			VariableUtils.StringToFile(file);
		}catch(Exception e){
			assertEquals(e.getMessage(),"path is null");
		}
	}
	
	@Test 
	public void stringToFile_2_KO(){
		String file = " ";
		try{
			VariableUtils.StringToFile(file);
		}catch(Exception e){
			assertEquals(e.getMessage(),"path is empty");
		}
	}
	
	@Test 
	public void stringToFile_3_KO(){
		String file = ".cs";
		try{
			VariableUtils.StringToFile(file);
		}catch(Exception e){
			assertEquals(e.getMessage(),".cs name has not the correct format");
		}
	}
	
	@Test 
	public void stringToFile_4_KO(){
		String file = "txt.txt";
		try{
			VariableUtils.StringToFile(file);
		}catch(Exception e){
			assertEquals(e.getMessage(),"txt.txt name is not a CSV file");
		}
	}
	
	@Test 
	public void stringToFile_5_KO(){
		String file = "prueba.csv";
		try{
			VariableUtils.StringToFile(file);
		}catch(Exception e){
			assertEquals(e.getMessage(),"prueba.csv doesn't exist");
		}
	}
	
}

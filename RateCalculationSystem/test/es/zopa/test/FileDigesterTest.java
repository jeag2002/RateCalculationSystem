package es.zopa.test;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.zopa.model.request.AmountBean;
import es.zopa.processing.FileDigester;

import static org.junit.Assert.assertEquals;

public class FileDigesterTest {
	
	private static final double DELTA = 1e-15;
	
	@Test
	public void parserLineOK_1() throws Exception{
		
		String inputData = "pepe,0.075,640";
		AmountBean aB = FileDigester.stringToAmount(inputData);
		double rate = aB.getRate().setScale(4, RoundingMode.HALF_UP).doubleValue();
		long cash = aB.getCashAvailable().longValue();
		assertEquals("rate",0.075,rate,DELTA);
		assertEquals("cash",640,cash);
	}
	
	@Test
	public void parserLineOK_2() throws Exception{
		
		String inputData = "pepe;0.075;640";
		AmountBean aB = FileDigester.stringToAmount(inputData);
		double rate = aB.getRate().setScale(4, RoundingMode.HALF_UP).doubleValue();
		long cash = aB.getCashAvailable().longValue();
		assertEquals("rate",0.075,rate,DELTA);
		assertEquals("cash",640,cash);
	}
	
	@Test
	public void parserLineKO_1() throws Exception{
		
		String inputData = "";
		try{
		AmountBean aB = FileDigester.stringToAmount(inputData);
		}catch(Exception e){
			assertEquals("ErrMsg","Line () cannot be processed",e.getMessage());
		}

	}
	
	@Test
	public void parserLineKO_2() throws Exception{
		
		String inputData = "pepe;0.075,640";
		try{
		AmountBean aB = FileDigester.stringToAmount(inputData);
		}catch(Exception e){
			assertEquals("ErrMsg","Line (pepe;0.075,640) cannot be processed",e.getMessage());
		}

	}
	
	@Test
	public void parserLineKO_3() throws Exception{
		
		String inputData = "pepe;pepe;pepe";
		try{
		AmountBean aB = FileDigester.stringToAmount(inputData);
		}catch(Exception e){
			assertEquals("ErrMsg","For input string: \"pepe\"",e.getMessage());
		}

	}
	
	@Test
	public void parserLineKO_4() throws Exception{
		
		String inputData = "pepe;0.075;640;3000";
		try{
		AmountBean aB = FileDigester.stringToAmount(inputData);
		}catch(Exception e){
			assertEquals("ErrMsg","Line (pepe;0.075;640;3000) cannot be processed",e.getMessage());
		}
	}
	
	@Test
	public void parserLineKO_5() throws Exception{
		
		File fil = new File("");
		try{
		FileDigester.processFile(fil);
		}catch(Exception e){
			assertEquals("ErrMsg","File () doesnt exist",e.getMessage());
		}

	}
	
	@Test
	public void parserLineOrder_1() throws Exception{
		
		ArrayList<AmountBean> aBList = new ArrayList<AmountBean>();
		aBList.add(new AmountBean((new BigDecimal(0.075)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(1000)));
		aBList.add(new AmountBean((new BigDecimal(0.080)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(1000)));
		aBList.add(new AmountBean((new BigDecimal(0.070)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(1000)));
			
		List<AmountBean> aBListOrdered = FileDigester.orderAmountBeanList(aBList);
			
		assertEquals("Elem0",0.070,aBListOrdered.get(0).getRate().setScale(3, RoundingMode.HALF_UP).doubleValue(),DELTA);
		assertEquals("Elem1",0.075,aBListOrdered.get(1).getRate().setScale(3, RoundingMode.HALF_UP).doubleValue(),DELTA);
		assertEquals("Elem2",0.080,aBListOrdered.get(2).getRate().setScale(3, RoundingMode.HALF_UP).doubleValue(),DELTA);
	}
	
	

}

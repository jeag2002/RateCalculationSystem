package es.zopa.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import es.zopa.engine.FinantialEngine;
import es.zopa.model.request.AmountBean;
import es.zopa.model.response.ResponseBean;
import es.zopa.utils.Constants;


public class FinantialEngineTest {
	
	private static final double DELTA = 1e-15;
	
	//KO TEST
	///////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void FinantialEngineTest_KO_1() throws Exception{
		try{
			ArrayList<AmountBean> data = new ArrayList<AmountBean>();
			long initial_amount = 1000L;
			ResponseBean rB = FinantialEngine.processAmount(initial_amount, data);
		}catch(Exception e){
			assertEquals("ErrMsg",e.getMessage(),"Not enough data for doing calculations");
		}
		
	}
	
	@Test
	public void FinantialEngineTest_KO_2() throws Exception{
		try{
			ArrayList<AmountBean> data = new ArrayList<AmountBean>();
			data.add(new AmountBean((new BigDecimal(0.075)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(640)));
			long initial_amount = 200L;
			ResponseBean rB = FinantialEngine.processAmount(initial_amount, data);
		}catch(Exception e){
			assertEquals("ErrMsg",e.getMessage(),"Initial amount (200) must be between limits (1000) and (15000) and be divisible by (100)");
		}
	}
	
	@Test
	public void FinantialEngineTest_KO_3() throws Exception{
		try{
			ArrayList<AmountBean> data = new ArrayList<AmountBean>();
			data.add(new AmountBean((new BigDecimal(0.075)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(640)));
			long initial_amount = 1251L;
			ResponseBean rB = FinantialEngine.processAmount(initial_amount, data);
		}catch(Exception e){
			assertEquals("ErrMsg",e.getMessage(),"Initial amount (1251) must be between limits (1000) and (15000) and be divisible by (100)");
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//OK TESTS
	///////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void FinantialEngineTest_1() throws Exception{
		
		ArrayList<AmountBean> aBList = new ArrayList<AmountBean>();
		aBList.add(new AmountBean((new BigDecimal(0.075)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(640)));		
		aBList.add(new AmountBean((new BigDecimal(0.069)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(480)));
		aBList.add(new AmountBean((new BigDecimal(0.071)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(520)));
		aBList.add(new AmountBean((new BigDecimal(0.104)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(170)));
		aBList.add(new AmountBean((new BigDecimal(0.081)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(320)));
		aBList.add(new AmountBean((new BigDecimal(0.074)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(140)));
		aBList.add(new AmountBean((new BigDecimal(0.071)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(64)));
		
		List<AmountBean> arrayAB = aBList.stream().sorted((e1,e2)->e1.getRate().compareTo(e2.getRate())).collect(Collectors.toList());
		long initial_amount = 1000L;
		ResponseBean rB = FinantialEngine.processAmount(initial_amount, arrayAB);
		
		long reqAmount = rB.getReqAmount().longValue();
		double rate = rB.getRate().setScale(1, RoundingMode.HALF_UP).doubleValue();
		double monthRepayment = rB.getMonthRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		double totRepayment = rB.getTotRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		
		assertEquals("reqAmount",1000L,reqAmount);
		assertEquals("rate",7.0,rate,DELTA);
		assertEquals("monthRepayment",30.78,monthRepayment,DELTA);
		assertEquals("totRepayment",1108.10,totRepayment,DELTA);
	}
	
	
	@Test
	public void FinantialEngineTest_2() throws Exception{
		
		
		ArrayList<AmountBean> aBList = new ArrayList<AmountBean>();
		aBList.add(new AmountBean((new BigDecimal(0.075)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(640)));		
		
		try{
			List<AmountBean> arrayAB = aBList.stream().sorted((e1,e2)->e1.getRate().compareTo(e2.getRate())).collect(Collectors.toList());
			long initial_amount = 1000L;
			ResponseBean rB = FinantialEngine.processAmount(initial_amount, arrayAB);
		}catch(Exception e){
			assertEquals("Exception",e.getMessage(),"not enough data for the loan monthly amount evaluation");
		}
	}
	
	
	@Test
	public void FinantialEngineTest_3() throws Exception{
		
		ArrayList<AmountBean> aBList = new ArrayList<AmountBean>();
		aBList.add(new AmountBean((new BigDecimal(0.010)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(750)));
		aBList.add(new AmountBean((new BigDecimal(0.020)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(750)));
		List<AmountBean> arrayAB = aBList.stream().sorted((e1,e2)->e1.getRate().compareTo(e2.getRate())).collect(Collectors.toList());
		
		
		long initial_amount = 1000L;
		ResponseBean rB = FinantialEngine.processAmount(initial_amount, arrayAB);
		
		
		
		long reqAmount = rB.getReqAmount().longValue();
		double rate = rB.getRate().setScale(1, RoundingMode.HALF_UP).doubleValue();
		double monthRepayment = rB.getMonthRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		double totRepayment = rB.getTotRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		
		assertEquals("reqAmount",1000L,reqAmount);
		assertEquals("rate",1.3,rate,DELTA);
		assertEquals("monthRepayment",28.31,monthRepayment,DELTA);
		assertEquals("totRepayment",1019.28,totRepayment,DELTA);
	}
	
	@Test
	public void FinantialEngineTest_4() throws Exception{
		
		ArrayList<AmountBean> aBList = new ArrayList<AmountBean>();
		aBList.add(new AmountBean((new BigDecimal(0.080)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(1000)));
		aBList.add(new AmountBean((new BigDecimal(0.070)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(1000)));
		List<AmountBean> arrayAB = aBList.stream().sorted((e1,e2)->e1.getRate().compareTo(e2.getRate())).collect(Collectors.toList());
		
		
		long initial_amount = 1000L;
		ResponseBean rB = FinantialEngine.processAmount(initial_amount, arrayAB);
		
		
		
		long reqAmount = rB.getReqAmount().longValue();
		double rate = rB.getRate().setScale(1, RoundingMode.HALF_UP).doubleValue();
		double monthRepayment = rB.getMonthRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		double totRepayment = rB.getTotRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		
		assertEquals("reqAmount",1000L,reqAmount);
		assertEquals("rate",7.0,rate,DELTA);
		assertEquals("monthRepayment",30.78,monthRepayment,DELTA);
		assertEquals("totRepayment",1108.04,totRepayment,DELTA);
	}
	
	@Test
	public void FinantialEngineTest_5() throws Exception{
		
		ArrayList<AmountBean> aBList = new ArrayList<AmountBean>();
		aBList.add(new AmountBean((new BigDecimal(0.051)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(200)));
		aBList.add(new AmountBean((new BigDecimal(0.051)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(1000)));
		List<AmountBean> arrayAB = aBList.stream().sorted((e1,e2)->e1.getRate().compareTo(e2.getRate())).collect(Collectors.toList());
		
		
		long initial_amount = 1100L;
		ResponseBean rB = FinantialEngine.processAmount(initial_amount, arrayAB);
		
		
		
		long reqAmount = rB.getReqAmount().longValue();
		double rate = rB.getRate().setScale(1, RoundingMode.HALF_UP).doubleValue();
		double monthRepayment = rB.getMonthRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		double totRepayment = rB.getTotRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		
		assertEquals("reqAmount",1100L,reqAmount);
		assertEquals("rate",5.1,rate,DELTA);
		assertEquals("monthRepayment",32.96,monthRepayment,DELTA);
		assertEquals("totRepayment",1186.57,totRepayment,DELTA);
	}
	
	@Test
	public void FinantialEngineTest_6() throws Exception{
		
		ArrayList<AmountBean> aBList = new ArrayList<AmountBean>();
		aBList.add(new AmountBean((new BigDecimal(0.051)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(1000)));
		aBList.add(new AmountBean((new BigDecimal(0.069)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(200)));
		List<AmountBean> arrayAB = aBList.stream().sorted((e1,e2)->e1.getRate().compareTo(e2.getRate())).collect(Collectors.toList());
		
		
		long initial_amount = 1200L;
		ResponseBean rB = FinantialEngine.processAmount(initial_amount, aBList);
		
		
		
		long reqAmount = rB.getReqAmount().longValue();
		double rate = rB.getRate().setScale(1, RoundingMode.HALF_UP).doubleValue();
		double monthRepayment = rB.getMonthRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		double totRepayment = rB.getTotRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		
		assertEquals("reqAmount",1200L,reqAmount);
		assertEquals("rate",5.4,rate,DELTA);
		assertEquals("monthRepayment",36.11,monthRepayment,DELTA);
		assertEquals("totRepayment",1300.00,totRepayment,DELTA);
	}
	
	@Test
	public void FinantialEngineTest_7() throws Exception{
		
		ArrayList<AmountBean> aBList = new ArrayList<AmountBean>();
		aBList.add(new AmountBean((new BigDecimal(0.600)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(1500)));
		aBList.add(new AmountBean((new BigDecimal(0.500)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(1000)));
		aBList.add(new AmountBean((new BigDecimal(0.200)).setScale(3, RoundingMode.HALF_UP),new BigDecimal(500)));
		List<AmountBean> arrayAB = aBList.stream().sorted((e1,e2)->e1.getRate().compareTo(e2.getRate())).collect(Collectors.toList());
		
		
		long initial_amount = 1500L;
		ResponseBean rB = FinantialEngine.processAmount(initial_amount, aBList);
		
		
		
		long reqAmount = rB.getReqAmount().longValue();
		double rate = rB.getRate().setScale(1, RoundingMode.HALF_UP).doubleValue();
		double monthRepayment = rB.getMonthRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		double totRepayment = rB.getTotRepayment().setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		
		assertEquals("reqAmount",1500L,reqAmount);
		assertEquals("rate",60.0,rate,DELTA);
		assertEquals("monthRepayment",79.27,monthRepayment,DELTA);
		assertEquals("totRepayment",2853.68,totRepayment,DELTA);
	}
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////

}

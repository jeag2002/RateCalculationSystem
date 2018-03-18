package es.zopa;

import java.io.File;
import java.math.RoundingMode;
import java.util.List;

import es.zopa.engine.FinantialEngine;
import es.zopa.model.request.AmountBean;
import es.zopa.model.response.ResponseBean;
import es.zopa.processing.FileDigester;
import es.zopa.utils.Constants;
import es.zopa.utils.VariableUtils;

public class Main {
	
	
	public Main(){
		
	}
	
	/**
	 * Print Results 
	 * @param rP
	 */
	
	private void printResponse(ResponseBean rP){
		
		System.out.println("Requested amount: " + Constants.POUND_ASCII_CODE + rP.getReqAmount().setScale(0, RoundingMode.HALF_UP));
		System.out.println("Rate: " + rP.getRate().setScale(1, RoundingMode.HALF_UP) + "%");
		System.out.println("Monthly repayment: " + Constants.POUND_ASCII_CODE + rP.getMonthRepayment().setScale(2, RoundingMode.HALF_UP));
		System.out.println("Total repayment: " + Constants.POUND_ASCII_CODE + rP.getTotRepayment().setScale(2, RoundingMode.HALF_UP));
		
	}
	
	/**
	 * Do calculations
	 * @param Amount
	 * @param fil
	 * @return
	 * @throws Exception
	 */
	
	public ResponseBean processRun(long Amount, File fil) throws Exception{
		//evaluate CSV file and transform to ArrayList<AmountBean>
		List<AmountBean> aBList = FileDigester.processFile(fil);
		//get result data
		ResponseBean rP = FinantialEngine.processAmount(Amount, aBList);
		return rP;
	}
	
	/**
	 * Process input parameters; check input parameters and do calculations
	 * @param args
	 * @return
	 * @throws Exception
	 */
	
	public int run(String[] args) throws Exception{
		
		int runtime = Constants.END_KO;
		try{
			
			if (args.length < 2){
				System.out.println("Not enough input parameters");
				System.out.println("MAIN <csv input file> <amount>");
				System.out.println("example: MAIN input.csv 1000L");
			}else{
				long Amount = 0L;
				
				//evaluate Amount
				Amount = VariableUtils.StringToNumeric(args[1]);
				//evaluate CSV file
				File fil = VariableUtils.StringToFile(args[0]);
				
				//Engine 
				ResponseBean rP = processRun(Amount,fil);
				
				//Print Results
				printResponse(rP);
				
				runtime = Constants.END_OK;
			}
			
		}catch(Exception e){
			System.out.println("Exception (" + e.getMessage() + ")");
			runtime = Constants.END_KO;
		}finally{
			return runtime;
		}	
	}
	
	/**
	 * MAIN M
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args){
		int halt = 0;
		
		try{
			Main main = new Main();
			halt = main.run(args);
		}catch(Exception e){
			System.out.println("GENERAL ERROR " + e.getMessage());
			halt = -1;
		}
		System.exit(halt);
	}

	
	

}

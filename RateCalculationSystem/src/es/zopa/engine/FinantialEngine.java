package es.zopa.engine;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import es.zopa.exceptions.EmptyDataException;
import es.zopa.exceptions.NotEnoughSumException;
import es.zopa.model.request.AmountBean;
import es.zopa.model.response.ResponseBean;
import es.zopa.utils.Constants;

public class FinantialEngine {
	
		
	/**
	 * Process Amount calculations
	 * @param initial_amount
	 * @param data
	 * @return
	 */
	public static ResponseBean processAmount(Long initial_amount, List<AmountBean>data) throws ArithmeticException, EmptyDataException, NotEnoughSumException{
		
	    List<AmountBean> aBListClone = new ArrayList<AmountBean>();
	    BigDecimal initial_amount_B = new BigDecimal(initial_amount);
	    
		ResponseBean rP = new ResponseBean();
		
		if (data.size() == 0){
			throw new EmptyDataException("Not enough data for doing calculations");
		}
		
		if (evaluateInitialAmount(initial_amount)){
			throw new ArithmeticException("Initial amount ("+ initial_amount +") must be between limits (" + Constants.infAmountLimit + ") and (" + Constants.supAmountLimit + ") and be divisible by (" + Constants.incAmount + ")");
		}
		
		
		aBListClone = cloneAmountList(data,initial_amount);
		
		
		BigDecimal weight = BigDecimal.ZERO;
		weight = calculateWeight(aBListClone,initial_amount);
		
		//Calculate diff parameters
		double weightL = weight.doubleValue();
		double monthlyRateL = Math.pow(weightL + 1.0,(double)1/(double)Constants.PERIODSOFYEAR) - 1.0;
		double pvAnnuityFactorL = (1.0 - Math.pow(1.0+ monthlyRateL,(double)(Constants.NUMTOTALPERIODS * (-1.0))))/monthlyRateL;
		double monthlyRepaymentL = (double)initial_amount/pvAnnuityFactorL;
		double totalRepaymentL = monthlyRepaymentL * (double)Constants.NUMTOTALPERIODS;
		
		
		//Set parameters to output
		BigDecimal rqAmount = new BigDecimal(initial_amount);
		BigDecimal rate = new BigDecimal(weightL);
		rate = rate.multiply(new BigDecimal(100));
		BigDecimal monthyRepayment = new BigDecimal(monthlyRepaymentL);
		BigDecimal totalRepayment = new BigDecimal(totalRepaymentL);
		
		rP.setReqAmount(rqAmount);
		rP.setRate(rate);
		rP.setMonthRepayment(monthyRepayment);
		rP.setTotRepayment(totalRepayment);
		
		return rP;
	}
	
	/**
	 * Evaluate initialAmount
	 * @param initial_amount
	 * @return
	 */
	
	public static boolean evaluateInitialAmount(Long initial_amount){
		boolean res = false;
		
		if (initial_amount < Constants.infAmountLimit){
			res = true;
		}
		
		else if (initial_amount > Constants.supAmountLimit){
			res = true;
		}
		
		else if (initial_amount%Constants.incAmount != 0){
			res = true;
		}
		
		return res;
	}
	
	
	/**
	 * Calculate weight 
	 * @param data
	 * @param initial_amount
	 * @return
	 */
	
	public static BigDecimal calculateWeight(List<AmountBean> data, Long initial_amount){
		BigDecimal weight = BigDecimal.ZERO;
		BigDecimal runningTotalAmount = BigDecimal.ZERO;
		BigDecimal remainingAmount = BigDecimal.ZERO;
		BigDecimal initial_amount_B = new BigDecimal(initial_amount);
		
		for(int i=0; i<data.size(); i++){
			
			AmountBean aB = data.get(i);
			
			remainingAmount = initial_amount_B.subtract(runningTotalAmount);
			runningTotalAmount = runningTotalAmount.add(aB.getCashAvailable());
			
			if (i == data.size() -1){
				weight = weight.add(aB.getRate().multiply(remainingAmount));
			}else{
				weight = weight.add(aB.getRate().multiply(aB.getCashAvailable()));
			}
		}
		
		weight = weight.divide(initial_amount_B,10,RoundingMode.HALF_UP);
		return weight;
	}
	
	/**
	 * Filter amount values.
	 * @param data
	 * @param initial_amount
	 * @return
	 */
	
	public static List<AmountBean> cloneAmountList(List<AmountBean> data, Long initial_amount) throws NotEnoughSumException{
		Long sum = 0L;
		List<AmountBean> aBListClone = new ArrayList<AmountBean>();
		
		for(int i=0; i<data.size(); i++){
			sum += data.get(i).getCashAvailable().longValue();
			aBListClone.add(data.get(i));
			if (sum >= initial_amount){
				break;
			}
		}
		
		if (sum < initial_amount){
			throw new NotEnoughSumException("not enough data for the loan monthly amount evaluation");
		}
		
		return aBListClone;
	}
}

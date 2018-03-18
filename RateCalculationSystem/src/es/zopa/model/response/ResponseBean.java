package es.zopa.model.response;

import java.math.BigDecimal;

public class ResponseBean {
	
	private BigDecimal ReqAmount;
	private BigDecimal Rate;
	private BigDecimal MonthRepayment;
	private BigDecimal TotRepayment;
	
	public ResponseBean(){
		ReqAmount = BigDecimal.ZERO;
		Rate = BigDecimal.ZERO;
		MonthRepayment = BigDecimal.ZERO;
		TotRepayment = BigDecimal.ZERO;
	}
	
	public BigDecimal getReqAmount() {
		return ReqAmount;
	}

	public void setReqAmount(BigDecimal reqAmount) {
		ReqAmount = reqAmount;
	}

	public BigDecimal getRate() {
		return Rate;
	}

	public void setRate(BigDecimal rate) {
		Rate = rate;
	}

	public BigDecimal getMonthRepayment() {
		return MonthRepayment;
	}

	public void setMonthRepayment(BigDecimal monthRepayment) {
		MonthRepayment = monthRepayment;
	}

	public BigDecimal getTotRepayment() {
		return TotRepayment;
	}

	public void setTotRepayment(BigDecimal totRepayment) {
		TotRepayment = totRepayment;
	}
	
	
	

}

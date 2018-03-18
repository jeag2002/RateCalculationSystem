package es.zopa.model.request;

import java.math.BigDecimal;

public class AmountBean {
	
	private BigDecimal rate;
	private BigDecimal cashAvailable;
	
	public AmountBean(){
		rate = BigDecimal.ZERO;
		cashAvailable = BigDecimal.ZERO;
	}
	
	public AmountBean(BigDecimal _rate, BigDecimal _cash){
		rate = _rate;
		cashAvailable = _cash;
	}
	
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getCashAvailable() {
		return cashAvailable;
	}

	public void setCashAvailable(BigDecimal cashAvailable) {
		this.cashAvailable = cashAvailable;
	}

	
	
	

}

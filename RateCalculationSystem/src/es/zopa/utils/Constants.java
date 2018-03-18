package es.zopa.utils;

public class Constants {
	public static final int END_OK = 0;
	public static final int END_KO = -1;
	
	//File extension
	public static final String CSV = "csv";
	//File types
	public static final String text = "text";
	public static final String excel = "application/vnd.ms-excel";
	
	//Periods of year. (for Months... 12 Months, 1 year)
	public static final int PERIODSOFYEAR = 12;
	//Num total of periods (36 Periods, 36 months; 3 years)
	public static final int NUMTOTALPERIODS = 36;
	
	//amount data
	public static final long infAmountLimit = 1000;
	public static final long supAmountLimit = 15000;
	public static final long incAmount = 100;
	
	
	//UTF-8 Code of sterling pounds.
	public static final String POUND_ASCII_CODE = "\u00a3";
}

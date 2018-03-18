package es.zopa.exceptions;

/**
 * Own type of Exception. Raise when cvs line register has not the expected format
 * 
 */
public class FormatLineException extends Exception{
	 public FormatLineException() { super(); }
	 public FormatLineException(String message) { super(message); }
	 public FormatLineException(String message, Throwable cause) { super(message, cause); }
	 public FormatLineException(Throwable cause) { super(cause); }
}

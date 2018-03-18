package es.zopa.exceptions;

/**
 * Exception. Raise when there is no information 
 * @author Usuario
 *
 */
public class EmptyDataException extends Exception{
	public EmptyDataException() { super(); }
	public EmptyDataException(String message) { super(message); }
	public EmptyDataException(String message, Throwable cause) { super(message, cause); }
	public EmptyDataException(Throwable cause) { super(cause); }

}

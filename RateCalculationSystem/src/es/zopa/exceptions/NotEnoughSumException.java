package es.zopa.exceptions;

public class NotEnoughSumException extends Exception {
	 public NotEnoughSumException() { super(); }
	 public NotEnoughSumException(String message) { super(message); }
	 public NotEnoughSumException(String message, Throwable cause) { super(message, cause); }
	 public NotEnoughSumException(Throwable cause) { super(cause); }
}

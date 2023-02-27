package exception;

public class InvalidException extends Exception
{

	private static final long serialVersionUID = -4028807594647250296L;

	public InvalidException()
	{

	}

	public InvalidException(String message)
	{
		super(message);
	}

	public InvalidException(Throwable cause)
	{
		super(cause);
	}

	public InvalidException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public InvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

package util;

import exception.InvalidException;

public class NullCheck
{
	public static void nullCheck(Object obj) throws InvalidException
	{
		if(obj == null)
		{
			throw new InvalidException("Value Should not be null");
		}
	}
}

package ArgumentsCheck.java;
import Exceptions.inputFormatExceptions;

public class ArgumentsCheck {
	//private void  ArgumentsCheck() {};
	public void isInputCorrect(String[] args) throws inputFormatExceptions
	{
		if (args.length != 3)
			throw (new inputFormatExceptions("Incorrect input format"));
	}
}
 


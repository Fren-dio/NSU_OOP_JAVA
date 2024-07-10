package Exceptions;

public class CSVException  extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_errorMessage = "";

	public CSVException(String errorMessage)
	{
		m_errorMessage = errorMessage;
	}
	
	public String getMessage()
	{
		return ("ERROR: " + m_errorMessage);
	}
}

package Exceptions;

public class inputFormatExceptions extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_errorMessage = "";
	private boolean m_inCorrectFlag = false;
	
	public inputFormatExceptions(String errorMessage)
	{
		m_errorMessage = errorMessage;
		m_inCorrectFlag = true;
	}
	
	public String getMessage()
	{
		return ("ERROR: " + m_errorMessage);
	}
	
	public boolean needMenu()
	{
		return m_inCorrectFlag;
	}
}

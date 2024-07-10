package Writer.java;

import java.io.IOException;
import java.io.PrintWriter;

import Exceptions.CSVException;
import Reader.java.Reader;

public class Writer {
	private String m_fileName;
	
	public Writer(String foutName)
	{
		m_fileName = foutName;
	}
	
	
	public void writeCSVTable(Reader myReader) throws IOException, CSVException
	{
		PrintWriter myWriter = new PrintWriter(m_fileName);
		
		if (myWriter.checkError()) 
		{
			myWriter.close();
			throw (new CSVException("error with work with output CSV file"));
		}
		
		if ((myReader.getCSVMap()).length >= 2)
		for (int i = 0; i < (myReader.getCSVMap()).length-1; i++) {
			float count = (float)(myReader.getCSVMap())[i].getCount();
			float frequence = 100 * (count)/((float)(myReader.getWordsCount()));
			String freq = (String.format("%.2f", frequence)).replace(',', '.');
			myWriter.println((myReader.getCSVMap())[i].getWord() + "," + (myReader.getCSVMap())[i].getCount() + "," + freq + "%");
		}
		
		myWriter.close();
	}
}
	
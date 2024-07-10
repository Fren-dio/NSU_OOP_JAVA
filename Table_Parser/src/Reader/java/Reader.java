
package Reader.java;
import java.io.IOException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;
import Exceptions.CSVException;



public class Reader {
	
	private String fileName;
	private int wordInTextCount = 0;
	private int wordInDictionary = 0;
	private wordData[] CSVMap = new wordData[1];
	
	public class wordData
	{
		private int count;
		private String word;
		
		public wordData()
		{
			count = 1;
			word = "";
		}
		
		public wordData(String newWord, int newCnt)
		{
			count = newCnt;
			word = newWord;
		}
		
		public void increaseCount()
		{
			count++;
		}
		
		public String getWord()
		{
			return word;
		}

		public int getCount()
		{
			return count;
		}
	}

	public class sortByCount implements Comparator<wordData> {
	    public int compare(wordData a, wordData b)
	    {
	        return b.count - a.count;
	    }
	}
	
	public int getWordsCount()
	{
		return wordInTextCount;
	}
	
		
	boolean containStr(wordData[] _CSVMap, String word)
	{
		for (int i = 0; i < _CSVMap.length-1; i++)
			if ((_CSVMap[i].getWord()).equalsIgnoreCase(word))
				return true;
		
		return false;
	}
	

    static wordData[] getNewArr(wordData [] oldArr) {
    	wordData[] newArr = new wordData[oldArr.length+1];
        for (int i = 0; i < oldArr.length; i++)
            newArr[i] = oldArr[i];
        return newArr;
    }
    
    
    static int getID(wordData[] arr, String elem) {
    	int ID = 0;
        for (int i = 0; i < arr.length; i++)
            if ((arr[i].getWord()).equalsIgnoreCase(elem)) {
            	ID = i;
            	return i;
            }
        return ID;
    }
    
	
	public Reader(String newFileName) 
	{
		fileName = newFileName;
	};
	
	public wordData[] getCSVMap()
	{
		return CSVMap;
	}
	
	public void readFile() throws CSVException, IOException
	{
		FileReader fin = new FileReader(fileName);
		if ((!fin.ready()) && (fin.read() != -1))
		{
			fin.close();	
			throw (new CSVException("Input file not exist"));
		}
		
		Scanner myParser = new Scanner (fin);
		
		String currentStr;
		while (myParser.hasNextLine())
		{
			currentStr = myParser.nextLine();
			String[] splittedStr = currentStr.split("[ ,.!?\"\\n«»]");
			for (String i: splittedStr)
			{
				if (i.length() > 0)
				{
					wordInTextCount++;
					if (CSVMap.length == 1)
					{
						wordData currentKey = new wordData(i, 1);
						CSVMap[0] = currentKey;
						CSVMap = getNewArr(CSVMap);
					}
					else
					{
						if (!containStr(CSVMap, i))
						{
							wordInDictionary++;
							if (wordInDictionary+1 > CSVMap.length)
								CSVMap = getNewArr(CSVMap);
							wordData currentKey = new wordData(i, 1);
							CSVMap[wordInDictionary] = currentKey;
						}
						else
						{
							int wordID = getID(CSVMap, i);
							wordData currentKey = CSVMap[wordID];
							currentKey.increaseCount();
							CSVMap[wordID] = currentKey;
						}
					}
				}
			}
		}
		
		if (CSVMap.length > 2)
			Arrays.sort(CSVMap, new sortByCount());
		
		myParser.close();
		fin.close();
	}
	
	public int getCountWorsInText()
	{
		return wordInTextCount;
	}
}

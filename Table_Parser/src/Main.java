
import Reader.java.Reader;
import Writer.java.Writer;

import java.io.IOException;

import ArgumentsCheck.java.*;
import Exceptions.*;

public class Main {

    public static void getMenu()
    {
        System.out.println("---Be careful---");
        System.out.println("The correct format for run this program is:");
        System.out.println("./prog.exe inputFile.txt outputFile.csv");
    }

    public static void main(String[] args) {
        try
        {
            ArgumentsCheck tester = new ArgumentsCheck();
            tester.isInputCorrect(args);
            Reader myReader = new Reader(args[1]);
            myReader.readFile();
            Writer myWriter = new Writer(args[2]);
            myWriter.writeCSVTable(myReader);
        }
        catch (inputFormatExceptions ex)
        {
            System.out.println(ex.getMessage());
            if (ex.needMenu())
                getMenu();
            return;
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
            return;
        }


        System.out.println("Program was completed.");
    }

}

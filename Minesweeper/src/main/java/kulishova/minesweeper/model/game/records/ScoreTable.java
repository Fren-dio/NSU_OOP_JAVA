package kulishova.minesweeper.model.game.records;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreTable
{
    public static ArrayList<ScoreData> getRecords()
    {
        ArrayList<ScoreData> records = new ArrayList<>();
        try(Scanner scan = new Scanner(new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Records.txt")))
        {
            while(scan.hasNext())
                records.add(new ScoreData(scan.nextLine()));
        }catch(Throwable ignored){}
        if(records.isEmpty())
            return null;
        else
            return records;
    }

    public static void saveRecord(ScoreData pair)
    {
        ArrayList<ScoreData> recs = getRecords();
        if(recs == null)
        {
            recs = new ArrayList<>();
            recs.add(pair);
        }else
            for(int i = 0; i < recs.size(); i++)
                if(!recs.get(i).better(pair.getTime()))
                {
                    recs.add(i, pair);
                    break;
                }

        if(!recs.contains(pair))
            recs.add(pair);

        writeAll(recs);
    }

    public static void removeRecords()
    {
        try
        {
            Files.delete(Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Records.txt"));
        }catch(IOException ignored){}
    }

    private static void writeAll(ArrayList<ScoreData> records)
    {
        try(PrintStream scan = new PrintStream(new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Records.txt")))
        {
            for(var p : records)
                scan.println(p.getName() + ";" + p.getTime());
        }catch(Throwable ignored){}
    }
}
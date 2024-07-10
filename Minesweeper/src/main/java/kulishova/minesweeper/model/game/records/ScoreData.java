package kulishova.minesweeper.model.game.records;

public class ScoreData
{
    private final String name;
    private final String time;

    public ScoreData(String data)
    {
        String[] arr = data.split(";");
        name = arr[0];
        time = arr[1];
    }

    public String getName()
    {
        return name;
    }

    public String getTime()
    {
        return time;
    }

    public boolean better(String time)
    {
        String[] timesOur = this.time.split(":");
        String[] timesAnother = time.split(":");
        if(Integer.parseInt(timesOur[0]) > Integer.parseInt(timesAnother[0]))
            return false;
        else if(Integer.parseInt(timesOur[0]) < Integer.parseInt(timesAnother[0]))
            return true;
        else
        {
            if(Integer.parseInt(timesOur[1]) > Integer.parseInt(timesAnother[1]))
                return false;
            else if(Integer.parseInt(timesOur[1]) < Integer.parseInt(timesAnother[1]))
                return true;
            else
            {
                if(Integer.parseInt(timesOur[2]) > Integer.parseInt(timesAnother[2]))
                    return false;
                else
                    return Integer.parseInt(timesOur[2]) < Integer.parseInt(timesAnother[2]);
            }
        }
    }
}
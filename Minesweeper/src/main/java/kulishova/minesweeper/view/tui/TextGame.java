package kulishova.minesweeper.view.tui;

import kulishova.minesweeper.model.game.Minesweeper;
import kulishova.minesweeper.model.game.records.ScoreData;
import kulishova.minesweeper.model.game.records.ScoreTable;
import kulishova.minesweeper.model.time.Timer;
import kulishova.minesweeper.controller.GameViewController;
import kulishova.minesweeper.model.game.other.Coordinates;
import kulishova.minesweeper.model.game.other.IOStream;
import kulishova.minesweeper.model.game.other.StringMethods;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static kulishova.minesweeper.model.game.other.IOStream.println;
import static java.lang.Integer.parseInt;

public class TextGame implements GameViewController
{
    private static final int FREE_CELL = 0;
    private static final int NOT_OPENED_CELL = -1;
    private static final int MARKED_CELL = -2;
    private static final int MINED_CELL = -4;
    private Console console;
    private Scanner scanner;

    private int[][] map;
    private boolean gameOverFlag = false;
    private boolean gameWinFlag = false;
    private boolean stopFlag = false;
    private boolean useConsole = true;
    private TextUserMenu textMenu = new TextUserMenu(0);

    public void init(boolean main)
    {
        map = new int[Minesweeper.getFieldSize().width][Minesweeper.getFieldSize().height];

        for(var arr : map)
            Arrays.fill(arr, NOT_OPENED_CELL);

        display(true);
        if(!main)
            return;

        if((console = System.console()) == null)
        {
            useConsole = false;
            scanner = new Scanner(System.in);
        }

        String line;
        do
        {
            if(useConsole)
                line = console.readLine().trim();
            else
                line = scanner.nextLine().trim();

            if(line.split(" ").length > 3)
            {
                IOStream.println("Unknown command: " + line);
                if(Minesweeper.isMainController(this))
                    IOStream.print("> ");
                continue;
            }

            String[] lineArr = line.split(" ");

            if(lineArr.length == 3 && StringMethods.isNumeric(lineArr[1]) && StringMethods.isNumeric(lineArr[2]) && !StringMethods.isNumeric(lineArr[0]))
            {
                if(!Coordinates.isAvailable(parseInt(lineArr[1]), parseInt(lineArr[2])))
                {
                    IOStream.println("Wrong coordinates!");
                    if(Minesweeper.isMainController(this))
                        IOStream.print("> ");
                    continue;
                }
                if(lineArr[0].length() != 1 || !"mo".contains(lineArr[0]))
                {
                    IOStream.println("Wrong mode!");
                    if(Minesweeper.isMainController(this))
                        IOStream.print("> ");
                    continue;
                }

                if(!Minesweeper.isGameStarted())
                {
                    Minesweeper.initField(parseInt(lineArr[1]), parseInt(lineArr[2]));
                    Minesweeper.startGame();
                    Timer.start(Minesweeper::repaintControllersTimer);
                }
                if(Minesweeper.isGameEnded())
                {
                    if(Minesweeper.isMainController(this))
                        IOStream.print("> ");
                    continue;
                }
                switch(lineArr[0])
                {
                    case "o" -> Minesweeper.openCell(parseInt(lineArr[1]), parseInt(lineArr[2]));
                    case "m" -> Minesweeper.markCell(parseInt(lineArr[1]), parseInt(lineArr[2]));
                }
                continue;
            }

            switch(line)
            {
                case "help", "?" -> {
                    IOStream.println("""
                               o <x> <y> - open cell with this coordinates
                               m <x> <y> - mark/mark for sure/unmark cell with this coordinates
                               pause     - pause a timer of game
                               replay    - replay game with same field size
                               new       - play a new game
                               menu      - back to start menu
                               exit      - exit from game
                            """);
                    IOStream.print("> ");
                }
                case "pause" -> {
                    if(!Minesweeper.isGameStarted())
                    {
                        if(Minesweeper.isMainController(this))
                            IOStream.print("> ");
                        continue;
                    }
                    Minesweeper.pauseControllers();
                    setPause();
                }
                case "replay" -> {
                    if(!Minesweeper.isGameEnded() && !Minesweeper.isGameStarted())
                    {
                        if(Minesweeper.isMainController(this))
                            IOStream.print("> ");
                        continue;
                    }
                    Minesweeper.restartControllers();
                }
                case "menu" -> {
                    textMenu.backToTextMenu();
                }
                case "new" -> Minesweeper.rebuildControllers();

                case "exit" -> System.exit(0);
                default -> {
                    if(gameWinFlag || gameOverFlag || line.isEmpty())
                    {
                        if(Minesweeper.isMainController(this))
                            IOStream.print("> ");
                        continue;
                    }
                    IOStream.println("Unknown command: " + line);
                    if(Minesweeper.isMainController(this))
                        IOStream.print("> ");
                }
            }
        } while(true);
    }

    private void display(boolean getInvite)
    {
        IOStream.println(StringMethods.getTimeString(Timer.getSeconds() / 3600) + ":" + StringMethods.getTimeString((Timer.getSeconds() % 3600) / 60) + ":" + StringMethods.getTimeString(Timer.getSeconds() % 60));
        IOStream.println(Minesweeper.getFlagCount() + "/" + Minesweeper.getMaxFlagCount());

        if(stopFlag)
        {
            IOStream.println("If you want to continue, write 'pause'");
            if(Minesweeper.isMainController(this))
                IOStream.print("> ");
            return;
        }

        IOStream.print("  ");
        for(int i = 0; i < map.length; i++)
            IOStream.print(StringMethods.getNumericString(i));
        IOStream.println();

        for(int x = 0; x < map.length; x++)
        {
            IOStream.print(StringMethods.getNumericString(x));
            for(int y = 0; y < map[x].length; y++)
                switch(map[y][x])
                {
                    case MINED_CELL -> IOStream.print(" *");
                    case NOT_OPENED_CELL -> IOStream.print(" ?");
                    case MARKED_CELL -> IOStream.print(" F");
                    case FREE_CELL -> IOStream.print("  ");
                    case 1, 2, 3, 4, 5, 6, 7, 8 -> IOStream.print(" " + map[y][x]);
                    default -> throw new IllegalArgumentException("Wrong map encoding: " + map[y][x]);
                }
            IOStream.println();
        }
        if(gameOverFlag)
            IOStream.println("YOU LOSE....");
        else if(gameWinFlag)
            IOStream.println("YOU WIN!!!");

        if(Minesweeper.isMainController(this) && getInvite)
            IOStream.print("> ");
    }

    @Override
    public void markCell(int x, int y)
    {
        map[x][y] = MARKED_CELL;
        display(true);
    }

    @Override
    public void markMaybeCell(int x, int y)
    {
        display(true);
    }

    @Override
    public void offMarkOnCell(int x, int y)
    {
        map[x][y] = NOT_OPENED_CELL;
    }

    @Override
    public void freeCell(int x, int y)
    {
        map[x][y] = FREE_CELL;
    }

    @Override
    public void setPause()
    {
        if(Minesweeper.isMainController(this))
        {
            if(Timer.isRunning())
                Timer.stop();
            else
                Timer.on();
        }
        stopFlag = !stopFlag;

        display(true);
    }

    @Override
    public void setNumCell(int x, int y, int num)
    {
        map[x][y] = (byte) num;
    }

    @Override
    public void repaintTimer() {}

    @Override
    public void repaintFlag() {}

    @Override
    public void disableGame(byte[][] map)
    {
        if(Minesweeper.isMainController(this))
            Timer.stop();
        for(int x = 0; x < map.length; x++)
            for(int y = 0; y < map[x].length; y++)
                if(Minesweeper.isMined(map[x][y]))
                    this.map[x][y] = MINED_CELL;
    }

    @Override
    public boolean restartGame()
    {
        gameWinFlag = false;
        gameOverFlag = false;
        stopFlag = false;
        for(var arr : map)
            Arrays.fill(arr, (byte) NOT_OPENED_CELL);
        if(Minesweeper.isMainController(this))
        {
            Timer.stop();
            Minesweeper.newGame();
        }
        display(true);
        return true;
    }
    private void getInfoAboutLevels()
    {
        IOStream.println("Okey, here is the basic information about the available levels:");
        IOStream.println("level x8: here you have:\n" +
                "weight of field: 8\n" +
                "height of field: 8\n" +
                "count of mines: 5\n");
        IOStream.println("level x9: here you have:\n" +
                "weight of field: 9\n" +
                "height of field: 9\n" +
                "count of mines: 10\n");
        IOStream.println("level x16: here you have:\n" +
                "weight of field: 16\n" +
                "height of field: 16\n" +
                "count of mines: 30\n");
    }

    @Override
    public boolean rebuildField()
    {
        if(Minesweeper.isMainController(this))
        {
            Timer.stop();
            IOStream.println("Select size of games field: x8, x9 or x16");
            IOStream.println("or write 'getinfo' if you want to know some information about difficulty level.");
            IOStream.println("or write 'menu' if you want back to start menu");
            String line;
            Minesweeper.FieldVariant dim = Minesweeper.FieldVariant.x8;

            boolean levelChosen = false;
            menu:
            do
            {
                if(Minesweeper.isMainController(this))
                    IOStream.print("> ");

                if(useConsole)
                    line = console.readLine().trim();
                else
                    line = scanner.nextLine().trim();

                if(line.split(" ").length > 1)
                {
                    IOStream.println("Unknown size: " + line);
                    continue;
                }

                switch(line)
                {
                    case "getinfo" -> {
                        getInfoAboutLevels();
                        break;
                    }
                    case "x8" -> {
                        dim = Minesweeper.FieldVariant.x8;
                        levelChosen = true;
                        break menu;
                    }
                    case "x9" -> {
                        dim = Minesweeper.FieldVariant.x9;
                        levelChosen = true;
                        break menu;
                    }
                    case "x16" -> {
                        dim = Minesweeper.FieldVariant.x16;
                        break menu;
                    }
                    case "menu" -> {
                        textMenu.backToTextMenu();
                    }
                    case "exit" -> System.exit(0);
                    default -> IOStream.println("Unknown size: " + line);
                }
            } while(!levelChosen);

            Minesweeper.newGame(dim);
        }
        map = new int[Minesweeper.getFieldSize().width][Minesweeper.getFieldSize().height];
        for(var arr : map)
            Arrays.fill(arr, NOT_OPENED_CELL);

        gameOverFlag = false;
        gameWinFlag = false;
        stopFlag = false;

        display(true);

        return true;
    }

    @Override
    public void noticeOverGame()
    {
        gameOverFlag = true;
        gameWinFlag = false;

        display(false);
        IOStream.println("""
                    Well, now you can enter:
                    replay - replay game on same difficulty level
                    menu   - back to start menu
                    new    - play a new game
                    exit   - exit from game
                    """);
        IOStream.print("> ");
    }

    @Override
    public void noticeWinGame()
    {
        gameWinFlag = true;
        gameOverFlag = false;

        display(false);

        IOStream.println("Enter your name\n");
        String name;
        do
        {
            IOStream.print("> ");
            if(useConsole)
                name = console.readLine().trim();
            else
                name = scanner.nextLine().trim();
            if (name.contains(";"))
                IOStream.println("Please, don't use ';'");
            if (name.length() > 10)
                IOStream.println("Maximum lenght - 10 symbols:");
        }while(name.length() > 10 && name.contains(";"));

        if(name.isEmpty())
        {
            name = (System.getProperty("user.name") == null ? "user" : System.getProperty("user.name").isEmpty() ? "user" : System.getProperty("user.name"));
            IOStream.println("Your name: '" + name + "' was added to scoretable\n");
        }

        ScoreData pair = new ScoreData(name + ";" + StringMethods.getTimeString(Timer.getSeconds() / 3600) + ":" +
                StringMethods.getTimeString((Timer.getSeconds() % 3600) / 60) + ":" +
                StringMethods.getTimeString(Timer.getSeconds() % 60));
        ScoreTable.saveRecord(pair);

        IOStream.println("Score table:");
        ArrayList<ScoreData> pairs = ScoreTable.getRecords();
        if(pairs == null)
        {
            IOStream.println("Score table is empty\n\n");
        }else
            for(int i = 0; i < pairs.size();i++)
                IOStream.println((i + 1) + ") " + pairs.get(i).getName() + ": " + pairs.get(i).getTime());

        IOStream.println();
        IOStream.println();
        IOStream.println("""
                    Well, now you can enter:
                    replay - replay game with same field size
                    menu      - back to start menu
                    new    - play a new game
                    exit   - exit from game
                    """);
    }

    @Override
    public boolean isGUI()
    {
        return false;
    }

    @Override
    public void update(boolean makeOnlyOutSymbol)
    {
        if(makeOnlyOutSymbol || gameOverFlag || gameWinFlag)
            IOStream.print("> ");
        else
            display(true);
    }

}
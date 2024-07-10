package kulishova.minesweeper.view.tui;

import kulishova.minesweeper.model.game.records.ScoreData;
import kulishova.minesweeper.model.game.records.ScoreTable;
import kulishova.minesweeper.model.game.other.IOStream;
import kulishova.minesweeper.model.game.Minesweeper;

import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

import static kulishova.minesweeper.model.game.other.IOStream.println;

public class TextUserMenu
{
    private boolean useConsole = true;
    private Console console;
    private Scanner scanner;

    public void getTextMenu()
    {
        IOStream.println();
        IOStream.println("Menu. Choose, what do you want do do:");
        IOStream.println("""
                        play   - play a new game
                        scores - get scoreboard
                        rules  - some info about game rules
                        about  - look about this game
                        exit   - exit from game
                    """);
    }

    public TextUserMenu()
    {
        getTextMenu();
        runMenu();
    }
    public TextUserMenu(int a)
    {
    }

    public void backToTextMenu()
    {
        getTextMenu();
        runMenu();
    }

    private void runMenu()
    {
        if((console = System.console()) == null)
        {
            useConsole = false;
            scanner = new Scanner(System.in);
        }

        String line;
        menu: do
        {
            IOStream.print("> ");
            if(useConsole)
                line = console.readLine().trim();
            else
                line = scanner.nextLine().trim();

            if (line.equals("play"))
            {
                playGame();
                break menu;
            }
            else if (line.equals("scores"))
                getScores();
            else if (line.equals("rules"))
                getRules();
            else if (line.equals("about"))
                getAbout();
            else if (line.equals("help"))
                getTextMenu();
            else if (line.equals("exit"))
                exit();
            else
            {
                IOStream.println("Unknown command: " + line);
                IOStream.println("try next command: 'help'");
            }
        }while(true);
    }

    private void getInfoAboutLevels()
    {
        IOStream.println("Okey, here is the basic information about the available levels:");
        IOStream.println("level x8: here you have:\n" +
                "weight of field: 8\n" +
                "height of field: 8\n" +
                "count of mines: 1\n");
        IOStream.println("level x9: here you have:\n" +
                "weight of field: 9\n" +
                "height of field: 9\n" +
                "count of mines: 10\n");
        IOStream.println("level x16: here you have:\n" +
                "weight of field: 16\n" +
                "height of field: 16\n" +
                "count of mines: 30\n");
    }

    private void playGame()
    {
        IOStream.println("Select size of games field: x8, x9 or x16");
        IOStream.println("or write 'getinfo' if you want to know some information about difficulty level.");
        IOStream.println("or write 'menu' if you want back to start menu");
        String line;
        Minesweeper.FieldVariant dim = Minesweeper.FieldVariant.x8;

        boolean levelChosen = false;
        menu: do
        {
            IOStream.print("> ");
            if(useConsole)
                line = console.readLine().trim();
            else
                line = scanner.nextLine().trim();

            if(line.split(" ").length == 0)
                continue;
            if(line.split(" ").length > 1)
            {
                IOStream.println("Unknown size of game: " + line);
                getInfoAboutLevels();
                continue;
            }

            switch(line)
            {
                case "getinfo" -> {
                    getInfoAboutLevels();
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
                    levelChosen = true;
                    break menu;
                }
                case "menu" -> {
                    backToTextMenu();
                }
                case "exit" -> exit();
                default -> {
                    IOStream.println("Unknown size of field: " + line);
                }
            }
        }while(!levelChosen);

        Minesweeper.newGame(dim);

        TextGame tg = new TextGame();
        Minesweeper.setMainController(tg);
        tg.init(true);
    }

    private void getScores()
    {
        ArrayList<ScoreData> pairs = ScoreTable.getRecords();
        if(pairs == null)
        {
            IOStream.println("Records list is empty!");
        }else
            for(var p : pairs)
                IOStream.println(p.getName() + ": " + p.getTime());
    }

    private void getRules()
    {
        IOStream.println("The playing field is divided into adjacent cells, some of which are “mined.” The number of “mined” cells is known.\n" +
                "\n" +
                "The goal of the game is to open all cells that do not contain mines. The player opens the cells, being careful not to open a cell with a mine. Having opened a cell with a mine, he loses.\n" +
                "\n" +
                "Mines are placed after the first move. If there is no mine under an open cell, then a number appears in it showing how many cells adjacent to the one just opened are “mined.” Using these numbers, the player tries to calculate the location of the mines.\n" +
                "\n" +
                "By opening all the “unmined” cells, the player wins.");
    }

    private void getAbout()
    {
        IOStream.println(String.format("It is a classical minesweeper\n Goal: you need to find all free spaces"));
    }

    private void exit()
    {
        System.exit(0);
    }
}
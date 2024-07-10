package kulishova.minesweeper;

import kulishova.minesweeper.model.game.Minesweeper;

public class Main
{
    private static void startFormatMessage()
    {
        System.out.println("You have 2 variant to use this game: text(console) or 2d format.\n");
        System.out.println("if you want graphic interface, write: 'minesweeper.java graphic' \n");
        System.out.println("if you want text interface, write: 'minesweeper.java text' \n");
    }

    public static void main(String[] args)
    {
        boolean mainGUI = false;
        if (args.length == 1)
        {
            if (args[0].equals("graphic"))
                mainGUI = true;
            else if (args[0].equals("text"))
                mainGUI = false;
            else if (args[0].equals("help")) {
                startFormatMessage();
                return;
            }
            else {
                System.out.println("Unknown command: " + args[0]);
                System.out.println("try 'help' command...");
                return;
            }
        }
        else
        {
            System.out.println("Incorrect input format.\n");
            System.out.println("try 'help' command...");
            return;
        }
        Minesweeper.showMenu(mainGUI);
    }
}
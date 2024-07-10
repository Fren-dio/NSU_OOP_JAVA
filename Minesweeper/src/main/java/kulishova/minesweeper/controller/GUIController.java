package kulishova.minesweeper.controller;

import kulishova.minesweeper.model.game.Minesweeper;

import javax.swing.*;

public class GUIController
{
    public static void invokeController(GameViewController controller, Runnable runnable)
    {
        try
        {
            if(controller == null || controller.isGUI())
            {
                if(!Minesweeper.isMainControllerIsGUI())
                    SwingUtilities.invokeAndWait(runnable);
                else
                    runnable.run();
            }else
                runnable.run();
        } catch(Throwable ignored) {}
    }
}
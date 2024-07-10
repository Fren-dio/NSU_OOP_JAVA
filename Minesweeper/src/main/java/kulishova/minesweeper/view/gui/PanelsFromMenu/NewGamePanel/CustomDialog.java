package kulishova.minesweeper.view.gui.PanelsFromMenu.NewGamePanel;

import kulishova.minesweeper.model.game.Minesweeper;

import javax.swing.*;
import java.awt.*;

public class CustomDialog
{
    private static final String[] dimensions =
            {
                    "8x8",
                    "9x9",
                    "16x16",
            };

    public static Minesweeper.FieldVariant getDimension(Component frame)
    {
        String result = (String) JOptionPane.showInputDialog(
                frame,
                "Choose your difficulty level (or look 'about', if you don't know):",
                "Message",
                JOptionPane.QUESTION_MESSAGE,
                null, dimensions, dimensions[0]);

        if(result == null)
            return Minesweeper.FieldVariant.nothing;

        switch(result)
        {
            case "8x8":
                return Minesweeper.FieldVariant.x8;
            case "9x9":
                return Minesweeper.FieldVariant.x9;
            case "16x16":
                return Minesweeper.FieldVariant.x16;
        }
        return Minesweeper.FieldVariant.nothing;
    }
}
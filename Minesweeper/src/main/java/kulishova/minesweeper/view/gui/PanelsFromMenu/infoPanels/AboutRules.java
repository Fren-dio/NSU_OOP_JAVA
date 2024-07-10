package kulishova.minesweeper.view.gui.PanelsFromMenu.infoPanels;

import kulishova.minesweeper.view.gui.FontAndColors.FontAndColors;

import javax.swing.*;
import java.awt.*;

import static kulishova.minesweeper.view.gui.FontAndColors.FontAndColors.DEFAULT_BACKGROUND;

public class AboutRules
{



    public static void showRules()
    {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(FontAndColors.DEFAULT_BACKGROUND);

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel label1 = new JLabel("Game rules:");
        label1.setFont(FontAndColors.getFont(18, label1.getFont(), false));
        label1.setForeground(Color.LIGHT_GRAY);
        content.add(label1, gbc);

        JLabel label2 = new JLabel("1) The playing field is divided into adjacent cells, some of which are 'mined'. The number of 'mined' cells is known.");
        label2.setFont(FontAndColors.getFont(18, label2.getFont(), false));
        label2.setForeground(Color.LIGHT_GRAY);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        content.add(label2, gbc);

        JLabel label3 = new JLabel("<html>2) The goal of the game is to open all cells that do not contain mines. The player opens the cells, being careful" +
                "<br />not to open a cell with a mine. Having opened a cell with a mine, he loses.</html>");
        label3.setFont(FontAndColors.getFont(18, label3.getFont(), false));
        label3.setForeground(Color.LIGHT_GRAY);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        content.add(label3, gbc);

        JLabel label4 = new JLabel("<html>3) Mines are placed after the first move. If there is no mine under an open cell, then a number appears in" +
                                        "<br />it showing how many cells adjacent to the one just opened are 'mined'. " +
                                        "<br />Using these numbers, the player tries to calculate the location of the mines.</html>");
        label4.setFont(FontAndColors.getFont(18, label4.getFont(), false));
        label4.setForeground(Color.LIGHT_GRAY);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        content.add(label4, gbc);

        JLabel label5 = new JLabel("<html>SUMMARY: By opening all the 'unmined' cells, the player wins.</html>");
        label5.setFont(FontAndColors.getFont(18, label5.getFont(), false));
        label5.setForeground(Color.LIGHT_GRAY);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        content.add(label5, gbc);

        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 0.01;
        spacer1.setBackground(DEFAULT_BACKGROUND);
        content.add(spacer1, gbc);

        JFrame frame = new JFrame();
        frame.setContentPane(content);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Game rules");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
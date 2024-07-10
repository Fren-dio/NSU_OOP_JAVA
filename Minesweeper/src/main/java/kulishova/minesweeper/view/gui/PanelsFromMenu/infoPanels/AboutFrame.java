package kulishova.minesweeper.view.gui.PanelsFromMenu.infoPanels;

import kulishova.minesweeper.view.gui.FontAndColors.FontAndColors;

import javax.swing.*;
import java.awt.*;


//панель about
public class AboutFrame
{

    public static void showAbout()
    {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(FontAndColors.DEFAULT_BACKGROUND);

        JLabel label1 = new JLabel("It is a classical minesweeper");
        label1.setFont(FontAndColors.getFont(18, label1.getFont(), false));
        label1.setForeground(Color.LIGHT_GRAY);
        content.add(label1);

        JLabel label2 = new JLabel("Goal: you need to find all free spaces.");
        label2.setFont(FontAndColors.getFont(18, label2.getFont(), false));
        label2.setForeground(Color.LIGHT_GRAY);
        content.add(label2);

        JLabel levels1 = new JLabel("<html><br />Game has 3 difference levels:</html>");
        levels1.setFont(FontAndColors.getFont(18, levels1.getFont(), false));
        levels1.setForeground(Color.LIGHT_GRAY);
        content.add(levels1);

        JLabel levels2 = new JLabel("<html>EASY - field 8x8 and 5 mines</html>");
        levels2.setFont(FontAndColors.getFont(18, levels2.getFont(), false));
        levels2.setForeground(Color.LIGHT_GRAY);
        content.add(levels2);

        JLabel levels3 = new JLabel("<html>NORMAL - field 9x9 and 10 mines</html>");
        levels3.setFont(FontAndColors.getFont(18, levels3.getFont(), false));
        levels3.setForeground(Color.LIGHT_GRAY);
        content.add(levels3);

        JLabel levels4 = new JLabel("<html>HARD - field 16x16 and 30 mines</html>");
        levels4.setFont(FontAndColors.getFont(18, levels4.getFont(), false));
        levels4.setForeground(Color.LIGHT_GRAY);
        content.add(levels4);

        JLabel label3 = new JLabel("<html><br />If you more detail information look 'Rules',<br />        which is in the main menu</html>");
        label3.setFont(FontAndColors.getFont(18, label3.getFont(), false));
        label3.setForeground(Color.LIGHT_GRAY);
        content.add(label3);

        JFrame frame = new JFrame();
        frame.setSize(
                350,
                280
        );
        frame.setContentPane(content);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("About game");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
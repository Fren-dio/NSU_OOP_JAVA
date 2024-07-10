package kulishova.minesweeper.view.gui.PanelsFromMenu.infoPanels;

import kulishova.minesweeper.model.game.records.ScoreData;
import kulishova.minesweeper.model.game.records.ScoreTable;
import kulishova.minesweeper.view.gui.PanelsFromMenu.NewGamePanel.CustomButton;
import kulishova.minesweeper.view.gui.FontAndColors.FontAndColors;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScoresFrame
{
    private static JTextArea textArea = null;

    public static void showRecords(JFrame parent)
    {
        ArrayList<ScoreData> pairs = ScoreTable.getRecords();
        if(pairs == null)
        {
            JOptionPane.showMessageDialog(parent, "Score table is empty!");
            return;
        }

        JPanel recPanel = new JPanel();
        recPanel.setLayout(new BorderLayout());
        recPanel.setBackground(FontAndColors.DEFAULT_BACKGROUND);

        JPanel recHPanel = new JPanel();
        recHPanel.setLayout(new BorderLayout());
        recHPanel.setBackground(FontAndColors.DEFAULT_BACKGROUND);
        recHPanel.setMinimumSize(new Dimension(250, 40));
        recHPanel.setPreferredSize(new Dimension(250, 40));

        JLabel recL = new JLabel("Records list:");
        recL.setFont(FontAndColors.getFont(20, recL.getFont(), false));
        recL.setForeground(Color.LIGHT_GRAY);
        recHPanel.add(recL, BorderLayout.WEST);

        CustomButton removeRecords = new CustomButton("Clear table");
        removeRecords.setFont(FontAndColors.getFont(12, removeRecords.getFont(), false));
        removeRecords.addActionListener(e -> {
            ScoreTable.removeRecords();
            textArea.setText("");
        });
        removeRecords.setAlignmentX(JButton.EAST);
        recHPanel.add(removeRecords, BorderLayout.EAST);

        recPanel.add(recHPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setCaretPosition(0);
        textArea.setEditable(false);
        textArea.setBackground(FontAndColors.DEFAULT_BACKGROUND);
        textArea.setForeground(Color.LIGHT_GRAY);

        for(var p : pairs)
            textArea.setText(textArea.getText() + p.getName() + ": " + p.getTime() + "\n");

        JScrollPane scrollPane = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(250, 300));
        recPanel.add(scrollPane, BorderLayout.CENTER);
        recPanel.setBackground(FontAndColors.DEFAULT_BACKGROUND);
        recPanel.setForeground(Color.LIGHT_GRAY);

        JFrame frame = new JFrame();
        frame.setContentPane(recPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Score table");
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
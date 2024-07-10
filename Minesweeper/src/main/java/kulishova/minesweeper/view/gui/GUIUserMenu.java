package kulishova.minesweeper.view.gui;

import kulishova.minesweeper.model.game.Minesweeper;
import kulishova.minesweeper.view.gui.PanelsFromMenu.infoPanels.AboutFrame;
import kulishova.minesweeper.view.gui.PanelsFromMenu.infoPanels.ScoresFrame;
import kulishova.minesweeper.view.gui.PanelsFromMenu.NewGamePanel.CustomButton;
import kulishova.minesweeper.view.gui.FontAndColors.FontAndColors;
import kulishova.minesweeper.view.gui.PanelsFromMenu.infoPanels.AboutRules;

import javax.swing.*;
import java.awt.*;

import static kulishova.minesweeper.view.gui.PanelsFromMenu.NewGamePanel.CustomDialog.getDimension;
import static kulishova.minesweeper.view.gui.FontAndColors.FontAndColors.DEFAULT_BACKGROUND;

public class GUIUserMenu extends JFrame
{
    private static String TITLE_IMG = "src/main/resources/title.jpg";
    private static JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel infoPanel;

    public GUIUserMenu()
    {
        FontAndColors.init();
        if(mainFrame != null)
            return;

        setupUI();
        setContentPane(getRootComponent());
        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame = this;
    }

    public void getInfoMessage()
    {
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(DEFAULT_BACKGROUND);

        CustomButton newGameButton = new CustomButton();
        newGameButton.setText("Back");
        newGameButton.addActionListener(e -> {
            infoPanel.setVisible(false);
        });

        Font label12Font = FontAndColors.getFont(15, newGameButton.getFont(), false);
        if(label12Font != null) newGameButton.setFont(label12Font);

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        infoPanel.add(newGameButton, gbc);

        final JLabel label1 = new JLabel();
        Font label1Font = FontAndColors.getFont(26, label1.getFont(), false);
        if(label1Font != null) label1.setFont(label1Font);
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("MINESWEEPER");
        label1.setVerticalAlignment(1);
        label1.setVerticalTextPosition(1);
        label1.setForeground(Color.LIGHT_GRAY);

        infoPanel.add(label1);
    }

    private void setupUI()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(DEFAULT_BACKGROUND);

        CustomButton newGameButton = new CustomButton();
        newGameButton.setText("New game");
        // отслеживаем все действия
        newGameButton.addActionListener(e -> {
            Minesweeper.FieldVariant dim = getDimension(mainFrame);
            if(dim == Minesweeper.FieldVariant.nothing)
                return;
            if(dim == Minesweeper.FieldVariant.getinfo)
            {
                getInfoMessage();
                //return;
            }
            openGame(dim);
        });


        Font label12Font = FontAndColors.getFont(25, newGameButton.getFont(), false);
        if(label12Font != null) newGameButton.setFont(label12Font);

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(newGameButton, gbc);


        ImageIcon icon = new ImageIcon(TITLE_IMG);
        JLabel label = new JLabel(icon);
        CustomButton ghostButton = new CustomButton();
        ghostButton.setIcon(icon);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        int iconSize = 3;
        gbc.gridheight = iconSize;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(ghostButton, gbc);


        CustomButton highScoresButton = new CustomButton();
        highScoresButton.setText("High Scores");
        label12Font = FontAndColors.getFont(25, highScoresButton.getFont(), false);
        if(label12Font != null) highScoresButton.setFont(label12Font);
        highScoresButton.addActionListener((e) -> ScoresFrame.showRecords(this));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1+iconSize+1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(highScoresButton, gbc);

        CustomButton rulesButton = new CustomButton();
        rulesButton.setText("Rules");
        rulesButton.addActionListener((e) -> AboutRules.showRules());
        label12Font = FontAndColors.getFont(25, rulesButton.getFont(), false);
        if(label12Font != null) rulesButton.setFont(label12Font);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1+iconSize+2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(rulesButton, gbc);

        CustomButton aboutButton = new CustomButton();
        aboutButton.setText("About");
        aboutButton.addActionListener((e) -> AboutFrame.showAbout());
        label12Font = FontAndColors.getFont(25, aboutButton.getFont(), false);
        if(label12Font != null) aboutButton.setFont(label12Font);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1+iconSize+3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(aboutButton, gbc);

        CustomButton exitButton = new CustomButton();
        exitButton.setText("Exit");
        label12Font = FontAndColors.getFont(25, exitButton.getFont(), false);
        if(label12Font != null) exitButton.setFont(label12Font);
        exitButton.addActionListener(e -> System.exit(0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1+iconSize+4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(exitButton, gbc);

        final JLabel label1 = new JLabel();
        Font label1Font = FontAndColors.getFont(45, label1.getFont(), false);
        if(label1Font != null) label1.setFont(label1Font);
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("MINESWEEPER");
        label1.setVerticalAlignment(1);
        label1.setVerticalTextPosition(1);
        label1.setForeground(Color.LIGHT_GRAY);

        mainPanel.add(label1);

        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.01;
        spacer1.setBackground(DEFAULT_BACKGROUND);
        mainPanel.add(spacer1, gbc);
    }

    private void openGame(Minesweeper.FieldVariant dim)
    {

        if(dim == Minesweeper.FieldVariant.getinfo)
        {
            Minesweeper.newGame(dim);
        }
        else {
            Minesweeper.newGame(dim);

            GUIGame cont = new GUIGame();
            Minesweeper.setMainController(cont);
            cont.init(this);
        }
    }
    private JComponent getRootComponent()
    {
        return mainPanel;
    }
}
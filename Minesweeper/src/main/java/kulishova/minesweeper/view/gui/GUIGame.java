package kulishova.minesweeper.view.gui;

import kulishova.minesweeper.model.game.Minesweeper;
import kulishova.minesweeper.model.game.records.ScoreData;
import kulishova.minesweeper.model.game.records.ScoreTable;
import kulishova.minesweeper.controller.GameViewController;
import kulishova.minesweeper.view.gui.PanelsFromMenu.NewGamePanel.CustomButton;
import kulishova.minesweeper.view.gui.PanelsFromMenu.NewGamePanel.CustomDialog;
import kulishova.minesweeper.view.gui.PanelsFromMenu.NewGamePanel.CustomPanel;
import kulishova.minesweeper.view.gui.FontAndColors.FontAndColors;
import kulishova.minesweeper.view.gui.PanelsFromMenu.infoPanels.ScoresFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import static kulishova.minesweeper.model.time.Timer.*;
import static kulishova.minesweeper.view.gui.FontAndColors.FontAndColors.DEFAULT_BACKGROUND;
import static kulishova.minesweeper.model.game.other.StringMethods.getTimeString;

public class GUIGame extends JPanel implements GameViewController
{
    private static String FLAG_IMG = "src/main/resources/flag.png";
    private static String TIMER_IMG = "src/main/resources/timer.png";
    private static String BOMB_IMG = "src/main/resources/bomb.png";
    private ImageIcon icon;
    private static final int SIZE = 40;
    private final HashMap<CustomButton, ImageIcon> imageMap = new HashMap<>();
    private ImageIcon bomb;
    private ImageIcon flag;
    private CustomPanel minePanel;
    private CustomPanel pausePanel;
    private JPanel statPanel;
    private JLabel flagCountL;
    private JLabel timerCountL;
    private CustomButton pause;
    private CustomButton back;
    private CustomButton replay;
    private CustomButton[] cells;
    private JPanel infoPanel;

    private boolean isButtonStop = false;
    private JFrame parent;

    private static int PANEL_SIZE_WIDTH = 300;
    private static int PANEL_SIZE_HEIGHT = 340;
    private static boolean small_window = false;

    public void init(JFrame parent)
    {
        FontAndColors.init();
        if(parent == null)
        {
            this.parent = new JFrame();
            this.parent.pack();
            Dimension dim;
            if (parent.getSize().height < PANEL_SIZE_HEIGHT) {
                small_window = true;
                dim = parent.getSize();
                dim.height = PANEL_SIZE_HEIGHT;
                dim.width = parent.getSize().width + 200;
                parent.setSize(dim);
            }
            this.parent.setLocationRelativeTo(null);
            this.parent.setDefaultCloseOperation(Minesweeper.isMainController(this) ? WindowConstants.EXIT_ON_CLOSE : WindowConstants.DISPOSE_ON_CLOSE);
            this.parent.setVisible(true);
        } else
            this.parent = parent;

        makeFrameListeners();
        setLayout(new GridBagLayout());
        this.parent.setContentPane(this);

        setSizes();
        fillContent();
        setAutoResizer();
        updatePanel();
        this.parent.setLocationRelativeTo(null);

        if(parent == null)
        {
            this.parent.revalidate();
            this.parent.repaint();
        }
    }


    @Override
    public Dimension getMinimumSize()
    {
        int width = minePanel.getMinimumSize().width + statPanel.getMinimumSize().width;
        int height = minePanel.getMinimumSize().height;
        return new Dimension(width, height);
    }

    @Override
    public Dimension getPreferredSize()
    {
        int width = minePanel.getMinimumSize().width + statPanel.getMinimumSize().width;
        int height = minePanel.getMinimumSize().height;
        return new Dimension(width, height);
    }

    private void setAutoResizer()
    {
        for(JButton b : cells)
        {
            Font label12Font = FontAndColors.getFont(22, b.getFont(), true);
            if(label12Font != null) b.setFont(label12Font);
            b.addComponentListener(new ComponentAdapter()
            {
                protected void decreaseFontSize(JButton comp)
                {
                    Font font = comp.getFont();
                    FontMetrics fm = comp.getFontMetrics(font);
                    int width = comp.getWidth();
                    int height = comp.getHeight();
                    int textWidth = fm.stringWidth(comp.getText());
                    int textHeight = fm.getHeight();

                    int size = font.getSize();
                    while(size > 0 && (textHeight - 10 > height || textWidth - 10 > width))
                    {
                        size -= 2;
                        font = font.deriveFont(font.getStyle(), size);
                        fm = comp.getFontMetrics(font);
                        textWidth = fm.stringWidth(comp.getText());
                        textHeight = fm.getHeight();
                    }

                    comp.setFont(font);
                }

                protected void increaseFontSize(JButton comp)
                {
                    Font font = comp.getFont();
                    FontMetrics fm = comp.getFontMetrics(font);
                    int width = comp.getWidth();
                    int height = comp.getHeight();
                    int textWidth = fm.stringWidth(comp.getText());
                    int textHeight = fm.getHeight();

                    int size = font.getSize();
                    while(textHeight - 25 < height && textWidth - 25 < width)
                    {
                        size += 2;
                        font = font.deriveFont(font.getStyle(), size);
                        fm = comp.getFontMetrics(font);
                        textWidth = fm.stringWidth(comp.getText());
                        textHeight = fm.getHeight();
                    }

                    comp.setFont(font);
                    decreaseFontSize(comp);
                }

                @Override
                public void componentResized(ComponentEvent e)
                {
                    JButton comp = (JButton) e.getComponent();
                    Font font = comp.getFont();
                    FontMetrics fm = comp.getFontMetrics(font);
                    int width = comp.getWidth();
                    int height = comp.getHeight();
                    int textWidth = fm.stringWidth(comp.getText());
                    int textHeight = fm.getHeight();

                    int offset;
                    if(textHeight > height || textWidth > width)
                    {
                        decreaseFontSize(comp);
                        offset = (int) (width * 0.15);
                    } else
                    {
                        increaseFontSize(comp);
                        offset = (int) (width * 0.2);
                    }

                    if(imageMap.containsKey(comp))
                    {
                        //comp.setIcon(new ImageIcon(imageMap.get(comp).getScaledInstance(width - offset, height - offset, Image.SCALE_AREA_AVERAGING)));
                        if(!imageMap.get(comp).equals(bomb))
                            comp.setDisabledIcon(comp.getIcon());
                    }
                }
            });
        }
    }

    private void setSizes()
    {
        setPreferredSize(new Dimension(Minesweeper.getFieldSize().width * SIZE, Minesweeper.getFieldSize().height * SIZE));
        setSize(new Dimension(Minesweeper.getFieldSize().width * SIZE, Minesweeper.getFieldSize().height * SIZE));
        setToMinimum();
    }


    private void fillContent()
    {
        this.removeAll();
        this.setBackground(DEFAULT_BACKGROUND);
        minePanel = new CustomPanel();
        isButtonStop = false;

        minePanel.setPreferredSize(new Dimension(Minesweeper.getFieldSize().width * SIZE, Minesweeper.getFieldSize().height * SIZE));
        minePanel.setMinimumSize(new Dimension(Minesweeper.getFieldSize().width * SIZE, Minesweeper.getFieldSize().height * SIZE));
        minePanel.setLayout(new GridLayout(Minesweeper.getFieldSize().width, Minesweeper.getFieldSize().height, 0, 0));
        minePanel.setBackground(DEFAULT_BACKGROUND);

        cells = new CustomButton[Minesweeper.getFieldSize().width * Minesweeper.getFieldSize().height];

        for(int i = 0; i < Minesweeper.getFieldSize().width * Minesweeper.getFieldSize().height; i++)
        {
            CustomButton btn = new CustomButton();
            final int finalI = i;

            if(Minesweeper.isMainController(this))
                btn.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mousePressed(MouseEvent e)
                    {
                        if(!e.getComponent().isEnabled())
                            return;
                        if(!Minesweeper.isGameStarted())
                        {
                            Minesweeper.initField(finalI % Minesweeper.getFieldSize().width, finalI / Minesweeper.getFieldSize().height);
                            Minesweeper.startGame();
                            pause.setVisible(true);
                            replay.setVisible(true);
                            turnOnTimer();
                        }

                        if(SwingUtilities.isLeftMouseButton(e))
                            Minesweeper.openCell(finalI % Minesweeper.getFieldSize().width, finalI / Minesweeper.getFieldSize().height);
                        else if(SwingUtilities.isRightMouseButton(e))
                            Minesweeper.markCell(finalI % Minesweeper.getFieldSize().width, finalI / Minesweeper.getFieldSize().height);
                    }
                });
            minePanel.add(btn);
            if(!Minesweeper.isMainController(this))
                btn.setEnabled(false);
            cells[i] = btn;
        }
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        add(minePanel, c);  //игровое поле


        //теперь добавляем время и всякие кнопочки
        statPanel = new JPanel();
        statPanel.setLayout(new BorderLayout());
        statPanel.setBackground(DEFAULT_BACKGROUND);
        statPanel.setMinimumSize(new Dimension(120, Minesweeper.getFieldSize().height * SIZE));
        statPanel.setPreferredSize(new Dimension(120, Minesweeper.getFieldSize().height * SIZE));


        JPanel stat0Panel = new JPanel();
        stat0Panel.setLayout(new BoxLayout(stat0Panel, BoxLayout.Y_AXIS));
        stat0Panel.setBackground(DEFAULT_BACKGROUND);
        stat0Panel.setPreferredSize(new Dimension(125, 170));

        final JPanel spacer1 = new JPanel();
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.gridx = 0;
        gdc.gridy = 0;
        gdc.weightx = 1.0;
        gdc.weighty = 0.01;
        spacer1.setBackground(DEFAULT_BACKGROUND);
        stat0Panel.add(spacer1, gdc);

        gdc = new GridBagConstraints();
        gdc.gridx = 0;
        gdc.gridy = 1;
        flagCountL = new JLabel();
        icon = new ImageIcon(FLAG_IMG);
        flagCountL.setIcon(icon);

        flagCountL.setText("");
        flagCountL.setForeground(Color.LIGHT_GRAY);
        flagCountL.setAlignmentX(Component.CENTER_ALIGNMENT);
        flagCountL.setHorizontalTextPosition(JLabel.CENTER);
        flagCountL.setVerticalTextPosition(JLabel.BOTTOM);
        flagCountL.setSize(new Dimension(flagCountL.getMaximumSize().width*50, flagCountL.getMaximumSize().height*50));
        stat0Panel.add(flagCountL, gdc);

        timerCountL = new JLabel();
        icon = new ImageIcon(TIMER_IMG);
        timerCountL.setSize(10, 10);
        timerCountL.setIcon(icon);

        timerCountL.setText("00:00:00");
        timerCountL.setForeground(Color.LIGHT_GRAY);
        timerCountL.setHorizontalTextPosition(JLabel.CENTER);
        timerCountL.setVerticalTextPosition(JLabel.BOTTOM);
        timerCountL.setAlignmentX(Component.CENTER_ALIGNMENT);
        gdc = new GridBagConstraints();
        gdc.gridx = 0;
        gdc.gridy = 2;
        gdc.gridheight = 3;
        add(timerCountL, gdc);
        //gdc.gridheight = 4;
        stat0Panel.add(timerCountL, gdc);

        statPanel.add(stat0Panel, BorderLayout.NORTH);


        final JPanel spacer2 = new JPanel();
        gdc = new GridBagConstraints();
        gdc.gridx = 0;
        gdc.gridy = 5;
        gdc.weightx = 1.0;
        gdc.weighty = 0.01;
        spacer1.setBackground(DEFAULT_BACKGROUND);
        stat0Panel.add(spacer2, gdc);

        JPanel stat1Panel = new JPanel();
        stat1Panel.setLayout(new BoxLayout(stat1Panel, BoxLayout.Y_AXIS));
        stat1Panel.setBackground(DEFAULT_BACKGROUND);
        stat1Panel.setPreferredSize(new Dimension(120, 90));

        CustomButton newGame = new CustomButton("New game");
        newGame.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        newGame.addActionListener(e -> Minesweeper.rebuildControllers());

        Font label12Font = FontAndColors.getFont(15, newGame.getFont(), false);
        if(label12Font != null) newGame.setFont(label12Font);
        gdc = new GridBagConstraints();
        gdc.gridx = 0;
        gdc.gridy = 6;
        stat1Panel.add(newGame, gdc);

        replay = new CustomButton("Restart");
        replay.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        replay.addActionListener(e -> Minesweeper.restartControllers());

        label12Font = FontAndColors.getFont(15, replay.getFont(), false);
        if(label12Font != null) replay.setFont(label12Font);
        gdc = new GridBagConstraints();
        gdc.gridx = 0;
        gdc.gridy = 7;
        stat1Panel.add(replay, gdc);

        back = new CustomButton("Exit");
        back.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        back.addActionListener(e ->
        {
            int a = JOptionPane.showConfirmDialog(parent,
            "<html><h2>You are sure?</h2><i>Do you want close the game?</i>",
            "Message",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        });

        label12Font = FontAndColors.getFont(15, back.getFont(), false);
        if(label12Font != null) back.setFont(label12Font);
        stat1Panel.add(back);

        pause = new CustomButton("Pause");
        pause.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        pause.addActionListener(e ->
        {
            if(Minesweeper.isGameEnded())
                return;

            Minesweeper.pauseControllers();
            setPause();
        });

        label12Font = FontAndColors.getFont(15, pause.getFont(), false);
        if(label12Font != null) pause.setFont(label12Font);
        stat1Panel.add(pause);
        statPanel.add(stat1Panel, BorderLayout.SOUTH);


        c.gridx = Minesweeper.getFieldSize().width + 1;
        c.gridy = 0 ;
        add(statPanel, c);

        repaintFlag();

        if(!Minesweeper.isMainController(this))
            newGame.setVisible(false);
        pause.setVisible(false);
        replay.setVisible(false);
    }

    public void repaintFlag()
    {
        flagCountL.setText(Minesweeper.getFlagCount() + "/" + Minesweeper.getMaxFlagCount());
    }

    @Override
    public boolean restartGame()
    {
        if(Minesweeper.isMainController(this))
        {
            if(!Minesweeper.isGameEnded() && Minesweeper.isGameStarted())
            {
                int a = JOptionPane.showConfirmDialog(parent,
                        "<html><i>Do you want to restart?</i>",
                        "Restart game",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if(a != JOptionPane.YES_OPTION)
                {
                    this.grabFocus();
                    return false;
                }
            }
        }

        if(!isRunning() && pausePanel != null)
        {
            GridBagConstraints c0 = new GridBagConstraints();
            c0.gridx = 0;
            c0.gridy = 0;
            c0.fill = GridBagConstraints.BOTH;
            c0.anchor = GridBagConstraints.CENTER;

            pause.setText("Pause");
            remove(pausePanel);
            pausePanel = null;
            add(minePanel, c0);
        }

        for(var b : cells)
        {
            b.setBackground(FontAndColors.BUTTON_BACKGROUND);
            b.setText("");
            b.setIcon(null);
            b.setDisabledIcon(null);
            b.setEnabled(Minesweeper.isMainController(this));
            imageMap.remove(b);
        }
        if(Minesweeper.isMainController(this))
            Minesweeper.newGame();
        pause.setVisible(false);
        timerCountL.setText("00:00:00");
        isButtonStop = false;

        revalidate();
        repaint();
        repaintFlag();

        return true;
    }

    public void getInfoMessage()
    {
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(DEFAULT_BACKGROUND);
    }

    @Override
    public boolean rebuildField()
    {
        if(Minesweeper.isMainController(this))
        {
            if(!Minesweeper.isGameEnded() && Minesweeper.isGameStarted())
            {
                int a = JOptionPane.showConfirmDialog(parent,
                        "<html>Do you want start a new game?</html>",
                        "Info message",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if(a != JOptionPane.YES_OPTION)
                {
                    this.grabFocus();
                    return false;
                }
            }
            Minesweeper.FieldVariant dim = CustomDialog.getDimension(parent);
            if (dim == Minesweeper.FieldVariant.getinfo)
            {
                getInfoMessage();
                return true;
            }
            Minesweeper.newGame(dim);
        }

        setSizes();
        fillContent();
        setAutoResizer();
        updatePanel();
        isButtonStop = false;
        pause.setVisible(false);
        timerCountL.setText("00:00:00");
        repaintFlag();

        return true;
    }

    @Override
    public void noticeOverGame()
    {
        if(Minesweeper.isMainController(this))
            JOptionPane.showMessageDialog(parent, "<html><h2>You lose</h2>Try again!");
    }

    @Override
    public void noticeWinGame()
    {
        if(Minesweeper.isMainController(this))
        {
            JOptionPane.showMessageDialog(parent, "<html><h2>You win!</h2>Your score " +
                    getTimeString(getSeconds() / 3600) + ":" +
                    getTimeString((getSeconds() % 3600) / 60) + ":" +
                    getTimeString(getSeconds() % 60) + "!");

            String name;
            do
                name = (String) JOptionPane.showInputDialog(parent, "Enter your name (< 10 symb)", "Message", JOptionPane.QUESTION_MESSAGE, null, null, System.getProperty("user.name") == null ? "user" : System.getProperty("user.name"));
            while(name == null || name.contains(";") || name.length() > 10);

            ScoreData p = new ScoreData(name + ";" + getTimeString(getSeconds() / 3600) + ":" +
                    getTimeString((getSeconds() % 3600) / 60) + ":" +
                    getTimeString(getSeconds() % 60));
            ScoreTable.saveRecord(p);
            ScoresFrame.showRecords(parent);
        }
    }

    @Override
    public boolean isGUI()
    {
        return true;
    }

    @Override
    public void update(boolean makeOnlyOutSymbol)
    {
    }

    private CustomPanel createPausePanel()
    {
        CustomPanel blockPanel = new CustomPanel();
        blockPanel.setBackground(Color.GRAY);
        blockPanel.setPreferredSize(minePanel.getSize());
        blockPanel.setMinimumSize(minePanel.getMinimumSize());
        blockPanel.setLayout(new BorderLayout());

        JLabel pause = new JLabel("Pause");
        pause.setForeground(Color.LIGHT_GRAY);
        pause.setHorizontalTextPosition(JLabel.CENTER);
        pause.setHorizontalAlignment(JLabel.CENTER);
        pause.setFont(FontAndColors.getFont(28, pause.getFont(), false));
        pause.addComponentListener(new ComponentAdapter()
        {
            protected void decreaseFontSize(JLabel comp)
            {
                Font font = comp.getFont();
                FontMetrics fm = comp.getFontMetrics(font);
                int width = comp.getWidth();
                int height = comp.getHeight();
                int textWidth = fm.stringWidth(comp.getText());
                int textHeight = fm.getHeight();

                int size = font.getSize();
                while(size > 0 && (textHeight >= height * 0.2 || textWidth > width * 0.2))
                {
                    size -= 2;
                    font = font.deriveFont(font.getStyle(), size);
                    fm = comp.getFontMetrics(font);
                    textWidth = fm.stringWidth(comp.getText());
                    textHeight = fm.getHeight();
                }

                comp.setFont(font);
            }

            protected void increaseFontSize(JLabel comp)
            {
                Font font = comp.getFont();
                FontMetrics fm = comp.getFontMetrics(font);
                int width = comp.getWidth();
                int height = comp.getHeight();
                int textWidth = fm.stringWidth(comp.getText());
                int textHeight = fm.getHeight();

                int size = font.getSize();
                while(textHeight <= height * 0.2 || textWidth <= width * 0.2)
                {
                    size += 2;
                    font = font.deriveFont(font.getStyle(), size);
                    fm = comp.getFontMetrics(font);
                    textWidth = fm.stringWidth(comp.getText());
                    textHeight = fm.getHeight();
                }

                comp.setFont(font);
                decreaseFontSize(comp);
            }

            @Override
            public void componentResized(ComponentEvent e)
            {
                JLabel comp = (JLabel) e.getComponent();
                Font font = comp.getFont();
                FontMetrics fm = comp.getFontMetrics(font);
                int width = comp.getWidth();
                int height = comp.getHeight();
                int textWidth = fm.stringWidth(comp.getText());
                int textHeight = fm.getHeight();

                if(textHeight > height || textWidth > width)
                    decreaseFontSize(comp);
                else
                    increaseFontSize(comp);
            }
        });
        blockPanel.add(pause, BorderLayout.CENTER);

        return blockPanel;
    }

    private void turnOnTimer()
    {
        start(Minesweeper::repaintControllersTimer);
    }

    public void repaintTimer()
    {
        timerCountL.setText(getTimeString(getSeconds() / 3600) + ":" + getTimeString((getSeconds() % 3600) / 60) + ":" + getTimeString(getSeconds() % 60));
    }

    public void setNumCell(int x, int y, int num)
    {
        CustomButton btn = cells[y * Minesweeper.getFieldSize().width + x];
        Color col = switch(num)
        {
            case 1 -> new Color(3, 229, 15, 255);
            case 2 -> new Color(248, 217, 48, 255);
            case 3 -> new Color(243, 181, 122, 255);
            case 4 -> new Color(255, 134, 86, 255);
            case 5 -> new Color(231, 104, 103, 255);
            case 6 -> new Color(204, 62, 89, 255);
            case 7 -> new Color(182, 24, 37, 255);
            case 8 -> new Color(141, 1, 0, 255);
            default -> new Color(255, 255, 255, 255);
        };
        btn.setBackground(col);
        btn.setText("" + num);
        btn.setIcon(null);
        btn.setDisabledIcon(null);
        btn.repaint();
        btn.setEnabled(false);
    }

    public void markCell(int x, int y)
    {
        CustomButton btn = cells[y * Minesweeper.getFieldSize().width + x];
        flag = new ImageIcon(FLAG_IMG);
        btn.setIcon(flag);
        btn.repaint();
        imageMap.put(btn, flag);
    }

    public void markMaybeCell(int x, int y)
    {
    }

    public void offMarkOnCell(int x, int y)
    {
        CustomButton btn = cells[y * Minesweeper.getFieldSize().width + x];
        btn.setIcon(null);
        btn.setDisabledIcon(null);
        btn.repaint();
        imageMap.remove(btn);
    }

    public void freeCell(int x, int y)
    {
        CustomButton btn = cells[y * Minesweeper.getFieldSize().width + x];
        btn.setBackground(Color.WHITE);
        btn.setIcon(null);
        btn.setDisabledIcon(null);
        btn.setEnabled(false);
        btn.repaint();
    }

    public void onPause()
    {
        if(!isRunning())
            return;
        GridBagConstraints c0 = new GridBagConstraints();
        c0.gridx = 0;
        c0.gridy = 0;
        c0.fill = GridBagConstraints.BOTH;
        c0.anchor = GridBagConstraints.CENTER;

        remove(minePanel);
        pausePanel = createPausePanel();
        add(pausePanel, c0);
        revalidate();
        repaint();
        pause.setText("Run");
        stop();
        for(var a : cells)
            a.setEnabled(false);
    }

    public void offPause()
    {
        if(isRunning() || isButtonStop)
            return;

        GridBagConstraints c0 = new GridBagConstraints();
        c0.gridx = 0;
        c0.gridy = 0;
        c0.fill = GridBagConstraints.BOTH;
        c0.anchor = GridBagConstraints.CENTER;

        pause.setText("Pause");
        remove(pausePanel);
        pausePanel = null;
        add(minePanel, c0);
        revalidate();
        repaint();

        on();
        for(var a : cells)
            if(a.getBackground() == FontAndColors.BUTTON_BACKGROUND)
            {
                a.setEnabled(true);
            }
    }

    public void setPause()
    {
        GridBagConstraints c0 = new GridBagConstraints();
        c0.gridx = 0;
        c0.gridy = 0;
        c0.fill = GridBagConstraints.BOTH;
        c0.anchor = GridBagConstraints.CENTER;

        if(isRunning())
        {
            remove(minePanel);
            pausePanel = createPausePanel();
            add(pausePanel, c0);
            revalidate();
            repaint();
            pause.setText("Continue");
            isButtonStop = true;
            if(Minesweeper.isMainController(this))
                stop();
            for(var a : cells)
                a.setEnabled(false);
        } else
        {
            if(pausePanel != null)
            {
                remove(pausePanel);
                pausePanel = null;
                add(minePanel, c0);
            }
            pause.setText("Pause");
            revalidate();
            repaint();

            isButtonStop = false;
            if(Minesweeper.isMainController(this))
                on();
            for(var a : cells)
                if(a.getBackground() == FontAndColors.BUTTON_BACKGROUND && Minesweeper.isMainController(this))
                    a.setEnabled(true);
        }
    }

    public void disableGame(byte[][] map)
    {
        if(Minesweeper.isMainController(this))
            stop();

        for(int x = 0; x < map.length; x++)
            for(int y = 0; y < map[x].length; y++)
            {
                CustomButton btn = cells[y * Minesweeper.getFieldSize().width + x];
                if(Minesweeper.isMined(map[x][y]))
                {
                    bomb = new ImageIcon(BOMB_IMG);
                    btn.setIcon(bomb);
                    imageMap.put(btn, bomb);
                }
            }

        for(var a : cells)
            a.setEnabled(false);
        pause.setVisible(false);
    }

    private void setToMinimum()
    {
        parent.setMinimumSize(new Dimension(100, 100));
    }

    private void updatePanel()
    {
        parent.revalidate();
        parent.pack();

        Dimension dim;
        if (parent.getSize().height < PANEL_SIZE_HEIGHT) {
            dim = parent.getSize();
            dim.height = PANEL_SIZE_HEIGHT;
            dim.width = parent.getSize().width;
            parent.setSize(dim);
        }
        parent.setResizable(false);
    }

    private void makeFrameListeners()
    {
        if(Minesweeper.isMainController(this))
        {
            parent.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            parent.addWindowFocusListener(new WindowFocusListener()
            {
                @Override
                public void windowGainedFocus(WindowEvent e)
                {
                    if(Minesweeper.isGameStarted() && !isButtonStop)
                    {
                        Minesweeper.pauseControllers();
                        offPause();
                    }
                }

                @Override
                public void windowLostFocus(WindowEvent e)
                {
                    if(Minesweeper.isGameStarted() && !isButtonStop)
                    {
                        Minesweeper.pauseControllers();
                        onPause();
                    }
                }
            });

            parent.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    if(Minesweeper.isGameStarted() && Minesweeper.isMainController(((GameViewController) ((JFrame) e.getWindow()).getContentPane())))
                    {
                        int a = JOptionPane.showConfirmDialog(parent,
                                "<html><h2>You are sure?</h2><i>Do you want close the game?</i>",
                                "Message",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);

                        if(a != JOptionPane.YES_OPTION)
                            return;
                    }
                    Minesweeper.removeController(((GameViewController) ((JFrame) e.getWindow()).getContentPane()));
                    if(Minesweeper.isMainController(((GameViewController) ((JFrame) e.getWindow()).getContentPane())))
                        System.exit(0);
                }
            });
        }
    }
}
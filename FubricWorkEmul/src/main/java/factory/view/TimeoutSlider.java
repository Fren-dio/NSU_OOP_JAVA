package factory.view;

import javax.swing.*;

public class TimeoutSlider extends JSlider {
    public final static int TICK_SPACING = 50;

    public TimeoutSlider(int min, int max, int value) {
        super(min, max, value);

        setMajorTickSpacing(TICK_SPACING);
        setPaintTicks(true);
        setPaintLabels(true);
        setSnapToTicks(true);
    }
}

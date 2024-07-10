package factory.controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class BodySliderListener extends AbstractSliderListener {
    public BodySliderListener(FactoryControllerListener listener) {
        super(listener);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        listener.bodyTimeoutChanged(slider.getValue());
    }
}

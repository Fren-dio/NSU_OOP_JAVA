package factory.controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class AccessorySliderListener extends AbstractSliderListener {
    public AccessorySliderListener(FactoryControllerListener listener) {
        super(listener);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        listener.accessoryTimeoutChanged(slider.getValue());
    }
}

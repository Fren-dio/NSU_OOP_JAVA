package factory.controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DealerSliderListener extends AbstractSliderListener {
    public DealerSliderListener(FactoryControllerListener listener) {
        super(listener);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        listener.requestTimeoutChanged(slider.getValue());
    }
}

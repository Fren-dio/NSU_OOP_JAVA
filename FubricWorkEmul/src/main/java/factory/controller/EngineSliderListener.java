package factory.controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EngineSliderListener extends AbstractSliderListener {
    public EngineSliderListener(FactoryControllerListener listener) {
        super(listener);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        listener.engineTimeoutChanged(slider.getValue());
    }
}

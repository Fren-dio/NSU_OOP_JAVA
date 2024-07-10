package factory.controller;

import javax.swing.event.ChangeListener;

public abstract class AbstractSliderListener implements ChangeListener {
    protected final FactoryControllerListener listener;

    protected AbstractSliderListener(FactoryControllerListener listener) {
        this.listener = listener;
    }
}

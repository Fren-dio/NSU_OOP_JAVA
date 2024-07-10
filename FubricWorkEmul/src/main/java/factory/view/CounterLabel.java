package factory.view;

import javax.swing.*;
import java.awt.*;

public class CounterLabel extends JLabel {
    private final String text;
    private int counter;

    public CounterLabel(String text, int counter) {
        super(text + ": " + counter);
        this.text = text;
        this.counter = counter;
        setFont(new Font("Arial", Font.PLAIN, 18));
    }

    public void increment() {
        setCounter(++counter);
    }

    public void setCounter(int newCounter) {
        setText(text + ": " + newCounter);
        this.counter = newCounter;
    }

    public int getCounter() {
        return counter;
    }
}

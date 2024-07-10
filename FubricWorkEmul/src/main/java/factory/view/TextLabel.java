package factory.view;

import javax.swing.*;
import java.awt.*;

public class TextLabel extends JLabel {
    public TextLabel(String text) {
        super(text);
        setFont(new Font("Arial", Font.PLAIN, 18));
    }
}

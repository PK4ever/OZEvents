import javax.swing.*;
import java.awt.*;

public class CategoryButton extends JButton {
    public boolean current;
    private Color defaultColor;

    public CategoryButton(String s) {
        super(s);
        current = false;
        defaultColor = super.getBackground();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(current)
            super.setBackground(new Color(249,179,4));
        else
            super.setBackground(defaultColor);
    }
}
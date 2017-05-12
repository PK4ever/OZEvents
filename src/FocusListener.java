import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FocusListener extends FocusAdapter {
    @Override
    public void focusGained(FocusEvent e) {
        JTextField textField = (JTextField) e.getSource();
        if (textField.getText().equals(textField.getName())){
            textField.setText("");
            textField.setForeground(Color.black);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField textField = (JTextField) e.getSource();
        if(textField.getText().equals("")) {
            textField.setText(textField.getName());
            textField.setForeground(Color.gray);
        }
    }
}

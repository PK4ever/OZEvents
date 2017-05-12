import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ArrowListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        JButton button = (JButton) mouseEvent.getSource();
        button.setContentAreaFilled(false);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        JButton button = (JButton) mouseEvent.getSource();
        button.setContentAreaFilled(false);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        JButton button = (JButton) mouseEvent.getSource();
        button.setContentAreaFilled(false);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        JButton button = (JButton) mouseEvent.getSource();
        button.setContentAreaFilled(false);
    }
}

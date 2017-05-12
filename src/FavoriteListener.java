import com.evdb.javaapi.data.Event;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FavoriteListener implements MouseListener {
    private Event event;
    private Favorites favorites;

    public FavoriteListener(Event event, Favorites favorites) {
        this.event = event;
        this.favorites = favorites;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        JButton button = (JButton) mouseEvent.getSource();
        button.setContentAreaFilled(true);
        if (button.getIcon().toString().equals("lib/heart-empty-icon.png")) {
            button.setIcon(new ImageIcon("lib/heart-icon.png"));
            favorites.addEvent(event);
        } else {
            button.setIcon(new ImageIcon("lib/heart-empty-icon.png"));
            favorites.removeEvent(event);
        }
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
        button.setContentAreaFilled(true);
        button.setToolTipText("Add to favorites");
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        JButton button = (JButton) mouseEvent.getSource();
        button.setContentAreaFilled(false);
    }
}

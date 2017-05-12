import com.evdb.javaapi.data.Event;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class EventPanelListener implements MouseListener {
    Event event;
    public static Favorites favorites;

    public EventPanelListener(Event event, Favorites favorites) {
        this.event = event;
        this.favorites = favorites;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        EventPanel eventPanel = (EventPanel) e.getSource();

        if (Constants.EVENT_DIALOG != null){
            Constants.EVENT_DIALOG.setVisible(false);
            Constants.EVENT_DIALOG.dispose();
            Constants.EVENT_DIALOG = null;
        }
        Constants.EVENT_DIALOG = new EventDialog(eventPanel.getFrame(), event.getTitle(), false, favorites);

        try {
            Constants.EVENT_DIALOG.populate(event);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Constants.EVENT_DIALOG.setVisible(true);
        
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        EventPanel panel = (EventPanel) e.getSource();
        panel.getInfoLabel().setBackground(Color.white);
        panel.setBackground(Color.white);
        panel.setToolTipText(event.getTitle());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        EventPanel panel = (EventPanel) e.getSource();
        panel.getInfoLabel().setBackground(new ColorUIResource(214,217,223));
        panel.setBackground(new ColorUIResource(214,217,223));
    }
}
import com.evdb.javaapi.data.Event;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EventfulWorker extends SwingWorker {
    private Eventful eventful;
    private GUI gui;
    private List<Event> list;

    public EventfulWorker(Eventful eventful, GUI gui) {
        this.eventful = eventful;
        this.gui = gui;
    }

    /**
     * Try and obtain the Lock provided by the Eventful class.
     * If we obtain the lock, then we are the only Thread attempting to pull information from the Eventful API
     * Otherwise, some other Thread is loading and we should do nothing.
     * Set the curser to the Wait Cursor & load the list of Events, pass the list to the slider component,
     * and the slider component will repaint itself.
     * @return
     * @throws Exception
     */
    @Override
    protected Object doInBackground() throws Exception {
        if(eventful.loadingLock.tryLock()) {
            try {
                System.out.println("Trying to load......");
                gui.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                JButton[] buttons = gui.categoryButtons;
                for(int i = 0; i < buttons.length; i++) {
                    buttons[i].setEnabled(false);
                }
                gui.keywords.setEnabled(false);
                gui.location.setEnabled(false);
                gui.sort.setEnabled(false);
                gui.search.setEnabled(false);
                gui.leftButton.setEnabled(false);
                gui.rightButton.setEnabled(false);
                list = eventful.eventOperations.search(eventful.eventSearchRequest).getEvents();
            } finally {

                System.out.println("Done Loading.");
                gui.slider.repopulate(list, gui.frame);
                JButton[] buttons = gui.categoryButtons;
                for(int i = 0; i < buttons.length; i++) {
                    buttons[i].setEnabled(true);
                }
                gui.keywords.setEnabled(true);
                gui.location.setEnabled(true);
                gui.sort.setEnabled(true);
                gui.search.setEnabled(true);
                gui.leftButton.setEnabled(false);
                gui.rightButton.setEnabled(true);
                eventful.loadingLock.unlock();
                gui.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
        return null;
    }
}
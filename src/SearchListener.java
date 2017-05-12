import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SearchListener implements ActionListener {
    private Eventful eventful;
    private Favorites favorites;
    private GUI gui;

    public SearchListener(Eventful eventful, GUI gui, Favorites favorites) {
        this.eventful = eventful;
        this.favorites = favorites;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* TODO: Clean This Up */
        // search
        String category;
        if(e.getSource() instanceof CategoryButton) {
            CategoryButton cb = (CategoryButton) e.getSource();
            category = cb.getText();
            if(category.equalsIgnoreCase("favorites")) {
                try {
                    gui.slider.repopulate(favorites.getFavorites(), gui.frame);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return;
            }
        }else if(e.getSource() == gui.sort) {
            System.out.println("sort is the sourse");
            category = gui.categoryButtons.toString();
            switch(String.valueOf(gui.sort.getSelectedItem())){
                case "Most Recent":
                    try {
                        gui.eventful.sortEvents(gui.keywords.getText(), gui.location.getText(), gui.categoryButtons.toString(),  gui, "DATE");
                    } catch (EVDBRuntimeException e1) {
                        e1.printStackTrace();
                    } catch (EVDBAPIException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "A-Z":
                    try {
                        gui.eventful.sortEvents(gui.keywords.getText(), gui.location.getText(), gui.categoryButtons.toString(),  gui, "TITLE");
                    } catch (EVDBRuntimeException e1) {
                        e1.printStackTrace();
                    } catch (EVDBAPIException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "Location":
                    try {
                        gui.eventful.sortEvents(gui.keywords.getText(), gui.location.getText(), gui.categoryButtons.toString(),  gui, "DISTANCE");
                    } catch (EVDBRuntimeException e1) {
                        e1.printStackTrace();
                    } catch (EVDBAPIException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "Category":
                    try {
                        gui.eventful.sortEvents(gui.keywords.getText(), gui.location.getText(), gui.categoryButtons.toString(),  gui, "VENUE_NAME");
                    } catch (EVDBRuntimeException e1) {
                        e1.printStackTrace();
                    } catch (EVDBAPIException e1) {
                        e1.printStackTrace();
                    }
                    break;
            }

        } else {
            gui.categoryButtons[0].doClick();
            return;
        }

        String keywords = gui.keywords.getText();
        if(keywords.equals(gui.keywords.getName()))
            keywords = "";
        String location = gui.location.getText();

        try {
            eventful.getEvents(keywords, location, category, gui);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

import javax.swing.*;

public class Main {
    // one Favorites object passed around
    static Favorites favorites = new Favorites();
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getInstalledLookAndFeels()[1].getClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Eventful eventful = new Eventful();
        GUI gui = new GUI(eventful, favorites);
        try {
            SwingUtilities.invokeAndWait(() -> {
                gui.createAndShow();
            });
        } catch(Exception e) {
            e.printStackTrace();
        }

        // attach listeners to components
        gui.location.addFocusListener(new FocusListener());
        gui.keywords.addFocusListener(new FocusListener());
        for(int i = 0; i < gui.categoryButtons.length; i++) {
            gui.categoryButtons[i].addActionListener(new CategoryListener(gui));
            gui.categoryButtons[i].addActionListener(new SearchListener(eventful, gui, favorites));
        }
        gui.sort.addActionListener(new SearchListener(eventful, gui, favorites));
        gui.search.addActionListener(new SearchListener(eventful, gui, favorites));
        gui.leftButton.addMouseListener(new ArrowListener());
        gui.rightButton.addMouseListener(new ArrowListener());
        gui.categoryButtons[0].doClick();
        gui.frame.setVisible(true);
    }
}
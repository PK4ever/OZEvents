import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryListener implements ActionListener {
    private GUI gui;

    public CategoryListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CategoryButton button = (CategoryButton) e.getSource();

        if (gui.currentCategory == button) {
            return;
        }

        button.current = true;
        gui.currentCategory.current = false;
        gui.currentCategory.repaint();
        gui.currentCategory = button;
    }
}
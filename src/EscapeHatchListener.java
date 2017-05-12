import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EscapeHatchListener implements ActionListener {
    EventDialog dialog;

    public EscapeHatchListener(EventDialog dialog){
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.setVisible(false);
    }
}

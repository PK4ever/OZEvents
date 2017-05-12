import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

public class MyComponentListener implements ComponentListener{

    public MyComponentListener(){
    }
    @Override
    public void componentResized(ComponentEvent e) {
        if (e.getSource() instanceof EventPanel) {
            EventPanel eventPanel = (EventPanel) e.getSource();
            try {
                eventPanel.setImageSize();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}

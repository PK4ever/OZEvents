import com.evdb.javaapi.data.Event;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyContainer <ParentType extends Container> {
    private JPanel glassPane;
    private JPanel basePanel;
    private boolean isSlideInProgress;
    private static final int RIGHT = 0x01;
    private static final int LEFT = 0x02;
    private ParentType  parent = null;
    private boolean useSlideButton = true;
    private final int next = 0;

    private final ArrayList<Component> jPanels = new ArrayList<>();

    private final Object lock= new Object();
    private Favorites favorites;
    public JButton leftButton;
    public JButton rightButton;
    Image image1;
    Image image2;
    Image image3;

    public MyContainer(ParentType parent, JButton leftButton, JButton rightButton, Favorites favorites) {
        this.leftButton = leftButton;
        this.rightButton = rightButton;
        image1 = new ImageIcon("lib/img1.jpg").getImage();
        image2 = new ImageIcon("lib/img2.jpg").getImage();
        image3 = new ImageIcon("lib/img3.jpg").getImage();
        this.parent = parent;
        this.favorites = favorites;

        glassPane = new JPanel();
        glassPane.setOpaque(false);

        // basePanel is the panel of which you add all the Panels that hold the Events.
        this.basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout());
        this.basePanel.setSize(parent.getSize());

        attachPanel();

        /* TODO: fix this */
        if (useSlideButton) {
            leftButton.addActionListener(e -> {
                rightButton.setContentAreaFilled(false);
                leftButton.setContentAreaFilled(true);
                slideLeft();
            });

            rightButton.addActionListener(e -> {
                rightButton.setContentAreaFilled(true);
                leftButton.setContentAreaFilled(false);
                slideRight();
            });
        }
    }

    private void attachPanel() {
        final ParentType w = this.parent;
        final JPanel j = (JPanel) w;
        j.add(basePanel);
    }

    public JPanel getBasePanel() {
        return basePanel;
    }

    public void repopulate(List<Event> events, JFrame frame) throws IOException {
        // remove old stuff
        basePanel.removeAll();
        jPanels.clear();

        // add new content
        ArrayList<JPanel> panels = new ArrayList<>();
        //JPanel currentPanel = new JPanel(new MigLayout("wrap 4"));

        JPanel currentPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                //g.drawImage(image, 0, 0, null);
                g.drawImage(image1,0,0,this.getWidth(),this.getHeight(),null);
            }
        };
        currentPanel.setLayout(new MigLayout("wrap 4"));
        panels.add(currentPanel);

        int currentCount = 0;
        int page = 2;
        for(Event e: events) {
            if(currentCount >= 12) {
                if (currentCount == 12 && page == 2) {
                    /**
                     * added images to the background panels
                     */
                    currentPanel = new JPanel() {
                        @Override
                        public void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            //g.drawImage(image, 0, 0, null);
                            g.drawImage(image2, 0, 0, this.getWidth(), this.getHeight(), null);
                        }
                    };
                    page = page+1;
                }else{
                    currentPanel = new JPanel() {
                        @Override
                        public void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            //g.drawImage(image, 0, 0, null);
                            g.drawImage(image3, 0, 0, this.getWidth(), this.getHeight(), null);
                        }
                    };
                }
                currentPanel.setLayout(new MigLayout("wrap 4"));
                panels.add(currentPanel);
                currentCount = 0;
            }

            EventPanel eventPanel = new EventPanel(e, favorites);
            eventPanel.setFrame(frame);

            /**
             * Making gaps between events
             */

            if (currentCount == 0 || currentCount ==4 || currentCount ==8) {
                currentPanel.add(eventPanel, "gap left 200, gap right 10,gap bottom 0, push, grow");
            }else if (currentCount == 3 ||currentCount == 7 || currentCount ==11){
                currentPanel.add(eventPanel, "gap left 10, gap right 200,gap bottom 0, push, grow");

            }else {
                currentPanel.add(eventPanel, "gap left 10, gap right 10,gap bottom 0, push, grow");

            }
            currentCount ++;
        }

        while(currentCount < 12) {
            if (currentCount == 0 || currentCount ==4 || currentCount ==8) {
                currentPanel.add(new EventPanel(), "gap left 200, gap right 10,gap bottom 0, push, grow");
            }else if (currentCount == 3 ||currentCount == 7 || currentCount ==11){
                currentPanel.add(new EventPanel(), "gap left 10, gap right 200,gap bottom 0, push, grow");

            }else {
                currentPanel.add(new EventPanel(), "gap left 10, gap right 10,gap bottom 0, push, grow");

            }
            currentCount ++;
        }

        currentPanel.setName("last");

        for(JPanel jp: panels) {
            addComponent(jp);
        }

        // repaint
        basePanel.revalidate();
        basePanel.repaint();

        // naming the panels
        for (int i = 0; i<jPanels.size();i++){
            if (i == 0) {
                jPanels.get(i).setName("firstPanel");
            }else if (i == jPanels.size()-1){
                jPanels.get(i).setName("lastPanel");
            }else{
                jPanels.get(i).setName("");
            }
        }
    }

    /**
     * Add the sliding panels to the basePanel
     * @param component The sliding panel
     */
    public void addComponent(Component component){
        if (jPanels.contains(component)) {
        }
        else {
            jPanels.add(component);
            if (jPanels.size() == 1) {
                basePanel.add(component);
            }
            component.setSize(basePanel.getSize());
            component.setLocation(0, 0);
        }
    }

    public void slide(final int slideType){
        if (!isSlideInProgress) {
            isSlideInProgress = true;
            final Thread t0 = new Thread(new Runnable() {
                @Override
                public void run() {
                    parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    glassPane.setVisible(false);
                    slide(true, slideType);
                    glassPane.setVisible(true);
                    parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    isSlideInProgress = false;
                }
            });
            t0.setDaemon(true);
            t0.start();
        }
        else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    public void slideLeft() {
        slide(LEFT);
    }

    public void slideRight() {
        slide(RIGHT);
    }

    private void slide(final boolean useLoop, final int slideType) {
        if (jPanels.size() < 2) {
            System.err.println("Not enough panels");
            return;
        }


        synchronized (lock) {
            Component componentOld = null;
            Component componentNew = null;

            if (slideType == LEFT) {
                rightButton.setEnabled(true);
                componentNew = jPanels.remove(jPanels.size() - 1);
                componentOld = jPanels.get(0);
                jPanels.add(0, componentNew);

                /**
                 *If it's the first panel, disable the left button
                 */
                if (componentNew.getName().equals("firstPanel")){
                    leftButton.setEnabled(false);
                    //rightButton.setEnabled(true);
                }

            }
            if (slideType == RIGHT) {
                leftButton.setEnabled(true);
                componentOld = jPanels.remove(0);
                jPanels.add(componentOld);
                componentNew = jPanels.get(0);
                /**
                 *If it's the last panel, disable the right button
                 */
                if (componentNew.getName().equals("lastPanel")){
                    rightButton.setEnabled(false);
                    //leftButton.setEnabled(true);
                }

            }

            final int w = componentOld.getWidth();
            final int h = componentOld.getHeight();
            final Point p1 = componentOld.getLocation();
            final Point p2 = new Point(0, 0);
            if (slideType == LEFT) {
                p2.x -= w;
            }
            if (slideType == RIGHT) {
                p2.x += w;
            }
            componentNew.setLocation(p2);
            int step = 0;
            if ((slideType == LEFT) || (slideType == RIGHT)) {
                step = (int) (((float) parent.getWidth() / (float) Toolkit.getDefaultToolkit().getScreenSize().width) * 40.f);
            }
            //else {
                //step = (int) (((float) parent.getHeight() / (float) Toolkit.getDefaultToolkit().getScreenSize().height) * 20.f);
            //}
            step = step < 5 ? 5 : step;
            basePanel.add(componentNew);
            basePanel.revalidate();
            if (useLoop) {
                final int max = (slideType == LEFT) || (slideType == RIGHT) ? w : h;
                final long t0 = System.currentTimeMillis();
                for (int i = 0; i != (max / step); i++) {
                    switch (slideType) {
                        case RIGHT: {
                            p1.x -= step;
                            componentOld.setLocation(p1);
                            p2.x -= step;
                            componentNew.setLocation(p2);
                            componentNew.repaint();

                            break;
                        }
                        case LEFT: {
                            p1.x += step;
                            componentOld.setLocation(p1);
                            p2.x += step;
                            componentNew.setLocation(p2);
                            componentNew.repaint();

                            break;
                        }

                        default:
                            new RuntimeException("ProgramCheck").printStackTrace();
                            break;
                    }

                    try {
                        Thread.sleep(1000/ (max / step));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
                final long t1 = System.currentTimeMillis();
            }
            componentOld.setLocation(+10000, +10000);
            componentNew.setLocation(0, 0);
        }
    }
}
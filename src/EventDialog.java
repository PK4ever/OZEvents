import com.evdb.javaapi.data.Event;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public  class EventDialog extends JDialog {
    private static EventDialog singleton;
    Font font = new Font("SansSerif", Font.BOLD, 12);
    Font font2 = new Font("SansSerif", Font.BOLD, 20);
    public JButton closeButton;
    public JButton escapeHatch;

    public Event event;
    public static Favorites favorites;

    public EventDialog(Frame owner, String title, boolean modal, Favorites favorites) {
        super(owner, title, modal);
        this.favorites = favorites;

        this.closeButton = new JButton(new ImageIcon("lib/exit.png"));
        closeButton.setContentAreaFilled(false);
        closeButton.addMouseListener(new ArrowListener());
        closeButton.addActionListener(new EscapeHatchListener(this));
        closeButton.setName("closeButton");

        this.escapeHatch = new JButton(new ImageIcon("lib/Oz-Events_sm.png"));
        escapeHatch.setName("escapeHatch");
        escapeHatch.setContentAreaFilled(false);
        escapeHatch.addActionListener(new EscapeHatchListener(this));

        this.setMinimumSize(new Dimension(1000, 1000));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    protected JRootPane createRootPane() {
        JRootPane rootPane = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");

        Action actionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        };
        InputMap inputMap = rootPane
                .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        rootPane.getActionMap().put("ESCAPE", actionListener);

        return rootPane;
    }

    public void populate(Event event) throws IOException {
        //JPanel panel = new JPanel(new MigLayout("flowy"));
        Image image3 = new ImageIcon("lib/img1.jpg").getImage();
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                //g.drawImage(image, 0, 0, null);
                g.drawImage(image3, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        panel.setLayout(new MigLayout("flowy"));
        EventPanel eventPanel = new EventPanel(event, favorites);
        panel.setPreferredSize(new Dimension(350,450));

        for (MouseListener ml: eventPanel.getMouseListeners()){
            eventPanel.removeMouseListener(ml);
        }

        eventPanel.eventTitleLabel.setFont(font);
        eventPanel.setMaximumSize(new Dimension(350,550));

        eventPanel.add(eventPanel.eventTitleLabel);
        JTextArea ta = new JTextArea();
        if (event.getDescription().equals("")){
            ta.setText("THERE IS NO DESCRIPTION FOR THIS EVENT");
        }else{
            ta.setText(event.getDescription());
        }
        ta.setRequestFocusEnabled(false);
        ta.setLineWrap(true);
        ta.setOpaque(false);
        ta.setBackground(new Color(214, 217, 223));
        ta.setSize(new Dimension(1000,500));

        JLabel label = new JLabel("Event Details");
        label.setFont(font2);
        label.setForeground(Color.white);

        JLabel title = new JLabel(event.getTitle());
        title.setFont(font2);
        title.setForeground(new Color(249,179,4));

        JPanel hatch = new JPanel(new MigLayout());
        hatch.add(escapeHatch,"center");
        hatch.setBackground(super.getBackground());
        panel.add(hatch,"north");
        //panel.add(hatch,"cell 0 0, gap left 400, gap top 10, gap bottom 10");
        panel.add(eventPanel,"cell 0 1,grow, gap top 20");
        panel.add(title,"cell 0 2, gap left 150");
        panel.add(closeButton, "cell 0 1, gap left 900, gap top 0");
        panel.add(label,"cell 0 3,gap top 20, gap bottom 10");
        panel.add(ta, "gap top 0, grow,push");
        //panel.setBackground(new Color(29,36,44));
        //panel.setBackground(new Color(3,45,84));
        this.add(panel);
    }
}
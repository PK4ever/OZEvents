import com.evdb.javaapi.data.Event;

import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class EventPanel extends JPanel {
    private Event event;
    private JLabel imageLabel = new JLabel();
    public JPanel informationPanel = new JPanel(new MigLayout());
    String eventInfo;
    BufferedImage image1;
    JLabel eventTitleLabel;
    public JFrame frame;
    boolean nullEvent = false;

    int x = 300, y = 150;
    JButton likeButton;
    Font font = new Font("SansSerif", Font.BOLD, 16);

    public static Favorites favorites;
    public String eventTitle;

    public EventPanel(Event event, Favorites favorites) throws IOException {
        this.event = event;
        this.favorites = favorites;
        this.setLayout(new MigLayout());

        this.eventTitle = event.getTitle();
        eventInfo = "<html>" + event.getStartTime().toString() +
                "<br>" + event.getVenueCity() + "</html>";
        this.addComponentListener(new MyComponentListener());
        if (favorites.containsEvent(event))
            likeButton = new JButton(new ImageIcon("lib/heart-icon.png"));
        else
            likeButton = new JButton(new ImageIcon("lib/heart-empty-icon.png"));
        likeButton.setContentAreaFilled(false);
        likeButton.addMouseListener(new FavoriteListener(event, favorites));
        this.addMouseListener(new EventPanelListener(event, favorites));

        loadPanel();
    }

    public EventPanel() throws IOException {
        this.nullEvent = true;
        this.setLayout(new MigLayout());
        this.eventTitle = "Empty Event";
        this.eventInfo = "NO EVENT AVAILABLE";
        this.addComponentListener(new MyComponentListener());
        likeButton = new JButton(new ImageIcon("lib/heart-empty-icon.png"));
        likeButton.setName("likeButton");
        likeButton.setContentAreaFilled(false);
        likeButton.addMouseListener(new FavoriteListener(event, favorites));
        this.addMouseListener(new EventPanelListener(event, favorites));
        this.setVisible(false);
        loadPanel();
    }

    public void loadPanel() throws IOException {
        if (!nullEvent && event.getImages().get(0).getMedium() != null) {
            String imageUrl = event.getImages().get(0).getMedium().getUrl();
            URL url = new URL(imageUrl);
            image1 = ImageIO.read(url);
            JLabel label = new JLabel();
            label.setLayout(new MigLayout());

            label.setIcon(new ImageIcon(image1.getScaledInstance(x, y, image1.SCALE_SMOOTH)));
            this.imageLabel = label;
        } else {
            imageLabel = new JLabel(new ImageIcon("lib/default-events.jpg"));
        }

        eventTitleLabel = new JLabel();
        eventTitleLabel.setMaximumSize(eventTitleLabel.getSize());
        imageLabel.setMinimumSize(new Dimension(100, 50));

        eventTitleLabel.setText(eventTitle);
        eventTitleLabel.setFont(font);
        informationPanel.setLayout(new MigLayout());
        informationPanel.add(eventTitleLabel, "wrap, grow, push");
        informationPanel.add(new JLabel(eventInfo), "grow, push");

        this.setOpaque(true);

        this.add(imageLabel, "north, grow, push");
        this.add(informationPanel, "south, grow, push");
        this.add(likeButton, "east, grow, push");



    }

    public void setImageSize() throws IOException {
        x = this.getWidth();
        y = this.getHeight();
        if (image1 != null) {
            imageLabel.setIcon(new ImageIcon(image1.getScaledInstance(x, (y / 2), image1.SCALE_SMOOTH)));
        } else {
            image1 = ImageIO.read(new File("lib/default-events.jpg"));
            imageLabel.setIcon(new ImageIcon(image1.getScaledInstance(x, (y / 2), image1.SCALE_SMOOTH)));
        }
        imageLabel.setMinimumSize(new Dimension(50, 50));
        informationPanel.setMinimumSize(new Dimension(50, 50));
        informationPanel.setMaximumSize(new Dimension(400, 300));
        eventTitleLabel.setMaximumSize(new Dimension(this.getWidth() - 50, 20));
    }

    public JPanel getInfoLabel() {
        return informationPanel;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
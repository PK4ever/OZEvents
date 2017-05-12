import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class GUI {
    public CategoryButton currentCategory;
    public CategoryButton[] categoryButtons;
    public Eventful eventful;
    public static Favorites favorites;
    public JButton search, leftButton, rightButton;
    public JComboBox sort;
    public JFrame frame;
    public JTextField keywords, location;
    public MyContainer slider;
    public JLabel logo;

    public GUI(Eventful eventful, Favorites favorites) {
        this.eventful = eventful;
        this.favorites = favorites;
    }

    public void createAndShow() {
        frame = new JFrame("Events");

        addComponentToPane(frame.getContentPane());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(false); // initialize to hidden until main method adds all the listeners
    }

    private void addComponentToPane(Container pane) {

        pane.setLayout(new MigLayout());

        Font searchFont = new Font("SansSerif", Font.BOLD, 14);
        // Search bar
        keywords = new JTextField();
        keywords.setFont(searchFont);

        // check against default value
        keywords.setName("Title, Venue, Keywords, etc.");
        keywords.setPreferredSize(new Dimension(300, 40));
        keywords.setText(keywords.getName());
        keywords.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        keywords.setForeground(Color.gray);

        JLabel keywordsLabel = new JLabel("Search:");
        Font labelFont = new Font("SansSerif", Font.BOLD, 20);
        keywordsLabel.setFont(labelFont);
        keywordsLabel.setForeground(new Color(249,179,4));

        // location bar
        location = new JTextField();
        location.setPreferredSize(new Dimension(300, 40));
        location.setName(eventful.getLocation().getCity());
        location.setText(location.getName());
        location.setFont(searchFont);
        location.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        location.setForeground(Color.gray);

        JLabel locationLabel = new JLabel("Location (city):");
        locationLabel.setFont(labelFont);
        locationLabel.setForeground(new Color(249,179,4));

        // Sort
        sort = new JComboBox(new String[]{"DATE", "DISTANCE"});
        sort.setPreferredSize(new Dimension(20, 40));

        JLabel sortLabel = new JLabel("Sort by:");
        Font sortFont = new Font("SansSerif", Font.BOLD, 16);
        sortLabel.setFont(sortFont);
        sortLabel.setForeground(new Color(249,179,4));


        // search Button
        search = new JButton(new ImageIcon("lib/searchIcon.png"));

        // Button panel
        String[] buttonNames = {"ALL", "MUSIC", "SPORTS", "MOVIES", "COMEDY", "EDUCATION", "FAMILY", "ARTS", "FOOD", "FAVORITES"};
        categoryButtons = new CategoryButton[buttonNames.length];

        for(int i = 0; i < buttonNames.length; i++) {
            categoryButtons[i] = new CategoryButton(buttonNames[i]);
            categoryButtons[i].setPreferredSize(new Dimension(20,40));

            categoryButtons[i].setForeground(new Color(92,119,5));
            if(i == 0) { // "ALL"
                currentCategory = categoryButtons[i];
                categoryButtons[i].current = true;
            }
        }
        /**
         * ADDS COLORS TO THE LABELS
         */


        /**
        *@logoPanel is a panel at the top of the page that holds the panel
         * this is so we can set it a different color from the rest of the pane
         */
        // add everything to the panel
        ImageIcon icon = new ImageIcon("./lib/Oz-Events.png");
        logo = new JLabel(icon);
        JPanel logoPanel = new JPanel(new MigLayout());
        logoPanel.add(logo,"center,grow, push");
        //pane.add(logo, "cell 1 0, center");
        //logoPanel.setBackground(Color.black);

        pane.add(logoPanel,"north");
        pane.add(locationLabel, "cell 1 1, gap bottom 60, center, pushx");
        pane.add(location, "cell 1 1");
        pane.add(keywordsLabel, "cell 1 1, pushx");
        pane.add(keywords, "cell 1 1");
        pane.add(search, "cell 1 1, gap bottom 60, gap left 0, pushx");
        pane.add(sortLabel, "cell 1 1, gap left 20, pushx");
        pane.add(sort, "cell 1 1");

        /* button panel */
        for(int i = 0; i < categoryButtons.length; i++) {
            pane.add(categoryButtons[i], "cell 1 2, center, gap right 0, gap left 0, , gap top 0, gap bottom 20");
        }

        JPanel panel = new JPanel();
        leftButton = new JButton(new ImageIcon("lib/leftIcon.png"));
        rightButton = new JButton(new ImageIcon("lib/rightIcon.png"));
        rightButton.setContentAreaFilled(false);
        leftButton.setContentAreaFilled(false);

        pane.add(leftButton,"cell 0 3,gap left 50");
        pane.add(rightButton,"cell 2 3 ,gap right 50");

        slider = new MyContainer(panel, leftButton, rightButton, favorites);

        JPanel sliderPanel = slider.getBasePanel();
        pane.add(sliderPanel,"cell 1 3, push, grow");
        /**
         * pane color
         */
        //pane.setBackground(new Color( 243,207,4));
        //pane.setBackground(new Color(53, 42, 85));
        pane.setBackground(new Color(3,45,84));

    }
}
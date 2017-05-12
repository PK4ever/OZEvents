import com.evdb.javaapi.APIConfiguration;
import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;
import com.evdb.javaapi.data.Image;
import com.evdb.javaapi.data.Location;
import com.evdb.javaapi.data.request.EventSearchRequest;
import com.evdb.javaapi.operations.EventOperations;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Eventful {
    public Lock loadingLock;
    final private String API_KEY = "qX2dNH9TZGLvBT8B";
    final private String USER_NAME = "events420";
    final private String PASSWORD = "pass55word";

    private Location location;
    public EventOperations eventOperations;
    public EventSearchRequest eventSearchRequest;

    public Eventful() {
        loadingLock = new ReentrantLock();
        APIConfiguration apiConfiguration = new APIConfiguration();
        apiConfiguration.setApiKey(API_KEY);
        apiConfiguration.setEvdbUser(USER_NAME);
        apiConfiguration.setEvdbPassword(PASSWORD);

        eventOperations = new EventOperations();
        eventSearchRequest = new EventSearchRequest();
        eventSearchRequest.setPageSize(30);

        location = new Location();
        location.setCountry("US");
        location.setCity("New York");
    }

    /* TODO: Clean this and sortEvents up */
    public void getEvents(String keywords, String location, String category, GUI gui) throws EVDBRuntimeException, EVDBAPIException {
        eventSearchRequest.setKeywords(keywords.toLowerCase());
        this.location.setCity(location.toLowerCase());
        eventSearchRequest.setLocation(this.location.getCity());
        eventSearchRequest.setCategory(category.toLowerCase());

        eventSearchRequest.setSortOrder(EventSearchRequest.SortOrder.valueOf((String)gui.sort.getSelectedItem()));

        new EventfulWorker(this, gui).execute();
    }

    public Location getLocation() {
        return location;
    }

    public List<Image> getEventImages(Event event){
        return event.getImages();
    }

    public JLabel getEventImage(Event event) throws IOException {
        String imageUrl = event.getImages().get(0).getMedium().getUrl();

        URL url = new URL(imageUrl);
        BufferedImage image1 = ImageIO.read(url);
        JLabel label = new JLabel();
        label.setLayout(new MigLayout());
        label.setIcon(new ImageIcon(image1.getScaledInstance(220, 100, image1.SCALE_SMOOTH)));

        return label;
    }

    public void sortEvents(String keywords, String location, String category, GUI gui, String order) throws EVDBRuntimeException, EVDBAPIException {
        eventSearchRequest.setKeywords(keywords.toLowerCase());
        this.location.setCity(location.toLowerCase());
        eventSearchRequest.setLocation(this.location.getCity());
        eventSearchRequest.setCategory(category.toLowerCase());

        //eventSearchRequest.setSortOrder(EventSearchRequest.SortOrder.valueOf(order));

        new EventfulWorker(this, gui).execute();

    }
}

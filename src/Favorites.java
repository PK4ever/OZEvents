import com.evdb.javaapi.data.Event;

import java.util.ArrayList;
import java.util.Date;

public class Favorites {
    private ArrayList<ListObject> list;

    public Favorites() {
        list = new ArrayList<>();
    }

    public void addEvent(Event event) {
        if(!this.containsEvent(event))
            list.add(new ListObject(event));
    }

    public void removeEvent(Event event) {
        System.out.println("removeEvent() with size of: " + list.size());
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).equals(event)) {
                System.out.println(list.remove(i));
                System.out.println("*** Removed!! size is now : " + list.size() + " ***");
                return;
            }
        }
    }

    public boolean containsEvent(Event event) {
        for(ListObject lo: list) {
            if (lo.equals(event))
                return true;
        }
        return false;
    }

    public ArrayList<Event> getFavorites() {
        ArrayList<Event> returnList = new ArrayList<>();
        for(ListObject lo: list) {
            returnList.add(lo.event);
        }

        return returnList;
    }

    private class ListObject {
        Event event;

        ListObject(Event event) {
            this.event = event;
        }

        @Override
        public boolean equals(Object e) {
            if(e == null || !(e instanceof Event)) {
                return false;
            }

            Event checkEvent = (Event) e;
            String title = checkEvent.getTitle();
            String description = checkEvent.getDescription();
            Date date = checkEvent.getStartTime();

            if(event.getTitle().equals(title) &&
                    event.getDescription().equals(description) &&
                    event.getStartTime().equals(date)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
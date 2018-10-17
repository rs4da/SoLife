package virginia.edu.solife;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventItem{

    private String name;
    private String host;
    private String desc;
    private String address;
    private Date date;
    private ArrayList<String> invite = new ArrayList<String>();
    // TODO: must add QR code field here

    public EventItem(String name, String host, String desc, String address, Date date){
        this.name = name;
        this.host = host;
        this.address = address;
        this.desc = desc;
        this.date = date;
    }
    // Getters and Setters
    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String gethost() {
        return host;
    }

    public void sethost(String host) {
        this.host = host;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<String> getInvite() {
        return invite;
    }

    public void setInvite(ArrayList<String> invite) {
        this.invite = invite;
    }

    public static ArrayList<EventItem> createInitialEventList(){
        ArrayList<EventItem> toReturn = new ArrayList<EventItem>();
        toReturn.add(new EventItem("Party", "Bob", "Gonna be lit!", "Middle of nowhere", new Date(2018-1900, 4, 25)));
        toReturn.add(new EventItem("Fundraiser", "NGO", "Come help a good cause!", "Paris",  new Date(2018-1900, 4, 29)));

        return toReturn;
    }
}

package virginia.edu.solife;

public class Event {

    private int eID;
    private String name;
    private String desc;
    private String host;
    private String time;
    private String adrs;
    private double lati;
    private double lngt;

    public Event(int eID, String name, String desc, String host, String time, String adrs, double lati, double lngt){
        this.eID = eID;
        this.name = name;
        this.desc = desc;
        this.host = host;
        this.time = time;
        this.adrs = adrs;
        this.lati = lati;
        this.lngt = lngt;
    }

    public Event(int eID, String name, String desc, String host, String time, String adrs){
        this.eID = eID;
        this.name = name;
        this.desc = desc;
        this.host = host;
        this.time = time;
        this.adrs = adrs;
        this.lati = 0;
        this.lngt = 0;
    }

    public int geteID() {
        return eID;
    }

    public void seteID(int eID) {
        this.eID = eID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAdrs() {
        return adrs;
    }

    public void setAdrs(String adrs) {
        this.adrs = adrs;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLngt() {
        return lngt;
    }

    public void setLngt(double lngt) {
        this.lngt = lngt;
    }

    @Override
    public String toString(){
        return eID + ": " + name + " @" + time;
    }
}

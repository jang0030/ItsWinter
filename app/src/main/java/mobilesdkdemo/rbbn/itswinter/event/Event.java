package mobilesdkdemo.rbbn.itswinter.event;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.net.URL;

public class Event {


    /**EventDetails.java
     * Zackery Brennan
     * 040952243
     *
     * Used to temporally store events in ArrayLists
     * */


    private String name,startDate,tkUrl;
    private double priceMin, priceMax;
    private String promoImage;
    private boolean saved;
    private long id;
    private String apiId;

    public Event(String name, String startDate, String tkUrl, Double priceMin, Double priceMax, String promoImage, Boolean saved, String apiId){
        this.name = name;
        this.startDate = startDate;
        this.tkUrl = tkUrl;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.promoImage = promoImage;
        this.saved = saved;
        this.apiId = apiId;
    }

    public Event(String name, String startDate, String tkUrl, Double priceMin, Double priceMax, String promoImage, Boolean saved, long id, String apiId){
        this(name, startDate, tkUrl, priceMin, priceMax, promoImage, saved, apiId);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTkUrl() {
        return tkUrl;
    }

    public double getPriceMin() { return priceMin; }

    public double getPriceMax() { return priceMax; }

    public String getPromoImage() {
        return promoImage;
    }

    public boolean isSaved() { return saved; }

    public void setSaved(boolean saved) { this.saved = saved; }

    public long getId() { return id; }

    public void setId(Long id){ this.id = id;}

    public String getApiId() { return apiId; }
}

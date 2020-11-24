package mobilesdkdemo.rbbn.itswinter.event;

import android.graphics.Bitmap;

public class Event {
    private String name,startDate,tkUrl;
    private double priceMin, priceMax;
    private Bitmap promoImage;
    private boolean saved;
    private long id;

    public Event(String name, String startDate, String tkUrl, Double priceMin, Double priceMax, Bitmap promoImage, Boolean saved){
        this.name = name;
        this.startDate = startDate;
        this.tkUrl = tkUrl;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.promoImage = promoImage;
        this.saved = saved;
    }

    public Event(String name, String startDate, String tkUrl, Double priceMin, Double priceMax, Bitmap promoImage, Boolean saved, long id){
        this(name, startDate, tkUrl, priceMin, priceMax, promoImage, saved);
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

    public Bitmap getPromoImage() {
        return promoImage;
    }

    public boolean isSaved() { return saved; }

    public void setSaved(boolean saved) { this.saved = saved; }

    public long getId() { return id; }
}

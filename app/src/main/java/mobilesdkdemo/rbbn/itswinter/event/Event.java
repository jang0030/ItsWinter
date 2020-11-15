package mobilesdkdemo.rbbn.itswinter.event;

import android.graphics.Bitmap;

public class Event {
    protected String name,startDate,tkUrl;
    protected double priceMin, priceMax;
    protected Bitmap promoImage;

    public Event(String name, String startDate, String tkUrl, Double priceMin, Double priceMax, Bitmap promoImage){
        this.name = name;
        this.startDate = startDate;
        this.tkUrl = tkUrl;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.promoImage = promoImage;
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

    public double getPriceMin() {
        return priceMin;
    }

    public double getPriceMax() {
        return priceMax;
    }

    public Bitmap getPromoImage() {
        return promoImage;
    }
}

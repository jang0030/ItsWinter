package mobilesdkdemo.rbbn.itswinter.event;

import android.graphics.Bitmap;

public class Event {
    protected String name,startDate,tKUrl;
    protected double priceMin, priceMax;
    protected Bitmap promoImage;

    public Event(String name, String startDate, String tKUrl, Double priceMin, Double priceMax, Bitmap promoImage){
        this.name = name;
        this.startDate = startDate;
        this.tKUrl = tKUrl;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.promoImage = promoImage;
    }
}

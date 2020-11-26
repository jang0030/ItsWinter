package mobilesdkdemo.rbbn.itswinter.event;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventSqlOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "EventsSavedDb";
    protected final static int VERSION_NUM = 1;
    public final static String EVENT_TABLE_NAME = "EVENTS";
    public final static String EVENT_COL_NAME = "NAME";
    public final static String EVENT_COL_START_DATE = "START_DATE";
    public final static String EVENT_COL_TKURL = "TKURL";
    public final static String EVENT_COL_PRICE_MIN = "PRICE_MIN";
    public final static String EVENT_COL_PRICE_MAX = "PRICE_MAX";
    public final static String EVENT_COL_PROMO_IMAGE = "PROMO_IMAGE";
    public final static String EVENT_COL_SAVED = "SAVED";
    public final static String EVENT_COL_ID = "id";
    public final static String EVENT_COL_APIID = "APIID";

    public EventSqlOpener(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EVENT_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EVENT_COL_NAME + " text,"
                + EVENT_COL_START_DATE + " text,"
                + EVENT_COL_TKURL + " text,"
                + EVENT_COL_PRICE_MIN  + " text,"
                + EVENT_COL_PRICE_MAX + " text,"
                + EVENT_COL_PROMO_IMAGE + " text,"
                + EVENT_COL_SAVED + " boolean, "
                + EVENT_COL_APIID + " String );");  // add or remove columns
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
}

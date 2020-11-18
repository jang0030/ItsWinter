package mobilesdkdemo.rbbn.itswinter.audio.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import mobilesdkdemo.rbbn.itswinter.audio.model.Album;

/**
 * This WinterDB is a concrete class for {@link androidx.room.RoomDatabase}
 *  * <p>
 *  This WinterDB make SQLite Database.
 *  The data base is named as WinterDB
 *  This WinterDBis singlton.
 *  This WinterDB has some DAO {@link AlbumDao}
 *  This WinterDB use DAO to get data.
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
@Database(entities = {Album.class},version = 1,  exportSchema = false)
public abstract class WinterDB extends RoomDatabase {

    public static final String DATABASE_NAME = "WinterDB";
    private static WinterDB instance;

    static WinterDB getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    WinterDB.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract AlbumDao getAlbumDao();
}

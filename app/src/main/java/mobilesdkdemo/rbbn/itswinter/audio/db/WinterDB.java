package mobilesdkdemo.rbbn.itswinter.audio.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import mobilesdkdemo.rbbn.itswinter.audio.model.Album;


@Database(entities = {Album.class},version = 1,  exportSchema = true)
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

package mobilesdkdemo.rbbn.itswinter.audio.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import mobilesdkdemo.rbbn.itswinter.audio.data.IAudioRepository;
import mobilesdkdemo.rbbn.itswinter.audio.model.Album;
import mobilesdkdemo.rbbn.itswinter.audio.utils.AudioUtils;

/**
 * This AlbumDao is an inteface for {@link WinterDB}
 *  * <p>
 *  This AlbumDao is made for Roomdatabase
 *  AlbumDao is used by Data Access object
 *  The method is implemeted automatically by Room
 *  This AlbumDao is the regular Dao inteface for {@link androidx.room.RoomDatabase}
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
@Dao
public interface AlbumDao {

    @Query("Select * from album")
    LiveData<List<Album>> getList_Album();

    @Query("SELECT * FROM album WHERE strAlbum LIKE '%' || :keyword || '%'" +
            "OR myMemo LIKE '%' || :keyword || '%'" +
            "OR strArtist LIKE '%' || :keyword || '%'")
    LiveData<List<Album>> getList_Album_filter(String keyword);
    @Insert
    long[] insert_Album(Album... albums);

    @Update
    int update_Album(Album... album);

    @Delete
    int delete_Album(Album... album);
}

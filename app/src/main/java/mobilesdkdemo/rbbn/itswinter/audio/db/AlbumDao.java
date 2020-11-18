package mobilesdkdemo.rbbn.itswinter.audio.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import mobilesdkdemo.rbbn.itswinter.audio.model.Album;

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

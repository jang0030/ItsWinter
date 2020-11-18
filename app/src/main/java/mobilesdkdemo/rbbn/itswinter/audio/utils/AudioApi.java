package mobilesdkdemo.rbbn.itswinter.audio.utils;

import mobilesdkdemo.rbbn.itswinter.audio.adapter.AlbumAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.adapter.MyRecyclerAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.model.Wrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This AudioApi is an interface to implment {@link AudioUtils}
 *  <p>
 *  This AudioApi has the field BASE_URL for https connection url
 *  This AudioApi is used by API for {@link retrofit2}
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public interface AudioApi {
    String BASE_URL = "https://www.theaudiodb.com/api/v1/json/1/";


   // https://www.theaudiodb.com/api/v1/json/1/searchalbum.php?s=XXXX

    // 쿼리
    @GET("searchalbum.php?")
    Call<Wrapper> getAlbums(@Query("s") String album);

    // https://www. theaudiodb.com/api/v1/json/1/track.php?m=2115888
    @GET("track.php?")
    Call<Wrapper> getTracks(@Query("m") int albumId);
    // https://www.theaudiodb.com/api/v1/json/1/track.php?=32793500
    @GET("track.php?")
    Call<Wrapper> getTrack(@Query("h") int trackId);
}

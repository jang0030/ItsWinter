package mobilesdkdemo.rbbn.itswinter.audio.data;

import mobilesdkdemo.rbbn.itswinter.audio.model.Wrapper;
import mobilesdkdemo.rbbn.itswinter.audio.utils.AudioUtils;
import retrofit2.Callback;

/**
 * This adapter is a inteface for {@link AudioRepository}
 *  * <p>
 *  This has 3 method to get data {@link #getAlbums(String, Callback)}, {@link #getTracks(int, Callback)}, {@link #getTrack(int, Callback)}
 *  The method need to be overloaded by th concrete class
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public interface IAudioRepository {
    void getAlbums(String albumName, Callback<Wrapper> callback);
    void getTracks(int albumId, Callback<Wrapper> callback);
    void getTrack(int trackId, Callback<Wrapper> callback);
}

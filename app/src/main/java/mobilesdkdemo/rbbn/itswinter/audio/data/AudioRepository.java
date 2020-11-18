package mobilesdkdemo.rbbn.itswinter.audio.data;

import mobilesdkdemo.rbbn.itswinter.audio.adapter.AlbumAdapter;
import mobilesdkdemo.rbbn.itswinter.audio.model.Wrapper;
import mobilesdkdemo.rbbn.itswinter.audio.utils.AudioUtils;
import retrofit2.Callback;

/**
 * This AudioRepository is a concrete class that extends {@link IAudioRepository}
 *  * <p>
 *  AudioRepository is used to get data by using {@link AudioUtils}
 *  AudioRepository has the audioUtils as the field of  this object.
 *  the audioUtils is setted as the AudioUtils with singlton
 *  This has 3 method to get data {@link #getAlbums(String, Callback)}, {@link #getTracks(int, Callback)}, {@link #getTrack(int, Callback)}
 *  This AudioRepository is used by retrofit .
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public class AudioRepository implements IAudioRepository {
    private AudioUtils audioUtils;

    public AudioRepository() {
        this.audioUtils = AudioUtils.getInstance();
    }

    @Override
    public void getAlbums(String albumName, Callback<Wrapper> callback) {
        audioUtils.getApi().getAlbums(albumName).enqueue(callback);
    }

    @Override
    public void getTracks(int albumId, Callback<Wrapper> callback) {
        audioUtils.getApi().getTracks(albumId).enqueue(callback);

    }

    @Override
    public void getTrack(int trackId, Callback<Wrapper> callback) {
        audioUtils.getApi().getTrack(trackId).enqueue(callback);
    }
}

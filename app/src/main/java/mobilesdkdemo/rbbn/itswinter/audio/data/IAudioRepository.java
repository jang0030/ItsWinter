package mobilesdkdemo.rbbn.itswinter.audio.data;

import mobilesdkdemo.rbbn.itswinter.audio.model.Wrapper;
import retrofit2.Callback;

public interface IAudioRepository {
    void getAlbums(String albumName, Callback<Wrapper> callback);
    void getTracks(int albumId, Callback<Wrapper> callback);
    void getTrack(int trackId, Callback<Wrapper> callback);
}

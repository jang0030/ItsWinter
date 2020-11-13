package mobilesdkdemo.rbbn.itswinter.audio.data;

import mobilesdkdemo.rbbn.itswinter.audio.model.Wrapper;
import mobilesdkdemo.rbbn.itswinter.audio.utils.AudioUtils;
import retrofit2.Callback;

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

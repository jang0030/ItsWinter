package mobilesdkdemo.rbbn.itswinter.audio.model;

import java.util.List;

/**
 * This Wrapper is class for get results in {@link mobilesdkdemo.rbbn.itswinter.audio.data.AudioRepository}.
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public class Wrapper {
    List<Album> album;
    List<Track> track;

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    public List<Track> getTrack() {
        return track;
    }

    public void setTrack(List<Track> track) {
        this.track = track;
    }
}


package mobilesdkdemo.rbbn.itswinter.audio.model;

import android.os.Parcelable;

/**
 * This Track is class for Track model and Track Table in {@link mobilesdkdemo.rbbn.itswinter.audio.data.AudioRepository}.
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public class Track {
    private int idTrack;
    private int idAlbum;
    private int idArtist;
    private int idLyric;
    private String idIMVDB;
    private String strTrack;
    private String strAlbum;
    private String strArtist;
    private String strArtistAlternate;
    private int intCD;
    private int intDuration;
    private String strGenre;
    private String strMood;
    private String strStyle;
    private String strTheme;
    private String strDescriptionEN;
    private String strTrackThumb;
    private String strTrack3DCase;
    private String strTrackLyrics;
    private String strMusicVid;
    private String strMusicVidDirector;
    private String strMusicVidCompany;
    private String strMusicVidScreen1;
    private int intTrackNumber;
    private int intLoved;
    private int intScore;
    private int intScoreVotes;
    private int intTotalListeners;
    private int intTotalPlays;
    private String strLocked;

    public int getIdTrack() {
        return idTrack;
    }

    public void setIdTrack(int idTrack) {
        this.idTrack = idTrack;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public int getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(int idArtist) {
        this.idArtist = idArtist;
    }

    public int getIdLyric() {
        return idLyric;
    }

    public void setIdLyric(int idLyric) {
        this.idLyric = idLyric;
    }

    public String getIdIMVDB() {
        return idIMVDB;
    }

    public void setIdIMVDB(String idIMVDB) {
        this.idIMVDB = idIMVDB;
    }

    public String getStrTrack() {
        return strTrack;
    }

    public void setStrTrack(String strTrack) {
        this.strTrack = strTrack;
    }

    public String getStrAlbum() {
        return strAlbum;
    }

    public void setStrAlbum(String strAlbum) {
        this.strAlbum = strAlbum;
    }

    public String getStrArtist() {
        return strArtist;
    }

    public void setStrArtist(String strArtist) {
        this.strArtist = strArtist;
    }

    public String getStrArtistAlternate() {
        return strArtistAlternate;
    }

    public void setStrArtistAlternate(String strArtistAlternate) {
        this.strArtistAlternate = strArtistAlternate;
    }

    public int getIntCD() {
        return intCD;
    }

    public void setIntCD(int intCD) {
        this.intCD = intCD;
    }

    public int getIntDuration() {
        return intDuration;
    }

    public void setIntDuration(int intDuration) {
        this.intDuration = intDuration;
    }

    public String getStrGenre() {
        return strGenre;
    }

    public void setStrGenre(String strGenre) {
        this.strGenre = strGenre;
    }

    public String getStrMood() {
        return strMood;
    }

    public void setStrMood(String strMood) {
        this.strMood = strMood;
    }

    public String getStrStyle() {
        return strStyle;
    }

    public void setStrStyle(String strStyle) {
        this.strStyle = strStyle;
    }

    public String getStrTheme() {
        return strTheme;
    }

    public void setStrTheme(String strTheme) {
        this.strTheme = strTheme;
    }

    public String getStrDescriptionEN() {
        return strDescriptionEN;
    }

    public void setStrDescriptionEN(String strDescriptionEN) {
        this.strDescriptionEN = strDescriptionEN;
    }

    public String getStrTrackThumb() {
        return strTrackThumb;
    }

    public void setStrTrackThumb(String strTrackThumb) {
        this.strTrackThumb = strTrackThumb;
    }

    public String getStrTrack3DCase() {
        return strTrack3DCase;
    }

    public void setStrTrack3DCase(String strTrack3DCase) {
        this.strTrack3DCase = strTrack3DCase;
    }

    public String getStrTrackLyrics() {
        return strTrackLyrics;
    }

    public void setStrTrackLyrics(String strTrackLyrics) {
        this.strTrackLyrics = strTrackLyrics;
    }

    public String getStrMusicVid() {
        return strMusicVid;
    }

    public void setStrMusicVid(String strMusicVid) {
        this.strMusicVid = strMusicVid;
    }

    public String getStrMusicVidDirector() {
        return strMusicVidDirector;
    }

    public void setStrMusicVidDirector(String strMusicVidDirector) {
        this.strMusicVidDirector = strMusicVidDirector;
    }

    public String getStrMusicVidCompany() {
        return strMusicVidCompany;
    }

    public void setStrMusicVidCompany(String strMusicVidCompany) {
        this.strMusicVidCompany = strMusicVidCompany;
    }

    public String getStrMusicVidScreen1() {
        return strMusicVidScreen1;
    }

    public void setStrMusicVidScreen1(String strMusicVidScreen1) {
        this.strMusicVidScreen1 = strMusicVidScreen1;
    }

    public int getIntTrackNumber() {
        return intTrackNumber;
    }

    public void setIntTrackNumber(int intTrackNumber) {
        this.intTrackNumber = intTrackNumber;
    }

    public int getIntLoved() {
        return intLoved;
    }

    public void setIntLoved(int intLoved) {
        this.intLoved = intLoved;
    }

    public int getIntScore() {
        return intScore;
    }

    public void setIntScore(int intScore) {
        this.intScore = intScore;
    }

    public int getIntScoreVotes() {
        return intScoreVotes;
    }

    public void setIntScoreVotes(int intScoreVotes) {
        this.intScoreVotes = intScoreVotes;
    }

    public int getIntTotalListeners() {
        return intTotalListeners;
    }

    public void setIntTotalListeners(int intTotalListeners) {
        this.intTotalListeners = intTotalListeners;
    }

    public int getIntTotalPlays() {
        return intTotalPlays;
    }

    public void setIntTotalPlays(int intTotalPlays) {
        this.intTotalPlays = intTotalPlays;
    }

    public String getStrLocked() {
        return strLocked;
    }

    public void setStrLocked(String strLocked) {
        this.strLocked = strLocked;
    }
}

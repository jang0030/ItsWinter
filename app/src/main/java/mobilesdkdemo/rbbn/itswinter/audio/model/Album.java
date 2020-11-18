package mobilesdkdemo.rbbn.itswinter.audio.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import mobilesdkdemo.rbbn.itswinter.audio.adapter.MyRecyclerAdapter;

/**
 * This Album is class for Album model and Album Table in {@link mobilesdkdemo.rbbn.itswinter.audio.db.WinterDB}.
 *  This Album is implemented {@link Parcelable}.
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
@Entity(tableName = "Album")
public class Album implements Parcelable {

    @PrimaryKey
    private int idAlbum;
    private int idArtist;
    private int idLabel;
    private String strAlbum;
    private String strArtist;
    private int intYearReleased;
    private String strStyle;
    private String strGenre;
    private String strLabel;
    private String strReleaseFormat;
    private int intSales;
    private String strAlbumThumb;
    private String strDescriptionEN;
    private String strDescriptionFR;
    private int intLoved;
    private double intScore;
    private int intScoreVotes;
    private String strReview;
    private String strMood;
    private String strLocked;
    private String myMemo;
    private int isSaved;

    public Album() {
    }


    protected Album(Parcel in) {
        idAlbum = in.readInt();
        idArtist = in.readInt();
        idLabel = in.readInt();
        strAlbum = in.readString();
        strArtist = in.readString();
        intYearReleased = in.readInt();
        strStyle = in.readString();
        strGenre = in.readString();
        strLabel = in.readString();
        strReleaseFormat = in.readString();
        intSales = in.readInt();
        strAlbumThumb = in.readString();
        strDescriptionEN = in.readString();
        strDescriptionFR = in.readString();
        intLoved = in.readInt();
        intScore = in.readDouble();
        intScoreVotes = in.readInt();
        strReview = in.readString();
        strMood = in.readString();
        strLocked = in.readString();
        myMemo = in.readString();
        isSaved = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idAlbum);
        dest.writeInt(idArtist);
        dest.writeInt(idLabel);
        dest.writeString(strAlbum);
        dest.writeString(strArtist);
        dest.writeInt(intYearReleased);
        dest.writeString(strStyle);
        dest.writeString(strGenre);
        dest.writeString(strLabel);
        dest.writeString(strReleaseFormat);
        dest.writeInt(intSales);
        dest.writeString(strAlbumThumb);
        dest.writeString(strDescriptionEN);
        dest.writeString(strDescriptionFR);
        dest.writeInt(intLoved);
        dest.writeDouble(intScore);
        dest.writeInt(intScoreVotes);
        dest.writeString(strReview);
        dest.writeString(strMood);
        dest.writeString(strLocked);
        dest.writeString(myMemo);
        dest.writeInt(isSaved);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public int getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(int isSaved) {
        this.isSaved = isSaved;
    }

    public String getMyMemo() {
        return myMemo;
    }

    public void setMyMemo(String myMemo) {
        this.myMemo = myMemo;
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

    public int getIdLabel() {
        return idLabel;
    }

    public void setIdLabel(int idLabel) {
        this.idLabel = idLabel;
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

    public int getIntYearReleased() {
        return intYearReleased;
    }

    public void setIntYearReleased(int intYearReleased) {
        this.intYearReleased = intYearReleased;
    }

    public String getStrStyle() {
        return strStyle;
    }

    public void setStrStyle(String strStyle) {
        this.strStyle = strStyle;
    }

    public String getStrGenre() {
        return strGenre;
    }

    public void setStrGenre(String strGenre) {
        this.strGenre = strGenre;
    }

    public String getStrLabel() {
        return strLabel;
    }

    public void setStrLabel(String strLabel) {
        this.strLabel = strLabel;
    }

    public String getStrReleaseFormat() {
        return strReleaseFormat;
    }

    public void setStrReleaseFormat(String strReleaseFormat) {
        this.strReleaseFormat = strReleaseFormat;
    }

    public int getIntSales() {
        return intSales;
    }

    public void setIntSales(int intSales) {
        this.intSales = intSales;
    }

    public String getStrAlbumThumb() {
        return strAlbumThumb;
    }

    public void setStrAlbumThumb(String strAlbumThumb) {
        this.strAlbumThumb = strAlbumThumb;
    }

    public String getStrDescriptionEN() {
        return strDescriptionEN;
    }

    public void setStrDescriptionEN(String strDescriptionEN) {
        this.strDescriptionEN = strDescriptionEN;
    }

    public String getStrDescriptionFR() {
        return strDescriptionFR;
    }

    public void setStrDescriptionFR(String strDescriptionFR) {
        this.strDescriptionFR = strDescriptionFR;
    }

    public int getIntLoved() {
        return intLoved;
    }

    public void setIntLoved(int intLoved) {
        this.intLoved = intLoved;
    }

    public double getIntScore() {
        return intScore;
    }

    public void setIntScore(double intScore) {
        this.intScore = intScore;
    }

    public int getIntScoreVotes() {
        return intScoreVotes;
    }

    public void setIntScoreVotes(int intScoreVotes) {
        this.intScoreVotes = intScoreVotes;
    }

    public String getStrReview() {
        return strReview;
    }

    public void setStrReview(String strReview) {
        this.strReview = strReview;
    }

    public String getStrMood() {
        return strMood;
    }

    public void setStrMood(String strMood) {
        this.strMood = strMood;
    }

    public String getStrLocked() {
        return strLocked;
    }

    public void setStrLocked(String strLocked) {
        this.strLocked = strLocked;
    }


}

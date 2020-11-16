package mobilesdkdemo.rbbn.itswinter.audio.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Album")
public class AlbumMini implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int albumID;

    private int idAlbum;
    private int idArtist;
    private String strAlbum;
    private String strArtist;
    private int intYearReleased;
    private String strGenre;

    public AlbumMini( int idAlbum, int idArtist, String strAlbum, String strArtist, int intYearReleased, String strGenre) {
        this.idAlbum = idAlbum;
        this.idArtist = idArtist;
        this.strAlbum = strAlbum;
        this.strArtist = strArtist;
        this.intYearReleased = intYearReleased;
        this.strGenre = strGenre;
    }

    protected AlbumMini(Parcel in) {
        albumID = in.readInt();
        idAlbum = in.readInt();
        idArtist = in.readInt();
        strAlbum = in.readString();
        strArtist = in.readString();
        intYearReleased = in.readInt();
        strGenre = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(albumID);
        dest.writeInt(idAlbum);
        dest.writeInt(idArtist);
        dest.writeString(strAlbum);
        dest.writeString(strArtist);
        dest.writeInt(intYearReleased);
        dest.writeString(strGenre);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlbumMini> CREATOR = new Creator<AlbumMini>() {
        @Override
        public AlbumMini createFromParcel(Parcel in) {
            return new AlbumMini(in);
        }

        @Override
        public AlbumMini[] newArray(int size) {
            return new AlbumMini[size];
        }
    };

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
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

    public String getStrGenre() {
        return strGenre;
    }

    public void setStrGenre(String strGenre) {
        this.strGenre = strGenre;
    }
}

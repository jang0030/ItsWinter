package mobilesdkdemo.rbbn.itswinter.audio.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This AudioUtils is class for {@link retrofit2}
 *  <p>
 *  This AudioUtils has the field mGetApi for {@link AudioApi}
 *  This AudioUtils has the field mRetrofit for {@link retrofit2}
 *  the mRetrofit make API  by {@link AudioApi} to call data
 *  </p>
 *  @author kiwoong kim
 *  @since 11152020
 *  @version 1.0
 */
public class AudioUtils {

    private AudioApi mGetApi;
    private static Retrofit mRetrofit;
    public static AudioUtils audioUtils;

    private AudioUtils() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(AudioApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGetApi = mRetrofit.create(AudioApi.class);
    }

    public static AudioUtils getInstance(){
        if(audioUtils ==null){
            audioUtils =new AudioUtils();
        }

        return audioUtils;
    }

    public AudioApi getApi() {
        return mGetApi;
    }
}

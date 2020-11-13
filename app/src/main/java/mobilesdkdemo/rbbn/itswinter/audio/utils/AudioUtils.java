package mobilesdkdemo.rbbn.itswinter.audio.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AudioUtils {

    private AudioApi mGetApi;
    private static Retrofit mRetrofit;
    public static AudioUtils movieUtil;

    private AudioUtils() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(AudioApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGetApi = mRetrofit.create(AudioApi.class);
    }

    public static AudioUtils getInstance(){
        if(movieUtil==null){
            movieUtil=new AudioUtils();
        }

        return movieUtil;
    }

    public AudioApi getApi() {
        return mGetApi;
    }
}

package rxjava.example.m;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;
import rxjava.example.ApiConstants;
import rxjava.example.BaseDataEntity;
import rxjava.example.CustomGsonConverterFactory;

public interface ApiService {

    /**
     * 工厂类
     */
    class Factory {
        private static final int DEFAULT_TIMEOUT = 30;
        private static final int DEFAULT_READ_TIMEOUT = 0;

        public static ApiService createApiService() {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);

            OkHttpClient httpClient = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(/*StringConverterFactory.create()*/CustomGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ApiService.class);
        }
    }

    //@FormUrlEncoded
    @GET
    Observable<BaseDataEntity<String>> chat(@Url String key);


}
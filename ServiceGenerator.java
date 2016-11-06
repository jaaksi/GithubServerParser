package org.an.proj.contacts.net.service.retrofit.util;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.an.proj.contacts.config.UriUtil;
import org.an.proj.contacts.net.service.retrofit.coverter.GsonConverterFactory;
import org.an.proj.contacts.net.service.retrofit.interceptor.HttpLoggingInterceptor;
import org.an.proj.contacts.net.service.retrofit.interceptor.SimpleHeaderInterceptor;
import org.an.proj.contacts.net.service.retrofit.upload.ProgressListener;
import org.an.proj.contacts.net.service.retrofit.upload.UploadInterceptor;
import org.an.proj.contacts.util.DataParseUtil;
import retrofit2.Retrofit;

/**
 * 创建时间：2016年10月10日11:10 <br>
 * 作者：fuchaoyang <br>
 * 描述：这里为了少创建，不采用url不同 Builder.baseUrl("").builder()的方式每次创建
 * （如果baseUrl过多可使用上述方式避免使用过多静态数据）
 */
public class ServiceGenerator {
  private static Retrofit sGithubRetrofit;
  private static Retrofit sAPIRetrofit;
  // 登录相关
  private static final Retrofit.Builder githubRetrofitBuilder;
  // 其他api相关
  private static final Retrofit.Builder apiRetrofitBuilder;
  private static OkHttpClient.Builder sHttpClientBuilder = new OkHttpClient.Builder();

  static {
    // 配置超时
    sHttpClientBuilder.connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES);
    // 设置超时时间 具体时长待定
    //.addNetworkInterceptor(new UploadProgressInterceptor(progressListener))
    //.addInterceptor()

    // 请求头
    SimpleHeaderInterceptor headerInterceptor = new SimpleHeaderInterceptor() {
      @Override public void initHeaders(HashMap<String, String> headers) {
        headers.put("time", String.valueOf(System.currentTimeMillis()));
        // token
        headers.put("token", "");
      }
    };
    sHttpClientBuilder.addInterceptor(headerInterceptor);

    // debug模式下打log
    if (Config.isDebugJsonLog) {
      HttpLoggingInterceptor loggingInterceptor = HttpLoggingInterceptor.createLoggingInterceptor()
          .setLevel(HttpLoggingInterceptor.Level.BODY);
      sHttpClientBuilder.addInterceptor(loggingInterceptor);
    }

    //  初始化retrofitBuilder
    githubRetrofitBuilder = generateRetrofitBuilder(DataParseUtil.mGithubGson);
    apiRetrofitBuilder = generateRetrofitBuilder(DataParseUtil.mGson);
  }

  private static Retrofit.Builder generateRetrofitBuilder(Gson gson) {
    return new Retrofit.Builder()
        //.addCallAdapterFactory(LinkCallAdapterFactory.created())
        //.addCallAdapterFactory(LinkRxCallAdapterFactory.create())
        //.addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson)).client(sHttpClientBuilder.build());
  }

  /**
   * 创建api Service
   */
  public static <S> S createService(Class<S> serviceClass) {
    if (sAPIRetrofit == null || !sAPIRetrofit.baseUrl()
        .equals(HttpUrl.parse(UriUtil.getUriBase()))) {
      sAPIRetrofit = apiRetrofitBuilder.baseUrl(UriUtil.getUriBase()).build();
    }

    return sAPIRetrofit.create(serviceClass);
  }

  /**
   * 创建github Service
   */
  public static <S> S createGithubService(Class<S> serviceClass) {
    if (sGithubRetrofit == null || !sGithubRetrofit.baseUrl()
        .equals(HttpUrl.parse(UriUtil.getGithubUriBase()))) {
      sGithubRetrofit = githubRetrofitBuilder.baseUrl(UriUtil.getGithubUriBase()).build();
    }

    return sGithubRetrofit.create(serviceClass);
  }

  /**
   * 创建 上传 Service
   */
  public static <S> S createUploadService(Class<S> serviceClass, ProgressListener listener) {
    OkHttpClient okHttpClient =
        sHttpClientBuilder.addNetworkInterceptor(new UploadInterceptor(listener)).build();

    if (sAPIRetrofit == null || !sAPIRetrofit.baseUrl()
        .equals(HttpUrl.parse(UriUtil.getUriBase()))) {
      sAPIRetrofit = apiRetrofitBuilder.baseUrl(UriUtil.getUriBase()).build();
    }

    return sAPIRetrofit.create(serviceClass);
  }
}

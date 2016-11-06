package org.an.proj.contacts.net.service.retrofit.coverter;

import android.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 * Created by an on 2016/11/4.<br/>
 * 自定义Gson TypeAdapterFactory,解析Github服务器返回response直接返回content对应的实体
 */

public class GithubTypeAdapterFactory implements TypeAdapterFactory {
  private static final String KEY_CONTENT = "content";

  @Override public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
    final TypeAdapter<T> adapter = new Gson().getAdapter(type);
    final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
    final TypeAdapter<JsonElement> elementTypeAdapter = gson.getAdapter(JsonElement.class);

    return new TypeAdapter<T>() {
      @Override public void write(JsonWriter out, T value) throws IOException {
        delegate.write(out, value);
      }

      //当获取了http请求的数据后，获得data所对应的值
      @Override public T read(JsonReader in) throws IOException {
        JsonElement jsonElement = elementTypeAdapter.read(in);
        if (jsonElement.isJsonObject()) {
          JsonObject jsonObject = jsonElement.getAsJsonObject();
          if (jsonObject.has(KEY_CONTENT)) {
            jsonElement = jsonObject.get(KEY_CONTENT);
          }
          String data = jsonElement.getAsString().replaceAll("\n", "");
          byte[] decode = Base64.decode(data, Base64.DEFAULT);
          String json = new String(decode);
          return adapter.fromJson(json);
        }

        return null;
      }
    }.nullSafe();
  }
}

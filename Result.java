package org.an.proj.contacts.model.response;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

/**
 * 接口返回基本数据结构
 * 注解@Expose必须和GsonBuilder配合使用,需要导出的字段上加上@Expose 注解
 *
 * @param <T> 返回数据对象
 * @author hlwang
 */
public class Result<T> implements Serializable {

  /**
   * 错误码
   */
  @Expose public int errno;

  /**
   * 错误原因
   */
  @Expose public String error;

  /**
   * 返回数据对象
   */
  @Expose public T data;
}

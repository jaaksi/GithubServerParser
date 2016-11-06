package org.an.proj.contacts.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by an on 2016/11/4.<br/>
 * 配置，这个要存起来
 */

public class ConfigInfoVo implements Serializable {
  /**
   * tab配置
   */
  public List<ConfigItemVo> tab;

  /**
   * 侧滑菜单配置
   */
  public List<ConfigItemVo> menu;

  public static class ConfigItemVo implements Serializable {
    public String id;
    public String title;
    public String imgUrl;
    public String actionUrl;
  }
}

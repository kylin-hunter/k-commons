package io.github.kylinhunter.commons.properties;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.GeneralException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-01 15:20
 */
public class PropObjectPool {
  private final Map<String, PropObject> datas = MapUtils.newHashMap();

  /**
   * @return java.lang.Object
   * @title getRoot
   * @description
   * @author BiJi'an
   * @date 2023-04-01 16:15
   */
  public PropObject getRoot() {
    return datas.get(PropObject.ROOT_ID);
  }

  /**
   * @param id id
   * @return java.lang.Object
   * @title getObject
   * @description
   * @author BiJi'an
   * @date 2023-04-01 15:47
   */
  public PropObject get(String id) {
    return datas.get(id);
  }

  /**
   * @param propObject objectDescriptor
   * @return io.github.kylinhunter.commons.properties.ObjectDescriptor
   * @title put
   * @description
   * @author BiJi'an
   * @date 2023-04-01 16:08
   */
  public PropObject add(PropObject propObject) {
    String objecId = propObject.getObjecId();
    if (datas.containsKey(objecId)) {
      throw new GeneralException("duplicate=>" + objecId);
    }
    this.datas.put(objecId, propObject);
    return propObject;
  }

  /**
   * @return java.util.List<io.github.kylinhunter.commons.properties.ObjectDescriptor>
   * @title resort
   * @description
   * @author BiJi'an
   * @date 2023-04-01 16:21
   */
  public List<PropObject> getSortedPropObjes() {
    return datas.values().stream().sorted().collect(Collectors.toList());
  }
}

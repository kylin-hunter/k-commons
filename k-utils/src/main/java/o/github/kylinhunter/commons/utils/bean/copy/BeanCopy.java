package o.github.kylinhunter.commons.utils.bean.copy;

import java.util.List;
import lombok.Setter;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.ConvertExcetion;
import o.github.kylinhunter.commons.utils.bean.copy.convertor.FieldConvertor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 */
public class BeanCopy {

  @Setter private List<FieldConvertor> fieldConvertors;

  public boolean isEmpty() {
    return (fieldConvertors == null || fieldConvertors.isEmpty());
  }

  /**
   * @param source source
   * @param target target
   * @return void
   * @title copy
   * @description
   * @author BiJi'an
   * @date 2022-11-19 01:14
   */
  public void copy(Object source, Object target) throws ConvertExcetion {

    if (fieldConvertors != null && !fieldConvertors.isEmpty()) {
      for (FieldConvertor fieldConvertor : fieldConvertors) {
        fieldConvertor.convert(source, target);
      }
    }
  }
}

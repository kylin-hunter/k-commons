package o.github.kylinhunter.commons.utils.bean.copy.convertor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldCopy {
  /**
   * @return io.github.kylinhunter.commons.bean.copy.convertor.ConvertType
   * @title value
   * @description
   * @author BiJi'an
   * @date 2022-11-19 01:05
   */
  ConvertType value();
}

package io.github.kylinhunter.commons.exception.info;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.init.AbstractInitializer;
import io.github.kylinhunter.commons.init.ClassScanner;
import io.github.kylinhunter.commons.init.Order;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.reflections.ReflectionUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-15 10:48
 */
@Order(Integer.MIN_VALUE)
public class ErrInfoInitializer extends AbstractInitializer {

  public ErrInfoInitializer(Set<String> pkgs) {
    super(pkgs);
  }

  public ErrInfoInitializer(ClassScanner classScanner) {
    super(classScanner);
  }

  /**
   * @title initialize
   * @description initialize
   * @author BiJi'an
   * @date 2023-06-19 17:33
   */
  @SuppressWarnings("unchecked")
  @Override
  public void init() throws InitException {

    Set<Class<?>> classes = this.classScanner.getTypesAnnotatedWith(ErrInfoAware.class);
    for (Class<?> clazz : classes) {
      Set<Field> allFields = ReflectionUtils.getAllFields(clazz);
      allFields.forEach(ErrInfoInitializer::processField);
    }

  }

  /**
   * @param field field
   * @title processField
   * @description processField
   * @author BiJi'an
   * @date 2023-06-21 01:22
   */
  private static void processField(Field field) {
    int modifiers = field.getModifiers();
    if (field.getType() == ErrInfo.class && Modifier.isStatic(modifiers)) {
      ErrInfo errInfo = (ErrInfo) ReflectUtils.get(null, field);
      if (StringUtil.isEmpty(errInfo.getDefaultMsg())) {
        errInfo.setDefaultMsg(field.getName());
      }
      ErrInfoManager.add(errInfo);
    }
  }

}
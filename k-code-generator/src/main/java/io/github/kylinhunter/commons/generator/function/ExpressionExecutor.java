package io.github.kylinhunter.commons.generator.function;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.sun.jndi.toolkit.url.Uri;
import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CAfter;
import io.github.kylinhunter.commons.exception.embed.biz.BizException;
import io.github.kylinhunter.commons.generator.exception.CodeException;
import io.github.kylinhunter.commons.io.IOHelper;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.util.ObjectValues;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.Lombok;
import lombok.SneakyThrows;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 01:05
 */
@C
public class ExpressionExecutor {

  /**
   * @param expression expression
   * @return java.lang.Object
   * @title execute
   * @description
   * @author BiJi'an
   * @date 2023-03-19 01:07
   */
  @SuppressWarnings("unchecked")
  public <T> T execute(final String expression) {
    try {
      return (T) AviatorEvaluator.execute(expression);
    } catch (Exception e) {
      throw new CodeException("execute error:" + expression, e);
    }
  }

  /**
   * @param expression expression
   * @param type       type
   * @return T
   * @title execute
   * @description
   * @author BiJi'an
   * @date 2023-06-01 20:23
   */
  public <T> T execute(final String expression, Class<T> type) {
    try {
      return ObjectValues.get(AviatorEvaluator.execute(expression), type);
    } catch (Exception e) {
      throw new CodeException("execute error:" + expression, e);
    }
  }

  /**
   * @param expression expression
   * @param env        env
   * @return T
   * @title execute
   * @description
   * @author BiJi'an
   * @date 2023-02-19 18:50
   */
  @SuppressWarnings("unchecked")
  @SneakyThrows(MalformedURLException.class)
  public <T> T execute(final String expression, final Map<String, Object> env) {

    new Uri(UserDirUtils.get().getAbsolutePath());

    return (T) AviatorEvaluator.execute(expression, env, true);

  }

  /**


  /**
   * @param expression expression
   * @param env        env
   * @param type       type
   * @return T
   * @title execute
   * @description
   * @author BiJi'an
   * @date 2023-06-01 20:29
   */
  public <T> T execute(final String expression, final Map<String, Object> env, Class<T> type) {
    try {
      return ObjectValues.get(AviatorEvaluator.execute(expression, env, true), type);
    } catch (Exception e) {
      throw new CodeException("execute error:" + expression, e);
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T executeByFile(final String path, final Map<String, Object> env) {
    try (InputStream inputStream = ResourceHelper.getInputStream(path)) {
      String text = IOHelper.toString(inputStream, StandardCharsets.UTF_8);
      Expression expression = AviatorEvaluator.getInstance().compile(path, text, true);
      return (T) expression.execute(env);
    } catch (Exception e) {
      throw new CodeException("execute error:" + path, e);
    }
  }

  /**
   * @param path path
   * @param env  env
   * @param type type
   * @return T
   * @title executeFromFile
   * @description
   * @author BiJi'an
   * @date 2023-06-01 20:53
   */
  public <T> T executeByFile(final String path, final Map<String, Object> env, Class<T> type) {
    return ObjectValues.get(executeByFile(path, env), type);
  }

  @CAfter
  private void init() {
    AviatorEvaluator.addFunction(new StringToCamel());
    AviatorEvaluator.addFunction(new StringToLower());
  }
}

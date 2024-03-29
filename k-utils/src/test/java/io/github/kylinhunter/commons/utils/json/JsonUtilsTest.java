package io.github.kylinhunter.commons.utils.json;

import static org.mockito.ArgumentMatchers.any;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class JsonUtilsTest {

  private final TestBean testBean = new TestBean("keyValue", "snakeValue");

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  private static class TestBean {

    private String key;
    private String snakeTest;
  }

  @Test
  public void testReadToObject() {

    String text = "{\"key\":\"keyValue\",\"snake_test\":\"snake_yes\",\"snakeTest\":\"snake_no\"}";
    Assertions.assertEquals("keyValue", JsonUtils.readToObject(text, Map.class).get("key"));

    TestBean testBean = JsonUtils.readToObject(text, TestBean.class);
    Assertions.assertEquals("snake_no", testBean.getSnakeTest());

    testBean = JsonUtils.readToObject(text, TestBean.class, JsonOptions.FAIL_SNAKE);
    Assertions.assertNotNull(testBean);
    Assertions.assertEquals("snake_yes", testBean.getSnakeTest());

    testBean = JsonUtils.readToObject(text.getBytes(StandardCharsets.UTF_8), TestBean.class);
    Assertions.assertNotNull(testBean);
    Assertions.assertEquals("snake_no", testBean.getSnakeTest());

    testBean =
        JsonUtils.readToObject(
            text.getBytes(StandardCharsets.UTF_8), TestBean.class, JsonOptions.FAIL_SNAKE);
    Assertions.assertNotNull(testBean);
    Assertions.assertEquals("snake_yes", testBean.getSnakeTest());

    Assertions.assertThrows(KRuntimeException.class, () -> JsonUtils.readToObject("{", Map.class));

    Map<?, ?> map = JsonUtils.readToObject(text.getBytes(StandardCharsets.UTF_8), Map.class);
    Assertions.assertEquals("keyValue", map.get("key"));
    Assertions.assertThrows(
        KRuntimeException.class,
        () -> JsonUtils.readToObject("{".getBytes(StandardCharsets.UTF_8), Map.class));
  }

  @Test
  public void testWriteToString() throws KRuntimeException {
    String text = JsonUtils.writeToString(testBean);
    Assertions.assertEquals("snakeValue", JsonUtils.readToObject(text, Map.class).get("snakeTest"));
    text = JsonUtils.writeToString(testBean, JsonOptions.FAIL_SNAKE);
    Assertions.assertEquals(
        "snakeValue", JsonUtils.readToObject(text, Map.class).get("snake_test"));
    try (MockedStatic<ObjectMapperProxy> proxy = Mockito.mockStatic(ObjectMapperProxy.class)) {
      proxy.when(() -> ObjectMapperProxy.writeValueAsString(any(), any()))
          .thenThrow(new RuntimeException("mock writeValueAsString error"));
      Assertions.assertThrows(KRuntimeException.class, () -> JsonUtils.writeToString(testBean));

      Assertions.assertEquals("", JsonUtils.writeToString(testBean, JsonOptions.NO_FAIL));
    }
  }

  @Test
  public void testWriteToBytes() {
    Assertions.assertTrue(JsonUtils.wirteToBytes(testBean).length > 0);

    try (MockedStatic<ObjectMapperProxy> proxy = Mockito.mockStatic(ObjectMapperProxy.class)) {
      proxy.when(() -> ObjectMapperProxy.writeValueAsBytes(any(), any()))
          .thenThrow(new RuntimeException("mock writeValueAsBytes error"));

      Assertions.assertThrows(
          KRuntimeException.class, () -> JsonUtils.wirteToBytes(testBean, JsonOptions.DEFAULT));

      Assertions.assertEquals(0, JsonUtils.wirteToBytes(testBean, JsonOptions.NO_FAIL).length);
    }


  }

  @Test
  public void testReadToMap() throws KRuntimeException {
    String text = JsonUtils.writeToString(testBean);
    Assertions.assertEquals("keyValue", JsonUtils.readToMap(text).get("key"));
  }

  @Test
  public void testReadToListObject() {

    String text1 = JsonUtils.writeToString(ListUtils.newArrayList(testBean, testBean));
    Assertions.assertEquals(
        "keyValue", JsonUtils.readToListObject(text1, Map.class).get(0).get("key"));
    Assertions.assertEquals(
        "keyValue", JsonUtils.readToListObject(text1, Map.class).get(1).get("key"));

    String text2 = JsonUtils.writeToString(ListUtils.newArrayList(testBean, testBean));
    System.out.println("text:" + text2);
    List<TestBean> testBeans = JsonUtils.readToListObject(text2, TestBean.class);
    System.out.println("testBeans:" + testBeans);

    Assertions.assertEquals("keyValue", testBeans.get(0).getKey());
    Assertions.assertEquals("keyValue", testBeans.get(1).getKey());
  }
}

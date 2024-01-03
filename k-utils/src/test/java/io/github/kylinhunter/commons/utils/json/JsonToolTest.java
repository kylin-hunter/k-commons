package io.github.kylinhunter.commons.utils.json;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JsonToolTest {

  private final TestBean TEST_BEAN = new TestBean("keyValue", "snakeValue");
  private final String TEST_JSON = "{\"key\":\"keyValue\","
      + "\"snake_test\":\"snake_yes\",\"snakeTest\":\"snake_no\"}";


  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  @EqualsAndHashCode
  private static class TestBean {

    private String key;
    private String snakeTest;
  }


  @Test
  public void testReadToObject() {
    JsonTool jsonToolDefault = JsonTool.builder().build();

    Assertions.assertEquals("keyValue",
        jsonToolDefault.readToObject(TEST_JSON, Map.class).get("key"));

    TestBean testBean = jsonToolDefault.readToObject(TEST_JSON, TestBean.class);
    Assertions.assertEquals("snake_no", testBean.getSnakeTest());

    JsonTool jsonToolSnake = JsonTool.builder().snake(true).build();
    testBean = jsonToolSnake.readToObject(TEST_JSON, TestBean.class);
    Assertions.assertEquals("snake_yes", testBean.getSnakeTest());

    // test bytes
    testBean = jsonToolDefault.readToObject(TEST_JSON.getBytes(StandardCharsets.UTF_8),
        TestBean.class);
    Assertions.assertNotNull(testBean);
    Assertions.assertEquals("snake_no", testBean.getSnakeTest());

    jsonToolSnake = JsonTool.builder().snake(true).build();
    testBean = jsonToolSnake.readToObject(TEST_JSON.getBytes(StandardCharsets.UTF_8),
        TestBean.class);
    Assertions.assertEquals("snake_yes", testBean.getSnakeTest());

    Assertions.assertThrows(KRuntimeException.class,
        () -> jsonToolDefault.readToObject("{", Map.class));

    Assertions.assertThrows(
        KRuntimeException.class,
        () -> jsonToolDefault.readToObject("{".getBytes(StandardCharsets.UTF_8), Map.class));
  }


  @Test
  public void testReadToMap() throws KRuntimeException {
    JsonTool jsonTool = JsonTool.get();
    Assertions.assertEquals("keyValue", jsonTool.readToMap(TEST_JSON).get("key"));
  }

  @Test
  public void testReadToListObject() {
    String text = "[" + TEST_JSON + "," + TEST_JSON + "]";
    JsonTool jsonTool = JsonTool.get();
    System.out.println("text:" + text);
    List<TestBean> testBeans = jsonTool.readToListObject(text, TestBean.class);
    System.out.println("testBeans:" + testBeans);

    Assertions.assertEquals("keyValue", testBeans.get(0).getKey());
    Assertions.assertEquals("keyValue", testBeans.get(1).getKey());
  }


  @Test
  public void testWriteToString() throws KRuntimeException, JsonProcessingException {
    JsonTool jsonTool = JsonTool.get();
    String text = jsonTool.writeToString(TEST_BEAN);
    Assertions.assertEquals("snakeValue", jsonTool.readToObject(text, Map.class).get("snakeTest"));

    JsonTool jsonToolSnakeNoFail = JsonTool.builder().snake(true).throwIfFailed(false).build();
    text = jsonToolSnakeNoFail.writeToString(TEST_BEAN);
    Object snakeTest = jsonToolSnakeNoFail.readToObject(text, Map.class).get("snake_test");
    Assertions.assertEquals("snakeValue", snakeTest);

    ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);

    when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("mock error"));

    JsonTool jsonTool1 = JsonTool.builder().snake(true).throwIfFailed(true).build();
    ReflectUtils.setField(jsonTool1, "objectMapper", objectMapper);
    JsonTool jsonTool2 = JsonTool.builder().snake(true).throwIfFailed(false).build();
    ReflectUtils.setField(jsonTool2, "objectMapper", objectMapper);

    Assertions.assertThrows(KRuntimeException.class, () -> jsonTool1.writeToString(TEST_BEAN));

    Assertions.assertEquals("", jsonTool2.writeToString(TEST_BEAN));

  }

  @Test
  public void testWriteToBytes() throws JsonProcessingException {
    JsonTool jsonTool = JsonTool.get();
    Assertions.assertTrue(jsonTool.writetoBytes(TEST_BEAN).length > 0);

    ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);

    when(objectMapper.writeValueAsBytes(any())).thenThrow(new RuntimeException("mock error"));

    JsonTool jsonTool1 = JsonTool.builder().snake(true).throwIfFailed(true).build();
    ReflectUtils.setField(jsonTool1, "objectMapper", objectMapper);
    JsonTool jsonTool2 = JsonTool.builder().snake(true).throwIfFailed(false).build();
    ReflectUtils.setField(jsonTool2, "objectMapper", objectMapper);

    Assertions.assertThrows(KRuntimeException.class, () -> jsonTool1.writetoBytes(TEST_BEAN));

    Assertions.assertEquals(0, jsonTool2.writetoBytes(TEST_BEAN).length);


  }

  @Test
  public void testReadValue() {

    JsonTool jsonTool = JsonTool.builder().pretty(false).build();
    JavaType javaType = jsonTool.constructCollectionType(List.class, TestBean.class);
    List<TestBean> testBeans = ListUtils.newArrayList(TEST_BEAN, TEST_BEAN);
    String json = jsonTool.writeToString(testBeans);

    System.out.println("json:" + json);

    List<TestBean> testBeans2 = jsonTool.readToListObject(json, TestBean.class);
    System.out.println("testBeans2:" + testBeans2);
    Assertions.assertEquals(testBeans, testBeans2);

    List<TestBean> testBeans3 = jsonTool.readValue(json, javaType);
    System.out.println("testBeans3:" + testBeans3);
    Assertions.assertEquals(testBeans, testBeans3);

    List<TestBean> testBeans4 = jsonTool.readValue(json);
    System.out.println("testBeans4:" + testBeans4);
    Assertions.assertNotEquals(testBeans, testBeans4);

    JsonTool jsonToolAutType = JsonTool.builder().pretty(true).autoType(true).build();
    String jsonWithAutType = jsonToolAutType.writeToString(testBeans);

    System.out.println("jsonWithAutType:" + jsonWithAutType);

    List<TestBean> testBeans5 = jsonToolAutType.readValue(jsonWithAutType);
    System.out.println("testBeans5:" + testBeans5);
    Assertions.assertNotEquals(testBeans, testBeans4);
  }
}
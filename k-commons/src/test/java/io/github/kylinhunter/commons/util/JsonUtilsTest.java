package io.github.kylinhunter.commons.util;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.json.JsonOptions;
import io.github.kylinhunter.commons.json.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockit.Mock;
import mockit.MockUp;

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
                JsonUtils.readToObject(text.getBytes(StandardCharsets.UTF_8), TestBean.class, JsonOptions.FAIL_SNAKE);
        Assertions.assertNotNull(testBean);
        Assertions.assertEquals("snake_yes", testBean.getSnakeTest());

        Assertions.assertThrows(KRuntimeException.class, () -> JsonUtils.readToObject("{", Map.class));

        Map<?, ?> map = JsonUtils.readToObject(text.getBytes(StandardCharsets.UTF_8), Map.class);
        Assertions.assertEquals("keyValue", map.get("key"));
        Assertions.assertThrows(KRuntimeException.class,
                () -> JsonUtils.readToObject("{".getBytes(StandardCharsets.UTF_8), Map.class));

    }

    @Test
    public void testWriteToString() throws KRuntimeException {
        String text = JsonUtils.writeToString(testBean);
        Assertions.assertEquals("snakeValue", JsonUtils.readToObject(text, Map.class).get("snakeTest"));
        text = JsonUtils.writeToString(testBean, JsonOptions.FAIL_SNAKE);
        Assertions.assertEquals("snakeValue", JsonUtils.readToObject(text, Map.class).get("snake_test"));

        new MockUp<ObjectMapper>(ObjectMapper.class) {
            @Mock
            public String writeValueAsString(Object value) {
                throw new RuntimeException("mock writeValueAsString error");
            }
        };
        Assertions.assertThrows(KRuntimeException.class, () -> {
            JsonUtils.writeToString(testBean);
        });

        Assertions.assertEquals("", JsonUtils.writeToString(testBean, JsonOptions.NO_FAIL));
    }

    @Test
    public void testWriteToBytes() {
        Assertions.assertTrue(JsonUtils.wirteToBytes(testBean).length > 0);

        new MockUp<ObjectMapper>(ObjectMapper.class) {
            @Mock
            public byte[] writeValueAsBytes(Object value) {
                throw new RuntimeException("mock writeValueAsBytes error");
            }
        };

        Assertions.assertThrows(KRuntimeException.class, () -> {
            JsonUtils.wirteToBytes(testBean, JsonOptions.DEFAULT);
        });

        Assertions.assertEquals(0, JsonUtils.wirteToBytes(testBean, JsonOptions.NO_FAIL).length);

    }

    @Test
    public void testToMap() throws KRuntimeException {
        String text = JsonUtils.writeToString(testBean);
        Assertions.assertEquals("keyValue", JsonUtils.readToMap(text).get("key"));
    }

    @Test
    public void testToListMap() throws KRuntimeException {
        String text = JsonUtils.writeToString(Lists.newArrayList(testBean, testBean));
        Assertions.assertEquals("keyValue",
                JsonUtils.toList(text, Map.class, JsonOptions.DEFAULT).get(0).get("key"));
        Assertions.assertEquals("keyValue",
                JsonUtils.toList(text, Map.class, JsonOptions.DEFAULT).get(1).get("key"));
    }

    @Test
    public void testToLis() throws KRuntimeException, JsonProcessingException {
        String text = JsonUtils.writeToString(Lists.newArrayList(testBean));
        System.out.println("text:" + text);
        List<TestBean> testBeans = JsonUtils.toList(text, TestBean.class, JsonOptions.DEFAULT);
        System.out.println("testBeans:" + testBeans);

        Assertions.assertEquals(true, testBeans.get(0) instanceof TestBean);
    }

}

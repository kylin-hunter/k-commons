package com.kylinhunter.plat.commons.util;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.kylinhunter.plat.commons.exception.common.KRuntimeException;

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
    public void testToObject() {

        String text = "{\"key\":\"keyValue\",\"snake_test\":\"snake_yes\",\"snakeTest\":\"snake_no\"}";
        Assertions.assertEquals("keyValue", JsonUtils.toObject(text, Map.class).get("key"));
        Assertions.assertEquals("snake_no", Objects
                .requireNonNull(JsonUtils.toObject(text, TestBean.class, false, true)).getSnakeTest());
        Assertions.assertEquals("snake_yes", JsonUtils.toObject(text, TestBean.class, true, true).getSnakeTest());

        Assertions.assertThrows(KRuntimeException.class, () -> JsonUtils.toObject("{", Map.class));

        Assertions.assertEquals("keyValue",
                JsonUtils.toObject(text.getBytes(StandardCharsets.UTF_8), Map.class).get("key"));

        Assertions.assertEquals("snake_no",
                Objects.requireNonNull(
                        JsonUtils.toObject(text.getBytes(StandardCharsets.UTF_8), TestBean.class, false, true))
                        .getSnakeTest());
        Assertions.assertEquals("snake_yes",
                Objects.requireNonNull(
                        JsonUtils.toObject(text.getBytes(StandardCharsets.UTF_8), TestBean.class, true, true))
                        .getSnakeTest());

        Assertions.assertThrows(KRuntimeException.class,
                () -> JsonUtils.toObject("{".getBytes(StandardCharsets.UTF_8), Map.class));

    }

    @Test
    public void testToBytes() {
        byte[] bytes = JsonUtils.toBytes(testBean);
        Assertions.assertEquals("keyValue", JsonUtils.toObject(bytes, TestBean.class).getKey());

        new MockUp<ObjectMapper>(ObjectMapper.class) {
            @Mock
            public byte[] writeValueAsBytes(Object value) {
                throw new RuntimeException("mock writeValueAsBytes error");
            }
        };

        Assertions.assertThrows(KRuntimeException.class, () -> {
            byte[] toBytes = JsonUtils.toBytes(testBean, true);
        });
    }

    @Test
    public void testToString() throws KRuntimeException {
        String text = JsonUtils.toString(testBean);
        Assertions.assertEquals("snakeValue", JsonUtils.toObject(text, Map.class).get("snakeTest"));
        text = JsonUtils.toString(testBean, true, true);
        Assertions.assertEquals("snakeValue", JsonUtils.toObject(text, Map.class).get("snake_test"));

        new MockUp<ObjectMapper>(ObjectMapper.class) {
            @Mock
            public String writeValueAsString(Object value) {
                throw new RuntimeException("mock writeValueAsString error");
            }
        };
        Assertions.assertThrows(KRuntimeException.class, () -> {
            JsonUtils.toString(testBean, true);
        });

        Assertions.assertEquals("", JsonUtils.toString(testBean, false));
    }

    @Test
    public void testToMap() throws KRuntimeException {
        String text = JsonUtils.toString(testBean);
        Assertions.assertEquals("keyValue", JsonUtils.toMap(text).get("key"));
    }

    @Test
    public void testToListMap() throws KRuntimeException {
        String text = JsonUtils.toString(Lists.newArrayList(testBean));
        Assertions.assertEquals("keyValue",
                Objects.requireNonNull(JsonUtils.toList(text, Map.class, true)).get(0).get("key"));
    }

    @Test
    public void testToLis() throws KRuntimeException, JsonProcessingException {
        String text = JsonUtils.toString(Lists.newArrayList(testBean));
        System.out.println("text:" + text);
        List<TestBean> testBeans = JsonUtils.toList(text, TestBean.class, false);
        System.out.println("testBeans:" + testBeans);

        Assertions.assertEquals(true, testBeans.get(0) instanceof TestBean);
    }

}

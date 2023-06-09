package o.github.kylinhunter.commons.utils.bean.info;

import java.util.Map;
import o.github.kylinhunter.commons.utils.properties.data.Father;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BeanIntrospectorsTest {

  @Test
  void testGet() {
    BeanIntrospector beanIntrospector = BeanIntrospectors.get(Father.class);
    System.out.println("getPropertyDescriptors=Father");

    Map<String, ExPropertyDescriptor> propertyDescriptors =
        beanIntrospector.getExPropertyDescriptors();

    propertyDescriptors.forEach(
        (k, v) ->
            System.out.println(k + ":" + v.getReadMethod().getName() + ":" + v.isCanReadWrite()));
    Assertions.assertEquals(5, propertyDescriptors.size());
  }
}

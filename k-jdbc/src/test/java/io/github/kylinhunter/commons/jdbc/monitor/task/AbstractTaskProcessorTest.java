package io.github.kylinhunter.commons.jdbc.monitor.task;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.jdbc.monitor.bean.Config;
import io.github.kylinhunter.commons.jdbc.monitor.bean.Table;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AbstractTaskProcessorTest {

  @Test
  void test() {
    TestProcessor t = new TestProcessor();

    TableTaskManager taskManager = Mockito.mock(TableTaskManager.class);
    ReflectUtils.setField(t, "taskManager", taskManager);
    ReflectUtils.setField(t, "config", new Config());
    List<Table> tables = ListUtils.newArrayList(new ScanTable());
    ReflectUtils.setField(t, "tables", tables);
    t.setExecCallback(new TestCallback());
    t.reset();
    t.start();
    t.shutdown();
  }


  static class TestProcessor extends AbstractTaskProcessor {

  }
}
package io.github.kylinhunter.commons.jdbc.monitor.task;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.jdbc.monitor.bean.Config;
import io.github.kylinhunter.commons.jdbc.monitor.bean.Table;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AbstractTaskProcessorTest {

  @Test
  void test() {
    TestProcessor processor = new TestProcessor();

    TableTaskManager taskManager = Mockito.mock(TableTaskManager.class);
    ReflectUtils.setField(processor, "taskManager", taskManager);
    ReflectUtils.setField(processor, "config", new Config());
    List<Table> tables = ListUtils.newArrayList(new ScanTable());
    ReflectUtils.setField(processor, "tables", tables);
    processor.setRowListener(new TestRowListener());
    processor.reset();
    processor.setScheduler(Mockito.mock(ScheduledExecutorService.class));
    processor.start();
  }


  static class TestProcessor extends AbstractTaskProcessor {


  }
}
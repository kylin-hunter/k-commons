package io.github.kylinhunter.commons.jdbc.binlog;


import com.github.shyiko.mysql.binlog.BinaryLogClient.EventListener;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

/**
 * @author BiJi'an
 * @description EventListener
 * @date 2023-11-25 02:56
 */
public class DefaultBinLogEventListener implements EventListener {

  @Override
  public void onEvent(Event event) {
    EventData data = event.getData();
    if (data instanceof TableMapEventData) {
      //只要连接的MySQL发生的增删改的操作，则都会进入这里，无论哪个数据库

      TableMapEventData tableMapEventData = (TableMapEventData) data;

      //可以通过转成TableMapEventData类实例的tableMapEventData来获取当前发生变更的数据库
      System.out.println("发生变更的数据库：" + tableMapEventData.getDatabase());

      System.out.print("TableID:");
      //表ID
      System.out.println(tableMapEventData.getTableId());
      System.out.print("TableName:");
      //表名字
      System.out.println(tableMapEventData.getTable());
    }
    //表数据发生修改时触发
    if (data instanceof UpdateRowsEventData) {
      System.out.println("Update:");
      System.out.println(data.toString());
      //表数据发生插入时触发
    } else if (data instanceof WriteRowsEventData) {
      System.out.println("Insert:");
      System.out.println(data.toString());
      //表数据发生删除后触发
    } else if (data instanceof DeleteRowsEventData) {
      System.out.println("Delete:");
      System.out.println(data.toString());
    }
  }
}
package io.github.kylinhunter.commons.jdbc.datasource;

import static org.mockito.ArgumentMatchers.any;

import io.github.kylinhunter.commons.reflect.ObjectCreator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class DataSourceManagerTest {

  @Test
  void init() {

    MockedStatic<ObjectCreator> mock = Mockito.mockStatic(ObjectCreator.class);
    mock.when(
            () -> ObjectCreator.create(Mockito.any(Class.class), any(Class[].class),
                any(Object[].class)))
        .thenReturn(new ExDataSource() {

          @Override
          public <T> T unwrap(Class<T> iface) {
            return null;
          }

          @Override
          public boolean isWrapperFor(Class<?> iface) {
            return false;
          }

          @Override
          public Connection getConnection() {
            return null;
          }

          @Override
          public Connection getConnection(String username, String password) {
            return null;
          }

          @Override
          public PrintWriter getLogWriter() {
            return null;
          }

          @Override
          public void setLogWriter(PrintWriter out) {

          }

          @Override
          public void setLoginTimeout(int seconds) {

          }

          @Override
          public int getLoginTimeout() {
            return 0;
          }

          @Override
          public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return null;
          }

          @Override
          public void close() throws IOException {

          }

          @Override
          public String getDsName() {
            return null;
          }

          @Override
          public void setDsName(String name) {

          }

          @Override
          public int getDsNo() {
            return 0;
          }

          @Override
          public void setDsNo(int no) {

          }
        });
    DataSourceManager manager = new DataSourceManager(false);
    manager.init();
    mock.close();

  }
}
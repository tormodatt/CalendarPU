/**
 * 
 */
package com.zaxxer.hikari;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.zaxxer.hikari.mocks.MockDataSource;
import com.zaxxer.hikari.util.UtilityElf;

/**
 * Test for cases when db network connectivity goes down and close is called on existing connections. By default Hikari
 * blocks longer than getMaximumTimeout (it can hang for a lot of time depending on driver timeout settings). Closing
 * async the connections fixes this issue.
 *
 */
public class TestConnectionCloseBlocking {
   private volatile boolean shouldSleep = true;

   @Test
   public void testConnectionCloseBlocking() throws SQLException {
      HikariConfig config = new HikariConfig();
      config.setMinimumIdle(0);
      config.setMaximumPoolSize(1);
      config.setConnectionTimeout(1500);
      config.setDataSource(new CustomMockDataSource());

      HikariDataSource ds = new HikariDataSource(config);

      long start = System.currentTimeMillis();
      try {
         Connection connection = ds.getConnection();
         connection.close();
         // Hikari only checks for validity for connections with lastAccess > 1000 ms so we sleep for 1001 ms to force
         // Hikari to do a connection validation which will fail and will trigger the connection to be closed
         UtilityElf.quietlySleep(1001);
         start = System.currentTimeMillis();
         connection = ds.getConnection(); // on physical connection close we sleep 2 seconds
         Assert.assertTrue("Waited longer than timeout",
               (UtilityElf.elapsedTimeMs(start) < config.getConnectionTimeout()));
      } catch (SQLException e) {
         Assert.assertTrue("getConnection failed because close connection took longer than timeout",
               (UtilityElf.elapsedTimeMs(start) < config.getConnectionTimeout()));
      } finally {
         shouldSleep = false;
         ds.close();
      }
   }

   private class CustomMockDataSource extends MockDataSource {
      @Override
      public Connection getConnection() throws SQLException {
         Connection mockConnection = super.getConnection();
         when(mockConnection.isValid(anyInt())).thenReturn(false);
         doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
               if (shouldSleep) {
                  TimeUnit.SECONDS.sleep(2);
               }
               return null;
            }
         }).when(mockConnection).close();
         return mockConnection;
      }
   }

}

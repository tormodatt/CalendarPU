/*
 * Copyright (C) 2013, 2014 Brett Wooldridge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zaxxer.hikari;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.zaxxer.hikari.util.UtilityElf;

/**
 * Test HikariCP/CodaHale metrics integration.
 *
 * @author Brett Wooldridge
 */
public class TestMetrics
{
   @Test
   public void testMetricWait() throws SQLException
   {
      MetricRegistry metricRegistry = new MetricRegistry();

      HikariConfig config = new HikariConfig();
      config.setMinimumIdle(1);
      config.setMaximumPoolSize(1);
      config.setMetricRegistry(metricRegistry);
      config.setInitializationFailFast(false);
      config.setPoolName("test");
      config.setDataSourceClassName("com.zaxxer.hikari.mocks.StubDataSource");

      HikariDataSource ds = new HikariDataSource(config);
      try {
         ds.getConnection().close();

         Timer timer = metricRegistry.getTimers(new MetricFilter() {
            /** {@inheritDoc} */
            @Override
            public boolean matches(String name, Metric metric)
            {
               return "test.pool.Wait".equals(MetricRegistry.name("test", "pool", "Wait"));
            }
         }).values().iterator().next();

         Assert.assertEquals(1, timer.getCount());
         Assert.assertTrue(timer.getMeanRate() > 0.0);
      }
      finally {
         ds.close();
      }
   }

   @Test
   public void testMetricUsage() throws SQLException
   {
      MetricRegistry metricRegistry = new MetricRegistry();

      HikariConfig config = new HikariConfig();
      config.setMinimumIdle(1);
      config.setMaximumPoolSize(1);
      config.setMetricRegistry(metricRegistry);
      config.setInitializationFailFast(false);
      config.setPoolName("test");
      config.setDataSourceClassName("com.zaxxer.hikari.mocks.StubDataSource");

      HikariDataSource ds = new HikariDataSource(config);
      try {
         Connection connection = ds.getConnection();
         UtilityElf.quietlySleep(250L);
         connection.close();

         Histogram histo = metricRegistry.getHistograms(new MetricFilter() {
            /** {@inheritDoc} */
            @Override
            public boolean matches(String name, Metric metric)
            {
               return "test.pool.Usage".equals(MetricRegistry.name("test", "pool", "Usage"));
            }
         }).values().iterator().next();

         Assert.assertEquals(1, histo.getCount());
         double seventyFifth = histo.getSnapshot().get75thPercentile();
         Assert.assertTrue("Seventy-fith percentile less than 250ms: " + seventyFifth, seventyFifth >= 250.0);
      }
      finally {
         ds.close();
      }
   }

   @Test
   public void testHealthChecks() throws Exception
   {
      MetricRegistry metricRegistry = new MetricRegistry();
      HealthCheckRegistry healthRegistry = new HealthCheckRegistry();

      HikariConfig config = new HikariConfig();
      config.setMaximumPoolSize(10);
      config.setMetricRegistry(metricRegistry);
      config.setHealthCheckRegistry(healthRegistry);
      config.setPoolName("test");
      config.setDataSourceClassName("com.zaxxer.hikari.mocks.StubDataSource");
      config.addHealthCheckProperty("connectivityCheckTimeoutMs", "1000");
      config.addHealthCheckProperty("expected99thPercentileMs", "10");

      HikariDataSource ds = new HikariDataSource(config);
      try {
         UtilityElf.quietlySleep(TimeUnit.SECONDS.toMillis(2));

         Connection connection = ds.getConnection();
         connection.close();

         connection = ds.getConnection();
         connection.close();

         SortedMap<String, Result> healthChecks = healthRegistry.runHealthChecks();

         Result connectivityResult = healthChecks.get("test.pool.ConnectivityCheck");
         Assert.assertTrue(connectivityResult.isHealthy());

         Result slaResult = healthChecks.get("test.pool.Connection99Percent");
         Assert.assertTrue(slaResult.isHealthy());
      }
      finally {
         ds.close();
      }
   }
}

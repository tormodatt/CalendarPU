/*
 * Copyright (C) 2014 Brett Wooldridge
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
package com.zaxxer.hikari.pool;

import java.sql.Connection;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.zaxxer.hikari.util.IConcurrentBagEntry;

/**
 * Entry used in the ConcurrentBag to track Connection instances.
 *
 * @author Brett Wooldridge
 */
public final class PoolBagEntry implements IConcurrentBagEntry
{
   public final AtomicInteger state = new AtomicInteger();

   public final Connection connection;
   public long lastOpenTime;
   public volatile boolean evicted;
   public volatile boolean aborted;
   
   protected long lastAccess;

   private volatile ScheduledFuture<?> endOfLife;

   public PoolBagEntry(final Connection connection, final BaseHikariPool pool) {
      this.connection = connection;
      this.lastAccess = System.currentTimeMillis();

      final long maxLifetime = pool.configuration.getMaxLifetime();
      if (maxLifetime > 0) {
         endOfLife = pool.houseKeepingExecutorService.schedule(new Runnable() {
            public void run()
            {
               // If we can reserve it, close it
               if (pool.connectionBag.reserve(PoolBagEntry.this)) {
                  pool.closeConnection(PoolBagEntry.this);
               }
               else {
                  // else the connection is "in-use" and we mark it for eviction by pool.releaseConnection() or the housekeeper
                  PoolBagEntry.this.evicted = true;
               }
            }
         }, maxLifetime, TimeUnit.MILLISECONDS);
      }
   }

   void cancelMaxLifeTermination()
   {
      if (endOfLife != null) {
         endOfLife.cancel(false);
      }
   }


   /** {@inheritDoc} */
   @Override
   public AtomicInteger state()
   {
      return state;
   }

   @Override
   public String toString()
   {
      return "Connection......" + connection + "\n"
           + "  Last  access.." + lastAccess + "\n"
           + "  Last open....." + lastOpenTime + "\n"
           + "  State........." + stateToString();
   }

   private String stateToString()
   {
      switch (state.get()) {
      case STATE_IN_USE:
         return "IN_USE";
      case STATE_NOT_IN_USE:
         return "NOT_IN_USE";
      case STATE_REMOVED:
         return "REMOVED";
      case STATE_RESERVED:
         return "RESERVED";
      default:
         return "Invalid";
      }
   }
}

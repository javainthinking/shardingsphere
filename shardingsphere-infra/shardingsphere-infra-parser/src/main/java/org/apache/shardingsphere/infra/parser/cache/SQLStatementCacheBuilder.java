/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.parser.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.infra.config.properties.ConfigurationProperties;
import org.apache.shardingsphere.infra.config.properties.ConfigurationPropertyKey;
import org.apache.shardingsphere.sql.parser.sql.common.statement.SQLStatement;

/**
 * SQL statement cache builder.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SQLStatementCacheBuilder {
    
    /**
     * Build SQL statement cache.
     *
     * @param props configuration props
     * @param databaseType database type
     * @return built SQL statement cache
     */
    public static LoadingCache<String, SQLStatement> build(final ConfigurationProperties props, final String databaseType) {
        int initialCapacity = props.getValue(ConfigurationPropertyKey.SQL_CACHE_INITIAL_CAPACITY);
        long maximumSize = props.getValue(ConfigurationPropertyKey.SQL_CACHE_MAXIMUM_SIZE);
        int concurrencyLevel = props.getValue(ConfigurationPropertyKey.SQL_CACHE_CONCURRENCY_LEVEL);
        boolean sqlCommentParseEnabled = props.getValue(ConfigurationPropertyKey.SQL_COMMENT_PARSE_ENABLED);
        return CacheBuilder.newBuilder().softValues().initialCapacity(initialCapacity).maximumSize(maximumSize)
                .concurrencyLevel(concurrencyLevel).build(new SQLStatementCacheLoader(databaseType, sqlCommentParseEnabled));
    }
}

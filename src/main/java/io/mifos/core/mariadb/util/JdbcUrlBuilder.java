/*
 * Copyright 2017 The Mifos Initiative.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mifos.core.mariadb.util;

public final class JdbcUrlBuilder {

  private final DatabaseType type;
  private String host;
  private String port;
  private String instanceName;

  private JdbcUrlBuilder(final DatabaseType type) {
    super();
    this.type = type;
  }

  public static JdbcUrlBuilder create(final DatabaseType type) {
    return new JdbcUrlBuilder(type);
  }

  public JdbcUrlBuilder host(final String host) {
    this.host = host;
    return this;
  }

  public JdbcUrlBuilder port(final String port) {
    this.port = port;
    return this;
  }

  public JdbcUrlBuilder instanceName(final String instanceName) {
    this.instanceName = instanceName;
    return this;
  }

  public String build() {
    final String[] hostList = this.host.split(",");
    switch (this.type) {
      case MARIADB:
        final StringBuilder jdbcUrl = new StringBuilder();
        final String jdbcProtocol = this.type.prefix() + (hostList.length > 1 ? "replication://" : "//");
        jdbcUrl.append(jdbcProtocol);
        for (int i = 0; i < hostList.length; i++) {
          jdbcUrl.append(hostList[i].trim()).append(":").append(this.port);
          if ((i + 1) < hostList.length) {
            jdbcUrl.append(",");
          }
        }
        if (this.instanceName != null) {
          jdbcUrl.append("/").append(this.instanceName);
        }
        return jdbcUrl.toString();
      default:
        throw new IllegalArgumentException("Unknown database type '" + this.type.name() + "'");
    }
  }

  public enum DatabaseType {
    MARIADB("jdbc:mariadb:");

    private final String prefix;

    DatabaseType(final String prefix) {
      this.prefix = prefix;
    }

    String prefix() {
      return this.prefix;
    }
  }
}

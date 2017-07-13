/*
 * Copyright (C) 2016-2017 Nicolai Ehemann (en@enlightened.de).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.enlightened.maven.plugin.sqlenumgen.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Nicolai Ehemann
 */
public class JDBCCfg {

  /**
   * The database URL (required).
   * @since 0.0.1
   */
  private String url;

  /**
   * The database user.
   * @since 0.0.1
   */
  private String user;

  /**
   * The database password.
   * @since 0.0.1
   */
  private String password;

  public JDBCCfg() {
  }

  public final String getUrl() {
    return this.url;
  }

  public final void setUrl(final String url) {
    this.url = url;
  }

  public final String getUser() {
    return this.user;
  }

  public final void setUser(final String user) {
    this.user = user;
  }

  public final String getPassword() {
    return this.password;
  }

  public final void setPassword(final String password) {
    this.password = password;
  }

  public final Connection getConnection() throws SQLException {
    final Connection connection;

    if (this.user != null) {
      connection = DriverManager.getConnection(this.url, this.user, this.password);
    } else {
      connection = DriverManager.getConnection(this.url);
    }
    return connection;
  }
}

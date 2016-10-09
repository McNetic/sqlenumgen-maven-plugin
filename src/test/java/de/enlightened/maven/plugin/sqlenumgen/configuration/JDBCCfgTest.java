/*
 * Copyright (C) 2016 Nicolai Ehemann (en@enlightened.de).
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
import java.sql.SQLException;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Nicolai Ehemann
 */
public class JDBCCfgTest {

  public static final String JDBC_URL = "jdbc:h2:mem:testjdbc";

  private JDBCCfg jdbc;

  @Before
  public void setUp() throws SQLException, ClassNotFoundException{
    Class.forName("org.h2.Driver");
    jdbc = new JDBCCfg();
  }

  @Test
  public void testGetConnectionWithoutUser() throws Exception {
    this.jdbc.setUrl("jdbc:h2:mem:jdbc1");
    try (Connection connection = this.jdbc.getConnection()) {
      assertNotNull("Connection created", connection);
      assertFalse("Connection connected", connection.isClosed());
    }
  }

  @Test
  public void testGetConnectionWithUser() throws Exception {
    this.jdbc.setUrl("jdbc:h2:mem:jdbc1");
    this.jdbc.setUser("username");
    try (Connection connection = this.jdbc.getConnection()) {
      assertNotNull("Connection created", connection);
      assertFalse("Connection connected", connection.isClosed());
    }
  }

  @Test
  public void testGetterSetter() {
    final String url = "url";
    this.jdbc.setUrl(url);
    assertEquals("url is equal", url, this.jdbc.getUrl());

    final String user = "user";
    this.jdbc.setUser(user);
    assertEquals("user is equal", user, this.jdbc.getUser());

    final String password = "password";
    this.jdbc.setPassword(password);
    assertEquals("password is equal", password, this.jdbc.getPassword());
  }
}

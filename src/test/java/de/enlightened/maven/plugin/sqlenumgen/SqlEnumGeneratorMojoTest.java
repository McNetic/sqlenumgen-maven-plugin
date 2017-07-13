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
package de.enlightened.maven.plugin.sqlenumgen;

import com.google.common.jimfs.Jimfs;
import static de.enlightened.maven.plugin.sqlenumgen.SqlEnumGeneratorMojo.VELOCITY_TEMPLATE;
import de.enlightened.maven.plugin.sqlenumgen.configuration.Configuration;
import de.enlightened.maven.plugin.sqlenumgen.configuration.DatabaseCfg;
import de.enlightened.maven.plugin.sqlenumgen.configuration.EnumCfg;
import de.enlightened.maven.plugin.sqlenumgen.util.Column;
import de.enlightened.maven.plugin.sqlenumgen.util.SqlType;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 *
 * @author Nicolai Ehemann
 */
@RunWith(PowerMockRunner.class)
public class SqlEnumGeneratorMojoTest extends AbstractMojoTestCase {

  public static final String JDBC_URL_CLOSE_DELAY = ";DB_CLOSE_DELAY=-1";
  public static final String JDBC_URL = "jdbc:h2:mem:test";
  private static final String JDBC_NO_DRIVER = "no driver";
  private static final String JDBC_NON_INSTANTIABLE_DRIVER = "java.lang.Class";
  private final static String EXPECTED_ENUM_PATH = "src/test/resources/expectedEnum.java";

  @SuppressWarnings("checkstyle:constantname")
  private final static FileSystem fileSystem = Jimfs.newFileSystem();

  private SqlEnumGeneratorMojo mojo;

  private Configuration configuration;

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    super.setUp();
    Class.forName("org.h2.Driver");
  }

  @Test
  public void testExecute() throws MojoFailureException, SQLException, IOException, IllegalAccessException {
    setupInMemoryMojo();

    DriverManager.getConnection(JDBC_URL + ";INIT=RUNSCRIPT FROM './src/test/resources/test.init.sql'" + JDBC_URL_CLOSE_DELAY);

    configuration.getJdbc().setUrl(JDBC_URL + JDBC_URL_CLOSE_DELAY);
    mojo.setConfiguration(configuration);

    final DatabaseCfg database = new DatabaseCfg();
    final EnumCfg enm = new EnumCfg();
    enm.setName("testEnum");
    enm.setTable("TEST");
    database.addEnum(enm);
    configuration.getGenerator().setDatabase(database);

    mojo.execute();

    final Path generatedEnumPath = SqlEnumGeneratorMojoTest.fileSystem.getPath(
        SqlEnumGeneratorMojo.DEFAULT_OUTPUT_DIRECTORY,
        SqlEnumGeneratorMojo.DEFAULT_PACKAGE.replace(".", "/"),
        "testEnum.java")
        .toAbsolutePath();
    final String generatedEnumClass = new String(Files.readAllBytes(generatedEnumPath));
    final Path expectedEnumPath = FileSystems.getDefault()
        .getPath(EXPECTED_ENUM_PATH).toAbsolutePath();
    final String expectedEnumClass = new String(Files.readAllBytes(expectedEnumPath));

    final int beforeVersionPosExpected = expectedEnumClass.indexOf("sqlenumgen version") + 18;
    final int afterVersionPosExpected = beforeVersionPosExpected + 7;
    final int beforeDatePosExpected = expectedEnumClass.indexOf("date = ") + 7;
    final int afterDatePosExpected = beforeDatePosExpected + 26;
    final int beforeVersionPosGenerated = generatedEnumClass.indexOf("sqlenumgen version") + 18;
    final int afterVersionPosGenerated = beforeVersionPosGenerated + SqlEnumGeneratorMojo.CONFIGURATION.PROJECT_VERSION.length() + 2;
    final int beforeDatePosGenerated = generatedEnumClass.indexOf("date = ") + 7;
    final int afterDatePosGenerated = beforeDatePosGenerated + 26;
    assertEquals("Generated enum class",
        expectedEnumClass.substring(0, beforeVersionPosExpected)
            + expectedEnumClass.substring(afterVersionPosExpected, beforeDatePosExpected)
            + expectedEnumClass.substring(afterDatePosExpected),
        generatedEnumClass.substring(0, beforeVersionPosGenerated)
            + generatedEnumClass.substring(afterVersionPosGenerated, beforeDatePosGenerated)
            + generatedEnumClass.substring(afterDatePosGenerated));
    assertEquals("Generated enum sqlenumgen version",
        SqlEnumGeneratorMojo.CONFIGURATION.PROJECT_VERSION,
        generatedEnumClass.substring(beforeVersionPosGenerated + 1, afterVersionPosGenerated - 1));
    assertTrue("Generated enum date",
        generatedEnumClass.substring(beforeDatePosGenerated + 1, afterDatePosGenerated - 1)
            .matches("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z"));
  }

  @Test
  public void testExecuteFailDBError() throws Exception {
    setupInMemoryMojo();
    mojo.setConfiguration(configuration);

    final DatabaseCfg database = new DatabaseCfg();
    final EnumCfg enm = new EnumCfg();
    enm.setName("testEnum");
    enm.setTable("TEST");
    database.addEnum(enm);
    configuration.getGenerator().setDatabase(database);

    exception.expect(MojoFailureException.class);
    exception.expectMessage("Mojo execution failed due to database error (The url cannot be null).");
    mojo.execute();
  }

  @Test
  public void testExecuteFailDriverError() throws Exception {
    setupInMemoryMojo();

    configuration.getJdbc().setUrl(JDBC_URL);
    configuration.getJdbc().setDriver(JDBC_NO_DRIVER);
    mojo.setConfiguration(configuration);

    final DatabaseCfg database = new DatabaseCfg();
    final EnumCfg enm = new EnumCfg();
    enm.setName("testEnum");
    enm.setTable("TEST");
    database.addEnum(enm);
    configuration.getGenerator().setDatabase(database);

    exception.expect(MojoFailureException.class);
    exception.expectMessage("Mojo execution failed due to missing database driver (" + JDBC_NO_DRIVER + ").");
    mojo.execute();
  }

  @Test
  public void testExecuteFailDriverInstantiationError() throws Exception {
    setupInMemoryMojo();

    configuration.getJdbc().setUrl(JDBC_URL);
    configuration.getJdbc().setDriver(JDBC_NON_INSTANTIABLE_DRIVER);
    mojo.setConfiguration(configuration);

    final DatabaseCfg database = new DatabaseCfg();
    final EnumCfg enm = new EnumCfg();
    enm.setName("testEnum");
    enm.setTable("TEST");
    database.addEnum(enm);
    configuration.getGenerator().setDatabase(database);

    exception.expect(MojoFailureException.class);
    exception.expectMessage("Mojo execution failed due to database driver instantiation failure (Can not call newInstance() on the Class for " + JDBC_NON_INSTANTIABLE_DRIVER + ").");
    mojo.execute();
  }

  @Test
  public void testcompleteEnumCfgFromColumns1() throws Exception {
    mojo = new SqlEnumGeneratorMojo();

    final EnumCfg enumCfgIn = new EnumCfg();
    enumCfgIn.setName("TestEnum");
    final LinkedMap<String, Column> columns = new LinkedMap<>();
    columns.put("name", new Column("name", SqlType.VARCHAR));
    final EnumCfg actualEnumCfg = (EnumCfg) Whitebox.invokeMethod(mojo, "completeEnumCfgFromColumns", enumCfgIn, columns);

    final EnumCfg expectedEnumCfg = new EnumCfg();
    expectedEnumCfg.setName("TestEnum");
    expectedEnumCfg.setValueColumn("name");

    assertEquals("enumCfg is returned", expectedEnumCfg, actualEnumCfg);
  }

  @Test
  public void testcompleteEnumCfgFromColumns2() throws Exception {
    mojo = new SqlEnumGeneratorMojo();

    final EnumCfg enumCfgIn = new EnumCfg();
    enumCfgIn.setName("TestEnum");
    final LinkedMap<String, Column> columns = new LinkedMap<>();
    columns.put("id", new Column("id", SqlType.INTEGER));
    columns.put("name", new Column("name", SqlType.VARCHAR));
    final EnumCfg actualEnumCfg = (EnumCfg) Whitebox.invokeMethod(mojo, "completeEnumCfgFromColumns", enumCfgIn, columns);

    final EnumCfg expectedEnumCfg = new EnumCfg();
    expectedEnumCfg.setName("TestEnum");
    expectedEnumCfg.setValueColumn("name");

    assertEquals("enumCfg is returned", expectedEnumCfg, actualEnumCfg);
  }

  @Test
  public void testcompleteEnumCfgFromColumns3() throws Exception {
    mojo = new SqlEnumGeneratorMojo();

    final EnumCfg enumCfgIn = new EnumCfg();
    enumCfgIn.setName("TestEnum");
    final LinkedMap<String, Column> columns = new LinkedMap<>();
    columns.put("id", new Column("id", SqlType.INTEGER));
    columns.put("name", new Column("name", SqlType.VARCHAR));
    final EnumCfg actualEnumCfg = (EnumCfg) Whitebox.invokeMethod(mojo, "completeEnumCfgFromColumns", enumCfgIn, columns);

    final EnumCfg expectedEnumCfg = new EnumCfg();
    expectedEnumCfg.setName("TestEnum");
    expectedEnumCfg.setValueColumn("name");

    assertEquals("enumCfg is returned", expectedEnumCfg, actualEnumCfg);
  }

  @Test
  public void testcompleteEnumCfgFromColumns4() throws Exception {
    mojo = new SqlEnumGeneratorMojo();

    final EnumCfg enumCfgIn = new EnumCfg();
    enumCfgIn.setName("TestEnum");
    final LinkedMap<String, Column> columns = new LinkedMap<>();
    columns.put("id", new Column("id", SqlType.VARCHAR));
    columns.put("name", new Column("name", SqlType.VARCHAR));
    final EnumCfg actualEnumCfg = (EnumCfg) Whitebox.invokeMethod(mojo, "completeEnumCfgFromColumns", enumCfgIn, columns);

    final EnumCfg expectedEnumCfg = new EnumCfg();
    expectedEnumCfg.setName("TestEnum");
    expectedEnumCfg.setValueColumn("id");

    assertEquals("enumCfg is returned", expectedEnumCfg, actualEnumCfg);
  }

  @Test
  public void testcompleteEnumCfgFromColumns5() throws Exception {
    mojo = new SqlEnumGeneratorMojo();

    final EnumCfg enumCfgIn = new EnumCfg();
    enumCfgIn.setName("TestEnum");
    enumCfgIn.setNameColumn("NameColumn");
    final LinkedMap<String, Column> columns = new LinkedMap<>();
    columns.put("id", new Column("id", SqlType.VARCHAR));
    columns.put("name", new Column("name", SqlType.VARCHAR));
    columns.put("NameColumn", new Column("NameColumn", SqlType.VARCHAR));
    final EnumCfg actualEnumCfg = (EnumCfg) Whitebox.invokeMethod(mojo, "completeEnumCfgFromColumns", enumCfgIn, columns);

    final EnumCfg expectedEnumCfg = new EnumCfg();
    expectedEnumCfg.setName("TestEnum");
    expectedEnumCfg.setNameColumn("NameColumn");
    expectedEnumCfg.setValueColumn("id");

    assertEquals("enumCfg is returned", expectedEnumCfg, actualEnumCfg);
    assertEquals("columns size reduced", 2, columns.size());
    assertEquals("NameColumn removed from colums", -1, columns.indexOf("NameColumn"));
  }

  @Test
  public void testcompleteEnumCfgFromColumnsFailNoStringColumn1() throws Exception {
    mojo = new SqlEnumGeneratorMojo();

    final EnumCfg enumCfgIn = new EnumCfg();
    enumCfgIn.setName("TestEnum");
    final LinkedMap<String, Column> columns = new LinkedMap<>();
    columns.put("name", new Column("name", SqlType.INTEGER));

    exception.expect(MojoFailureException.class);
    exception.expectMessage("Only column for enum TestEnum must have String representation (for enum value).");
    Whitebox.invokeMethod(mojo, "completeEnumCfgFromColumns", enumCfgIn, columns);
  }

  @Test
  public void testcompleteEnumCfgFromColumnsFailNoStringColumn2() throws Exception {
    mojo = new SqlEnumGeneratorMojo();

    final EnumCfg enumCfgIn = new EnumCfg();
    enumCfgIn.setName("TestEnum");
    final LinkedMap<String, Column> columns = new LinkedMap<>();
    columns.put("id", new Column("name", SqlType.INTEGER));
    columns.put("name", new Column("name", SqlType.INTEGER));

    exception.expect(MojoFailureException.class);
    exception.expectMessage("Enum TestEnum must have at least one column with String representation (for enum value).");
    Whitebox.invokeMethod(mojo, "completeEnumCfgFromColumns", enumCfgIn, columns);
  }

  @Test
  public void testcompleteEnumCfgFromColumnsFailNoNameColumn() throws Exception {
    mojo = new SqlEnumGeneratorMojo();

    final EnumCfg enumCfgIn = new EnumCfg();
    enumCfgIn.setName("name");
    enumCfgIn.setNameColumn("TestColumn");
    final LinkedMap<String, Column> columns = new LinkedMap<>();
    columns.put("id", new Column("name", SqlType.VARCHAR));
    columns.put("name", new Column("name", SqlType.VARCHAR));

    exception.expect(MojoFailureException.class);
    exception.expectMessage("Configured nameColumn missing for enum name.");
    Whitebox.invokeMethod(mojo, "completeEnumCfgFromColumns", enumCfgIn, columns);
  }

  @Test
  public void testcompleteEnumCfgFromColumnsFailNameColumnNotString() throws Exception {
    mojo = new SqlEnumGeneratorMojo();

    final EnumCfg enumCfgIn = new EnumCfg();
    enumCfgIn.setName("name");
    enumCfgIn.setNameColumn("TestColumn");
    final LinkedMap<String, Column> columns = new LinkedMap<>();
    columns.put("id", new Column("name", SqlType.VARCHAR));
    columns.put("name", new Column("name", SqlType.VARCHAR));
    columns.put("TestColumn", new Column("TestColumn", SqlType.INTEGER));

    exception.expect(MojoFailureException.class);
    exception.expectMessage("Configured nameColumn TestColumn for enum must have String representation (for enum name).");
    Whitebox.invokeMethod(mojo, "completeEnumCfgFromColumns", enumCfgIn, columns);
  }

  @Test
  public void testcompleteEnumCfgFromColumnsFailNoColumns() throws Exception {
    mojo = new SqlEnumGeneratorMojo();

    final EnumCfg enumCfgIn = new EnumCfg();
    enumCfgIn.setName("TestEnum");
    final LinkedMap<String, Column> columns = new LinkedMap<>();

    exception.expect(MojoFailureException.class);
    exception.expectMessage("No columns found for enum TestEnum.");
    Whitebox.invokeMethod(mojo, "completeEnumCfgFromColumns", enumCfgIn, columns);
  }

  public void testStringToJavaIdentifier() throws Exception {
    mojo = new SqlEnumGeneratorMojo();

    final String[] inputString = {"Abcdef", ".bcdef", "A.cdef", "A_cdef", "50 %"};
    final String[] expectedJavaIdentifier = {"Abcdef", "_46_bcdef", "A_46_cdef", "A__cdef", "_53_0_32__37_"};

    for (int i = 0; i < inputString.length; i++) {
      String actualJavaIdentifier = (String) Whitebox.invokeMethod(mojo, "stringToJavaIdentifier", inputString[i]);
      assertEquals(expectedJavaIdentifier[i], actualJavaIdentifier);
    }
  }

  public void testReadEnumNamesFromDB() throws SQLException, Exception {
    mojo = new SqlEnumGeneratorMojo();

    final Connection connection = mock(Connection.class);
    final PreparedStatement stmt = mock(PreparedStatement.class);
    final ResultSet result = mock(ResultSet.class);
    when(result.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    when(result.getString("nameColumn")).thenReturn("name1").thenReturn("name2");
    when(stmt.executeQuery()).thenReturn(result);
    when(connection.prepareStatement("SELECT DISTINCT nameColumn FROM table")).thenReturn(stmt);

    final EnumCfg enumCfg = new EnumCfg();
    enumCfg.setNameColumn("nameColumn");
    enumCfg.setTable("table");

    final Map<String, String> enumNames = (Map<String, String>) Whitebox.invokeMethod(mojo, "readEnumNamesFromDB", connection, enumCfg);
    assertTrue(enumNames.containsKey("name1"));
    assertEquals(enumNames.get("name1"), "name1");
    assertTrue(enumNames.containsKey("name2"));
    assertEquals(enumNames.get("name2"), "name2");
    assertEquals("enum names size", 2, enumNames.size());
  }

  @Test
  public void testLoadTemplate() throws Exception {
    final VelocityEngine velocityEngine = mock(VelocityEngine.class);
    final Template expectedTemplate = mock(Template.class);
    when(velocityEngine.getTemplate(VELOCITY_TEMPLATE)).thenReturn(expectedTemplate);
    mojo = new SqlEnumGeneratorMojo(velocityEngine);

    final Template actualTemplate = (Template) Whitebox.invokeMethod(mojo, "loadTemplate");

    assertSame("template is returned", expectedTemplate, actualTemplate);
  }

  @Test
  public void testLoadTemplateFail() throws Exception {
    final VelocityEngine velocityEngine = mock(VelocityEngine.class);
    when(velocityEngine.getTemplate(VELOCITY_TEMPLATE)).thenThrow(new NullPointerException());
    mojo = new SqlEnumGeneratorMojo(velocityEngine);

    exception.expect(RuntimeException.class);
    Whitebox.invokeMethod(mojo, "loadTemplate");
  }

  private void setupInMemoryMojo() throws IllegalAccessException {
    mojo = new SqlEnumGeneratorMojo(SqlEnumGeneratorMojoTest.fileSystem);
    configuration = new Configuration();
    configuration.getGenerator().getTarget().setDirectory(SqlEnumGeneratorMojo.DEFAULT_OUTPUT_DIRECTORY);
    configuration.getGenerator().getTarget().setPackage(SqlEnumGeneratorMojo.DEFAULT_PACKAGE);

    final MavenProject project = new MavenProjectStub();
    setVariableValueToObject(mojo, "project", project);
}
}

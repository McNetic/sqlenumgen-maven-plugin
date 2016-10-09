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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Nicolai Ehemann
 */
public class ConfigurationTest {

  private Configuration configuration;

  @Before
  public void setUp() {
    this.configuration = new Configuration();
  }

  @Test
  public void testConstructor() {
    assertNotNull("jdbc initialized", this.configuration.getJdbc());
    assertNotNull("generator initialized", this.configuration.getGenerator());
  }

  @Test
  public void testGetterSetter() {
    final JDBCCfg jdbc = new JDBCCfg();
    this.configuration.setJdbc(jdbc);
    assertSame("jdbc is the same", jdbc, this.configuration.getJdbc());

    final GeneratorCfg generator = new GeneratorCfg();
    this.configuration.setGenerator(generator);
    assertSame("generator is same", generator, this.configuration.getGenerator());
  }
}

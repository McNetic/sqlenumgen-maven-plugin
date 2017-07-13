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

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Nicolai Ehemann
 */
public class GeneratorCfgTest {

  private GeneratorCfg generator;

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Before
  public void setUp() {
    this.generator = new GeneratorCfg();
  }

  @Test
  public void testConstructor() {
    assertNotNull("target initialized", this.generator.getTarget());
  }

  @Test
  public void testGetterSetter() {
    final DatabaseCfg database = new DatabaseCfg();
    this.generator.setDatabase(database);
    assertSame("database is same", database, this.generator.getDatabase());

    final String databaseSchema = "schema";
    this.generator.setDatabaseSchema(databaseSchema);
    assertSame("databaseSchema is same", databaseSchema, this.generator.getDatabaseSchema());

    final TargetCfg target = new TargetCfg();
    this.generator.setTarget(target);
    assertSame("target is same", target, this.generator.getTarget());

    this.generator.setAttributeVisibility(AttributeVisibility.PUBLIC);
    assertSame("attributeVisibility is same", AttributeVisibility.PUBLIC, this.generator.getAttributeVisibility());

    this.generator.setAttributeVisibility(AttributeVisibility.PUBLIC.name());
    assertSame("attributeVisibility is same", AttributeVisibility.PUBLIC, this.generator.getAttributeVisibility());

    this.generator.setAttributeVisibility(AttributeVisibility.PUBLIC.name().toLowerCase());
    assertSame("attributeVisibility is same", AttributeVisibility.PUBLIC, this.generator.getAttributeVisibility());
  }

  @Test
  public void testSetterFails() {
    exception.expect(IllegalArgumentException.class);
    this.generator.setAttributeVisibility("");
  }
}

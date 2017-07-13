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
import org.junit.Test;

/**
 *
 * @author Nicolai Ehemann
 */
public class EnumCfgTest {

  private EnumCfg enumCfg1;
  private EnumCfg enumCfg2;
  private EnumCfg enumCfg3;
  private EnumCfg enumCfg4;
  private EnumCfg enumCfg5;
  private EnumCfg enumCfg6;

  @Before
  public void setUp() {
    enumCfg1 = new EnumCfg();
    enumCfg1.setName("name1");
    enumCfg1.setNameColumn("nameColumn1");
    enumCfg1.setTable("table1");
    enumCfg1.setValueColumn("valueColumn1");
    enumCfg2 = new EnumCfg();
    enumCfg2.setName("name1");
    enumCfg2.setNameColumn("nameColumn1");
    enumCfg2.setTable("table1");
    enumCfg2.setValueColumn("valueColumn1");
    enumCfg3 = new EnumCfg();
    enumCfg3.setName(null);
    enumCfg3.setNameColumn("nameColumn1");
    enumCfg3.setTable("table1");
    enumCfg3.setValueColumn("valueColumn1");
    enumCfg4 = new EnumCfg();
    enumCfg4.setName("name1");
    enumCfg4.setNameColumn(null);
    enumCfg4.setTable("table1");
    enumCfg4.setValueColumn("valueColumn1");
    enumCfg5 = new EnumCfg();
    enumCfg5.setName("name1");
    enumCfg5.setNameColumn("nameColumn1");
    enumCfg5.setTable(null);
    enumCfg5.setValueColumn("valueColumn1");
    enumCfg6 = new EnumCfg();
    enumCfg6.setName("name1");
    enumCfg6.setNameColumn("nameColumn1");
    enumCfg6.setTable("table1");
    enumCfg6.setValueColumn(null);
  }

  @Test
  public void testGettersSetters() {
    assertEquals("name1", enumCfg1.getName());
    assertEquals("nameColumn1", enumCfg1.getNameColumn());
    assertEquals("table1", enumCfg1.getTable());
    assertEquals("valueColumn1", enumCfg1.getValueColumn());

    enumCfg1.setName("name2");
    enumCfg1.setNameColumn("nameColumn2");
    enumCfg1.setTable("table2");
    enumCfg1.setValueColumn("valueColumn2");

    assertEquals("name2", enumCfg1.getName());
    assertEquals("nameColumn2", enumCfg1.getNameColumn());
    assertEquals("table2", enumCfg1.getTable());
    assertEquals("valueColumn2", enumCfg1.getValueColumn());
  }

  @Test
  public void testEquals() {
    assertNotSame(enumCfg1, enumCfg2);
    assertNotSame(enumCfg2, enumCfg3);
    assertNotSame(enumCfg3, enumCfg1);

    assertTrue(enumCfg1.equals(enumCfg1));
    assertTrue(enumCfg2.equals(enumCfg2));
    assertTrue(enumCfg3.equals(enumCfg3));

    assertTrue(enumCfg1.equals(enumCfg2));
    assertTrue(enumCfg2.equals(enumCfg1));
    assertFalse(enumCfg2.equals(enumCfg3));
    assertFalse(enumCfg3.equals(enumCfg2));
    assertFalse(enumCfg1.equals(enumCfg3));
    assertFalse(enumCfg3.equals(enumCfg1));

    assertFalse(enumCfg1.equals(enumCfg4));
    assertFalse(enumCfg4.equals(enumCfg1));
    assertFalse(enumCfg1.equals(enumCfg5));
    assertFalse(enumCfg5.equals(enumCfg1));
    assertFalse(enumCfg1.equals(enumCfg6));
    assertFalse(enumCfg6.equals(enumCfg1));

    assertFalse(enumCfg1.equals(null));
    assertFalse(enumCfg1.equals("enumCfg1"));

    enumCfg1.setName("name3");

    assertFalse(enumCfg1.equals(enumCfg2));
    assertFalse(enumCfg2.equals(enumCfg1));
  }

  @Test
  public void testHashCode() {
    assertEquals(enumCfg1.hashCode(), enumCfg2.hashCode());
    assertNotEquals(enumCfg2.hashCode(), enumCfg3.hashCode());
    assertNotEquals(enumCfg3.hashCode(), enumCfg1.hashCode());

    enumCfg1.setName("name3");

    assertNotEquals(enumCfg1.hashCode(), enumCfg2.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("EnumCfg{" +
        "name=" + enumCfg1.getName() +
        ", nameColumn=" + enumCfg1.getNameColumn() +
        ", table=" + enumCfg1.getTable() +
        ", valueColumn=" + enumCfg1.getValueColumn() + "}",
        enumCfg1.toString());
  }
}

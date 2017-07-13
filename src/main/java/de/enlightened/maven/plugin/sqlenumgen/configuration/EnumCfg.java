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

import java.util.Objects;

/**
 *
 * @author Nicolai Ehemann
 */
public class EnumCfg {

  /**
   * The enum name
   * @since 0.0.1
   */
  private String name;

  /**
   * The escaped enum name
   * @since 0.0.2
   */
  private String escapedName;

  /**
   * The column to generate the enum name from
   * @since 0.0.1
   */
  private String nameColumn;

  /**
   * The database table to generate the enum from (required)
   * @since 0.0.1
   */
  private String table;

  /**
   * The column to generate the enum value from
   * @since 0.0.2
   */
  private String valueColumn;

  public EnumCfg() {
  }

  public final String getName() {
    return this.name;
  }

  public final String getEscapedName() {
    return escapedName;
  }

  public final void setName(final String name) {
    this.name = name;
  }

  public final void setEscapedName(final String escapedName) {
    this.escapedName = escapedName;
  }

  public final String getNameColumn() {
    return nameColumn;
  }

  public final void setNameColumn(final String nameColumn) {
    this.nameColumn = nameColumn;
  }

  public final String getTable() {
    return this.table;
  }

  public final void setTable(final String table) {
    this.table = table;
  }

  public final String getValueColumn() {
    return this.valueColumn;
  }

  public final void setValueColumn(final String valueColumn) {
    this.valueColumn = valueColumn;
  }

  private static final int HASH_BASE = 7;

  private static final int HASH_FACTOR = 97;

  @Override
  public final int hashCode() {
    int hash = HASH_BASE;
    hash = HASH_FACTOR * hash + Objects.hashCode(this.name);
    hash = HASH_FACTOR * hash + Objects.hashCode(this.escapedName);
    hash = HASH_FACTOR * hash + Objects.hashCode(this.nameColumn);
    hash = HASH_FACTOR * hash + Objects.hashCode(this.table);
    hash = HASH_FACTOR * hash + Objects.hashCode(this.valueColumn);
    return hash;
  }

  @Override
  public final boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final EnumCfg other = (EnumCfg) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.escapedName, other.escapedName)) {
      return false;
    }
    if (!Objects.equals(this.nameColumn, other.nameColumn)) {
      return false;
    }
    if (!Objects.equals(this.table, other.table)) {
      return false;
    }
    if (!Objects.equals(this.valueColumn, other.valueColumn)) {
      return false;
    }
    return true;
  }

  @Override
  public final String toString() {
    return "EnumCfg{" + "name=" + name + ", escapedName=" + escapedName + ", nameColumn=" + nameColumn + ", table=" + table + ", valueColumn=" + valueColumn + "}";
  }
}

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
   */
  private String name;

  /**
   * The database table to generate the enum from
   */
  private String table;

  /**
   * The column to generate the enum name from
   */
  private String nameColumn;

  /**
   * The column to generate the enum id from
   */
  private String idColumn;

  public EnumCfg() {
  }

  public final String getName() {
    return this.name;
  }

  public final void setName(final String name) {
    this.name = name;
  }

  public final String getTable() {
    return this.table;
  }

  public final void setTable(final String table) {
    this.table = table;
  }

  public final String getNameColumn() {
    return this.nameColumn;
  }

  public final void setNameColumn(final String nameColumn) {
    this.nameColumn = nameColumn;
  }

  public final String getIdColumn() {
    return this.idColumn;
  }

  public final void setIdColumn(final String idColumn) {
    this.idColumn = idColumn;
  }


  private static final int HASH_BASE = 7;

  private static final int HASH_FACTOR = 97;

  @Override
  public final int hashCode() {
    int hash = HASH_BASE;
    hash = HASH_FACTOR * hash + Objects.hashCode(this.name);
    hash = HASH_FACTOR * hash + Objects.hashCode(this.table);
    hash = HASH_FACTOR * hash + Objects.hashCode(this.nameColumn);
    hash = HASH_FACTOR * hash + Objects.hashCode(this.idColumn);
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
    if (!Objects.equals(this.table, other.table)) {
      return false;
    }
    if (!Objects.equals(this.nameColumn, other.nameColumn)) {
      return false;
    }
    if (!Objects.equals(this.idColumn, other.idColumn)) {
      return false;
    }
    return true;
  }

  @Override
  public final String toString() {
    return "EnumCfg{" + "name=" + name + ", table=" + table + ", nameColumn=" + nameColumn + ", idColumn=" + idColumn + '}';
  }
}

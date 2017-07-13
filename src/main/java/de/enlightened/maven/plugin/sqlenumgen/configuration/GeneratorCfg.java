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

/**
 *
 * @author Nicolai Ehemann
 */
public class GeneratorCfg {

  /**
   * The database schema
   */
  private String databaseSchema;

  /**
   * Visibility of enum attributes (if and only if visibility is private (default),
   * getters will be generated for attributes)
   */
  private AttributeVisibility attributeVisibility = AttributeVisibility.PRIVATE;

  /**
   * Target configuration
   */
  private TargetCfg target;

  /**
   * The database configuration
   */
  private DatabaseCfg database;

  public GeneratorCfg() {
    this.target = new TargetCfg();
  }

  public final String getDatabaseSchema() {
    return this.databaseSchema;
  }

  public final void setDatabaseSchema(final String databaseSchema) {
    this.databaseSchema = databaseSchema;
  }

  public final AttributeVisibility getAttributeVisibility() {
    return attributeVisibility;
  }

  public final void setAttributeVisibility(final AttributeVisibility attributeVisibility) {
    this.attributeVisibility = attributeVisibility;
  }

  public final void setAttributeVisibility(final String attributeVisibility) {
    this.attributeVisibility = AttributeVisibility.valueOf(attributeVisibility.toUpperCase());
  }

  public final TargetCfg getTarget() {
    return this.target;
  }

  public final void setTarget(final TargetCfg target) {
    this.target = target;
  }

  public final DatabaseCfg getDatabase() {
    return this.database;
  }

  public final void setDatabase(final DatabaseCfg database) {
    this.database = database;
  }
}

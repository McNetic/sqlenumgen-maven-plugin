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
package de.enlightened.maven.plugin.sqlenumgen.util;

/**
 *
 * @author Nicolai Ehemann
 */
public class Column {

  private final String name;

  private final SqlType type;

  public Column(final String name, final SqlType type) {
    this.name = name;
    this.type = type;
  }

  public final String getName() {
    return this.name;
  }

  public final SqlType getType() {
    return this.type;
  }
}

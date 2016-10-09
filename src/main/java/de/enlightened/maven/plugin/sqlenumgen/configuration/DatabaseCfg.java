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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicolai Ehemann
 */
public class DatabaseCfg {

  /**
   * The enum(s) to generate
   */
  private final List<EnumCfg> enums = new ArrayList<EnumCfg>();

  public DatabaseCfg() {
  }

  public final List<EnumCfg> getEnums() {
    return this.enums;
  }

  public final void addEnum(final EnumCfg enm) {
    this.enums.add(enm);
  }
}

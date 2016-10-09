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

/**
 *
 * @author Nicolai Ehemann
 */
public class TargetCfg {

  /**
   * Package name of the generated enums
   */
  private String pkg;

  /**
   * The output directory for the generated classes
   */
  private String directory;

  public TargetCfg() {
  }

  public final String getPackage() {
    return this.pkg;
  }

  public final void setPackage(final String setpkg) {
    this.pkg = setpkg;
  }

  public final String getDirectory() {
    return this.directory;
  }

  public final void setDirectory(final String directory) {
    this.directory = directory;
  }
}

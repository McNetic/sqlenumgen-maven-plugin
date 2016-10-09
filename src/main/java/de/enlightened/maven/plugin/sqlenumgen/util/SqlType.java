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

import java.math.BigDecimal;

/**
 *
 * @author Nicolai Ehemann
 */
public enum SqlType {
  VARCHAR(String.class, "\"%s\""),
  CHAR(String.class, "\"%s\""),
  LONGVARCHAR(String.class, "\"%s\""),
  BIT(Boolean.class),
  NUMERIC(BigDecimal.class, "new BigDecimal(\"%s\")"),
  BIGDECIMAL(BigDecimal.class, "new BigDecimal(\"%s\")"),
  TINYINT(Byte.class),
  SMALLINT(Short.class),
  INTEGER(Integer.class),
  BIGINT(Long.class),
  REAL(Float.class),
  FLOAT(Float.class),
  DOUBLE(Double.class),
  DATE(java.sql.Date.class),
  TIME(java.sql.Time.class),
  TIMESTAMP(java.sql.Timestamp.class);

  private final Class javaClass;

  private final String literalFormat;

  SqlType(final Class javaClass, final String literalFormat) {
    this.javaClass = javaClass;
    this.literalFormat = literalFormat;
  }

  SqlType(final Class javaClass) {
    this(javaClass, "%s");
  }

  public final Class getJavaClass() {
    return this.javaClass;
  }

  public String getLiteralFormat() {
    return this.literalFormat;
  }
}

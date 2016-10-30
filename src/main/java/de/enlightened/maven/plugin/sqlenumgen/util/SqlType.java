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
import java.sql.Types;

/**
 *
 * @author Nicolai Ehemann
 */
public enum SqlType {
  VARCHAR(Types.VARCHAR, String.class, "\"%s\""),
  CHAR(Types.VARCHAR, String.class, "\"%s\""),
  LONGVARCHAR(Types.VARCHAR, String.class, "\"%s\""),
  NUMERIC(Types.NUMERIC, BigDecimal.class, "new BigDecimal(\"%s\")"),
  DECIMAL(Types.DECIMAL, BigDecimal.class, "new BigDecimal(\"%s\")"),
  BIT(Types.BIT, Boolean.class),
  TINYINT(Types.TINYINT, Byte.class),
  SMALLINT(Types.SMALLINT, Short.class),
  INTEGER(Types.INTEGER, Integer.class),
  BIGINT(Types.BIGINT, Long.class),
  REAL(Types.REAL, Float.class),
  FLOAT(Types.FLOAT, Double.class),
  DOUBLE(Types.DOUBLE, Double.class),
  DATE(Types.DATE, java.sql.Date.class),
  TIME(Types.TIME, java.sql.Time.class),
  TIMESTAMP(Types.TIMESTAMP, java.sql.Timestamp.class);

  private final int sqlType;

  private final Class javaClass;

  private final String literalFormat;

  SqlType(final int sqlType, final Class javaClass, final String literalFormat) {
    this.sqlType = sqlType;
    this.javaClass = javaClass;
    this.literalFormat = literalFormat;
  }

  SqlType(final int sqlType, final Class javaClass) {
    this(sqlType, javaClass, "%s");
  }

  public final int getSqlType() {
    return this.sqlType;
  }

  public final Class getJavaClass() {
    return this.javaClass;
  }

  public final String getLiteralFormat() {
    return this.literalFormat;
  }

  public static SqlType valueOf(final int type) {
    for (SqlType sqlType : SqlType.values()) {
      if (sqlType.getSqlType() == type) {
        return sqlType;
      }
    }
    throw new IllegalArgumentException(String.format("No SqlType enum representation for int %d", type));
  }
}

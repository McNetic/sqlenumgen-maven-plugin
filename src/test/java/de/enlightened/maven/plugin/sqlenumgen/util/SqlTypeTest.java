/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.enlightened.maven.plugin.sqlenumgen.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Nicolai Ehemann, 2016-11-18
 */
public class SqlTypeTest {

  @Test
  public void testLiteralFormatNotNull() {
    assertEquals("\"\\\"\\n\"", SqlType.VARCHAR.generateLiteral("\"\n"));
    assertEquals("\"\\\"\\n\"", SqlType.CHAR.generateLiteral("\"\n"));
    assertEquals("\"\\\"\\n\"", SqlType.LONGVARCHAR.generateLiteral("\"\n"));
    assertEquals("new BigDecimal(\"123456789\")", SqlType.NUMERIC.generateLiteral(123456789));
    assertEquals("new BigDecimal(\"123456789\")", SqlType.DECIMAL.generateLiteral(123456789));
    assertEquals("new BigDecimal(\"123456789123456789\")", SqlType.NUMERIC.generateLiteral(123456789123456789L));
    assertEquals("new BigDecimal(\"123456789123456789\")", SqlType.DECIMAL.generateLiteral(123456789123456789L));
    assertEquals("new BigDecimal(\"1.2345679\")", SqlType.NUMERIC.generateLiteral(1.23456789F));
    assertEquals("new BigDecimal(\"1.2345679\")", SqlType.DECIMAL.generateLiteral(1.23456789F));
    assertEquals("new BigDecimal(\"1.234567891234\")", SqlType.NUMERIC.generateLiteral(1.234567891234D));
    assertEquals("new BigDecimal(\"1.234567891234\")", SqlType.DECIMAL.generateLiteral(1.234567891234D));
    assertEquals("true", SqlType.BIT.generateLiteral(true));
    assertEquals("false", SqlType.BIT.generateLiteral(false));
    assertEquals("32", SqlType.TINYINT.generateLiteral(0x20));
    assertEquals("42", SqlType.SMALLINT.generateLiteral(42));
    assertEquals("42", SqlType.INTEGER.generateLiteral(42));
    assertEquals("42L", SqlType.BIGINT.generateLiteral(42));
    assertEquals("42L", SqlType.BIGINT.generateLiteral(42L));
    assertEquals("123456789123456789L", SqlType.BIGINT.generateLiteral(123456789123456789L));
    assertEquals("1.2345679F", SqlType.REAL.generateLiteral(1.23456789F));
    assertEquals("1.2345679F", SqlType.FLOAT.generateLiteral(1.23456789F));
    assertEquals("1.2345679D", SqlType.DOUBLE.generateLiteral(1.23456789F));
    assertEquals("1.234567891234D", SqlType.DOUBLE.generateLiteral(1.234567891234D));
    assertEquals("Date.valueOf(\"2016-11-21\")", SqlType.DATE.generateLiteral(java.sql.Date.valueOf("2016-11-21")));
    assertEquals("Time.valueOf(\"12:30:10\")", SqlType.TIME.generateLiteral(java.sql.Time.valueOf("12:30:10")));
    assertEquals("Timestamp.valueOf(\"2016-11-21 12:30:10.494\")", SqlType.TIMESTAMP.generateLiteral(java.sql.Timestamp.valueOf("2016-11-21 12:30:10.494")));
  }

  @Test
  public void testLiteralFormatNull() {
    for (SqlType type : SqlType.values()) {
      assertEquals("null", type.generateLiteral(null));
    }
  }
}
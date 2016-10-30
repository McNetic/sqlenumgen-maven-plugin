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
package de.enlightened.maven.plugin.sqlenumgen.repr;

import de.enlightened.maven.plugin.sqlenumgen.util.SqlType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Nicolai Ehemann
 */
public class EnumRepr {
  private final String name;

  private final LinkedHashSet<ValueRepr> values = new LinkedHashSet<ValueRepr>();
  private final Set<String> valueNames = new HashSet<String>();
  private final List<MemberRepr> members = new ArrayList<MemberRepr>();
  private final SortedSet<String> packages = new TreeSet<String>();

  public EnumRepr(final String name) {
    this.name = name;
  }

  public final String getName() {
    return this.name;
  }

  public final LinkedHashSet<ValueRepr> getValues() {
    return this.values;
  }

  public final List<MemberRepr> getMembers() {
    return this.members;
  }

  public final boolean hasMembers() {
    return this.members.size() > 0;
  }

  public final SortedSet<String> getPackages() {
    return this.packages;
  }

  public final boolean hasPackages() {
    return this.packages.size() > 0;
  }

  public final void addValue(final ValueRepr value) {
    this.valueNames.add(value.getName());
    this.values.add(value);
  }

  public final void addMember(final String memberName, final SqlType sqlType) {
    final Class javaClass = sqlType.getJavaClass();
    this.members.add(new MemberRepr(memberName, javaClass));
    if (!"java.lang".equals(javaClass.getPackage().getName())) {
      this.packages.add(javaClass.getCanonicalName());
    }
  }

  public final boolean hasValue(final String valueName) {
    return this.valueNames.contains(valueName);
  }
}

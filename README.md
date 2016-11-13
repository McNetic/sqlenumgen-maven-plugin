# sqlenumgen-maven-plugin
Maven plugin to generate java enums from sql database table(s)

## Abstract

Often, database-backed applications require so called 'master data' tables,
containing a few data records that are runtime constant. In Java, such master
data is represented by enums.

To avoid having to manually synchronize the generation of the database
tables/records and corresponding enum types, this maven plugin allows to
generate the enums automatically from existing database tables via jdbc.

## Usage

Add the plugin to your pom.xml:

    <build>
      <plugins>
        <plugin>
          <groupId>de.enlightened</groupId>
          <artifactId>sql-enum-generator-maven-plugin</artifactId>
          <version>0.2.0</version>

          <!-- The plugin should hook into the generate goal -->
          <executions>
            <execution>
              <goals>
                <goal>generate</goal>
              </goals>
            </execution>
          </executions>
          <dependencies>
            ...
          </dependencies>
          <configuration>
            <jdbc>...</jdbc>
            <generator>...</generator>
          </configuration>
        </plugin>
      </plugin>
    </build>

The configuration is also made in the pom.xml. First, you will usually need a
dependency on the jdbc driver for your database (in the plugin configuration,
as depicted above). For example, if you use sqlite, add the following to the
above dependencies:

    <dependency>
      <groupId>org.xerial</groupId>
      <artifactId>sqlite-jdbc</artifactId>
      <version>3.8.11.2</version>
    </dependency>

Next, you can add the jdbc connection parameters as required to the above
configuration:

      <jdbc>
        <url>...</url>
        <user>...</user>
        <password>...</password>
      </jdbc>

For example:

    <jdbc>
      <url>jdbc:sqlite:test.db</url>
    </jdbc>

Now for the interesting part: The generator configuration. By default, the enums
will be generated in a package `de.enlightened.sqlenum` in the directory
`target/generated-sources/sql-enum`. You can override both:

        <target>
          <package>de.enlightened.sqlenum</package>
          <directory>target/generated-sources/sql-enum</directory>
        </target>

At last, you specify the enums to be generated:

      <database>
        <enum>
          <name>Enumname</name>
          <table>tablename</table>
          <valueColumn>columnname</valueColumn>
        </enum>
        ...
      </database>

This will generate an enum with the specified name from the specified table
records. By default, the first column whose sql type has a string representation
in java will be chosen for naming the enum values. This can be overriden by
explicitly setting the 'valueColumn'. All other columns will be used to populate
properties of the enum.

Multiple enums can be configured.

It is also possible to have multiple enums generated from one table. In this
case, one column has to be specified as nameColumn
(`<nameColumn>columnname</nameColumn>`). All rows with the same value in this
column will be used for generating values in an enum, whose name is the
value of the name column.


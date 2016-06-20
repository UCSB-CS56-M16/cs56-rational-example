
This is a list of the additional changes to the build.xml file for ex04 that were omitted from the main <README.md> (for length).

The clean target.  Before:

```
  <target name="clean">
    <delete>
      <fileset dir="." includes="*.class"/>
    </delete>
  </target>

```

After:

```xml
  <target name="clean" description="clean up the project">
    <delete>
      <fileset dir="build" includes="*.class"/>
      <fileset dir="build" includes="*.jar"/>	
    </delete>
  </target>
```

The run target.  Before:

```xml
  <target name="run">
    <java classname="Rational" classpath="."/>
  </target>
```

After:

```xml
  <target name="run" descripton="run the main">
    <java classname="Rational" classpath="build"/>
  </target>
```

The jar target.  Before:

```xml
  <target name="jar" depends="compile">
    <jar destfile="build/rational.jar">
      <fileset dir="." includes="*.class"/>
      <manifest>
	<attribute name="Main-Class" value="Rational"/>
      </manifest>
    </jar>
  </target>
```

After:

```xml
  <target name="jar" depends="compile" description="create a jar archive">
    <jar destfile="build/rational.jar">
      <fileset dir="build" includes="*.class"/>
      <manifest>
	<attribute name="Main-Class" value="Rational"/>
      </manifest>
    </jar>
  </target>
```

And the test target.  Before:

```xml
  <target name="test" depends="compile" description="run JUnit tests">
    <junit haltonerror="no" haltonfailure="no">
      <classpath refid="project.class.path" />
      <batchtest fork="yes">
	<fileset dir=".">
	  <include name="*Test.java"/>
	</fileset>
      </batchtest>
      <formatter type="brief" usefile="false" />
    </junit>
  </target>
```

After:

```xml
  <target name="test" depends="compile" description="run JUnit tests">
    <junit haltonerror="no" haltonfailure="no">
      <classpath refid="project.class.path" />
      <batchtest fork="yes">
	<fileset dir="src">
	  <include name="*Test.java"/>
	</fileset>
      </batchtest>
      <formatter type="brief" usefile="false" />
    </junit>
  </target>
```

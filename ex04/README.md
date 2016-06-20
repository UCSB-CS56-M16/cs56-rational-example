# cs56-rational-example/ex03

In this example, we will add three new features to our build.xml file

* clean up our directory structure, moving source files to `/src` and class files into `/build`
* we'll adjust the `clean` target accordingly
* we'll add a target to create javadoc
* we'll discuss the idea of immutable objects

# Cleaning up the directory structure

Before our adjustments, our directory structure looked like this:

```
:ex04 pconrad$ ls
Main.java	       		Rational.java		build			lib
README.md						RationalTest.java	build.xml
ex04 pconrad$
```

The only subdirectories here are build, which is where the `jar` target puts the `rational.jar` file when it creates it, and `lib` which
is where we are keeping a copy of the JAR file for JUnit.

Each time we compile, our directory gets cluttered up with `.class` files.  That may be ok when we only have three class files, but
pretty soon, its going to get a bit out of hand.   To keep things tidy, we'll put those in their own directory.   We'll use the build directory for that purpose.

We'll also create a src subdirectory, and move our .java files into it:

```
ex04 pconrad$ ls
Main.java	       		Rational.java		build			lib
README.md						RationalTest.java	build.xml
Phillips-Mac-mini:ex04 pconrad$ mkdir src
Phillips-Mac-mini:ex04 pconrad$ mv *.java src
Phillips-Mac-mini:ex04 pconrad$ ls
README.md	       build		build.xml	lib		src
ex04 pconrad$
```

We'll adjust the build.xml file accordingly.    First the classpath: we change `location="."` to `location="build"`:

Before:

```xml
  <path id="project.class.path"> 
    <pathelement location="."/>
    <pathelement location="lib/junit-4.8.2.jar"/>
  </path>
```

After:

```xml
  <path id="project.class.path"> 
    <pathelement location="src"/>
    <pathelement location="lib/junit-4.8.2.jar"/>
  </path>
```

We also adjust the compile target. We change the `srcdir` and `destdir` attributes both from `"."` (current directory) to `src` and `build`, respectively.    As we adjust this target, and the ones that follow, we'll add description attributes.  This may seem
redundant, but it turns out to be useful if for no other reason than this: it allows us to use `ant -p` to get a list of all of the
targets in the ant file.     

Before:

```xml
  <target name="compile" description="compile the code">
    <javac srcdir="."
	   destdir="."
	   includeantruntime="false">
      <classpath refid="project.class.path" />
    </javac>
  </target>
```


After:

```xml
  <target name="compile" description="compile the code">
    <javac srcdir="src"
	   destdir="build"
	   includeantruntime="false">
      <classpath refid="project.class.path" />
    </javac>
  </target>
```

Listing all of these changes will be pretty tedious, so the rest are listed in a separate .md file here: <CHANGES.md>


Now we can run `ant -p` to see a list of our targets, and then try each one to make sure they still work:

```
ex04 pconrad$ ant -p
Buildfile: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/build.xml

Main targets:

 clean    clean up the project
 compile  compile the code
 jar      create a jar file
 run      run the main
 test     run JUnit tests
ex04 pconrad$

```

The file <FULL_BUILD.md> shows what running each of those looks like after the build.xml file is adjusted.

# Adding Javadoc

The classes that make up the Java Standard Library all have comprehensive documentation, e.g.
* this documentation for [java.lang.String](http://docs.oracle.com/javase/8/docs/api/java/lang/String.html)
* this documentation for [java.util.ArrayList](http://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)

It turns out that generating documentation in this format for your own classes is very easy.  As you may have guessed: there's an Ant task for that.

Add this to your build.xml.  Note that we see a `property` definition. This is like an assignment statement. It sets up `javadoc_absolute_path` as a symbol for the absolute filepath that corresponds to the location of the `javadoc` directory.  We use that definition later
in the contents of the `echo` task (`echo` is like a print statement.)  The `${javadoc_absolute_path}` is a dereference of the contents
of that variable.

```xml

<property name="javadoc_absolute_path" location="javadoc"/>

<target name="javadoc" depends="compile" description="generate javadoc">
 <javadoc destdir="javadoc">
   <fileset dir="src" >
     <include name="*.java"/>
   </fileset>
   <classpath refid="project.class.path" />
 </javadoc>
 <echo>
   javadoc written to file://${javadoc_absolute_path}/index.html
 </echo>
</target>
```

After putting this in our build.xml, we can run the target:

```
ex04 pconrad$ ant javadoc
Buildfile: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/build.xml

compile:

javadoc:
  [javadoc] Generating Javadoc
  [javadoc] Javadoc execution
  [javadoc] Loading source file /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/src/Main.java...
  [javadoc] Loading source file /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/src/Rational.java...
  [javadoc] Loading source file /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/src/RationalTest.java...
  [javadoc] Constructing Javadoc information...
  [javadoc] Standard Doclet version 1.8.0_91
  [javadoc] Building tree for all the packages and classes...
  [javadoc] Building index for all the packages and classes...
  [javadoc] Building index for all classes...
  [echo]
  [echo]       javadoc written to file:///Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/javadoc/index.html
  [echo]

BUILD SUCCESSFUL
Total time: 1 second
ex04 pconrad$
```

If you point your browser to that url, you should see the documentation for the all of the files in the project, in javadoc format.


# Immutable objects

Immutable objects are instance of classes that have no setters (or any other methods that mutate the objects).

The idea is that the only point in time at which you can set the internal state of the object is at the time you invoke the constructor.

Immutable objects have many advantages: they are easier to reason about.

Think carefully about whether you actually need objects that can be mutated (i.e. objects with setters) before you implement setters.

For now, instances of our Rational class will be immutable.

We'll discuss setters later when we actually need mutable objects.

# What's next?

That's it for ex04.

In the next example we'll note that our class currently has what might
be considered a bug.   Consider this code:


```Java
  Rational r = new Rational(3,6);
  Rational s = new Rational(15,5);
  Rational t = new Rational(0,7);
  System.out.println("r=" + r + " s=" + s + " t=" + t);
```

The output will be:

```
r=3/6 s=15/5 t=0/7
```

This is probably not what we want.  We probably want this:

```
r=1/2 s=3 r=0
```

That is, we want:

* fractions to be printed in reduced form
* the denominator to be ommitted when:
    * denominator is 1, or
    * numerator is 0

To be able to reduce fractions, we'll need a gcd method.  That's a
good candidate to be a static method of the Rational class.


We'll take this on on example ex05.

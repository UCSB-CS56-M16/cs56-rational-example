# cs56-rational-example/ex03

In this example, we will add three new features to our build.xml file

* creating a jar file of our code
* automating testing with junit
* creating javadoc for our project

The second of those will also require a new class, RationalTest.java,
a class that exists only for the purpose of collecting test cases for
all of the constructors and methods of Rational.java.

This is a practice we'll try to follow whenever developing a class
that other parts of the code rely on for correctness.  That is,
whenever possible, every class Foo.java should have a FooTest.java
that goes along with it.

# Adding a JUnit task to make a `.jar` file

We add the following section to our `build.xml` file to create
a `.jar` file of our project.    This jar file is nothing more than 
a ZIP archive of .class files along with some metadata that tells
us which class is the default main to run if we ask to "run the .jar file".

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

To run this new target, we use:
```
ex03 pconrad$ ant jar
Buildfile: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex03/build.xml

compile:
    [javac] Compiling 2 source files to /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex03

jar:
      [jar] Building jar: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex03/build/rational.jar

BUILD SUCCESSFUL
Total time: 0 seconds
ex03 pconrad$ 
```

We can then run the file with this command:

```
ex03 pconrad$ java -jar build/rational.jar 
r.getNumerator()=5
r.getDenominator()=7
ex03 pconrad$ 

```

Notice several things:
* The `target` element contains a `depends="compile"` attribute that indicates that the `compile` target should be run first if either:
    * The .class files that the compile step creates don't exist
    * Any of the files that the `compile` target depends on have changed since it was last run.
    * We can demonstrate that if the .class files don't exist, or have changed, the compile target is run first.   Also, we can show that any time the .class files have changed, the jar target gets run. Otherwise it does not.

* We put the jar file into a directory called "build".   Starting with ex04, we are going to
  put all of the artifacts from compiling our code into this subdirectory, so that they are not
  cluttering up the main directory.  We'll also put our code into a subdirectory called src.
  This is more reflective of the common practices used on professional Java coding projects.

* We've left something undone here: we really should add code into our `clean` target that
  cleans up the .jar file as well as the .class files.   We'll take care of that in ex04 as well.

* Reference: [Ant Jar task documentation](https://ant.apache.org/manual/Tasks/jar.html)


# JUnit tests

One of the purposes of a jar file is to be able to distribute java code to others.   This is a common
way that third-party libraries are added into a project.  Rather than incorporating all of the source
code for a project and recompiling it, we add a `.jar` file into the project.

One of the most useful third-party libraries--that is a library of useful code that doesn't come standard
with the Java distribution from Oracle--is a library called JUnit.

Unit testing and test driven development is a large topic, and we are not going to give it a full
treatment in this tutorial.  

Instead, we'll just show the mechanics of how to incorporate JUnit testing
into the project.  For a more detailed treatment of Test Driven Development (TDD), JUnit, and testing practices, you'll need to look elsewhere--for example, these articles from the CS56 web site on [TDD](https://ucsb-cs56-pconrad.github.io/topics/test_driven_development/) and [JUnit](https:\
//ucsb-cs56-pconrad.github.io/topics/junit/).

The first thing we'll do is create a subdirectory called lib in our ex03 directory, and copy
the latest version of the Java JUnit .jar into this directory.

We use the command line tool `wget` to copy the file from the URL shown.  You could also use a web browser and do a save as
if you don't have wget on your system.   The command line tool `curl` is another option.

```
ex03 pconrad$ wget http://www.cs.ucsb.edu/~pconrad/cs56/lib/junit-4.8.2.jar
--2016-06-18 17:13:15--  http://www.cs.ucsb.edu/~pconrad/cs56/lib/junit-4.8.2.jar
Resolving www.cs.ucsb.edu... 128.111.27.14
Connecting to www.cs.ucsb.edu|128.111.27.14|:80... connected.
HTTP request sent, awaiting response... 200 OK
Length: 237344 (232K) [application/x-java-archive]
Saving to: 'junit-4.8.2.jar'

junit-4.8.2.jar            100%[======================================>] 231.78K  13.5KB/s    in 21s     

2016-06-18 17:13:39 (11.1 KB/s) - 'junit-4.8.2.jar' saved [237344/237344]

ex03 pconrad$ 
```

After doing this, in the lib directory, we have the file `junit-4.8.2.jar`:

```
ex03 pconrad$ pwd
/Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex03/lib
ex03 pconrad$ ls
junit-4.8.2.jar
ex03 pconrad$ 
```

(Note that as of this writing (06/18/2016), there is a much later
version of JUnit available, namely JUnit 4.12.  For this tutorial, I'm
using 4.8.2 because 4.12 relies on an additional .jar file called
hamcrest-core.jar version 1.3.  Before we start getting into .jars
that need other .jars, its probably better to just go ahead and move
to Maven.)

Ok, now that we have this .jar file, what do we do with it?   There are several steps to
getting to the point where we can write tests:

1. Write some code that uses JUnit.
2. Get that code to compile (that means putting the JUnit .jar in the classpath of the Ant `javac` task inside the ant `compile` target.)
3. Add a `test` target that calls the `junit` Ant task to run the tests.

We'll do each of those next.

TODO: Each of those items.

## 1. Write some code that uses JUnit


A simple test we can do is to see if our constructors and getters are working properly.
We'll set up a few objects and then see if the getters return the expected values.

We'll add a file called RationalTest.java to our project, with these contents:

```Java
// insert RationalTest.java here
```

Clever students may have noticed: one of the challenges right away is
that the tests are not exactly testing only one unit of code: they
work only if both the constructor *and* the getters are correct.  (If you didn't notice
that, or it didn't both you, no worries.)

The unit tests that we write here are not necessarily the best examples 
of good testing practice.  These are here more to illustrate the mechanics of
getting testing working.





## 2. Get the JUnit code to compile

When we try to compile this, we'll get some errors:

```
ex03 pconrad$ ant compile
Buildfile: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex03/build.xml

compile:
    [javac] Compiling 3 source files to /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex03
    [javac] /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex03/RationalTest.java:1: error: package org.junit does not exist
... [Lots more errors here... ]
```

The reason is that we can't import JUnit unless JUnit is in the classpath.  JUnit is an add-on, not
part of the standard Java library.

To get it into this classpath, we introduce this bit of code into our build.xml.  It says
to look first in the current directory, then in the junit `.jar` file. (The Java system libraries
are always included without us having to do anything.)

```xml
  <path id="project.class.path"> 
    <pathelement location="."/>                                                       
    <pathelement location="lib/junit-4.8.2.jar"/>    
  </path>
```

We then tell the `javac` task to use our classpath, by modifying it like this.  Note that the
`classpath` element is a nested element.

```xml
 <javac srcdir="."
           destdir="."
           includeantruntime="false">
      <classpath refid="project.class.path" />
 </javac>

```

Now we can compile:

```
ex03 pconrad$ ant compile
Buildfile: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex03/build.xml

compile:
    [javac] Compiling 3 source files to /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex03

BUILD SUCCESSFUL
Total time: 0 seconds
ex03 pconrad$ 
```

But how do we run the tests?  There's a special ant task for that.

## Add a test target to run the tests




TODO:

Carry over to ex04:

TODO: refactoring the directories into src and build (no packages yet)
TODO: fix the clean task
TODO: javadoc
TODO: immutable objects

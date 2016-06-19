# cs56-rational-example/ex02

In this example we introduce:

* overriding the toString method
* adding getters
* multiple classes with main in a project
* a simple build.xml file for Ant

# Overriding the toString method.

First we introduce overridding the toString method so that the following code:

```Java
   System.out.println("r = " + r); // implicit r.toString()
```

produces what we want to see, which is:

```
r1 = 4/3
```

instead of:

```
r1 = Rational@2a139a55
```

The code to override the toString method looks like this:

```java
    public String toString() {
	return num + "/" + denom;
    }
```

After overriding the toString method we get this output:


```java
-bash-4.3$ javac *.java
-bash-4.3$ java Main 4 3
r1 = 1/1
r2 = 4/3
-bash-4.3$
```

The expression `r.toString()` now invokes the `toString()` method of the `Rational` class instead of the default `toString` method of `java.lang.Object`, which
would give us something like `Rational@2a139a55`.

The default toString method of java.lang.Object is fine for some debugging
purposes, but more often than not, there is some better representation of
the object that we can come up with.   For the exams in this course,
you will be expected to know how to override the toString()
method properly, and the implications when you do, and don't do it.

# Adding getters

We next add getters.   We'll want to test those getters in some way.

## An aside about testing

At this point, we are still doing testing "the hard way", which is
to say, we are:
* putting some code in a main that "should" do something
* knowing "in our head" what the output "should" be
* running by hand, and checking by hand whether the output is correct.

This is an "easy" way to do testing only in the sense that it doesn't
require more than rudimentary skills in the Java language.  However,
over the long run, its a lousy way to do testing:
* It is error prone
* It leads to code that has not been sufficiently and rigorously tested 
* It is time consuming, especially when you consider the lost time from bugs that
  you don't notice at first glance.
* It is not in line with "current best practices" in the software industry

The smart way to do testing is to automate the testing with a testing framework.  We'll introduce that in [ex03](../ex03).   A key advantage of that approach is
that each time you write a test, you can be sure that from then on, *every* test is
run *each and every* time you test your code.  You are less likely to miss
things when you do it this way.

# Ok, back to the getters

We want to write some code to test our getters, but rather than putting it
in the existing main, we are going to add a main to our Rational class, just
to show that we can.     That is, our collection of java classes can have more
than one main, and we can choose which one to run at run time. (This is a bit
different from the C/C++ way of doing things.)

We add this main into our Rational class.  Note the special format
of the comment immediately before the class.  This is a *javadoc* comment.
We'll discuss javadoc comments more thoroughly in just a moment.

```java
  /**
     For testing getters.
   */

  public static void main (String [] args) {
    Rational r = new Rational(5,7);
     System.out.println("r.getNumerator()=" + r.getNumerator());
     System.out.println("r.getDenominator()=" + r.getDenominator());
  }                                                                       

```

We then add the getters.  For simple getters, placing the method definition
on a single line is typically an acceptable style choice, though not
all developers agree on this.  (In fact, there is little if anything that
"all developers agree on" when it comes to matters of style.  The best we can
do is try to suggest some style guidelines that are "typical".)

```java
    public int getNumerator() { return this.num; }
    public int getDenominator() { return this.denom; }
```

We run and see that works ok.  And that we can still run both `main` methods: the one in class `Main` and the one in class `Rational`:

```
-bash-4.3$ javac *.java
-bash-4.3$ java Rational
r.getNumerator()=5
r.getDenominator()=7
-bash-4.3$ java Main 9 13
r1 = 1/1
r2 = 9/13
-bash-4.3$
```

# Automating the build process with ant (build.xml)

With just two java files, it may seem like overkill to put a build
system in place, i.e. something like the make utility with a Makefile;
and to be honest, it really is.

However, our java systems are not going to stay small for long.
And it will be a lot easier to see what is going on with a build system
if we start while the whole code base is relatively small, instead of once
we have a huge application.

So, here goes.

During this course, we'll see two different build systems.

* The first is [Ant](https://ucsb-cs56-pconrad.github.io/topics/ant/).
    * The initial release of Ant was in 2000.
    * Ant was developed as a replacement for make/Makefiles for java systems.
    * If you understand make/Makefiles, Ant is straightforward
    
* The second is Maven.
    * The initial release of Ant was in 2004.
    * Maven combines a build tool with some of the functions
        of a package manager to manage .jar dependencies for you.
    * That's typically useful when you are using lots of 3rd party libraries.
    * In that respect, its similar to tools such as `pip` for Python,
      `npm` for node, or `bundler` for ruby.

In ex02, we'll start by adding a simple build.xml for Ant.

It will start out very simple.  It subsequent examples, we'll add in features
that push us closer to actual real world Java practice (which will necessitate
making the build.xml incrementally more complex at each stage.)

Finally, we'll take the plunge and switch over to Maven.

# A simple build.xml file for a simple project

Here is a very simple build.xml.  This build.xml doesn't exactly correspond to
"java best practices", but it does have the advantage of being very short,
and hence easy to follow and understand.


```xml
<project>

  <target name="compile">
    <javac srcdir="."
	   destdir="."
	   includeantruntime="false" />
  </target>

  <target name="clean">
    <delete>
      <fileset dir="." includes="*.class"/>
    </delete>
  </target>

  <target name="run">
    <java classname="Rational" classpath="."/>
  </target>
  
</project>
```


To use the ant build.xml file is easy.  Here are examples:

Run ant.  This does nothing except check the syntax of the build.xml file,
because we didn't specify a target on the command line, nor did we set a default
target in the build.xml file.  Unlike Makefiles, the default target has to be explictly set; it
doesn't just default to the first target in the file.

```
-bash-4.3$ ant
Buildfile: /cs/faculty/pconrad/github/cs56-rational-example/ex02/build.xml

BUILD SUCCESSFUL
Total time: 0 seconds
-bash-4.3$ 
```

Run `ant compile` to compile the code:

```
-bash-4.3$ ls
build.xml  Main.java  Rational.java  README.md
-bash-4.3$ ant compile
Buildfile: /cs/faculty/pconrad/github/cs56-rational-example/ex02/build.xml

compile:
    [javac] Compiling 2 source files to /cs/faculty/pconrad/github/cs56-rational-example/ex02

BUILD SUCCESSFUL
Total time: 0 seconds
-bash-4.3$ ls
build.xml  Main.class  Main.java  Rational.class  Rational.java  README.md
-bash-4.3$
```

Run `ant run` to run the main from the Rational class (that's what we specified in the target):

```
-bash-4.3$ ant run
Buildfile: /cs/faculty/pconrad/github/cs56-rational-example/ex02/build.xml

run:
     [java] r.getNumerator()=5
          [java] r.getDenominator()=7

BUILD SUCCESSFUL
Total time: 0 seconds
-bash-4.3$
```

To run the other Main, since it takes command line parameters, it is
better to run it interactively the "old fashioned way".  It is
possible to pass command line parameters into a build.xml file to run
a program with command line parameters, but it isn't straightforward.
It's easier to just run the program directly (outside of ant).  The
same is true for interactive programs that read from stdin
(`System.in` in java).  (As we'll see later, for GUIs, ant can be
used; we just have to specify the `fork="true"` attribute in the
`java` task. But we are getting ahead of ourselves.)

```
-bash-4.3$ java Main
Usage: java Main int denom
  int and denom should be integers; denom may not be zero.
  -bash-4.3$ java Main 2 7
  r1 = 1/1
  r2 = 2/7
  -bash-4.3$
```

To clean up, we type `ant clean`

```
-bash-4.3$ ls
build.xml  Main.class  Main.java  Rational.class  Rational.java  README.md
-bash-4.3$ ant clean
Buildfile: /cs/faculty/pconrad/github/cs56-rational-example/ex02/build.xml

clean:

BUILD SUCCESSFUL
Total time: 0 seconds
-bash-4.3$ ls
build.xml  Main.java  Rational.java  README.md
-bash-4.3$
```

In lecture, we may go over the following concepts related to the build.xml file above.  These
are things you should know for the exams in this course.  If you miss the lecture on these
topics, or have questions while reviewing for the exam, be sure you ask (and not at the last minute.)

* syntax of xml
    * tags: open, close, self-closing tags
    * attributes: attribute names, attribute values, can use either double or single quotes
    * elements, parent, child, contents, nesting
 
* concepts of an ant project
    * project top level element
    * targets vs. tasks
    * Using the [Apache Ant Task Documentation](https://ant.apache.org/manual/tasksoverview.html)

* basic tasks shown in this example
    * javac for compiling
    * delete for removing files
    * java for running java code

* other things you need to know
    * the use of `"."` to mean current directory
    * the meaning of `classpath`
    * the use of `<fileset dir="." includes="*.class"/>`
    * the meaning of `*.class` in the `fileset` syntax
  

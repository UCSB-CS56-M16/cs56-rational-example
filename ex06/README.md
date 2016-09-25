# cs56-rational-example/ex06


In ex06, we'll look at:

* Understanding inheritance: what we inherit from java.lang.Object
* Adding links to the Java Standard Libraries into our javadoc
* The situation with negative rational numbers, and negative parameters to our constructor
* Multiplying rational numbers

# Understanding inheritance

Lookat the javadoc we generated for the previous example, ex05,
available here: http://ucsb-cs56-m16.github.io/cs56-rational-example/ex05/javadoc/index.html

In particular, look at the Rational class.   You'll see that we have this section:

<div style="margin-left:3em; border: 1px solid #eee; width:80%;">
<h3 style="background-color: #dee3e9; border: 1px solid #d0d9e0;"> Methods inherited from class java.lang.Object </h3>
<pre>clone, equals, finalize, getClass, hashCode, notify, notifyAll, wait, wait, wait</pre>
</div>

We see that `Rational` inherits several methods from `java.lang.Object`.

* Every Java class, if it does not explictly extend another class, implicitly extends `java.lang.Object`.
* That is, it inherits methods from `java.lang.Object`.
* Inheritance is transitive (if a extends b, and b extends c, then a extends c).
* Thus, *every Java class* ultimately is an extension of `java.lang.Object`

In particular, we have ten methods we've inherited from java.lang.Object:

<pre>clone, equals, finalize, getClass, hashCode, notify, notifyAll, wait, wait, wait</pre>

One annoying thing: there are three `wait` methods.  They must differ in the number and type of parameters (overloading).
But we can't see any more detail unless we go directly to the Oracle javadoc for java.lang.Object.  It would be nicer
if our javadoc linked directly to the Oracle javadoc.  Fortunately, the javadoc task allows us to do that.  We'll do that next.

# Better javadoc: links to standard javadoc

Inside your `javadoc` target (i.e. `<target name="javadoc" ... >`  there is a nested element for the `javadoc` task.

Inside that `javadoc` task element, place this element. 

```xml
  <link href="http://docs.oracle.com/javase/8/docs/api/" />       
```

This URL is for Java 8.  If you are using a version of the Java SE
environment other than Java 8, adjust the URL accordingly.  (UCSB's
CMPSC 56 course is using Java 8 as of this writing, Summer 2016, but
check with your instructor if this page hasn't been updated since
then.)

Now, we want to regenerate our javadoc with `ant javadoc`.
Note, however, that the current javadoc target doesn't get rid of the
old javadoc; it just writes on top of the old one.  That means that if
we rename a class, or delete one, the old files might stick around.

It is cleaner to get rid of the old javadoc directory entirely before
we regenerate the new one.  We can adjust the javadoc target accordingly
by adding a `delete` task to the `javadoc` target, before we invoke
the javadoc task.

```
  <target name="javadoc" depends="compile" description="generate javadoc">
    <delete>
      <fileset dir="javadoc" />
    </delete>
    <javadoc destdir="javadoc">
      <fileset dir="src" >
	<include name="*.java"/>
      </fileset>
      <classpath refid="project.class.path" />
      <link href="http://docs.oracle.com/javase/8/docs/api/" />          
    </javadoc>
    <echo>
      javadoc written to file://${javadoc_absolute_path}/index.html
    </echo> 
  </target>

```

If we now generate the javadoc again with `ant javadoc`, we should see that
each time something appears such as `java.lang.Object`, or a method name
inherited from `java.lang.Object`, that text is a link to the Oracle documentation
for that class or method.

We can now differentiate the three different `wait` methods; if we click on each one,
we see immediately how they differ in the number and type of parameters.

Note the correct usages of *target* and *task* in the descriptions
in this section of the README.md for ex06.   This
is something you need to understand about javadoc
for the exams in this course.

We now turn to two elements of actual functionality of our class.

# Negative rational numbers

So far, we haven't talked at all about negative rational numbers. And, we haven't tested our gcd function on negative numbers.

We first have to think about what behavior we would want if negative numbers were passed to the constructor, either in the numerator,
denominator or both.   (We may also want to think about what happens if we pass zero (0) to the denominator, but we'll defer that to
ex07, since it will take us into a discussion of Exceptions in Java.)

We need to think about what we want as the internal representation, as well as what we want the toString() method to display.

Here is one set of rational choices (sorry for the pun):

* If both numerator and denominator are negative, just factor our the -1 before submitting to the gcd function.
* If only the numerator is negative, leave it alone.
* If only the denominator is negative, move the negative to the numerator.
* When calculating the gcd, move any negative in the denominator to the numerator first, then pass the absolute value of the numerator to the gcd function.   The factoring out of the gcd will still work, and we don't need to be concerned with how the gcd method will handle negative input.

On this last point: it might be a bit "lazy" to not be concerned about whether the gcd method works properly for negative numbers. We can
justify this laziness if we (a) determine that we don't *need* to use it on negative numbers, (b) protect ourselves by putting validation of the parameters into the function so that if someone did call it on a negative number, they'd find out that it isn't guaranteed to work on negative numbers.  We'll come back to that in ex07.

TODO...

# Multiplying rational numbers

TODO...


# Putting our javadoc online using github-pages

If you are working in a github.com repo, it is easy to put your javadoc online using github-pages, a value added feature of github.com.

It does involve using *branches*, a slightly more advanced feature of git, but only in the most basic way possible.

Here, I'll just explain the workflow quickly, and not go into a lot of detail.     We might go into more detail in lecture about what
is happening here.

To publish online, do this series of commands:

* Work in the master branch as usual
* Use `ant javadoc` to generate the javadoc
* Use the usual commands to push to master:
    * That is, `git add ...`,  `git commit -m "message"`, `git push origin master` 
* Use `git checkout gh-pages` to go to the `gh-pages` branch
    * The *first time* only, use `git checkout -b gh-pages` to create the branch.  That's a one-time thing.
* Use `git merge master` to bring in changes from the `master` branch into the `gh-pages` branch.
* Use `git push origin gh-pages` to push your changes to the `gh-pages` branch.

Then, your changes should be on github at:
*  <https://username.github.io/repo-name> if it is a personal repo
*  <https://orgname.github.io/repo-name> if it is an organizational repo

Note that if you are looking for the javadoc, you might have to add the exact directory plus the "index.html" to the repo name, otherwise you'll get a `404-Document not found`.   For example, in the case of this repo, the index to the javadoc for ex06 is under `/ex06/javadoc/index.html`, and the organization is `UCSB-CS56-M16` (at least as of this writing). So the URL is:

* <https://UCSB-CS56-M16.github.io/cs56-rational-example/ex06/javadoc/index.html>


# Looking ahead to ex07

In ex07, we'll look at Exceptions in Java by exploring how they can be used to enforce some assumptions we've been making about parameters to functions:
* We've been "assuming" that folks won't pass a zero for the denominator of our Rational object.  If they do, bad things might happenincluding division by zero.
* To prevent this we can throw a `java.lang.IllegalArgumentException` as soon as we detect that our assumption has been violated. The sooner you let the programmer know that something is wrong, the easier it will be for them to fix it.
* We can use the same approach for our `gcd` method, to ensure that it is only used on positive integers (the only ones we have tested it on.)
* We'll look at how to use JUnit with exceptions to test that constructors and methods throw the intended Exception under the intended circumstances.



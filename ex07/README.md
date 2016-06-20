# cs56-rational-example/ex07



In ex07, we'll look at Exceptions in Java by exploring how they can be used to enforce some assumptions we've been making about parameters to functions:
* We've been "assuming" that folks won't pass a zero for the denominator of our Rational object.  If they do, bad things might happenincluding division by zero.
* To prevent this we can throw a `java.lang.IllegalArgumentException` as soon as we detect that our assumption has been violated. The sooner you let the programmer know that something is wrong, the easier it will be for them to fix it.
* We can use the same approach for our `gcd` method, to ensure that it is only used on positive integers (the only ones we have tested it on.)
* We'll look at how to use JUnit with exceptions to test that constructors and methods throw the intended Exception under the intended circumstances.


# About Exceptions

In Java, all exceptions derive from java.lang.Exception, in the following inheritance hierarchy:


<ul style="margin:0; padding:0;">
 <li style="display:inline; list-style:none;">
   <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html" title="class in java.lang">java.lang.Object</a>
 </li>
 <li style="display:inline; list-style:none;">
  <ul style="margin-left:15px; padding-left:15px; padding-top:1px;">
   <li style="display:inline; list-style:none;">
     <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html" title="class in java.lang">java.lang.Throwable</a>
   </li>
   <li style="display:inline; list-style:none;">
    <ul style="margin-left:15px; padding-left:15px; padding-top:1px;">
     <li style="display:inline; list-style:none;"><a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Exception.html" title="class in java.lang">java.lang.Exception</a></li>
    </ul>
   </li>
  </ul>
 </li>
</ul>

There are two main categories of Exceptions:

* Checked Exceptions   
* Unchecked Exceptions (these all extend [java.lang.RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html))  

There are signficant differences between Checked Exceptions and Unchecked Exceptions, in terms of:
* The use case (i.e. why they are used by programmers, the circumstances in which they are useful)
* How the code must be written to handle one versus the other.

A complete discussion of that topic can be found in the Head First Java, 2nd Edition textbook, Chapter 11 ("Risky Behavior").  Exceptions are the focus of that entire chapter.

For this example, we'll defer a larger discussion of Exceptions.  We are dealing only with one particular exception, the exception `java.lang.IllegalArgumentException` (javadoc [here](https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalArgumentException.html)), which happens to be an Unchecked Exception.  We can see this in its inheritance hierarchy, which shows that it extends [java.lang.RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html):


<ul style="margin:0; padding:0;">
 <li style="display:inline; list-style:none;">
   <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html" title="class in java.lang">java.lang.Object</a>
 </li>
 <li style="display:inline; list-style:none;">
  <ul style="margin-left:15px; padding-left:15px; padding-top:1px;">
   <li style="display:inline; list-style:none;">
     <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html" title="class in java.lang">java.lang.Throwable</a>
   </li>
   <li style="display:inline; list-style:none;">
    <ul style="margin-left:15px; padding-left:15px; padding-top:1px;">
     <li style="display:inline; list-style:none;"><a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Exception.html" title="class in java.lang">java.lang.Exception</a></li>
     <li>
       <ul style="margin-left:15px; padding-left:15px; padding-top:1px;">
       <li style="display:inline; list-style:none;"><a href="https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalArgumentException.html" title="class in java.lang">java.lang.IllegalArgumentException</a></li>
       </ul>
     </li>
    </ul>
   </li>
  </ul>
 </li>
</ul>

To *throw* an exception in Java, we use the java keyword `throw`, followed by an instance of the the exception we want to throw.  It
is typical to use an inline constructor invocation to create that instance, as in this example.


```java
   if (denom == 0) {
     throw new IllegalArgumentException("denominator may not be zero");
   }
```

A few things to note about the example above:

* Exceptions typically have a constructor that takes a `java.lang.String` argument; in this string,
  we can provide some extra helpful information about the reason the exception is being thrown.
  In writing this string, we need to use good design; the tradeoff is brevity, versus helpfulness
  to the human that will be reading the message.  We should pack as much useful information as possible
  into the fewest number of characters.    Note that an exception typically includes a stack trace,
  so it may not be necessary to include the name of the constructor; that will be shown anyway.
* Throwing an exception typically ends the constructor or method invocation.  We don't need, and should not
   write, a return, or any other statement after a `throw`, since it will be unreachable.
* If a throw is inside the block of an `if`, it isn't really necessary to follow it with an `else`.  The `else`
  is implied by the fact that end the current constructor or method invocation when an exception is thrown.
* We don't have to use the full name
  `java.lang.IllegalArgumentException`; for any class that is in the
  package `java.lang`, we can leave off the `java.lang.` prefix.  It
  is as if we always have a `import java.lang.*` statement at the top
  of every Java source file.

So that's what the code will look like that throws an exception.  But first, let's
write some tests; TDD says "write the test first, and then write the code".  So let's
follow that practice.


# About Annotations

To write a JUnit test for an exception, we use annotations.   Annotations are those
things that start with an at sign (`@`).  We've already seen the annotations
`@Before` and `@Test` in this code from `RationalTest.java` (note that the ... is
not part of the code, but indicates some code is omitted.)


```java
  @Before public void setUp() {
    r_5_15 = new Rational(5,15);
    ...
  }

  @Test
  public void test_getNumerator_20_25() {
    assertEquals(4, r_20_25.getNumerator());
  }
```

These annotations come from the import statements:

```java
import org.junit.Test;
import org.junit.Before;
```

The javadoc for these annotations can be seen here:

* [org.junit.Before](http://junit.sourceforge.net/javadoc/org/junit/Before.html)
* [org.junit.Test](http://junit.sourceforge.net/javadoc/org/junit/Test.html)

# Writing JUnit tests for Exceptions

The easiest way to write a JUnit test for an Exception is to pass a parameter to the `@Test` annotation,
as shown here.


```Java
 @Test(expected = IllegalArgumentException.class)
 public void test_denom_zero() {
   Rational r = new Rational(1,0);
 }
```

This test will pass if an instance of `IllegalArgumentException` is thrown, and will fail if it is not thrown.

Some notes:
* This way of testing does not allow you to check whether the message in
  the exception is the expected one, i.e. whether it is `"denominator
  may not be zero", which would make sense, or `"Go Gauchos!"`, which
  while an admirable sentiment, is not what we are looking for here.
* The wiki associated with the junit-team's github repo has an article
  that explains some [more sophisticated ways of testing exceptions](https://github.com/junit-team/junit4/wiki/Exception-testing)
  that *do* allow us to check whether the message is the right one.
  We'll leave creating an example of that kind of testing to a future article, or perhaps
  as an "exercise for the student".
* The `.class` at the end of `IllegalArgumentException.class` doesn't refer to the file extension of a `.class` file, though it would be   understandable why you might think so. Instead, there it is a public property of every class name that refers to
  the object that *is* the class itself.  It is an instance of the parameterized class `Class<T>`
  (see javadoc here: [java.lang.Class<T>](http://docs.oracle.com/javase/8/docs/api/java/lang/Class.html), and this
  [StackOverflow answer](http://stackoverflow.com/questions/15078935/what-does-class-mean-in-java).)


We will add this test to our `src/RationalTest.java` file, and then test to see the test fail:

```
test:
    [junit] Testsuite: RationalTest
    [junit] Tests run: 16, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.096 sec
    [junit]
    [junit] Testcase: test_denom_zero(RationalTest):	FAILED
    [junit] Expected exception: java.lang.IllegalArgumentException
    [junit] junit.framework.AssertionFailedError: Expected exception: java.lang.IllegalArgumentException
    [junit]
    [junit]
    [junit] Test RationalTest FAILED				    
```

We then add this code into our constructor: 

```java
   if (denom == 0) {
     throw new IllegalArgumentException("denominator may not be zero");
   }
```

And we'll see the test pass:

```
test:
    [junit] Testsuite: RationalTest
    [junit] Tests run: 16, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.028 sec
    [junit]
```

With that done, we'll turn our attention to negative numerator and denominator.

# TODO: Negative num and denom


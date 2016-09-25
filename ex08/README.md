# cs56-rational-example/ex08

In ex08 we'll show how to write functions to multiply two rational numbers together.

After that, we'll take on the task of writing a comparison function,
with an eye towards being able to sort collections of rational
numbers.  That will take us into the land of "common denominators",
which is also something we'll need if we want to be able to add
rational numbers.

# Multiplying two rational numbers

Both C++ and Python allow the programmer to "overload" operators, essentially redefining operators such as +, *, - and / to have
particular meanings when applied to objects of particular types.

*Java does not allow operator overloading*.

This may be a good thing.  This article, for example, makes the argument that operator overloading is a form of ['syntactic heroin'](http://queue.acm.org/detail.cfm?id=1071738) (those are the author's words, not mine).

In any case, if we want to multiply two objects of type Rational, we are going to have to do it with methods.

There are two commonly used patterns here, which we'll illustrate by example.  Suppose we have two rational objects a and b:

```java
   Rational a = new Rational(3,4);
   Rational b = new Rational(5,7);
```

To set c to the product of a and b, there are two approaches.

The first is an instance method (non-static method) that operates on one object, and takes a second as a parameter. For example:

```java
   Rational c = a.times(b);
```

The second is a static method (class method) that operates on two objects:

```java
   Rational c = Rational.product(a,b);
```

It is important to note that in both cases, both a and b are left
unchanged.  We are creating a new instance of Rational, not mutating
an existing one.

It is also important to note that although both the `times` method and
`product` method here are not constructors, they do result in new
instances of `Rational` being created.  The constructor call will be
embedded inside the method, and a reference to the object will be
returned.

In C++, in such a circumstance, we would have to be very concerned
about memory leaks (i.e. ensure that there was a strategy for making
sure that the memory allocated was freed at an appropriate point, by
some other part of the application.  In Java, we are not entirely free
of the concern of memory leaks, but we can be much *less* concerned.
If at any point, there are not longer any live variables
(i.e. variables on the stack) that directly or indirectly reference
the memory allocated, the memory will be automatically eligible to be
freed.  If heap memory is needed, automatic garbage collection will
take place.

Our next step will be to write unit tests for both styles of method,
i.e. both the `times` and `product` methods noted above.  We'll see the
tests fail, and then see the tests pass.

# Unit tests for times

Here are some unit tests for the times method.  Note that
multiplying rational numbers is equivalent to multiplying fractions:
you multiply the numerators and the denominators and then
reduce.   Fortunately, the Rational constructor is already set up
to do the reduction for us.

These unit tests use the numbers already defined in the `setUp()` method
which, to remind, you looks like this:

```java
    @Before public void setUp() {
	r_5_15 = new Rational(5,15);
	r_24_6 = new Rational(24,6);
	r_3_7  = new Rational(3,7);
	r_13_4 = new Rational(13,4);
	r_20_25 = new Rational(20,25);
	r_0_1 = new Rational(0,1);
    }
```

As a side-note, a rookie mistake is to write this.  Why is this wrong?  (See if you can figure it out on your own.
We'll discuss it in lecture).

```java
    @Before public void setUp() {
	Rational r_5_15 = new Rational(5,15);
	Rational r_24_6 = new Rational(24,6);
	... etc...
    }
```

Ok, so here are some unit tests for times:

```java

    @Test
    public void test_r_5_15_times_r_3_7() {
	Rational r = r_5_15.times(r_3_7);
	assertEquals("1/7",r.toString());
    }

    @Test
    public void test_r_3_7_times_r_13_4() {
	Rational r = r_3_7.times(r_13_4);
	assertEquals("39/28",r.toString());
    }

    @Test
    public void test_r_m3_1_times_1_m3() {
	Rational r_m3_1 = new Rational(-3,1);
	Rational r_1_m3 = new Rational(1,-3);
	Rational r = r_m3_1.times(r_1_m3);
	assertEquals("1",r.toString());
    }
```

We run them, and at first they don't compile, so we add a stub.
The stub returns a value that is *not* the expected value of any test.

The point of the stub is to see the tests fail, and make sure that
they fail before they pass.  It is a way to "test the tests". It isn't
foolproof, but it is better than doing nothing.  In my own programming
practice it has often caught errors in my tests, so I've learned to
trust the practice.

```java
    public Rational times(Rational r) {
	return new Rational(-42,1);
    }
```

After seeing the tests fail:

```
test:
    [junit] Testsuite: RationalTest
    [junit] Tests run: 22, Failures: 3, Errors: 0, Skipped: 0, Time elapsed: 0.036 sec
    [junit]
    [junit] Testcase: test_r_5_15_times_r_3_7(RationalTest):	FAILED
    [junit] expected:<[1/7]> but was:<[-42]>
    [junit] junit.framework.AssertionFailedError: expected:<[1/7]> but was:<[-42]>
    [junit] 				      at RationalTest.test_r_5_15_times_r_3_7(RationalTest.java:126)
    [junit]
    ...				
```

We fix the code to some correct code and see the unit
tests pass:

```java
    public Rational times(Rational r) {
	return new Rational(this.num * r.num,
			    this.denom * r.denom);
    }
```

And our tests pass:

```
test:
    [junit] Testsuite: RationalTest
    [junit] Tests run: 22, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.051 sec
    [junit] 
```

# Doing the same for the product method

The product method is similar, except that we declare it
as a static method of the class.  We can use the same
tests, but in a different form:


```java

```

Our stub can be similar:

```java
```

And the final solution is similar as well.  The differences are:

* The use of the keyword `static`
* The fact that we have two parameters instead of one

```java
```

# What should you be able to do now?

Based on what you've seen here, you should be able to start with the code in the ex08 directory,
and add both tests and correct implementations of these methods to the class.

Note that for each method, you should add a reasonable number of tests, but no less than three.


| method                             | explanation                     |
|------------------------------------|---------------------------------|
| `public static int lcm(int a, int b)` | returns least common multiple of a and b. See [wikipedia discussion](https://en.wikipedia.org/wiki/Least_common_multiple#Computing_the_least_common_multiple) |
| `public Rational plus(Rational r)` | returns sum of this number plus r |
| `public static Rational sum(Rational a, Rational b)` | returns a+b |
| `public Rational minus(Rational r)` | returns this number minus r |
| `public static Rational difference(Rational a, Rational b)` | returns a-b |
| `public Rational reciprocalOf()`   | returns reciprocal (swap numerator and denominator).  If numerator if zero, throws an instance of `java.lang.ArithmeticException`  |
| `public Rational dividedBy(Rational r) | returns this number divided by r |
| `public static Rational quotient(Rational a, Rational b) | returns a divided by b |

Some hints to make things easier:

* (a - b) is equivalent to (a + (-1 * b))
* (a / b) is equivalent to (a * reciprocal(b))

So, don't repeat yourself:
* Multiplication and gcd are already defined for you in the example code.
* You need lcm to find a common denominator to add two rationals, so define lcm before addition.
* The lcm can be defined in terms of gcd and absolute value&mdash;see [wikipedia discussion](https://en.wikipedia.org/wiki/Least_common_multiple#Computing_the_least_common_multiple). Absolute value is predefined `public int Math.abs(int a)`
* Define addition before subtraction, and then define subtraction in terms of addition and multiplication.
* Define reciprocal before division, then define division as multiplication by the reciprocal.

You should be able to signify that you are finished by committing code to a github repo that contains a modified version of ex08, with all of the
following:
* a `build.xml` file (you shouldn't need to modify the example)
* a `src` subdirectory containing `Main.java`, `Rational.java`, and `RationalTest.java`
* a `lib` subdirectory contining the jar file for JUnit
* a `javadoc` subdirectory in which you have produced the javadoc by running `ant javadoc`

Furthermore, you should be able to follow the steps outlined in the [README.md for ex06](../ex06/README.md) to publish the javadoc to a gh-pages branch.

That is the basis of a reasonable lab assignment.

# Where we go from here

There are still many more topics to cover.   We will, in ex09 and beyond, cover:




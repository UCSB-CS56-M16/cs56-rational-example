# cs56-rational-example/ex05


# A bug?

Consider this code:

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

To be able to reduce fractions, we'll need a gcd method (greatest common divisor).
That's a good candidate to be a static method of the Rational class.


A static method of a class is used when we need a function that isn't necessarily
attached to any particular object.  It instead, belongs to the class itself.
This is similar to the way that static methods are used in C++.

# Start with unit tests

We'll start by adding some unit tests for a static gcd method to our RationalTest class.

These methods will fail to compile until we add a stub method to the Rational class.

That's ok.  That's the TDD way of doing things.

Here are some tests:

```Java

    @Test
    public void test_gcd_5_15() {
	assertEquals(5, Rational.gcd(5,15));
    }

        @Test
    public void test_gcd_15_5() {
	assertEquals(5, Rational.gcd(15,5));
    }

    @Test
    public void test_gcd_24_6() {
	assertEquals(6, Rational.gcd(24,6));
    }

    @Test
    public void test_gcd_17_5() {
	assertEquals(1, Rational.gcd(17,5));
    }
    
    @Test
    public void test_gcd_1_1() {
	assertEquals(1, Rational.gcd(1,1));
    }

    @Test
    public void test_gcd_20_25() {
	assertEquals(5, Rational.gcd(20,25));
    }
```

As expected, when we `ant compile`, we get:

```
compile:
    [javac] Compiling 3 source files to ...ex05/build
    [javac] .../ex05/src/RationalTest.java:33: error: cannot find symbol
    [javac]    assertEquals(5, Rational.gcd(5,15));
    ...
```

So, we add a stub to `src/Rational.java` along with some reasonable javadoc comments:

```
    /** 
	greatest common divisor of a and b
	@param a first number
	@param b second number
	@return gcd of a and b
    */
    public static int gcd(int a, int b) {
	return -42;
    }
```

Now we can compile, but our tests don't pass:

```
compile:
    [javac] Compiling 2 source files to ...ex05/build

test:
    [junit] Testsuite: RationalTest
    [junit] Tests run: 10, Failures: 6, Errors: 0, Skipped: 0, Time elapsed: 0.041 sec
    [junit]
    [junit] Testcase: test_gcd_5_15(RationalTest):	FAILED
    [junit] expected:<5> but was:<-42>
    [junit] junit.framework.AssertionFailedError: expected:<5> but was:<-42>
    [junit] at RationalTest.test_gcd_5_15(Unknown Source)
    [junit]
...				
```

By the way, that `(Unknown Source)` thing is annoying.  We can take care of that with this adjustment to our build.xml: on the javac line, include the `debug="true"` attribute:

```xml
    <javac srcdir="src"
	   destdir="build"
	   includeantruntime="false"
	   debug="true" >

```

Then after running `ant clean` and `ant test` again, we get the proper line numbers in our error messages:

```
compile:
    [javac] Compiling 3 source files to ...ex05/build

test:
    [junit] Testsuite: RationalTest
    [junit] Tests run: 10, Failures: 6, Errors: 0, Skipped: 0, Time elapsed: 0.026 sec
    [junit]
    [junit] Testcase: test_gcd_5_15(RationalTest):	FAILED
    [junit] expected:<5> but was:<-42>
    [junit] junit.framework.AssertionFailedError: expected:<5> but was:<-42>
    [junit]   at RationalTest.test_gcd_5_15(RationalTest.java:33)
```

Now its time to actually implement a gcd algorithm.

[Euclid's gcd algorithm](https://en.wikipedia.org/wiki/Euclidean_algorithm) should do the trick.

```
    public static int gcd(int a, int b) {
	if (a==0)
	    return b;
	else if (b==0)
	    return a;
	else
	    return gcd(b%a, a);
    }
```

Now it passes the tests:

```
test:
    [junit] Testsuite: RationalTest
    [junit] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.021 sec
    [junit]
```

So, next we defined some unit tests that test for the behavior we want.

Here, it may be helpful to make use of a `setUp` method that sets up some
instances of the Rational class that we can reuse in our tests:

Take a look at these features of the code:

* `setUp` method in `src/Rational.test`
* additional unit tests for toString, and modified tests for getters
* modifications to the two argument constructor, and the toString method

That's all we are going to do in ex05.

In ex06, we'll look at:

* Adding links to the Java Standard Libraries into our javadoc
* The situation with negative rational numbers, and negative parameters to our constructor
* Multiplying rational numbers
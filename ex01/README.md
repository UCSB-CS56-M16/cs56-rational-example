# cs56-rational-example/ex01

In this example, we show a basic main class that
will be our "driver" to show how the rational class
will be used.

We also provide a "stub" rational class that has
the minimum we need to get started:

* private instance variables for numerator and denominator

* a default constructor, `Rational()`, that takes no arguments, and
  creates an instance of class `Rational`.  We'll assume that this
  creates an instance of rational that represents the number `1`.

* a constructor that takes arguments for numerator and denominator

* code to get values for those arguments from the command line, and pass them to the constructor. 

* code that prints out these values using the `toString()` method of the class `Object`

We'll see that the built in `toString()` method is not of much use.  In the next
example, we'll override it to make something more useful.

(Note on overriding: See https://ucsb-cs56-pconrad.github.io/topics/java_overriding_vs_overloading/ )

The following summary shows compiling and running the simplest possible way
at a `bash` command line using plain old `javac` and `java`:

```bash
-bash-4.3$ ls
Main.java  Rational.java  README.md
-bash-4.3$ javac Main.java
-bash-4.3$ ls
Main.class  Main.java  Rational.class  Rational.java  README.md
-bash-4.3$ javac Rational.java
-bash-4.3$ ls
Main.class  Main.java  Rational.class  Rational.java  README.md
-bash-4.3$ java Main
Usage: java Main int denom
  int and denom should be integers; denom may not be zero.
-bash-4.3$ java Main 3 4
r1 = Rational@2a139a55
r2 = Rational@15db9742
-bash-4.3$
```

You can also use `javac *.java` which compiles both files at once.

A few things to note:
* We can only *run* a class that contains a `main` method.
* We do not specify the `.class` extension when running.
* We instead, specify the name of the class.
* The `Rational.class` and `Main.class` files remain separate.
    * There is no "linking" phase as in C++/C that combines them into a single program
    * There is a way to combine multiple `.class` files into a single `.jar` file, but this is
      not the same as linking, as explained [here](https://ucsb-cs56-pconrad.github.io/topics/java_jars/)
* When we write `System.out.println("r = " + r);` we are trying to do string concatentation on two instances
  of the class `java.lang.String`.   Since `r` is a reference to an instance of class `Rational`, it can't
  be concatenated to a String.  In this case, the JVM will try to invoke a `toString()` method on the
  object to convert it to a String.
* The `toString()` method that gets invoked here is actually part of the class `java.lang.Object`

# Every class (directly or indirectly) `extends Object`

When we write `public class Foo extends Bar {` it signifies that Bar is the parent class of Foo, i.e. the Foo inherits from Bar.

If we write just `public class Foo`, unlike in C++ where that would signify that Foo has no parent class,
in Java, any time we write this, it is the same as writing `public class Foo extends java.lang.Object`.

Notes:
* Classes that start with `java.lang.` are part of the Java Language.
* That includes `java.lang.String` and `java.lang.Object`, just to name a couple.
* We don't have to write `java.lang.Object`; for any class that is part of the `java.lang.` package, we can leave off the `java.lang.` prefix, and just write `Object`.


One implication of this is that nearly every instance of every class in Java
has a `public String toString()` method, because:

* If it doesn't have its own, it inherits one.
* It may inherit one from its parent class, grandparent class, great-grandparent class, etc.
* But in the limit, if none of those classes have one, there will always be one in java.lang.Object.
* Ok, there are bizarre corner cases where someone deliberately tries to mess this up, say by declaring a private toString() method and making it inaccessible to us.  But mostly, you can rely on there being one.

The thing is, the toString() method we inherit from java.lang.String is seldom what we want.

For example, for a object of type `Rational` with numerator 4, and denominator 3, as the output of:

```Java
   System.out.println("r = " + r); // implicit r.toString()
```

We want to see:

```
r1 = 4/3
```

Instead, we get:

```
r1 = Rational@2a139a55
```

To fix this, we need to override the toString method.  We'll do this in example [ex02](../ex02)


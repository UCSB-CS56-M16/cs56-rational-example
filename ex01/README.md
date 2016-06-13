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
-bash-4.3$ javac Rational.java
-bash-4.3$ ls
Main.class  Main.java  Rational.class  Rational.java  README.md
-bash-4.3$ java Main
Usage: java Main int denom
  int and denom should be integers; denom may not be zero.
  -bash-4.3$ java Main 3 4
  -bash-4.3$ java Rational
  Error: Main method not found in class Rational, please define the main method as:
     public static void main(String[] args)
     or a JavaFX application class must extend javafx.application.Application
     -bash-4.3$
```


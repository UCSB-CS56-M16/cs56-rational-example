# cs56-rational-example

This is a set of tutorial examples of a class for Rational Numbers.  Illustrates various concepts/techinques of Java Coding (a list of these appears below.)

These examples are intended as support for a course in which there are also reading assignments and lectures; this repo is not intended to be a stand-alone curriculum for learning Java.  

Having said that, if you follow the code examples in order, and look up the concepts that are new to you at each step, that will probably go a long way towards learning some important basic concepts of Java programming.

# Guide to this set of Examples

* Top level directory contains 
    * this `README.md` file
    * subdirectories `ex01`, `ex02`, etc. one per example
    * support files for continuous integration (.travis.yml)
    
* To follow these examples, consult each README.md in each subdirectory (ex01/README.md) etc. in order, for further information.

# Basic description of the Rational class

The Rational class provides an abstraction for a rational number, that is one that has an integer numerator, and a non-zero integer
denominator.  For a more formal definition, consult the [Wikipedia article on Rational Numbers](https://en.wikipedia.org/wiki/Rational_number).

This tutorial will not try to motivate *why* we need a class for rational numbers.   We will assume that there is some need to represent these in a program.   We'll assume that once some Java code has gotten input from somewhere for the numerator and denominator of two rational numbers, it needs to:
* Constructor objects that represent these two rational numbers
* Compute new rational numbers representing their sum, difference, and product.   
* Output those rational numbers in some way to the user.

We'll assume that instances of the class are immutable, i.e. we provide a way to construct objects, and getters for numerator and denominator, but no setters for numerator and denominator.

We'll assume that when instances of Rational are constructed, that if there are any common factors between the numerator and denominator, they are factored out.  That is, the rational is represented internally in reduced form, and the getters for numerator and denominator reflect this.  Example:
* Suppose we create a Rational with `Rational r=new Rational(2,4);` 
* Then, `r.toString()` will return `"1/2"`
* The getters for numerator and denominator will return `1` and `2`, respectively.

We'll assume the following rules for negative numbers: 
* The negative sign is attached to the numerator, and the denominator is always positive.  
* Thus getNumerator() can return either a positive, zero, or negative integer, while getDenominator() always returns a strictly positive integer.

We'll also assume that, as a straightforward way of "outputting" rational numbers, a toString() method suffices, with these formatting rules:
* The representation is simply `numerator/demoninator`, e.g. `3/4`, `-6/7`.   
* We'll also assume that when the denominator is 1, that it should be omitted, e.g. `2`, and `0`, not `2/1` and `0/1'
* We'll assume that the negative sign, when present, always appears in the numerator

We'll leave the "quotient" of two rational numbers as a very straightforward programming exercise.

In the first few examples, not all of these rules may yet be in place.  We'll add those incrementally.  Also, as we proceed through the examples, we may make additional assumptions, set additional goals, and/or place additional restrictions.  Those will be noted in the README.md for each example.

# Java Language Concepts 

This is a work in progress; not all may be covered yet.  Will update this list with reference to the first example where each concept is introduced as work on this repo progresses.

1. private data members  (ex01)
1. public constructors (ex01)
1. toString method (ex02)
1. public getters (ex03)
1. immutable objects (throughout, but discussed in ex03)
1. JUnit testing (ex03)
1. static methods (class level)
1. equals vs. == 
1. proper way to override equals and hashcode
1. exceptions (IllegalArgumentException)
1. comparable interface
1. creating an arraylist of rationals
1. sorting an arraylist of rationals

Java Language Toolset Skills

1. Compiling/Running by hand with `javac` and `java` (ex01)
1. Ant (ex02)
1. Running from a jar file (ex02)
1. Javadoc (ex02)
1. JUnit jars (ex03)
1. cyber-dojo.org  (for divisible, gcd)
1. Packages
1. Maven


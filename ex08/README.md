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

# TODO... finish ex08


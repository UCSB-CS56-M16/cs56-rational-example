This file goes with the first part of ex04.

It shows the full build process for ex04 after we make the changes to the Makefile
to account for putting the `.java` files in `src` and the `.class` files in `build`.


```
ex04 pconrad$ ls
README.md	       build		build.xml	lib		src
ex04 pconrad$ ant -p
Buildfile: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/build.xml

Main targets:

 clean    clean up the project
 compile  compile the code
 jar      create a jar file
 run      run the main
 test     run JUnit tests
ex04 pconrad$ ls build
ex04 pconrad$ ant compile
Buildfile: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/build.xml

compile:
    [javac] Compiling 3 source files to /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/build

BUILD SUCCESSFUL
Total time: 0 seconds
ex04 pconrad$ ls build
Main.class	       		Rational.class		RationalTest.class
ex04 pconrad$ ant jar
Buildfile: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/build.xml

compile:

jar:
      [jar] Building jar: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/build/rational.jar

BUILD SUCCESSFUL
Total time: 0 seconds
ex04 pconrad$ ls build
Main.class	       		Rational.class		RationalTest.class	rational.jar
ex04 pconrad$ ant run
Buildfile: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/build.xml

run:
     [java] r.getNumerator()=5
     [java] r.getDenominator()=7

BUILD SUCCESSFUL
Total time: 0 seconds
ex04 pconrad$ java -jar build/rational.jar
r.getNumerator()=5
r.getDenominator()=7
ex04 pconrad$ ant test
Buildfile: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/build.xml

compile:

test:
    [junit] Testsuite: RationalTest
    [junit] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.018 sec
    [junit]

BUILD SUCCESSFUL
Total time: 0 seconds
ex04 pconrad$ ant clean
Buildfile: /Users/pconrad/github/UCSB-CS56-M16/cs56-rational-example/ex04/build.xml

clean:

BUILD SUCCESSFUL
Total time: 0 seconds
ex04 pconrad$ ls build
ex04 pconrad$
```
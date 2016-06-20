public class Main { // implicit: public class Main extends object
    
    public static void main (String [] args) {

	String usageMessage =
	    "Usage: java Main int denom\n" +
	    "  int and denom should be integers; denom may not be zero.";
	
	if (args.length != 2) {
	    System.err.println(usageMessage);
	    System.exit(1);
	}

	int num=1, denom=1; // must initialize to avoid "might not..." error
	try {
	    num = Integer.parseInt(args[0]);
	    denom = Integer.parseInt(args[1]);
	} catch (NumberFormatException e) {
	    System.err.println(usageMessage);
	    System.exit(2);
	}

	Rational r1 = new Rational();
	Rational r2 = new Rational(num, denom);

	System.out.println("r1 = " + r1); // implicit: r1.toString()
	System.out.println("r2 = " + r2); // implicit: r2.toString()
	
    } // main

}

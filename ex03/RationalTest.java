import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RationalTest {
  @Test
	  public void test_getNumerator_1() {
	  Rational r=new Rational(4,5);
	  assertEquals(4, r.getNumerator());
  }

  @Test
	  public void test_getNumerator_2() {
	  Rational r=new Rational(7,3);
	  assertEquals(7, r.getNumerator());
  }

  @Test
	  public void test_getDenominator_1() {
	  Rational r=new Rational(4,5);
	  assertEquals(5, r.getDenominator());
  }

  @Test
	  public void test_getDenominator_2() {
	  Rational r=new Rational(7,3);
	  assertEquals(3, r.getDenominator());
  }

}
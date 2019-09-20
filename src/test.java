public class test {
	public static void main(String[] args) {
		String regex ="<.*>";
		String test = "<name>";
		System.out.println(test.matches(regex));
	}
}

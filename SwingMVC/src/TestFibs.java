
public class TestFibs {
	
	public static void main (String[] args){
		for (int i=1; i< 51; i++) {
			System.out.println("The " + i + " number in the Fib. Seq. is " + findSize(i));
		}
	}
	
	public static int findSize(int n) {
		if (n == 1 || n == 2) {
			return 1;
		}
		else {
			int first = 1;
			int second = 1;
			int total = 0;
			for (int i=3; i<=n; i++) {
				total = (first +  second);
				first = second;
				second = total;
			}
			return total;
		}
	
	}

}





package ca.uwo.eng.se2205b;

/**
* Problem #1: FizzBuzz
*/
public class FizzBuzz {

    /**
     * Driver method for FizzBuzz
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            System.out.print(i + "\t");
            if ((i % 3) == 0) {
                if (0 == (i % 5)) {
                    System.out.println("FizzBuzz");
                } else {
                    System.out.println("Fizz");
                }
            } else if ((i % 5) == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println("");
            }
        }
    }

}

package ca.uwo.eng.se2205b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
* Problem #2: Finds prime numbers and checks if they are palindromes.
*/
public class PalindromicPrime {

    /**
     * Creates an iterator that returns prime palindrome numbers.
     *
     * @return Non-{@code null} iterator to get palindrome prime numbers.
     */

    public Iterator<Integer> palindromeIterator() {
        return new PrimeIterator();
    }

    private static class PrimeIterator implements Iterator<Integer> {
        private static final int NUMPPS = 100;
        private  int[] palindromicPrime = new int[NUMPPS];
        private boolean[] prime;
        private int num = 0;

        private PrimeIterator(){
                prime = new boolean[100000];
                Arrays.fill(prime, true);

                for(int i=2;i<100000;i++){
                    if(prime[i]) {
                        for (int j = i; j < 100000; j += i) {
                            if(j != i) {
                                prime[j] = false;
                            }
                        }
                    }
                }

                for(int i=2, j=0; i<100000 && j<100;i++){
                    if(prime[i] && isPalindrome(i)){
                        palindromicPrime[j]=i;
                        j++;
                    }
                }

        }

        @Override
        public boolean hasNext() {
            if (num % 10 == 0) {
                System.out.println();
            }
            return num < 100;
        }

        @Override
        public Integer next() {
            return palindromicPrime[num++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("");
        }

        private boolean isPalindrome(int curr) {
            String a = Integer.toString(curr);
            String b = new StringBuffer(a).reverse().toString();
            return a.equals(b);
        }


    }


    public static void main(String[] args) {
        PalindromicPrime generator = new PalindromicPrime();
        Iterator<Integer> primeIter = generator.palindromeIterator();

        while (primeIter.hasNext()) {
            System.out.print(primeIter.next() + "\t");
        }
    }


}

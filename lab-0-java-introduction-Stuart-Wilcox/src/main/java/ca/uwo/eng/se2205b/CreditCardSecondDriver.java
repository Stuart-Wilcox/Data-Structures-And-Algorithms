package ca.uwo.eng.se2205b;

/**
 *Second driver for testing of fixed credit card numbers
 */
public class CreditCardSecondDriver extends CreditCardValidator{
    public static void main(String[] args){
        long[] myCards = {54321L, 4388576018402626L, 4111111111111111L, 5500000000000004L, 340000000000009L};
        CreditCardValidator validator = new CreditCardValidator();
        for(int i=0;i<5; i++) {
            System.out.println(validator.Validate(myCards[i]));
        }
    }
}

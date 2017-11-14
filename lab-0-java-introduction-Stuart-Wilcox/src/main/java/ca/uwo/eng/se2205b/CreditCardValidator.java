package ca.uwo.eng.se2205b;

import java.util.Scanner;

/**
* Problem #3: Validates a Credit Card
*/
public class CreditCardValidator {
    /**
     * Compute if the number is a valid Credit Card Number.
     *
     * @param number Credit Card number to validate.
     *               return Non-{@code null} enum of the type of credit card.
     */
    private enum CardType {
        //Visa, Master Card, AMEX, and Discover ENUM
        VISA, MASTERCARD, AMEX, DISCOVER
    }

    private int DoubleAndAdd(int num) {
        num *= 2;//double the number
        if (num > 9) {
            num -= 10;//if the number is greater than 10 then add the digits
            num++;//the first digit will always be 1 (ie. 12 => 1+2 , 18 => 1+8 , etc...)
        }
        return num;
    }

    protected CardType Validate(long number) {
        String num = Long.toString(number);//change the representation to string to work with it
        num = new StringBuffer(num).reverse().toString();//reverse the string (since we check right to left)
        int sz = num.length();

        if(sz<13 || sz>16){
            return null;//the number is too short or too long
        }

        int sum = 0;//hold the sum for later

        for (int i = 1, j = 0; i <= sz; i++) {
            if(i%2 == 0){
                sum += DoubleAndAdd(num.charAt(i-1)-'0');//add the even numbers (DOUBLED and ADDED)
            }
            else{
                sum += num.charAt(i-1) - '0';//add the odd numbers
                j++;
            }
        }



        int FirstTwoDigits = (num.charAt(sz-1)-'0')*10 + (num.charAt(sz-2)-'0');//integer to represent first two digits (which are actually last two since we reverse num)

        if(sum%10 != 0){
            return null;//the number is invalid so we return null
        }

        //find out what card types
        if(FirstTwoDigits==34){
            return CardType.AMEX;
        }
        else if(FirstTwoDigits >39 && FirstTwoDigits<50){
            return CardType.VISA;
        }
        else if(FirstTwoDigits >49 && FirstTwoDigits<60){
            return CardType.MASTERCARD;
        }
        else if(FirstTwoDigits>59 && FirstTwoDigits<70){
            return CardType.DISCOVER;
        }
        else{
            return null;//the card type is invalid so we return null
        }
    }

    public static void main(String[] args) {
        long someNum = 4388576018410707L;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a credit card number to validate:");
        someNum = scanner.nextLong();
        CreditCardValidator a = new CreditCardValidator();
        System.out.println(a.Validate(someNum) + "\n");


    }

}



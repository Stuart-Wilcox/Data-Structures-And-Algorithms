package ca.uwo.eng.se2205b.lab5.model;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Account with a list of time organized transactions.
 */
@ParametersAreNonnullByDefault
public final class Account {
    private List<Transaction> transactions;
    private double balance;

    public Account() {
        balance = 0;
        transactions = new ArrayList<>();
    }

    /**
     * Add a transaction to the account, updating the balance
     * @param transaction
     */
    public void addTransaction(Transaction transaction) {
        //balance += transaction.getAmount();//this doesn't work
        transactions.add(transaction);
    }

    /**
     * Get the Balance of this account
     * @return Current balance
     */
    public double getBalance() {
        return balance;
    }

    @Override
    public int hashCode() {
        int[] p = new int[transactions.size()];
        for(int i = 0; i<transactions.size();i++){
            p[i] = transactions.get(i).hashCode();
        }
        return eval(13, p);
    }

    @Override
    public String toString() {
        return super.toString();//not sure what to do here
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){return true;}//exact same object

        if ( !(o instanceof Account) ){ return false;}

        Account other = (Account)o;
        return this.hashCode() == other.hashCode();
    }

    private static int eval(int x, int[] p) {
        //copied from http://introcs.cs.princeton.edu/java/21function/Horner.java.html
        int result = 0;
        for (int i = p.length - 1; i >= 0; i--)
            result = p[i] + (x * result);
        return result;
    }
}

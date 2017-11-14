package ca.uwo.eng.se2205b.lab5.model;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import java.time.LocalDateTime;
import java.util.Currency;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a Transaction
 */
@ParametersAreNonnullByDefault @Immutable
public final class Transaction {

    private final LocalDateTime dateTime;
    private double value;
    private final Currency amount;

    public Transaction(LocalDateTime dateTime, Currency amount) {
        this.dateTime = checkNotNull(dateTime, "dateTime == null");
        this.amount = checkNotNull(amount, "amount == null");
    }

    /**
     * The time this transaction took place.
     * @return Date and time of a transaction.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * The size of the transaction
     * @return
     */
    public Currency getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){return true;}//exact same object

        if ( !(o instanceof Transaction) ){ return false;}

        Transaction other = (Transaction) o;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        int p[] = new int[2];
        p[0] = dateTime.hashCode();
        p[1] = amount.hashCode();
        return eval(13, p);
    }

    @Override
    public String toString() {
        return amount.toString() + ' ' + dateTime.toString();
    }

    private static int eval(int x, int[] p) {
        //copied from http://introcs.cs.princeton.edu/java/21function/Horner.java.html
        int result = 0;
        for (int i = p.length - 1; i >= 0; i--)
            result = p[i] + (x * result);
        return result;
    }
}

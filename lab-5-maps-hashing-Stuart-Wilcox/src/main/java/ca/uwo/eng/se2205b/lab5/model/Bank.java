package ca.uwo.eng.se2205b.lab5.model;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a Bank
 */
@ParametersAreNonnullByDefault
public final class Bank {
    private List<Person> customers;
    private String name;

    public Bank() {
        customers = new ArrayList<>();
    }


    /**
     * Get all of the accounts for a person
     * @param person Person whom has an account
     * @return Possibly empty {@code Set} of accounts for a Person
     *
     * @throws NullPointerException if {@code person} is {@code null}
     */
    public Set<Account> getAccounts(Person person) {
        return person.getAccount();
    }

    /**
     * Opens a new {@link Account} for an individual.
     * @param person Person who is on an account
     * @return New {@code Account}
     *
     * @throws NullPointerException if {@code person} is {@code null}
     */
    public Account openAccount(Person person) {
        return person.openAccount();
    }

    /**
     * Closes an account for an individual
     * @param person Person who is on an account
     * @param account Account to close
     *
     * @throws NullPointerException if {@code person} or {@code account} is {@code null}
     * @throws AccountCloseException if the Account can not be closed
     */
    public void closeAccount(Person person, Account account) throws AccountCloseException {
            person.closeAccount(account);
    }

    /**
     * Get <em>all</em> of the wealth of a person, the total of all of a person's accounts.
     * @param person Non-{@code null} person to get the total wealth of.
     * @return Total wealth
     *
     * @throws NullPointerException if {@code person} is {@code null}
     */
    public double getTotalWealth(Person person) {
        return person.getNetWorth();
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){return true;}//exact same object

        if ( !(o instanceof Bank) ){ return false;}

        //this uses a ton of time and memory (potentially a lot of delegation)
        Bank other = (Bank)o;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {

        //this takes a bunch of resources to compute (alot of stuff getting delegated)

        int[] p = new int[customers.size()+1];
        p[0] = name.hashCode();

        for (int i = 0; i < customers.size(); i++) {
            p[i] = customers.get(i).hashCode();
        }
        return eval(13, p);
    }

    private static int eval(int x, int[] p) {
        int result = 0;
        for (int i = p.length - 1; i >= 0; i--)
            result = p[i] + (x * result);//Horner's method
        return result;
    }

}

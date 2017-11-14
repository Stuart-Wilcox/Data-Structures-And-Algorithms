package ca.uwo.eng.se2205b.lab5.model;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Represents a Person
 */
@ParametersAreNonnullByDefault
@Immutable
public final class Person {

    private final String firstName;
    private final String lastName;
    private Set<Account> accounts;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<Account> getAccount(){
        return accounts;
    }

    public Account openAccount(){
        Account newAccount = new Account();
        accounts.add(newAccount);
        return newAccount;
    }

    public void closeAccount(Account account) throws AccountCloseException{
        if(!accounts.contains(account)){
            throw new AccountCloseException(account, "No such account exists.");
        }
        if(account.getBalance() < 0){
            throw new AccountCloseException(account, "Balance is below 0, account cannot close.");
        }
        accounts.remove(account);
    }

    public double getNetWorth(){
        double worth = 0;
        for (Account account:accounts) {
            worth += account.getBalance();
        }
        return worth;
    }

    @Override
    public int hashCode() {
        int[] p = new int[accounts.size() + 2];
        p[0] = firstName.hashCode();
        p[1] = lastName.hashCode();
        Iterator<Account> accountIterator = accounts.iterator();
        int count = 2;
        while(accountIterator.hasNext()){
            p[count] = accountIterator.next().hashCode();
            count++;
        }
        return eval(13, p);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){return true;}//exact same object

        if ( !(o instanceof Person) ){ return false;}

        Person other = (Person)o;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder name = new StringBuilder();
        name.append(firstName);
        name.append(' ');
        name.append(lastName);
        return name.toString();
    }

    private static int eval(int x, int[] p) {
        //copied from http://introcs.cs.princeton.edu/java/21function/Horner.java.html
        int result = 0;
        for (int i = p.length - 1; i >= 0; i--)
            result = p[i] + (x * result);
        return result;
    }
}

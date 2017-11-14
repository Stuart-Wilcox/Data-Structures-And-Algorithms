package ca.uwo.eng.se2205b.lab5;

import ca.uwo.eng.se2205b.lab5.model.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Iterator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Includes testing for the Banking Model
 */
public class ModelTests {
    MyHashMap<Integer, String> hm = new MyHashMap<>();

    @Test
    public void Test() {
        //Bank b = createModel();
        hm.put(1, "1");
        hm.put(2, "2");
        Iterator iter = hm.entrySet().iterator();
        hm.remove(2);
        hm.remove(1);
        while(iter.hasNext()){
            System.out.println(iter.next());
        }

    }

    private Bank createModel(){
        Bank b = new Bank();
        Person p = new Person("John", "Doe");
        b.openAccount(p);
        Account a = p.openAccount();

        return b;
    }
}

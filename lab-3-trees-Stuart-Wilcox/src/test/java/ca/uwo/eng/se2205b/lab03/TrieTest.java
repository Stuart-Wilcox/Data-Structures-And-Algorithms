package ca.uwo.eng.se2205b.lab03;


import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *  Tests for Trie implementation
 */
public class TrieTest {

    private final Trie underTest=new LinkedTrie();

    @Test
    public void sizeAndIsEmpty() throws Exception {
        // Check empty tree, after adding and removing elements
        assertEquals(underTest.size(), 0);//empty trie
        underTest.put("Zebra");
        assertEquals(underTest.size(), 1);//one element
        underTest.put("Tea");
        assertEquals(underTest.size(), 2);//two element
        Character a = 'a';
        for(int i = 97; i < 107 ; i++){
            underTest.put(a.toString());//add ten elements
            a++;
        }
        assertEquals(underTest.size(), 12);
        underTest.put("Tea");
        assertEquals(underTest.size(), 12);//added an element that already existed in the trie
    }

    @Test
    public void put() throws Exception {
        // Check what happens when adding and contains
        assertFalse(underTest.contains("Poop"));//empty list
        underTest.put("Cheese");
        assertTrue(underTest.contains("Cheese"));//make sure it really went in
        assertFalse(underTest.contains("Poop"));//was never put in

        underTest.put("Poop");
        assertTrue(underTest.contains("Poop"));//should be in there
        assertTrue(underTest.contains("poop"));//upper/lower casing shouldn't matter

        assertFalse(underTest.put("Cheese"));//is already in there
        assertFalse(underTest.put("cheese"));//case doesn't matter

        underTest.put("chose");
        assertTrue(underTest.contains("chose"));

        assertTrue(underTest.put("Act"));
        assertTrue(underTest.put("Acted"));
        assertTrue(underTest.put("Actor"));
        assertTrue(underTest.put("Actress"));
        assertTrue(underTest.put("Acting"));
        assertTrue(underTest.put("Actual"));
        assertTrue(underTest.put("Actuarial"));

        assertTrue(underTest.contains("Act"));
        assertTrue(underTest.contains("Acted"));
        assertTrue(underTest.contains("Actor"));
        assertTrue(underTest.contains("Actress"));
        assertTrue(underTest.contains("Acting"));
        assertTrue(underTest.contains("Actual"));
        assertTrue(underTest.contains("Actuarial"));

        assertFalse(underTest.contains("acte"));//part of an actuall word in the trie
    }

    @Test
    public void putAll() throws Exception {
        // make sure it works compared to put
        SortedSet<String> s = new TreeSet<>();
        //build a little set
        s.add("Act");
        s.add("Acted");
        s.add("Actor");
        s.add("Actress");
        s.add("Acting");
        s.add("Actual");
        s.add("Actuarial");


        assertEquals(underTest.putAll(s), 7);//make sure all 7 go in

        assertTrue(underTest.contains("Act"));
        assertTrue(underTest.contains("Acted"));
        assertTrue(underTest.contains("Actor"));
        assertTrue(underTest.contains("Actress"));
        assertTrue(underTest.contains("Acting"));
        assertTrue(underTest.contains("Actual"));
        assertTrue(underTest.contains("Actuarial"));

        assertEquals(underTest.putAll(s), 0);//make sure none go in
    }

    @Test
    public void getNextN() throws Exception {
        // Make sure you get the results you expect

        //build a little trie with some similar words
        assertTrue(underTest.put("Act"));
        assertTrue(underTest.put("Acted"));
        assertTrue(underTest.put("Actor"));
        assertTrue(underTest.put("Actress"));
        assertTrue(underTest.put("Acting"));
        assertTrue(underTest.put("Actual"));
        assertTrue(underTest.put("Actuarial"));

        //should have: act, acted, acting
        SortedSet<String> ts = underTest.getNextN("Ac", 3);

        assertEquals(ts.size(), 3);
        assertFalse(ts.contains("Actress"));//should not be in here
        assertTrue(ts.contains("Act"));
        assertTrue(ts.contains("Acted"));
        assertTrue(ts.contains("Acting"));

        //should have: act, acted, acting, actor, actress
        SortedSet<String> a = underTest.getNextN("Act", 5);
        assertEquals(a.size(), 5);
        assertTrue(a.contains("Act"));//*** should be included
        assertTrue(a.contains("Acted"));
        assertTrue(a.contains("Acting"));
        assertTrue(a.contains("Actor"));
        assertTrue(a.contains("Actress"));
        assertFalse(a.contains("Actual"));

        a = underTest.getNextN("b", 5);//there are no words starting with b in our trie
        assertEquals(a.size(), 0);

        a = underTest.getNextN("act", 0);//should return an empty list
        assertEquals(a.size(), 0);

        a = underTest.getNextN("a", -1);//returns all the words
        assertEquals(a.size(), 7);
    }

}
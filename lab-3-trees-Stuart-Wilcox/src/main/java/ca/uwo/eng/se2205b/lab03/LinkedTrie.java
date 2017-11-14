package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implement a Trie via linking Nodes.
 */
public class LinkedTrie implements Trie {
    private Character letter;
    private boolean isWord;
    private LinkedTrie[] children;
    private int size=0;


    public LinkedTrie() {
        size = 0;
        isWord = false;
        children = new LinkedTrie[26];
    }

    private LinkedTrie(Character l, boolean w){
        letter = l;
        isWord = w;
        children = new LinkedTrie[26];
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean put(String word) {
        LinkedTrie here = this;
        char[] w = word.toCharArray();

        for(int i = 0;i<word.length();i++){
            if(here.children[charToKey(w[i])] == null) {
                here.children[charToKey(w[i])] = new LinkedTrie(w[i], false);
            }
            here = here.children[charToKey(w[i])];
        }
        if(here.isWord){
            return false;
        }
        else{
            size++;
            here.isWord = true;
            return true;
        }
    }

    @Override
    public int putAll(SortedSet<? extends String> words) {
        if(words == null){
            throw new IllegalArgumentException();
        }
        int numWordsInserted = 0;
        int sz = words.size();
        for(int i = 0 ; i < sz ; i++){
            if(put(words.first())){
                numWordsInserted++;
            }
            words.remove(words.first());
        }
        return numWordsInserted;
    }

    @Override
    public SortedSet<String> getNextN(@Nonnull String prefix, int N) throws IllegalArgumentException {
        if(N<-1){
            throw new IllegalArgumentException();
        }
        if(N==0){
            return new TreeSet<>();
        }
        //follow a prefix down then return N (or less) possible words

        LinkedTrie here = this;
        char[] w = prefix.toCharArray();

        for(int i = 0 ; i < prefix.length() ; i++){
            if(here.children[charToKey(w[i])] == null) {
                return new TreeSet<>();//if there are no words fitting the prefix then return empty set
            }
            here = here.children[charToKey(w[i])];//move down the prefix
        }
        ArrayList<String> list = new ArrayList<>();//list to hold words later
        StringBuilder sb = new StringBuilder(prefix);

        getN(N, list, sb, here);//recursive call to helper function

        return new TreeSet<>(list);
    }

    private void getN(int N, ArrayList<String> list, StringBuilder sb, LinkedTrie trie){
        if(list.size() == N && N != -1){
            return;
        }
        if(trie.isWord){
            list.add(sb.toString());
        }
        for(int i = 0 ; i < 26; i++){
            if(trie.children[i]!=null){
                sb.append(keyToChar(i));
                getN(N,  list, sb, trie.children[i]);
                sb.deleteCharAt(sb.length()-1);
            }
        }
    }

    @Override
    public boolean contains(@Nonnull String word) throws IllegalArgumentException {
        if(!checkArg(word)){
            return false;
        }
        LinkedTrie here = this;
        char[] w = word.toCharArray();

        for(int i = 0 ; i < word.length() ; i++){
            if(here.children[charToKey(w[i])] == null){
                return false;
            }
            else{
                here = here.children[charToKey(w[i])];
            }
        }
        return here.isWord;
    }

    private int charToKey(char a){
        Character z = Character.toLowerCase(a);
        return z - 97;
    }
    private char keyToChar(int a){
        return (char)(a+97);
    }

    private boolean checkArg(String word){
        if(word == null){
            throw new IllegalArgumentException("Word is null");
        }
        if(word.length() == 0){
            throw new IllegalArgumentException("Word is empty");
        }
        for(int i = 0 ; i < word.length() ; i++){

            if(Character.toLowerCase(word.charAt(i)) < 97 || Character.toLowerCase(word.charAt(i))>122){
                return false;
            }
        }
        return true;
    }

}

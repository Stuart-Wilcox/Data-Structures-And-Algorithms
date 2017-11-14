package ca.uwo.eng.se2205b.lab5.model;

import ca.uwo.eng.se2205b.lab5.IHashMap;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Stu on 2017-03-09.
 */
public class MyHashMap<K,V> extends AbstractMap<K,V> implements IHashMap<K,V> {
    private MyEntry[] table;//store key with value in non growing structure
    private static final int DEFAULT_SIZE = 17;
    private final float defaultLoadFactorThreshold = 0.3f;
    private float loadFactor;
    private float loadFactorThreshold;
    private int numElements;
    private MySet entrySet;
    private ArrayList<MyEntry> list;

    public MyHashMap(){
        table = (MyEntry[]) Array.newInstance(MyEntry.class, DEFAULT_SIZE); //resize the table to the default size
        //table = new MyEntry[DEFAULT_SIZE];
        loadFactorThreshold = defaultLoadFactorThreshold;
        loadFactor = 0;
        numElements = 0;
        entrySet = new MySet();
        list = new ArrayList<>();
    }

    public MyHashMap(double num){
        loadFactorThreshold = (float)num;
        loadFactor = 0;
        //table = (MyEntry<K,V>[])(new Object[DEFAULT_SIZE]);//resize the table to the default size
        table = (MyEntry[]) Array.newInstance(MyEntry.class, DEFAULT_SIZE);
        loadFactor = 0;
        numElements = 0;
        entrySet = new MySet();
        list = new ArrayList<>();
    }

    public MyHashMap(int initialCapacity, double loadFactorThreshold){
        this.loadFactorThreshold = (float)loadFactorThreshold;
        loadFactor = 0;
        numElements = 0;
        entrySet = new MySet();
        list = new ArrayList<>();
        if(initialCapacity <= 0){
            table = (MyEntry[]) Array.newInstance(MyEntry.class, DEFAULT_SIZE);
        }
        table = (MyEntry[]) Array.newInstance(MyEntry.class, initialCapacity);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        if(null == null) {
            return entrySet = new MySet();
        }
        return null;
    }

    private int compressionFunction(int hashCode){
        return hashCode%table.length;
    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public boolean containsKey(Object o) {
        //Returns true if this map contains a mapping for the specified key.
        int index = myHash(((K)o));

        while(table[index%capacity()] != null){
            if(table[index].getKey().equals((K)o) && !table[index].isFlag()){
                return true;
            }
            index++;
            index %= capacity();
        }
        return false;
    }

    @Override
    public V get(Object o) {
        if(o==null){
            //return table[0].getValue();
            return null;
        }
        int index = myHash((K)o);

//        while(table[index%table.length] != null){
//            if(table[index%table.length].equals((K)o)){
//                break;
//            }
//            index++;
//        }
//
      if(table[index] == null){
          return null;
      }
        return table[index%table.length].getValue();
    }

    @Override
    public V put(K k, V v) {
        if(containsKey(k)){
            return v;
        }

        int index = myHash(k);//get the index from the hashFunction

        MyEntry entry = new MyEntry(k, v);//make a new entry to put into the hashTable
        index %= capacity();
        if(table[index] == null){
            table[index] = entry;//no need for linear probing, first spot works
            loadFactor = (float)++numElements/capacity();
            list.add(entry);
            if(loadFactor>=loadFactorThreshold){
                resize();
            }
            return null;
        }else{
//            while(table[index] != null){
//                if(entry.equals(table[index])){
//                    //entry already exists, so we preserve uniqueness
//                    return null;
//                }
//                index++;
//                index = index%table.length;
//            }
//            table[index] = entry;
            list.add(entry);
            MyEntry returnVal = table[index];
            table[index] = entry;
            return returnVal.getValue();
        }
    }

    @Override
    public V remove(Object o) {
        if(o == null){
            return null;
        }
        int index = myHash((K)o);
        if(table[index] == null){
            return null;
        }
        else{
//            while(table[index] != null){
//                if(table[index].getKey().equals(o)){
//                    table[index].setFlag(true);
//                    numElements--;
//                    list.remove(table[index]);
//                    entrySet.iterator().remove();
//                    return table[index].getValue();
//                }
//                index++;
//                index = index%table.length;
//            }
            MyEntry returnVal = table[index];
            table[index] = null;
            list.remove(table[index]);
            entrySet.iterator().remove();
            loadFactor = --numElements/capacity();
            return returnVal.getValue();
        }
    }

    @Override
    public void clear() {
        table = (MyEntry[]) Array.newInstance(MyEntry.class, DEFAULT_SIZE);
        numElements = 0;loadFactor = 0;
        list = new ArrayList<>();
        entrySet = new MySet();
    }

    @Override
    public boolean equals(Object o){

        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return entrySet().stream().mapToInt(Object::hashCode).sum();
    }

    @Override
    public double loadFactorThreshold() {
        return this.loadFactorThreshold;
    }

    @Override
    public double loadFactor() {
        return this.loadFactor;
    }

    @Override
    public int capacity() {
        return table.length;
    }

    private class MyEntry extends SimpleEntry<K,V>{
        private  boolean flag;

        public MyEntry(K k, V v) {
            super(k, v);
            flag = false;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }

    private int myHash(K k){
        return compressionFunction(k.hashCode());
    }

    private void resize(){
        //do stuff to resize to make sure the loadfactor is under the threshold
        int capacity = getDoubleNextPrime(numElements);
        while(numElements/capacity > loadFactorThreshold){
            capacity = getDoubleNextPrime(capacity);
        }
        loadFactor = numElements/capacity;
        MyEntry[] temp = table;
        numElements = 0;
        table = (MyEntry[]) Array.newInstance(MyEntry.class, capacity);
        for(int i = 0; i<temp.length; i++){
            if(temp[i] != null){
                put(temp[i].getKey(), temp[i].getValue());
            }
        }

    }

    private int getDoubleNextPrime(int n){
        n *= 2;
        n++;
        while(!isPrime(n)){
            n++;
        }
        return n;
    }

    private boolean isPrime(int n){
        int root = (int)Math.sqrt(n);

        for(int i = 2;i<root ; i++){
            if(i%n == 0){
                return false;
            }
        }
        return true;
    }

    private class MySet extends AbstractSet<Entry<K,V>>{
        private Iterator<MyEntry> it;
        private class MyIterator implements Iterator<Entry<K,V>> {
            private MyEntry entry;
            MyIterator(){
                it = list.iterator();
            }
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }
            @Override
            public MyEntry next() {
                return entry = it.next();
            }
            @Override
            public void remove() {
                if(entry == null || numElements == 0){
                    return;
                }
                try {
                    it.remove();
                    MyHashMap.this.remove(entry.getKey());
                }catch(IllegalStateException e){
                    return;
                }
            }
        }
        @Override
        public Iterator<Entry<K,V>> iterator() {
            return new MyIterator();
        }

        @Override
        public int size() {
            return numElements;
        }
    }
}

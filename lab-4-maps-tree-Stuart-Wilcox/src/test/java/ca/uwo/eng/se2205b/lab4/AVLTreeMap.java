package ca.uwo.eng.se2205b.lab4;

import java.util.*;

/**
 * Created by Stu on 2017-03-09.
 */
public class AVLTreeMap<K,V> extends AbstractMap<K,V>{
    private AVLTree<Entry<K,V>> tree;

    AVLTreeMap(){
        tree = new AVLTree<>();
    }

    //non-static method cannot be referenced from a static context
    //Comparator<Entry<K,V>> comparator = Comparator.comparing(Entry::getKey, Comparator.naturalOrder());

    @Override
    public Set<Entry<K, V>> entrySet() {
        //returns a Set of SimpleEntries from the Map
        return new EntrySet<Entry<K,V>>();
    }

    @Override
    public int size(){
        return tree.size();
    }

    @Override
    public V get(Object var1) {
        return tree.get(var1).getValue();
    }

    @Override
    public V put(K var1, V var2) {
        if(tree.put(new SimpleEntry<K, V>(var1, var2))){
            return var2;
        }
        return null;
    }

    @Override
    public V remove(Object var1) {
        if(tree.remove((SimpleEntry<K,V>)var1)){
            return (V)var1;
        }
        return null;
    }

    @Override
    public boolean containsKey(Object var1) {
        return tree.contains(new SimpleEntry<K, V>((K)var1, null));
    }

    @Override
    public boolean equals(Object var1) {
        return this.tree.equals(var1);
    }

    @Override
    public int hashCode() {
       return tree.hashCode();
    }
}

package ca.uwo.eng.se2205b.lab01;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.AbstractList;

/**
 * Array List implementation
 */
public class MyArrayList<T> extends AbstractList<T>{

    private T[] list;//underlying array container
    private int capacity;//used for tracking overflow
    private int size;

    @SuppressWarnings("unchecked")
    public MyArrayList(@Nonnull List<? extends T> base) {
        //constructor
        list = (T[])(base.toArray());//make a new array
        size = list.length;
        capacity = list.length;
        if(capacity<=0){
            list = (T[])(new Object[1]);
            capacity = 1;//capacity must always be at least 1
        }
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(int initialCapacity) {
        if(initialCapacity<0){
            throw new IllegalArgumentException("");
        }
        //size constructor
        size = 0;//no elements in the list
        if(initialCapacity<=0){initialCapacity=1;}
        capacity = initialCapacity;//set capacity
        list = (T[])(new Object[initialCapacity]);//create underlying array
    }

    public void add(int index, T item){

        if(index<0||index>size){
            throw new IndexOutOfBoundsException("");
        }

        if(atCapacity() || index>=capacity){
            increaseSize();//the list is at capacity o we enlarge it to contain the new item
        }

        //insert element at indexed pos then shift everything over (if needed)

        for(int i=size-1;i>=index;i--){
            list[i+1]=list[i];
        }
        list[index]=item;//set the item in the location
        size++;//increase size

    }

    public T set(int i, T item){
        if(i<0||i>=size){
            throw new IndexOutOfBoundsException("");
        }
        T returnVal = list[i];
        list[i]=item;
        return returnVal;
    }

    public T remove(int i){
        if(i<0||i>=size){
            throw new IndexOutOfBoundsException("");
        }
        T returnVal = list[i];//keep to return later
        for(int j=i;j<size - 1;j++){
            list[j]=list[j+1];//shift everything over
        }
//        list[--size]=null;//decrement size and eliminate last position
        size--;
        return returnVal;
    }

    public T get(int i){
        if(i<0 || i>=size){
            throw new IndexOutOfBoundsException("");
        }

        return list[i];
    }

    public int size(){
        return size;
    }

    public String toString(int i){
        return list[i].toString();
    }

    public boolean equals(MyArrayList<T> other){

        // Check if the type of `o` is a List, if not, return false, they can't be equal
        if (!List.class.isAssignableFrom(other.getClass())) return false;


        if(size() != other.size()){
            return false;//cannot be equal if they are different sizes
        }
        for(int i=0;i<size();i++){
            if(get(i) != other.get(i)){
                return false;
            }
        }
        return true;
    }

    private void increaseSize(){
        //private function used for dealing with overflow
        T[] temp = (T[])(new Object[capacity*2]);
        System.arraycopy(list, 0, temp, 0, size);
        list = temp;
        capacity*=2;
    }

    private boolean atCapacity(){
        return size==capacity;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void clear(){
        list = (T[])(new Object[0]);//make the list an empty one
        size = 0;
        capacity = 1;
    }
}


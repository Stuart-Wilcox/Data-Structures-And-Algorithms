package ca.uwo.eng.se2205b.lab01;

import com.sun.istack.internal.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.AbstractList;

/**
 * Doubly linked list implementation
 */
public class MyLinkedList<T> extends AbstractList<T>{

    private Node _head;
    private Node _tail;
    private int _size;

    private class Node {
        private T element;
        private Node next;
        private Node prev;
        Node(){next = null;prev=null;element=null;}
        Node(T element) {
            this.element = element;
        }
        Node(T element, Node next, Node prev){
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
        Node(Node node){
            this.element = node.GetElement();
            this.next = node.GetNext();
            this.prev = node.GetPrev();
        }
        Node GetNext() {
            return this.next;
        }
        void SetNext(Node next) {
            this.next = next;
        }
        T GetElement(){return this.element;}
        void SetElement(T el){this.element = el;}
        Node GetPrev(){return this.prev;}
        void SetPrev(Node prev){this.prev = prev;}
    }


    public MyLinkedList(List<? extends T> base) {
        _head = new Node();
        _tail = _head;//head and tail are the same node
        _size = 0;
        for(int i=0;i<base.size();i++){
            add(i,base.get(i));//fill the list with elements from base list
        }
    }

    @Override
    public int size(){return _size;}

    @Override
    public void add(int index, T item){
        validateInput(index);
        Node temp = getNode(index);

        if(_size==0) {
            _tail = _head = new Node(item);//list is currently empty
            _size++;
            return;
        }
        if(index == _size) {
            _tail = _tail.next = new Node(item, null, _tail);//addTail
            _size++;
            return;
        }
        if(index == 0){
            _head = _head.prev = new Node(item, _head, null);//addHead
            _size++;
            return;
        }
        temp.prev = new Node(item, temp, temp.prev);//make a new node, make it go in between temp and temp.prev
        temp.prev.prev.next = temp.prev;//confusing looking sorry but it works
        _size++;
    }

    @Override
    public T remove(int index){
        validateInput(index);
        if(index==_size){
            throw new IndexOutOfBoundsException("");
        }
        Node temp = getNode(index);
        T returnVal = temp.element;
        //1. We want to set temp's previous' next to temp's next (if they should exist)
        //2. We want to set temp's next's previous to temp's previous (if they should exist)

        //1. -temp.prev may not exist (temp is head, size is 0)
        //   -temp.prev may become the new tail (temp is tail)

        //2. -temp.next may not exist (temp is tail, size is 0)
        //   -temp.next may become the new head (temp is head, size is 0)
        //!!!!make an exception for size==0!!!!//later
        _size--;//one item will be removed (below) so the size is decremented in advance of this
        if(temp.prev != null){
            if(temp == _tail){
                //temp is the tail and there is at least one element preceding it
                //ie temp.prev exists but temp.next does not
                _tail = temp.prev;
                temp.prev.next = null;
                return returnVal;
            }
            else{
                //there are elements preceding temp but it is not the tail
                //ie temp.prev and temp.next exist
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                temp = null;
                return returnVal;
            }
        }
        else if(temp.next != null){
            if(temp == _head){
                //temp is the head and there is at least one element proceeding it
                //ie temp.next exists but temp.prev does not
                _head = temp.next;
                temp.next.prev = null;
                return returnVal;
            }
            else{
                //there are elements proceeding temp but it is not the head
                //ie temp.next and temp.prev exist
                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;
                temp = null;
                return returnVal;
            }
        }
        else{
            //temp is the only element in the list
            //ie both temp.next and temp.prev do not exist
            temp = null;
            return returnVal;
        }
    }

    @Override
    public void clear(){
        _head = null;
        _tail = null;
        _size = 0;
    }

    @Override
    public T set(int index, T item){
        validateInput(index);
        if(index==_size){
            throw new IndexOutOfBoundsException("");
        }
        T returnVal = getNode(index).element;
        getNode(index).element = item;
        return returnVal;
    }

    @Override
    public T get(int index){
        validateInput(index);
        if(index==_size){
            throw new IndexOutOfBoundsException("");
        }
        return getNode(index).element;
    }

    private void validateInput(int index){
        /**
         * Throws exception if invalid input is passed (gatekeeper)
         */
        if(index<0||index>_size) {
            throw new IndexOutOfBoundsException("");
        }
    }

    private Node getNode(int index){
        /**
         * common functionality for many functions. Returns node at index
         */
        validateInput(index);

        Node temp = _head;
        for(int i=0;i<index;i++){
            temp = temp.next;
        }
        return temp;
    }

    public void print(){
        //used for testing purposes
        Node temp = _head;
        for(int i = 0; i<_size;i++){
            System.out.print(temp.element);
            temp = temp.next;
        }
    }

    public boolean equals(MyLinkedList<? extends T> a){
        if(_size != a.size()){
            return false;
        }
        for(int i=0;i<_size;i++){
            if(get(i) != a.get(i)){
                return false;
            }
        }
        return true;
    }

}



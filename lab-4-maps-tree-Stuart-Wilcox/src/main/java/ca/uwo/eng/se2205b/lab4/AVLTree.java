package ca.uwo.eng.se2205b.lab4;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/**
 * Created by Stu on 2017-03-06.
 */
public class AVLTree<E> implements Tree<E, AVLTree.AVLBinaryNode<E>>  {
    private AVLBinaryNode<E> root;
    private int size;
    private Comparator<E> comparator;

    AVLTree(){
        size = 0;
        this.comparator = comparator();
    }

    AVLTree(Comparator<E> c){
        this.comparator = comparator();
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
    public int height() {
        if(root == null){
            return 0;
        }
        return root.height();
    }

    @Override
    public boolean isBalanced() {
        if(root == null){
            return true;
        }
        return root.isBalanced();
    }

    @Override
    public boolean isProper() {
        if(root == null){
            return true;
        }
        return root.isProper();
    }

    @Override
    public Iterator<E> iterator(Traversal how) {
        switch(how){
            case InOrder:{
                return new inOrderIterator();
            }
            case PreOrder:{
                return new preOrderIterator();
            }
            case PostOrder:{
                return new postOrderIterator();
            }
        }
        return null;
    }
    private class inOrderIterator implements Iterator<E>{
        int curr = 0;
        ArrayList<E> list = new ArrayList<>();

        inOrderIterator(){
            list = root.inOrder(list);
        }

        @Override
        public E next(){
            return list.get(curr++);
        }
        @Override
        public boolean hasNext(){
            return curr<size;
        }
    }

    private class postOrderIterator implements Iterator<E>{
        int curr = 0;
        ArrayList<E> list = new ArrayList<E>();
        postOrderIterator(){
            list = root.postOrder(list);
        }
        public E next(){
            return list.get(curr++);
        }
        public boolean hasNext(){
            return curr<size;
        }
    }

    private class preOrderIterator implements Iterator<E>{
        int curr = 0;
        ArrayList<E> list = new ArrayList<E>();
        preOrderIterator(){
            list = root.preOrder(list);
        }
        public E next(){
            return list.get(curr++);
        }
        public boolean hasNext(){
            return curr<size;
        }
    }

    @Override
    public boolean contains(E element) {
        if(root == null){
            return false;//empty tree
        }
        AVLBinaryNode<E> here = root;
        while(here != null){//traverse until you get to a leaf
            if(comparator.compare(here.getElement(), element) == 0){
                return true;//found a match before getting to a leaf
            }
            if(comparator.compare(here.getElement(), element)==1){
                here = here.getLeft();//element is less than current value so move left
                continue;
            }
            if(comparator.compare(here.getElement(), element)==-1){
                here = here.getRight();//element is greater than current value so move right
            }
        }
        //return here.getElement().compareTo(element)==0;//see if the leaf is the right value
        return false;
    }

    private Comparator<E> comparator(){
        return new Comparator<E>() {
            @Override
            public int compare(E e, E t1) {
                return ((Comparable<E>)(e)).compareTo(t1);
            }
        };
    }

    @Override
    public boolean put(E element) {
        //https://www.bing.com/images/search?view=detailV2&ccid=bf5sQ7b2&id=7A6938B1E7A3B1219F187C8ED6EE4645C8A76996&q=avl+tree+rotations&simid=608051238868553286&selectedIndex=11&ajaxhist=0
        AVLBinaryNode<E> here = root;
        if(root == null){
            //tree is currently empty
            root = new AVLBinaryNode<E>(null, element);
            size++;
            return true;
        }
        while(true){
            //keep looping until you get to the right spot or find a duplicate
            if(comparator.compare(here.getElement(), element)==-1){
                //element is less than here's
                if(here.getLeft()==null){//here has no left child
                    here.left = new AVLBinaryNode<E>(here, element);//insert in the left child spot
                    here = here.left;
                    size++;
                    break;
                }
                else{
                    here = here.getLeft();//here has a left chile so we continue down the tree
                }
            }
            else if(comparator.compare(here.getElement(), element)==1){//element is greater than here's
                if(here.getRight() == null){//here has no right node
                    here.right = new AVLBinaryNode<E>(here, element);//insert in the right child spot
                    here = here.right;
                    size++;
                    break;
                }
                else{
                    here = here.getRight();//continue down the tree
                }
            }
            else{
                return false;//element is equal to here's so we cannot insert (duplicate)
            }
        }
        //for this part I consulted wikipedia https://en.wikipedia.org/wiki/AVL_tree#Insertion
        //climb back up the tree and see if we need a rotation (retrace)
        AVLBinaryNode<E> Z = here;
        AVLBinaryNode<E> X = Z.getParent();

        balanceTree(X, Z);

        return true;
    }

    private void balanceTree(AVLBinaryNode<E> X, AVLBinaryNode<E> Z){
        while(X != null){
            AVLBinaryNode<E> A = X.parent;
            if(A == null){
                break;
            }

            if (balanceFactor(A) == -2) {
                //left-heavy
                if (balanceFactor(X) == -1) {
                    //left-left

                    if(A==this.root){
                        root = X;
                    }
                    //      A
                    //    X   t1
                    //  Z   t2
                    //t3 t4

                    //      X
                    //   Z     A
                    // t3 t4  t2 t1

                    AVLBinaryNode<E> temp = X.right;

                    X.parent = A.parent;
                    X.right = A;
                    A.left = temp;
                    if(A.parent != null){
                        if(A == A.parent.left) {
                            A.parent.left = X;
                        }
                        else{
                            A.parent.right = X;
                        }
                    }
                    if(temp != null){
                        temp.parent = A;
                    }
                    A.parent = X;
                    if(X==root){
                        return;
                    }

                } else if(balanceFactor(X) == 1){
                    //left right


                    AVLBinaryNode<E> temp = Z.left;
                    Z.left = X;
                    X.right = temp;
                    X.parent = Z;
                    Z.parent = A;
                    A.left = Z;
                    if(temp != null){
                        temp.parent = X;
                    }

                    if(A==this.root){
                        root = Z;
                    }

                    temp = Z.right;

                    Z.parent = A.parent;
                    Z.right = A;
                    A.left = temp;
                    if(A.parent != null){if(A == A.parent.left) {
                        A.parent.left = Z;
                    }
                    else{
                        A.parent.right = Z;
                    }
                    }
                    if(temp != null){
                        temp.parent = A;
                    }
                    A.parent = Z;

                    temp = X;
                    X = Z;
                    Z = temp;

                    if(Z==root){
                        return;
                    }
                }
            }
            if(balanceFactor(A) == 2){
                //leaf-heavy
                if(balanceFactor(X) == 1){
                    //right-right

                    if(A==this.root){
                        root = X;
                    }

                    AVLBinaryNode<E> temp = X.left;

                    X.parent = A.parent;
                    X.left = A;
                    A.right = temp;
                    if(A.parent != null){
                        if(A == A.parent.left) {
                            A.parent.left = X;
                        }
                        else{
                            A.parent.right = X;
                        }
                    }
                    if(temp != null){
                        temp.parent = A;
                    }
                    A.parent = X;

                    if(X == root){
                        return;
                    }
                }
                else if(balanceFactor(X) == -1){
                    AVLBinaryNode<E> temp = Z.right;
                    Z.right = X;
                    X.left = temp;
                    X.parent = Z;
                    Z.parent = A;
                    A.right = Z;
                    if(temp != null){
                        temp.parent = X;
                    }

                    if(A==this.root){
                        root = Z;
                    }

                    temp = Z.left;

                    Z.parent = A.parent;
                    Z.left = A;
                    A.right = temp;
                    if(A.parent != null){
                        if(A == A.parent.left) {
                            A.parent.left = Z;
                        }
                        else{
                            A.parent.right = Z;
                        }
                    }
                    if(temp != null){
                        temp.parent = A;
                    }
                    A.parent = Z;

                    if(Z == root){
                        return;
                    }

                    temp = X;
                    X = Z;
                    Z = temp;
                }
            }
            X = X.parent;
            Z = Z.parent;
        }
    }


    private int balanceFactor(AVLBinaryNode<E> n){
        int hL = 0, hR = 0;
        if(n.left != null){
            hL = n.left.height();
        }
        if(n.right != null){
            hR = n.right.height();
        }
        return hR - hL;
    }

    @Override
    public int putAll(SortedSet<? extends E> elements) {
        int num = 0;
        for (E el: elements) {
            if(put(el)){
                num++;
            }
        }
        return num;
    }

    @Override
    public void setRoot(@Nullable AVLBinaryNode<E> root) {
        this.root = root;
    }

    @Nullable
    @Override
    public AVLBinaryNode<E> getRoot() {
        return root;
    }

    @Override
    public boolean remove(E element) {
        if (size == 0 || root == null) {
            return false;
        }
        if (size == 1) {
            size--;
            root = null;
            return true;
        }
        AVLBinaryNode<E> here = root;
        while (!here.isLeaf()) {
            if (comparator.compare(here.getElement(), element) == 0) {
                break;
            }
            if (comparator.compare(here.getElement(), element) == 1) {
                if (here.getLeft() == null) {
                    return false;
                }
                here = here.getLeft();
            }
            if (comparator.compare(here.getElement(), element) == -1) {
                if (here.getRight() == null) {
                    return false;
                }
                here = here.getRight();
            }
        }
        if (comparator.compare(here.getElement(), element) != 0) {
            return false;
        }
        size--;
        if (here == root) {
            AVLBinaryNode<E> temp = here.getRight();
            if (temp == null) {
                root = root.left;
                root.parent = null;
                return true;
            }
            AVLBinaryNode<E> A;
            while (temp.getLeft() != null) {
                temp = temp.getLeft();
            }
            root.element = temp.element;
            temp.element = null;
            A = temp.parent;
            temp = A.parent;
            balanceTree(temp, A);
        }
        if (here.isLeaf()) {
            AVLBinaryNode<E> A = here.parent;
            AVLBinaryNode<E> B = A.parent;
            if (here == A.right) {
                A.right = null;
            } else {
                A.left = null;
            }
            here = null;

            balanceTree(B, A);

            return true;
        }

        if (here.isInternal()) {
            AVLBinaryNode<E> temp = here.getRight();
            if (temp == null) {
                temp = here.getLeft();
                temp.parent = here.parent;
                if (here.parent.left == here) {
                    here.parent.left = temp;
                } else {
                    here.parent.right = temp;
                }
                here = null;
                balanceTree(temp.parent, temp);
                return true;
            }
            while (temp.getLeft() != null) {
                temp = temp.getLeft();
            }
            here.element = temp.element;
            AVLBinaryNode<E> A = temp.parent;
            AVLBinaryNode<E> B = A.parent;
            if (temp == A.right) {
                A.right = null;
            } else {
                A.left = null;
            }
            temp = null;

            balanceTree(B, A);
        }

        return true;
    }

    static class AVLBinaryNode<E> extends BinaryNode<E, AVLBinaryNode<E>> {
        AVLBinaryNode<E> left;
        AVLBinaryNode<E> right;
        AVLBinaryNode<E> parent;
        int balanceFactor;
        E element;

        AVLBinaryNode(AVLBinaryNode par, E el){
            balanceFactor = 0;
            parent = par;
            element = el;
        }

        public AVLBinaryNode<E> setParent(AVLBinaryNode<E> parent){
            AVLBinaryNode<E> re = this.parent;
            this.parent = parent;
            return re;
        }

        @Override
        int getSize() {
            int sL = 0, sR = 0;
            if(left != null){
                sL = left.getSize();
            }
            if(right != null){
                sR = right.getSize();
            }
            return sL + sR;
        }

        public AVLBinaryNode<E> getLeft(){
            return this.left;
        }

        @Override
        AVLBinaryNode<E> setLeft(@Nullable AVLBinaryNode<E> left) {
            AVLBinaryNode<E> re = this.left;
            this.left = left;
            return re;
        }

        @Nullable
        @Override
        AVLBinaryNode<E> getRight() {
            return this.right;
        }

        @Override
        AVLBinaryNode<E> setRight(@Nullable AVLBinaryNode<E> right) {
            AVLBinaryNode<E> re = this.right;
            this.right = right;
            return re;
        }

        @Nullable
        @Override
        AVLBinaryNode<E> getParent() {
            return this.parent;
        }


        @Override
        public E getElement() {
            return this.element;
        }

        @Override
        public E setElement(E element) {
            E re = this.element;
            this.element = element;
            return re;
        }

        @Override
        public int height() {
            if(isLeaf()){
                return 1;
            }
            int hL = 0, hR = 0;
            if(this.left != null){
                hL = this.left.height();
            }
            if(this.right != null){
                hR = this.right.height();
            }
            return Integer.max(hL, hR) + 1;
        }

        private ArrayList<E> inOrder(ArrayList<E> list){
            if(this.left != null){
                this.left.inOrder(list);
            }
            list.add(this.getElement());
            if(this.right != null){
                this.right.inOrder(list);
            }
            return list;
        }
        private ArrayList<E> postOrder(ArrayList<E> list){
            if(this.left != null){
                this.left.postOrder(list);
            }
            if(this.right!=null){
                this.right.postOrder(list);
            }
            list.add(this.getElement());
            return list;
        }
        private ArrayList<E> preOrder(ArrayList<E> list){
            list.add(this.getElement());
            if(this.left != null){
                this.left.preOrder(list);
            }
            if(this.right != null){
                this.right.preOrder(list);
            }
            return list;
        }
    }

    @Override
    public int hashCode() {
        Iterator<E> a = this.iterator(Traversal.InOrder);
        int hashCode = 0;
        for(int i=0;a.hasNext();i++){
            hashCode += a.next().hashCode();
        }
        return 31*hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AVLTree<E> avlTree = (AVLTree<E>) o;

        if (size != avlTree.size) return false;
        Iterator<E> a = this.iterator(Traversal.InOrder);
        Iterator<E> b = avlTree.iterator(Traversal.InOrder);

        ArrayList<E> c = new ArrayList<E>();
        ArrayList<E> d = new ArrayList<E>();
        for(int i = 0; a.hasNext(); i++){
            c.add(a.next());
            d.add(b.next());
        }
        return c.equals(d);
    }

    public E get(Object o){
        E element = (E)o;
        if(root == null) {
            return null;//empty tree
        }
        AVLBinaryNode<E> here = root;
        while(here != null){//traverse until you get to a leaf
            if(comparator.compare(here.getElement(), element) == 0){
                return here.getElement();//found a match before getting to a leaf
            }
            if(comparator.compare(here.getElement(), element)==1){
                here = here.getLeft();//element is less than current value so move left
                continue;
            }
            if(comparator.compare(here.getElement(), element)==-1){
                here = here.getRight();//element is greater than current value so move right
            }
        }
        //return here.getElement().compareTo(element)==0;//see if the leaf is the right value
        return null;
    }
}

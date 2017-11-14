package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Binary Search Tree
 */
@ParametersAreNonnullByDefault
public class BinarySearchTree<E extends Comparable<E>> implements Tree<E> {

    /**
     * Internal Node structure used for the BinaryTree
     * @param <E>
     */
    private BinaryNode<E> root;
    private int size;

    static class BinaryNode<E extends Comparable<E>> implements Tree.Node<E> {
        private E element;
        private BinaryNode<E> parent;
        private BinaryNode<E> left;
        private BinaryNode<E> right;

        BinaryNode(E elem, @Nullable BinaryNode<E> parent) {
            element = elem;
            this.parent = parent;
        }

        @Nullable BinaryNode<E> getLeft() {
            return left;
        }

        @Nullable BinaryNode<E> getRight() {
            return right;
        }

        @Nullable BinaryNode<E> getParent() {
            return parent;
        }

        @Override
        public int size() {
            return this.children().size();
        }

        @Override
        public boolean isEmpty() {
            return size()==0;
        }

        @Override
        public int height() {
            if(isEmpty()){
                return 0;
            }
            else{
                return height(this);//go to recursion model
            }
        }
        private int height(BinaryNode<E> _node){
            int hL = 0, hR = 0;
            if(_node.left != null){
                hL = height(_node.left);//recursive step
            }
            if(_node.right != null){
                hR = height(_node.right);//recursive step
            }
            if(hL > hR){
                return hL + 1;
            }
            else{
                return hR + 1;
            }
        }

        @Override
        public boolean isProper() {
            //Returns true if every leaf node has a height difference of less than or equal to 1 between itself and every other leaf node.
            List<Integer> list = new ArrayList<>();
            //this.getLeavesHeight(list);
            if(list.isEmpty()){
                return true;
            }
            int h = list.get(0);
            for(int i = 0;i<list.size();i++){
                h = list.get(i);
                for(int j = 0;j < i;j++){
                    if(Math.abs(list.get(j)-h)>1){
                        return false;
                    }
                }
                for(int j = i;j < list.size();j++){
                    if(Math.abs(list.get(j)-h)>1){
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public boolean isBalanced() {
            //every node has either 0 or 2 children
            if(this.getLeft()!=null ^ this.getRight()!=null){
                return false;
            }
            if(this.getLeft() != null){
                if(!this.getLeft().isBalanced()){//recursive step
                    return false;
                }
            }
            if(this.getRight() != null){
                if(!this.getRight().isBalanced()){//recursive step
                    return false;
                }
            }
            return true;
        }

        @Override
        public E getElement() {
            return element;
        }

        @Nonnull
        @Override
        public Collection<? extends Node<E>> children() {
            List<BinaryNode<E>> childList = new ArrayList<>();

            addToList(this, childList);//go to recursive function
            return childList;
        }
        private void addToList(BinaryNode<E> n, List<BinaryNode<E>> l){
            //add this node as well as any children nodes to the list then returns it
            l.add(n);
            if(n.getLeft() != null){
                addToList(n.getLeft(),l);
            }
            if(n.getRight() != null){
                addToList(n.getRight(), l);
            }
        }

        @Override
        public boolean isInternal() {
            return parent != null && (left != null || right != null);
        }


        @Override
        public boolean isLeaf() {
            return this.left == null && this.right == null;//has no children then it is a leaf
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

    public BinarySearchTree() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    @Override
        public int height() {
        if(root == null){
            return 0;
        }
        return root.height();
    }

    @Override
    public boolean isProper() {
        if(size==0){
            return true;
        }
        return root.isProper();
    }

    @Override
    public boolean isBalanced() {
        if(size==0){
            return true;
        }
        return root.isBalanced();
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

    @Override
    public boolean contains(E element) {
        if(root == null){
            return false;//empty tree
        }
        BinaryNode<E> here = root;
        while(here != null){//traverse until you get to a leaf
            if(here.getElement().compareTo(element)==0){
                return true;//found a match before getting to a leaf
            }
            if(here.getElement().compareTo(element)==1){
                here = here.getLeft();//element is less than current value so move left
                continue;
            }
            if(here.getElement().compareTo(element)==-1){
                here = here.getRight();//element is greater than current value so move right
            }
        }
        //return here.getElement().compareTo(element)==0;//see if the leaf is the right value
        return false;
    }

    @Override
    public boolean put(E element) {
        BinaryNode<E> here = root;
        if(root == null){
            //tree is currently empty
            root = new BinaryNode<E>(element, null);
            size++;
            return true;
        }
        while(true){
            //keep looping until you get to the right spot or find a duplicate
            if(element.compareTo(here.getElement())==-1){
                //element is less than here's
                if(here.getLeft()==null){//here has no left child
                    here.left = new BinaryNode<E>(element, here);//insert in the left child spot
                    size++;
                    return true;
                }
                else{
                    here = here.getLeft();//here has a left chile so we continue down the tree
                }
            }
            else if(element.compareTo(here.getElement())==1){//element is greater than here's
                if(here.getRight() == null){//here has no right node
                    here.right = new BinaryNode<E>(element, here);//insert in the right child spot
                    size++;
                    return true;
                }
                else{
                    here = here.getRight();//continue down the tree
                }
            }
            else{
                return false;//element is equal to here's so we cannot insert (duplicate)
            }
        }
    }

    @Override
    public boolean remove(E element) {

        if(!this.contains(element)){
            throw new RuntimeException("Element does not exist");
        }
        if(size==0 || root ==null){
            return false;
        }
        BinaryNode<E> here = root;
        while(!here.isLeaf()){
            if(here.getElement().compareTo(element)==0){
                break;
            }
            if(here.getElement().compareTo(element)==1) {
                here = here.getLeft();
            }
            if(here.getElement().compareTo(element)==-1){
                here = here.getRight();
            }
        }
        if(here == root){
            if(size==1) {
                root = here = null;//the root is the only thing in the tree
                size = 0;
            }
            else{
                BinaryNode<E> temp = here.getRight();//go right once then go left as much as possible
                if(here.getRight() == null){
                    //root has no right children, so we just move one down the tree and call that the root
                    root = here.getLeft();
                    here.getLeft().parent = null;
                    return true;
                }
                while(temp.getLeft() != null){
                    //go as left as possible
                    temp = temp.getLeft();
                }
                here.element = temp.getElement();//set the root's element to this one's
                if(temp.parent.getLeft() == temp){
                    //we actually went left at least once
                    temp.parent.left = null;//break the parent's link
                    temp.parent = null;//break this node's link
                }
                else if(temp.parent.getRight() == temp){
                    //we didn't actually go left at all
                    temp.parent.right = null;//break parent's link
                    temp.parent = null;//break this node's link
                }
                size--;
                temp.element = null;
            }
            return true;
        }

        if(here.getElement().compareTo(element)==0 && here.isLeaf()){
            //this node is a leaf, so removal is easy
            here.element = null;
            if(here.parent.left == here){
                here.parent.left = null;
                here.parent = null;
            }
            else if(here.parent.right == here){
                here.parent.right = null;
                here.parent = null;
            }
            size--;
            return true;
        }
        if(here.getElement().compareTo(element)==0 && here.isInternal()){
            BinaryNode<E> temp = here.getRight();
            if(here.getRight()==null){
                //only has one child (left)
                here.getLeft().parent = here.getParent();//move this node out and connect its parent to its only child (left)
                here.getParent().left = here.getLeft();
                here= null;
                return true;
            }
            while(temp.getLeft() != null){
                temp = temp.getLeft();
            }
            here.element = temp.getElement();
            temp.element = null;
            if(temp.parent.getRight() == temp){
                //we didn't move left
                temp.parent.right = null;
                temp.parent = null;
            }
            else if(temp.parent.getLeft() == temp){
                //we did move left
                temp.parent.left = null;
                temp.parent = null;
            }
            size--;
            return true;
        }
        return false;//something went wrong
    }

    @Nullable
    @Override
    public BinaryNode<E> getRoot() {
        return root;
    }

    private class inOrderIterator implements Iterator<E>{
        int curr = 0;
        ArrayList<E> list = new ArrayList<>();

        inOrderIterator(){
            list = root.inOrder(list);
        }

        @Override
        public E next(){
            BinaryNode<E> here = root;
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
}

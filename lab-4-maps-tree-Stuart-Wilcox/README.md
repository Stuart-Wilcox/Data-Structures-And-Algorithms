# Lab 4 - Maps and Trees


See lab document: <https://uwoece-se2205b-2017.github.io/labs/4-maps-tree>

## Questions

### Question 1
```
Q: What is the benefit of an AVL tree over a traditional binary search tree?
A: Binary search trees are on average O(logn) for insert(), delete() and contains(), but on worst case the devolve into O(n). 
AVL trees are guaranteed to be proper, which also guarantees that they are O(logn) in every case.

```

### Question 2
```
Q: Red-black trees were covered in class, explain what the benefits of the Red-black tree over the AVL tree.
A: At most, the path from the root to the deepest leaf in an AVL tree is ~1.44log(n+2) and in red-black trees its ~2log(n+1). Because of 
this, AVL lookup is usually faster, but insertion and deletion are much slower because of the high cost of rotations in AVL.
```
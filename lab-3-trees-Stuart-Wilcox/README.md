# Lab 03 - Trees

See lab document: <https://uwoece-se2205b-2017.github.io/labs/03-trees>

## Questions

### Question 1
```
Q: How would you use a BST to implement a Map?
A: Have each node in the tree contain the key=>value pair and then build the tree by comparing values of keys. This will produce a map with O(log(n)) average case performance.

Q: What is the complexity of the operations, put(), remove() and height()? Answer the previous for a guaranteed balanced and unbalanced tree.
A: 
|Function|Best Case Complexity|Worst Case Complexity|
|--------|----------|-------------------------------|
|put()   |O(log(n)) |O(n)                           |
|--------|----------|-------------------------------|
|remove()|O(log(n)) |O(n)                           |
|--------|----------|-------------------------------|
|height()|O(log(n)) |O(n)                           |
|--------|----------|-------------------------------|

```
### Question 2
```
Q: What is the complexity of verifying a word of length k is in the Trie? Is there a structure we've covered in class that can beat this? Justify your answer. Be sure to consider k in your answer.
A: Best Case: O(1)
   Worst Case O(k)

Q: What structure is a Trie a "specialization" of?
A: A tree.
```
### Question 3
![Auto-Complete Implementation](Auto-Complete.PNG)

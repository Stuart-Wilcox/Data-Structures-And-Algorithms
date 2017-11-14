# Lab 6 - Sorting



See lab document: <https://uwoece-se2205b-2017.github.io/labs/06-sorting>



## Questions


### Question 1
```
Q: Using 'n' data, use a spreadsheet program to use regression to figure out the complexity. When you use a regression line, save the chart with the R^2 values included (do not show the equations). Explain what the R^2 values imply.
A: The R^2 values are an indication of how closely the data follows the trendline, and in this case how accurately the collected data follows the time theoretical time complexity.

```
![/results/results.PNG](/results/results.PNG)

<br />

### Question 2
```
Q: Given the plots from (1), what can you say about the algorithms worst-case vs. average-case? Are they equivalent? Use qualitative anlysis, quantitative is not necessary.
A: With low numbers of elements, Merge Sort's performance is not very good, but as n grows it becomes relatively better. If the chart extended, it looks like it ould eventually beat Quick Sort. Similarly, Insertion Sort starts off worse than Selection Sort for low n-values, but as n increases Insertion Sort out performs Selection Sort. 


```
### Question 3. 
```
Q: For the two major trade-offs between operation time and comparison time, which algorithm should be used in the case of large latency in each? Explain why.
A: Given large latency of operations and comparisons, the best choice would be Merge Sort. To justify why, look at the table below. The List Ops/Sort and Comparison Ops/Sort are a total of n=(5, 10, 20, 40, 100) over 3 runs. It is obvious Merge Sort is vastly lighter on operations. 
```


|Algorithm  |List Ops/Sort  |Comparison Ops/Sort  |Total Ops/Sort |

|-----------|:-------------:|:-------------------:|:-------------:|

|Selection  |29348          |23900                |53248          |

|Insertion  |70616          |12188                |82804          |

|Merge      |1400           |3216                 |4616           |

|Quick      |12652          |3628                 |16280          |


<br/>

###Question 

4. 
```
Q: Overall, what is the worst sorting algorithm we have?
A: The worst sorting algorithm we have is porbably Selection Sort. It is average case O(n^2) and had the worst performance on lists of size 40 or larger.

```

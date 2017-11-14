# Lab 7 - Graphs


See lab document: <https://uwoece-se2205b-2017.github.io/labs/07-graphs>


##Questions



### Question 1
```
Q: Implementing a Directed Graph

A: 
For this question I chose a hybrid of edge list and adjacancy list. The graph uses both lists in unison for a simple solution to adhere to the graph interface.

```



### Question 2
```
Q: Implementing an Undirected Graph

A: 
For this question I again chose the hybrid of edge list and adjacancy list. This made the implementation simple because it was very similar ot the previous question.


```

### Question 3 
```
Q: Pricing Flights, what type of graph did you use? What alorithm did you use to find the cheapest itinerary?
A: For this question I used a directed graph. This best models the directed nature of flights from airport to airport. For the alogrithm I used Dijkstra's algorithm. This was used on each node in the constructor to find all of the shortest paths from each node, and then the results were put into a list for fast lookup in FlightShopper#price(Airport to, Airport from).
```


### Question 4
```
Q: Landing a Rover on Mars, what type of graph did you use? What algorithm di you use ot find the drop spot?
A: For this question I used a directed graph. This was becuase my implementation of Dijkstra's algorithm from the previous question only used directed graphs. So every tile in the topography was connected in both directions (to and from).  
As previously stated, I used Dijkstra's algorithm. I already had an implementation available from the previous question. A better choice likely would have been the Floyd-Warshall algorithm. 


```
# Lab 7 - Graphs

https://uwoece-se2205b-2017.github.io/labs/07-graphs

## Q1 Implementing a Directed Graph

For this question I chose a hybrid of edge list and adjacancy list. The graph uses both lists in unison for a simple solution to adhere to the graph interface.

## Q2 Implementing an Undirected Graph

For this question I again chose the hybrid of edge list and adjacancy list. This made the implementation simple becuase it was very similar ot the previous question.

## Q3 Pricing Flights

*Graph type used*: Directed graph. This best models the directed nature of flights from airport to airport. </br>
*Algorithm decisions*: Dijkstra's algorithm. This was used on each node in the constructor to find all of the shortest paths from each node, and then the results were put into a list for fast lookup in FlightShopper#price(Airport to, Airport from).

## Q4 Landing a Rover on Mars

*Graph type used*: Directed graph. This was becuase my implementation of Dijkstra's algorithm only used directed graphs. So every tile in the topography was connected in both directions (to and from).  </br>
*Algorithm choices*: Dijkstra's algorithm. I already had an implementation available from the previous question. A better choice likely would have been the Floyd-Warshall algorithm. 


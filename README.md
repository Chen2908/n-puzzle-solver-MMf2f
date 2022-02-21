#  n-puzzle-solver-MMf2f :two_men_holding_hands:


## :bulb: Abstract
In this work we present an inprovement to the MM search algorithm presented in [[1]](#1).
MM is a bidirectional search algorithm which is guranteed to always "meet in the middle", which means the forward search and the backwards search will not expand nodes which are 
past the middle point of the optial solution. 

The problem we focus on is tile puzzle. We present how bidirectional search and MM search in particular, is applied to this problem.

The improvement we offer is changing the method for calculating the heuristic of the nodes in the graph.
The original MM search algorithm uses front-to-end (f2e) hueristic, which in this work we offer to perform the calculation by the front-to-front (f2f) heuristic. 
During our experiments we compared the performance of our method (MM algorithm with f2f heuristic) to the original MM algorithm, and to A* search and bidirectional A* search.
We included 35 inputs of 15-puzzle in our experiments. The inputs and results are attached. 


---


##  :mag_right: Research hypothesis
During this work we inspected the influence of changing the evaluation of distance between a certain state to the goal state in the MM algorithm.
The change was reflected in altering the nodes heuristic from front to end to front to front. The performance of the algorithm was measured by amount of generated nodes, 
path length and running time (in ms). 
Our hypothesis was that in our method the algorithm will find the optimal solution which expnading less nodes however its running time 
will be longer, due to additionl calculations. 


---


## :ledger: Running instructions
To run the code first clone this repository.
Mark the 'Puzzle' class as the main class and provide two arguments as the Program arguments: 
1. txt. input file path (our input file is attached)
2. puzzle size (3 for 8-puzzle, 4 for 15-puzzle)


---


### :ribbon: Credits:
Some algorithms were adapted from the source code of the following repository: https://github.com/yuvallb/15-puzzle-solver-MM-search


----

## :point_right: References
<a id="1">[1]</a>
R. C. Holte, A. Felner, G. Sharon and N. R. Sturtevant, "Bidirectional Search That Is Guaranteed to Meet in the Middle," 
in Proceedings of the AAAI Conference on Artificial Intelligence, 2016. 

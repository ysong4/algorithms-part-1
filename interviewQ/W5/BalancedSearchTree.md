# Balanced Search Tree

## 1. Red–black BST with no extra memory. 

Describe how to save the memory for storing the color information when implementing a red–black BST.

### My Ans

no idea

### Others solution

newR = P + P - realR

reference: https://stackoverflow.com/a/49493099/4972386

Others propose to use bit instead of boolean(byte).

reference: https://stackoverflow.com/questions/16088364/how-to-save-the-memory-when-storing-color-information-in-red-black-trees

## 2. Document search. 

Design an algorithm that takes a sequence of `n` document words and a sequence of `m` query words and find the shortest interval in which the `m` query words appear in the document in the order given. 

The length of an interval is the number of words in that interval.

### My Ans

Brutal force solution.

## 3. Generalized queue. 

Design a generalized queue data type that supports all of the following operations in logarithmic time (or better) in the worst case.

- Create an empty data structure.
- Append an item to the end of the queue.
- Remove an item from the front of the queue.
- Return the ith item in the queue.
- Remove the ith item from the queue.

### My Ans

Red black tree. With a start and an end int counters.

Delete: always find the target.right minimum node and exchange with target.
# Hash Table

## 1. 4-SUM.

Given an array a[] of n integers, the 4-SUM problem is to determine if there exist distinct indices i, j, k, and l such that `a[i] + a[j] = a[k] + a[l]`. Design an algorithm for the 4-SUM problem that takes time proportional to n^2 (under suitable technical assumptions).

### My Answer

Building a HashTable (a map in go) whose key is `a[i] + a[j]` and value is `[]Pair`.

Checking the distinct indices while building the hash table.

The time complexity of this step is n^2.

```Go
type Pair struct {
  X int
  Y int
}

func main() {
  table := make(map[int][]Pair)
  
  for (i := 0; i < n; i++) {
    for (j := i; j < n; j++) {
      value := a[i] + a[j]

      if val, ok := table[value]; ok {
        // Already has this value in the table

        // Check collision, see if i,j,k,l could be found.
        // If found, print to stdout.
        // TODO

        // Add the new value into the corresponding []Pair
        pair := Pair{
          X: i,
          Y: j
        }
        append(table[value], pair)

      } else {
        // First time set this value in the table
        pair := Pair{
          X: i,
          Y: j
        }

        pairs := []Pair{pair}
        table[value] = pairs
      }
    }
  }
}
```

## 2. Hashing with wrong hashCode() or equals()

Suppose that you implement a data type `OlympicAthlete` for use in a `java.util.HashMap`.

1. Describe what happens if you override `hashCode()` but not `equals()`.
2. Describe what happens if you override `equals()` but not `hashCode()`.
3. Describe what happens if you override `hashCode()` but implement `public boolean equals(OlympicAthlete that)` instead of `public boolean equals(Object that)`.

### My Answer

`hashCode` default using memory address.
`equals` default compare memory address.

1. Bad. `hash()` will be fine, but when you try `get()` and `put()` the behavior will be unexpected. 

2. Bad. `get()` and `put()` will be expected. But `hash()` will be unexpected. Even two Athelete objects referring to the same athelete, these two objects will have different `hashCode`. Therefore their `hash()` outputs will be different.

3. Same as the first one. Override `equals()` in this way is useless, as function signature is different.

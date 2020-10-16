# Hashtag-Counter
Building a Hashtag counter using Fibonacci Heap in JAVA
It was required to implement a system to find the n most popular hashtags that appear on social media
such as Facebook or Twitter. For the scope of this project hashtags will be given from an input file.
Basic idea for the implementation was to use a max priority structure to find out the most popular hashtags.
I have used the following structures for the implementation.
1. Max Fibonacci heap: use to keep track of the frequencies of hashtags.
2. Hash table: The key for the hash table is the hashtag, and the value is the pointer to the corresponding
node in the Fibonacci heap.
I have assumed there will be a large number of hashtags appearing in the stream and I need to perform
increase key operation many times. Max Fibonacci heap is recommended because it has an amortized
complexity of O(1) for the increase key operation. 

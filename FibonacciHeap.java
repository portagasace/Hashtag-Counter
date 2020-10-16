package com.ads.project;

import java.util.*;

public class FibonacciHeap {

	Node headPtr;
	public Node maxPtr;

	public List<String> extractMax(int num) {
		// do deleteMax num times and then reinsert it back into
		// the fibHeap
		
		HashMap<String,Integer> map = new HashMap<>();
		
		List<Node> maxNodes = new ArrayList<Node>();

		for (int i = 0; i <= num+1; i++) {
			maxNodes.add(maxPtr);
			map.put(maxPtr.Key,maxPtr.Data);
			removeMaxNode();

		}

		// insert it back into the fibHeap
		for (Node max : maxNodes) {
			max.Parent = null;
			max.Child = null;
			max.Degree = 0;
			addtoDoublyLinkedList(max);
		}

		List<String> result = new ArrayList<String>();
		map.entrySet().stream().sorted((e1,e2)->((e1.getValue()==e2.getValue())? e2.getKey().compareTo(e1.getKey()): e2.getValue()-e1.getValue())).forEach(e->result.add(e.getKey()));
		
		return result.subList(0, result.size()-2);
	}



	Node insertKey(int data, String key) {
		if (headPtr == null) {
			headPtr = new Node(key, data);
			headPtr.Right = headPtr;
			headPtr.Left = headPtr;
			maxPtr = headPtr;
			return headPtr;
		} else {
			Node curr = new Node(key, data);

			addtoDoublyLinkedList(curr);
			return curr;
		}
	}

	Node pairWiseMeld(Node node1, Node node2) {
		removeFromDoublyLinkedList(node1);
		removeFromDoublyLinkedList(node2);
		if (node1.Data > node2.Data) {

			node1.Degree = node1.Degree + 1;
			if (node1.Child == null) {
				node1.Child = node2;
				node2.Parent = node1;

				node2.Right = node2;
				node2.Left = node2;

			} else {
				Node FinalNode = node1.Child.Left;
				node2.Left = FinalNode;
				node2.Right = node1.Child;
				FinalNode.Right = node2;
				node1.Child.Left = node2;
				node2.Parent = node1;
			}

			addtoDoublyLinkedList(node1);
			return node1;
		} else {

			node2.Degree = node2.Degree + 1;
			if (node2.Child == null) {
				node2.Child = node1;
				node1.Parent = node2;

				node1.Right = node1;
				node1.Left = node1;

			} else {
				Node FinalNode = node2.Child.Left;
				node1.Left = FinalNode;
				node1.Right = node2.Child;
				FinalNode.Right = node1;
				node2.Child.Left = node1;
				node1.Parent = node2;
			}

			addtoDoublyLinkedList(node2);
			return node2;
		}
	}

	Node addtoDoublyLinkedList(Node curr) {
		if (curr == null)
			return null;

		if (headPtr != null) {

			curr.Left = headPtr.Left;
			curr.Right = headPtr;
			curr.ChildCut = false;
			curr.Parent = null;
			Node finalNode = headPtr.Left;
			headPtr.Left = curr;
			finalNode.Right = curr;

		} else {

			headPtr = curr;
			headPtr.Key = curr.Key;
			headPtr.Data = curr.Data;
			headPtr.Right = headPtr;
			headPtr.Left = headPtr;
			headPtr.Parent = null;
			headPtr.ChildCut = false;
			maxPtr = headPtr;
			return headPtr;

		}
		
		
		maxPtr = curr.Data > maxPtr.Data ? curr: maxPtr;
		
		return curr;
	}

	void removeFromDoublyLinkedList(Node curr) {
		if (curr.Right == curr || curr.Left == curr) {
			headPtr = null;
			return;
		} else {
			curr.Left.Right = curr.Right;
			curr.Right.Left = curr.Left;
			if (headPtr == curr) {
				headPtr = curr.Right;
			}
		}
	}

	void increaseKey(int Data, Node curr) {
		curr.Data = curr.Data + Data;
		if (curr.Parent != null && curr.Data > curr.Parent.Data) {

			Node Parent = curr.Parent;

			if (Parent.Child == curr) {
				Parent.Child = curr.Right;
			}

			curr.Parent = null;
			Parent.Degree--;
			if (Parent.Degree == 0) {
				Parent.Child = null;
			}

			// removing node from child level doubly linked circular list
			Node prevNode = curr.Left;
			Node finalNode = curr.Right;
			curr.Left.Right = finalNode;
			curr.Right.Left = prevNode;

			// node is inserted into top level doubly linked circular list
			addtoDoublyLinkedList(curr);
			curr.ChildCut = false;

			cascadeCut(Parent);
		} else {
			
			maxPtr = curr.Data > maxPtr.Data ? curr: maxPtr;
			
		}
	}

	void cascadeCut(Node curr) {
		Node Parent = curr.Parent;
		if (Parent != null) {

			if (curr.ChildCut == true) {

				if (Parent.Child == curr) {
					Parent.Child = curr.Right;
				}

				curr.Parent = null;
				Parent.Degree--;
				if (Parent.Degree == 0) {
					Parent.Child = null;
				}

				// removing node from child level doubly linked circular list
				Node prevNode = curr.Left;
				Node finalNode = curr.Right;
				curr.Left.Right = finalNode;
				curr.Right.Left = prevNode;

				// node is inserted into top level doubly linked circular list
				addtoDoublyLinkedList(curr);
				curr.ChildCut = false;

				cascadeCut(Parent);

			}

			else

			{

				curr.ChildCut = true;

			}
		}
	}
	
	void removeMaxNode() {
		// take the Children of maxPtr and insert it to the top level circular list
		
		HashMap<Integer, Node> nodeDegree = new HashMap<Integer, Node>();
		Node Child = maxPtr.Child;
		int maxDegree = maxPtr.Degree; 
		int nodeCount = 0;
		
		if(Child != null)
		{
		for (int i = 0; i < maxDegree; i++) {
		
			
			Node rightNode = Child.Right;
			Node leftNode = Child.Left;

			Child.Left.Right = rightNode;
			Child.Right.Left = leftNode;

			addtoDoublyLinkedList(Child);

			Child = rightNode;
		}
		}
		// detach maxPtr from the toplevel circular list
		removeFromDoublyLinkedList(maxPtr);
		if (headPtr == null)
			return;
		
			maxPtr = headPtr;
		
		// count the num of nodes in top circular list
		Node node = this.headPtr;
		
		
		
		do {
			node = node.Right;
			maxPtr = node.Data > maxPtr.Data ? node: maxPtr;
			nodeCount++;
		} while (this.headPtr != node);
		
		
		
		// start pairwise merging
		
		nodeDegree.put(headPtr.Degree, headPtr);
		
		node = headPtr.Right;
		
		for(int counter = 1 ; counter < nodeCount; counter++ )
		{
			int Degree = node.Degree;
			Node sibling = node.Right;
			
			while (nodeDegree.containsKey(Degree)) {
				
				Node currNode = nodeDegree.get(Degree);
				Node mergedNode = pairWiseMeld(currNode, node);
				nodeDegree.remove(Degree);
				Degree = mergedNode.Degree;
				node = mergedNode;
			}
			
			nodeDegree.put(Degree, node);
			
			maxPtr = node.Data > maxPtr.Data ? node: maxPtr;
			
			node = sibling;
		}
		
	}

	

}

package com.ads.project;

//Node Structure of a Max fibbonacci Heap
public class Node {
	public
	Node Right; // Used for circular doubly linked list of siblings
	Node Left; // Used for circular doubly linked list of siblings
	Node Parent; // Pointer to Parent node
	Node Child;
	String Key="";
	int Data=0;
	int Degree=0;
	boolean ChildCut=false;

	
	
	//Max Fibbonacci Heap Constructor for initialization 
	public Node(String Key, int Data)
	{
		this.Key = Key;
		this.Data = Data;		
	}
	
	
}
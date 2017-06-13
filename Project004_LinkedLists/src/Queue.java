
public interface Queue <T>{

	public Node<T> enQueue(T item, int id);	//add an item to the tail
	public void deQueue(); //remove the item at the head
	public boolean isEmpty();//check if queue is empty
	public int size();//return the num of items in the queue
	public Node getHead();//return head
	
}


/*
 * a list or colection with restriction that i
 * nsertion can be performed at one end 
 * (rear) and deletion can be performed at the other end (front)
 * 
 * <----dequeue[head][][][][][][][][tail]<---enqueue
 * 
 * cost of insertion/removal
 * 1. at head-O(1)
 * 2. at tail-O(n)
 * */



public class priorityNode {
    
	private Object data;
    private int priority; //an int 1 (highest priority) through 10 (lowest priority).
    private priorityNode prev;
    private priorityNode next;

    public priorityNode(Object data, int priority, priorityNode prev, priorityNode next) {
        this.data = data;
        this.priority = priority;
        this.prev = prev;
        this.next = next;
	}

    public Object getData() {
        return this.data;
	}

    public int getPriority() {
        return this.priority;
	}
		
    public priorityNode getPrev() {
        return this.prev;
	}
    
    public priorityNode getNext() {
        return this.next;
	}

    public void setNext(priorityNode newNext) {
        this.next = newNext;
    }

    @Override
    public String toString() {
        return (this.getData() + ", priority: " + this.getPriority());
	}
}
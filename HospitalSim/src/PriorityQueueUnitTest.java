	import static org.junit.Assert.*;
	import java.awt.Color;
	import java.awt.Point;
	import java.util.LinkedList;

import org.junit.Test;


public class PriorityQueueUnitTest {

		@Test
		public void testForwardTraversal1() { //create a new PriorityQueue
			PriorityQueue<Integer> testQueue = new PriorityQueue<Integer>();
			testQueue.insert(7); //add some values of varying sizes in no particular order
			testQueue.insert(4);
			testQueue.insert(3);
			testQueue.insert(6);
			testQueue.insert(5);
			testQueue.insert(1);
			testQueue.insert(2);
			testQueue.insert(10);
			testQueue.insert(9);
			testQueue.insert(8);
			LinkedList<Integer> testList = (LinkedList<Integer>) testQueue.testForwardTraversal();
			assertTrue(testList.getFirst().equals(10)); //the largest value should be first
		}
		
		@Test
		public void testForwardTraversal2() {
			PriorityQueue<Integer> testQueue = new PriorityQueue<Integer>(); //create a new PriorityQueue
			testQueue.insert(7); //add some values of varying sizes in no particular order
			testQueue.insert(4);
			testQueue.insert(3);
			testQueue.insert(6);
			testQueue.insert(5);
			testQueue.insert(1);
			testQueue.insert(2);
			testQueue.insert(10);
			testQueue.insert(9);
			testQueue.insert(8);
			LinkedList<Integer> testList = (LinkedList<Integer>) testQueue.testForwardTraversal();
			assertTrue(testList.getLast().equals(1)); //the lowest value should be last	
		}
		
		@Test
		public void testReverseTraversal1() {
			PriorityQueue<Integer> testQueue = new PriorityQueue<Integer>(); //create a new PriorityQueue
			testQueue.insert(7); //add some values of varying sizes in no particular order
			testQueue.insert(4);
			testQueue.insert(3);
			testQueue.insert(6);
			testQueue.insert(5);
			testQueue.insert(1);
			testQueue.insert(2);
			testQueue.insert(10);
			testQueue.insert(9);
			testQueue.insert(8);
			LinkedList<Integer> testList = (LinkedList<Integer>) testQueue.testReverseTraversal(); //fetch a reversed list
			assertTrue(testList.getFirst().equals(1)); //the lowest value should be first			
		}
		
		@Test
		public void testReverseTraversal2() {
			PriorityQueue<Integer> testQueue = new PriorityQueue<Integer>(); //create a new PriorityQueue
			testQueue.insert(7); //add some values of varying sizes in no particular order
			testQueue.insert(4);
			testQueue.insert(3);
			testQueue.insert(6);
			testQueue.insert(5);
			testQueue.insert(1);
			testQueue.insert(2);
			testQueue.insert(10);
			testQueue.insert(9);
			testQueue.insert(8);
			LinkedList<Integer> testList = (LinkedList<Integer>) testQueue.testReverseTraversal(); //fetch a reversed list
			assertTrue(testList.getLast().equals(10)); //the highest value should be last		
		}
		
		@Test
		public void testForwardTraversal3() { //create a new PriorityQueue
			PriorityQueue testQueue = new PriorityQueue<>();
			testQueue.insert(new PatientVisit(1, 7, 1)); //add some PatientVisit objects of varying urgencies
			testQueue.insert(new PatientVisit(1, 4, 1));
			testQueue.insert(new PatientVisit(1, 3, 1));
			testQueue.insert(new PatientVisit(1, 6, 1));
			testQueue.insert(new PatientVisit(1, 5, 1));
			testQueue.insert(new PatientVisit(1, 1, 1)); // 1 is highest priority
			testQueue.insert(new PatientVisit(1, 2, 1));
			testQueue.insert(new PatientVisit(1, 10, 1)); // lowest priority
			testQueue.insert(new PatientVisit(1, 9, 1));
			testQueue.insert(new PatientVisit(1, 8, 1));
			LinkedList testList = (LinkedList) testQueue.testForwardTraversal();
			System.out.println(testList);
			assertTrue(((PatientVisit) testList.getFirst()).getUrgency() == 1); //the largest value should be the highest priority in this case
		}
		
		@Test
		public void testForwardTraversal4() { //create a new PriorityQueue
			PriorityQueue testQueue = new PriorityQueue<>();
			testQueue.insert(new PatientVisit(1, 7, 1)); //add some PatientVisit objects of varying urgencies
			testQueue.insert(new PatientVisit(1, 4, 1));
			testQueue.insert(new PatientVisit(1, 3, 1));
			testQueue.insert(new PatientVisit(1, 6, 1));
			testQueue.insert(new PatientVisit(1, 5, 1));
			testQueue.insert(new PatientVisit(1, 1, 1)); // 1 is highest priority
			testQueue.insert(new PatientVisit(1, 2, 1));
			testQueue.insert(new PatientVisit(1, 10, 1)); // lowest priority
			testQueue.insert(new PatientVisit(1, 9, 1));
			testQueue.insert(new PatientVisit(1, 8, 1));
			LinkedList testList = (LinkedList) testQueue.testForwardTraversal();
			System.out.println(testQueue);
			assertTrue(((PatientVisit) testList.getLast()).getUrgency() == 10); //the smallest value should be the lowest priority in this case
		}
		
		@Test
		public void testForwardTraversal5() { //create a new PriorityQueue
			PriorityQueue testQueue = new PriorityQueue<>();
			testQueue.insert(new PatientVisit(7, 1, 1)); //add some PatientVisit objects of varying urgencies and arrival times
			testQueue.insert(new PatientVisit(7, 2, 1));
			testQueue.insert(new PatientVisit(3, 5, 1));
			testQueue.insert(new PatientVisit(3, 4, 1));
			testQueue.insert(new PatientVisit(5, 1, 1)); // priority 1 arriving at 5 minutes is highest priority
			testQueue.insert(new PatientVisit(1, 2, 1)); 
			testQueue.insert(new PatientVisit(2, 3, 1));
			testQueue.insert(new PatientVisit(10, 5, 1)); // priority 5 arriving at 10 minutes is lowest priority
			testQueue.insert(new PatientVisit(9, 5, 1));
			testQueue.insert(new PatientVisit(8, 1, 1));
			LinkedList testList = (LinkedList) testQueue.testForwardTraversal();	
			System.out.println(testQueue);
			assertTrue(((PatientVisit) testList.getLast()).getUrgency() == 5); 
			assertTrue(((PatientVisit) testList.getFirst()).getArrivalTime() == 5);
		}
		
		@Test
		public void testForwardTraversal6() { //create a new PriorityQueue
			PriorityQueue testQueue = new PriorityQueue<>();
			ExamRoom room1 = new ExamRoom(); //create some ExamRoom objects and give them various times
			room1.setInUseTime(8);
			ExamRoom room2 = new ExamRoom();
			room2.setInUseTime(4);
			ExamRoom room3 = new ExamRoom(); //lowest priority room
			room3.setInUseTime(10);
			ExamRoom room4 = new ExamRoom(); // highest priority room
			room4.setInUseTime(2);
			testQueue.insert(room1); //add the rooms to a PriorityQueue
			testQueue.insert(room2);
			testQueue.insert(room3);
			testQueue.insert(room4);
			LinkedList testList = (LinkedList) testQueue.testForwardTraversal();	
			System.out.println(testQueue);
			assertTrue(((ExamRoom) testList.getLast()).getInUseTime() == 10); 
			assertTrue(((ExamRoom) testList.getFirst()).getInUseTime() == 2);
		}
	
}

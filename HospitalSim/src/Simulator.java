import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Simulator {
	
	/**
	 * Seeks input from the user and then runs a simulation based on that input by calling the appropriate method.
	 */
	public static void main(String[] args) {
		
		Scanner console = new Scanner(System.in); // create the console
		int roomNum = 0;
		while (roomNum <= 0) {
			System.out.print("How many exam rooms will there be in this simulation? "); // ask the user for the number of rooms
			roomNum = console.nextInt();
		}
		PriorityQueue roomQueue = new PriorityQueue<>(); // make a queue for the available exam rooms
		for (int i=0; i<roomNum; i+=1) {
			ExamRoom room = new ExamRoom(); // create a room
			room.setIdent(i+1); // assign the room an identified
			roomQueue.insert(room); // populate the queue with the rooms
		}
		int modeSelector = 3;
		while (modeSelector != 1 && modeSelector != 2) {
			System.out.print("What kind of simulation? 1 for random, 2 for preset: "); // ask the user what mode to run the sim in
			modeSelector = console.nextInt();
		}
		int totalTimeHours = 0;
		while (totalTimeHours <= 0) {
			System.out.print("For how many hours should the simulation run? "); // ask the user for a number of hours
			totalTimeHours = console.nextInt();
		}
		int totalTimeMinutes = totalTimeHours*60; // turn the hours into minutes
		if (modeSelector == 1) { // if uses selects random mode, call the appropriate method
			randomSim(roomQueue, totalTimeMinutes, roomNum);
		}
		else { // if uses selects test mode call the appropriate method
			testSim(roomQueue, totalTimeMinutes, roomNum);
		}
	}

	/**
	 * Runs a simulation with the specified number of rooms for the specified number of hours, using preset patient arrival events, 
	 * and prints data about the simulation for the user.
	 * @param roomQueue a PriorityQueue populated by the number of ExamRoom objects specified by the user
	 * @param totalTime an int representing the total time specified by the user in minutes
	 * @param roomNum an int specifying the number of rooms indicated by the user
	 */
	public static void testSim(PriorityQueue roomQueue, int totalTime, int roomNum) {
		//this will run a test with the user specified number of rooms
		//and preset patient visits
		
		int currentTime = 0; //the current time in minutes - the number of minutes that have passed
		PriorityQueue arrivalQueue = new PriorityQueue<>(); // a queue to hold arriving patients
		PriorityQueue departureQueue = new PriorityQueue<>(); // a queue to hold departing patients
		ArrayList<PatientVisit> treatedPatients = new ArrayList<PatientVisit>(); // a list to store the treated patients (will actually be deep copies of patients as their fields existed when they were moved to rooms)		
		ArrayList<ExamRoom> roomsInUse = new ArrayList<ExamRoom>(); // a list to hold rooms while they are in use
		
		//THE DATA WE WANT TO KEEP TRACK OF
		int totalPatients = 0;
		int highPriorityPatients = 0;
		int lowPriorityPatients = 0;
		double totalTreatmentTime = 0;
		double avgTreatmentTime;
		double highPriorityWaitTime = 0;
		double avgHighPriorityWaitTime;
		double lowPriorityWaitTime = 0;
		double avgLowPriorityWaitTime;
		double totalWaitTime = 0;
		double avgWaitTime;
		
		while (currentTime < totalTime || departureQueue.size() > 0) { //until the time has elapsed AND all patients have finished treatment
			
			if (currentTime < totalTime) { // if the time specified by the user has not yet elapsed
				PatientVisit newPatient = PatientVisitGenerator.getNextProgrammedArrival(currentTime, roomNum); //generate an arrival event
				arrivalQueue.insert(newPatient); //add that patient to the arrival queue		
			}
			
			else if (!arrivalQueue.isEmpty()) {
				arrivalQueue = new PriorityQueue<>(); // clear any future events when user specified time has elapsed
			}
			//PUT PATIENT IN ROOM IF POSSIBLE
			// if there is an available room AND there are waiting patients (who have already arrived)
			while (!roomQueue.isEmpty() && !arrivalQueue.isEmpty() && ((PatientVisit) arrivalQueue.front()).getArrivalTime() <= currentTime) { 
				
				//GRAB THE NEXT ROOM AND PATIENT IN THEIR RESPECTIVE QUEUES
				
				ExamRoom room = (ExamRoom) roomQueue.remove(); // take the first room from the room list
				PatientVisit patient = (PatientVisit) arrivalQueue.remove(); // take the first patient from the patient list
				
				//MODIFY THE ROOM AND PATIENT AND CAPTURE SOME DATA
				//PATIENTS
				patient.setWaitTime(currentTime - patient.getArrivalTime()); // set the time the patient waited
				patient.setDepartureTime(currentTime + patient.getDuration()); // set the departure time of the patient equal to arrival time + duration of appointment
				treatedPatients.add(patient.deepCopy()); // add a copy of this patient to a queue for treated patients to preserve its wait time and urgency (for data collection purposes)
				patient.setUrgency(10); // set each patient to lowest priority once in treatment room (allows them to be compared based on departure time)
				patient.setArrivalTime(0); // set each arrival time to 0 once in treatment room (allows them to be compared based on departure time)
				departureQueue.insert(patient); // add patient to departure queue to be sorted by departure time
				
				//ROOMS
				room.setInUseTime(room.getInUseTime() + patient.getDuration()); // add the duration of the patient's treatment to the in-use time of the room
				room.setPatientNum(room.getPatientNum() + 1); // increase the number of patients treated in the room by 1
				room.setDepartureTime(patient.getDepartureTime()); // set departure time of room to that of patient
				roomsInUse.add(room); // put the rooms in a list for rooms in use
				
				//GENERAL DATA
				totalPatients ++; //increment number of patients treated				
				totalTreatmentTime += patient.getDuration(); // add the patient's treatment time to the total
			}
			
			//GO THROUGH ROOMS AND PATIENTS TO FIND THOSE WHICH ARE DONE
			//ROOMS
			for (int i=0; i<roomsInUse.size(); i++) { // look through rooms currently in use
				ExamRoom room = roomsInUse.get(i); // for each room
				if (room.getDepartureTime() <= currentTime) { // if the departure time is now or has passed
					roomsInUse.remove(i); //remove that room from the list of in-use rooms
					roomQueue.insert(room); // add it back to the room queue where it will be sorted according to its new in-use time
					i--; // go back one index as the list is now shorter
				}
			}
			
			//PATIENTS		
			while (!departureQueue.isEmpty() && ((PatientVisit) departureQueue.front()).getDepartureTime() <= currentTime) { // while the next patient to depart is ready to depart
				departureQueue.remove(); // remove that patient from the departure queue
			}
			currentTime += 1; // increment time 1 minute
		}
		
		//CALCULATE THE AVERAGE WAIT TIMES
		Iterator<PatientVisit> treatedPatientsIter = treatedPatients.iterator(); //attach an iterator to the list of treated patients
		while (treatedPatientsIter.hasNext()) {
			PatientVisit currentPatient = treatedPatientsIter.next(); // go through all treated patients
			if (currentPatient.getUrgency() >= 1 && currentPatient.getUrgency() <= 4) { // if the patient was high priority
				highPriorityPatients += 1; // add 1 to total of high priority patients
				highPriorityWaitTime += currentPatient.getWaitTime(); // add patient's wait time to high priority wait-time total				
			}
			else if (currentPatient.getUrgency() >= 9) { //if patient was low priority
				lowPriorityPatients += 1; //add 1 to total of low priority patients
				lowPriorityWaitTime += currentPatient.getWaitTime(); // add patient's wait time to low priority wait-time total	
			}
			totalWaitTime += currentPatient.getWaitTime();
		}
		avgHighPriorityWaitTime = highPriorityWaitTime/highPriorityPatients; // calculate the average for high priority patients
		avgLowPriorityWaitTime = lowPriorityWaitTime/lowPriorityPatients; // calculate the average for low priority patients
		avgWaitTime = totalWaitTime/totalPatients;
		avgTreatmentTime = totalTreatmentTime/totalPatients; // calculate overall average treatment time	
		
		// PRINT THE DATA FOR THE USER
		System.out.println();
		//System.out.println("total treatment time: " + totalTreatmentTime);
		System.out.println("There are " + roomNum + " rooms in the system.");
		System.out.println("The simulation ran for " + currentTime/60 + " hours and " + currentTime%60 + " minutes.");
		System.out.println(totalPatients + " patients were treated in total.");
		System.out.printf("The average wait time for treatment was: " + "%.2f" + " minutes.\n", avgWaitTime);
		if (highPriorityPatients > 0) {
			System.out.printf("There were " + highPriorityPatients + " patients with urgency 1 to 4 and their average wait time was: " + "%.2f" + " minutes.\n", avgHighPriorityWaitTime);
		}
		else {
			System.out.printf("There were " + highPriorityPatients + " patients with urgency 1 to 4 and their average wait time cannot be calculated.");
		}
		if (lowPriorityPatients > 0) {
			System.out.printf("There were " + lowPriorityPatients + " patients with urgency 9 to 10 and their average wait time was: " + "%.2f" + " minutes.\n", avgLowPriorityWaitTime);
		}
		else {
			System.out.printf("There were " + lowPriorityPatients + " patients with urgency 9 to 10 and their average wait cannot be calculated.\n");
		}
		System.out.printf("The average duration of treatment was: " + "%.2f" + " minutes.\n", avgTreatmentTime);
		for (int i=0; i<roomNum; i++) {
			ExamRoom room = (ExamRoom) roomQueue.remove();
			int patientNum = room.getPatientNum();
			double busyTime = room.getInUseTime();
			System.out.println(patientNum + " patients were treated in room " + room.getIdent() + ".");
			System.out.printf("Room " + room.getIdent() + " was busy " + "%.2f" + " percent of the time.\n", (busyTime/currentTime)*100);
		}
		System.out.println();		
	}

	/**
	 * Runs a simulation with the specified number of rooms for the specified number of hours, using random patient arrival events, 
	 * and prints data about the simulation for the user.
	 * @param roomQueue a PriorityQueue populated by the number of ExamRoom objects specified by the user
	 * @param totalTime an int representing the total time specified by the user in minutes
	 * @param roomNum an int specifying the number of rooms indicated by the user
	 */
	private static void randomSim(PriorityQueue roomQueue, int totalTime, int roomNum) {
		//this will run a test with the user specified number of rooms
		//and random patient visits
		
		int currentTime = 0; //the current time in minutes - the number of minutes that have passed
		PriorityQueue arrivalQueue = new PriorityQueue<>(); // a queue to hold arriving patients
		PriorityQueue departureQueue = new PriorityQueue<>(); // a queue to hold departing patients
		ArrayList<PatientVisit> treatedPatients = new ArrayList<PatientVisit>(); // a list to store the treated patients (will actually be deep copies of patients as their fields existed when they were moved to rooms)		
		ArrayList<ExamRoom> roomsInUse = new ArrayList<ExamRoom>(); // a list to hold rooms while they are in use
		
		//THE DATA WE WANT TO KEEP TRACK OF
		int totalPatients = 0;
		int highPriorityPatients = 0;
		int lowPriorityPatients = 0;
		double totalTreatmentTime = 0;
		double avgTreatmentTime;
		double highPriorityWaitTime = 0;
		double avgHighPriorityWaitTime;
		double lowPriorityWaitTime = 0;
		double avgLowPriorityWaitTime;
		double totalWaitTime = 0;
		double avgWaitTime;
		
		while (currentTime < totalTime || departureQueue.size() > 0) { //until the time has elapsed AND all patients have finished treatment
			
			if (currentTime < totalTime) { // if the time specified by the user has not yet elapsed
				PatientVisit newPatient = PatientVisitGenerator.getNextRandomArrival(currentTime); //generate an arrival event
				arrivalQueue.insert(newPatient); //add that patient to the arrival queue		
			}
			
			else if (!arrivalQueue.isEmpty()) {
				arrivalQueue = new PriorityQueue<>(); // clear any future events when user specified time has elapsed
			}
			//PUT PATIENT IN ROOM IF POSSIBLE
			// if there is an available room AND there are waiting patients (who have already arrived)
			while (!roomQueue.isEmpty() && !arrivalQueue.isEmpty() && ((PatientVisit) arrivalQueue.front()).getArrivalTime() <= currentTime) { 
				
				//GRAB THE NEXT ROOM AND PATIENT IN THEIR RESPECTIVE QUEUES
				
				ExamRoom room = (ExamRoom) roomQueue.remove(); // take the first room from the room list
				PatientVisit patient = (PatientVisit) arrivalQueue.remove(); // take the first patient from the patient list
				
				//MODIFY THE ROOM AND PATIENT AND CAPTURE SOME DATA
				//PATIENTS
				patient.setWaitTime(currentTime - patient.getArrivalTime()); // set the time the patient waited
				patient.setDepartureTime(currentTime + patient.getDuration()); // set the departure time of the patient equal to arrival time + duration of appointment
				treatedPatients.add(patient.deepCopy()); // add a copy of this patient to a queue for treated patients to preserve its wait time and urgency (for data collection purposes)
				patient.setUrgency(10); // set each patient to lowest priority once in treatment room (allows them to be compared based on departure time)
				patient.setArrivalTime(0); // set each arrival time to 0 once in treatment room (allows them to be compared based on departure time)
				departureQueue.insert(patient); // add patient to departure queue to be sorted by departure time
				
				//ROOMS
				room.setInUseTime(room.getInUseTime() + patient.getDuration()); // add the duration of the patient's treatment to the in-use time of the room
				room.setPatientNum(room.getPatientNum() + 1); // increase the number of patients treated in the room by 1
				room.setDepartureTime(patient.getDepartureTime()); // set departure time of room to that of patient
				roomsInUse.add(room); // put the rooms in a list for rooms in use
				
				//GENERAL DATA
				totalPatients ++; //increment number of patients treated				
				totalTreatmentTime += patient.getDuration(); // add the patient's treatment time to the total
			}
			
			//GO THROUGH ROOMS AND PATIENTS TO FIND THOSE WHICH ARE DONE
			//ROOMS
			for (int i=0; i<roomsInUse.size(); i++) { // look through rooms currently in use
				ExamRoom room = roomsInUse.get(i); // for each room
				if (room.getDepartureTime() <= currentTime) { // if the departure time is now or has passed
					roomsInUse.remove(i); //remove that room from the list of in-use rooms
					roomQueue.insert(room); // add it back to the room queue where it will be sorted according to its new in-use time
					i--; // go back one index as the list is now shorter
				}
			}
			
			//PATIENTS		
			while (!departureQueue.isEmpty() && ((PatientVisit) departureQueue.front()).getDepartureTime() <= currentTime) { // while the next patient to depart is ready to depart
				departureQueue.remove(); // remove that patient from the departure queue
			}
			currentTime += 1; // increment time 1 minute
		}
		
		//CALCULATE THE AVERAGE WAIT TIMES
		Iterator<PatientVisit> treatedPatientsIter = treatedPatients.iterator(); //attach an iterator to the list of treated patients
		while (treatedPatientsIter.hasNext()) {
			PatientVisit currentPatient = treatedPatientsIter.next(); // go through all treated patients
			if (currentPatient.getUrgency() >= 1 && currentPatient.getUrgency() <= 4) { // if the patient was high priority
				highPriorityPatients += 1; // add 1 to total of high priority patients
				highPriorityWaitTime += currentPatient.getWaitTime(); // add patient's wait time to high priority wait-time total				
			}
			else if (currentPatient.getUrgency() >= 9) { //if patient was low priority
				lowPriorityPatients += 1; //add 1 to total of low priority patients
				lowPriorityWaitTime += currentPatient.getWaitTime(); // add patient's wait time to low priority wait-time total	
			}
			totalWaitTime += currentPatient.getWaitTime();
		}
		avgHighPriorityWaitTime = highPriorityWaitTime/highPriorityPatients; // calculate the average for high priority patients
		avgLowPriorityWaitTime = lowPriorityWaitTime/lowPriorityPatients; // calculate the average for low priority patients
		avgWaitTime = totalWaitTime/totalPatients;
		avgTreatmentTime = totalTreatmentTime/totalPatients; // calculate overall average treatment time	
		
		// PRINT THE DATA FOR THE USER
		System.out.println();
		//System.out.println("total treatment time: " + totalTreatmentTime);
		System.out.println("There are " + roomNum + " rooms in the system.");
		System.out.println("The simulation ran for " + currentTime/60 + " hours and " + currentTime%60 + " minutes.");
		System.out.println(totalPatients + " patients were treated in total.");
		System.out.printf("The average wait time for treatment was: " + "%.2f" + " minutes.\n", avgWaitTime);
		if (highPriorityPatients > 0) {
			System.out.printf("There were " + highPriorityPatients + " patients with urgency 1 to 4 and their average wait time was: " + "%.2f" + " minutes.\n", avgHighPriorityWaitTime);
		}
		else {
			System.out.printf("There were " + highPriorityPatients + " patients with urgency 1 to 4 and their average wait time cannot be calculated.");
		}
		if (lowPriorityPatients > 0) {
			System.out.printf("There were " + lowPriorityPatients + " patients with urgency 9 to 10 and their average wait time was: " + "%.2f" + " minutes.\n", avgLowPriorityWaitTime);
		}
		else {
			System.out.printf("There were " + lowPriorityPatients + " patients with urgency 9 to 10 and their average wait cannot be calculated.\n");
		}
		System.out.printf("The average duration of treatment was: " + "%.2f" + " minutes.\n", avgTreatmentTime);
		for (int i=0; i<roomNum; i++) {
			ExamRoom room = (ExamRoom) roomQueue.remove();
			int patientNum = room.getPatientNum();
			double busyTime = room.getInUseTime();
			System.out.println(patientNum + " patients were treated in room " + room.getIdent() + ".");
			System.out.printf("Room " + room.getIdent() + " was busy " + "%.2f" + " percent of the time.\n", (busyTime/currentTime)*100);
		}
		System.out.println();	
		
	}
	
}

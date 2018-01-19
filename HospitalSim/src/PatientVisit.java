/**
 * A class to describe the visit of a patient, defined by the arrival time, the
 * urgency of the visit, and the length of the visit.
 * @param <E>
 */
public class PatientVisit<E extends Comparable<E>> implements Comparable<E> {

	private double arrivalTime; // in minutes
	private int urgency; // 1 to 10 (1 is the most urgent)
	private double duration; // duration of the visit in minutes
	private double waitTime; //the number of minutes the patient is in a queue before being moved to a room
	private double departureTime; //the time the patient will leave the treatment queue (time of start of examination + duration of examination)

	/**
	 * Creates a patient visit given its characteristics
	 * 
	 * @param theArrivalTime
	 *            the time of the arrival (in minutes)
	 * @param theUrgency
	 *            the urgency level (1 to 10, 1 being most urgent)
	 * @param theDuration
	 *            the length of the patient visit (in minutes)
	 */
	public PatientVisit(double theArrivalTime, int theUrgency, double theDuration) {
		this.arrivalTime = theArrivalTime;
		this.urgency = theUrgency;
		this.duration = theDuration;
		this.waitTime = 0;
		this.departureTime = 0;
	}
	
	/**
	 * Creates a patient visit given its characteristics
	 * 
	 * @param theArrivalTime
	 *            the time of the arrival (in minutes)
	 * @param theUrgency
	 *            the urgency level (1 to 10, 1 being most urgent)
	 * @param theDuration
	 *            the length of the patient visit (in minutes)
	 * @param waitTime
	 *            the time this patient had to wait before being seen
	 */
	public PatientVisit(double theArrivalTime, int theUrgency, double theDuration, double waitTime) {
		this.arrivalTime = theArrivalTime;
		this.urgency = theUrgency;
		this.duration = theDuration;
		this.waitTime = waitTime;
		this.departureTime = 0;
	}

	/**
	 * Returns the arrival time (in minutes)
	 * @return this.arrivalTime, the arrival time (in minutes)
	 */
	public double getArrivalTime() {
		return this.arrivalTime;
	}
	
	/**
	 * Sets the arrival time (in minutes)
	 * @param newArrivalTime the arrival time (in minutes)
	 */
	public void setArrivalTime(double newArrivalTime) {
		this.arrivalTime = newArrivalTime;
	}
	
	/**
	 * Returns the departure time (in minutes)
	 * @return this.departureTime, the departure time (in minutes)
	 */
	public double getDepartureTime() {
		return this.departureTime;
	}

	/**
	 * Returns the duration of the visit (in minutes)
	 * @return duration, the duration of the visit (in minutes)
	 */
	public double getDuration() {
		return duration;
	}
	
	/**
	 * Sets the duration of the visit (in minutes)
	 * @param the remaining duration of the visit (in minutes)
	 */
	public void setDuration(double newDuration) {
		this.duration = newDuration;
	}
	
	/**
	 * Returns the wait time of the patient (in minutes)
	 * @return this.waitTime, the wait time of the patient (in minutes)
	 */
	public double getWaitTime() {
		return this.waitTime;
	}
	
	/**
	 * Sets the wait time of the patient (in minutes) 
	 * @param newWaitTime, the wait time of the patient (in minutes)
	 */
	public void setWaitTime(double newWaitTime) {
		this.waitTime = newWaitTime;
	}

	/**
	 * Returns the urgency of the visit (1 to 10, 1 being most urgent)
	 * @return urgency, the urgency of the visit
	 */
	public int getUrgency() {
		return urgency;
	}
	
	/**
	 * Sets the urgency of the patient
	 * @param newUrgency, the urgency of the patient
	 */
	public void setUrgency(int newUrgency) {
		this.urgency = newUrgency;
	}
	
	/**
	 * Sets the departure time of the patient
	 * @param newDepartureTime, the departure time of the patient
	 */
	public void setDepartureTime(double newDepartureTime) {
		this.departureTime = newDepartureTime;
	}
	
	
	/**
	 * Compares this object to another PatientVisit object based on urgency, arrival time, and departure time
	 * @param o, an object
	 * @return 0, 1, or -1, an int specifying the comparison between this PatientVisit and the passed PatientVisit
	 */
	public int compareTo(E o) throws IllegalArgumentException{
		if (o != null && o.getClass() == this.getClass()) {
			PatientVisit<?> v = (PatientVisit<?>) o;
			if (this.urgency < v.getUrgency()) { //if this visit has higher urgency (a lower number), this visit is 'greater than'
				return 1;
			}
			else if (this.urgency > v.getUrgency()) { //if this visit has lower urgency (a higher number), this visit is 'less than'
				return -1;
			}
			else { //if the urgencies are equal, compare arrival times
				if (this.arrivalTime < v.arrivalTime) { //if this visit has a lower arrival time (arrived sooner) it is 'greater than'
					return 1;
				}
				else if (this.arrivalTime > v.arrivalTime) { // if this visit has a higher arrival time (arrived later) it is less than
					return -1; //otherwise it is 'less than'
				}
				else { // if both the arrival time and urgency are the same
					if (this.departureTime < v.departureTime) { //if this visit will end at a lower time (sooner) it is 'greater than'
						return 1;
					}
					else if (this.departureTime > v.departureTime) { //if this visit will end at a higher time (later) it is 'less than'
						return -1;
					}
					else { //if every field is the same
						return 0;
					}
				}
			}
		}
		else {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Prints the fields of this PatientVisit in a formatted way
	 * @return string a String of the important fields in this PatientVisit
	 */
	public String toString() {
		return "[at: " + this.getArrivalTime() + "dt: " + this.getDepartureTime() + ", u: " + this.getUrgency() + ", d: " + this.getDuration() + "]";
	}
	
	/**
	 * Provides a deep copy of this PatientVisit
	 * @return PatientVisit, a deep copy of this PatientVisit
	 */
	public PatientVisit deepCopy() {
		return new PatientVisit(this.arrivalTime, this.urgency, this.duration, this.waitTime);
	}
	
	





}
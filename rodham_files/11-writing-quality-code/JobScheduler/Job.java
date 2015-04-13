
import java.util.Calendar;


/**
 * Job stores information about a customer job
 */
public class Job {

	/**
	 * Human-readable description of the job
	 */
	private String description;
	
	/**
	 * Duration of the job, measured in hours [1-8]
	 */
	private int duration;
	
	/**
	 * Scheduled start date/time for the job
	 */
	private Calendar startTime;
	
	/**
	 * Scheduled finish date/time for the job
	 */
	private Calendar finishTime;
	
	/**
	 * Constructor
	 * 
	 * @param description Description string
	 * @param duration Job duration
	 * 
	 * @pre description != null && description.length() > 0
	 * @pre duration >= 1 && duration <= 8
	 * 
	 * @post description and duration have been set to the specified values,
	 *  and start and finish times are null
	 */
	public Job(String description, int duration) {
		setDescription(description);
		setDuration(duration);
	}
	
	/**
	 * Sets the job's description string
	 * 
	 * @param value New description string
	 * 
	 * @pre value != null && value.length() > 0
	 * 
	 * @post job's description string has been set to the new value
	 */
	public void setDescription(String value) {
		if (value == null || value.length() == 0) {
			throw new IllegalArgumentException();
		}
		description = value;
	}

	/**
	 * Returns the job's description string
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the job's duration
	 * 
	 * @param value New job duration
	 * 
	 * @pre value >= 0 && value <= 8
	 * 
	 * @post job's duration has been set to the new value
	 */
	public void setDuration(int value) {
		if (value < 1 || value > 8) {
			throw new IllegalArgumentException();
		}
		duration = value;
	}
	
	/**
	 * Returns the job's duration
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Has the job's start time been previously set?
	 */
	public boolean hasStartTime() {
		return (startTime != null);
	}
	
	/**
	 * Sets the job's start time
	 * 
	 * @param value New start time
	 * 
	 * @pre value != null
	 * 
	 * @post job's start time has been set to the new value
	 */
	public void setStartTime(Calendar value) {
		if (value == null) {
			throw new IllegalArgumentException();			
		}
		startTime = (Calendar)value.clone();
	}
	
	/**
	 * Returns the job's start time
	 */
	public Calendar getStartTime() {
		return startTime;
	}
	
	/**
	 * Has the job's finish time been previously set?
	 */
	public boolean hasFinishTime() {
		return (finishTime != null);
	}
	
	/**
	 * Sets the job's finish time
	 * 
	 * @param value New finish time
	 * 
	 * @pre value != null
	 * 
	 * @post job's finish time has been set to the new value
	 */
	public void setFinishTime(Calendar value) {
		if (value == null) {
			throw new IllegalArgumentException();			
		}
		finishTime = (Calendar)value.clone();
	}
	
	/**
	 * Returns the job's finish time
	 */
	public Calendar getFinishTime() {
		return finishTime;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Job: [");
		builder.append(description);
		builder.append(", ");
		builder.append(duration);
		builder.append(", ");
		
		if (startTime != null) {
			builder.append(DateFormatter.formatDateTime(startTime.getTime()));
		}
		else {
			builder.append("null");
		}
		
		builder.append(", ");
		
		if (finishTime != null) {
			builder.append(DateFormatter.formatDateTime(finishTime.getTime()));
		}
		else {
			builder.append("null");
		}
		
		builder.append("]");
		
		return builder.toString();
	}

}

import java.util.Calendar;
import java.util.ArrayList;

/**
 * DailySchedule is responsible for managing the job schedule for one particular
 * day. It stores a date and a list of jobs that are scheduled for that date. It
 * also provides operations for adding new jobs to the day's schedule.
 */
class DailySchedule {

	/**
	 * Each work day begins at 8am
	 */
	private static final int WORK_DAY_START_HOUR = 8;

	/**
	 * Each work day is 8 hours long, not including lunch
	 */
	private static final int WORK_DAY_DURATION = 8;

	/**
	 * Lunch break begins at noon
	 */
	private static final int LUNCH_TIME_HOUR = 12;

	/**
	 * Lunch break lasts 1 hour
	 */
	private static final int LUNCH_DURATION = 1;

	/**
	 * Date to which this schedule applies
	 */
	private Calendar date;

	/**
	 * Jobs scheduled for this date, sorted by increasing start time
	 */
	private ArrayList<Job> schedule;

	/**
	 * Constructor
	 * 
	 * @param date
	 *            The date to which this schedule applies
	 * 
	 * @pre date != null
	 * 
	 * @post the date has been set to the specified value, and the schedule's
	 *       list of jobs is empty
	 */
	public DailySchedule(Calendar date) {
		setDate(date);
		schedule = new ArrayList<Job>();
	}

	/**
	 * Sets the date to which this schedule applies
	 * 
	 * @param value
	 *            New date
	 * 
	 * @pre date != null
	 * 
	 * @post the schedule's date has been set to the new value
	 */
	private void setDate(Calendar value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		date = (Calendar) value.clone();
	}

	/**
	 * Returns the schedule's date
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * Returns the number of free (i.e., unscheduled) hours on this day
	 * 
	 * @pre none
	 * 
	 * @post returns an integer in the range [0-8]
	 */
	public int getUnscheduledTime() {
		int result = WORK_DAY_DURATION;
		for (Job job : schedule) {
			result -= job.getDuration();
		}
		assert (result >= 0 && result <= WORK_DAY_DURATION);
		return result;
	}

	/**
	 * Adds a new job to this day's schedule. Jobs are scheduled back-to-back.
	 * The scheduling algorithm ensures that time is allocated for the lunch
	 * break.
	 * 
	 * @param job
	 *            Job being added to the schedule
	 * 
	 * @pre job != null
	 * @pre job.getDuration() <= this.getUnscheduledTime()
	 * 
	 * @post job has been added to this day's schedule at the earliest possible
	 *       time
	 * @post job's start time and finish time have been set
	 */
	public void add(Job job) {

		final Calendar lunchTime = getWorkDayLunchTime();

		// calculate the job's start time
		//
		Calendar jobStartTime = null;
		if (schedule.isEmpty()) {
			// this is the first job of the day
			jobStartTime = getWorkDayStartTime();
		} else {
			// start the new job when the previous job finishes
			jobStartTime = getLastJob().getFinishTime();
		}
		// account for lunch
		if (jobStartTime.equals(lunchTime)) {
			jobStartTime.add(Calendar.HOUR_OF_DAY, LUNCH_DURATION);
		}

		// calculate the job's finish time
		//
		Calendar jobFinishTime = (Calendar) jobStartTime.clone();
		jobFinishTime.add(Calendar.HOUR_OF_DAY, job.getDuration());
		// account for lunch
		if (jobStartTime.before(lunchTime) && jobFinishTime.after(lunchTime)) {
			jobFinishTime.add(Calendar.HOUR_OF_DAY, LUNCH_DURATION);
		}

		// add the job to the schedule
		//
		job.setStartTime(jobStartTime);
		job.setFinishTime(jobFinishTime);
		schedule.add(job);
	}

	private Calendar getWorkDayStartTime() {
		return getTime(WORK_DAY_START_HOUR);
	}

	private Calendar getWorkDayLunchTime() {
		return getTime(LUNCH_TIME_HOUR);
	}

	private Calendar getTime(int hour) {
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH);
		int dayOfMonth = date.get(Calendar.DAY_OF_MONTH);
		Calendar result = Calendar.getInstance();
		result.clear();
		result.set(year, month, dayOfMonth, hour, 0);
		return result;

	}

	private Job getLastJob() {
		return schedule.get(schedule.size() - 1);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Daily Schedule: ");
		builder.append(DateFormatter.formatDate(date.getTime()));
		builder.append("\n");

		for (Job job : schedule) {
			builder.append(job.toString());
			builder.append("\n");
		}

		return builder.toString();
	}

}

import java.util.Calendar;
import java.util.ArrayList;

/**
 * Schedule is responsible for scheduling new jobs, and storing jobs that have
 * been previously scheduled. It stores a list of DailySchedules, each of which
 * contains one or more scheduled jobs. It also provides operations for adding
 * new jobs to the schedule.
 */
public class Schedule {

	/**
	 * List of daily schedules (one for each date on which at least one job has
	 * been scheduled)
	 */
	private ArrayList<DailySchedule> schedule;

	/**
	 * Constructor
	 * 
	 * @pre none
	 * 
	 * @post schedule is empty (i.e., contains no jobs)
	 * 
	 */
	public Schedule() {
		schedule = new ArrayList<DailySchedule>();
	}

	/**
	 * Adds a job to the schedule. The job will be scheduled at the earliest
	 * available time, but not earlier than the following day. The job will be
	 * scheduled so that the entire job can be started and finished on the same
	 * day.
	 * 
	 * @param job
	 *            The job to be scheduled
	 * 
	 * @pre job != null
	 * 
	 * @post job has been added to the schedule at the earliest possible time
	 * @post job's start time and finish time have been set
	 */
	public void add(Job job) {

		final Calendar today = getToday();

		// see if the new job will fit in any of the partially-scheduled days
		for (DailySchedule dailySchedule : schedule) {
			if (dailySchedule.getDate().after(today)
					&& dailySchedule.getUnscheduledTime() >= job.getDuration()) {
				// we found a spot on a partially-scheduled day
				dailySchedule.add(job);
				return;
			}
		}

		// start a new daily schedule
		DailySchedule newDailySchedule = new DailySchedule(
				getNextSchedulableDay(today));
		schedule.add(newDailySchedule);
		newDailySchedule.add(job);
	}

	private Calendar getNextSchedulableDay(Calendar today) {
		Calendar baseDate = today;
		if (!schedule.isEmpty()) {
			if (getLastDailySchedule().getDate().after(today)) {
				baseDate = getLastDailySchedule().getDate();
			}
		}
		return getNextWorkDay(baseDate);
	}

	private DailySchedule getLastDailySchedule() {
		return schedule.get(schedule.size() - 1);
	}

	private Calendar getToday() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);

		Calendar today = Calendar.getInstance();
		today.clear();
		today.set(year, month, dayOfMonth);

		return today;
	}

	private Calendar getNextWorkDay(Calendar after) {
		Calendar result = (Calendar) after.clone();
		while (true) {
			// advance one day
			result.add(Calendar.DAY_OF_MONTH, 1);

			// if it's not a weekend or holiday, let's work!
			if (!isWeekendDay(result) && !isHoliday(result)) {
				return result;
			}
		}
	}

	private boolean isWeekendDay(Calendar date) {
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
	}

	private boolean isHoliday(Calendar date) {
		return (isNewYearsDay(date) || isMemorialDay(date)
				|| isIndependenceDay(date) || isPioneerDay(date)
				|| isLaborDay(date) || isThanksgiving(date) || isChristmas(date));
	}

	private boolean isNewYearsDay(Calendar date) {
		return (date.get(Calendar.MONTH) == Calendar.JANUARY && date
				.get(Calendar.DAY_OF_MONTH) == 1);
	}

	private boolean isChristmas(Calendar date) {
		return (date.get(Calendar.MONTH) == Calendar.DECEMBER && date
				.get(Calendar.DAY_OF_MONTH) == 25);
	}

	private boolean isIndependenceDay(Calendar date) {
		return (date.get(Calendar.MONTH) == Calendar.JULY && date
				.get(Calendar.DAY_OF_MONTH) == 4);
	}

	private boolean isPioneerDay(Calendar date) {
		return (date.get(Calendar.MONTH) == Calendar.JULY && date
				.get(Calendar.DAY_OF_MONTH) == 24);
	}

	private boolean isMemorialDay(Calendar date) {
		// Memorial Day is on the last Monday of May
		return isLastDayOfWeekInMonth(date, Calendar.MONDAY, Calendar.MAY);
	}

	private boolean isLaborDay(Calendar date) {
		// Labor Day is on the 1st Monday in September
		return isNthDayOfWeekInMonth(date, 1, Calendar.MONDAY,
				Calendar.SEPTEMBER);
	}

	private boolean isThanksgiving(Calendar date) {
		// Thanksgiving is on the 4th Thursday in November
		return isNthDayOfWeekInMonth(date, 4, Calendar.THURSDAY,
				Calendar.NOVEMBER);
	}

	private boolean isNthDayOfWeekInMonth(Calendar date, int n, int dayOfWeek,
			int month) {
		return (date.get(Calendar.DAY_OF_WEEK) == dayOfWeek
				&& date.get(Calendar.MONTH) == month && date
					.get(Calendar.DAY_OF_WEEK_IN_MONTH) == n);
	}

	private boolean isLastDayOfWeekInMonth(Calendar date, int dayOfWeek,
			int month) {
		if (date.get(Calendar.DAY_OF_WEEK) == dayOfWeek
				&& date.get(Calendar.MONTH) == month) {

			// date has the correct month and day of week;
			// now we'll determine if it's the last.

			// move ahead one week and see if the month changes
			Calendar oneWeekLater = (Calendar) date.clone();
			oneWeekLater.add(Calendar.DAY_OF_MONTH, 7);
			return (oneWeekLater.get(Calendar.MONTH) != month);
		} else {
			return false;
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Schedule:\n\n");

		for (DailySchedule dailySchedule : schedule) {
			builder.append(dailySchedule.toString());
			builder.append("\n");
		}

		return builder.toString();
	}

}

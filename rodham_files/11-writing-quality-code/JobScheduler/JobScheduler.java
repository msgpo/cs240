
/**
 * JobScheduler is the main driver class for the Job Scheduler application.
 */
public class JobScheduler {

	/**
	 * Entry point for Job Scheduler application.
	 * 
	 * @param args Command-line arguments specified by the user.
	 * The command-line arguments define the jobs to be scheduled,
	 * and should have the following form: 
	 * 
	 * java JobScheduler <job-name> <job-duration> <job-name> <job-duration> ...
	 * 
	 * <job-name> is the job's name, and can be any non-empty string
	 * <job-duration> is the length of the job in hours, and must be a whole number in the range 1-8
	 * 
	 * @pre args != null
	 * @pre args.length is even
	 * @pre for even i, args[i] is a non-empty, non-null string
	 * @pre for odd i, args[i] is the string representation of an integer in the range [1-8]
	 * 
	 * @post A schedule containing the specified jobs has been constructed and printed
	 * to standard output
	 * 
	 */
	public static void main(String[] args) {
		try {
			if (args.length % 2 != 0) {
				throw new IllegalArgumentException();
			}

			Schedule schedule = new Schedule();	

			for (int i=0; i < args.length; i += 2) {
				String jobName = args[i];
				int jobDuration = Integer.parseInt(args[i + 1]);
				schedule.add(new Job(jobName, jobDuration));
			}

			System.out.println(schedule);
		}
		catch (IllegalArgumentException e) {
			usage();
		}
	}
	
	private static void usage() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nUSAGE: java JobScheduler <job-name> <job-duration> <job-name> <job-duration> ...\n");
		builder.append("\n<job-name> is the job's name, and can be any non-empty string\n");
		builder.append("\n<job-duration> is the length of the job in hours, and must be a whole number in the range 1-8\n");
		System.out.println(builder.toString());
	}
	
}

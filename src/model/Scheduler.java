package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Scheduler {

	public static void main(String[] args) {
		TreeMap<LocalDate, List<Task>> tm = Scheduler.scheduleTasks("chancekrueger@arizona.edu");

		for (Entry<LocalDate, List<Task>> entry : tm.entrySet()) {
			LocalDate date = entry.getKey();
			List<Task> tasks = entry.getValue();

			System.out.println("Date: " + date);
			for (Task task : tasks) {
				ProjAssn proj = task.getProjAssn();
				System.out.printf("   Task: %s | Score: %.2f | Duration: %d min | Priority: %d | Allocated: %d min%n",
						proj.getTitle(), task.getScore(), task.getEstimatedMinutes(), task.getPriorityLevel(),
						task.getAllocatedMinutes());
			}
		}
	}

	private TreeMap<LocalDate, List<Task>> scheduler;

	public Scheduler(String email) {
		this.scheduler = Scheduler.scheduleTasks(email);
	}

	private static TreeMap<LocalDate, List<Task>> scheduleTasks(String email) {
		TreeMap<LocalDate, List<Task>> tm = new TreeMap<>();

		List<ProjAssn> allProjAssn = DataBase.getAllProjAssn(email);

		for (ProjAssn proj : allProjAssn) {
			LocalDate today = LocalDate.now();
			LocalDate dueDate = proj.getDue().toLocalDate();
			long daysUntilDue = ChronoUnit.DAYS.between(today, dueDate);

			// total minutes required for this project
			long totalMinutes = proj.getTime().toMinutes();

			// number of days to allocate (include today and due date)
			int daysToAllocate = (int) Math.max(1, daysUntilDue + 1);

			// evenly split minutes across days
			int perDay = (int) (totalMinutes / daysToAllocate);
			int remainder = (int) (totalMinutes % daysToAllocate);

			for (int i = 0; i < daysToAllocate; i++) {
				LocalDate day = today.plusDays(i);
				Task partialTask = new Task(proj);

				// distribute remainder fairly
				int alloc = perDay + (i < remainder ? 1 : 0);

				partialTask.setAllocatedMinutes(alloc);
				tm.computeIfAbsent(day, d -> new ArrayList<>()).add(partialTask);
			}
		}

		// Sort tasks within each day by score (highest first)
		Comparator<Task> comparator = Comparator.comparingDouble(Task::getScore).reversed();
		tm.values().forEach(taskList -> taskList.sort(comparator));

		return tm;
	}

	public TreeMap<LocalDate, List<Task>> getSchedule() {
		return this.scheduler;
	}

	public static class Task {
		private static final int MAX_EXPECTED_DURATION = 2880; // 48 hours
		private static final int MAX_PRIORITY_LEVEL = 10;

		private double score;
		private ProjAssn proj;
		private int allocatedMinutes; // new field

		public Task(ProjAssn proj) {
			this.proj = proj;
			this.score = Task.calculateScore(proj);
		}

		public double getScore() {
			return this.score;
		}

		public ProjAssn getProjAssn() {
			return proj;
		}

		public int getEstimatedMinutes() {
			return (proj.getTime().toHoursPart() * 60) + proj.getTime().toMinutesPart();
		}

		public int getPriorityLevel() {
			return proj.getPriority().getValue();
		}

		public int getAllocatedMinutes() {
			return allocatedMinutes;
		}

		public void setAllocatedMinutes(int minutes) {
			this.allocatedMinutes = minutes;
		}

		private static double calculateScore(ProjAssn proj) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime due = proj.getDue();

			long daysUntilDue = ChronoUnit.DAYS.between(now.toLocalDate(), due.toLocalDate());

			double urgencyScore;
			if (daysUntilDue > 0) {
				urgencyScore = 1.0 / (daysUntilDue + 1);
			} else {
				long minutesUntilDue = Duration.between(now, due).toMinutes();
				urgencyScore = minutesUntilDue > 0 ? 1.0 / (minutesUntilDue + 1) : 2.0;
			}

			double durationScore = Math.min(1.0, (double) proj.getTime().toMinutes() / MAX_EXPECTED_DURATION);
			double priorityScore = (double) proj.getPriority().getValue() / MAX_PRIORITY_LEVEL;

			double urgencyWeight = 0.5;
			double durationWeight = 0.3;
			double priorityWeight = 0.2;

			return (urgencyWeight * urgencyScore) + (durationWeight * durationScore) + (priorityWeight * priorityScore);
		}
	}
}

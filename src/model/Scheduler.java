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
				System.out.printf("   Task: %s | Score: %.2f | Duration: %d min | Priority: %d%n", proj.getTitle(),
						task.getScore(), task.getEstimatedMinutes(), task.getPriorityLevel());
			}
		}

	}

	public static TreeMap<LocalDate, List<Task>> scheduleTasks(String email) {

		TreeMap<LocalDate, List<Task>> tm = new TreeMap<>();

		List<ProjAssn> allProjAssn = DataBase.getAllProjAssn(email);

		for (ProjAssn proj : allProjAssn) {
			LocalDate date = LocalDate.of(proj.getDue().getYear(), proj.getDue().getMonth(),
					proj.getDue().getDayOfMonth());
			tm.computeIfAbsent(date, d -> new ArrayList<>()).add(new Task(proj));
		}

		Comparator<Task> comparator = Comparator.comparingDouble(Task::getScore).reversed();

		tm.values().forEach(taskList -> taskList.sort(comparator));

		return tm;
	}

}

class Task {

	private static final int MAX_EXPECTED_DURATION = 2880; // 48 hours
	private static final int MAX_PRIORITY_LEVEL = 10;

	private double score;
	private ProjAssn proj;

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
		return proj.getTime().toMinutesPart();
	}

	public int getPriorityLevel() {
		return proj.getPriority().getValue();
	}

	private static double calculateScore(ProjAssn proj) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime due = proj.getDue();

		long daysUntilDue = ChronoUnit.DAYS.between(now.toLocalDate(), due.toLocalDate());

		double urgencyScore;
		if (daysUntilDue > 0) {
			urgencyScore = 1.0 / (daysUntilDue + 1);
		} else {
			// Due today: use hours/minutes for finer urgency
			long minutesUntilDue = Duration.between(now, due).toMinutes();
			urgencyScore = minutesUntilDue > 0 ? 1.0 / (minutesUntilDue + 1) : 2.0; // boost overdue tasks
		}

		double durationScore = Math.min(1.0, (double) proj.getTime().toMinutes() / MAX_EXPECTED_DURATION);
		double priorityScore = (double) proj.getPriority().getValue() / MAX_PRIORITY_LEVEL;

		double urgencyWeight = 0.5;
		double durationWeight = 0.3;
		double priorityWeight = 0.2;

		return (urgencyWeight * urgencyScore) + (durationWeight * durationScore) + (priorityWeight * priorityScore);
	}
}

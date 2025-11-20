package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import model.Scheduler;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.awt.Color;
import java.awt.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SchedulerView {

	private JFrame frame;
	private JLabel saturdayLabel;
	private JLabel fridayLabel;
	private JLabel sundayLabel;
	private JLabel thursdayLabel;
	private JLabel wednesdayLabel;
	private JLabel tuesdayLabel;
	private JLabel scheulerBackgroundImage;
	private JLabel mondayLabel;
	private JButton backMainMenuButton;
	private JButton fowardWeekButton;
	private JButton backWeekButton;
	private JLabel curDateLabel;
	private String[] acct;
	private TreeMap<LocalDate, List<model.Scheduler.Task>> scheduler;
	private LocalDate currentWeekStart;
	private List<String> completedTaskIds = new ArrayList<>();
	private static final String COMPLETED_FILE = "completed_tasks.dat";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						SchedulerView window = new SchedulerView(args[0]);
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						SchedulerView window = new SchedulerView();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * Create the application.
	 */
	public SchedulerView(String email) {
		this.acct = new String[1];
		this.acct[0] = email;
		this.scheduler = new Scheduler(email).getSchedule();
		this.currentWeekStart = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
		initialize();
	}

	// TESTING
	public SchedulerView() {
		this.acct = new String[1];
		this.acct[0] = "chancekrueger@arizona.edu";
		this.scheduler = new Scheduler("chancekrueger@arizona.edu").getSchedule();
		this.currentWeekStart = LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 982, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		curDateLabel = new JLabel("");
		curDateLabel.setFont(new Font("PT Sans Narrow", Font.PLAIN, 17));
		curDateLabel.setBackground(new Color(255, 254, 251));
		curDateLabel.setOpaque(true);
		curDateLabel.setBounds(410, 83, 127, 28);
		curDateLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		frame.getContentPane().add(curDateLabel);

		backWeekButton = new JButton("");
		backWeekButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backWeekButton.setBorder(null);
		backWeekButton.setBounds(383, 84, 15, 29);
		frame.getContentPane().add(backWeekButton);

		fowardWeekButton = new JButton("");
		fowardWeekButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fowardWeekButton.setBorder(null);
		fowardWeekButton.setBounds(549, 82, 15, 29);
		frame.getContentPane().add(fowardWeekButton);

		backMainMenuButton = new JButton("< Main Menu");
		backMainMenuButton.setFont(new Font("PT Sans Narrow", Font.PLAIN, 16));
		backMainMenuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backMainMenuButton.setBorder(null);
		backMainMenuButton.setBounds(160, 61, 97, 29);
		frame.getContentPane().add(backMainMenuButton);

		mondayLabel = new JLabel("");
		mondayLabel.setOpaque(true);
		mondayLabel.setBackground(new Color(255, 254, 251));
		mondayLabel.setBounds(267, 122, 21, 16);
		frame.getContentPane().add(mondayLabel);

		tuesdayLabel = new JLabel("");
		tuesdayLabel.setBackground(new Color(255, 254, 251));
		tuesdayLabel.setOpaque(true);
		tuesdayLabel.setBounds(329, 122, 21, 16);
		frame.getContentPane().add(tuesdayLabel);

		wednesdayLabel = new JLabel("");
		wednesdayLabel.setBackground(new Color(255, 254, 251));
		wednesdayLabel.setOpaque(true);
		wednesdayLabel.setBounds(399, 122, 21, 16);
		frame.getContentPane().add(wednesdayLabel);

		thursdayLabel = new JLabel("");
		thursdayLabel.setBackground(new Color(255, 254, 251));
		thursdayLabel.setOpaque(true);
		thursdayLabel.setBounds(471, 123, 21, 16);
		frame.getContentPane().add(thursdayLabel);

		fridayLabel = new JLabel("");
		fridayLabel.setBackground(new Color(255, 254, 251));
		fridayLabel.setOpaque(true);
		fridayLabel.setBounds(549, 123, 21, 16);
		frame.getContentPane().add(fridayLabel);

		saturdayLabel = new JLabel("");
		saturdayLabel.setBackground(new Color(255, 254, 251));
		saturdayLabel.setOpaque(true);
		saturdayLabel.setBounds(620, 122, 21, 16);
		frame.getContentPane().add(saturdayLabel);

		sundayLabel = new JLabel("");
		sundayLabel.setBackground(new Color(255, 254, 251));
		sundayLabel.setOpaque(true);
		sundayLabel.setBounds(690, 122, 21, 16);
		frame.getContentPane().add(sundayLabel);

		scheulerBackgroundImage = new JLabel("");
		scheulerBackgroundImage.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		scheulerBackgroundImage.setIcon(new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/weeklySchedulerBackground.png"));
		scheulerBackgroundImage.setBounds(160, 0, 640, 548);
		frame.getContentPane().add(scheulerBackgroundImage);

		this.backMainMenuButton.addActionListener(e -> backArrow());
		this.backWeekButton.addActionListener(e -> backWeek());
		this.fowardWeekButton.addActionListener(e -> fowardWeek());

		loadCompletedTasks();
		renderWeek(currentWeekStart);

	}

	@SuppressWarnings("unchecked")
	private void loadCompletedTasks() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COMPLETED_FILE))) {
			completedTaskIds = (List<String>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			completedTaskIds = new ArrayList<>(); // fallback if file doesn't exist
		}
	}

	private void saveCompletedTasks() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COMPLETED_FILE))) {
			oos.writeObject(completedTaskIds);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openTaskPopup(Scheduler.Task task) {
		JFrame popup = new JFrame("Task Details");
		popup.setSize(300, 250);
		popup.setLocationRelativeTo(frame);
		popup.setLayout(null);

		String taskId = task.getProjAssn().getTitle() + "|" + task.getProjAssn().getDue().toLocalDate();

		JLabel titleLabel = new JLabel("Title: " + task.getProjAssn().getTitle());
		titleLabel.setBounds(20, 20, 250, 20);
		popup.add(titleLabel);

		JLabel priorityLabel = new JLabel("Priority: " + task.getProjAssn().getPriority());
		priorityLabel.setBounds(20, 50, 250, 20);
		popup.add(priorityLabel);

		JLabel timeLabel = new JLabel("Estimated Time: " + task.getEstimatedMinutes() + " min");
		timeLabel.setBounds(20, 80, 250, 20);
		popup.add(timeLabel);

		JLabel allocLabel = new JLabel("Allocated Time: " + task.getAllocatedMinutes() + " min");
		allocLabel.setBounds(20, 110, 250, 20);
		popup.add(allocLabel);

		JLabel dueLabel = new JLabel("Due: " + task.getProjAssn().getDue().toString());
		dueLabel.setBounds(20, 140, 250, 20);
		popup.add(dueLabel);

		JCheckBox doneCheckBox = new JCheckBox("Mark as completed");
		doneCheckBox.setBounds(20, 170, 200, 20);
		doneCheckBox.setOpaque(false);
		doneCheckBox.addActionListener(e -> {
			completedTaskIds.add(taskId);
			saveCompletedTasks();
			popup.dispose();

			// Refresh the current frame
			renderWeek(currentWeekStart);
			frame.revalidate();
			frame.repaint();
		});

		popup.add(doneCheckBox);

		JButton closeButton = new JButton("Close");
		closeButton.setBounds(100, 200, 80, 30);
		closeButton.addActionListener(e -> popup.dispose());
		popup.add(closeButton);

		popup.setVisible(true);
	}

	private void backWeek() {
		currentWeekStart = currentWeekStart.minusWeeks(1);
		renderWeek(currentWeekStart);
	}

	private void fowardWeek() {
		currentWeekStart = currentWeekStart.plusWeeks(1);
		renderWeek(currentWeekStart);
	}

	private void backArrow() {
		this.frame.dispose();
		MainMenuView.main(this.acct);
	}

	private void renderWeek(LocalDate weekStart) {
		List<LocalDate> weekDates = getCurrentWeekDates(weekStart);
		curDateLabel.setText(weekDates.get(0).getMonth().toString() + "/" + weekDates.get(0).getDayOfMonth() + "/"
				+ weekDates.get(0).getYear());

		JLabel[] dayLabels = { mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel, saturdayLabel,
				sundayLabel };

		int[] dayX = { 210, 280, 350, 420, 490, 560, 630 }; // Adjust as needed

		// Remove old scroll panes and panels
		for (Component comp : frame.getContentPane().getComponents()) {
			if (comp instanceof JScrollPane || comp instanceof JPanel) {
				frame.getContentPane().remove(comp);
			}
		}

		for (int i = 0; i < 7; i++) {
			LocalDate date = weekDates.get(i);
			dayLabels[i].setText(date.getDayOfMonth() + "");

			List<Scheduler.Task> tasks = scheduler.getOrDefault(date, new ArrayList<>());

			// Create vertical task panel
			JPanel taskPanel = new JPanel();
			taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
			taskPanel.setOpaque(false);

			for (Scheduler.Task task : tasks) {

				String taskId = task.getProjAssn().getTitle() + "|" + task.getProjAssn().getDue().toLocalDate();
				if (completedTaskIds.contains(taskId))
					continue;

				JButton taskButton = new JButton(
						task.getProjAssn().getTitle() + " – " + task.getAllocatedMinutes() + " min");
				taskButton.setFont(new Font("PT Sans Narrow", Font.PLAIN, 12));
				taskButton.setMaximumSize(new Dimension(100, 20));
				taskButton.setFocusPainted(false);
				taskButton.setBorderPainted(false);
				taskButton.setContentAreaFilled(false);
				taskButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				taskButton.addActionListener(e -> openTaskPopup(task));
				taskPanel.add(taskButton);
			}

			// Wrap in scroll pane
			JScrollPane scrollPane = new JScrollPane(taskPanel);
			scrollPane.setBounds(dayX[i], 150, 100, 300); // Adjust height as needed
			scrollPane.setBorder(null);
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

			frame.getContentPane().add(scrollPane);
			frame.getContentPane().setComponentZOrder(scrollPane, 0); // bring to front
		}

		frame.repaint();
	}

	private List<LocalDate> getCurrentWeekDates(LocalDate referenceDate) {
		List<LocalDate> week = new ArrayList<LocalDate>();
		LocalDate monday = referenceDate.minusDays(referenceDate.getDayOfWeek().getValue() - 1);
		for (int i = 0; i < 7; i++) {
			week.add(monday.plusDays(i));
		}
		return week;
	}

}

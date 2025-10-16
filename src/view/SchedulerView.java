package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.Scheduler;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.awt.Color;
import java.awt.Component;

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

		// RENDER DATES
		List<LocalDate> weekDates = getCurrentWeekDates(LocalDate.now());
		curDateLabel.setText(weekDates.get(0).getMonth().toString() + "/" + weekDates.get(0).getDayOfMonth() + "/"
				+ weekDates.get(0).getYear());

		JLabel[] dayLabels = { mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel, saturdayLabel,
				sundayLabel };

		for (int i = 0; i < 7; i++) {
			LocalDate date = weekDates.get(i);
			dayLabels[i].setText(date.getDayOfMonth() + "");

			List<Scheduler.Task> tasks = scheduler.getOrDefault(date, new ArrayList<>());

			int yOffset = 150;
			for (Scheduler.Task task : tasks) {
				JLabel taskLabel = new JLabel(
						task.getProjAssn().getTitle() + " – " + task.getEstimatedMinutes() + " min");
				taskLabel.setFont(new Font("PT Sans Narrow", Font.PLAIN, 12));
				taskLabel.setBounds(267 + (i * 62), yOffset, 100, 20); // Adjust x/y for spacing
				frame.getContentPane().add(taskLabel);
				yOffset += 25; // Stack vertically
			}
		}

		scheulerBackgroundImage = new JLabel("");
		scheulerBackgroundImage.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		scheulerBackgroundImage.setIcon(new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/weeklySchedulerBackground.png"));
		scheulerBackgroundImage.setBounds(160, 0, 640, 548);
		frame.getContentPane().add(scheulerBackgroundImage);

		this.backMainMenuButton.addActionListener(e -> backArrow());
		this.backWeekButton.addActionListener(e -> backWeek());
		this.fowardWeekButton.addActionListener(e -> fowardWeek());

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

		// Clear old task labels (optional: store them in a list and remove from frame)
		for (Component comp : frame.getContentPane().getComponents()) {
			if (comp instanceof JLabel && comp != curDateLabel && comp != scheulerBackgroundImage && comp != mondayLabel
					&& comp != tuesdayLabel && comp != wednesdayLabel && comp != thursdayLabel && comp != fridayLabel
					&& comp != saturdayLabel && comp != sundayLabel) {
				frame.getContentPane().remove(comp);
			}
		}

		for (int i = 0; i < 7; i++) {
			LocalDate date = weekDates.get(i);
			dayLabels[i].setText(date.getDayOfMonth() + "");

			List<Scheduler.Task> tasks = scheduler.getOrDefault(date, new ArrayList<>());

			int yOffset = 150;
			for (Scheduler.Task task : tasks) {
				JLabel taskLabel = new JLabel(
						task.getProjAssn().getTitle() + " – " + task.getEstimatedMinutes() + " min");
				taskLabel.setFont(new Font("PT Sans Narrow", Font.PLAIN, 12));
				taskLabel.setBounds(267 + (i * 62), yOffset, 100, 20);
				frame.getContentPane().add(taskLabel);
				yOffset += 25;
			}
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

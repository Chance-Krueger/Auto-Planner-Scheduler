package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import model.Calendar;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Cursor;

public class CalendarView {

	private JFrame frame;
	private JButton leftButton;
	private JButton rightButton;
	private JLabel dayMonthLabel;
	private LocalDate curTime;

	private Calendar calendar;
	private String[] acct;
	private ArrayList<JLabel> dayLabels = new ArrayList<JLabel>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if (args.length > 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						CalendarView window = new CalendarView(args[0]);
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
						CalendarView window = new CalendarView();
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
	public CalendarView() {
		this.acct = new String[1];
		this.acct[0] = "";
		curTime = LocalDate.now();

		initialize();

	}

	public CalendarView(String email) {
		this.acct = new String[1];
		this.acct[0] = email; // -> NEED TO GO THROUGH SQL TO GRAB DATA? OR SHOULD I MAKE IT IN THE ACCOUNT?
		curTime = LocalDate.now();
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 982, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buildUIComponents();

		this.leftButton.addActionListener(e -> goBackInTime());
		this.rightButton.addActionListener(e -> goFowardinTime());
	}

	private void redo() {
		frame.getContentPane().removeAll(); // Clear existing components
		frame.revalidate();
		frame.repaint();

		buildUIComponents();

		this.leftButton.addActionListener(e -> goBackInTime());
		this.rightButton.addActionListener(e -> goFowardinTime());

	}

	private void buildUIComponents() {
		// Rebuild components
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.getContentPane().setLayout(null);

		createDates();

		JLabel coveringOfDates = new JLabel("");
		coveringOfDates.setBackground(Color.WHITE);
		coveringOfDates.setOpaque(true);
		coveringOfDates.setBounds(277, 205, 276, 181);
		frame.getContentPane().add(coveringOfDates);

		leftButton = new JButton("");
		leftButton.setBorder(null);
		leftButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leftButton.setBounds(264, 128, 29, 29);
		frame.getContentPane().add(leftButton);

		rightButton = new JButton("");
		rightButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		rightButton.setBorder(null);
		rightButton.setBounds(524, 128, 29, 29);
		frame.getContentPane().add(rightButton);

		dayMonthLabel = new JLabel(curTime.getMonth() + " " + curTime.getYear(), SwingConstants.CENTER);
		dayMonthLabel.setFont(new Font("PT Sans Caption", Font.PLAIN, 20));
		dayMonthLabel.setBounds(317, 128, 181, 34);
		frame.getContentPane().add(dayMonthLabel);

		JLabel calendarBackground = new JLabel("");
		calendarBackground.setBackground(Color.DARK_GRAY);
		calendarBackground.setIcon(
				new ImageIcon("/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/CalendarImage.png"));
		calendarBackground.setBounds(207, 0, 640, 548);
		frame.getContentPane().add(calendarBackground);
	}

	private void createDates() {
		clearOldLabels();

		LocalDate firstDay = LocalDate.of(curTime.getYear(), curTime.getMonth(), 1);
		int startOffset = calculateDayOffset(firstDay);
		int daysInMonth = curTime.lengthOfMonth();

		int dayNumber = 1;
		for (int i = 0; i < 42; i++) {
			if (i >= startOffset && dayNumber <= daysInMonth) {
				int[] pos = calculateGridPosition(i);
				addDayLabel(dayNumber++, pos[0], pos[1]);
			}
		}

		frame.revalidate();
		frame.repaint();
	}

	private void clearOldLabels() {
		for (JLabel lb : dayLabels) {
			frame.getContentPane().remove(lb);
		}
		dayLabels.clear();
	}

	private int calculateDayOffset(LocalDate firstDay) {
		String[] weekOfDays = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
		for (int i = 0; i < weekOfDays.length; i++) {
			if (firstDay.getDayOfWeek().toString().equals(weekOfDays[i]))
				return i;
		}
		return 0; // fallback
	}

	private int[] calculateGridPosition(int index) {
		int row = index / 7;
		int col = index % 7;

		int y = 205 + (row * 37);
		int x;
		switch (col) {
		case 0:
			x = 274;
			break;
		case 1:
			x = 320;
			break;
		case 2:
			x = 364;
			break;
		case 3:
			x = 406;
			break;
		case 4:
			x = 445;
			break;
		case 5:
			x = 482;
			break;
		case 6:
			x = 519;
			break;
		default:
			x = 274;
		}

		return new int[] { x, y };
	}

	private void addDayLabel(int day, int x, int y) {
		JLabel lb = new DayLabel(String.valueOf(day), x, y, Color.GRAY, Color.BLACK, true);
		frame.getContentPane().add(lb);
		dayLabels.add(lb);
	}

	private void goFowardinTime() {

		this.dayMonthLabel.setVisible(false);

		this.curTime = this.curTime.plusMonths(1);
		this.dayMonthLabel.setText(curTime.getMonth() + " " + curTime.getYear());

		this.dayMonthLabel.setVisible(true);
		redo(); // Refresh the calendar

	}

	private void goBackInTime() {

		this.dayMonthLabel.setVisible(false);

		this.curTime = this.curTime.minusMonths(1);
		this.dayMonthLabel.setText(curTime.getMonth() + " " + curTime.getYear());

		this.dayMonthLabel.setVisible(true);
		redo(); // Refresh the calendar
	}
}

package view;

import java.awt.EventQueue;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTable;

import model.BlockOffDates;
import model.Repeat;

import java.awt.ScrollPane;
import java.awt.Font;
import java.awt.Cursor;

public class BlockOffDatesView {

	private JFrame frame;
	private String email;
	private String cameFrom;
	private JButton cancelButton;
	private JButton confirmButton;
	private JButton backButton;
	private JTable table_1;
	private BlockOffDates bod;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if (args.length == 2) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						BlockOffDatesView window = new BlockOffDatesView(args[0], args[1]);
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
						BlockOffDatesView window = new BlockOffDatesView();
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
	public BlockOffDatesView() {
		this.cameFrom = "";
		this.email = "";
		this.bod = new BlockOffDates();

		// TESTING
		HashSet<LocalTime> s = new HashSet<LocalTime>();
		LocalTime l = LocalTime.of(0, 15);
		s.add(l);
		this.bod.changeBlockedTimeOfDay(Repeat.MON, s);
		System.out.println(this.bod.getBlockedDates());

		initialize();
	}

	public BlockOffDatesView(String email, String cameFrom) {
		this.cameFrom = cameFrom;
		this.email = email;
		this.bod = new BlockOffDates();
		initialize();
	}

	public BlockOffDatesView(String email, String cameFrom, BlockOffDates bod) {
		this.cameFrom = cameFrom;
		this.email = email;
		this.bod = bod;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 982, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		this.cancelButton = new JButton("");
		cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelButton.setBorder(null);
		cancelButton.setBounds(386, 462, 91, 29);
		frame.getContentPane().add(cancelButton);

		this.confirmButton = new JButton("");
		confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		confirmButton.setBorder(null);
		confirmButton.setBounds(501, 450, 188, 58);
		frame.getContentPane().add(confirmButton);

		this.backButton = new JButton("");
		backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backButton.setBorder(null);
		backButton.setBounds(290, 45, 301, 46);
		frame.getContentPane().add(backButton);

		JTable table = makeTable();
		table.setFont(new Font("PT Sans", Font.PLAIN, 15));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(40);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setFont(new Font("PT Sans", Font.PLAIN, 20));
		scrollPane.setBounds(269, 115, 444, 323);
		frame.getContentPane().add(scrollPane);

		JLabel blockOffDateImage = new JLabel("");
		blockOffDateImage.setIcon(new ImageIcon(
				"/Users/chancekrueger/Documents/GitHub/Auto-Planner-Scheduler/Photos/BlockOffDatesImage.png"));
		blockOffDateImage.setBounds(172, 0, 640, 548);
		frame.getContentPane().add(blockOffDateImage);

		frame.setBounds(100, 100, 982, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		this.cancelButton.addActionListener(e -> cancel());
		this.confirmButton.addActionListener(e -> confrim());
		this.backButton.addActionListener(e -> backArrow());
	}

	private JTable makeTable() {
		String[] timeSlots = generateTimeSlots(); // 00:00 to 23:45 (96 slots)
		String[] columnNames = new String[timeSlots.length + 1];
		columnNames[0] = "Day";

		// Add time labels as column names
		System.arraycopy(timeSlots, 0, columnNames, 1, timeSlots.length);

		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		Object[][] data = new Object[days.length][columnNames.length];

		Map<Repeat, Set<LocalTime>> saved = this.bod.getBlockedDates();

		for (int i = 0; i < days.length; i++) {
			data[i][0] = days[i]; // Set the day name in column 0
			for (int j = 1; j < columnNames.length; j++) {
				LocalTime time = LocalTime.parse(timeSlots[j - 1]);
				data[i][j] = saved.getOrDefault(Repeat.dayOfWeek(i), new HashSet<>()).contains(time);

			}
		}

		return new JTable(data, columnNames) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				return column == 0 ? String.class : Boolean.class; // Day or checkbox
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0; // Only time slot cells are editable
			}
		};
	}

	private String[] generateTimeSlots() {

		String[] time = new String[96];

		LocalTime cur = LocalTime.MIDNIGHT;
		time[0] = cur.toString();
		cur = cur.plusMinutes(15);
		int index = 1;
		while (cur != LocalTime.MIDNIGHT) {
			time[index] = cur.toString();

			index++;
			cur = cur.plusMinutes(15);
		}
		return time;
	}

	private void backArrow() {

		try {
			if (this.cameFrom.equals("SettingsView")) {
				String[] sArray = new String[1];
				sArray[0] = this.email;
				this.frame.dispose();
				SettingsView.main(sArray);
			} else {
				String[] sArray = new String[1];
				sArray[0] = this.email;
				this.frame.dispose();
				MainMenuView.main(sArray);
			}

		} catch (Exception e) {
			System.err.println("An error has occured");
		}

	}

	private void confrim() {
		// TODO Auto-generated method stub

	}

	private void cancel() {
		// TODO Auto-generated method stub

	}
}

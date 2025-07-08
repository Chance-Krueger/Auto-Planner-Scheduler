package view;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;

public class DayLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	public DayLabel(String day, int x, int y, Color back, Color forg, boolean btn) {
		setText(day);
		setHorizontalAlignment(JLabel.CENTER);
		setOpaque(true);
		setBackground(Color.WHITE);
//		setForeground(forg);
		if (btn)
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		setBounds(x, y, 29, 31);
	}
}

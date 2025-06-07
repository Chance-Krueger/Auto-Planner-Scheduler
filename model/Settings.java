package model;

import java.awt.Color;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Set;

public class Settings {

//	- be able to change theme colors
//	- be able to edit block off dates
//	- logout 

	private BlockOffDates bod;
	private Color themeColor;
	private Color accentColor;

	public Settings() {
		this.bod = new BlockOffDates();
		this.themeColor = Color.DARK_GRAY;
		this.accentColor = Color.LIGHT_GRAY;
	}

	public Settings(Settings settings) {
		this.bod = settings.bod;
		this.themeColor = settings.themeColor;
		this.accentColor = settings.accentColor;
	}

	public BlockOffDates getBod() {
		return new BlockOffDates(bod);
	}

	public void setBod(BlockOffDates bod) {
		this.bod = new BlockOffDates(bod);
	}

	public Color getThemeColor() {
		return themeColor;
	}

	public void setThemeColor(Color themeColor) {
		this.themeColor = themeColor;
	}

	public Color getAccentColor() {
		return accentColor;
	}

	public void setAccentColor(Color accentColor) {
		this.accentColor = accentColor;
	}

	public void changeBlockedTimeOfDay(int day, ArrayList<Set<LocalTime>> times) {
		this.bod.changeBlockedTimeOfDay(Repeat.dayOfWeek(day), new ArrayList<Set<LocalTime>>(times));
	}

	public void changeBlockedTimeOfDay(Repeat day, ArrayList<Set<LocalTime>> times) {
		this.bod.changeBlockedTimeOfDay(day, new ArrayList<Set<LocalTime>>(times));
	}
}

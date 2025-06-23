package model;

import java.awt.Color;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Set;

/**
 * The {@code Settings} class represents user-specific preferences within the
 * calendar system. These include visual customization (theme and accent colors)
 * and schedule management (blocked-off time periods). It acts as a
 * configuration container for adjusting the user's calendar experience.
 *
 * <p>
 * <b>Instance Variables:</b>
 * <ul>
 * <li>{@code bod} — A {@code BlockOffDates} object representing time blocks the
 * user is unavailable</li>
 * <li>{@code themeColor} — The user's selected UI theme color</li>
 * <li>{@code accentColor} — The user's selected accent color for UI
 * highlights</li>
 * </ul>
 *
 * <p>
 * Methods are provided to get or update each setting, and to change blocked
 * time periods using either an integer index (0–6 for weekdays) or a
 * {@code Repeat} constant.
 * 
 * @see BlockOffDates
 * @see Repeat
 * @see java.awt.Color
 * @see java.time.LocalTime
 * @see model.Calendar
 *
 * @author Chance Krueger
 */
public class Settings {

	private BlockOffDates bod;
	private Color themeColor;
	private Color accentColor;

	/**
	 * Constructs a new {@code Settings} object with default values. Theme is set to
	 * dark gray, accent to light gray, and no initial blocked times.
	 */
	public Settings() {
		this.bod = new BlockOffDates();
		this.themeColor = Color.DARK_GRAY;
		this.accentColor = Color.LIGHT_GRAY;
	}

	/**
	 * Copy constructor. Creates a new {@code Settings} object by copying another.
	 * Note: the {@code BlockOffDates} is deep copied for encapsulation.
	 *
	 * @param settings the {@code Settings} instance to copy
	 */
	public Settings(Settings settings) {
		this.bod = settings.bod;
		this.themeColor = settings.themeColor;
		this.accentColor = settings.accentColor;
	}

	/**
	 * Returns a deep copy of the {@code BlockOffDates} object.
	 *
	 * @return a new {@code BlockOffDates} instance
	 */
	public BlockOffDates getBod() {
		return new BlockOffDates(bod);
	}

	/**
	 * Sets the blocked-off dates using a deep copy of the given
	 * {@code BlockOffDates}.
	 *
	 * @param bod the new {@code BlockOffDates} to assign
	 */
	public void setBod(BlockOffDates bod) {
		this.bod = new BlockOffDates(bod);
	}

	/**
	 * Returns the currently selected theme color.
	 *
	 * @return the {@code Color} used for the theme
	 */
	public Color getThemeColor() {
		return themeColor;
	}

	/**
	 * Sets the theme color for the UI.
	 *
	 * @param themeColor the new {@code Color} to assign as the theme
	 */
	public void setThemeColor(Color themeColor) {
		this.themeColor = themeColor;
	}

	/**
	 * Returns the currently selected accent color.
	 *
	 * @return the {@code Color} used for UI highlights and accents
	 */
	public Color getAccentColor() {
		return accentColor;
	}

	/**
	 * Sets the accent color for the UI.
	 *
	 * @param accentColor the new {@code Color} to assign as the accent
	 */
	public void setAccentColor(Color accentColor) {
		this.accentColor = accentColor;
	}

	/**
	 * Updates the blocked time periods for a specific day of the week, specified by
	 * an integer (0 = Monday to 6 = Sunday).
	 *
	 * @param day   an integer representing the day of the week
	 * @param times a list of time intervals to block off for that day
	 */
	public void changeBlockedTimeOfDay(int day, ArrayList<Set<LocalTime>> times) {
		this.bod.changeBlockedTimeOfDay(Repeat.dayOfWeek(day), new ArrayList<Set<LocalTime>>(times));
	}

	/**
	 * Updates the blocked time periods for a specific day of the week, specified by
	 * a {@code Repeat} constant.
	 *
	 * @param day   the {@code Repeat} constant for the desired day
	 * @param times a list of time intervals to block off for that day
	 */
	public void changeBlockedTimeOfDay(Repeat day, ArrayList<Set<LocalTime>> times) {
		this.bod.changeBlockedTimeOfDay(day, new ArrayList<Set<LocalTime>>(times));
	}
}

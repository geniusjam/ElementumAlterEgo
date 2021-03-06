package com.strangeone101.elementumbot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	public enum Direction {LEFT, RIGHT};
	
	public enum WordType {
		
		WORD("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
		WORD_WITH_NUMERICS("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"),
		WORD_WITH_NUMERICS_CHARS("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!~@#$%^&*(),.?/|;:'\"\\<>-+_="),
		WORD_WITH_CHARS("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!~@#$%^&*(),.?/|;:'\"\\<>-+_="),
		NUMERICS("0123456789"),
		NUMERICS_WITH_MATHCHARS("0123456789+-/*()^%");
		
		WordType(String chars) {
			this.chars = chars;
		}
		
		private String chars;
		
		public String getChars() {
			return chars;
		}
	}
	
	/**
	 * Gets the boundary of the next found word in the string provided.
	 * @param string The string to search
	 * @param startIndex Where to start searching
	 * @param direction The direction to search ({@link Direction.LEFT} or {@link Direction.RIGHT})
	 * @param type The {@link WordType} that we are searching with
	 * @return The index that the boundary is at, or -1 if something went wrong.
	 */
	public static int getBoundary(String string, int startIndex, Direction direction, WordType type) {
		int index = startIndex;
		if (direction == Direction.LEFT) {
			index--;
			while (index > 0) {
				if (!type.getChars().contains(string.charAt(index) + "")) {
					return index - 1;
				}
				index--;
			}
			return index;
		} else if (direction == Direction.RIGHT) {
			index++;
			while (index < string.length()) {
				if (!type.getChars().contains(string.charAt(index) + "")) {
					return index - 1;
				}
				index++;
			} 
			return index - 1;
		}
		return -1;
	}
	
	/**
	 * Insert a string into an existing string between 2 points. The two points will also be replaced.
	 * @param string
	 * @param startIndex
	 * @param endIndex
	 * @param replaceString
	 * @return
	 */
	public static String replaceBetween(String string, int startIndex, int endIndex, String replaceString) {
		String first = string.substring(0, startIndex);
		String last = string.substring(endIndex + 1);
		return first + replaceString + last;
	}
	
	/**
	 * Splits the string every x characters. Allows the same 
	 * string to wrap to the next line after so many characters
	 * @param line The full string
	 * @param length The length to cut off to
	 * */
	public static String lengthSplit(String line, int length)
	{
		Pattern p = Pattern.compile("\\G\\s*(.{1,"+length+"})(?=\\s|$)", Pattern.DOTALL);
		Matcher m = p.matcher(line);
		String newString = "";
		char lastColor = 'f';
		while (m.find())
		{
			String string = m.group(1);
			if (string.contains("\u00A7")) {
				lastColor = string.charAt(string.lastIndexOf('\u00A7') + 1);
			}
			newString = newString + "\n\u00A7" + lastColor + string;
		}
		return newString.substring(1);
	}
	
	public static String combine(String[] array) {
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s = s + (i != 0 ? " " : "") + array[i];
		}
		return s;
	}

	/**
	 * Translates some emoji to plaintext
	 * @param string The string
	 * @return An emoji free string
	 */
	public static String emojiTranslate(String string) {
		return string.replaceAll(new String(Character.toChars(0x1F602)), "lol")
				.replaceAll(new String(Character.toChars(0x1F604)), ":D")
				.replaceAll(new String(Character.toChars(0x1F642)), ":)")
				.replaceAll(new String(Character.toChars(0x1F60A)), ":)")
				.replaceAll(new String(Character.toChars(0x1F609)), ";)")
				.replaceAll(new String(Character.toChars(0x1F61B)), ":P")
				.replaceAll(new String(Character.toChars(0x1F61C)), ";P")
				.replaceAll(new String(Character.toChars(0x1F609)), ";)")
				.replaceAll(new String(Character.toChars(0x1F641)), ":(")
				.replaceAll(new String(Character.toChars(0x1F622)), ":'(")
				.replaceAll(new String(Character.toChars(0x1F62E)), ":o")
				.replaceAll(new String(Character.toChars(0x1F620)), ">:(")
				.replaceAll(new String(Character.toChars(0x1F494)), "</3")
				.replaceAll(new String(Character.toChars(0x1F914)), "HMMMMM")
				.replaceAll(new String(Character.toChars(0x1F62E)), ":o")
			.replaceAll("\u2764\uFE0F", "\u00A7c\u2764\u00A7r") //Replace emoji hearts with red chatcolor ascii hearts
			.replaceAll("[\ud83c\udf00-\ud83d\ude4f]|[\ud83d\ude80-\ud83d\udeff]|[\u200D-\u27BF]]", ""); //Filter all emoji not in here
	}

}

package com.example.unique.presencetracking;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class text {
	public static String getDate(){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
		String dateString = format.format( new Date());
		return dateString;
	}
	public static String getTime(){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm",Locale.US);
		String timeString = format.format( new Date());
		return timeString;
	}
	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
//		Log.v("status",String.valueOf(isValid));
		return isValid;
	}
	public static String convertStreamToString(InputStream is)
			throws IOException {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[]
		 * buffer) method. We iterate until the Reader return -1 which means
		 * there's no more data to read. We use the StringWriter class to
		 * produce the string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"),8000);
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();

			}
			return writer.toString();
		} else {
			return "";
		}
	}


	//	android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(); return Boolean
}
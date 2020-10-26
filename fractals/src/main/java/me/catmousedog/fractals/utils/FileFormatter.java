package me.catmousedog.fractals.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FileFormatter extends Formatter {

	long ms = System.currentTimeMillis();

	@Override
	public String format(LogRecord record) {
		Throwable throwable = record.getThrown();
		String t;
		if (throwable != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.println();
			record.getThrown().printStackTrace(pw);
			pw.close();
			t = "\n" + sw.toString();
		} else {
			t = "";
		}

		return String.format("%1$8d | %2$s: %3$s%4$s\n", record.getMillis() - ms, record.getLevel().toString(),
				record.getMessage(), t);
	}

}

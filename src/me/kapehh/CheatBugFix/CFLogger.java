package me.kapehh.CheatBugFix;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 * Created with IntelliJ IDEA.
 * User: kapehh
 * Date: 14.11.13
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class CFLogger {
    public static final String fileSep = System.getProperty("file.separator");
    public static final String lineSep = System.getProperty("line.separator");

    public static class CFLogFormatter extends SimpleFormatter {
        private DateFormat dateFormat;

        @Override
        public synchronized String format(LogRecord record) {
            StringBuffer buf = new StringBuffer(180);
            if (dateFormat == null)
                dateFormat = DateFormat.getDateTimeInstance();

            buf.append('[');
            buf.append(dateFormat.format(new Date(record.getMillis())));
            buf.append("] ");
            buf.append(formatMessage(record));
            buf.append(lineSep);

            Throwable throwable = record.getThrown();
            if (throwable != null) {
                StringWriter sink = new StringWriter();
                throwable.printStackTrace(new PrintWriter(sink, true));
                buf.append(sink.toString());
            }
            return buf.toString();
        }
    }

    private static final Logger log = Logger.getLogger("me.kapehh.CheatBugFix.log");

    public static void PrintInfo(String str) {
        log.info(str);
    }

    public static void setup() {
        try {
            FileHandler fh = new FileHandler(TestCheatBugFix.instance.getDataFolder().getPath() + fileSep + "logs.log", true);
            fh.setFormatter(new CFLogFormatter());
            log.setUseParentHandlers(false);
            log.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shutDown() {
        for (Handler fh : log.getHandlers()) {
            log.removeHandler(fh);
            fh.close();
        }
    }
}

package io.github.atholton.sigmath.util;

import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LazyLogger extends Lazy<Logger> {
    String name;
    public LazyLogger(String name) {
        super(() -> initLogger(name));
    }

    private static Logger initLogger(String name) {
        Logger logger = Logger.getLogger(name);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd_HH-mm");
            String sdfstr = sdf.format(Calendar.getInstance().getTime());

            Files.createTempDirectory("sigmath");
            FileHandler fh = new FileHandler("%t/sigmath/" + sdfstr + "_%u.log", 1024 * 32, 8);

            SimpleFormatter f = new SimpleFormatter();
            logger.addHandler(fh);
            fh.setFormatter(f);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        return logger;
    }
}

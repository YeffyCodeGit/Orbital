package io.github.yeffycodegit.orbital.logger;


import java.util.Calendar;

/**
 * The OrbitalLogger class handles all the logging done in Orbital.
 *
 * @version 1.0.0
 * @author YeffyCodeGit
 */
public class OrbitalLogger {
    public void log(LogLevel level, String msg) {
        Calendar calendar = Calendar.getInstance();

        switch(level) {
            case ERROR:
                System.err.println("[ERROR] " + msg);
                break;
            case SUCCESS:
                System.out.println("[" + calendar.getTime() + "] " + msg);
                break;
            case WARNING:
                System.out.println("[WARNING] " + msg);
                break;
            default:
                System.out.println(msg);
        }
    }
}

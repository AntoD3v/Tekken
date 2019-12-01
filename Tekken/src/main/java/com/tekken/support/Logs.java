package com.tekken.support;

import com.tekken.Option;
import org.fusesource.jansi.AnsiConsole;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;


public class Logs implements Runnable {

    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public Logs() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        //Write in file
    }

    public static void debug(String message) {
        print("DEBUG: " + message);
    }

    public static void info(String message) {
        print("INFO: " + message);
    }

    public static void warn(String message) {
        print("WARN: " + message);
    }

    public static void error(String message) {
        print("ERROR: " + message);
    }

    public static void error(String message, Throwable t) {
        print("ERROR: " + message);
        t.printStackTrace();
    }

    private static void print(String message) {
        if(isLinux())
            printUnix(message);
        else
            printWin(message);
    }

    private static void printWin(String msg) {
        if (Option.TEKKEN_DEBUG) {
            LocalTime currentTime = LocalTime.now();
            String timestamp = currentTime.format(timeFormatter);

            AnsiConsole.systemUninstall();
            if(msg.startsWith("ERROR")) {
                System.out.println(ansi().eraseScreen().fg(RED).a(timestamp + " " + msg));
            }
            else if(msg.startsWith("WARN")) {
                System.out.println(ansi().eraseScreen().fg(CYAN).a(timestamp + " " + msg));
            }
            else {
                System.out.println(ansi().eraseScreen().fg(DEFAULT).a(timestamp + " " + msg));
            }
            AnsiConsole.systemInstall();

        }
    }

    private static void printUnix(String msg) {
        if (Option.TEKKEN_DEBUG) {
            LocalTime currentTime = LocalTime.now();
            String timestamp = currentTime.format(timeFormatter);

            if(msg.startsWith("ERROR")) {
                System.out.println("\u001B[31m"+timestamp + " " + msg);
            } else if(msg.startsWith("WARN")) {
                System.out.println("\u001B[36m"+timestamp + " " + msg);
            }else {
                System.out.println(timestamp + " " + msg);
            }
        }
    }

    private static boolean isLinux() {
        if(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0)
            return false;
        else
            return true;
    }
}

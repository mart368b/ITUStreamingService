package debugging;

import sun.rmi.runtime.Log;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Logger {
    private static final String SAVEPATH = "res/log";
    private static final int LOGLIMIT = 5;

    private static Logger instance;
    private StringBuilder log;

    private Logger(){
        log = new StringBuilder();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (Logger.hasLog()){
                Logger.log("End of session");
                Logger.save();
            }
        }));
    }

    private static boolean hasLog(){
        return instance != null;
    }

    /**
     * Create a simple log entry
     */
    public static void log( String msg){
        log(msg, LogTypes.INFO);
    }

    /**
     * Create a justified log entry
     */
    public static void log( String msg, LogTypes logType){
        if (instance == null){
            instance = new Logger();
        }
        instance.log.append(new SimpleDateFormat("HH:mm:ss").format(getTimestamp()));
        instance.log.append(":").append(getCallerClassName()).append(logType.getMsg());
        instance.log.append(msg).append("\n");
        if (logType == LogTypes.SOFTERROR){
            System.err.println(msg);
        }
    }

    private static void save(){
        File logDir = new File(SAVEPATH);
        if (!logDir.exists()){
            boolean succes = logDir.mkdir();
            if (!succes){
                return;
            }
        }
        ArrayList<File> logs = new ArrayList<>(Arrays.asList(logDir.listFiles()));
        if ( logs.size() >= LOGLIMIT){
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
            logs.sort(new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    int res = 100000;
                    try {
                        Date d1 = formatter.parse(f1.getName());
                        Date d2 = formatter.parse(f2.getName());
                        res = d1.compareTo(d2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return res;
                }
            });
            try {
                Date dt = formatter.parse(logs.get(0).getName());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Iterator<File> ite = logs.iterator();
            int logCount = logs.size();
            while (ite.hasNext() && logs.size() > LOGLIMIT - 1){
                File f = ite.next();
                boolean succes = f.delete();
                ite.remove();
            }
        }
        String filePath = SAVEPATH + "/" + new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(getTimestamp()) + ".log";
        saveLog( new File(filePath));
    }

    public static void logErrors(ArrayList<Exception> errors, String msg, LogTypes logType){
        if (errors.size() > 0){
            StringBuilder builder = new StringBuilder();
            builder.append(msg);
            for (Exception e: errors){
                builder.append("\n");
                builder.append(e.getCause().getMessage().toString());
            }
            Logger.log(builder.toString(), logType);
        }
    }

    private static void saveLog( File f){
        try {
            boolean succes = f.createNewFile();
            if (!succes){
                return;
            }
            FileWriter writer = new FileWriter(f);
            writer.write(instance.log.toString());
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static Timestamp getTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    private static String getCallerClassName() {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        String s = "";
        for ( int i = 1; i < stackTraces.length; i++){
            StackTraceElement e = stackTraces[i];
            s += i + "\n";
            if ( e.getClassName() != Logger.class.getName()){
                return e.getClassName();
            }
        }
        return "Logger";
    }
}

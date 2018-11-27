package debugging;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
            logs.sort(Comparator.comparing(File::getName));
            Iterator<File> ite = logs.iterator();
            int logCount = logs.size();
            while (ite.hasNext() && logCount > LOGLIMIT - 1){
                File f = ite.next();
                boolean succes = f.delete();
                if ( succes ){
                    logCount--;
                }
            }
        }
        String filePath = SAVEPATH + "/" + new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(getTimestamp()) + ".log";
        saveLog( new File(filePath));


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
        StackTraceElement stElements = Thread.currentThread().getStackTrace()[4];
        return stElements.getClassName();
    }
}

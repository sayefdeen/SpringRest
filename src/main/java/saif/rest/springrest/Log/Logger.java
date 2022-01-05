package saif.rest.springrest.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Logger {
    private static Logger logger;
    private static final ArrayList<String> logs = new ArrayList<>();

    private Logger(){}


    public static Logger getLogger() {
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    public void addLog(String message){
        logs.add(message + " " +  new Date());
        createFiles();
    }

    public static void createFiles() {
        try {
            FileWriter myWriter = new FileWriter("logs.txt");
            for (String message: logs) {
                myWriter.write(message+"\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

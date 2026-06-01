package rescue;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        
        // בדיקת מספר הארגומנטים
        if (args.length == 0) {
            System.out.println("ERROR: Missing commands file");
            return;
        }
        
        if (args.length > 1) {
            System.out.println("ERROR: Invalid arguments");
            return;
        }
        
        // בדיקה שקובץ הפקודות קיים וניתן לקריאה
        File commandsFile = new File(args[0]);
        if (!commandsFile.exists() || !commandsFile.canRead()) {
            System.out.println("ERROR: Cannot read commands file");
            return;
        }
        
        // ניקוי errors.log
        FileManager.clearErrorLog();
        
        // יצירת המערכת
        NationalRescueAuthority system = new NationalRescueAuthority();
        
        // עיבוד הפקודות
        FileManager.processCommands(args[0], system);
    }
}
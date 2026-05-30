package rescue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// מחלקה לניהול קבצים (שמירת נתונים וטעינתם)    
public class FileManager {

    // קוראת קבצים ומוסיפה את המשתמשים התקינים למערכת
    public static void loadResponders(String path, NationalRescueAuthority system){
        
    }

    //  קוראת קובץ אירועים יוצרת אוביקט ומוסיפה את האירועים התקינים למערכת 
    public static void loadIncidents(String path, NationalRescueAuthority system){

    }

    //קוראת את קובץ הפקודות ומבצעת את הפקודות
    public static void processCommands(String path, NationalRescueAuthority system){

    }

    // מוסיפה שורת שגיאה לקובץ
    public static void logError(String fileName, int lineNumber, String message){
        System.err.println("Error in file: " + fileName + " at line: " + lineNumber + " - " + message);
    }
   
    // מנקה את קובץ השגיאות
    public static void clearErrorLog(){

    }

}

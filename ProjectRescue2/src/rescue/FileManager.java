package rescue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// מחלקה לניהול קבצים (שמירת נתונים וטעינתם)    
public class FileManager {

    // קוראת קבצים ומוסיפה את המשתמשים התקינים למערכת
    public static void loadResponders(String path, NationalRescueAuthority system){
        String line;
        // שורת הכותרת היא 1
        int lineNumber = 1; 

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // דילוג על שורת הכותרת
            br.readLine(); 

            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",", -1);
                if (data.length != 10) {
                    logError(path, lineNumber, "Invalid row format");
                    continue;
                }
                
                String type = data[0].trim();
                if (!type.equals("MEDIC") && !type.equals("DISPATCHER") && !type.equals("DRONE")) {
                    logError(path, lineNumber, "Invalid type");
                    continue;
                }
 
                // בדיקת תקינות לת"ז 
                String idString = data[1].trim();
                int id;

                try {
                    id = Integer.parseInt(idString);
                    if (id <= 0) {
                        logError(path, lineNumber, "Invalid id");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    logError(path, lineNumber, "Invalid id");
                    continue;
                }

                // בדיקה לשם תקין
                String name = data[2].trim();
 
                // בדיקה האם דרגת הגישה תקינה
                ClearanceLevel clearance;
                try {
                    clearance = ClearanceLevel.valueOf(data[3].trim());
                } catch (IllegalArgumentException e) {
                    logError(path, lineNumber, "Invalid clearance");
                    continue;
                }
 
                Responder r = null;
 
                if (type.equals("MEDIC")) {
                    if (name.isEmpty() || idString.length() != 9 || !data[5].trim().isEmpty() || !data[6].trim().isEmpty() || !data[7].trim().isEmpty() || !data[8].trim().isEmpty()) {
                        logError(path, lineNumber, "Invalid row format");
                        continue;
                    }

                    // SPECIALIZATION
                    Specialization spec;
                    try {
                        spec = Specialization.valueOf(data[4].trim());
                    } catch (IllegalArgumentException e) {
                        logError(path, lineNumber, "Invalid specialization");
                        continue;
                    }

                    // בדיקה האם השכר תקין
                    double salary;
                    try {
                        salary = Double.parseDouble(data[9].trim());
                        if (salary < NationalRescueAuthority.minSalaryMedic) {
                            logError(path, lineNumber, "Invalid salary");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        logError(path, lineNumber, "Invalid salary");
                        continue;
                    }

                    Medic m = new Medic(id, false, clearance, name, 0.0, salary, spec);
                    r = m;
 
                } else if (type.equals("DISPATCHER")) {
                    if (name.isEmpty() || idString.length() != 9 || !data[4].trim().isEmpty() || !data[7].trim().isEmpty() || !data[8].trim().isEmpty()) {
                        logError(path, lineNumber, "Invalid row format");
                        continue;
                    }

                    // בדיקה האם הניסיון תקין
                    int experience;
                    try {
                        experience = Integer.parseInt(data[5].trim());
                        if (experience < 0) {
                            logError(path, lineNumber, "Invalid experience");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        logError(path, lineNumber, "Invalid experience");
                        continue;
                    }

                    // מדיקה האם העדפת המשקה תקינה
                    DrinkPreference drink;
                    try {
                        drink = DrinkPreference.valueOf(data[6].trim());
                    } catch (IllegalArgumentException e) {
                        logError(path, lineNumber, "Invalid drink");
                        continue;
                    }

                    // בדיקה האם השכר תקין
                    double salary;
                    try {
                        salary = Double.parseDouble(data[9].trim());
                        if (salary < NationalRescueAuthority.minSalaryDispatcher) {
                            logError(path, lineNumber, "Invalid salary");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        logError(path, lineNumber, "Invalid salary");
                        continue;
                    }

                    Dispatcher d = new Dispatcher(id, false, clearance, name, 0.0, salary, experience, drink);
                    r = d;
 
                  // בדיקה האם הנתונים של הרחפן תקינים
                } else {
                    if (!name.isEmpty() || idString.length() != 5 || !data[4].trim().isEmpty() || !data[5].trim().isEmpty() || !data[6].trim().isEmpty() || !data[9].trim().isEmpty()) {
                        logError(path, lineNumber, "Invalid row format");
                        continue;
                    }

                    // בדיקה האם שעות העבודה ללא תשלום תקינות
                    double workNoCharge;
                    try {
                        workNoCharge = Double.parseDouble(data[7].trim());
                        if (workNoCharge < 0) {
                            logError(path, lineNumber, "Invalid work no charge");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        logError(path, lineNumber, "Invalid work no charge");
                        continue;
                    }

                    // בדיקה האם מספר הכשלים תקין
                    int failures;
                    try {
                        failures = Integer.parseInt(data[8].trim());
                        if (failures < 0) {
                            logError(path, lineNumber, "Invalid failures");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        logError(path, lineNumber, "Invalid failures");
                        continue;
                    }

                    Drone dr = new Drone(id, false, clearance, workNoCharge, 0);
                    dr.setWorkNoCharge(workNoCharge);
                    for (int i = 0; i < failures; i++) dr.addAFailure();
                    r = dr;
                }
 
                // בדיקת כפילות והוספה
                if (system.getResponderById(id) != null) {
                    logError(path, lineNumber, "Duplicate responder");
                } else {
                    system.addResponder(r);
                }
            }
        } catch (IOException e) {
            logError(path, 0, "Cannot read file");
        }
    }


    //  קוראת קובץ אירועים יוצרת אוביקט ומוסיפה את האירועים התקינים למערכת 
    public static void loadIncidents(String path, NationalRescueAuthority system) {
        String line;
        int lineNumber = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // דילוג על שורת הכותרת
            br.readLine();

            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",", -1);
                if (data.length != 8) {
                    logError(path, lineNumber, "Invalid row format");
                    continue;
                }

                // TYPE
                String type = data[0].trim();
                if (type.isEmpty()) {
                    logError(path, lineNumber, "Invalid incident type");
                    continue;
                }

                // בדיקה האם הסידורי תקין
                int serial;
                try {
                    serial = Integer.parseInt(data[1].trim());
                    if (serial <= 0) {
                        logError(path, lineNumber, "Invalid serial");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    logError(path, lineNumber, "Invalid serial");
                    continue;
                }

                // CLEARANCE
                ClearanceLevel clearance;
                try {
                    clearance = ClearanceLevel.valueOf(data[2].trim());
                } catch (IllegalArgumentException e) {
                    logError(path, lineNumber, "Invalid clearance");
                    continue;
                }

                // בדיקה האם מספר השעות תקין
                double hours;
                try {
                    hours = Double.parseDouble(data[3].trim());
                    if (hours <= 0) {
                        logError(path, lineNumber, "Invalid hours");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    logError(path, lineNumber, "Invalid hours");
                    continue;
                }

                // בדיקה האם מספר המוקדנים תקין
                int dispatchers;
                try {
                    dispatchers = Integer.parseInt(data[4].trim());
                    if (dispatchers < 0) {
                        logError(path, lineNumber, "Invalid dispatchers");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    logError(path, lineNumber, "Invalid dispatchers");
                    continue;
                }

                // בדיקה האם מספר החדר תקין 
                int room;
                try {
                    room = Integer.parseInt(data[5].trim());
                    if (room <= 0) {
                        logError(path, lineNumber, "Invalid room");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    logError(path, lineNumber, "Invalid room");
                    continue;
                }

                // בדיקה האם מספר הפרמדיקים תקין
                int medics;
                try {
                    medics = Integer.parseInt(data[6].trim());
                    if (medics < 0) {
                        logError(path, lineNumber, "Invalid medics");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    logError(path, lineNumber, "Invalid medics");
                    continue;
                }

                // בדיקה האם מספר הרחפנים תקין
                int drones;
                try {
                    drones = Integer.parseInt(data[7].trim());
                    if (drones < 0) {
                        logError(path, lineNumber, "Invalid drones");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    logError(path, lineNumber, "Invalid drones");
                    continue;
                }

                // יצירת האירוע והוספה
                Incident incident = new Incident(type, serial, clearance, hours,
                        dispatchers, room, medics, drones);

                if (system.getIncidentByKey(type + "-" + serial) != null) {
                    logError(path, lineNumber, "Duplicate incident");
                } else {
                    system.addIncident(incident);
                }
            }
        } catch (IOException e) {
            logError(path, 0, "Cannot read file");
        }
    }

    //קוראת את קובץ הפקודות ומבצעת את הפקודות
    public static void processCommands(String path, NationalRescueAuthority system){
        String line;
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String trimmedLine = line.trim();
                
                // דילוג על שורות ריקות או הערות
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                    continue;
                }

                String[] parts = trimmedLine.split("\\s+");
                String command = parts[0];

                try {
                    if (command.equals("LOAD_DATA")) {
                        if (parts.length != 3) {
                            logError("commands", lineNumber, "Invalid command format");
                        } else {
                            // קריאה למתודות הטעינה 
                            loadResponders(parts[1], system);
                            loadIncidents(parts[2], system);
                        }
                    } else if (command.equals("ASSIGN_ALL")) {
                        if (parts.length != 1) {
                            logError("commands", lineNumber, "Invalid command format");
                        } else {
                            system.assignAll();
                        }
                    } else if (command.equals("EXPORT_REPORT")) {
                        if (parts.length != 2) {
                            logError("commands", lineNumber, "Invalid command format");
                        } else {
                            system.exportReport(parts[1]);
                        }
                    } else {
                        logError("commands", lineNumber, "Unknown command");
                    }
                } catch (Exception e) {
                    // טיפול כללי בשגיאות הרצה של פקודה ספציפית
                    logError("commands", lineNumber, e.getMessage());
                }
            }
        } catch (IOException e) {
            // שגיאה בקריאת קובץ הפקודות עצמו 
            logError(path, 0, "Cannot read commands file");
        }
    }


    // פונקציה שמוסיפה שורת שגיאה לקובץ
    public static void logError(String fileName, int lineNumber, String message) {
        String errorLine = "ERROR," + fileName + "," + lineNumber + "," + message;
 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/errors.log", true))) {
            writer.write(errorLine);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Critical Error: Could not write to log file: " + errorLine);
        }
    }

    
    public static void logError(String fileName, String identifier, String message) {
        String errorLine = "ERROR," + fileName + "," + identifier + "," + message;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/errors.log", true))) {
            writer.write(errorLine);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Critical Error: " + errorLine);
        }
    }
   
    // מנקה את קובץ השגיאות
    public static  void clearErrorLog() {
        File directory = new File("output");
        if (!directory.exists()) {
            directory.mkdirs();
        }
 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output/errors.log", false))) {
            // הקובץ ייווצר או יתרוקן
        } catch (IOException e) {
            System.err.println("Critical Error: Could not clear log file.");
        }
    }


    // יוצרת קובץ דוח מלא עם כל הנתונים 
    public static void exportReport(String path, NationalRescueAuthority system) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            
            // שימוש נכון ב-system כדי לקבל נתונים
            ArrayList<Incident> allIncidents = system.getAllIncidentsSorted();
            int assigned = 0, unassigned = 0;
            for (Incident inc : allIncidents) {
                // וודאי שיש מתודה isAssigned בתוך Incident
                if (inc.getContributors().size() > 0) assigned++; 
                else unassigned++;
            }
            
            writer.write("PROJECT_RESCUE_REPORT"); writer.newLine();
            writer.write("RESPONDERS_COUNT=" + system.getAllResponders().size()); writer.newLine();
            writer.write("INCIDENTS_COUNT=" + allIncidents.size()); writer.newLine();
            writer.write("ASSIGNED_INCIDENTS_COUNT=" + assigned); writer.newLine();
            writer.write("UNASSIGNED_INCIDENTS_COUNT=" + unassigned); writer.newLine();
            writer.newLine();
            
            // משתתפים
            writer.write("RESPONDERS"); writer.newLine();
            for (Responder r : system.getRespondersByType("MEDIC")) {
                Medic medic = (Medic) r;
                writer.write(String.format("MEDIC,%d,%s,%s,%s,%.1f,%.1f,%b",
                        medic.getId(), medic.getName(), medic.getClearance(), medic.getSpecialization(),
                        medic.getWorkHours(), medic.getSalary(), medic.isBusy()));
                writer.newLine();
            }
            for (Responder r : system.getRespondersByType("DISPATCHER")) {
                Dispatcher dispatcher = (Dispatcher) r;
                writer.write(String.format("DISPATCHER,%d,%s,%s,%d,%s,%.1f,%.1f,%b",
                        dispatcher.getId(), dispatcher.getName(), dispatcher.getClearance(), dispatcher.getExperience(),
                        dispatcher.getDrink(), dispatcher.getWorkHours(), dispatcher.getSalary(), dispatcher.isBusy()));
                writer.newLine();
            }
            for (Responder r : system.getRespondersByType("DRONE")) {
                Drone drone = (Drone) r;
                writer.write(String.format("DRONE,%d,%s,%.1f,%d,%b",
                        drone.getId(), drone.getClearance(), drone.getWorkNoCharge(), drone.getReportedFailures(), drone.isBusy()));
                writer.newLine();
            }
            
            writer.newLine();
            // אירועים
            writer.write("INCIDENTS"); writer.newLine();
            for (Incident inc : allIncidents) {
                String status = inc.getContributors().isEmpty() ? "UNASSIGNED" : "ASSIGNED";
                writer.write(String.format("INCIDENT,%s,%d,%s,%.1f,%s,%d",
                        inc.getType(), inc.getSerialNumber(), inc.getClearance(), inc.getHours(), status,
                        inc.getContributors().size()));
                writer.newLine();
            }
            
            writer.newLine();
            writer.write("END_REPORT"); writer.newLine();
            
        } catch (IOException e) {
            logError(path, 0, "Cannot write file");
        }
    }
    
}
 

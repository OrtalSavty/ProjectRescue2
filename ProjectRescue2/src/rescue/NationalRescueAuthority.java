package rescue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// רשות ההצלה הלאומית
public class NationalRescueAuthority {
    // מספר השעות עבודה המקסימאלי של חובש
    public static int medicMaxWork;
    // מספר השעות עבודה המקסימאלי של מוקדן
    public static int dispatcherMaxWork;
    // שכר מינימום של מוקדן
    public static double minSalaryDispatcher;
    // שכר מינימום של חובש
    public static double minSalaryMedic;
    // שעות עבודה שרחפן יכול לעבוד ללא טעינה
    public static int droneWorkNoCharge;
    // רשימת כל המחלקת חילוץ
    private Registry<Integer, Responder> responders = new Registry<>();
    // רשימת כל האירועים
    private Registry<String, Incident> incidents = new Registry<>();

    //  בנאי
    public NationalRescueAuthority(int medicMaxWork, int dispatcherMaxWork,
                                   double minSalaryDispatcher, double minSalaryMedic,
                                   int droneWorkNoCharge) {
        NationalRescueAuthority.medicMaxWork = medicMaxWork;
        NationalRescueAuthority.dispatcherMaxWork = dispatcherMaxWork;
        NationalRescueAuthority.minSalaryDispatcher = minSalaryDispatcher;
        NationalRescueAuthority.minSalaryMedic = minSalaryMedic;
        NationalRescueAuthority.droneWorkNoCharge = droneWorkNoCharge;
    }

    // בנאי ריק
    public NationalRescueAuthority() {
        // ערכים ברירת מחדל לתקינות המערכת
        NationalRescueAuthority.medicMaxWork = 40;
        NationalRescueAuthority.dispatcherMaxWork = 40;
        NationalRescueAuthority.minSalaryDispatcher = 8500.0;
        NationalRescueAuthority.minSalaryMedic = 9500.0;
        NationalRescueAuthority.droneWorkNoCharge = 4;
    }

    //  מתודות get
    public int getMedicMaxWork() {
        return medicMaxWork;
    }
    public int getDispatcherMaxWork() {
        return dispatcherMaxWork;
    }
    public double getMinSalaryDispatcher() {
        return minSalaryDispatcher;
    }
    public double getMinSalaryMedic() {
        return minSalaryMedic;
    }
    public int getDroneWorkNoCharge() {
        return droneWorkNoCharge;
    }
    public ArrayList<Responder> getResponders() {
        return new ArrayList<>(responders.getAll());
    }
    public ArrayList<Incident> getIncidents() {
        return new ArrayList<>(incidents.getAll());

    }

    //  מתודות set
    public void setMedicMaxWork(int medicMaxWork) {
        NationalRescueAuthority.medicMaxWork = medicMaxWork;
    }
    public void setDispatcherMaxWork(int dispatcherMaxWork) {
        NationalRescueAuthority.dispatcherMaxWork = dispatcherMaxWork;
    }
    public void setMinSalaryDispatcher(double minSalaryDispatcher) {
        NationalRescueAuthority.minSalaryDispatcher = minSalaryDispatcher;
    }
    public void setMinSalaryMedic(double minSalaryMedic) {  
        NationalRescueAuthority.minSalaryMedic = minSalaryMedic;
    }
    public void setDroneWorkNoCharge(int droneWorkNoCharge) {
        NationalRescueAuthority.droneWorkNoCharge = droneWorkNoCharge;
    }
    public void setResponders(ArrayList<Responder> responders) {
        this.responders.clear();
        //הוספה לרשימה
        for (Responder r : responders) {
            this.responders.add(r);
        }
    }
    public void setIncidents(ArrayList<Incident> incidents) {
        this.incidents.clear();
        //הוספה לרשימה
        for (Incident i : incidents) {
            this.incidents.add(i);
        }
    }

    // toString
    @Override
    public String toString() {
        return "NationalRescueAuthority{" +
                "medicMaxWork=" + medicMaxWork +
                ", dispatcherMaxWork=" + dispatcherMaxWork +
                ", minSalaryDispatcher=" + minSalaryDispatcher +
                ", minSalaryMedic=" + minSalaryMedic +
                ", droneWorkNoCharge=" + droneWorkNoCharge +
                ", responders=" + responders +
                ", incidents=" + incidents +
                '}';
    }

    // מעדכנת את כל ה-Responders
    public void newWeek() {
        for (Responder r : responders.getAll()) {
            // כולם חוזרים להיות פנויים
            r.setBusy(false);
            if (r instanceof Human) {
                // איפוס שעות עבודה לאנשים
                ((Human) r).setZeroWorkHours();
            } else if (r instanceof Drone) {
                // טעינה מלאה לרחפנים
                ((Drone) r).setWorkNoCharge();
            }
        }
    }

    // מוסיפה משתפי פעולה
    public boolean addResponder(Responder r){
        if (responders.containsKey(r.getKey())) {
            return false;
        } else {
            return responders.add(r);
        }
    }

    // מסירה משתפי פעולה לפי ת"ז
    public boolean removeResponder(Responder r){
        return responders.removeByKey(r.getKey());
    }

    // מחזירה משתפי פעולה לפי ת"ז
    public Responder getResponderById(int id){
        return responders.get(id);
    }

    // מחזירה רשימה של כל המשתתפים במערכת
    public ArrayList<Responder> getAllResponders(){
        return responders.getAll();
    }

    // מוסיפה אירוע למערכת
    public boolean addIncident(Incident in){
        if (incidents.containsKey(in.getKey())) {
            return false;
        } else {
            return incidents.add(in);
        }
    }

    // מסירה אירוע לפי המפתח שלו
    public boolean removeIncident(Incident in){
        return incidents.removeByKey(in.getKey());
    }

    // מחזירה אירוע לפי מפתח שלו
    public Incident getIncidentByKey(String key){
        return incidents.get(key);
    }

    // מחזירה רשימה של כל האירועים במערכת
    public ArrayList<Incident> getAllIncidents(){
        return incidents.getAll();
    }

    //מחזירה את כל המשתתפים מהסוג המבוקש
    public ArrayList<Responder> getRespondersByType(String type){
        ArrayList<Responder> result = new ArrayList<>();
        
        // סינון לפי סוג המשתתף
        for (Responder r : responders.getAll()) {
            if ((type.equals("MEDIC") && r instanceof Medic) ||
                (type.equals("DISPATCHER") && r instanceof Dispatcher) ||
                (type.equals("DRONE") && r instanceof Drone)) {
                result.add(r);
            }
        }

        // מיון 
        Collections.sort(result, new Comparator<Responder>() {
            @Override
            public int compare(Responder r1, Responder r2) {
                if (type.equals("MEDIC")) {
                    return Integer.compare(r1.getId(), r2.getId());
                } 
                else if (type.equals("DISPATCHER")) {
                    Dispatcher d1 = (Dispatcher) r1;
                    Dispatcher d2 = (Dispatcher) r2;
                    if (d1.getExperience() != d2.getExperience())
                        return Integer.compare(d1.getExperience(), d2.getExperience());
                    return Integer.compare(d1.getId(), d2.getId());
                } 
                else if (type.equals("DRONE")) {
                    Drone dr1 = (Drone) r1;
                    Drone dr2 = (Drone) r2;
                    if (dr1.getWorkNoCharge() != dr2.getWorkNoCharge())
                        return Double.compare(dr1.getWorkNoCharge(), dr2.getWorkNoCharge());
                    if (dr1.getReportedFailures() != dr2.getReportedFailures())
                        return Integer.compare(dr1.getReportedFailures(), dr2.getReportedFailures());
                    return Integer.compare(dr1.getId(), dr2.getId());
                }
                return 0;
            }
        });
        return result;
    }

    // מחזירה את כל האירועים ממוינים לפי סוג
    public ArrayList<Incident> getAllIncidentsSorted(){
        ArrayList<Incident> sortedIncidents = new ArrayList<>(incidents.getAll());
    
        sortedIncidents.sort((i1, i2) -> {
            // השוואה ראשונה לפי סוג
            int typeCompare = i1.getType().compareTo(i2.getType());
            if (typeCompare != 0) {
                return typeCompare;
            }
           
            return Integer.compare(i1.getSerialNumber(), i2.getSerialNumber());
        });
        
        return sortedIncidents;
    }

    //מקצה משתתפים לכל אירוע
    public void assignAll(){
        // קבלת רשימת אירועים ממויינת
        ArrayList<Incident> sortedIncidents = getAllIncidentsSorted();
        
        // מעבר על הרשימה הממוינת והקצאה
        for (Incident incident : sortedIncidents) {
            // בודקים אם האירוע טרם הוקצה  
            incident.assignResponders(this);
        }
    }

    // יוצרת קובץ דוח מלא 
    public void exportReport(String path) {
        try {
        // ניסיון לייצא את הדוח
        FileManager.exportReport(path, this);
        } catch (Exception e) {
            // טיפול בשגיאות
            try (FileWriter fw = new FileWriter("errors.log", true)) {
                fw.write("ERROR," + path + ",0,Cannot write file\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}

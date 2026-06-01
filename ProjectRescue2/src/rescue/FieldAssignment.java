package rescue;
import java.util.ArrayList;

// הביצוע הפיזי בשטח
public class FieldAssignment {
    // מספר הפרמידיקים שנדרש לאירוע
    private int numMedics;
    // מספר הרחפנים שנדרש לאירוע
    private int numDrones;
    // רשימת כל המשתתפים שהוקצו לחלק השטח של האירוע
    private  ArrayList<FieldParticipants> fieldParticipants;

    //  בנאי רגיל
    public FieldAssignment(int numMedics, int numDrones, ArrayList<rescue.FieldParticipants> fieldParticipants) {
        this.numMedics = numMedics;
        this.numDrones = numDrones;
        this.fieldParticipants = new ArrayList<>();
    }


    // בנאי מאתחל
    public FieldAssignment(int numMedics, int numDrones) {
        this.numMedics = numMedics;
        this.numDrones = numDrones;
        this.fieldParticipants = new ArrayList<>();
    }

    //  מתודות get
    public int getNumMedics() {
        return numMedics;
    }
    public int getNumDrones() {
        return numDrones;
    }
    public ArrayList<FieldParticipants> getFieldParticipants() {
        return fieldParticipants;
    }

    //  מתודות set
    public void setNumMedics(int numMedics) {
        this.numMedics = numMedics;
    }
    public void setNumDrones(int numDrones) {
        this.numDrones = numDrones;
    }
    public void setFieldParticipants(ArrayList<FieldParticipants> fieldParticipants) {
        this.fieldParticipants = fieldParticipants;
    }

    // toString
    @Override
    public String toString() {
        return "FieldAssignment{" +
                "numMedics=" + numMedics +
                ", numDrones=" + numDrones +
                ", fieldParticipants=" + fieldParticipants +
                '}';
    }

    private int getSpecializationPriority(Specialization specialization) {
        switch (specialization) {
            case PARAMEDIC:
                return 1;
            case SEARCHER:
                return 2;
            case NAVIGATOR:
                return 3;
            case TRAINEE:
                return 4;
            default:
                return 5;
        }
    }

    public void assignAllFieldParticipants(double hours, ClearanceLevel clearance, NationalRescueAuthority system) {        // שתי רשימות חדשות אחת לרחפנים ואחת לפרמדיקים
        ArrayList<Medic> availableMedics = new ArrayList<>();
        ArrayList<Drone> availableDrones = new ArrayList<>();
        // עוברים על כל אנשי מחלקת החילוץ
        for (Responder r : system.getResponders() ) {
            // בדיקה: לא עסוק, סיווג מתאים ויכול לעבוד את השעות
            if (!r.isBusy() && r.getClearance().ordinal() >= clearance.ordinal() && r.canWorkHours(hours)) {
                // אם הפרמדיק עבר את הבדיקה נוסיף לרשימה
                if (r instanceof Medic) {
                    availableMedics.add((Medic) r);
                    // אם הרחפן עבר את הבדיקה נוסיף לרשימה
                } else if (r instanceof Drone) {
                    availableDrones.add((Drone) r);
                }
            }
        }
        // שנה: בחר את הפרמדיקים המתאימים לפי עדיפות התמחות
        availableMedics.sort((m1, m2) -> {
            int cmp;
            if (clearance == ClearanceLevel.LOW) {
                cmp = Integer.compare(m1.getClearance().ordinal(), m2.getClearance().ordinal());
            } else {
                cmp = Integer.compare(m2.getClearance().ordinal(), m1.getClearance().ordinal());
            }
            if (cmp != 0) {
                return cmp;
            }
            int rank1 = getSpecializationPriority(m1.getSpecialization());
            int rank2 = getSpecializationPriority(m2.getSpecialization());
            if (rank1 != rank2) {
                return Integer.compare(rank1, rank2);
            }
            return Integer.compare(m1.getId(), m2.getId());
        });

        if (availableMedics.size() < this.numMedics) {
            throw new AssignFieldException("Not enough medics");
        }
        if (availableDrones.size() < numDrones) {
            throw new AssignFieldException("Not enough field forces");
        }

        for (int i = 0; i < this.numMedics; i++) {
            enroll(availableMedics.get(i), hours);
        }
        // מיון על פי שעות עבודה ללא טעינה, עם עדיפות לתקלות ו-ID
        availableDrones.sort((d1, d2) -> {
            int cmp = Double.compare(d1.getWorkNoCharge(), d2.getWorkNoCharge());
            if (cmp != 0) {
                return cmp;
            }
            cmp = Integer.compare(d1.getReportedFailures(), d2.getReportedFailures());
            if (cmp != 0) {
                return cmp;
            }
            return Integer.compare(d1.getId(), d2.getId());
        });
        for (int i = 0; i < numDrones; i++) {
            enroll(availableDrones.get(i), hours);
        }
    }

    public void clearAssignments(double hours) {
        for (FieldParticipants p : this.fieldParticipants) {
            if (p instanceof Responder) {
                Responder r = (Responder) p;
                r.setBusy(false);
                if (r instanceof Human) {
                    Human human = (Human) r;
                    human.setWorkHours(human.getWorkHours() - hours);
                } else if (r instanceof Drone) {
                    Drone drone = (Drone) r;
                    drone.setWorkNoCharge(drone.getWorkNoCharge() + hours);
                }
            }
        }
        this.fieldParticipants.clear();
    }

    // מתודת עזר
    private void enroll(FieldParticipants p, double hours) {
        if (p instanceof Responder) {
            Responder r = (Responder) p;
            // מעדכנים שהוא הפך לעסוק
            r.setBusy(true);
            // מעדכנים את שעות העבודה שלו
            r.didWork(hours);
        }
        //  מוסיפים אותו לרשימה הסופית של המשתתפים באירוע
        this.fieldParticipants.add(p);
    }


}

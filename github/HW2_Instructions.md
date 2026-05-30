**החוג למערכות מידע ![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.001.png)**

**קורס: תכנות מתקדם** 

**תרגיל בית מס. 2 נושאים מרכזיים:**  

` `Files, Generics, Collections

הוראות הגשה: בזוגות, עד לתאריך 02/06/26.  

."ProjectRescue2" :שם הפרוייקט ￿  ."rescue" :בשם (package) כל המחלקות צריכות להיות בחבילה ￿

- יש להגיש קובץ zip שמכיל בתוכו את כל הקבצים java. הרלוונטיים. נא וודאו פעם נוספת שכל הקבצים הנדרשים נמצאים. במידה ועניתם על שאלת הבונוס: נא לכלול בתוך ה-zip גם קובץ txt. עם 

התשובה לשאלה. 

- השם של הקובץ יהיה בפורמט הבא:  

` `ID1\_ID2\_HW2.zip  o

- בקשות הארכה מסיבות מוצדקות - חייבות להתבצע לפני מועד ההגשה. נתון לשיקול דעתם של 

המרצה/מתרגל. 

- **מותר להשתמש רק במבני הנתונים שנלמדו עד כה במסגרת הקורס, כגון מערך רגיל או רשימה .**  

**מבנה ההגשה:**  

` `ID1\_ID2\_HW2.zip

` `│

` `├── src/

` `│   └── rescue/

` `│       ├── Main.java

` `│       ├── Identifiable.java

` `│       ├── Registry.java

` `│       ├── FileManager.java

` `──└       │שאר מחלקות המערכת 

` `│

` `├── data/

` `──└   │קבצי הבדיקה 

` `│

` `├── output/

` `──└   │קבצי הפלט 

` `│

` `└── screenshots/

`     `├── run\_basic.png

`     `├── run\_advanced.png

`     `└── report\_created.png

התרגיל ייבדק אוטומטית. לכן יש להקפיד על שמות מחלקות, שמות מתודות, שמות קבצים, סדר שורות, פורמט פלט, רווחים, פסיקים ואותיות גדולות/ק טנות. 

**התוכנית תרוץ כך:**  

` `java rescue.Main data/commands\_basic.txt

**במקרה של שגיאת ארגומנטים יש להדפיס בדיוק אחת מההודעות הבאות:**  

` `ERROR: Missing commands file

` `ERROR: Invalid arguments

` `ERROR: Cannot read commands file

בזמן ריצה תקינה אין להדפיס למסך דבר. 

התרגיל מתבסס על הפתרון שלכם מתרגיל בית 1. כל המחלקות, הממשקים, ה Enums-והחריגות מתרגיל בית 1 ממשיכים להתקיים ולעבוד. מותר להוסיף להם שדות ומתודות לפי הצורך. ![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.002.png)

בתרגיל בית 2 יש להוסיף רק את המחלקות/הממשקים החדשים הבאים: 

` `Identifiable

` `Registry

` `FileManager

- תמשיך להיות המחלקה המרכזית שמנהלת את מצב המערכת  NationalRescueAuthority![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.003.png)

`  `**Identifiable**

יש להוסיף ממשק גנרי בשם  Identifiable, הממשק מייצג אובייקט שיש לו מפתח ייחודי. 

הגדרה: 

` `public interface Identifiable<K> {

` `K getKey();

` `}

- חייב לממש  Responder\
  ` `Identifiable<Integer>

` `idמחזירה את  ה getKey המתודה Responder ב

- חייב לממש  Incident

  ` `Identifiable<String>

` `TYPE-SERIAL :מחזירה מפתח בפורמ ט getKey המתודה-Incident, ב

` `FIRE-1 :לדוגמה![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.004.png)

`  `**Registry**

יש להוסיף מחלקה גנרית בשם  Registry, המחלקה מנהלת אובייקטים לפי מפתח. כותרת המחלקה: 

` `public class Registry<K, T extends Identifiable<K>>

` `private HashMap<K, T> items;  public Registry()

**שדה חובה:**  

**הבנאי:**  

הבנאי יאתחל HashMap ריק. **מתודות חובה:** 

` `public boolean add(T item)

המתודה מוסיפה אובייקט חדש. 

` `public boolean removeByKey(K key)

המתודה מסירה אובייקט לפי מפתח. 

` `public T get(K key)

המתודה מחזירה אובייקט לפי מפתח. 

`  `nullאם לא קיים אובייקט עם מפתח כזה, יש להחזיר

` `public boolean containsKey(K key)

המתודה מחזירה true אם קיים אובייקט עם המפתח הנתון 

` `public int size()

המתודה מחזירה את מספר האובייקטים השמורים. 

` `public ArrayList<T> getAll()

המתודה מחזירה את כל האובייקטים בתוך ArrayList חדש. 

` `public void clear()

המתודה מוחקת את כל האובייק טים. ![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.005.png)

`  `**FileManager**  FileManager  יש להוסיף מחלקה בשם

המחלקה אחראית על קריאת קבצים, ביצוע פקודות מקובץ וכתיבת שגיאות. אין שדות חובה במחלקה זו. 

**מתודות חובה:**  

` `public static void loadResponders(String path, NationalRescueAuthority system)

המתודה קוראת קובץ משתתפים, יוצרת Medic, Dispatcher או Drone לפי הנתונים, ומוסיפה את המשתתפים התקינים למערכת. 

` `public static void loadIncidents(String path, NationalRescueAuthority system)

המתודה קוראת קובץ אירועים, יוצרת אובייקטי ,Incident ומוסיפה את האירועים התקינים למערכת. 

` `public static void processCommands(String path, NationalRescueAuthority system)

המתודה קוראת קובץ פקודות ומבצעת את הפקודות לפי הסדר שבו הן מופיעות בקובץ. 

` `public static void logError(String fileName, int lineNumber, String message)

המתודה מוסיפה שורת שגיאה לקובץ: 

` `output/errors.log

פורמט שגיאה: 

` `ERROR,fileName,lineNumber,message

` `public static void clearErrorLog()

המתודה מנקה את הקובץ output/errors.log בתחילת כל ריצה. אם הקובץ לא קיים, יש ליצור אותו. ![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.006.png)

`  `**NationalRescueAuthority עדכונים ב**  Registry  כך שתנהל את המשתתפים והאירועים בעזרת NationalRescueAuthority יש לעדכן את

**שדות חובה:**  

` `private Registry<Integer, Responder> responders;  private Registry<String, Incident> incidents;

בנוסף, יש לשמור את ערכי המערכת שהוגדרו בתרגיל בית 1, כגון מגבלת שעות עבודה, שכר מינימלי, וערך ברירת מחדל לשעות עבודה של רחפן ללא טעינה. 

חובה להשתמש לפחות בשלושה אוספים שונים מתוך Java Collections Framework במהלך הפתרון, למשל 

`  `LinkedList.אוHashMap, ArrayList, TreeSet, PriorityQueue, HashSet 

**מתודות חובה:**  

` `public boolean addResponder(Responder r)

מוסיפה משתתף למערכת. 

` `public boolean removeResponder(Responder r)

` `id  מסירה משתתף לפי

` `public Responder getResponderById(int id)

` `id  מחזירה משתתף לפי

` `public ArrayList<Responder> getAllResponders()

מחזירה רשימה של כל המשתתפים במערכת. 

` `public boolean addIncident(Incident in)

מוסיפה אירוע למערכת. 

` `public boolean removeIncident(Incident in)

מסירה אירוע לפי המפתח שלו. 

` `public Incident getIncidentByKey(String key)

מחזירה אירוע לפי מפתח. 

` `public ArrayList<Incident> getAllIncidents()

מחזירה רשימה של כל האירועים במערכת. 

` `public ArrayList<Responder> getRespondersByType(String type)

מחזירה את כל המשתתפים מהסוג המבוקש. 

הערכים החוקיים הם: 

` `MEDIC

` `DISPATCHER

` `DRONE

סדר המיון: 

- בסדר עולה id לפי —  MEDIC

` `id  בסדר עולה, ובשוויון לפי experience לפי —  DISPATCHER  id  בסדר עולה, ובשוויון לפי workNoCharge לפי  DRONE —

אם type אינו תקין, יש להחזיר רשימה ריק ה. 

` `public ArrayList<Incident> getAllIncidentsSorted()

מחזירה את כל האירועים ממוינים לפי TYPE בסדר אלפביתי עולה, ובשוויון לפי SERIAL בסדר עולה. 

` `public void assignAll()

מנסה להקצות משתתפים לכל האירועים במערכת לפי כללי ההקצאה מתרגיל בית 1. האירועים יטופלו לפי TYPE בסדר אלפביתי עולה, ואז SERIAL בסדר עולה. 

אירוע שכבר הוקצה לא יוקצה שוב. 

אירוע שלא ניתן להקצות יישאר לא מוקצה. 

` `public void exportReport(String path)

יוצרת קובץ דוח מלא לפי הפורמט שמוגדר בהמשך. 

אם לא ניתן לכתוב את הקובץ, יש לרשום שגיאה ל errors.log-בפורמט: 

` `ERROR,path,0,Cannot write file![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.007.png)

**קובץ המשתתפים** 

קובץ המשתתפים הוא קובץ CSV השורה הראשונה היא שורת כותרת ויש לדלג עליה. מבנה השורה: 

` `TYPE,ID,NAME,CLEARANCE,SPECIALIZATION,EXPERIENCE,DRINK,WORK\_NO\_CHARGE,FAILURES,SALARY

כל שורה חייבת להכיל בדיוק 10 שדות. 

- סוג המשתתף  – TYPE

ערכים חוקיים: 

` `MEDIC

` `DISPATCHER

` `DRONE

- מזהה המשתתף – ID

יש לבדוק לפי הכללים מתרגיל בית 1. 

- שם המשתתף – NAME

` `DISPATCHER ול  MEDIC רלוונטי ל

עבור DRONE השדה צריך להיות ריק. 

- רמת הסיווג – CLEARANCE

ערכים חוקיים: 

` `LOW

` `MEDIUM

` `HIGH

` `MEDICרלוונטי ל – SPECIALIZATION  enum Specializationהערכים החוקיים הם הערכים שהוגדרו ב

עבור DISPATCHER ו DRONE-השדה צריך להיות ריק. 

` `DISPATCHERרלוונטי ל – EXPERIENCE

הערך הוא מספר שלם. 

עבור MEDIC וDRONE השדה צריך להיות ריק. 

` `DISPATCHERרלוונטי ל – DRINK

הערכים החוקיים הם הערכים שהוגדרו ב- 

עבור MEDIC וDRONE  השדה צריך להיות ריק. 

` `DRONEרלוונטי ל – WORK\_NO\_CHARGE

הערך הוא מספר ממשי. 

עבור MEDIC וDISPATCHER  השדה צריך להיות ריק. 

` `DRONEרלוונטי ל – FAILURES

הערך הוא מספר שלם. 

עבור MEDIC ו DISPATCHER-השדה צריך להיות ריק. 

` `DISPATCHERול  MEDICרלוונטי ל – SALARY

יש לבדוק לפי כללי השכר מתרגיל בית 1. 

עבור DRONE השדה צריך להיות ריק. 

` `output/errors.logאם שורה אינה תקינה, אין לעצור את התוכנית. יש לדלג על השורה ולרשום שגיאה ל![ref1]

**קובץ האירועים**  

קובץ האירועים הוא קובץ CSV השורה הראשונה היא שורת כותרת ויש לדלג עליה. 

מבנה השורה: 

` `TYPE,SERIAL,CLEARANCE,HOURS,DISPATCHERS,ROOM,MEDICS,DRONES

כל שורה חייבת להכיל בדיוק8 שדות. 

- סוג האירוע – TYPE

הערך הוא מחרוזת שאינה ריקה. 

- מספר סידורי של האירוע – SERIAL

הערך חייב להיות מספר שלם חיובי. 

- רמת הסיווג הנדרשת – CLEARANCE

ערכים חוקיים: 

` `LOW

` `MEDIUM

` `HIGH

- מספר שעות העבודה הדרוש לאירוע – HOURS

הערך חייב להיות מספר ממשי חיובי. 

- מספר המוקדנים הדרוש – DISPATCHERS

הערך חייב להיות מספר שלם שאינו שלילי. 

- מספר חדר הבקרה – ROOM

הערך חייב להיות מספר שלם חיובי. 

- מספר הפרמדיקים הדרוש – MEDICS

הערך חייב להיות מספר שלם שאינו שלילי. 

- מספר הרחפנים הדרוש – DRONES

הערך חייב להיות מספר שלם שאינו שלילי. 

המפתח של אירוע הוא: 

` `TYPE-SERIAL

אם כבר קיים אירוע עם אותו מפתח, יש לרשום שגיאה. 

` `output/errors.logאם שורה אינה תקינה, אין לעצור את התוכנית. יש לדלג על השורה ולרשום שגיאה ל![ref1]

**הודעות שגיאה בקבצי קלט** 

כל שגיאה תיכתב לקובץ: 

` `output/errors.log

פורמט: 

` `ERROR,fileName,lineNumber,message

- הוא מספר השורה בקובץ, כולל שורת הכותרת lineNumber

הודעות שגיאה אפשריות: 

` `Invalid row format

` `Invalid type

` `Invalid id

` `Invalid clearance

` `Invalid specialization

` `Invalid experience

` `Invalid drink

` `Invalid work no charge

` `Invalid failures

` `Invalid salary

` `Duplicate responder

` `Invalid incident type

` `Invalid serial

` `Invalid hours

` `Invalid dispatchers

` `Invalid room

` `Invalid medics

` `Invalid drones

` `Duplicate incident

` `Cannot read file

` `Cannot write file

אם אין שגיאות, הקובץ errors.log צריך להיות קובץ ריק. ![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.009.png)

**קובץ הפקודות ![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.010.png)**

התוכנית מקבלת כארגומנט נתיב לקובץ פקודות. 

יש לדלג על שורות ריקות ועל שורות שמתחילות ב# . 

המערכת תומכת בשלוש פקודות בלבד: 

` `LOAD\_DATA respondersPath incidentsPath

הפקודה קוראת את קובץ המשתתפים ואת קובץ האירועים ומוסיפה את הנתונים התקינים למערכת. אם אחד הקבצים לא ניתן לקריאה, יש לרשום: 

` `ERROR,path,0,Cannot read file

` `ASSIGN\_ALL

הפקודה מנסה להקצות משתתפים לכל האירועים במערכת לפי כללי תרגיל בית 1. אם אירוע לא ניתן להקצאה, יש לרשום שגיאה בפורמט: 

` `ERROR,ASSIGN\_ALL,incidentKey,message

- הוא incidentKey\
  ` `TYPE-SERIAL

הודעות אפשריות: 

` `Not enough dispatchers

` `Not enough medics

` `Not enough drones

` `EXPORT\_REPORT path   path.הפקודה יוצרת דוח מלא של מצב המערכת בקובץ

אם מופיעה פקודה לא מוכרת, יש לרשום: 

` `ERROR,commands,lineNumber,Unknown command

אם מספר הפרמטרים של פקודה אינו תקין, יש לרשום: 

` `ERROR,commands,lineNumber,Invalid command format![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.011.png)

**קובץ הדוח**  

הדוח נוצר על ידי הפקודה: 

` `EXPORT\_REPORT path

כל ערך double ייכתב עם ספרה אחת אחרי הנקודה. 

מבנה הדוח: 

` `PROJECT\_RESCUE\_REPORT  RESPONDERS\_COUNT=<number>

` `INCIDENTS\_COUNT=<number>  ASSIGNED\_INCIDENTS\_COUNT=<number>

` `UNASSIGNED\_INCIDENTS\_COUNT=<number>

` `RESPONDERS

` `<responder line>

` `<responder line>

` `INCIDENTS

` `<incident line>

` `<incident line>

` `END\_REPORT

- מספר כל המשתתפים במערכת – RESPONDERS\_COUNT
  - מספר כל האירועים במערכת – INCIDENTS\_COUNT
- מספר האירועים שהוקצו בהצלחה – ASSIGNED\_INCIDENTS\_COUNT
  - מספר האירועים שלא הוקצו – UNASSIGNED\_INCIDENTS\_COUNT

המשתתפים יודפסו לפי הסדר: 

` `MEDIC

` `DISPATCHER

` `DRONE

`  `getRespondersByType בתוך כל סוג יש להשתמש בסדר שהוגדר במתודה

`  `MEDIC מבנה שורת  MEDIC,id,name,clearance,specialization,workHours,salary,busy

` `DISPATCHER  מבנה שורת  DISPATCHER,id,name,clearance,experience,drink,workHours,salary,busy

` `DRONE  מבנה שורת  DRONE,id,clearance,workNoCharge,reportedFailures,busy

האירועים יודפסו לפי TYPE בסדר אלפביתי עולה, ובשוויון לפי SERIAL בסדר עולה. 

` `INCIDENT  מבנה שורת  INCIDENT,type,serial,clearance,hours,status,contributorsCount

` `UNASSIGNED או ASSIGNED :יהיה status

אם האירוע לא הוקצה contributorsCount ,יהיה 0. 

אין להוסיף רווחים לפני או אחרי פסיקים. 

אין להוסיף רווחים לפני או אחרי הסימן =  

אין להוסיף שורות או טקסט שלא מופיעים בפורמט הנדרש. ![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.012.png)

`  `**Main**

המחלקה Main תכיל את המתודה: 

` `public static void main(String[] args)

אם args.length == 0 יש להדפיס: 

` `ERROR: Missing commands file

אם args.length > 1 יש להדפיס: 

` `ERROR: Invalid arguments

אם קובץ הפקודות אינו קיים או אינו ניתן לקריאה, יש להדפיס: 

` `ERROR: Cannot read commands file

במקרה תקין Main ,תבצע: 

`  `errors.log  ניקוי

`  `NationalRescueAuthority יצירת  FileManager.processCommandsקריאה ל![](Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.013.png)

**קבצי בדיקה וצילומי מסך** 

יינתנו קבצי בדיקה לדוגמה בתיקיית data וקבצי expected\_output להשוואה. 

הבדיקה האוטומטית עשויה להשתמש בקבצים אחרים באותו מבנה. יש לצרף תיקיית screenshots ובה שלושה צילומי מסך: 

` `run\_basic.png

` `run\_advanced.png

` `report\_created.png

`  `commands\_basic.txt הרצה עם - run\_basic.png   commands\_advanced.txt הרצה עם - run\_advanced.png

`  `outputצילום שמראה שקבצי הפלט נוצרו בתיקיית  ה - report\_created.png

**בהצלחה!** 

[ref1]: Aspose.Words.d717fc94-c739-4668-91ae-37a7a4fa89a4.008.png

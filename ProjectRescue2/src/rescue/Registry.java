package rescue;

import java.util.ArrayList;
import java.util.HashMap;


// מחלקה גנרית לרישום אובייקטים עם מפתחות ייחודיים
public class Registry<K, T extends Identifiable<K>>
{
    private HashMap<K, T> items;

    // בנאי
    public Registry() {
        this.items = new HashMap<>();
    }

    //המתודה מוסיפה אוביקט לרשימה
    public boolean add(T item){
        // נבדוק שהפרמטר שקיבלנו לא ריק
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        // נבדוק אם כבר קיים אובייקט עם אותו מפתח ברשימה
        K key = item.getKey();
        // אם כבר קיים אובייקט עם אותו מפתח ברשימה, לא נוסיף
        if (items.containsKey(key)) {
            return false; 
        }
        // אם לא קיים אובייקט עם אותו מפתח ברשימה, נוסיף אותו
        items.put(key, item);
        return true;
    }

    // מתודה שמסירה אובייקט לפי מפתח
    public boolean removeByKey(K key){
        // נבדוק שהפרמטר שקיבלנו לא ריק
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        // נבדוק אם קיים אובייקט עם המפתח הנתון ברשימה
        if (!items.containsKey(key)) {
            return false; 
        }
        // אם קיים אובייקט עם המפתח הנתון ברשימה, נסיר אותו
        items.remove(key);
        return true;
    }

    // מתודה שמחזירה אוביקט לפי מפתח 
    public T get(K key){
        // נבדוק שהפרמטר שקיבלנו לא ריק
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        // נבדוק אם קיים אובייקט עם המפתח הנתון ברשימה
        if (!items.containsKey(key)) {
            return null; 
        }
        // אם קיים אובייקט עם המפתח הנתון ברשימה, נחזיר אותו
        return items.get(key);
    }

    // בודקת אם קיים ברשימה אוביקט עם המפתח הנתון
    public boolean containsKey(K key)
    {
        // נבדוק שהפרמטר שקיבלנו לא ריק
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return items.containsKey(key);
    }

    // מחזירה את מספר האובייקטים השמורים
    public int size(){
        return items.size();
    }

    // מחזירה את כל האובייקטים השמורים 
    public ArrayList<T> getAll(){
        return new ArrayList<>(items.values());
    }

    // מוחקת את כל האובייקטים 
    public void clear(){
        items.clear();
    }


}

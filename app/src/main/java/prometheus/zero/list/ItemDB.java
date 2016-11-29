package prometheus.zero.list;

import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.List;

public class ItemDB extends SugarRecord {
    public String itemName;
    public Long itemCost;
    public boolean itemStatus;

    public ItemDB() {
    }

    static List<ItemDB> getItemList() {
        return Select.from(ItemDB.class).list();
    }

    public static ItemDB getItemById(Long itemId) {
        return ItemDB.findById(ItemDB.class, itemId);
    }

    public static void deleteAllItems() {
        ItemDB.deleteAll(ItemDB.class);
    }

    public static void deleteItemById(Long itemId) {
        ItemDB.delete(getItemById(itemId));
    }
}

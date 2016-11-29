package prometheus.zero.list;

import java.util.List;

class ListModel {

    List<ItemDB> getAllList() {
        return ItemDB.getItemList();
    }

    ItemDB getItemById(String id) {
        return ItemDB.getItemById(Long.parseLong(id));
    }

    List<BudgetDB> getBudget() {
        return BudgetDB.getBudget();
    }

    void removeItemById(Long itemId) {
        ItemDB.deleteItemById(itemId);
    }

    void updateItemStatus(Long itemId, boolean check) {
        ItemDB itemDB = ItemDB.getItemById(itemId);
        itemDB.itemStatus = check;
        itemDB.save();
    }
}

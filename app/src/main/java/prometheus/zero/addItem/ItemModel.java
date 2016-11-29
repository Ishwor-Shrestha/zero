package prometheus.zero.addItem;

import prometheus.zero.list.ItemDB;

class ItemModel {
    void saveItem(String name, Long cost) {
        ItemDB item = new ItemDB();
        item.itemName = name;
        item.itemCost = cost;
        item.itemStatus = false;
        item.save();
    }

    void updateItem(String name, Long cost, Long itemId) {
        ItemDB itemDB = ItemDB.getItemById(itemId);
        itemDB.itemName = name;
        itemDB.itemCost = cost;
        itemDB.save();
    }
}

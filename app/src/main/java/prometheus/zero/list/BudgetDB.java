package prometheus.zero.list;

import com.orm.SugarRecord;
import com.orm.query.Select;

import java.util.List;

public class BudgetDB extends SugarRecord {
    public Long budget;

    public BudgetDB() {
    }

    public static List<BudgetDB> getBudget() {
        return Select.from(BudgetDB.class).list();
    }

    public static void deleteAllItems() {
        BudgetDB.deleteAll(BudgetDB.class);
    }
}

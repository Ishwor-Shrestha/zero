package prometheus.zero.addBudget;

import java.util.List;

import prometheus.zero.list.BudgetDB;

class BudgetModel {
    void saveBudget(Long budget) {
        List<BudgetDB> budgetDB = BudgetDB.getBudget();
        if (budgetDB.size() > 0) {
            budgetDB.get(0).budget = budget;
            budgetDB.get(0).save();
        } else {
            BudgetDB budgetDB1 = new BudgetDB();
            budgetDB1.budget = budget;
            budgetDB1.save();
        }
    }
}

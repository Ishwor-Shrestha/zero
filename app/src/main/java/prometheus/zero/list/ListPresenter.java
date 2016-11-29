package prometheus.zero.list;

import android.support.annotation.NonNull;

import java.util.List;

import prometheus.zero.utils.GuavaUtil;

class ListPresenter implements ListContract.Listener {
    @NonNull
    private final ListContract.View mHomeView;
    private ListModel listModel;

    ListPresenter(@NonNull ListContract.View mHomeView) {
        this.mHomeView = GuavaUtil.checkNotNull(mHomeView);
        mHomeView.setListener(this);
        listModel = new ListModel();
    }

    @Override
    public void setUpFAB() {
        mHomeView.setUpFAB();
    }

    @Override
    public void setUpList() {
        List<ItemDB> items = listModel.getAllList();
        if (items.size() > 0) {
            mHomeView.setUpList(items);
        }
    }

    @Override
    public List<ItemDB> getAllItems() {
        return listModel.getAllList();
    }

    @Override
    public void setListScrollListener() {
        mHomeView.setListScrollListener();
    }

    @Override
    public void setCostData() {
        List<ItemDB> items = listModel.getAllList();
        List<BudgetDB> budgetDB = listModel.getBudget();

        if (budgetDB.size() > 0) {
            mHomeView.setBudget(budgetDB.get(0).budget);
        } else {
            mHomeView.setBudget(0L);
        }

        if (items.size() > 0) {
            Long estimation = 0L;
            Long spent = 0L;

            for (ItemDB itemDB : items) {
                estimation += itemDB.itemCost;
                if (itemDB.itemStatus) {
                    spent += itemDB.itemCost;
                }
            }

            mHomeView.setEstimation(estimation, estimation > budgetDB.get(0).budget);
            mHomeView.setSpent(spent);

            if (budgetDB.size() > 0) {
                mHomeView.setRemaining(budgetDB.get(0).budget - spent);
            } else {
                mHomeView.setRemaining(0L);
            }
        }
    }

    @Override
    public void setBudget() {
        List<BudgetDB> budgetDB = listModel.getBudget();
        if (budgetDB.size() > 0) {
            mHomeView.setBudget(budgetDB.get(0).budget);
        } else {
            mHomeView.setBudget(0L);
        }
    }

    @Override
    public void setEstimation() {
        List<ItemDB> items = listModel.getAllList();
        List<BudgetDB> budgetDB = listModel.getBudget();
        Long estimation = 0L;
        Long spent = 0L;
        for (ItemDB itemDB : items) {
            estimation += itemDB.itemCost;
            if (itemDB.itemStatus) {
                spent += itemDB.itemCost;
            }
        }
        mHomeView.setEstimation(estimation, estimation > budgetDB.get(0).budget);
        mHomeView.setSpent(spent);
        mHomeView.setRemaining(budgetDB.get(0).budget - spent);
    }

    @Override
    public void setExpenditure() {
        List<ItemDB> items = listModel.getAllList();
        List<BudgetDB> budgetDB = listModel.getBudget();
        Long spent = 0L;
        for (ItemDB itemDB : items) {
            if (itemDB.itemStatus) {
                spent += itemDB.itemCost;
            }
        }

        mHomeView.setSpent(spent);
        mHomeView.setRemaining(budgetDB.get(0).budget - spent);
    }

    @Override
    public void toggleToolBarShadow(boolean show) {
        mHomeView.toggleToolBarShadow(show);
    }

    @Override
    public void toggleItemStatus(Long itemId, boolean check) {
        listModel.updateItemStatus(itemId, check);
    }

    @Override
    public void toggleStrikeThrough(boolean strike, int position) {
        mHomeView.toggleStrikeThrough(strike, position);
    }

    @Override
    public void toggleEditButton(boolean show, int position) {
        mHomeView.toggleEditButton(show, position);
    }

    @Override
    public void openItemForm() {
        mHomeView.openItemForm();
    }

    @Override
    public void openBudgetForm(Long budget) {
        mHomeView.openBudgetForm(budget);
    }

    @Override
    public void removeItem(Long itemId) {
        listModel.removeItemById(itemId);
    }

    @Override
    public void openRemoveConfirmation(Long itemId, int position) {
        mHomeView.openRemoveConfirmation("Do you want to delete item?", itemId, position);
    }
}

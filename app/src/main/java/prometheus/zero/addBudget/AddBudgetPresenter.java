package prometheus.zero.addBudget;

import android.support.annotation.NonNull;

import prometheus.zero.utils.GuavaUtil;

class AddBudgetPresenter implements AddBudgetContract.Listener {
    @NonNull
    private final AddBudgetContract.View mAddBudgetView;
    private BudgetModel budgetModel;

    AddBudgetPresenter(@NonNull AddBudgetContract.View mDishHomeView) {
        this.mAddBudgetView = GuavaUtil.checkNotNull(mDishHomeView);
        mDishHomeView.setListener(this);
        budgetModel = new BudgetModel();
    }

    @Override
    public void receiveArgumentData() {
        mAddBudgetView.receiveArgumentData();
    }

    @Override
    public void fillFormData() {
        mAddBudgetView.fillFormData();
    }

    @Override
    public void saveBudget(Long budget) {
        budgetModel.saveBudget(budget);
    }
}

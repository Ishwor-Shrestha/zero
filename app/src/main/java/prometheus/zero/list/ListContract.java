package prometheus.zero.list;

import java.util.List;

interface ListContract {
    interface View {

        void setUpFAB();

        void setUpList(List<ItemDB> items);

        void setListScrollListener();

        void setBudget(Long budget);

        void setEstimation(Long estimation, boolean overBudget);

        void setSpent(Long spent);

        void setRemaining(Long remaining);

        void toggleToolBarShadow(boolean show);

        void toggleStrikeThrough(boolean strike, int position);

        void toggleEditButton(boolean show, int position);

        void openItemForm();

        void openBudgetForm(Long budget);

        void openRemoveConfirmation(String message, Long itemId, int position);

        void setListener(Listener listener);
    }

    interface Listener {

        void setUpFAB();

        void setUpList();

        void setListScrollListener();

        void setCostData();

        void setBudget();

        void setEstimation();

        void setExpenditure();

        List<ItemDB> getAllItems();

        void toggleToolBarShadow(boolean show);

        void toggleItemStatus(Long itemId, boolean check);

        void toggleStrikeThrough(boolean strike, int position);

        void toggleEditButton(boolean show, int position);

        void openItemForm();

        void openBudgetForm(Long budget);

        void removeItem(Long itemId);

        void openRemoveConfirmation(Long itemId, int position);
    }
}

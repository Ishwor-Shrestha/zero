package prometheus.zero.addBudget;

interface AddBudgetContract {

    interface View {
        void receiveArgumentData();

        void fillFormData();

        void setListener(Listener listener);
    }

    interface Listener {
        void receiveArgumentData();

        void fillFormData();

        void saveBudget(Long budget);
    }
}

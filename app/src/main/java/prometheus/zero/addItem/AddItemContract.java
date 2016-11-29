package prometheus.zero.addItem;

interface AddItemContract {

    interface View {
        void receiveArgumentData();

        void fillFormData();

        void setListener(Listener listener);
    }

    interface Listener {
        void receiveArgumentData();

        void fillFormData();

        void saveItem(String name, Long price);

        void updateItem(String name, Long price, Long itemId);
    }
}

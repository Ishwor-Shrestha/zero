package prometheus.zero.addItem;

import android.support.annotation.NonNull;

import prometheus.zero.utils.GuavaUtil;
import prometheus.zero.utils.LogUtil;

class AddItemPresenter implements AddItemContract.Listener {
    @NonNull
    private final AddItemContract.View mAddItemView;
    private ItemModel itemModel;

    AddItemPresenter(@NonNull AddItemContract.View mDishHomeView) {
        this.mAddItemView = GuavaUtil.checkNotNull(mDishHomeView);
        mDishHomeView.setListener(this);
        itemModel = new ItemModel();
    }

    @Override
    public void receiveArgumentData() {
        mAddItemView.receiveArgumentData();
    }

    @Override
    public void fillFormData() {
        mAddItemView.fillFormData();
    }

    @Override
    public void saveItem(String name, Long price) {
        itemModel.saveItem(name, price);
    }

    @Override
    public void updateItem(String name, Long price, Long itemId) {
        itemModel.updateItem(name, price, itemId);
    }
}

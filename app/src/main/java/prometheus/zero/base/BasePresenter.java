package prometheus.zero.base;

import android.support.annotation.NonNull;

import prometheus.zero.utils.GuavaUtil;

class BasePresenter implements BaseContract.BaseListener {
    @NonNull
    private final BaseContract.View mBaseView;

    BasePresenter(@NonNull BaseContract.View mBaseView) {
        this.mBaseView = GuavaUtil.checkNotNull(mBaseView);
        mBaseView.setListener(this);
    }

    @Override
    public void loadToolbar() {
        mBaseView.loadToolbar();
    }
}

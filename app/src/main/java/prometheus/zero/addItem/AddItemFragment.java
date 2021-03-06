package prometheus.zero.addItem;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import prometheus.zero.R;

public class AddItemFragment extends BottomSheetDialogFragment implements AddItemContract.View, Validator.ValidationListener {
    @NotEmpty
    @BindView(R.id.etName)
    MaterialEditText etName;
    @NotEmpty
    @BindView(R.id.etPrice)
    MaterialEditText etPrice;
    @BindView(R.id.btnSaveItem)
    FloatingActionButton btnSaveItem;

    private View mainView;
    private Dialog dialog;
    private AddItemContract.Listener listener;
    private FragmentActivity fragmentActivity;
    private ItemCallBack itemCallBack;

    private String name;
    private Long price;
    private Long itemId;
    private int position;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.add_item_sheet, container, false);

        ButterKnife.bind(this, mainView);
        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        fragmentActivity = getActivity();
        listener = new AddItemPresenter(this);

        btnSaveItem.setOnClickListener(view -> validator.validate(true));

        return mainView;
    }*/

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.add_item_sheet, null);
        dialog.setContentView(contentView);
        this.dialog = dialog;

        ButterKnife.bind(this, contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        Validator validator = new Validator(this);
        validator.setValidationListener(this);

        listener = new AddItemPresenter(this);
        listener.receiveArgumentData();
        listener.fillFormData();

        btnSaveItem.setOnClickListener(view -> validator.validate(true));
    }

    @Override
    public void receiveArgumentData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            price = bundle.getLong("price");
            itemId = bundle.getLong("id");
            position = bundle.getInt("position");
        }
    }

    @Override
    public void fillFormData() {
        if (name != null) {
            etName.setText(name);
            etPrice.setText(price + "");
            etName.setSelection(name.length());
        }
    }

    @Override
    public void setListener(AddItemContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onValidationSucceeded() {
        if (name != null) {
            listener.updateItem(etName.getText().toString(), Long.parseLong(etPrice.getText().toString()), itemId);
            itemCallBack.addItemToList(false, position);
        } else {
            listener.saveItem(etName.getText().toString(), Long.parseLong(etPrice.getText().toString()));
            itemCallBack.addItemToList(true, 0);
        }
        dialog.dismiss();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError validationError : errors) {
            View view = validationError.getView();
            String message = validationError.getCollatedErrorMessage(fragmentActivity);

            if (view instanceof MaterialEditText) {
                YoYo.with(Techniques.Shake)
                        .duration(300)
                        .playOn(view);
                ((MaterialEditText) view).setError(message);
            } else {
                Toast.makeText(fragmentActivity, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public interface ItemCallBack {
        void addItemToList(boolean add, int position);
    }

    public void onItemCallBack(ItemCallBack itemCallBack) {
        this.itemCallBack = itemCallBack;
    }
}

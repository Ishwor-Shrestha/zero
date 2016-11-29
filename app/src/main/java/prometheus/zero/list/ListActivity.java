package prometheus.zero.list;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.ChangeBounds;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.FrameLayout;
import carbon.widget.LinearLayout;
import fontana.RobotoTextView;
import prometheus.zero.R;
import prometheus.zero.addBudget.AddBudgetFragment;
import prometheus.zero.addItem.AddItemFragment;
import prometheus.zero.base.BaseActivity;
import prometheus.zero.utils.NumberUtil;
import prometheus.zero.utils.ResourceUtil;
import prometheus.zero.utils.StringUtil;
import prometheus.zero.utils.UserInterfaceUtil;

public class ListActivity extends BaseActivity implements ListContract.View, ItemAdapter.ItemControl, AddItemFragment.ItemCallBack, AddBudgetFragment.BudgetCallBack {
    @BindView(R.id.tvTotalBudget)
    RobotoTextView tvTotalBudget;
    @BindView(R.id.tvTotalCost)
    RobotoTextView tvTotalCost;
    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.cdlMain)
    CoordinatorLayout cdlMain;
    @BindView(R.id.vToolbarShadow)
    View vToolbarShadow;
    @BindView(R.id.btnAddItem)
    FloatingActionButton btnAddItem;
    @BindView(R.id.llBudget)
    LinearLayout llBudget;
    @BindView(R.id.tvSpent)
    RobotoTextView tvSpent;
    @BindView(R.id.tvRemaining)
    RobotoTextView tvRemaining;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.tbToolbar)
    Toolbar tbToolbar;
    @BindView(R.id.vToolbarDivider)
    View vToolbarDivider;

    private ListContract.Listener listener;
    private ItemAdapter itemAdapter;
    private LinearLayoutManager layoutManager;
    private boolean listSetUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        onCreateBase();

        listener = new ListPresenter(this);
        listener.setUpFAB();
        listener.setUpList();
        listener.setCostData();
        listener.setListScrollListener();
        btnAddItem.setOnClickListener(view -> listener.openItemForm());
        llBudget.setOnClickListener(view -> listener.openBudgetForm(NumberUtil.removeComma(tvTotalBudget.getText().toString())));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setUpFAB() {
        if (NumberUtil.removeComma(tvTotalBudget.getText().toString()) > 0) {
            /*CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) btnAddItem.getLayoutParams();
            lp.setBehavior(new FABScrollBehavior());
            btnAddItem.setLayoutParams(lp);*/

            new Handler().postDelayed(() -> {
                btnAddItem.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp)
                        .duration(300)
                        .interpolate(new AccelerateDecelerateInterpolator())
                        .playOn(btnAddItem);
            }, 200);
        }
    }

    @Override
    public void setUpList(List<ItemDB> items) {
        itemAdapter = new ItemAdapter(this, items);
        rvList.setAdapter(itemAdapter);
        rvList.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);
        itemAdapter.onItemControls(this);
        listSetUp = true;
    }

    @Override
    public void setListScrollListener() {
        appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset < 0) {
                listener.toggleToolBarShadow(true);
            } else {
                listener.toggleToolBarShadow(false);
            }
        });
    }

    @Override
    public void setBudget(Long budget) {
        updateBudget(budget);
    }

    @Override
    public void setEstimation(Long estimation, boolean overBudget) {
        int normal = ResourceUtil.getColor(this, R.color.green500);
        int over = ResourceUtil.getColor(this, R.color.red500);

        ValueAnimator normalAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), over, normal);
        normalAnimation.setDuration(600);
        normalAnimation.addUpdateListener(animation -> tvTotalCost.setTextColor((int) animation.getAnimatedValue()));

        ValueAnimator overAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), normal, over);
        overAnimation.setDuration(600);
        overAnimation.addUpdateListener(animation -> tvTotalCost.setTextColor((int) animation.getAnimatedValue()));

        if (overBudget) {
            if (tvTotalCost.getCurrentTextColor() != ResourceUtil.getColor(this, R.color.red500)) {
                overAnimation.start();
            }
        } else {
            if (tvTotalCost.getCurrentTextColor() != ResourceUtil.getColor(this, R.color.green500)) {
                normalAnimation.start();
            }
        }
        tvTotalCost.setText(StringUtil.addComma(estimation));
    }

    @Override
    public void setSpent(Long spent) {
        tvSpent.setText(StringUtil.addComma(spent));
    }

    @Override
    public void setRemaining(Long remaining) {
        tvRemaining.setText(StringUtil.addComma(remaining));
    }

    @Override
    public void toggleToolBarShadow(boolean show) {
        if (show) {
            vToolbarShadow.setVisibility(View.VISIBLE);
        } else {
            vToolbarShadow.setVisibility(View.GONE);
        }
    }

    @Override
    public void toggleStrikeThrough(boolean strike, int position) {
        View view = layoutManager.findViewByPosition(position);
        RobotoTextView tvName = (RobotoTextView) view.findViewById(R.id.tvItemName);
        RobotoTextView tvPrice = (RobotoTextView) view.findViewById(R.id.tvItemCost);
        android.widget.LinearLayout llItem = (android.widget.LinearLayout) view.findViewById(R.id.llItem);

        TransitionSet transitionSet = new TransitionSet();

        Transition transition = new ChangeBounds();
        transition.setInterpolator(new AccelerateDecelerateInterpolator());
        transition.setDuration(4000);
        transition.addTarget(tvName);

        transitionSet.addTransition(transition);

        TransitionManager.beginDelayedTransition(llItem, transitionSet);

        if (strike) {
            tvName.setPaintFlags(tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            tvName.setTextColor(ResourceUtil.getColor(this, R.color.disabledLight));
            tvPrice.setTextColor(ResourceUtil.getColor(this, R.color.disabledLight));
        } else {
            tvName.setPaintFlags(tvName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            tvPrice.setPaintFlags(tvPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

            tvName.setTextColor(ResourceUtil.getColor(this, R.color.primaryTextLight));
            tvPrice.setTextColor(ResourceUtil.getColor(this, R.color.secondaryTextLight));
        }

    }

    @Override
    public void toggleEditButton(boolean show, int position) {
        View view = layoutManager.findViewByPosition(position);
        FrameLayout flEdit = (FrameLayout) view.findViewById(R.id.flEdit);
        if (show) {
            ScaleAnimation scaleUp = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleUp.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleUp.setDuration(200);

            flEdit.setVisibility(View.VISIBLE);
            flEdit.startAnimation(scaleUp);

        } else {
            ScaleAnimation scaleDown = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleDown.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleDown.setDuration(200);

            flEdit.startAnimation(scaleDown);
            scaleDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    flEdit.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    @Override
    public void openItemForm() {
        AddItemFragment addItemFragment = new AddItemFragment();
        addItemFragment.show(getSupportFragmentManager(), addItemFragment.getTag());
        addItemFragment.onItemCallBack(this);
    }

    @Override
    public void openBudgetForm(Long budget) {
        AddBudgetFragment addBudgetFragment = new AddBudgetFragment();
        if (budget > 0) {
            Bundle bundle = new Bundle();
            bundle.putString("budget", budget.toString());
            addBudgetFragment.setArguments(bundle);
        }
        addBudgetFragment.show(getSupportFragmentManager(), addBudgetFragment.getTag());
        addBudgetFragment.onBudgetCallBack(this);
    }

    @Override
    public void openRemoveConfirmation(String message, Long itemId, int position) {
        UserInterfaceUtil.showSnackBar(this, cdlMain, message, true, "REMOVE", Snackbar.LENGTH_INDEFINITE, () -> {
            listener.removeItem(itemId);
            itemAdapter.remove(position);
            listener.setEstimation();
        });
    }

    @Override
    public void setListener(ListContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void editItem(int position) {
        View view = layoutManager.findViewByPosition(position);
        RobotoTextView tvItemId = (RobotoTextView) view.findViewById(R.id.tvItemId);
        RobotoTextView tvName = (RobotoTextView) view.findViewById(R.id.tvItemName);
        RobotoTextView tvPrice = (RobotoTextView) view.findViewById(R.id.tvItemCost);

        AddItemFragment addItemFragment = new AddItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", tvName.getText().toString());
        bundle.putLong("price", NumberUtil.removeComma(tvPrice.getText().toString()));
        bundle.putLong("id", NumberUtil.removeComma(tvItemId.getText().toString()));
        bundle.putInt("position", position);

        addItemFragment.setArguments(bundle);

        addItemFragment.show(getSupportFragmentManager(), addItemFragment.getTag());
        addItemFragment.onItemCallBack(this);
    }

    @Override
    public void removeItem(int position) {
        View view = layoutManager.findViewByPosition(position);
        RobotoTextView tvItemId = (RobotoTextView) view.findViewById(R.id.tvItemId);

        listener.openRemoveConfirmation(Long.parseLong(tvItemId.getText().toString()), position);
    }

    @Override
    public void checkItem(int position, boolean checked) {
        View view = layoutManager.findViewByPosition(position);
        RobotoTextView tvItemId = (RobotoTextView) view.findViewById(R.id.tvItemId);

        listener.toggleItemStatus(Long.parseLong(tvItemId.getText().toString()), checked);
        listener.toggleStrikeThrough(checked, position);
        listener.toggleEditButton(!checked, position);
        listener.setEstimation();
    }

    @Override
    public void addItemToList(boolean add, int position) {
        if (!listSetUp) {
            listener.setUpList();
        } else {
            itemAdapter.add(listener.getAllItems());
            if (add) {
                rvList.smoothScrollToPosition(itemAdapter.getItemCount() - 1);
            } else {
                rvList.smoothScrollToPosition(position);
            }
        }
        listener.setEstimation();
    }

    @Override
    public void updateBudget(Long budget) {
        tvTotalBudget.setText(StringUtil.addComma(budget));
        if (btnAddItem.getVisibility() == View.GONE) {
            listener.setUpFAB();
        }
    }
}

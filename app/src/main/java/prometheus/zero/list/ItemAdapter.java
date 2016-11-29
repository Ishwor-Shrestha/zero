package prometheus.zero.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.FrameLayout;
import carbon.widget.RelativeLayout;
import fontana.RobotoTextView;
import prometheus.zero.R;
import prometheus.zero.utils.ResourceUtil;
import prometheus.zero.utils.StringUtil;

class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context ct;
    private List<ItemDB> items;
    private int lastPosition = -1;

    private static final int MAIN = 0;
    private static final int FOOTER = 1;

    private ItemControl itemControl;

    ItemAdapter(Context context, List<ItemDB> items) {
        this.ct = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == MAIN) {
            view = inflater.inflate(R.layout.item_row, parent, false);
        } else {
            view = inflater.inflate(R.layout.empty, parent, false);
        }

        return new MyViewHolder(view, viewType);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (holder.holderId == MAIN) {
            holder.tvItemId.setText(items.get(position).getId().toString());
            holder.tvItemName.setText(items.get(position).itemName);
            holder.tvItemCost.setText(StringUtil.addComma(items.get(position).itemCost));
            holder.cbItemStatus.setChecked(items.get(position).itemStatus);
            if (items.get(position).itemStatus) {
                holder.tvItemName.setPaintFlags(holder.tvItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvItemCost.setPaintFlags(holder.tvItemCost.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                holder.tvItemName.setTextColor(ResourceUtil.getColor(ct, R.color.disabledLight));
                holder.tvItemCost.setTextColor(ResourceUtil.getColor(ct, R.color.disabledLight));

                holder.flEdit.setVisibility(View.GONE);
            } else {
                holder.tvItemName.setPaintFlags(holder.tvItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.tvItemCost.setPaintFlags(holder.tvItemCost.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                holder.tvItemName.setTextColor(ResourceUtil.getColor(ct, R.color.primaryTextLight));
                holder.tvItemCost.setTextColor(ResourceUtil.getColor(ct, R.color.secondaryTextLight));

                holder.flEdit.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return FOOTER;
        } else {
            return MAIN;
        }
    }

    private boolean isPositionFooter(int position) {
        return position == items.size();
    }

    public void add(List<ItemDB> items) {
        this.items = items;
        notifyDataSetChanged();
//        notifyItemInserted(items.size() - 1);
    }

    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tvItemId)
        RobotoTextView tvItemId;
        @BindView(R.id.tvItemName)
        RobotoTextView tvItemName;
        @BindView(R.id.tvItemCost)
        RobotoTextView tvItemCost;
        @BindView(R.id.llItem)
        LinearLayout llItem;
        @BindView(R.id.flEdit)
        FrameLayout flEdit;
        @BindView(R.id.flClose)
        FrameLayout flClose;
        @BindView(R.id.rlContainer)
        RelativeLayout rlContainer;
        @BindView(R.id.cbItemStatus)
        CheckBox cbItemStatus;

        int holderId;

        MyViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == MAIN) {
                ButterKnife.bind(this, itemView);
                holderId = MAIN;
                flEdit.setOnClickListener(this);
                flClose.setOnClickListener(this);
                cbItemStatus.setOnClickListener(this);
            } else {
                holderId = FOOTER;
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.flEdit:
                    itemControl.editItem(getLayoutPosition());
                    break;
                case R.id.flClose:
                    itemControl.removeItem(getLayoutPosition());
                    break;
                case R.id.cbItemStatus:
                    itemControl.checkItem(getLayoutPosition(), cbItemStatus.isChecked());
                    break;
            }
        }
    }

    void onItemControls(ItemControl itemControl) {
        this.itemControl = itemControl;
    }

    protected void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(ct, android.R.anim.slide_in_left);
            viewToAnimate.setAnimation(animation);
            lastPosition = position;
        }
    }

    interface ItemControl {
        void editItem(int position);

        void removeItem(int position);

        void checkItem(int position, boolean checked);
    }
}

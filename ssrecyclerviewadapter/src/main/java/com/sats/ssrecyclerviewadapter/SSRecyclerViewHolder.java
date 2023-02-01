package com.sats.ssrecyclerviewadapter;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class SSRecyclerViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    protected T itemRowBinding;
    private int recyclerDataId;

    public SSRecyclerViewHolder(T itemRowBinding, int recyclerDataId) {
        super(itemRowBinding.getRoot());
        this.itemRowBinding = itemRowBinding;
        this.recyclerDataId = recyclerDataId;
    }

    public void bind(Object obj) {
        itemRowBinding.setVariable(recyclerDataId, obj);
        itemRowBinding.executePendingBindings();
    }
}

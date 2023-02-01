package com.sats.ssrecyclerviewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SSRecyclerViewAdapter<T extends ViewDataBinding, D> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * The list containing items to be populated in the adapter.
     */
    private List<D> recyclerDataList;

    /**
     * The layout id of the row.
     */
    private int recyclerViewItemLayoutId;

    /**
     * The binding variable of type OnRecyclerViewItemClickListener.
     */
    private int recyclerItemClickListenerId;

    /**
     * The binding variable of type D.
     */
    private int recyclerDataId;

    /**
     * The listener to receive callback when the row is clicked or long pressed.
     */
    private OnRecyclerViewItemClickListener recyclerItemClickListener;

    /**
     * The listener to receive callback at the end of {@link #onCreateViewHolder(ViewGroup, int)}
     * and {@link #onBindViewHolder(RecyclerView.ViewHolder, int)} methods.
     */
    private OnRecyclerViewAdapterSetup onRecyclerItemSetup;

    /**
     * Sets the list used to populate the adapter.
     *
     * @param recyclerDataList
     * @return SSRecyclerViewAdapter
     */
    public SSRecyclerViewAdapter setRecyclerDataList(List<D> recyclerDataList) {
        this.recyclerDataList = recyclerDataList;
        return this;
    }

    /**
     * Sets the layout id of the item in the RecyclerView.
     * The layout id must be from R.layout class.
     *
     * @param recyclerViewItemLayoutId - The id of the layout for each row in the RecyclerView.
     * @return SSRecyclerViewAdapter
     */
    public SSRecyclerViewAdapter setRecyclerViewItemLayoutId(int recyclerViewItemLayoutId) {
        this.recyclerViewItemLayoutId = recyclerViewItemLayoutId;
        return this;
    }

    /**
     * Sets the id of click listener variable defined for data binding.
     *
     * @param recyclerItemClickListenerId - The listener id from the generated BR file.
     * @return SSRecyclerViewAdapter
     */
    public SSRecyclerViewAdapter setRecyclerItemClickListenerId(int recyclerItemClickListenerId) {
        this.recyclerItemClickListenerId = recyclerItemClickListenerId;
        return this;
    }

    /**
     * Sets the id of data model variable defined for data binding.
     *
     * @param recyclerDataId - The model id from generated BR file.
     * @return SSRecyclerViewAdapter
     */
    public SSRecyclerViewAdapter setRecyclerDataId(int recyclerDataId) {
        this.recyclerDataId = recyclerDataId;
        return this;
    }

    /**
     * Register listener to be notified whenever a row in a RecyclerView is clicked.
     *
     * @param listener
     * @return SSRecyclerViewAdapter
     */
    public SSRecyclerViewAdapter setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.recyclerItemClickListener = listener;
        return this;
    }

    /**
     * Register listener to receive callback at the end of {@link #onCreateViewHolder(ViewGroup, int)}
     * and {@link #onBindViewHolder(RecyclerView.ViewHolder, int)} methods.
     *
     * @param onRecyclerItemSetup
     * @return SSRecyclerViewAdapter
     */
    public SSRecyclerViewAdapter setOnRecyclerViewItemSetup(OnRecyclerViewAdapterSetup onRecyclerItemSetup) {
        this.onRecyclerItemSetup = onRecyclerItemSetup;
        return this;
    }

    @Override
    public int getItemCount() {
        return recyclerDataList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        T binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                recyclerViewItemLayoutId, viewGroup, false);
        SSRecyclerViewHolder viewHolder = new SSRecyclerViewHolder(binding, recyclerDataId);
        if (onRecyclerItemSetup != null) onRecyclerItemSetup.onCreateViewHolder(viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        D recyclerData = recyclerDataList.get(viewHolder.getAdapterPosition());
        final SSRecyclerViewHolder<T> recyclerViewHolder = (SSRecyclerViewHolder) viewHolder;

        recyclerViewHolder.bind(recyclerData);

        // Bind the variable declared in the layout file to the actual implementation of the click listener.
        ((ViewDataBinding) recyclerViewHolder.itemRowBinding).setVariable(recyclerItemClickListenerId, new OnRecyclerViewItemClickListener<D>() {
            @Override
            public void onRecyclerItemClick(View recyclerItemView, D recyclerData) {
                if (recyclerItemClickListener != null) {
                    recyclerItemClickListener.onRecyclerItemClick(recyclerItemView, recyclerData);
                }
            }

            @Override
            public boolean onRecyclerItemLongClick(View recyclerItemView, D recyclerData) {
                if (recyclerItemClickListener != null) {
                    return recyclerItemClickListener.onRecyclerItemLongClick(recyclerItemView, recyclerData);
                }
                return false;
            }
        });

        if (onRecyclerItemSetup != null)
            onRecyclerItemSetup.onBindViewHolder(recyclerViewHolder.itemRowBinding.getRoot(), recyclerData, position);
    }
}

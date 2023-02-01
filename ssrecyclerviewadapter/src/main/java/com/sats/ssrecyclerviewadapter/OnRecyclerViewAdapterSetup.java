package com.sats.ssrecyclerviewadapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Interface definition for the callbacks invoked at the end of {@link androidx.recyclerview.widget.RecyclerView.Adapter}'s methods.
 */
public interface OnRecyclerViewAdapterSetup<D> {
    /**
     * Is invoked at the end of {@link androidx.recyclerview.widget.RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}'s implementation.
     *
     * @param viewType - The view type of the new View.
     */
    void onCreateViewHolder(int viewType);

    /**
     * Is invoked at the end of {@link androidx.recyclerview.widget.RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}'s implementation.
     *
     * @param rootView - The root view of the cell.
     * @param model    - The data object bound to the cell.
     * @param position - The position of the item within the adapter's data set.
     */
    void onBindViewHolder(View rootView, D model, int position);
}

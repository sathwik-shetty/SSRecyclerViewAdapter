package com.sats.ssrecyclerviewadapter;

import android.view.View;

/**
 * Interface definition for the callbacks invoked when a row is clicked.
 */
public interface OnRecyclerViewItemClickListener<D> {
    /**
     * Invoked when a single row is clicked.
     *
     * @param recyclerItemView - The view passed from the R.layout file. Ideally this should be the root view.
     * @param model            - The data object bound to the cell.
     */
    void onRecyclerItemClick(View recyclerItemView, D model);

    /**
     * Invoked when a row is long pressed.
     *
     * @param recyclerItemView - The view passed from the R.layout file. Ideally this should be the root view.
     * @param model            - The data object bound to the cell.
     * @return Returns the result of the actual implementation of this method, if this method is implemented.
     * Returns false if the method is not implemented.
     */
    boolean onRecyclerItemLongClick(View recyclerItemView, D model);
}


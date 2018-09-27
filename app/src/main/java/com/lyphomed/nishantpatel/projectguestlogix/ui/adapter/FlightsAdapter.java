package com.lyphomed.nishantpatel.projectguestlogix.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyphomed.nishantpatel.projectguestlogix.R;
import com.lyphomed.nishantpatel.projectguestlogix.config.PublicKeys;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.databinding.DirectFlightListItemBinding;
import com.lyphomed.nishantpatel.projectguestlogix.databinding.ViaPathListItemBinding;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.FullViaPath;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter responsible for displaying list of flights
 */
public class FlightsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FullViaPath> mFullPathList;
    private List<Routes> mDirectRoutesList;
    private int mDirectPath;
    private OnViaPathItemClick mOnViaPathItemClick;
    private OnDirectPathItemClick mOnDirectPathItemClick;

    public interface OnViaPathItemClick {
        void onItemClick(FullViaPath fullViaPath);
    }

    public interface OnDirectPathItemClick {
        void onItemClick(Routes routes);
    }

    public FlightsAdapter(OnDirectPathItemClick onDirectPathItemClick, OnViaPathItemClick onViaPathItemClick) {
        mFullPathList = new ArrayList<>();
        mDirectRoutesList = new ArrayList<>();
        mOnDirectPathItemClick = onDirectPathItemClick;
        mOnViaPathItemClick = onViaPathItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case PublicKeys.DIRECT_PATH_LIST_ITEM:
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.direct_flight_list_item, viewGroup, false);
                return new DirectPathViewHolder(DirectFlightListItemBinding.bind(view));
            case PublicKeys.VIA_PATH_LIST_ITEM:
                View view2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.via_path_list_item, viewGroup, false);
                return new ViaPathViewHolder(ViaPathListItemBinding.bind(view2));
            default:
                return super.createViewHolder(viewGroup, viewType);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                DirectPathViewHolder directPathViewHolder = (DirectPathViewHolder) viewHolder;
                directPathViewHolder.bind(mDirectRoutesList.get(i));
                break;
            case 1:
                ViaPathViewHolder viaPathViewHolder = (ViaPathViewHolder) viewHolder;
                viaPathViewHolder.bind(mFullPathList.get(i));
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mDirectPath == PublicKeys.DIRECT_PATH_LIST_ITEM) return mDirectRoutesList.size();
        else return mFullPathList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDirectPath;
    }

    /**
     * Call this method to update the recycler view data with via paths
     *
     * @param fullPathList New batch of data
     * @param identifier   1 for the via path
     */
    public void updateFullPath(List<FullViaPath> fullPathList, int identifier) {
        mDirectPath = identifier;
        mFullPathList.addAll(fullPathList);
        notifyDataSetChanged();
    }

    /**
     * Call this method to update recycler view data with direct paths
     *
     * @param routes     new batch pf data
     * @param identifier 0 for the direct path
     */
    public void updateDirectPath(List<Routes> routes, int identifier) {
        mDirectPath = identifier;
        mDirectRoutesList.addAll(routes);
        notifyDataSetChanged();
    }

    /**
     * View holder for displaying Via paths
     */
    class ViaPathViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ViaPathListItemBinding mBinding;

        ViaPathViewHolder(@NonNull ViaPathListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        void bind(FullViaPath fullViaPath) {
            mBinding.setViaPath(fullViaPath);
        }

        @Override
        public void onClick(View v) {
            mOnViaPathItemClick.onItemClick(mFullPathList.get(getAdapterPosition()));
        }
    }

    /**
     * View Holder for displaying direct flights
     */
    class DirectPathViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private DirectFlightListItemBinding mBinding;

        DirectPathViewHolder(@NonNull DirectFlightListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        void bind(Routes routes) {
            mBinding.setDirectRoute(routes);
        }

        @Override
        public void onClick(View v) {
            mOnDirectPathItemClick.onItemClick(mDirectRoutesList.get(getAdapterPosition()));
        }
    }
}

package com.lyphomed.nishantpatel.projectguestlogix.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyphomed.nishantpatel.projectguestlogix.R;
import com.lyphomed.nishantpatel.projectguestlogix.databinding.ViaPointListItemBinding;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.FullViaPath;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter responsible for displaying list of flights
 */
public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.FlightsViewHolder> {
    private List<FullViaPath> mFullPathList;

    public FlightsAdapter() {
        mFullPathList = new ArrayList<>();
    }

    @NonNull
    @Override
    public FlightsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.via_point_list_item, viewGroup, false);
        return new FlightsViewHolder(ViaPointListItemBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull FlightsViewHolder flightsViewHolder, int i) {
        flightsViewHolder.bind(mFullPathList.get(i));
    }

    @Override
    public int getItemCount() {
        return mFullPathList.size();
    }

    /**
     * Call this method to update the recycler view  data
     *
     * @param fullPathList New batch of data
     */
    public void updateData(List<FullViaPath> fullPathList) {
        mFullPathList.addAll(fullPathList);
        notifyDataSetChanged();
    }

    class FlightsViewHolder extends RecyclerView.ViewHolder {

        private ViaPointListItemBinding mBinding;

        FlightsViewHolder(@NonNull ViaPointListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(FullViaPath fullViaPath) {
            mBinding.viaListItemOrigin.setText(fullViaPath.getOrigin());
            mBinding.viaListItemDestination.setText(fullViaPath.getDestination());
            mBinding.viaListItemVia.setText(fullViaPath.getVia());
            mBinding.viaListItemVia2.setText(fullViaPath.getVia());
            mBinding.listItemToViaCode.setText(fullViaPath.getOriginToViaFlight());
            mBinding.listItemFromViaCode.setText(fullViaPath.getViaToDestination());
        }
    }
}

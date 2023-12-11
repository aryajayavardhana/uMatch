package com.uMatch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContainerOnboarding extends RecyclerView.Adapter<ContainerOnboarding.ListOnboarding> {
    private List<ItemOnboarding> itemOnboardings;

    public ContainerOnboarding(List<ItemOnboarding> itemOnboardings) {
        this.itemOnboardings = itemOnboardings;
    }

    @NonNull
    @Override
    public ListOnboarding onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListOnboarding(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.activity_container_onboarding, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ListOnboarding holder, int position) {
        holder.setData(itemOnboardings.get(position));

    }

    @Override
    public int getItemCount() {
        return itemOnboardings.size();
    }

    class ListOnboarding extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtDesc;
        public ImageView onboardingImage;

        public ListOnboarding(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            onboardingImage = itemView.findViewById(R.id.onboardingImage);
        }

        public void setData(ItemOnboarding itemOnboarding){
            txtTitle.setText(itemOnboarding.getTitle());
            txtDesc.setText(itemOnboarding.getDesc());
            onboardingImage.setImageResource(itemOnboarding.getImage());
        }
    }
}
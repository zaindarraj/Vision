package com.example.vision.alerts;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vision.R;
import com.example.vision.retrofit.RetrofitInstance;

import java.util.ArrayList;

public class AlertsFragmanet extends Fragment {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    TextView noAlerts;

    Observer<ArrayList<Alert>> arrayListObserver = new Observer<ArrayList<Alert>>() {
        @Override
        public void onChanged(ArrayList<Alert> alerts) {
            if(alerts.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                noAlerts.setVisibility(View.VISIBLE);
            }else{
                recyclerView.setVisibility(View.VISIBLE);
                noAlerts.setVisibility(View.GONE);
            }
            customAdapter.localDataSet = alerts;

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(customAdapter);
        }
    };



    private AlertsFragmanetViewModel mViewModel;

    public static AlertsFragmanet newInstance() {
        return new AlertsFragmanet();
    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<Alert> localDataSet;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder)
         */
        public  class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private  final ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View

                textView = (TextView) view.findViewById(R.id.alert);
                imageView = (ImageView) view.findViewById(R.id.alertImage);
            }

            public TextView getTextView() {
                return textView;
            }
        }

        /**
         * Initialize the dataset of the Adapter
         *
         * @param dataSet String[] containing the data to populate views to be used
         * by RecyclerView
         */
        public CustomAdapter(ArrayList<Alert> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.alert, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.getTextView().setText(localDataSet.get(position).alert);
            Glide.with(getParentFragment())
                    .load(RetrofitInstance.url+"/api/images/"+localDataSet.get(position).imageUrl)
                    .into(viewHolder.imageView);
            Log.println(Log.ASSERT,"dfsfsdfsdfx",RetrofitInstance.url+"/"+localDataSet.get(position).imageUrl);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alerts_fragmanet, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AlertsFragmanetViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.alerts);
        noAlerts = view.findViewById(R.id.no_alerts);
        customAdapter = new CustomAdapter(mViewModel.alertsMutableLiveData.getValue());
        mViewModel.alertsMutableLiveData.observe(getViewLifecycleOwner(), arrayListObserver);
        mViewModel.getAlerts();

    }
}
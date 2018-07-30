package com.software.ragp.proyectopsp3.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.ragp.proyectopsp3.R;
import com.software.ragp.proyectopsp3.models.AdapterTimes;
import com.software.ragp.proyectopsp3.models.CConsultTimes;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPhaseRemoved extends Fragment {


    public FragmentPhaseRemoved() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_phase_removed, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerDR);
        List<CConsultTimes> timesList = new ArrayList<>();
        AdapterTimes adapterTimes = new AdapterTimes(timesList);
        recyclerView.setAdapter(adapterTimes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);

        return view;
    }

}

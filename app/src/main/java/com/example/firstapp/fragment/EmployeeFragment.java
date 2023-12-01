package com.example.firstapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firstapp.R;

public class EmployeeFragment extends Fragment {
private RecyclerView rvEmployees;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_employee, container, false);

        initView(fragmentView);
        getData();
        return fragmentView;
    }

    private void initView(View view) {
        rvEmployees = view.findViewById(R.id.rvEmployees);
        rvEmployees.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }

    private void getData() {

    }
}
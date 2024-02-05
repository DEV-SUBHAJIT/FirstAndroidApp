package com.example.firstapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.firstapp.activity.HomeActivity;
import com.example.firstapp.databinding.FragmentProductListBinding;

public class ProductListFragment extends Fragment {
    public ProductListFragment() {
        //Empty Constructor needed
        // Do not Delete
    }

    private FragmentProductListBinding binding;
    private int subCategoryId;
    private String subCategoryName;

    public ProductListFragment(int id, String subCategoryName) {
        subCategoryId = id;
        this.subCategoryName = subCategoryName;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductListBinding.inflate(inflater, container, false);

        init();
        //Others function here

        return binding.getRoot();
    }

    private void init() {
        //Change toolbar title text
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(subCategoryName);
    }


}
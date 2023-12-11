package com.example.firstapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firstapp.R;
import com.example.firstapp.databinding.FragmentShareBinding;

public class ShareFragment extends Fragment {
    private FragmentShareBinding shareBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        shareBinding = FragmentShareBinding.inflate(inflater,container, false);


        return shareBinding.getRoot();
    }
}
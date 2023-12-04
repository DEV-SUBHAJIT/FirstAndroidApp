package com.example.firstapp.fragment;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.activity.HomeActivity;
import com.example.firstapp.adapter.CategoryAdapter;
import com.example.firstapp.api_response.CategoryResponse;
import com.example.firstapp.databinding.FragmentHomeBinding;
import com.example.firstapp.event.OnItemClick;
import com.example.firstapp.model.Category;
import com.example.firstapp.retrofit.DataService;
import com.example.firstapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    Context context;
    FragmentHomeBinding binding;

    private OnItemClick itemClick;
    private   List<Category> categoryList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        //Change toolbar title text
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Home");

        getCategories();
        clickEvent();
        return binding.getRoot();
    }

    private void getCategories() {
        DataService service = RetrofitClient.getDataService();
        Call<CategoryResponse> call = service.getCategories();
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response != null && response.isSuccessful()) {
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse.isStatus()) {
                       categoryList = categoryResponse.getCategoryList();

                        binding.rvCategory.setAdapter(new CategoryAdapter(categoryList, itemClick));
                    } else
                        Toast.makeText(requireActivity(), "No category found", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(requireActivity(), "Response failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(requireActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickEvent() {
        itemClick = new OnItemClick() {
            @Override
            public void onItemClick(int position) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, new SubCategoryFragment(categoryList.get(position).getId()))
                        .commit();
            }
        };
    }
}
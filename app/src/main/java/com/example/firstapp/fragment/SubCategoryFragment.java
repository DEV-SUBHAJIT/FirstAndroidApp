package com.example.firstapp.fragment;

import static com.example.firstapp.utility.Utility.changeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.activity.HomeActivity;
import com.example.firstapp.adapter.SubCategoryAdapter;
import com.example.firstapp.api_response.SubCategoryResponse;
import com.example.firstapp.event.OnItemClick;
import com.example.firstapp.model.SubCategory;
import com.example.firstapp.retrofit.DataService;
import com.example.firstapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryFragment extends Fragment {
    private RecyclerView rvSubCategory;
    private ProgressBar progressBar;

    private int categoryId;
    private String categoryName;

    public SubCategoryFragment(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        initView(view);
        getSubcategory();
        return view;
    }

    private void getSubcategory() {
        DataService service = RetrofitClient.getDataService();
        Call<SubCategoryResponse> call = service.getSubCategory(categoryId);
        call.enqueue(new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    SubCategoryResponse subCategoryResponse = response.body();

                    OnItemClick itemClick = position -> {
                        changeFragment(getActivity().getSupportFragmentManager(), R.id.frameLayout, new ProductListFragment(subCategoryResponse.getSubCategoryList().get(position).getId(),
                                (subCategoryResponse.getSubCategoryList().get(position).getSubCategoryName())));
                    };

                    if (subCategoryResponse.isStatus()) {
                        List<SubCategory> subCategoryList = subCategoryResponse.getSubCategoryList();
                        if (subCategoryList.size() > 0) {
                            rvSubCategory.setHasFixedSize(true);
                            rvSubCategory.setAdapter(new SubCategoryAdapter(subCategoryList, itemClick));
                        } else
                            Toast.makeText(requireActivity(), "No sub-category found", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(requireActivity(), "No sub-Category available", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(requireActivity(), "Response failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SubCategoryResponse> call, Throwable t) {
                Toast.makeText(requireActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initView(View view) {
        rvSubCategory = view.findViewById(R.id.rvSubCategory);
        progressBar = view.findViewById(R.id.progressBar);
        //Change toolbar title text
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(categoryName);

        rvSubCategory.setHasFixedSize(true);
        rvSubCategory.setLayoutManager(new GridLayoutManager(getActivity(), 3));

//        rvSubCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }


}
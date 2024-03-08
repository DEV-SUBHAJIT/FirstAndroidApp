package com.example.firstapp.fragment;

import static com.example.firstapp.utility.Utility.changeFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.R;
import com.example.firstapp.activity.HomeActivity;
import com.example.firstapp.activity.NewsActivity;
import com.example.firstapp.adapter.CategoryAdapter;
import com.example.firstapp.adapter.NewsAdapter;
import com.example.firstapp.adapter.ProductsAdepter;
import com.example.firstapp.api_response.CategoryResponse;
import com.example.firstapp.api_response.NewsResponse;
import com.example.firstapp.api_response.ProductsResponse;
import com.example.firstapp.databinding.FragmentHomeBinding;
import com.example.firstapp.databinding.FragmentProductListBinding;
import com.example.firstapp.event.OnItemClick;
import com.example.firstapp.model.News;
import com.example.firstapp.model.Products;
import com.example.firstapp.model.SubCategory;
import com.example.firstapp.retrofit.DataService;
import com.example.firstapp.retrofit.RetrofitClient;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends Fragment {
    public ProductListFragment() {
        //Empty Constructor needed
        // Do not Delete
    }

    private FragmentProductListBinding binding;
    Context context;


    private int subCategoryId;

    private List<Products> productsList = new ArrayList<>();

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
getProducts();


    }

   private void getProducts() {
       DataService service = RetrofitClient.getDataService();
       Call<ProductsResponse> call = service.getProducts(String.valueOf(subCategoryId));

       call.enqueue(new Callback<ProductsResponse>() {

           @Override
           public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {

               ProductsResponse productsResponse = response.body();

               OnItemClick itemClick = position -> {
                   changeFragment(getActivity().getSupportFragmentManager(), R.id.frameLayout, new ProductsDetails(productsResponse.getProductsList().get(position)));

               };

               if (productsResponse.isStatus()) {
                   productsList = productsResponse.getProductsList();

                   binding.rvProduct.setHasFixedSize(true);
                   binding.rvProduct.setLayoutManager(new GridLayoutManager(context, 2));
                   binding.rvProduct.setAdapter(new ProductsAdepter(productsList, itemClick));
               }
           }

           @Override
           public void onFailure(Call<ProductsResponse> call, Throwable t) {
               Toast.makeText(requireActivity(), "No Products Avbliable", Toast.LENGTH_LONG).show();

           }
       });

   }
}
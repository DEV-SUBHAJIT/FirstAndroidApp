package com.example.firstapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.firstapp.R;
import com.example.firstapp.activity.HomeActivity;
import com.example.firstapp.adapter.ColorAdapter;
import com.example.firstapp.adapter.SizeAdepter;
import com.example.firstapp.api_response.CartResponse;
import com.example.firstapp.api_response.ColorResponse;
import com.example.firstapp.api_response.ProductDetailsResponse;
import com.example.firstapp.api_response.SizeResponse;
import com.example.firstapp.databinding.FragmentProductsDetailsBinding;
import com.example.firstapp.event.OnItemClick;
import com.example.firstapp.model.Color;
import com.example.firstapp.model.Products;
import com.example.firstapp.products.ProductImage;
import com.example.firstapp.products.Rating;
import com.example.firstapp.products.Size;
import com.example.firstapp.products.SizeDetails;
import com.example.firstapp.retrofit.DataService;
import com.example.firstapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductsDetails extends Fragment {


    public ProductsDetails() {

    }

    private FragmentProductsDetailsBinding binding;
    private Context context;
    private Products product;

    private List<SlideModel> slideModelList = new ArrayList<>();

    private OnItemClick colorItemClick, sizeItemClick;

    private List<Color> colorList;

    private List<SizeDetails> sizeList;
    private int userId;

    private String productListName;

    public ProductsDetails(Products product) {

        this.product = product;

    }

    private ProgressDialog progressDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductsDetailsBinding.inflate(inflater, container, false);

        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(productListName);

        init();
        getProductDetail();
        getSizeDetails();
        getColorDetails();
        return binding.getRoot();
    }


    private  void init(){
        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("LoginDetails", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", 0);

        binding.tvCart.setOnClickListener(v->{

            DataService service = RetrofitClient.getDataService();
            Call<CartResponse> call = service.addToCart(userId,product.getId(),1, product.getColorDetails().getId(), product.getColorDetails().getSizeDetails().getId());
            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(@NonNull Call<CartResponse> call, @NonNull Response<CartResponse> response) {
                    if (response.isSuccessful() && response.body()!= null){
                        CartResponse cartResponse = response.body();
                       Toast.makeText(requireActivity(), ""+cartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(requireActivity(), "Response failed!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {

                }
            });
        });

        colorItemClick = new OnItemClick() {
            @Override
            public void onItemClick(int position) {
                Color color = colorList.get(position);

//                Show progress dialog
                progressDialog.show();

                DataService service = RetrofitClient.getDataService();
                Call<ProductDetailsResponse> call = service.getProductsByColorId(product.getId(),color.getId());

                call.enqueue(new Callback<ProductDetailsResponse>() {
                    @Override
                    public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {
                        //Dismiss progress dialog
                        progressDialog.dismiss();
                      
                        if (response.isSuccessful() && response.body()!=null){
                            ProductDetailsResponse productDetailsResponse = response.body();
                            if (productDetailsResponse.isStatus()){
                                product = productDetailsResponse.getProducts();

                                binding.tvPn.setText(product.getProductName());

                                if (product.getColorDetails().getRatings() != null) {
                                    double totalRating = 0;

                                    for (Rating rating : product.getColorDetails().getRatings()) {
                                        totalRating += rating.getRating();
                                    }

                                    double avgRating = totalRating / product.getColorDetails().getRatings().size();

                                    binding.rbBar.setRating((float) avgRating);
                                }

                                slideModelList.clear();

                                //Image list
                                for (ProductImage productImage :
                                        product.getColorDetails().getProductImageList()) {
                                    slideModelList.add(new SlideModel("https://" + productImage.getImageUrl(), ScaleTypes.CENTER_INSIDE));

                                }

                                //Set image list into image slider
                                binding.imageSlider.setImageList(slideModelList);
                                binding.tvPrice.setText(context.getString(R.string.product_price, String.valueOf(product.getColorDetails().getSizeDetails().getProductSellPrice())));
                                binding.tvMrp.setText(context.getString(R.string.product_price, String.valueOf(product.getColorDetails().getSizeDetails().getProductMRP())));
                                binding.tvMrp.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                            }
                        }
                        else
                            Toast.makeText(context,"Color Not Found", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ProductDetailsResponse> call, Throwable t) {
                        //Dismiss progress dialog
                        progressDialog.dismiss();
                        Toast.makeText(requireActivity(), "Server error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        sizeItemClick = new OnItemClick() {
            @Override
            public void onItemClick(int position) {
                //Show progress dialog
                SizeDetails size = sizeList.get(position);
                binding.tvPrice.setText(context.getString(R.string.product_price, String.valueOf(size.getProductSellPrice())));
                binding.tvMrp.setText(context.getString(R.string.product_price, String.valueOf(size.getProductMRP())));
                binding.tvMrp.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

            }
        };
    }
    private void getSizeDetails() {

        DataService service = RetrofitClient.getDataService();
        Call<SizeResponse> call = service.getSize(product.getId(), product.getColorDetails().getId());
        call.enqueue(new Callback<SizeResponse>() {
            @Override
            public void onResponse(Call<SizeResponse> call, Response<SizeResponse> response) {
                SizeResponse sizeResponse = response.body();
                if (sizeResponse.isStatus()) {
                    sizeList = sizeResponse.getSizeList();
                    binding.rvSize.setHasFixedSize(true);
                    binding.rvSize.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    binding.rvSize.setAdapter(new SizeAdepter(sizeList,sizeItemClick,product.getColorDetails().getSizeDetails().getId()));
                }
            }

            @Override
            public void onFailure(Call<SizeResponse> call, Throwable t) {

                Toast.makeText(context, "Size Not Found", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void getProductDetail() {

        DataService service = RetrofitClient.getDataService();
        Call<ProductDetailsResponse> call = service.getProductsDetail(product.getId());
        call.enqueue(new Callback<ProductDetailsResponse>() {
            @Override
            public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ProductDetailsResponse productDetailsResponse = response.body();

                    if (productDetailsResponse.isStatus()) {
                        product = productDetailsResponse.getProductsList().get(0);

                        binding.tvPn.setText(product.getProductName());
//                        Picasso.get().load("https://"+product.getColorDetails().getProductImageList().get(0).getImageUrl()).into(binding.ivImage);

                        if (product.getColorDetails().getRatings() != null) {
                            double totalRating = 0;

                            for (Rating rating : product.getColorDetails().getRatings()) {
                                totalRating += rating.getRating();
                            }

                            double avgRating = totalRating / product.getColorDetails().getRatings().size();

                            binding.rbBar.setRating((float) avgRating);
                        }

                        slideModelList.clear();
                        //Image list
                        for (ProductImage productImage :
                                product.getColorDetails().getProductImageList()) {
                            slideModelList.add(new SlideModel("https://" + productImage.getImageUrl(), ScaleTypes.CENTER_INSIDE));

                        }

                        //Set image list into image slider
                        binding.imageSlider.setImageList(slideModelList);
                        binding.tvPrice.setText(context.getString(R.string.product_price, String.valueOf(product.getColorDetails().getSizeDetails().getProductSellPrice())));
                        binding.tvMrp.setText(context.getString(R.string.product_price, String.valueOf(product.getColorDetails().getSizeDetails().getProductMRP())));
                        binding.tvMrp.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);


                    } else
                        Toast.makeText(context, productDetailsResponse.getMessage(), Toast.LENGTH_LONG).show();


                } else Toast.makeText(context, "Response failed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ProductDetailsResponse> call, Throwable t) {
                Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void getColorDetails() {
        DataService service = RetrofitClient.getDataService();
        Call<ColorResponse> call = service.getColor(product.getId());
        call.enqueue(new Callback<ColorResponse>() {
            @Override
            public void onResponse(Call<ColorResponse> call, Response<ColorResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ColorResponse colorResponse = response.body();
                    if (colorResponse.isStatus()) {

                        colorList = colorResponse.getColorList();
                        binding.rvColor.setHasFixedSize(true);
                        binding.rvColor.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        binding.rvColor.setAdapter(new ColorAdapter(colorList, colorItemClick, product.getColorDetails().getColorId()));
                        for (Color color : colorList) {
                            if (color.getId() == product.getColorDetails().getId()){
                                binding.tvColor.setText("Selected Color:" + color.getColorName());
                            }
                        }

                        binding.avColor.setText(colorResponse.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ColorResponse> call, Throwable t) {
                Toast.makeText(context, "Color Not Found", Toast.LENGTH_LONG).show();

            }
        });

    }


//    @Override
//    public void onItemClick(int position) {
//
//    }
//    implements OnItemClick
}
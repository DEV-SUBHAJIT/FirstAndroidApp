package com.example.firstapp.activity;

import static com.example.firstapp.utility.Utility.changeFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.firstapp.R;
import com.example.firstapp.adapter.PhotoAdapter;
import com.example.firstapp.adapter.RecyclerViewAdapter;
import com.example.firstapp.fragment.EmployeeFragment;
import com.example.firstapp.fragment.HomeFragment;
import com.example.firstapp.fragment.OrderFragment;
import com.example.firstapp.fragment.ShareFragment;
import com.example.firstapp.model.Employee;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Intent getValue;
    String nName, nEmail, mPassword, userId;
    int pinCode;
    boolean loginStatus;
    TextView textView;
    ImageView /*ivImage1,*/ ivBanner, ivEmployee, ivNews, ivSliteDb,ivCart;
    DrawerLayout rootLayout;
    RecyclerView rvEmployees;
    ProgressBar progressBar;
    private Button btnPost;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navView;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        setNavigation();
        setBottomNavigation();
        clickEvent();
        getApiData();

//        setEmployee();
        getPhotoList();

      /*  Picasso.get().load("https://wallpapers.com/images/hd/hd-vacation-house-in-the-beach-j4jasqgcc5ismew8.jpg")
                .placeholder(R.drawable.shopping_image)
                .into(ivImage1);*/

        Glide.with(HomeActivity.this).load("https://cdn.pixabay.com/photo/2014/09/14/18/04/dandelion-445228_640.jpg")
                .placeholder(R.drawable.shopping_image)
                .into(ivBanner);

        getValue = getIntent();

        if (getValue != null) {
            nName = getValue.getStringExtra("name");
            nEmail = getValue.getStringExtra("email");
            userId = getValue.getStringExtra("userId");
            mPassword = getValue.getStringExtra("password");
            pinCode = getValue.getIntExtra("pinCode", -1);
            loginStatus = getValue.getBooleanExtra("isLogin", false);
        } else {
            //Show Snackbar
            Snackbar.make(rootLayout, "Intent value not found", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void setBottomNavigation() {
//        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        changeFragment(getSupportFragmentManager(), R.id.frameLayout, new HomeFragment());
                      /*  FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameLayout, new HomeFragment())
                                .commit();*/

                      /*  getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, new HomeFragment())
                                .commit();*/
                        return true;

                    case R.id.bottom_employee:
                        changeFragment(getSupportFragmentManager(), R.id.frameLayout, new EmployeeFragment());
                        /*getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameLayout, new EmployeeFragment())
                                .commit();*/
                        return true;

                    case R.id.bottom_order:
                        changeFragment(getSupportFragmentManager(), R.id.frameLayout, new OrderFragment());
                        return  true;

                    default:
                        return false;
                }
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
    }

    private void clickEvent() {
        ivEmployee.setOnClickListener(v -> {
//            startActivity(new Intent(this, EmployeeActivity.class));
            startActivity(new Intent(this, UserListActivity.class));
        });
        ivSliteDb.setOnClickListener(v -> {
            startActivity(new Intent(this, SQLiteActivity.class));
        });
        ivNews.setOnClickListener(v -> {
            startActivity(new Intent(this, NewsActivity.class));
        });

        btnPost.setOnClickListener(view -> {
            startActivity(new Intent(this, PostListActivity.class));
        });

        ivCart.setOnClickListener(v ->{
            startActivity(new Intent(this, CartActivity.class));
        });
    }

    private void initView() {
        rootLayout = findViewById(R.id.rootLayout);
        textView = findViewById(R.id.tvImageName);
//        ivImage1 = findViewById(R.id.imageView);
        ivBanner = findViewById(R.id.ivBanner);
        rvEmployees = findViewById(R.id.rvEmployees);
        progressBar = findViewById(R.id.progressBar);

        ivSliteDb = findViewById(R.id.ivSliteDb);
        ivEmployee = findViewById(R.id.ivEmployee);
        ivNews = findViewById(R.id.ivNews);
        btnPost = findViewById(R.id.btnPost);
        ivCart=findViewById(R.id.ivCart);

        navView = findViewById(R.id.navView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);

    }

    private void setNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, rootLayout, R.string.nav_open, R.string.nav_close);
        setSupportActionBar(toolbar);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        rootLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView.setNavigationItemSelectedListener(HomeActivity.this);

    }

    private void getPhotoList() {
        String photoApiUrl = "https://picsum.photos/v2/list?page=1&limit=10";
//        String photoApiUrl = "https://jsonplaceholder.typicode.com/albums/1/photos";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, photoApiUrl, response -> {
            try {
                progressBar.setVisibility(View.GONE);
                JSONArray apiResponse = new JSONArray(response);
                PhotoAdapter adapter = new PhotoAdapter(apiResponse);
                rvEmployees.setHasFixedSize(true);
//                rvEmployees.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
//                rvEmployees.setLayoutManager(new GridLayoutManager(HomeActivity.this,2));
                rvEmployees.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
                rvEmployees.setAdapter(adapter);

            } catch (JSONException e) {
                Toast.makeText(HomeActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(HomeActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        });
        queue.add(request);
    }

    private void getApiData() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/todos/1";


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int userId = jsonObject.getInt("userId");
                    int id = jsonObject.getInt("id");
                    String title = jsonObject.getString("title");
                    boolean completed = jsonObject.getBoolean("completed");

                    textView.setText("User id : " + userId + "\n" +
                            "Id : " + id + "\n" +
                            "title : " + title + "\n" +
                            "completed : " + completed + "\n");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }

    private void setEmployee() {
        List<Employee> employeeList = new ArrayList<>();

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(employeeList);
        rvEmployees.setHasFixedSize(true);
        rvEmployees.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        rvEmployees.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

  /*  @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bottom_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .commit();
                return true;

            case R.id.bottom_employee:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new EmployeeFragment())
                        .commit();
                return true;
            default: return false;
        }
    }*/

    boolean doublePress = false;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (doublePress) {
                finishAffinity();
            } else {
                doublePress = true;
                Snackbar.make(rootLayout, "Press again to exit", Snackbar.LENGTH_LONG).show();

             /*   //Create AlertDialog instance
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                // Set alert dialog title
                builder.setTitle("Warning!");

                //Set alert dialog message
                builder.setMessage("Do you want to quit this app right now?");

                //Set positive button and negative button
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                //Show Alert Dialog
                builder.show();*/


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doublePress = false;
                    }
                }, 2500);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                changeFragment(getSupportFragmentManager(), R.id.frameLayout, new HomeFragment());
                break;
            case R.id.nav_share:
                changeFragment(getSupportFragmentManager(), R.id.frameLayout, new ShareFragment());
                break;
            case R.id.nav_rate:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                }
                break;
            case R.id.nav_bottom_dialog:
                try {
                    createDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                Toast.makeText(this, "This fetchers is not available", Toast.LENGTH_SHORT).show();
        }
        rootLayout.closeDrawers();
        return true;
    }

    private void createDialog() {
        dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_dialog, null, false);
        Button submit = view.findViewById(R.id.submit);
        EditText name = view.findViewById(R.id.name);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(HomeActivity.this, name.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setContentView(view);

        dialog.show();
    }
}
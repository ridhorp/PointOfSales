package co.id.codenusa.pointofsales;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.id.codenusa.pointofsales.adapter.AdapterProduct;
import co.id.codenusa.pointofsales.db.ProductViewModel;
import co.id.codenusa.pointofsales.model.Product;

import static android.content.ContentValues.TAG;

public class ProductFragment extends Fragment {

    private RecyclerView recyclerViewProduct;
    private LinearLayout btnAdd;
    private AdapterProduct adapterProduct;
    private List<Product> productList = new ArrayList<>();

    private ProductViewModel mProductViewModel;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProductViewModel = ViewModelProviders.of(ProductFragment.this).get(ProductViewModel.class);

        displayBill();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        recyclerViewProduct = view.findViewById(R.id.rv_product);

        initializeViews();
        showDialog(view);

        return view;
    }

    private void initializeViews() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewProduct.setLayoutManager(mLayoutManager);
        recyclerViewProduct.setItemAnimator(new DefaultItemAnimator());
        adapterProduct = new AdapterProduct(getActivity(), this, productList);
        recyclerViewProduct.setAdapter(adapterProduct);
    }

    private void showDialog(View view) {

        view.findViewById(R.id.container_button_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                LayoutInflater factory = LayoutInflater.from(v.getContext());
                final View view = factory.inflate(R.layout.dialog_add_product, null);
                final EditText etName = view.findViewById(R.id.et_product_name);
                final EditText etPrice = view.findViewById(R.id.et_product_price);

                alertDialog.setView(view);

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (etName.getText().toString().trim().equals("") || etName.getText().toString().trim().length() < 1
                                || etPrice.getText().toString().trim().equals("") || etPrice.getText().toString().trim().length() < 1) {
                            Toast.makeText(getActivity(), "Isi Nama Konsumen", Toast.LENGTH_SHORT).show();
                        }

                        if (!etName.getText().toString().trim().equals("") && etName.getText().toString().trim().length() > 0
                                || !etPrice.getText().toString().trim().equals("") && etPrice.getText().toString().trim().length() > 0) {

                            String name = etName.getText().toString();
                            String prices = etPrice.getText().toString();
                            int price = Integer.parseInt(prices);

                            saveProduct(name, price);
                        } else {
                            Toast.makeText(getActivity(), "Isi Nama Konsumen", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    public void displayBill() {
        mProductViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        mProductViewModel.getAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable final List<Product> items) {
                // Update the cached copy of the words in the adapter.
                productList.clear();
                productList.addAll(items);
                adapterProduct.setProducts(productList);
                Log.d(TAG, "Product List Size: " + items.size());
            }
        });
    }

    private void saveProduct(String name, int price) {

        Product product = new Product(name, price);
        mProductViewModel.insertProduct(product);
    }
}

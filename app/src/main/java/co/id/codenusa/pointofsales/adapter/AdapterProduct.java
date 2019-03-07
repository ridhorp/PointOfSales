package co.id.codenusa.pointofsales.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import co.id.codenusa.pointofsales.R;
import co.id.codenusa.pointofsales.db.BillViewModel;
import co.id.codenusa.pointofsales.db.ProductViewModel;
import co.id.codenusa.pointofsales.model.Bill;
import co.id.codenusa.pointofsales.model.Product;

import static android.support.constraint.Constraints.TAG;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.MyViewHolder> {

    private Activity activity;
    private List<Product> productList;
    private ProductViewModel mProductViewModel;
    private BillViewModel mBillViewModel;

    public AdapterProduct(Activity context, Fragment fragment, List<Product> itemList) {

        this.activity = context;
        this.productList = itemList;
        mProductViewModel = ViewModelProviders.of(fragment).get(ProductViewModel.class);
        mBillViewModel = ViewModelProviders.of(fragment).get(BillViewModel.class);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtProduct, txtPrice;
        ImageView btnDelete;

        MyViewHolder(View view) {
            super(view);
            txtProduct = view.findViewById(R.id.txt_product_name);
            txtPrice = view.findViewById(R.id.txt_product_price);
            btnDelete = view.findViewById(R.id.btn_item_delete);
        }
    }

    @NonNull
    @Override
    public AdapterProduct.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_product, viewGroup, false);
        return new AdapterProduct.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        final String name = productList.get(i).getNameProduct();
        final int price = productList.get(i).getPriceProduct();

        // add currency
        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

        myViewHolder.txtProduct.setText(name);
        myViewHolder.txtPrice.setText("Rp " + rupiahFormat.format(price));

        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductViewModel.deleteProduct(productList.get(i));
                productList.remove(i);
                Log.d("AdapterProduct", "deleted item: " + name);
                notifyDataSetChanged();
            }
        });

        myViewHolder.txtProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(name, price);
            }
        });

        myViewHolder.txtProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(name, price);
            }
        });
    }

    private void showDialog(final String name, final int price) {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(activity).create();
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_quantity, null);

        final EditText etQty = dialogView.findViewById(R.id.et_qty_item);
        Button btnCancel = dialogView.findViewById(R.id.btn_bill_dialog_cancel);
        Button btnOk = dialogView.findViewById(R.id.btn_bill_dialog_ok);
        Button btnPlus = dialogView.findViewById(R.id.btn_plus_item);
        Button btnMinus = dialogView.findViewById(R.id.btn_minus_item);
        etQty.setKeyListener(null);
        etQty.setText("1");

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qtys = etQty.getText().toString();
                int qty = Integer.parseInt(qtys);
                qty++;
                etQty.setText("" + qty);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qtys = etQty.getText().toString();
                int qty = Integer.parseInt(qtys);
                qty--;
                etQty.setText("" + qty);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS

                if (etQty.getText().toString().trim().equals("0") || etQty.getText().toString().trim().length() < 1) {
                    Toast.makeText(activity, "Isi Quantity Item", Toast.LENGTH_SHORT).show();
                }

                if (!etQty.getText().toString().trim().equals("0") && etQty.getText().toString().trim().length() > 0) {

                    String qtys = etQty.getText().toString();
                    int qty = Integer.parseInt(qtys);

                    Log.d(TAG, "Bill item : " + name);
                    Log.d(TAG, "Bill qty : " + qty);
                    Log.d(TAG, "Bill price : " + price);

                    Bill bill = new Bill(name, qty, price);
                    mBillViewModel.insertBill(bill);

                    dialogBuilder.dismiss();
                } else {
                    Toast.makeText(activity, "Isi Quantity Item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void setProducts(List<Product> items) {
        productList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
package co.id.codenusa.pointofsales.adapter;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import co.id.codenusa.pointofsales.R;
import co.id.codenusa.pointofsales.db.BillViewModel;
import co.id.codenusa.pointofsales.model.Bill;

public class AdapterBill extends RecyclerView.Adapter<AdapterBill.MyViewHolder> {

    private Activity activity;
    private List<Bill> billList;
    private BillViewModel mBillViewModel;

    public AdapterBill(Activity context, Fragment fragment, List<Bill> billList) {

        this.activity = context;
        this.billList = billList;
        mBillViewModel = ViewModelProviders.of(fragment).get(BillViewModel.class);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtItem, txtPrice, txtQty;
        ImageView btnDelete;

        MyViewHolder(View view) {
            super(view);
            txtItem = view.findViewById(R.id.txt_bill_name);
            txtPrice = view.findViewById(R.id.txt_bill_price);
            txtQty = view.findViewById(R.id.txt_bill_qty);
            btnDelete = view.findViewById(R.id.btn_item_delete);
        }
    }

    @NonNull
    @Override
    public AdapterBill.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_bill, viewGroup, false);
        return new AdapterBill.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBill.MyViewHolder myViewHolder, final int i) {

        final String name = billList.get(i).getNameItem();
        final int price = billList.get(i).getPriceItem();
        final int qty = billList.get(i).getQtyItem();

        // add currency
        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

        myViewHolder.txtItem.setText(name);
        myViewHolder.txtQty.setText("" + qty);
        myViewHolder.txtPrice.setText("Rp " + rupiahFormat.format(price));

        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBillViewModel.deleteBill(billList.get(i));
                billList.remove(i);
                Log.d("AdapterBill", "deleted item: " + name);
                notifyDataSetChanged();
            }
        });
    }

    public void setBills(List<Bill> items) {
        billList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }
}

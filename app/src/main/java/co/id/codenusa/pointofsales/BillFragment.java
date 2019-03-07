package co.id.codenusa.pointofsales;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.id.codenusa.pointofsales.adapter.AdapterBill;
import co.id.codenusa.pointofsales.db.BillViewModel;
import co.id.codenusa.pointofsales.db.PosDatabase;
import co.id.codenusa.pointofsales.model.Bill;

import static android.content.ContentValues.TAG;

public class BillFragment extends Fragment {

    private RecyclerView recyclerViewBill;
    private AdapterBill adapterBilll;
    private List<Bill> billList = new ArrayList<>();
    private Button btnClear, btnSave;
    private TextView txtIdBill;
    private NumberFormat rupiahFormat;
    private int total;
    private String idBill;

    private BillViewModel mBillViewModel;
    private PosDatabase posDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // add currency
        rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);

        posDatabase = posDatabase.getInstance(getActivity());
        mBillViewModel = ViewModelProviders.of(this).get(BillViewModel.class);
        generateId();
        displayBill();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        recyclerViewBill = view.findViewById(R.id.rv_bill);
        btnClear = view.findViewById(R.id.btn_clear_sale);
        btnSave = view.findViewById(R.id.btn_save_bill);
        txtIdBill = view.findViewById(R.id.txt_bill_id);
        txtIdBill.setText("ID: " + idBill);

        initializeViews();
        clickView();
        return view;
    }

    private void initializeViews() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewBill.setLayoutManager(mLayoutManager);
        recyclerViewBill.setItemAnimator(new DefaultItemAnimator());
        adapterBilll = new AdapterBill(getActivity(), this, billList);
        recyclerViewBill.setAdapter(adapterBilll);
    }

    private void clickView() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                posDatabase.getBillDao().deleteAll();
                billList.clear();
                displayBill();
                generateId();
                txtIdBill.setText("ID: " + idBill);
            }
        });
    }

    public void displayBill() {
        mBillViewModel = ViewModelProviders.of(this).get(BillViewModel.class);
        mBillViewModel.getAllBills().observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(@Nullable final List<Bill> items) {
                // Update the cached copy of the words in the adapter.
                billList.clear();
                billList.addAll(items);
                displayTotal(billList);
                adapterBilll.setBills(billList);
                Log.d(TAG, "Product List Size: " + items.size());
            }
        });
    }

    private void generateId() {
        // generate id bill
        //date
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        String date = df.format(d);
        idBill = date;
    }

    private void displayTotal(List<Bill> itemList) {

        //perhitungan total
        total = 0;

        if (itemList.size() == 0) {
            total = 0;
        }
        if (itemList.size() != 0) {
            for (int i = 0; i < itemList.size(); i++) {
                int priceBill = itemList.get(i).getPriceItem();
                int qtyBill = itemList.get(i).getQtyItem();
                int subTotal = priceBill * qtyBill;
                total = subTotal + total;
            }
        }

        //txtTotalBill.setText("Total Rp " + rupiahFormat.format(total));
        btnSave.setText("Charge Rp " + rupiahFormat.format(total));
    }

}

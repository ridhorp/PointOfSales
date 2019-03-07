package co.id.codenusa.pointofsales.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import co.id.codenusa.pointofsales.model.Bill;

public class BillViewModel extends AndroidViewModel {

    private BillRepository mRepository;
    private LiveData<List<Bill>> mAllBills;
    private String idBill;

    public BillViewModel(Application application) {
        super(application);
        mRepository = new BillRepository(application);
        mAllBills = mRepository.getAllBills();
    }

    public LiveData<List<Bill>> getAllBills() {
        return mAllBills;
    }

    public void insertBill(Bill bill) {
        mRepository.insertBill(bill);
    }

    public void deleteBill(Bill bill) {
        mRepository.deleteBill(bill);
    }
}

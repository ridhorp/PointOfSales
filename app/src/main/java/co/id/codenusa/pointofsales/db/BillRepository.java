package co.id.codenusa.pointofsales.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import co.id.codenusa.pointofsales.model.Bill;

public class BillRepository {

    private BillDao mBillDao;
    private LiveData<List<Bill>> mAllBills;

    BillRepository(Application application) {
        PosDatabase db = PosDatabase.getInstance(application);
        mBillDao = db.getBillDao();
        mAllBills = mBillDao.getAllBills();
    }

    LiveData<List<Bill>> getAllBills() {
        return mAllBills;
    }

    void insertBill(Bill bill) {
        new insertAsyncTask(mBillDao).execute(bill);
    }

    void deleteBill(Bill bill) {
        new deleteAsyncTask(mBillDao).execute(bill);
    }

    private static class insertAsyncTask extends AsyncTask<Bill, Void, Void> {

        private BillDao mAsyncTaskDao;

        insertAsyncTask(BillDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Bill... params) {
            mAsyncTaskDao.insertBill(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Bill, Void, Void> {
        private BillDao mAsyncTaskDao;

        deleteAsyncTask(BillDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Bill... params) {
            mAsyncTaskDao.deleteBill(params[0]);
            return null;
        }
    }
}

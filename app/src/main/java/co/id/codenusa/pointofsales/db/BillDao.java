package co.id.codenusa.pointofsales.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import co.id.codenusa.pointofsales.model.Bill;
import co.id.codenusa.pointofsales.util.Constants;

@Dao
public interface BillDao {
    @Query("SELECT * FROM " + Constants.TABLE_NAME_BILL)
    LiveData<List<Bill>> getAllBills();

    @Insert
    long insertBill(Bill bill);

    @Delete
    void deleteBill(Bill bill);

    @Query("DELETE FROM " + Constants.TABLE_NAME_BILL)
    void deleteAll();
}

package co.id.codenusa.pointofsales.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import co.id.codenusa.pointofsales.model.Report;
import co.id.codenusa.pointofsales.util.Constants;

@Dao
public interface ReportDao {

    @Query("SELECT * FROM " + Constants.TABLE_NAME_REPORT)
    LiveData<List<Report>> getAllReports();

    @Insert
    long insertReport(Report bill);

    @Delete
    void deleteReport(Report bill);

    @Query("DELETE FROM " + Constants.TABLE_NAME_REPORT)
    void deleteAll();
}

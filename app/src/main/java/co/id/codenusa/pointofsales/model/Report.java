package co.id.codenusa.pointofsales.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import co.id.codenusa.pointofsales.util.Constants;

@Entity(tableName = Constants.TABLE_NAME_REPORT)
public class Report {

    @PrimaryKey(autoGenerate = true)
    private long idReport;

    @ColumnInfo(name = "consumer_name")
    private String nameConsumer;

    @ColumnInfo(name = "bill_amount")
    private int amountBill;

    @ColumnInfo(name = "bill_date")
    private int dateBill;

    public int getDateBill() {
        return dateBill;
    }

    public void setDateBill(int dateBill) {
        this.dateBill = dateBill;
    }

    public int getAmountBill() {
        return amountBill;
    }

    public void setAmountBill(int amountBill) {
        this.amountBill = amountBill;
    }

    public String getNameConsumer() {
        return nameConsumer;
    }

    public void setNameConsumer(String nameConsumer) {
        this.nameConsumer = nameConsumer;
    }

    public long getIdReport() {
        return idReport;
    }

    public void setIdReport(long idReport) {
        this.idReport = idReport;
    }
}

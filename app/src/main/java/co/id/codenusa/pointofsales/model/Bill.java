package co.id.codenusa.pointofsales.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import co.id.codenusa.pointofsales.util.Constants;

@Entity(tableName = Constants.TABLE_NAME_BILL)
public class Bill {

    @PrimaryKey(autoGenerate = true)
    private long idBill;

    @ColumnInfo(name = "item_name")
    private String nameItem;

    @ColumnInfo(name = "item_qty")
    private int qtyItem;

    @ColumnInfo(name = "item_price")
    private int priceItem;

    public Bill(String nameItem, int qtyItem, int priceItem) {
        this.nameItem = nameItem;
        this.qtyItem = qtyItem;
        this.priceItem = priceItem;
    }

    public int getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(int priceItem) {
        this.priceItem = priceItem;
    }

    public int getQtyItem() {
        return qtyItem;
    }

    public void setQtyItem(int qtyItem) {
        this.qtyItem = qtyItem;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public long getIdBill() {
        return idBill;
    }

    public void setIdBill(long idBill) {
        this.idBill = idBill;
    }
}

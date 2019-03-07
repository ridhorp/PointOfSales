package co.id.codenusa.pointofsales.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import co.id.codenusa.pointofsales.util.Constants;

@Entity(tableName = Constants.TABLE_NAME_PRODUCT)
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long idProduct;

    @ColumnInfo(name = "product_name")
    private String nameProduct;

    @ColumnInfo(name = "product_price")
    private int priceProduct;

    public Product(String nameProduct, int priceProduct) {
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
    }

    public int getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(int priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }
}

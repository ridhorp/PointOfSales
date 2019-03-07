package co.id.codenusa.pointofsales.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import co.id.codenusa.pointofsales.model.Product;
import co.id.codenusa.pointofsales.util.Constants;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM " + Constants.TABLE_NAME_PRODUCT)
    LiveData<List<Product>> getAllProduct();

    @Insert
    long insertProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("DELETE FROM " + Constants.TABLE_NAME_PRODUCT)
    void deleteAll();
}

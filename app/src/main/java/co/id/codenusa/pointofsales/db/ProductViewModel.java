package co.id.codenusa.pointofsales.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import co.id.codenusa.pointofsales.model.Product;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private LiveData<List<Product>> mAllProduct;
    private String idProduct;

    public ProductViewModel(Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mAllProduct = mRepository.getAllProducts();
    }

    public LiveData<List<Product>> getAllProduct() {
        return mAllProduct;
    }

    public void insertProduct(Product product) {
        mRepository.insertProduct(product);
    }

    public void deleteProduct(Product product) {
        mRepository.deleteProduct(product);
    }

}

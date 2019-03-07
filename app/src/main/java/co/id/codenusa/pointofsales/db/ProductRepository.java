package co.id.codenusa.pointofsales.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import co.id.codenusa.pointofsales.model.Product;

public class ProductRepository {

    private ProductDao mProductDao;
    private LiveData<List<Product>> mAllProducts;

    ProductRepository(Application application) {
        PosDatabase db = PosDatabase.getInstance(application);
        mProductDao = db.getProductDao();
        mAllProducts = mProductDao.getAllProduct();
    }

    LiveData<List<Product>> getAllProducts() {
        return mAllProducts;
    }

    void insertProduct(Product product) {
        new ProductRepository.insertAsyncTask(mProductDao).execute(product);
    }

    void deleteProduct(Product product) {
        new ProductRepository.deleteAsyncTask(mProductDao).execute(product);
    }

    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {

        private ProductDao mAsyncTaskDao;

        insertAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Product... params) {
            mAsyncTaskDao.insertProduct(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao mAsyncTaskDao;

        deleteAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Product... params) {
            mAsyncTaskDao.deleteProduct(params[0]);
            return null;
        }
    }
}

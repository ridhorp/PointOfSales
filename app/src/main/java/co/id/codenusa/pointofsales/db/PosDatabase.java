package co.id.codenusa.pointofsales.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import co.id.codenusa.pointofsales.model.Bill;
import co.id.codenusa.pointofsales.model.Product;
import co.id.codenusa.pointofsales.model.Report;
import co.id.codenusa.pointofsales.util.Constants;

@Database(entities = {Product.class, Bill.class, Report.class}, version = 1)
public abstract class PosDatabase extends RoomDatabase {

    public abstract ProductDao getProductDao();

    public abstract BillDao getBillDao();

    public abstract ReportDao getBillListDao();

    private static PosDatabase itemDB;

    public static PosDatabase getInstance(Context context) {

        if (null == itemDB) {
            itemDB = buildDatabaseInstance(context);
        }
        return itemDB;
    }

    private static PosDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                PosDatabase.class,
                Constants.DB_NAME).allowMainThreadQueries().build();
    }

    public void cleanUp() {
        itemDB = null;
    }
}

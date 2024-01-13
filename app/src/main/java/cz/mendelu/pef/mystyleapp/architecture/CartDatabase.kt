package cz.mendelu.pef.mystyleapp.architecture

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.mendelu.pef.mystyleapp.data.CartItem

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [CartItem::class], version = 2, exportSchema = true)
abstract class CartDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        private var INSTANCE: CartDatabase? = null
        fun getDatabase(context: Context): CartDatabase {
            if (INSTANCE == null) {
                synchronized(CartDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CartDatabase::class.java, "tasks_database"
                        ).addMigrations()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }


    }
}
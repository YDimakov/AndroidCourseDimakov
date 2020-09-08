package Data

import Utils.Util
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactsinroomlibrary.Contact

@Database(entities = [Contact::class], version = Util.DATABASE_VERSION)
abstract class ContactAppDatabase : RoomDatabase() {
    abstract var contactDAO: ContactDAO
}


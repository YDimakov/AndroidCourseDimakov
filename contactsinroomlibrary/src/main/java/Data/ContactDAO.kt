package Data

import androidx.room.*
import com.example.contactsinroomlibrary.Contact

@Dao
interface ContactDAO {
    @Insert
    fun addContact(contact: Contact): Long

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @get:Query("select * from contacts")
    val allContacts: List<Contact?>

    @Query("select * from contacts where contact_id==:contactId ")
    fun getContact(contactId: Long): Contact
}
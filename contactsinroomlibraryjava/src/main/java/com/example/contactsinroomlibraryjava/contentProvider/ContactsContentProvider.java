package com.example.contactsinroomlibraryjava.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contactsinroomlibraryjava.data.ContactAppDatabase;
import com.example.contactsinroomlibraryjava.data.ContactDAO;

import static com.example.contactsinroomlibraryjava.utils.Util.AUTHORITY;
import static com.example.contactsinroomlibraryjava.utils.Util.DATABASE_NAME;

public class ContactsContentProvider extends ContentProvider {
    private static final int URI_CONTACT_CODE = 1;
    private static final UriMatcher uriMatcher;
    private ContactDAO contactDAO;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, DATABASE_NAME, URI_CONTACT_CODE);
    }

    @Override
    public boolean onCreate() {
        contactDAO = ContactAppDatabase.getInstance(getContext()).getContactDAO();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case URI_CONTACT_CODE:
                cursor =  contactDAO.findAll();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + uriMatcher.match(uri));
        }
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
        throw new IllegalArgumentException
                ("Unknown URI: " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

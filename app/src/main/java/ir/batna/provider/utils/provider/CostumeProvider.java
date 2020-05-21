package ir.batna.provider.utils.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ir.batna.provider.utils.parsxml.XmlReader;

public class CostumeProvider extends ContentProvider {
    private static final String TAG = "CostumeProvider";


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final String CONTENT_AUTHORITY = "ir.batna.provider.CostumeProvider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY + "/" + AppsContract.APPS);
    public static List<AppsContract> appsContractList;
    private static final int APPS = 100;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        //e.g. content://ir.batna.provider.utils.provider.provider/apps
        matcher.addURI(CONTENT_AUTHORITY, AppsContract.APPS, APPS);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        XmlReader xmlReader = new XmlReader(getContext());
        AppsContract appsContract = new AppsContract(xmlReader.passData().get(0), xmlReader.passData().get(1));
        appsContractList = new ArrayList<>();
        appsContractList.add(appsContract);
        return true;
    }

    public CostumeProvider() {
        super();
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Cursor c;
        Log.d(TAG, "query: match is " + match);

        switch (match) {
            case APPS:
                c = getCursorFromList(appsContractList);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case APPS:
                return AppsContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    public Cursor getCursorFromList(List<AppsContract> app) {

        MatrixCursor cursor = new MatrixCursor(
                new String[]{"_id", "name", "url1", "url2", "url3"}
        );

        cursor.newRow()
                .add("_id", 0)
                .add("name", app.get(0).getAPPS_NAME())
                .add("url1", app.get(0).getAPPS_URL());

        return cursor;
    }
}

package ir.batna.provider.utils.provider;


import android.net.Uri;

import static ir.batna.provider.utils.provider.CostumeProvider.CONTENT_AUTHORITY;
import static ir.batna.provider.utils.provider.CostumeProvider.CONTENT_AUTHORITY_URI;


/**
 * Created by Dev_am1 on 2/12/2020
 */
public class AppsContract {
    private String APPS_NAME = "app_name";
    private String APPS_URL = "url";

    public AppsContract(String appName, String appsUrl) {
        this.APPS_NAME = appName;
        this.APPS_URL = appsUrl;
    }


    /**
     * The Uri to access the Apps.
     */
    public static final String APPS = "apps";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, APPS);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + APPS;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + APPS;

    public String getAPPS_NAME() {
        return APPS_NAME;
    }

    public String getAPPS_URL() {
        return APPS_URL;
    }
}

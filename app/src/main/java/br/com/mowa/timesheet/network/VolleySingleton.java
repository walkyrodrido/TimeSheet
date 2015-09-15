package br.com.mowa.timesheet.network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import br.com.mowa.timesheet.TimeSheetApplication;

/**
 * Created by walky on 9/11/15.
 */
public class VolleySingleton {
    public static String URL_POST_CREATE_SESSIONS = "http://walkyteste.goldarkapi.com/sessions";
    private static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;

    private VolleySingleton() {
        this.mRequestQueue = Volley.newRequestQueue(TimeSheetApplication.getAppContext());
    }

    public static VolleySingleton getInstance() {
        if (sInstance == null) {
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}

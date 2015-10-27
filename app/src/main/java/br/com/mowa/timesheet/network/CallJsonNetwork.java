package br.com.mowa.timesheet.network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by walky on 14/09/2015.
 */
public class CallJsonNetwork {
    private CallJsonNetwork instanceCallJsonNetwork;
    private RequestQueue mRequestQueue;
    private String TAG = "CLASS CallJsonNetwork";

    public void callJsonObjectGet(String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.GET, url, null, responseListener, errorListener);

        mRequestQueue.add(request);
    }


    public void callJsonObjectPost(String url, JSONObject requestBody ,Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST, url, requestBody, responseListener, errorListener);

        this.mRequestQueue.add(request);
    }


    public void callJsonObjectPut(String url, JSONObject requestBody, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.PUT, url, requestBody, responseListener, errorListener);

        this.mRequestQueue.add(request);
    }


    public void callJsonObjectDelete(String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.DELETE, url, null, responseListener, errorListener);

        mRequestQueue.add(request);
    }
}

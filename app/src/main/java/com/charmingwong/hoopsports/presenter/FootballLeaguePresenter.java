package com.charmingwong.hoopsports.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.charmingwong.hoopsports.R;
import com.charmingwong.hoopsports.comminterface.OnResponseCallback;
import com.charmingwong.hoopsports.parser.FootballLeagueParser;
import com.charmingwong.hoopsports.utils.ApplicationUtils;
import com.charmingwong.hoopsports.utils.HashKeyUtils;
import com.charmingwong.hoopsports.utils.NetworkUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by 56223 on 2017/3/10.
 */

public class FootballLeaguePresenter extends LeaguePresenter {
    private final String DATA_TAG = "football_league_data";
    private final String TAG = "FootballLeaguePresenter";
    private final String CACHE_DIR = "football_league_data";
    private Queue<String> mUrls;
    private Queue<String> mKeys;

    private Context mContext;

    private OnResponseCallback mOnResponseCallback;

    private FootballLeagueParser mDataParser;

    private static FootballLeaguePresenter mInstance;

    public void setLeagueName(String leagueName) {
        String url = "http://op.juhe.cn/onebox/football/league?key=f1ef7a0470d83e01a6884725e39d7362&league=" + leagueName;
        if (checkDataUpdate(url)) {
            mUrls.add(url);
        }
        mKeys.add(url);
    }

    private FootballLeaguePresenter(Context context) {
        mContext = context;
        mUrls = new LinkedBlockingQueue<>();
        mKeys = new LinkedBlockingQueue<>();
    }

    public static FootballLeaguePresenter getInstance(Context context) {
        if (mInstance == null) {
            synchronized (FootballLeaguePresenter.class) {
                if (mInstance == null) {
                    mInstance = new FootballLeaguePresenter(context);
                }
            }
        }
        return mInstance;
    }

    //开始主导
    @Override
    public void startPresent() {
        if (mDataParser == null) {
            mDataParser = new FootballLeagueParser();
        }
        boolean isNetworkAvailable = NetworkUtils.checkNetworkStatus(mContext);
        boolean isUpdateAvailable = checkDataUpdate(mKeys.peek());
        if (isNetworkAvailable && isUpdateAvailable) {
            Log.i(TAG, "startPresent: load football data from network");
            requestData();
        } else {
            Log.i(TAG, "startPresent: load football data from local");
            Object data = loadDataFromLocal();
            if (data != null) {
                returnData(data);
            } else {
                String url = mKeys.poll();
                mUrls.add(url);
                mKeys.add(url);
                requestData();
            }
        }
    }

    //请求数据
    private void requestData() {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        Response.Listener listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: " + response);
                onRequestCompleted(response, mKeys.poll());

                if (!mKeys.isEmpty()) {
                    Log.i(TAG, "startPresent: load football data from local");
                    Object data = loadDataFromLocal();
                    if (data != null) {
                        returnData(data);
                    } else {
                        String url = mKeys.poll();
                        mUrls.add(url);
                        mKeys.add(url);
                        requestData();
                    }
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, mContext.getString(R.string.host_error_text), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onErrorResponse: " + error.getMessage());
                returnData(null);
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.GET, mUrls.poll(), listener, errorListener);
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    //请求完成
    @Override
    public void onRequestCompleted(Object result, String url) {
        Map<String, Object> map = new HashMap<>();
        if (mDataParser != null) {
            map = mDataParser.parse((String) result);
        }
        returnData(map);
        writeDataToLocal(map, url);
    }

    //返回数据
    @Override
    public void returnData(Object result) {
        if (mOnResponseCallback != null) {
            if (result != null) {
                if (result instanceof Map) {
                    mOnResponseCallback.onResponseSuccess(result);
                } else {
                    mOnResponseCallback.onResponseError(((Exception) result));
                }
            } else {
                Log.i(TAG, "returnData: " + "null");
                mOnResponseCallback.onResponseError(new Exception("request error null"));
            }
        }
    }

    //写入缓存
    private void writeDataToLocal(Object parsedData, String url) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            if (parsedData != null) {
                File file = new File(
                        ApplicationUtils.getDiskCacheDir(mContext, CACHE_DIR),
                        HashKeyUtils.generateHashKey(url)
                );
                fos = new FileOutputStream(file);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(parsedData);
                oos.flush();

                //更新时间
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(DATA_TAG, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(url, System.currentTimeMillis());
                editor.apply();
                Log.i(TAG, "writeDataToLocal: wrote football data successfully");
            }
        } catch (Exception e) {
            Log.i(TAG, "writeDataToLocal: failed to write football data");
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //读取缓存
    private synchronized Object loadDataFromLocal() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            File file = new File(
                    ApplicationUtils.getDiskCacheDir(mContext, CACHE_DIR),
                    HashKeyUtils.generateHashKey(mKeys.peek())
            );
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            Log.i(TAG, "loadDataFromLocal: load football data from local successful");
            mKeys.remove();
            return ois.readObject();
        } catch (Exception exception) {
            //本地加载数据失败，从网络获取数据
            Log.i(TAG, "loadDataFromLocal: failed to load football data from local");
            Log.i(TAG, "loadDataFromLocal: load football data from network");
            exception.printStackTrace();
            return null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //检查更新
    public boolean checkDataUpdate(String url) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DATA_TAG, Context.MODE_PRIVATE);
        long lastUpdate = sharedPreferences.getLong(url, 0);
        //距离上次更新过去30秒则再次更新
        return System.currentTimeMillis() - lastUpdate > 60 * 60 * 1000;
    }

    public void setOnResponseCallback(OnResponseCallback onResponseCallback) {
        this.mOnResponseCallback = onResponseCallback;
    }

}

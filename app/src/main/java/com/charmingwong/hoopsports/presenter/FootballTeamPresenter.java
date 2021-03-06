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
import com.charmingwong.hoopsports.parser.FootballTeamParser;
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

/**
 * Created by 56223 on 2017/3/10.
 */

public class FootballTeamPresenter implements IPresenter {
    private final String DATA_TAG = "football_team_data";
    private final String TAG = "FootballTeamPresenter";
    private final String CACHE_DIR = "football_team_data";
    private String mUrl;


    private Context mContext;

    private FootballTeamParser mDataParser;

    private OnResponseCallback mOnResponseCallback;

    private static FootballTeamPresenter mInstance;

    public void setTeamName(String leagueName) {
        mUrl = "http://op.juhe.cn/onebox/football/team?key=f1ef7a0470d83e01a6884725e39d7362&team=" + leagueName;
    }

    private FootballTeamPresenter(Context context) {
        mContext = context;
    }

    public static FootballTeamPresenter getInstance(Context context) {
        if (mInstance == null) {
            synchronized (FootballTeamPresenter.class) {
                if (mInstance == null) {
                    mInstance = new FootballTeamPresenter(context);
                }
            }
        }
        return mInstance;
    }


    //开始主导
    @Override
    public void startPresent() {
        if (mDataParser == null) {
            mDataParser = new FootballTeamParser();
        }
        boolean isNetworkAvailable = NetworkUtils.checkNetworkStatus(mContext);
        boolean isUpdateAvailable = checkDataUpdate();
        if (isNetworkAvailable && isUpdateAvailable) {
            Log.i(TAG, "startPresent: load football team data from network");
            requestData();
        } else {
            Log.i(TAG, "startPresent: load football team data from local");
            Object data = loadDataFromLocal();
            if (data != null) {
                returnData(data);
            } else {
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
                onRequestCompleted(response);
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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, mUrl, listener, errorListener);
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    //请求完成
    @Override
    public void onRequestCompleted(Object result) {
        parseData((String) result);
    }

    //解析数据
    private void parseData(String data) {
        Map<String, Object> map = new HashMap<>();
        if (mDataParser != null) {
            map = mDataParser.parse(data);
        }
        returnData(map);
        writeDataToLocal(map);
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
    private void writeDataToLocal(Object parsedData) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            if (parsedData != null) {
                File file = new File(
                        ApplicationUtils.getDiskCacheDir(mContext, CACHE_DIR),
                        HashKeyUtils.generateHashKey(mUrl)
                );
                fos = new FileOutputStream(file);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(parsedData);
                oos.flush();

                //更新时间
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(DATA_TAG, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(mUrl, System.currentTimeMillis());
                editor.apply();
                Log.i(TAG, "writeDataToLocal: wrote football team data successfully");
            }
        } catch (Exception e) {
            Log.i(TAG, "writeDataToLocal: failed to write football team data");
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
    private Object loadDataFromLocal() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            File file = new File(
                    ApplicationUtils.getDiskCacheDir(mContext, CACHE_DIR),
                    HashKeyUtils.generateHashKey(mUrl)
            );
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            Log.i(TAG, "loadDataFromLocal: load football team data from local successful");
            return ois.readObject();
        } catch (Exception exception) {
            //本地加载数据失败，从网络获取数据
            Log.i(TAG, "loadDataFromLocal: failed to load football team data from local");
            Log.i(TAG, "loadDataFromLocal: load football team data from network");
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
    private boolean checkDataUpdate() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(DATA_TAG, Context.MODE_PRIVATE);
        long lastUpdate = sharedPreferences.getLong(mUrl, 0);
        //距离上次更新过去30秒则再次更新
        return  System.currentTimeMillis() - lastUpdate > 60 * 60 * 1000;
    }

    public void setOnResponseCallback(OnResponseCallback onResponseCallback) {
        this.mOnResponseCallback = onResponseCallback;
    }
}

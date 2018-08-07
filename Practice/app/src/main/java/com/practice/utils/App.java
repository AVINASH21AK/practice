package com.practice.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by avinash.kahar on 3/20/2018.
 */

public class App extends Application {

    public static String TAG = "APP";
    static Context context;
    private static App mInstance;
    public static String PREF_NAME = "Demo_PREFERENCE";

    public static String App_mode = "1";  //1 for Production and 2 for Development
    public static String App_plateform = "2";  //1 for IOS and 2 for Android

    static Typeface tf_Thin, tf_Regular, tf_Bold, tf_Light, tf_Medium;

    public static String strSDcardPath = Environment.getExternalStorageDirectory().toString();
    public static String strFolderName = ".Demo";
    public static String strFolderFullPath = strSDcardPath + File.separator + App.strFolderName;

    public static Double latitude = 0d, longitude = 0d;

    public static String dateTimeFormateDateOnly2 = "dd/MM/yyyy";
    public static String dateTimeFormateDate = "MMMM dd, yyyy";
    public static String dateTimeFormateDateLong = "EEE MMM dd HH:mm:ss zzz yyyy";




    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        mInstance = this;

        /*------ Facebook image loading ---------*/
        Fresco.initialize(this);

    }


    /*-----start Retrofit-----*/

/*
    public static OkHttpClient getClient() {
        //OkHttpClient client =

        return new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();
        //return client;
    }

    public static Retrofit getRetrofitBuilder() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        return new Retrofit.Builder()
                .baseUrl("http://mlsportal.co.nz/wp-json/wp/")
                .client(getClient()) // it's optional for adding client
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getRetrofitApiService() {
        return getRetrofitBuilder().create(ApiService.class);
    }

    public static RequestBody createPartFromString(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }
*/

    /*-----end Retrofit-----*/





    /*------ Log -------*/
    public static void showLog(String ActivityName, String strMessage) {
        Log.d("From: ", ActivityName + " -- " + strMessage);
    }

    public static void showLog(String strMessage) {
        Log.d("From: ", strMessage);
    }

    public static void showLogResponce(String strFrom, String strMessage) {
        Log.d("From: " + strFrom, " strResponse: " + strMessage);
    }

    public static void showLogApi(String strFrom, String strMessage) {
        Log.d("From: " + strFrom, " OP_: " + strMessage);
    }



    /*------ Check Internet -------*/
    public static boolean isInternetAvail(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    /*---------- IMEI Number ----------*/
    public static String getIMEInumber(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String strIMEI = telephonyManager.getDeviceId();

        if (strIMEI != null && strIMEI.length() > 0) {
            return strIMEI;
        } else {
            Random ran = new Random();
            int x = ran.nextInt(5) + 1050;

            return x + "1050";
        }

    }



    /*--------- facebook hash key generator ------------*/
    public static void GenerateKeyHash() {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES); //GypUQe9I2FJr2sVzdm1ExpuWc4U= android pc -2 key
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                App.showLog(TAG, "Facebook KeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Photos and images bitmap
     * */
    public static void saveBitmapSdcard(String filename, Bitmap bitmap) {
        FileOutputStream out = null;
        try {
            String directoryPath = App.strFolderFullPath;

            File appDir = new File(directoryPath);

            if (!appDir.exists() && !appDir.isDirectory()) {
                if (appDir.mkdirs()) {
                    App.showLog("===CreateDir===", "App dir created");
                } else {
                    App.showLog("===CreateDir===", "Unable to create app dir!");
                }
            }


            out = new FileOutputStream(directoryPath + File.separator + filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    /*
     * Fonts Type
     * */
    public static Typeface getFont_Thin() {
        tf_Thin = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
        return tf_Thin;
    }

    public static Typeface getFont_Regular() {
        tf_Regular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        return tf_Regular;
    }

    public static Typeface getFont_Light() {
        tf_Light = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        return tf_Light;
    }

    public static Typeface getFont_Medium() {
        tf_Medium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        return tf_Medium;
    }

    public static Typeface getFont_Bold() {
        tf_Bold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
        return tf_Bold;
    }


    /*
     * DateFormater
     * */
    public static String getDateCustomFormat(String strDate, String inputDate, String outputDate) {

        if (strDate != null) {
            strDate = App.getDateOnly2(strDate, inputDate, outputDate);
        }

        return strDate;
    }

    public static String getDateOnly2(String convert_date_string, String inputDate, String outputDate) {
        String final_date = "";
        String date1 = "";
        if (convert_date_string != null) {

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat(inputDate);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputDate);
                String inputDateStr = convert_date_string;
                Date date = null;
                date = inputFormat.parse(inputDateStr);
                date1 = outputFormat.format(date);
                final_date = date1.toLowerCase();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return final_date;
    }


    public static String getPriceFormated(String strPrice) {
        String formatedPrice = "";
        try {
            DecimalFormat formatter = new DecimalFormat("##,##,##,###");
            formatedPrice = formatter.format(Integer.parseInt(strPrice));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formatedPrice;
    }


    /*
     * collapse view with animation
     * */
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    /*
     * expand view with animation
     * */
    public static void expand(final View v) {
        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT); //WRAP_CONTENT
        //v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.WRAP_CONTENT //WRAP_CONTENT
                        //? WindowManager.LayoutParams.MATCH_PARENT //WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }



    public static void hideSoftKeyboardMy(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

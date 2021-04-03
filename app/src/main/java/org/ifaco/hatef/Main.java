package org.ifaco.hatef;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

public class Main extends AppCompatActivity {
    ConstraintLayout mBody, mLogin, mLUserBorder, mLPassBorder, mLSignUpCl, mLRPasBorder, mLNameBorder, mLAdmnBorder;
    View mMotor, mBG, mLBorderBottom, mLAdmnBG;
    Toolbar mToolbar;
    ScrollView mBodyLogin;
    LinearLayout mLogin2, mLSUExpand, mLSignUp, mButtons;
    TextView mLTitle, mLSUExpTV;
    EditText mLUser, mLPass, mLRPas, mLName;
    CheckBox mLReme;
    ImageView mLSUExpPointer, mLGo, mLReset;
    Spinner mLAdmn;

    ActionBar mToolbarAB;
    Typeface tfYekan;
    SharedPreferences sp;
    boolean night = false, signUp = true, gReme = false, autoLogin = false, loggingIn = false, checkingFU = false, exiting = false;
    public static String[] validChars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
            "r", "s", "t", "u", "v", "w", "x", "y", "z", "_", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "@"},
            loginSPNames = new String[]{"liUser", "liPass", "liReme", "liDate", "liWhoo", "liAdmn"};
    int[] nightly = new int[]{R.color.mBodyLoginN, R.color.mLoginBorderN, R.color.mLoginN, R.color.mLoginETBorderN, R.color.mLoginETVN,
            R.color.mLoginETTN, R.color.mLoginETTHintN, R.color.mLTitleTN, R.color.mTBTTN, R.color.mTBIconsN,
            R.style.ThemeOverlay_AppCompat_Dark, R.color.mLRemeN, R.color.mLSUExpTVN, R.color.mTBSTTN, R.layout.spn_dd_2_n,
            R.color.spQuaVN, R.color.mLRemeBDN}, daily = new int[]{R.color.mBodyLogin, R.color.mLoginBorder, R.color.mLogin,
            R.color.mLoginETBorder, R.color.mLoginETV, R.color.mLoginETT, R.color.mLoginETTHint, R.color.mLTitleT, R.color.mTBTT,
            R.color.mTBIcons, R.style.ThemeOverlay_AppCompat_Light, R.color.mLReme, R.color.mLSUExpTV, R.color.mTBSTT,
            R.layout.spn_dd_2, R.color.spQuaV, R.color.mLRemeBD};
    int[][] nd = new int[][]{daily, nightly};
    Menu myMenu;
    int errorLogin = 0, tDur = 178;
    String gUser, gPass, gName, srTag1 = "login", version = null, ourCloudFolder = "";
    ProgressDialog PD, pDialog;//A tiny progress icon instead of a PD
    EditText[] inputTexts;
    public static String user, pass, perm, name, admin, list, file_url = "app-debug.apk", pasteURL, last_version = "last_version.txt",
            srTag2 = "check", tvType = "androidx.appcompat.widget.AppCompatTextView", ivType = "android.widget.ImageView";
    RequestQueue queue, cfuQueue;
    public static final int progress_bar_type = 0, errorLoginMax = 1, anLoadDelay = 222, exitingDelay = 5220;
    File pasted;
    ViewGroup[] inputBorders;
    private static final int permWriteExerStorUpdate = 522;
    public static boolean mssg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mBody = findViewById(R.id.mBody);
        mMotor = findViewById(R.id.mMotor);
        mToolbar = findViewById(R.id.mToolbar);
        mBG = findViewById(R.id.mBG);
        mBodyLogin = findViewById(R.id.mBodyLogin);
        mLogin = findViewById(R.id.mLogin);
        mLogin2 = findViewById(R.id.mLogin2);
        mLTitle = findViewById(R.id.mLTitle);
        mLUserBorder = findViewById(R.id.mLUserBorder);
        mLUser = findViewById(R.id.mLUser);
        mLPassBorder = findViewById(R.id.mLPassBorder);
        mLPass = findViewById(R.id.mLPass);
        mLReme = findViewById(R.id.mLReme);
        mLSUExpand = findViewById(R.id.mLSUExpand);
        mLSUExpPointer = findViewById(R.id.mLSUExpPointer);
        mLSUExpTV = findViewById(R.id.mLSUExpTV);
        mLSignUpCl = findViewById(R.id.mLSignUpCl);
        mLSignUp = findViewById(R.id.mLSignUp);
        mLRPasBorder = findViewById(R.id.mLRPasBorder);
        mLRPas = findViewById(R.id.mLRPas);
        mLNameBorder = findViewById(R.id.mLNameBorder);
        mLName = findViewById(R.id.mLName);
        mLAdmnBorder = findViewById(R.id.mLAdmnBorder);
        mLAdmnBG = findViewById(R.id.mLAdmnBG);
        mLAdmn = findViewById(R.id.mLAdmn);
        mLBorderBottom = findViewById(R.id.mLBorderBottom);
        mButtons = findViewById(R.id.mButtons);
        mLGo = findViewById(R.id.mLGo);
        mLReset = findViewById(R.id.mLReset);

        tfYekan = Typeface.createFromAsset(getAssets(), "b_yekan.ttf");
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        inputTexts = new EditText[]{mLUser, mLPass, mLRPas, mLName};
        inputBorders = new ViewGroup[]{mLUserBorder, mLPassBorder, mLRPasBorder, mLNameBorder, mLAdmnBorder};
        try {
            PackageInfo pInfo = Main.this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        pasteURL = Environment.getExternalStorageDirectory().toString() + "/app.apk";
        pasted = new File(pasteURL);
        if (pasted.exists()) pasted.delete();
        ourCloudFolder = getResources().getString(R.string.ourCloudFolder);

        setSupportActionBar(mToolbar);
        mToolbarAB = getSupportActionBar();
        //if (mToolbarAB != null) mToolbarAB.setDisplayHomeAsUpEnabled(true);
        for (int g = 0; g < mToolbar.getChildCount(); g++) {
            View getTitle = mToolbar.getChildAt(g);
            if (getTitle.getClass().getName().equalsIgnoreCase(tvType) && ((TextView) getTitle).getText().toString().equals(getResources().getString(R.string.app_name))) {
                TextView title = ((TextView) getTitle);
                title.setTypeface(tfYekan, Typeface.BOLD);
                //title.setTextSize(22f);
            }
            if (getTitle.getClass().getName().equalsIgnoreCase(tvType) && ((TextView) getTitle).getText().toString().equals(getResources().getString(R.string.mtbSubtitle))) {
                TextView title = ((TextView) getTitle);
                title.setTypeface(tfYekan);//, Typeface.BOLD
                //title.setTextSize(22f);
            }
        }

        ValueAnimator anLoad = mVA(mMotor, "translationX", anLoadDelay, 1f, 0f);
        anLoad.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                mLTitle.setTypeface(tfYekan);
                for (EditText inputText : inputTexts) inputText.setTypeface(tfYekan);
                //mLReme.setTypeface(tfYekan);
                //mLSUExpTV.setTypeface(tfYekan);//, Typeface.BOLD

                boolean getNight = sp.getBoolean("night", false);
                if (getNight != night) {
                    night = getNight;
                    night(night);
                }

                /*if (mLSignUpCl.getWidth() == 0) {
                    ValueAnimator anLoadC = mVA(mMotor, "translationX", 4150, 1f, 0f);
                    anLoadC.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            toggleSignUp();
                        }
                    });
                } else {}*/
                toggleSignUp();
            }
        });

        mLGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!loggingIn) {
                    if (!mLUser.getText().toString().equals("") && !mLPass.getText().toString().equals("") &&
                            (!signUp || !mLRPas.getText().toString().equals(""))) {
                        if (!checkValid(mLUser)) {
                            etHighlightRed(mLUser);
                            Toast.makeText(Main.this, R.string.error1, Toast.LENGTH_LONG).show();
                        } else {
                            if (signUp && !mLRPas.getText().toString().equals(mLPass.getText().toString())) {
                                etHighlightRed(mLRPas);
                                Toast.makeText(Main.this, R.string.error6, Toast.LENGTH_LONG).show();
                            } else {
                                etRemoveHighlightRed(mLPass);
                                etRemoveHighlightRed(mLRPas);

                                if (signUp) {
                                    AlertDialog.Builder askUpdate = new AlertDialog.Builder(Main.this)
                                            .setIcon(android.R.drawable.btn_dialog)
                                            .setMessage(R.string.mAskSignUp)
                                            .setPositiveButton(R.string.yes1, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    startDownload();
                                                }
                                            })
                                            .setNegativeButton(R.string.no1, null)
                                            .setCancelable(false);
                                    askUpdate.create().show();
                                } else startDownload();
                            }
                        }
                    } else {
                        if (mLUser.getText().toString().equals("")) etHighlightRed(mLUser);
                        if (mLPass.getText().toString().equals("")) etHighlightRed(mLPass);
                        if (signUp && mLRPas.getText().toString().equals(""))
                            etHighlightRed(mLRPas);
                        Toast.makeText(Main.this, R.string.error2, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        mLReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        for (EditText inputText : inputTexts) {
            inputText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    EditText et = (EditText) view;
                    etRemoveHighlightRed(et);
                    if (et.getId() == R.id.mLUser) {
                        if (et.getText().toString().equals(""))
                            et.setTextDirection(View.TEXT_DIRECTION_RTL);
                        else et.setTextDirection(View.TEXT_DIRECTION_LTR);
                    }
                    /*if (et.getId() == R.id.mLPass || et.getId() == R.id.mLRPas) {
                        if (et.getText().toString().equals("")) et.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                        else et.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                    }*/
                    return false;
                }
            });
        }

        mLReme.setButtonDrawable(changeColor(Main.this, getResources(), R.drawable.checkbox_1_black, daily[16]));
        mLReme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int btnDrawable = R.drawable.checkbox_1_black, btnColour = daily[16];
                if (isChecked) btnDrawable = R.drawable.checkbox_1_black_checked;
                if (night) btnColour = nightly[16];
                buttonView.setButtonDrawable(changeColor(Main.this, getResources(), btnDrawable, btnColour));
            }
        });

        mLSUExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSignUp();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Main.this,
                R.array.mAdmnNames, R.layout.spn_dd_2);//android.R.layout.simple_spinner_item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLAdmn.setAdapter(adapter);
        Drawable spnD = mLAdmn.getBackground();
        if (spnD != null)
            spnD.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Main.this, daily[15]), PorterDuff.Mode.SRC_IN));
        mLAdmn.setBackground(spnD);

        //Improvements for class DownloadFileFromURL
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    protected void onResume() {
        super.onResume();

        mLPass.setText("");
        mLRPas.setText("");
        for (EditText inputText : inputTexts) inputText.clearFocus();

        if (sp.contains(loginSPNames[0]) && sp.contains(loginSPNames[1]) && sp.contains(loginSPNames[3])) {
            if (((Calendar.getInstance().getTimeInMillis() / 1000) - sp.getLong(loginSPNames[3], 0)) < 2592000) {// = A MONTH
                autoLogin = true;
                startDownload();
            } else eraseSavedData(sp);
        } else eraseSavedData(sp);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) queue.cancelAll(srTag1);
        if (cfuQueue != null) cfuQueue.cancelAll(srTag2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_tb, menu);
        myMenu = menu;
        myMenu.getItem(2).setChecked(night);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mtbSignUp:
                toggleSignUp();
                return true;
            case R.id.mtbUpdate:
                if (!checkingFU && version != null) {
                    checkingFU = true;
                    RequestQueue cfuQueue = Volley.newRequestQueue(this);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, ourCloudFolder + last_version,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    checkingFU = false;
                                    if (response != null) {
                                        int curVer = -1, newVer = -1;
                                        try {
                                            curVer = Integer.parseInt(version.replace(".", ""));
                                            newVer = Integer.parseInt(response);
                                        } catch (NumberFormatException ignored) {
                                        }
                                        String[] split = Integer.toString(newVer).split("");
                                        String sNewVer = split[1] + "." + split[2] + "." + split[3],
                                                updateVer1 = getResources().getString(R.string.updateVer1),
                                                updateVer2 = getResources().getString(R.string.updateVer2),
                                                updateVer3 = getResources().getString(R.string.updateVer3);

                                        if (newVer != -1 && curVer != -1 && newVer > curVer) {
                                            AlertDialog.Builder askUpdate = new AlertDialog.Builder(Main.this)
                                                    .setIcon(android.R.drawable.btn_dialog)
                                                    .setMessage(getResources().getString(R.string.askUpdate) +
                                                            updateVer1 + sNewVer + updateVer3 + version + updateVer2)
                                                    .setPositiveButton(R.string.yes1, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            if (pasted.exists()) pasted.delete();

                                                            if (isExternalStorageWritable()) {
                                                                if (ContextCompat.checkSelfPermission(Main.this,
                                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                                        != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= 23) {

                                                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Main.this,
                                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                                                        Toast.makeText(Main.this, R.string.updateExpln, Toast.LENGTH_LONG).show();
                                                                    } else //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                                                        ActivityCompat.requestPermissions(Main.this,
                                                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                                                permWriteExerStorUpdate);
                                                                } else
                                                                    new DownloadFileFromURL().execute(ourCloudFolder + file_url);
                                                            } else
                                                                makeBasicMessage(Main.this, getResources().getString(R.string.error15));
                                                        }
                                                    })
                                                    .setNegativeButton(R.string.no1, null)
                                                    .setCancelable(false);
                                            askUpdate.create().show();
                                        } else if (newVer != -1 && curVer != -1 && newVer == curVer) {
                                            Main.makeBasicMessage(Main.this, getResources().getString(R.string.upToDate) +
                                                    updateVer1 + sNewVer + updateVer2);
                                        } else
                                            makeBasicMessage(Main.this, getResources().getString(R.string.error12) +
                                                    "\n\n" + response);
                                    } else
                                        makeBasicMessage(Main.this, getResources().getString(R.string.error12));
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            checkingFU = false;
                            makeBasicMessage(Main.this, getResources().getString(R.string.error12));
                        }
                    });
                    stringRequest.setTag(srTag2);
                    cfuQueue.add(stringRequest);
                }
                return true;
            case R.id.mtbNight:
                night = !night;//item.setChecked(night);
                night(night);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("night", night);
                editor.apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {//super.onBackPressed();
        if (exiting) {
            moveTaskToBack(true);//REQUIRED!
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } else {
            exiting = true;
            Toast.makeText(Main.this, getResources().getText(R.string.toExit), Toast.LENGTH_SHORT).show();
            ValueAnimator anExit = Main.mVA(mMotor, "translationY", exitingDelay, 111f, 0f);
            anExit.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    exiting = false;
                }
            });
        }
        /*exit(Main.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);//REQUIRED!
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });*/
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage(getResources().getString(R.string.updating));
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case permWriteExerStorUpdate: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    new DownloadFileFromURL().execute(ourCloudFolder + file_url);
                } else {
                    makeBasicMessage(Main.this, getResources().getString(R.string.error16));
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                //return;
            }
        } // other 'case' lines to check for other permissions this app might request.
    }


    public static ValueAnimator mVA(View v, String prop, int dur, float val1, float val2) {
        final ValueAnimator mValueAnimator1 = ObjectAnimator.ofFloat(v, prop, val1, val2).setDuration(dur);
        mValueAnimator1.start();
        return mValueAnimator1;
    }

    public static ObjectAnimator mOA(View v, String prop, float value, int dur) {
        ObjectAnimator mAnim = ObjectAnimator.ofFloat(v, prop, value).setDuration(dur);
        mAnim.start();
        return mAnim;
    }

    public static Drawable changeColor(Context c, Resources r, int drw, int colour) {
        Bitmap bmIcon = BitmapFactory.decodeResource(r, drw).copy(Bitmap.Config.ARGB_8888, true);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(c, colour),
                PorterDuff.Mode.SRC_IN));
        Canvas canvas = new Canvas(bmIcon);
        canvas.drawBitmap(bmIcon, 0, 0, paint);
        return new BitmapDrawable(r, bmIcon);
    }

    boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    void startDownload() {
        if (isOnline()) {
            downloadChanges(true);
            String sSignUp = "", url;
            boolean trueName = true, truePass = true;
            if (!autoLogin && signUp) {
                try {
                    sSignUp = "&name=" + URLEncoder.encode(gName, "utf-8") + "&admn=" +
                            getResources().getStringArray(R.array.mAdmnUsers)[mLAdmn.getSelectedItemPosition()];
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    trueName = false;
                }
            }
            String ggPass = "";
            try {
                ggPass = URLEncoder.encode(gPass, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                truePass = false;
            }
            url = ourCloudFolder + "login.php?user=" + gUser + "&pass=" + ggPass + sSignUp;

            if (trueName && truePass) {
                queue = Volley.newRequestQueue(this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                finishDownloading();
                                if (response != null) {
                                    if (response.length() > 67 && response.substring(62, 64).equals("go")) {
                                        if (gReme) {
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putString(loginSPNames[0], gUser);
                                            editor.putString(loginSPNames[1], gPass);
                                            editor.putLong(loginSPNames[3], Calendar.getInstance().getTimeInMillis() / 1000);
                                            editor.apply();
                                        } else if (!autoLogin) eraseSavedData(sp);

                                        user = gUser;
                                        pass = gPass;
                                        java.lang.Class dest = null;
                                        switch (response.substring(64, 67)) {
                                            case "Usr":
                                                dest = Form.class;
                                                if (response.length() >= 81)
                                                    perm = response.substring(67, 81);
                                                if (response.length() > (81 + 14))
                                                    name = fixEncoding(response.substring(81, response.length() - 14));//19
                                                admin = getResources().getStringArray(R.array.mAdmnUsers)[0];//FIX THIS...
                                                mssg = response.substring(response.length() - 12, response.length() - 11).equals("B");
                                                break;
                                            case "Adm":
                                                dest = Admin.class;
                                                if (response.length() >= 81)
                                                    list = fixEncoding(response.substring(67, response.length() - 14));
                                                break;
                                        }
                                        if (dest != null) {
                                            Intent go = new Intent(Main.this, dest);
                                            startActivity(go);
                                        }
                                        downloadChanges(false);
                                    } else if (response.equals("notFound")) {
                                        if (!autoLogin)
                                            makeBasicMessage(Main.this, getResources().getString(R.string.error5));
                                        else eraseSavedData(sp);
                                        downloadChanges(false);
                                    } else if (response.equals("alreadyExists")) {
                                        if (!autoLogin)
                                            makeBasicMessage(Main.this, getResources().getString(R.string.error7));
                                        else eraseSavedData(sp);
                                        downloadChanges(false);
                                    } else if (response.equals("waiting")) {
                                        makeBasicMessage(Main.this, getResources().getString(R.string.error18));
                                        downloadChanges(false);
                                        reset();
                                        if (signUp) toggleSignUp();
                                    } else errorLogin(response);//if (!autoLogin)
                                } else errorLogin(null);
                            }
                        }, new Response.ErrorListener() {
                    @Override//IK HOU VAN YE!!!
                    public void onErrorResponse(VolleyError error) {
                        finishDownloading();
                        errorLogin(null);
                    }
                });
                stringRequest.setTag(srTag1);
                queue.add(stringRequest);
                loggingIn = true;
            } else {
                if (!trueName) {
                    etHighlightRed(mLName);
                    Toast.makeText(Main.this, R.string.error11, Toast.LENGTH_LONG).show();
                }
                if (!truePass) {
                    etHighlightRed(mLPass);
                    etHighlightRed(mLRPas);
                    Toast.makeText(Main.this, R.string.error13, Toast.LENGTH_LONG).show();
                }
            }
        } else Toast.makeText(Main.this, R.string.error3, Toast.LENGTH_LONG).show();
    }

    public static boolean checkValid(EditText et) {
        if (et != null && !et.getText().toString().equals("")) {
            String text = et.getText().toString();
            String[] split = text.split("");
            StringBuilder valid = new StringBuilder();
            for (int c = 0; c < split.length; c++) {
                boolean foundValid = false;
                for (int v = 0; v < validChars.length; v++)
                    if (split[c].equals(validChars[v])) foundValid = true;
                if (foundValid) valid.append(split[c]);
            }//et.setText(valid.toString());
            return text.equals(valid.toString());
        } else return false;
    }

    void night(boolean n) {
        if (myMenu != null) myMenu.getItem(2).setChecked(night);
        int[] colours;
        if (n) colours = nd[1];
        else colours = nd[0];
        mBody.setBackgroundColor(ContextCompat.getColor(Main.this, colours[0]));
        mLogin.setBackgroundColor(ContextCompat.getColor(Main.this, colours[1]));
        mLogin2.setBackgroundColor(ContextCompat.getColor(Main.this, colours[2]));
        /*mLTitle.setTextColor(ContextCompat.getColor(Main.this, colours[7]));
        mToolbar.setTitleTextColor(ContextCompat.getColor(Main.this, colours[8]));
        mToolbar.setSubtitleTextColor(ContextCompat.getColor(Main.this, colours[13]));
        Drawable d = mToolbar.getOverflowIcon();
        if (d != null)
            d.setColorFilter(ContextCompat.getColor(this, colours[9]), PorterDuff.Mode.SRC_ATOP);*/
        //mToolbar.setPopupTheme(colours[10]);

        for (ViewGroup inputBorder : inputBorders)
            inputBorder.setBackgroundColor(ContextCompat.getColor(Main.this, colours[3]));
        mLBorderBottom.setBackgroundColor(ContextCompat.getColor(Main.this, colours[3]));
        for (EditText inputText : inputTexts) {
            inputText.setBackgroundColor(ContextCompat.getColor(Main.this, colours[4]));
            inputText.setTextColor(ContextCompat.getColor(Main.this, colours[5]));
            inputText.setHintTextColor(ContextCompat.getColor(Main.this, colours[6]));
        }
        int btnDrawable = R.drawable.checkbox_1_black;
        if (mLReme.isChecked()) btnDrawable = R.drawable.checkbox_1_black_checked;
        mLReme.setButtonDrawable(changeColor(Main.this, getResources(), btnDrawable, colours[16]));
        mLReme.setTextColor(ContextCompat.getColor(Main.this, colours[11]));
        mLSUExpTV.setTextColor(ContextCompat.getColor(Main.this, colours[12]));

        mLAdmnBG.setBackgroundColor(ContextCompat.getColor(Main.this, colours[4]));
        int selected = mLAdmn.getSelectedItemPosition();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Main.this,
                R.array.mAdmnNames, colours[14]);//android.R.layout.simple_spinner_item
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLAdmn.setAdapter(adapter);
        mLAdmn.setSelection(selected);
        Drawable spnD = mLAdmn.getBackground();
        if (spnD != null)
            spnD.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Main.this, colours[15]), PorterDuff.Mode.SRC_IN));
        mLAdmn.setBackground(spnD);
    }

    public static void exit(Context c, DialogInterface.OnClickListener click) {
        AlertDialog.Builder exit = new AlertDialog.Builder(c)
                .setIcon(android.R.drawable.btn_dialog)
                .setMessage(R.string.exit)
                .setPositiveButton(R.string.yes1, click)
                .setNegativeButton(R.string.no1, null)
                .setCancelable(false);
        exit.create().show();
    }

    void runPD() {
        PD = new ProgressDialog(Main.this);
        PD.setCancelable(false);
        PD.setMessage(getResources().getString(R.string.wait1));
        PD.setButton(getResources().getText(R.string.cancel1), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                downloadChanges(false);
                dialogInterface.dismiss();
                errorLogin = 0;
            }
        });
        PD.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finishDownloading();
                PD = null;
            }
        });
        PD.show();
    }

    void toggleSignUp() {
        signUp = !signUp;
        if (myMenu != null) myMenu.getItem(0).setChecked(signUp);
        if (signUp) {
            mLTitle.setText(R.string.mLTitleSU);
            mOA(mLSUExpPointer, "rotation", -90f, tDur);
            mOA(mLSignUp, "translationY", 0f, tDur);
            ViewGroup.LayoutParams mLSignUpClLP = mLSignUpCl.getLayoutParams();//Causes error, if not loaded before 178ms!!!
            mLSignUpClLP.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mLSignUpCl.setLayoutParams(mLSignUpClLP);
        } else {
            mLTitle.setText(R.string.mLTitle);
            mOA(mLSUExpPointer, "rotation", 0f, tDur);
            ValueAnimator closeAnim = mOA(mLSignUp, "translationY",
                    (float) (mLSignUp.getHeight() - (mLSignUp.getHeight() * 2)), tDur);
            closeAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ViewGroup.LayoutParams mLSignUpClLP = mLSignUpCl.getLayoutParams();
                    mLSignUpClLP.height = 0;
                    mLSignUpCl.setLayoutParams(mLSignUpClLP);
                }
            });
        }
    }

    void etHighlightRed(EditText et) {
        int etColour;
        if (!night) etColour = R.color.etError1;
        else etColour = R.color.etError1N;
        et.setBackgroundColor(ContextCompat.getColor(Main.this, etColour));
    }

    void etRemoveHighlightRed(EditText et) {
        int etColour;
        if (!night) etColour = R.color.mLoginETV;
        else etColour = R.color.mLoginETVN;
        et.setBackgroundColor(ContextCompat.getColor(Main.this, etColour));
    }

    void errorLogin(String res) {
        errorLogin += 1;
        downloadChanges(false);
        if (errorLogin < errorLoginMax) {
            startDownload();
        } else {
            errorLogin = 0;
            if (autoLogin) {
                autoLogin = false;
                mLUser.setText(sp.getString(loginSPNames[0], ""));
                mLPass.setText(sp.getString(loginSPNames[1], ""));
                mLReme.setChecked(true);
            }
            String RES = "";
            if (res != null) RES = "\n\n" + res;
            makeBasicMessage(Main.this, getResources().getString(R.string.error4) + RES);
        }
    }

    void downloadChanges(boolean start) {
        if (start) {
            if (!autoLogin) {
                gUser = mLUser.getText().toString();
                gPass = mLPass.getText().toString();
                gName = mLName.getText().toString();
                gReme = mLReme.isChecked();
            } else {
                gUser = sp.getString(loginSPNames[0], "");
                gPass = sp.getString(loginSPNames[1], "");
                gReme = false;
            }
            for (EditText inputText : inputTexts) inputText.setEnabled(false);
            mLReme.setEnabled(false);
            runPD();
        } else {
            gUser = "";
            gPass = "";
            if (!autoLogin) gName = "";
            gReme = false;
            for (EditText inputText : inputTexts) inputText.setEnabled(true);
            mLReme.setEnabled(true);
            if (PD != null && PD.isShowing()) {
                PD.dismiss();
                PD = null;
            } else if (PD != null) PD = null;
        }
    }

    public static void eraseSavedData(SharedPreferences SP) {
        SharedPreferences.Editor editor = SP.edit();
        if (SP.contains(loginSPNames[0])) editor.remove(loginSPNames[0]);
        if (SP.contains(loginSPNames[1])) editor.remove(loginSPNames[1]);
        //if (SP.contains(loginSPNames[2])) editor.remove(loginSPNames[2]);
        if (SP.contains(loginSPNames[3])) editor.remove(loginSPNames[3]);
        if (SP.contains(loginSPNames[4])) editor.remove(loginSPNames[4]);
        editor.apply();
    }

    void finishDownloading() {
        if (queue != null) queue.cancelAll(srTag1);
        loggingIn = false;
    }

    public static String fixEncoding(String response) {
        try {
            byte[] u = response.toString().getBytes(
                    "ISO-8859-1");
            response = new String(u, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public static void makeBasicMessage(Context c, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c)
                .setMessage(message)
                .setNeutralButton(R.string.ok1, null)
                .setCancelable(true);//.setCustomTheme(View)
        builder.create().show();
    }

    public static void signOut(SharedPreferences SP) {
        Main.eraseSavedData(SP);
        Main.user = null;
        Main.pass = null;
        Main.perm = null;
        Main.name = null;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    void reset() {
        int etColour;
        if (!night) etColour = R.color.mLoginETV;
        else etColour = R.color.mLoginETVN;
        for (EditText inputText : inputTexts) {
            inputText.setBackgroundColor(ContextCompat.getColor(Main.this, etColour));
            inputText.setText("");
        }
        mLReme.setChecked(false);
    }


    class DownloadFileFromURL extends AsyncTask<String, String, String> {//Background Async Task to download file

        @Override//Before starting background thread Show Progress Bar Dialog
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {//Downloading file in background thread
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                int lenghtOfFile = conection.getContentLength();//this will be useful so that you can show a tipical 0-100% progress bar
                InputStream input = new BufferedInputStream(url.openStream(), 8192);//download the file
                OutputStream output = new FileOutputStream(pasteURL);

                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;// After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);//writing data to file
                }

                output.flush();// flushing output

                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            pDialog.setProgress(Integer.parseInt(progress[0]));// setting progress percentage
        }

        @Override//After completing background task Dismiss the progress dialog
        protected void onPostExecute(String file_url) {
            dismissDialog(progress_bar_type);// dismiss the dialog after the file was downloaded

            if (pasted.exists()) {
                Intent promptInstall = new Intent(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= 24) {
                    promptInstall.setDataAndType(FileProvider.getUriForFile(Main.this,
                            BuildConfig.APPLICATION_ID + ".provider", pasted),
                            "application/vnd.android.package-archive");
                    List<ResolveInfo> resInfoList = Main.this.getPackageManager().queryIntentActivities(promptInstall,
                            PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        Main.this.grantUriPermission(resolveInfo.activityInfo.packageName, Uri.parse(pasted.getPath()),
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    promptInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                } else {
                    promptInstall.setDataAndType(Uri.fromFile(pasted), "application/vnd.android.package-archive");
                    promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(promptInstall);
            } else makeBasicMessage(Main.this, getResources().getString(R.string.error14));
        }

    }
}

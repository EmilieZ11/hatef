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
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Admin extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, PopupMenu.OnDismissListener {
    ConstraintLayout aBody, aBody2;
    View aMotor, aBG;
    Toolbar aToolbar;
    ScrollView aSV, aSV2;
    LinearLayout aLL, aLL2;

    ActionBar aToolbarAB;
    Typeface tfYekan;
    SharedPreferences sp;
    boolean night = false, checkingFU = false, showingUser = false, gettingList = false, exiting = false;
    Menu myMenu;
    int[] nightly = new int[]{R.color.mTBTTN, R.color.mTBIconsN, R.style.ThemeOverlay_AppCompat_Dark, R.color.mBody2N, R.color.mTBNavN},
            daily = new int[]{R.color.mTBTT, R.color.mTBIcons, R.style.ThemeOverlay_AppCompat_Light, R.color.mBody2, R.color.mTBNav};
    int[][] nd = new int[][]{daily, nightly};
    String version = null, ourCloudFolder = "", srTagList = "getList", srTagList3 = "refresh", cloudAdmin = "admin.php";
    RequestQueue cfuQueue, listQueue, refreshQueue;
    File pasted;
    public static final int progress_bar_type = 0;
    ProgressDialog pDialog;
    ArrayList<User> users;
    ArrayList<Integer> clUsrIds, clWekIds, clWktIds, clScrIds, tvScrIds, ivToolsIds, tvUNewIds, tvWNewIds;
    int showUserDur = 261, maxScore, iUser = -1, rbcDisabled = 126, menuMessenger = 0, menuUpdateList = 1, menuDelete = 2, menuNight = 3,
            vUsrBGDur = 630, resumed = 0, morSelected = -1, clUBGPos;
    ArrayList<Week> weeks;
    String[] lessons, buttons, months;
    private static final int permWriteExerStorUpdate = 522;
    float vUsrBGAlpha = 0.78f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        aBody = findViewById(R.id.aBody);
        aMotor = findViewById(R.id.aMotor);
        aToolbar = findViewById(R.id.aToolbar);
        aBG = findViewById(R.id.aBG);
        aBody2 = findViewById(R.id.aBody2);
        aSV2 = findViewById(R.id.aSV2);
        aLL2 = findViewById(R.id.aLL2);
        aSV = findViewById(R.id.aSV);
        aLL = findViewById(R.id.aLL);

        tfYekan = Typeface.createFromAsset(getAssets(), "b_yekan.ttf");
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            PackageInfo pInfo = Admin.this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        Main.pasteURL = Environment.getExternalStorageDirectory().toString() + "/app.apk";
        pasted = new File(Main.pasteURL);
        if (pasted.exists()) pasted.delete();
        ourCloudFolder = getResources().getString(R.string.ourCloudFolder);
        lessons = getResources().getStringArray(R.array.lessons);
        buttons = getResources().getStringArray(R.array.buttons);
        maxScore = lessons.length * (buttons.length - 1);
        months = getResources().getStringArray(R.array.months);

        setSupportActionBar(aToolbar);
        aToolbarAB = getSupportActionBar();
        //if (aToolbarAB != null) mToolbarAB.setDisplayHomeAsUpEnabled(true);
        for (int g = 0; g < aToolbar.getChildCount(); g++) {
            View getTitle = aToolbar.getChildAt(g);
            if (getTitle.getClass().getName().equalsIgnoreCase(Main.tvType)
                    && ((TextView) getTitle).getText().toString().equals(getResources().getString(R.string.app_name))) {
                TextView title = ((TextView) getTitle);
                title.setTypeface(tfYekan, Typeface.BOLD);
            }
            if (getTitle.getClass().getName().equalsIgnoreCase(Main.tvType) &&
                    ((TextView) getTitle).getText().toString().equals(getResources().getString(R.string.atbSubtitle))) {
                TextView title = ((TextView) getTitle);
                title.setTypeface(tfYekan);//, Typeface.BOLD
                //title.setTextSize(22f);
            }
        }
        if (aToolbarAB != null) {
            aToolbarAB.setDisplayHomeAsUpEnabled(false);
            aToolbarAB.setDisplayShowHomeEnabled(false);
        }

        ValueAnimator anLoad = Main.mVA(aMotor, "translationX", Main.anLoadDelay, 1f, 0f);
        anLoad.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                if (Main.list != null) arrangeUsers(Main.list);
            }
        });

        aToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveScene(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Main.user == null || Main.pass == null || Main.list == null || Main.user.equals("") || Main.pass.equals("")) {
            Toast.makeText(Admin.this, R.string.error8, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Main.class));

            if (resumed != 0) refresh();
            resumed += 1;
        }

        ValueAnimator anLoad = Main.mVA(aMotor, "translationX", Main.anLoadDelay, 1f, 0f);
        anLoad.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                boolean getNight = sp.getBoolean("night", false);
                if (getNight != night) {
                    night = getNight;
                    night(night);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cfuQueue != null) cfuQueue.cancelAll(Main.srTag2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_tb, menu);
        myMenu = menu;

        myMenu.getItem(menuMessenger).setIcon(Main.changeColor(Admin.this, getResources(), R.drawable.messenger_1_black,
                R.color.tbMessengerIcon));
        iconAlpha(myMenu, menuMessenger, rbcDisabled);
        myMenu.getItem(menuUpdateList).setIcon(Main.changeColor(Admin.this, getResources(), R.drawable.reload_1_gray,
                R.color.aUpdateList));
        iconAlpha(myMenu, menuUpdateList, 255);
        myMenu.getItem(menuDelete).setIcon(Main.changeColor(Admin.this, getResources(), R.drawable.recycle_bin_1_black,
                R.color.aDeleteUser));
        iconAlpha(myMenu, menuDelete, rbcDisabled);
        myMenu.getItem(menuNight).setChecked(night);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atbUpdateList:
                refresh();
                return true;
            case R.id.atbDeleteUser:
                final ConstraintLayout V = Form.messBuilder(Admin.this, night, "", 1);
                AlertDialog.Builder dlt = new AlertDialog.Builder(Admin.this)
                        .setTitle(R.string.aDeleteUserT)
                        .setMessage(R.string.aDeleteUser)
                        .setView(V)
                        .setPositiveButton(R.string.yes1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText et = (EditText) V.getChildAt(0);
                                if (et.getText().toString().equals(Main.pass)) {
                                    if (iUser != -1) action("drop_" + users.get(iUser).user);
                                    else
                                        Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError4));
                                } else
                                    Main.makeBasicMessage(Admin.this, getResources().getString(R.string.error17));
                            }
                        })
                        .setNegativeButton(R.string.no1, null);
                dlt.create().show();
                return true;
            case R.id.atbMessenger:
                if (iUser != -1)
                    startActivity(new Intent(Admin.this, Messenger.class)
                            .putExtra("user", Main.user)
                            .putExtra("pass", Main.pass)
                            .putExtra("type", "adm")
                            .putExtra("to", users.get(iUser).user)
                    );
                return true;
            case R.id.atbNight:
                night = !night;
                night(night);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("night", night);
                editor.apply();
                return true;
            case R.id.atbUpdate:
                if (!checkingFU && version != null) {
                    checkingFU = true;
                    cfuQueue = Volley.newRequestQueue(this);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, ourCloudFolder + Main.last_version,
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
                                            AlertDialog.Builder askUpdate = new AlertDialog.Builder(Admin.this)
                                                    .setIcon(android.R.drawable.btn_dialog)
                                                    .setMessage(getResources().getString(R.string.askUpdate) +
                                                            updateVer1 + sNewVer + updateVer3 + version + updateVer2)
                                                    .setPositiveButton(R.string.yes1, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            if (pasted.exists()) pasted.delete();

                                                            if (Main.isExternalStorageWritable()) {
                                                                if (ContextCompat.checkSelfPermission(Admin.this,
                                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                                        != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= 23) {

                                                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Admin.this,
                                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                                                        Toast.makeText(Admin.this, R.string.updateExpln, Toast.LENGTH_LONG).show();
                                                                    } else
                                                                        ActivityCompat.requestPermissions(Admin.this,
                                                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                                                permWriteExerStorUpdate);
                                                                } else
                                                                    new DownloadFileFromURL().execute(ourCloudFolder + Main.file_url);
                                                            } else
                                                                Main.makeBasicMessage(Admin.this, getResources().getString(R.string.error15));
                                                        }
                                                    })
                                                    .setNegativeButton(R.string.no1, null)
                                                    .setCancelable(false);
                                            askUpdate.create().show();
                                        } else if (newVer != -1 && curVer != -1 && newVer == curVer) {
                                            Main.makeBasicMessage(Admin.this, getResources().getString(R.string.upToDate) +
                                                    updateVer1 + sNewVer + updateVer2);
                                        } else
                                            Main.makeBasicMessage(Admin.this, getResources().getString(R.string.error12) +
                                                    "\n\n" + response);
                                    } else
                                        Main.makeBasicMessage(Admin.this, getResources().getString(R.string.error12));
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            checkingFU = false;
                            Main.makeBasicMessage(Admin.this, getResources().getString(R.string.error12));
                        }
                    });
                    stringRequest.setTag(Main.srTag2);
                    cfuQueue.add(stringRequest);
                }
                return true;
            case R.id.atbLogOut:
                AlertDialog.Builder ask = new AlertDialog.Builder(Admin.this)
                        .setMessage(R.string.exitAcc)
                        .setPositiveButton(R.string.yes1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Main.eraseSavedData(sp);
                                Main.user = null;
                                Main.pass = null;
                                startActivity(new Intent(Admin.this, Main.class));
                            }
                        })
                        .setNegativeButton(R.string.no1, null)
                        .setCancelable(false);
                ask.create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {//super.onBackPressed();
        if (!showingUser) {
            if (exiting) {
                moveTaskToBack(true);//REQUIRED!
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            } else {
                exiting = true;
                Toast.makeText(Admin.this, getResources().getText(R.string.toExit), Toast.LENGTH_SHORT).show();
                ValueAnimator anExit = Main.mVA(aMotor, "translationY", Main.exitingDelay, 111f, 0f);
                anExit.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        exiting = false;
                    }
                });
            }
            /*Main.exit(Admin.this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    moveTaskToBack(true);//REQUIRED!
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });*/
        } else moveScene(false);
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    new DownloadFileFromURL().execute(ourCloudFolder + Main.file_url);
                else Main.makeBasicMessage(Admin.this, getResources().getString(R.string.error16));
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int i = morSelected;
        morSelected = -1;
        switch (item.getItemId()) {
            case R.id.amorInfo:
                String DATE;
                try {
                    String[] date = users.get(i).date.split("-");
                    DATE = date[2] + " " + months[Integer.parseInt(date[1]) - 1] + " " + date[0];
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    DATE = users.get(i).date;
                }
                Main.makeBasicMessage(Admin.this,
                        "نام: " + users.get(i).name + "\n" +
                                "نام کاربری: " + users.get(i).user + "\n" +
                                "رمز عبور: " + users.get(i).pass + "\n" +
                                "تاریخ ثبت نام: " + DATE);
                return true;
            case R.id.amorDelete:
                if (users.get(i).verf) {
                    final ConstraintLayout V = Form.messBuilder(Admin.this, night, "", 1);
                    final int II = i;
                    AlertDialog.Builder dlt = new AlertDialog.Builder(Admin.this)
                            .setTitle(R.string.aDeleteUserT)
                            .setMessage(R.string.aDeleteUser)
                            .setView(V)
                            .setPositiveButton(R.string.yes1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    EditText et = (EditText) V.getChildAt(0);
                                    if (et.getText().toString().equals(Main.pass))
                                        action("drop_" + users.get(II).user);
                                    else
                                        Main.makeBasicMessage(Admin.this, getResources().getString(R.string.error17));
                                }
                            })
                            .setNegativeButton(R.string.no1, null);
                    dlt.create().show();
                } else action("drop_" + users.get(i).user);
                return true;
            case R.id.amorChat:
                if (i != -1)
                    startActivity(new Intent(Admin.this, Messenger.class)
                            .putExtra("user", Main.user)
                            .putExtra("pass", Main.pass)
                            .putExtra("type", "adm")
                            .putExtra("to", users.get(i).user)
                    );
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDismiss(PopupMenu popupMenu) {
        morSelected = -1;
    }


    void arrangeUsers(String list) {
        aLL.removeAllViews();

        String[] firstSplit = list.split(" @@@ "),
                splitUsers = firstSplit[0].split("/"),
                splitNames = firstSplit[1].split("/q-v-d-z/"),
                splitPasse = firstSplit[2].split("/q--v--d--z/"),
                splitRecse = firstSplit[3].split("q"),
                splitDates = firstSplit[4].split("qvdz"),
                splitMssgs = firstSplit[5].split("q"),
                splitVerfs = firstSplit[6].split(""), //REQUIRES A "+ 1" IN POSITION!
                splitNewss = firstSplit[7].split(""),
                splitSumss = firstSplit[8].split("q");
        users = new ArrayList<>();
        for (int s = 0; s < splitUsers.length; s++)
            users.add(new User(
                    splitUsers[s],
                    splitNames[s],
                    splitPasse[s],
                    Integer.parseInt(splitRecse[s]),
                    splitDates[s],
                    Integer.parseInt(splitMssgs[s]),
                    splitVerfs[s + 1].equals("1"),
                    splitNewss[s + 1].equals("1"),
                    Integer.parseInt(splitSumss[s])));

        Collections.sort(users, new sortByScore());

        clUsrIds = new ArrayList<>();
        ivToolsIds = new ArrayList<>();
        tvUNewIds = new ArrayList<>();
        for (int u = 0; u < users.size(); u++) {
            clUsrIds.add(View.generateViewId());
            ivToolsIds.add(View.generateViewId());
            tvUNewIds.add(View.generateViewId());
            int tvUsrMar = (int) (aToolbar.getHeight() / 5.22), ivToolsWH = (int) (tvUsrMar * 3.33), ivToolsPad = (int) (tvUsrMar / 2.22),
                    clUsrMar = (int) (tvUsrMar / 2), tvNewMar = (int) (aToolbar.getHeight() / 73.2);
            ;

            ConstraintLayout clUsr = new ConstraintLayout(Admin.this);
            LinearLayout.LayoutParams clUsrLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            clUsrLP.setMargins(0, clUsrMar, 0, clUsrMar);
            clUsr.setId(clUsrIds.get(u));
            if (users.get(u).verf) clUsr.setBackground(ContextCompat.getDrawable(Admin.this,
                    R.drawable.square_1_orange_to_darkorange_xml));
            else clUsr.setBackgroundColor(ContextCompat.getColor(Admin.this, R.color.aUsrNotVerf));
            aLL.addView(clUsr, clUsrLP);

            View vUsrBG = new View(Admin.this);
            ConstraintLayout.LayoutParams vUsrBGLP = new ConstraintLayout.LayoutParams(0, 0);
            vUsrBGLP.startToStart = clUsrIds.get(u);
            vUsrBGLP.topToTop = clUsrIds.get(u);
            vUsrBGLP.endToEnd = clUsrIds.get(u);
            vUsrBGLP.bottomToBottom = clUsrIds.get(u);
            vUsrBG.setBackgroundColor(ContextCompat.getColor(Admin.this, R.color.aVUsrBG));
            vUsrBG.setAlpha(0f);
            clUsr.addView(vUsrBG, vUsrBGLP);
            if (users.get(u).mssg > 0) msgAlarm(vUsrBG);

            clUBGPos = 1;
            ConstraintLayout clUBG = new ConstraintLayout(Admin.this);
            ConstraintLayout.LayoutParams clUBGLP = new ConstraintLayout.LayoutParams(0, 0);
            clUBGLP.startToStart = clUsrIds.get(u);
            clUBGLP.topToTop = clUsrIds.get(u);
            clUBGLP.endToEnd = clUsrIds.get(u);
            clUBGLP.bottomToBottom = clUsrIds.get(u);
            clUsr.addView(clUBG, clUBGLP);

            ImageView ivUGlance = new ImageView(Admin.this);
            ConstraintLayout.LayoutParams ivUGlanceLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ivUGlance.setImageResource(R.drawable.glance_1_white);
            ivUGlance.setVisibility(View.INVISIBLE);
            clUBG.addView(ivUGlance, ivUGlanceLP);

            if (users.get(u).news) {
                shine(ivUGlance, aLL2.getWidth(), true, View.generateViewId(), 1, true, 19f);

                TextView tvNew = new TextView(new ContextThemeWrapper(Admin.this, R.style.aTvNew), null, 0);
                ConstraintLayout.LayoutParams tvNewLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                tvNewLP.topToTop = clUsrIds.get(u);
                tvNewLP.endToEnd = clUsrIds.get(u);
                tvNew.setPaddingRelative(0, tvNewMar, tvNewMar * 9, 0);
                tvNew.setText(R.string.aTvNew);
                tvNew.setRotation(-5f);
                tvNew.setTextColor(ContextCompat.getColor(Admin.this, R.color.aTvNew));
                tvNew.setTypeface(tfYekan);
                tvNew.setId(tvUNewIds.get(u));
                clUsr.addView(tvNew, tvNewLP);
            }

            LinearLayout llContent = new LinearLayout(Admin.this);
            ConstraintLayout.LayoutParams llContentLP = new ConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            llContentLP.startToStart = clUsrIds.get(u);
            llContentLP.topToTop = clUsrIds.get(u);
            llContentLP.endToStart = ivToolsIds.get(u);
            llContentLP.bottomToBottom = clUsrIds.get(u);
            llContentLP.setMargins(0, tvUsrMar, 0, tvUsrMar);
            llContentLP.setMarginStart(tvUsrMar);
            llContent.setOrientation(LinearLayout.VERTICAL);
            clUsr.addView(llContent, llContentLP);

            TextView tvUsr = new TextView(new ContextThemeWrapper(Admin.this, R.style.aTvUsr), null, 0);
            LinearLayout.LayoutParams tvUsrLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            tvUsr.setText((u + 1) + getResources().getString(R.string.aTvUsrSep) + users.get(u).name +
                    getResources().getString(R.string.aTvUsrBfUsr) + users.get(u).user + getResources().getString(R.string.aTvUsrAfUsr));
            tvUsr.setTypeface(tfYekan, Typeface.BOLD);
            llContent.addView(tvUsr, tvUsrLP);

            if (users.get(u).verf) {
                TextView tvPrp = new TextView(new ContextThemeWrapper(Admin.this, R.style.aTvPrp), null, 0);
                LinearLayout.LayoutParams tvPrpLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                tvPrp.setText(users.get(u).recs + getResources().getString(R.string.aRecords));
                tvPrp.setTypeface(tfYekan);
                tvPrp.setPaddingRelative(tvUsrMar, 0, 0, 0);
                llContent.addView(tvPrp, tvPrpLP);

                TextView tvScr = new TextView(new ContextThemeWrapper(Admin.this, R.style.aTvPrp), null, 0);
                LinearLayout.LayoutParams tvScrLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                tvScr.setText(getResources().getString(R.string.aScores) + users.get(u).sums);
                tvScr.setTypeface(tfYekan);
                tvScr.setPaddingRelative(tvUsrMar, 0, 0, 0);
                llContent.addView(tvScr, tvScrLP);
            }

            ImageView ivTools = new ImageView(Admin.this);
            ConstraintLayout.LayoutParams ivToolsLP = new ConstraintLayout.LayoutParams(ivToolsWH, ivToolsWH);
            ivToolsLP.topToTop = clUsrIds.get(u);
            ivToolsLP.bottomToBottom = clUsrIds.get(u);
            ivToolsLP.endToEnd = clUsrIds.get(u);
            ivToolsLP.setMarginEnd((int) (llContentLP.getMarginStart() / 2));
            ivTools.setImageResource(R.drawable.tools_2);
            ivTools.setColorFilter(ContextCompat.getColor(Admin.this, R.color.aUClT), PorterDuff.Mode.SRC_ATOP);
            ivTools.setBackground(ContextCompat.getDrawable(Admin.this, R.drawable.square_1_tp_to_white_alpha_xml));//IK HOU VAN YE!!!
            ivTools.setPaddingRelative(ivToolsPad, ivToolsPad, ivToolsPad, ivToolsPad);
            ivTools.setId(ivToolsIds.get(u));
            clUsr.addView(ivTools, ivToolsLP);
            final int U = u;
            ivTools.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMore(view, U);
                }
            });

            clUsr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (users.get(U).verf) {
                        if (!gettingList && !showingUser) getList(U);
                    } else action("verf_" + users.get(U).user);
                }
            });
        }
    }

    void night(boolean n) {
        if (myMenu != null) myMenu.getItem(menuNight).setChecked(night);
        int[] colours;
        if (n) {
            colours = nd[1];
            aToolbar.setNavigationIcon(Main.changeColor(Admin.this, getResources(), R.drawable.back_3_white, colours[4]));
        } else {
            colours = nd[0];
            aToolbar.setNavigationIcon(Main.changeColor(Admin.this, getResources(), R.drawable.back_3_white, colours[4]));
        }
        aBody.setBackgroundColor(ContextCompat.getColor(Admin.this, colours[3]));
        /*aToolbar.setTitleTextColor(ContextCompat.getColor(Admin.this, colours[0]));
        Drawable d = aToolbar.getOverflowIcon();
        if (d != null)
            d.setColorFilter(ContextCompat.getColor(this, colours[1]), PorterDuff.Mode.SRC_ATOP);*/
        //mToolbar.setPopupTheme(colours[2]);
    }

    void backBut(boolean show) {
        if (aToolbarAB != null) {
            aToolbarAB.setDisplayHomeAsUpEnabled(show);
            if (show) {
                int navCol;
                if (night) navCol = nightly[4];
                else navCol = daily[4];
                aToolbar.setNavigationIcon(Main.changeColor(Admin.this, getResources(), R.drawable.back_3_white, navCol));
            }
        }
    }

    void getList(final int USER) {
        if (isOnline()) {
            listQueue = Volley.newRequestQueue(Admin.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ourCloudFolder + cloudAdmin +
                    "?user=" + Main.user + "&pass=" + Main.pass + "&act=show_" + users.get(USER).user,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            gettingList = false;
                            if (response != null) {
                                if (response.length() > 76 && response.substring(0, 62).equals(
                                        "<!doctype html><html><head><meta charset='utf-8'></head><body>")) {
                                    iUser = USER;
                                    moveScene(true);
                                    showList(response.substring(62, response.length() - 14));
                                } else if (response.equals("signInFailed")) {
                                    Main.signOut(sp);
                                    Toast.makeText(Admin.this, R.string.error8, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Admin.this, Main.class));
                                } else if (response.equals("insufficientData")) {
                                    Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError1));
                                } else if (response.equals("empty")) {
                                    iUser = USER;
                                    moveScene(true);
                                    Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aEmpty));
                                } else if (response.equals("notFound")) {
                                    Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError2));
                                } else
                                    Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError1) +
                                            "\n\n" + response);

                            } else
                                Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError1));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    gettingList = false;
                    Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError1));
                }
            });
            stringRequest.setTag(srTagList);
            listQueue.add(stringRequest);
            gettingList = true;
        } else Toast.makeText(Admin.this, R.string.error3, Toast.LENGTH_LONG).show();
    }

    void showList(String info) {
        String[] week = info.split(" !!!Q.V.D.Z!!! ");
        weeks = new ArrayList<>();
        for (int s = 0; s < week.length; s++) {
            String[] bigSplit = week[s].split(" @@@ "),
                    seenSplit = bigSplit[3].split("@");
            String id = "", date = "", items = "", mess = "";
            boolean seen = false;
            if (bigSplit.length > 3) id = seenSplit[1];
            if (bigSplit.length > 0) date = bigSplit[0];
            if (bigSplit.length > 1) items = bigSplit[1];
            if (bigSplit.length > 2) mess = Main.fixEncoding(bigSplit[2]);
            if (bigSplit.length > 3) seen = seenSplit[0].equals("1");
            weeks.add(new Week(id, date, items, mess, seen));
        }

        clWekIds = new ArrayList<>();
        clWktIds = new ArrayList<>();
        clScrIds = new ArrayList<>();
        tvScrIds = new ArrayList<>();
        tvWNewIds = new ArrayList<>();
        for (int w = 0; w < weeks.size(); w++) {
            clWekIds.add(View.generateViewId());
            clWktIds.add(View.generateViewId());
            clScrIds.add(View.generateViewId());
            tvScrIds.add(View.generateViewId());
            tvWNewIds.add(View.generateViewId());
            int tvWekMar = (int) (aToolbar.getHeight() / 7.32), clWekMar = (int) (tvWekMar / 2),
                    clScrMar = (int) (aToolbar.getHeight() / 4), tvNewMar = (int) (tvWekMar / 10);

            ConstraintLayout clWek = new ConstraintLayout(Admin.this);
            LinearLayout.LayoutParams clWekLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            clWekLP.setMargins(0, clWekMar, 0, clWekMar);
            clWek.setBackground(ContextCompat.getDrawable(Admin.this, R.drawable.square_1_orange_to_darkorange_xml));
            clWek.setId(clWekIds.get(w));
            aLL2.addView(clWek, clWekLP);

            ConstraintLayout clWBG = new ConstraintLayout(Admin.this);
            ConstraintLayout.LayoutParams clWBGLP = new ConstraintLayout.LayoutParams(0, 0);
            clWBGLP.startToStart = clWekIds.get(w);
            clWBGLP.topToTop = clWekIds.get(w);
            clWBGLP.endToEnd = clWekIds.get(w);
            clWBGLP.bottomToBottom = clWekIds.get(w);
            clWek.addView(clWBG, clWBGLP);

            ImageView ivWGlance = new ImageView(Admin.this);
            ConstraintLayout.LayoutParams ivWGlanceLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ivWGlance.setImageResource(R.drawable.glance_1_white);
            ivWGlance.setVisibility(View.INVISIBLE);
            clWBG.addView(ivWGlance, ivWGlanceLP);

            if (!weeks.get(w).seen) {
                shine(ivWGlance, aLL2.getWidth(), true, View.generateViewId(), 1, true, 19f);

                TextView tvNew = new TextView(new ContextThemeWrapper(Admin.this, R.style.aTvNew), null, 0);
                ConstraintLayout.LayoutParams tvNewLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                tvNewLP.topToTop = clWekIds.get(w);
                tvNewLP.endToEnd = clWekIds.get(w);
                tvNew.setPaddingRelative(0, tvNewMar, tvNewMar * 9, 0);
                tvNew.setText(R.string.aTvNew);
                tvNew.setRotation(-5f);
                tvNew.setTextColor(ContextCompat.getColor(Admin.this, R.color.aTvNew));
                tvNew.setTypeface(tfYekan);
                tvNew.setId(tvWNewIds.get(w));
                clWek.addView(tvNew, tvNewLP);
            }


            ConstraintLayout clWkt = new ConstraintLayout(Admin.this);
            ConstraintLayout.LayoutParams clWktLP = new ConstraintLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            clWktLP.startToStart = clWekIds.get(w);
            clWktLP.topToTop = clWekIds.get(w);
            clWktLP.endToStart = clScrIds.get(w);
            clWktLP.bottomToBottom = clWekIds.get(w);
            clWktLP.setMargins(0, tvWekMar, 0, tvWekMar);
            clWktLP.setMarginStart((int) (tvWekMar * 1.4));
            clWkt.setId(clWktIds.get(w));
            clWek.addView(clWkt, clWktLP);

            String[] Date = weeks.get(w).date.split("-");
            String year = "", month = "", day = "";
            if (Date.length > 0) year = Date[0];
            if (Date.length > 1)
                month = getResources().getStringArray(R.array.months)[Integer.parseInt(Date[1]) - 1];
            //"(" + Integer.toString(Integer.parseInt(Date[1])) + ")"
            if (Date.length > 2) day = Integer.toString(Integer.parseInt(Date[2]));
            TextView tvWek = new TextView(new ContextThemeWrapper(Admin.this, R.style.aTvWek), null, 0);
            ConstraintLayout.LayoutParams tvWekLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            tvWekLP.startToStart = clWktIds.get(w);
            tvWekLP.topToTop = clWktIds.get(w);
            tvWekLP.bottomToBottom = clWktIds.get(w);
            final String wTitle = day + " " + month + " " + year;
            tvWek.setText(wTitle);
            tvWek.setTypeface(tfYekan, Typeface.BOLD);
            clWkt.addView(tvWek, tvWekLP);


            ConstraintLayout clScr = new ConstraintLayout(Admin.this);
            ConstraintLayout.LayoutParams clScrLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            clScrLP.topToTop = clWekIds.get(w);
            clScrLP.endToEnd = clWekIds.get(w);
            clScrLP.bottomToBottom = clWekIds.get(w);
            clScrLP.setMargins(0, tvWekMar, 0, tvWekMar);
            clScrLP.setMarginEnd(clScrMar);
            clScr.setId(clScrIds.get(w));
            clWek.addView(clScr, clScrLP);

            TextView tvMSc = new TextView(new ContextThemeWrapper(Admin.this, R.style.aTvMSc), null, 0);
            ConstraintLayout.LayoutParams tvMScLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            tvMScLP.bottomToBottom = tvScrIds.get(w);
            tvMScLP.endToStart = tvScrIds.get(w);
            tvMSc.setTypeface(tfYekan);//, Typeface.BOLD
            tvMSc.setText(maxScore + "/");
            clScr.addView(tvMSc, tvMScLP);

            int score = 0;
            String[] scores = weeks.get(w).itms.split("");
            for (int s = 0; s < scores.length; s++) {
                if (scores[s].equals("0") || scores[s].equals("1") || scores[s].equals("2") || scores[s].equals("3")) {
                    if (scores[s].equals("1")) scores[s] = "3";
                    else if (scores[s].equals("3")) scores[s] = "1";
                    score += Integer.parseInt(scores[s]);
                }
            }
            TextView tvScr = new TextView(new ContextThemeWrapper(Admin.this, R.style.aTvScr), null, 0);
            ConstraintLayout.LayoutParams tvScrLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            tvScrLP.topToTop = clScrIds.get(w);
            tvScrLP.endToEnd = clScrIds.get(w);
            tvScr.setTypeface(tfYekan, Typeface.BOLD);
            tvScr.setText(Integer.toString(score));
            tvScr.setId(tvScrIds.get(w));
            clScr.addView(tvScr, tvScrLP);


            final String ITEMS = weeks.get(w).itms, MSG = weeks.get(w).mess, WID = weeks.get(w).id;
            final boolean SEEN = weeks.get(w).seen;
            final ConstraintLayout CLWBG = clWBG;
            final int W = w;
            clWek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!SEEN) {
                        action("seen_" + WID + "@" + users.get(iUser).user);
                        Week wOld = weeks.get(W);
                        weeks.set(W, new Week(wOld.id, wOld.date, wOld.itms, wOld.mess, true));
                        CLWBG.removeAllViews();
                        try {
                            TextView tvNew = findViewById(tvWNewIds.get(W));
                            tvNew.setVisibility(View.GONE);
                        } catch (NullPointerException ignored) {
                        }

                        boolean noNew = true;
                        for (int n = 0; n < weeks.size(); n++) {
                            if (!weeks.get(n).seen) noNew = false;
                        }
                        if (noNew) {
                            User old = users.get(iUser);
                            users.set(iUser, new User(old.user, old.name, old.pass, old.recs, old.date, old.mssg, old.verf, old.news, old.sums));
                            ConstraintLayout clBG = (ConstraintLayout) ((ConstraintLayout) findViewById(clUsrIds.get(iUser)))
                                    .getChildAt(clUBGPos);
                            clBG.removeAllViews();
                            try {
                                TextView tvUNew = findViewById(tvUNewIds.get(iUser));
                                tvUNew.setVisibility(View.GONE);
                            } catch (NullPointerException ignored) {
                            }
                        }
                    }

                    String[] msgs = MSG.split(Form.messSeparater);
                    int clWeekId = R.id.aClWeek, tvWTitleId = R.id.aTvWTitle, svWeekMar = (int) (aToolbar.getHeight() / 3),
                            trWeekPad = (int) (aToolbar.getHeight() / 8), tvWTitlekMar = (int) (aToolbar.getHeight() / 8),
                            vWSepLine1W = (int) (aBody.getWidth() * 0.85), tvWTitlePad = (int) (tvWTitlekMar * 1.85),
                            ivWCloseWH = (int) (aToolbar.getHeight() * 0.78), ivWCloseMar = (int) (aToolbar.getHeight() / 6),
                            ivWClosePad = (int) (aToolbar.getHeight() / 7);

                    ConstraintLayout clWeek = new ConstraintLayout(Admin.this);
                    ConstraintLayout.LayoutParams clWeekLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    clWeek.setId(clWeekId);
                    clWeek.setBackgroundColor(ContextCompat.getColor(Admin.this, R.color.aTlWeek));
                    aBody.addView(clWeek, clWeekLP);

                    TextView tvWTitle = new TextView(new ContextThemeWrapper(Admin.this, R.style.aTvWTitle), null, 0);
                    ConstraintLayout.LayoutParams tvWTitleLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    tvWTitleLP.startToStart = clWeekId;
                    tvWTitleLP.topToTop = clWeekId;
                    tvWTitleLP.endToEnd = clWeekId;
                    tvWTitle.setPaddingRelative(0, tvWTitlePad, 0, tvWTitlePad);
                    tvWTitle.setText(wTitle);
                    tvWTitle.setTypeface(tfYekan);
                    tvWTitle.setId(tvWTitleId);
                    clWeek.addView(tvWTitle, tvWTitleLP);

                    ImageView ivWClose = new ImageView(Admin.this);
                    ConstraintLayout.LayoutParams ivWCloseLP = new ConstraintLayout.LayoutParams(ivWCloseWH, ivWCloseWH);
                    ivWCloseLP.startToStart = clWeekId;
                    ivWCloseLP.topToTop = clWeekId;
                    ivWCloseLP.bottomToBottom = tvWTitleId;
                    ivWCloseLP.setMarginStart(ivWCloseMar);
                    ivWClose.setImageResource(R.drawable.cross_1);
                    ivWClose.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Admin.this, R.color.aIvWClose),
                            PorterDuff.Mode.SRC_IN));
                    ivWClose.setBackground(ContextCompat.getDrawable(Admin.this, R.drawable.square_1_tp_to_white_alpha_xml));
                    ivWClose.setPaddingRelative(ivWClosePad, ivWClosePad, ivWClosePad, ivWClosePad);
                    clWeek.addView(ivWClose, ivWCloseLP);
                    final ConstraintLayout CLWEEK = clWeek;
                    ivWClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            aBody.removeView(CLWEEK);
                        }
                    });

                    View vWSepLine1 = new View(Admin.this);
                    ConstraintLayout.LayoutParams vWSepLine1LP = new ConstraintLayout.LayoutParams(vWSepLine1W, 2);
                    vWSepLine1LP.startToStart = clWeekId;
                    vWSepLine1LP.endToEnd = clWeekId;
                    vWSepLine1LP.bottomToBottom = tvWTitleId;
                    vWSepLine1.setBackgroundColor(ContextCompat.getColor(Admin.this, R.color.aVWSepLine1));
                    clWeek.addView(vWSepLine1, vWSepLine1LP);


                    ScrollView svWeek = new ScrollView(Admin.this);
                    ConstraintLayout.LayoutParams svWeekLP = new ConstraintLayout.LayoutParams(0, 0);
                    svWeekLP.startToStart = clWeekId;
                    svWeekLP.topToBottom = tvWTitleId;
                    svWeekLP.endToEnd = clWeekId;
                    svWeekLP.bottomToBottom = clWeekId;
                    svWeekLP.setMargins(svWeekMar, svWeekMar, svWeekMar, svWeekMar);
                    clWeek.addView(svWeek, svWeekLP);

                    TableLayout tlWeek = new TableLayout(Admin.this);
                    ScrollView.LayoutParams tlWeekLP = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    svWeek.addView(tlWeek, tlWeekLP);

                    int maxPlace = (int) (aBody.getWidth() - (svWeekMar * 2));
                    for (int l = 0; l < ITEMS.length(); l++) {
                        String msg = "";
                        for (int m = 0; m < msgs.length; m++) {
                            String[] msgSplit = msgs[m].split("=", 2);
                            try {
                                if (Integer.parseInt(msgSplit[0]) == l) msg = msgSplit[1];
                            } catch (NumberFormatException ignored) {
                            }
                        }

                        TableRow trWeek = new TableRow(Admin.this);
                        TableLayout.LayoutParams trWeekLP = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        tlWeek.addView(trWeek, trWeekLP);

                        TextView tvWLesson = new TextView(new ContextThemeWrapper(Admin.this, R.style.aTvWLesson), null, 0);
                        TableRow.LayoutParams tvWLessonLP = new TableRow.LayoutParams((int) (maxPlace * 0.7),
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        tvWLesson.setText((l + 1) + ". " + lessons[l]);
                        tvWLesson.setTypeface(tfYekan);
                        tvWLesson.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        tvWLesson.setPaddingRelative(0, trWeekPad, 0, trWeekPad);
                        trWeek.addView(tvWLesson, tvWLessonLP);

                        TextView tvWScore = new TextView(new ContextThemeWrapper(Admin.this, R.style.aTvWScore), null, 0);
                        TableRow.LayoutParams tvWScoreLP = new TableRow.LayoutParams((int) (maxPlace * 0.3),
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        tvWScore.setText(buttons[Integer.parseInt(String.valueOf(ITEMS.charAt(l)))]);
                        tvWScore.setTypeface(tfYekan);
                        if (!msg.equals(""))
                            tvWScore.setBackground(ContextCompat.getDrawable(Admin.this,
                                    R.drawable.square_1_white_alpha_to_orange_alpha_xml));
                        tvWScore.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tvWScore.setPaddingRelative(0, trWeekPad, 0, trWeekPad);
                        trWeek.addView(tvWScore, tvWScoreLP);

                        final String theMSG = msg;
                        if (!msg.equals("")) {
                            tvWScore.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Main.makeBasicMessage(Admin.this, theMSG);
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    void action(String act) {
        final String actItself = act.substring(0, 4);
        final boolean mute = actItself.equals("seen");

        if (isOnline()) {
            RequestQueue queue = Volley.newRequestQueue(Admin.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ourCloudFolder + cloudAdmin +
                    "?user=" + Main.user + "&pass=" + Main.pass + "&act=" + act,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != null) {
                                if (response.equals("dropped")) {
                                    Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aDropped));
                                    moveScene(false);
                                    refresh();
                                } else if (response.equals("verified")) {
                                    Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aVerified));
                                    refresh();
                                } else if (response.equals("signInFailed")) {
                                    if (!mute) {
                                        Main.signOut(sp);
                                        Toast.makeText(Admin.this, R.string.error8, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Admin.this, Main.class));
                                    }
                                } else if (response.equals("insufficientData")) {
                                    if (!mute)
                                        Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError6));
                                } else if (response.equals("notFound")) {
                                    if (!mute)
                                        Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError2));
                                } else if (!mute)
                                    Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError6) +
                                            "\n\n" + response);

                            } else if (!mute)
                                Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError6));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (!mute)
                        Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError6));
                }
            });
            stringRequest.setTag(actItself);
            queue.add(stringRequest);
        } else if (!mute) Toast.makeText(Admin.this, R.string.error3, Toast.LENGTH_LONG).show();
    }

    void moveScene(boolean go) {
        if (showingUser != go) {
            showingUser = go;
            backBut(showingUser);
            myMenu.getItem(menuMessenger).setEnabled(showingUser);
            myMenu.getItem(menuDelete).setEnabled(showingUser);
            myMenu.getItem(menuUpdateList).setEnabled(!showingUser);
            float dest;
            int cucia = 255, rbcia = 255, ulcia = 255;
            if (go) {
                dest = (float) aSV.getWidth();
                if (iUser != -1 && users.get(iUser).name != null && !users.get(iUser).name.equals(""))
                    aToolbar.setSubtitle(users.get(iUser).name);//IT MUST TAKE PLACE ONLY HERE!!!
                ulcia = rbcDisabled;
            } else {
                weeks = null;
                aLL2.removeAllViews();
                iUser = -1;

                dest = 0f;
                aToolbar.setSubtitle(R.string.atbSubtitle);
                cucia = rbcDisabled;
                rbcia = rbcDisabled;
            }
            Main.mOA(aSV, "translationX", dest, showUserDur);
            iconAlpha(myMenu, menuMessenger, cucia);
            iconAlpha(myMenu, menuDelete, rbcia);
            iconAlpha(myMenu, menuUpdateList, ulcia);
        }
    }

    void refresh() {
        if (isOnline()) {
            refreshQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ourCloudFolder +
                    "login.php?user=" + Main.user + "&pass=" + Main.pass,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != null) {
                                if (response.length() >= 81 && response.substring(62, 67).equals("goAdm")) {
                                    Main.list = Main.fixEncoding(response.substring(67, response.length() - 14));
                                    arrangeUsers(Main.list);
                                    Toast.makeText(Admin.this, R.string.aListUpdated, Toast.LENGTH_SHORT).show();
                                } else if (response.equals("notFound")) {
                                    Main.signOut(sp);
                                    Toast.makeText(Admin.this, R.string.error8, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Admin.this, Main.class));
                                } else
                                    Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError5) +
                                            "\n\n" + response);
                            } else
                                Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError5));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Main.makeBasicMessage(Admin.this, getResources().getString(R.string.aError5));
                }
            });
            stringRequest.setTag(srTagList3);
            refreshQueue.add(stringRequest);
        } else Toast.makeText(Admin.this, R.string.error3, Toast.LENGTH_LONG).show();
    }

    boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void iconAlpha(Menu menu, int i, int alpha) {
        Drawable icon = menu.getItem(i).getIcon();
        if (icon != null) {
            icon.setAlpha(alpha);
            menu.getItem(i).setIcon(icon);
        }
    }

    void msgAlarm(final View bg) {
        ObjectAnimator anAl = Main.mOA(bg, "alpha", vUsrBGAlpha, vUsrBGDur);
        anAl.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator anAl2 = Main.mOA(bg, "alpha", 0f, vUsrBGDur);
                anAl2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        msgAlarm(bg);
                    }
                });
            }
        });
    }

    void showPopupMore(View v, int U) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.admin_more);
        /*MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.admin_more, popup.getMenu());*/// (for API 13-)
        morSelected = U;
        popup.show();
    }

    void shine(final View glance, final int area, final boolean first, final int motorId, final int dur, final boolean dir, final float qunt) {
        if (glance != null) {
            View motor;
            if (first) {
                motor = new View(new ContextThemeWrapper(Admin.this, R.style.Motor1), null, 0);
                ConstraintLayout.LayoutParams motorLP = new ConstraintLayout.LayoutParams(1, 1);
                motorLP.topToTop = R.id.aBody;
                motorLP.rightToRight = R.id.aBody;
                motor.setId(motorId);
                aBody.addView(motor, motorLP);
                glance.setVisibility(View.VISIBLE);
            } else motor = findViewById(motorId);

            float min = 0f - ((float) (glance.getWidth() * 1.11)),
                    max = (float) area + (float) (glance.getWidth() * 0.11);
            if (dir) {
                if (glance.getTranslationX() >= max) glance.setTranslationX(min);
                else glance.setTranslationX((float) glance.getTranslationX() + qunt);
            } else {
                if (glance.getTranslationX() <= min) glance.setTranslationX(max);
                else glance.setTranslationX((float) glance.getTranslationX() - qunt);
            }

            ValueAnimator anShine = Main.mVA(motor, "translationX", dur, 1f, 0f);
            anShine.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    shine(glance, area, false, motorId, dur, dir, qunt);
                }
            });
        }
    }


    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                int lenghtOfFile = conection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream(Main.pasteURL);

                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {
            dismissDialog(progress_bar_type);
//MIJN BESTE VRIEND!!!
            if (pasted.exists()) {
                Intent promptInstall = new Intent(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= 24) {
                    promptInstall.setDataAndType(FileProvider.getUriForFile(Admin.this,
                            BuildConfig.APPLICATION_ID + ".provider", pasted),
                            "application/vnd.android.package-archive");
                    List<ResolveInfo> resInfoList = Admin.this.getPackageManager().queryIntentActivities(promptInstall,
                            PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        Admin.this.grantUriPermission(resolveInfo.activityInfo.packageName, Uri.parse(pasted.getPath()),
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    promptInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                } else {
                    promptInstall.setDataAndType(Uri.fromFile(pasted), "application/vnd.android.package-archive");
                    promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(promptInstall);
            } else Main.makeBasicMessage(Admin.this, getResources().getString(R.string.error14));
        }

    }

    class User {
        final String user, name, pass, date;
        final int recs, mssg, sums;
        final boolean verf, news;

        private User(String user, String name, String pass, int recs, String date, int mssg, boolean verf, boolean news, int sums) {
            this.user = user;
            this.name = name;
            this.pass = pass;
            this.recs = recs;
            this.date = date;
            this.mssg = mssg;
            this.verf = verf;
            this.news = news;
            this.sums = sums;
        }
    }

    class Week {
        final String id, date, itms, mess;
        final boolean seen;

        private Week(String id, String date, String itms, String mess, boolean seen) {
            this.id = id;
            this.date = date;
            this.itms = itms;
            this.mess = mess;
            this.seen = seen;
        }
    }

    private class sortByScore implements Comparator<User> {
        public int compare(User a, User b) {
            return b.sums - a.sums;
            //FOR ASCENDING MODE: return a.sums - b.sums;
            //FOR TEXTS: return a.sums.compareTo(b.sums);
        }
    }
}

package org.ifaco.hatef;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.view.Gravity;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Form extends AppCompatActivity {
    ConstraintLayout mBody, mBody2;
    View mMotor;//, fBG
    Toolbar mToolbar;
    ScrollView mSV;
    LinearLayout mLL;

    ActionBar mToolbarAB;
    Typeface tfYekan;
    String[] lessons, buttons, messages;
    ArrayList<Integer> lesIds, namIds, quaIds, cbLesIds, lesBGIds, mesIds;
    float allDisabledAlpha = 0.52f, spnDisabled = 0.52f;
    SharedPreferences sp;
    boolean night = false, submitting = false, disabled = false, checkingFU = false, fLesBG = true, alarming = false, exiting = false;
    int[] nightly = new int[]{R.color.mTBTTN, R.color.mTBIconsN, R.style.ThemeOverlay_AppCompat_Dark, R.color.mBody2N,
            R.color.tvLesN, R.layout.spn_dd_1_n, R.color.spQuaVN, R.color.mTBSTTN, R.color.fLesBG1N, R.color.fLesBG2N, R.color.fMesIVN,
            R.drawable.square_1_tp_to_orange_alpha_xml, R.color.fCbLesN}, daily = new int[]{R.color.mTBTT, R.color.mTBIcons,
            R.style.ThemeOverlay_AppCompat_Light, R.color.mBody2, R.color.tvLes, R.layout.spn_dd_1, R.color.spQuaV, R.color.mTBSTT,
            R.color.fLesBG1, R.color.fLesBG2, R.color.fMesIV, R.drawable.square_1_tp_to_black_less_alpha_xml, R.color.fCbLes};
    int[][] nd = new int[][]{daily, nightly};
    Menu myMenu;
    int errorLogin = 0, spnChIndex = 0, spnMarkIndex = 1, menuMessenger = 0, menuSelectAll = 1, menuClearAll = 2, menuNight = 3,
            tbMsgAlarmDur = 408;
    ProgressDialog PD, pDialog;
    String submitUrl, srTag1 = "srTag1", version = null, ourCloudFolder = "";
    RequestQueue queue, cfuQueue;
    File pasted;
    public static final int progress_bar_type = 0;
    public static String messSeparater = "---q-v-d-z---";
    private static final int permWriteExerStorUpdate = 522;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        mBody = findViewById(R.id.fBody);
        mMotor = findViewById(R.id.fMotor);
        mToolbar = findViewById(R.id.fToolbar);
        //fBG = findViewById(R.id.fBG);
        mBody2 = findViewById(R.id.fBody2);
        mSV = findViewById(R.id.fSV);
        mLL = findViewById(R.id.fLL);

        lessons = getResources().getStringArray(R.array.lessons);
        buttons = getResources().getStringArray(R.array.buttons);
        tfYekan = Typeface.createFromAsset(getAssets(), "b_yekan.ttf");
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            PackageInfo pInfo = Form.this.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        Main.pasteURL = Environment.getExternalStorageDirectory().toString() + "/app.apk";
        pasted = new File(Main.pasteURL);
        if (pasted.exists()) pasted.delete();
        messages = new String[lessons.length];
        ourCloudFolder = getResources().getString(R.string.ourCloudFolder);

        setSupportActionBar(mToolbar);
        mToolbarAB = getSupportActionBar();
        //if (mToolbarAB != null) mToolbarAB.setDisplayHomeAsUpEnabled(true);
        for (int g = 0; g < mToolbar.getChildCount(); g++) {
            View getTitle = mToolbar.getChildAt(g);
            if (getTitle.getClass().getName().equalsIgnoreCase(Main.tvType) &&
                    ((TextView) getTitle).getText().toString().equals(getResources().getString(R.string.app_name))) {
                TextView title = ((TextView) getTitle);
                title.setTypeface(tfYekan, Typeface.BOLD);
                //title.setTextSize(22f);
            }
            if (getTitle.getClass().getName().equalsIgnoreCase(Main.tvType) &&
                    ((TextView) getTitle).getText().toString().equals(getResources().getString(R.string.ftbSubtitle))) {
                TextView title = ((TextView) getTitle);
                title.setTypeface(tfYekan);//, Typeface.BOLD
                //title.setTextSize(22f);
            }
        }
        //mToolbar.setNavigationIcon(Main.changeColor(this, getResources(), R.drawable.back_3_darkred, R.color.colorPrimary2));
        if (Main.name != null && !Main.name.equals("")) mToolbar.setSubtitle(Main.name);

        ValueAnimator anLoad = Main.mVA(mMotor, "translationX", Main.anLoadDelay, 1f, 0f);
        anLoad.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                lesIds = new ArrayList<>();
                lesBGIds = new ArrayList<>();
                namIds = new ArrayList<>();
                cbLesIds = new ArrayList<>();
                mesIds = new ArrayList<>();
                quaIds = new ArrayList<>();
                for (int l = 0; l < lessons.length; l++) {
                    lesIds.add(View.generateViewId());
                    lesBGIds.add(View.generateViewId());
                    namIds.add(View.generateViewId());
                    cbLesIds.add(View.generateViewId());
                    mesIds.add(View.generateViewId());
                    quaIds.add(View.generateViewId());
                    int hMargin = (int) (mToolbar.getHeight() / 4), vMargin = (int) (hMargin * 0.78), spnH = (int) (hMargin * 3),
                            spnW = (int) (spnH * 2.2), messWH = spnH, messPad = 0, spnMarkWH = (int) (spnH / 2.61),//2.2
                            miniHMargin = (int) (hMargin / 2), messMar = hMargin, spnMarkMar = (int) (hMargin / 3);
                    //cbLesWH = (int) (spnH / 1.78);

                    ConstraintLayout clLes = new ConstraintLayout(Form.this);
                    LinearLayout.LayoutParams clLesLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    clLes.setId(lesIds.get(l));
                    clLes.setBackgroundResource(R.drawable.square_1_tp_to_orange_alpha_xml);
                    mLL.addView(clLes, clLesLP);

                    int fLesBGWhich;
                    if (fLesBG) fLesBGWhich = 8;
                    else fLesBGWhich = 9;
                    View lesBG = new View(Form.this);
                    ConstraintLayout.LayoutParams lesBGLP = new ConstraintLayout.LayoutParams(0, 0);
                    lesBGLP.startToStart = lesIds.get(l);
                    lesBGLP.topToTop = lesIds.get(l);
                    lesBGLP.endToEnd = lesIds.get(l);
                    lesBGLP.bottomToBottom = lesIds.get(l);
                    lesBG.setBackgroundColor(ContextCompat.getColor(Form.this, daily[fLesBGWhich]));
                    lesBG.setId(lesBGIds.get(l));
                    clLes.addView(lesBG, lesBGLP);
                    fLesBG = !fLesBG;


                    LinearLayout llNam = new LinearLayout(Form.this);
                    ConstraintLayout.LayoutParams llNamLP = new ConstraintLayout.LayoutParams(0,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    llNamLP.startToStart = lesIds.get(l);
                    llNamLP.topToTop = lesIds.get(l);
                    llNamLP.bottomToBottom = lesIds.get(l);
                    llNamLP.endToStart = mesIds.get(l);
                    llNamLP.setMargins(0, vMargin, 0, vMargin);
                    llNamLP.setMarginStart(miniHMargin);
                    llNam.setOrientation(LinearLayout.HORIZONTAL);
                    llNam.setId(namIds.get(l));
                    llNam.setPaddingRelative(0, 0, messMar, 0);
                    clLes.addView(llNam, llNamLP);

                    CheckBox cbLes = new CheckBox(Form.this);
                    LinearLayout.LayoutParams cbLesLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    cbLes.setId(cbLesIds.get(l));
                    cbLes.setButtonDrawable(Main.changeColor(Form.this, getResources(), R.drawable.checkbox_1_black, daily[12]));
                    llNam.addView(cbLes, cbLesLP);

                    TextView tvLes = new TextView(new ContextThemeWrapper(Form.this, R.style.mTVLes), null, 0);
                    LinearLayout.LayoutParams tvLesLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    tvLesLP.setMarginStart(hMargin);
                    tvLes.setText(lessons[l]);
                    tvLes.setTypeface(tfYekan);
                    llNam.addView(tvLes, tvLesLP);


                    ConstraintLayout clMes = new ConstraintLayout(Form.this);
                    ConstraintLayout.LayoutParams clMesLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    clMesLP.endToStart = quaIds.get(l);
                    clMesLP.topToTop = lesIds.get(l);
                    clMesLP.bottomToBottom = lesIds.get(l);
                    clMesLP.setMargins(0, vMargin, 0, vMargin);
                    clMesLP.setMarginEnd(messMar);
                    clMes.setId(mesIds.get(l));
                    clMes.setBackground(ContextCompat.getDrawable(Form.this, R.drawable.square_1_tp_to_black_less_alpha_xml));
                    clMes.setPaddingRelative(messPad, messPad, messPad, messPad);
                    clLes.addView(clMes, clMesLP);

                    ImageView ivMes = new ImageView(Form.this);
                    ConstraintLayout.LayoutParams ivMesLP = new ConstraintLayout.LayoutParams(messWH, messWH);
                    ivMes.setImageResource(R.drawable.message_1_black);
                    ivMes.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Form.this, daily[10]),
                            PorterDuff.Mode.SRC_IN));
                    clMes.addView(ivMes, ivMesLP);


                    ConstraintLayout clQua = new ConstraintLayout(Form.this);
                    ConstraintLayout.LayoutParams clQuaLP = new ConstraintLayout.LayoutParams(spnW, spnH);
                    clQuaLP.endToEnd = lesIds.get(l);
                    clQuaLP.topToTop = lesIds.get(l);
                    clQuaLP.bottomToBottom = lesIds.get(l);
                    clQuaLP.setMargins(0, vMargin, 0, vMargin);
                    clQuaLP.setMarginEnd(hMargin);
                    clQua.setId(quaIds.get(l));
                    clLes.addView(clQua, clQuaLP);

                    Spinner spQua = new Spinner(Form.this, Spinner.MODE_DIALOG);//MODE_DROPDOWN
                    ConstraintLayout.LayoutParams spQuaLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    spQuaLP.startToStart = quaIds.get(l);
                    spQuaLP.topToTop = quaIds.get(l);
                    spQua.setPrompt(getResources().getString(R.string.fSelect));
                    spQua.setEnabled(false);
                    spQua.setAlpha(spnDisabled);
                    spQua.setBackground(ContextCompat.getDrawable(Form.this, R.drawable.spinner_border_1_black));
                    clQua.addView(spQua, spQuaLP);
                    ArrayAdapter<CharSequence> spQuaAdapter = ArrayAdapter.createFromResource(Form.this,
                            R.array.buttons, R.layout.spn_dd_1);//android.R.layout.simple_spinner_item
                    spQuaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spQua.setAdapter(spQuaAdapter);

                    ImageView spnMark = new ImageView(Form.this);
                    ConstraintLayout.LayoutParams spnMarkLP = new ConstraintLayout.LayoutParams(spnMarkWH, spnMarkWH);
                    spnMarkLP.bottomToBottom = quaIds.get(l);
                    spnMarkLP.endToEnd = quaIds.get(l);
                    spnMarkLP.setMargins(0, 0, 0, spnMarkMar);
                    spnMarkLP.setMarginEnd(spnMarkMar);
                    spnMark.setImageResource(R.drawable.spinner_1_black);
                    spnMark.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Form.this, daily[6]),
                            PorterDuff.Mode.SRC_IN));
                    spnMark.setAlpha(spnDisabled);
                    clQua.addView(spnMark, spnMarkLP);

                    Drawable spnD = spQua.getBackground();
                    if (spnD != null)
                        spnD.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Form.this, daily[6]),
                                PorterDuff.Mode.SRC_IN));
                    spQua.setBackground(spnD);


                    final CheckBox CBLES = cbLes;
                    clLes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CBLES.setChecked(!CBLES.isChecked());
                        }
                    });
                    final Spinner SPQUA = spQua;
                    final View SPMRK = spnMark;
                    cbLes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            int btnDrawable = R.drawable.checkbox_1_black, btnColour = daily[12];//android.R.drawable.checkbox_off_background
                            if (isChecked) {
                                btnDrawable = R.drawable.checkbox_1_black_checked;//android.R.drawable.checkbox_on_background
                                SPQUA.setAlpha(1f);
                                SPMRK.setAlpha(1f);
                            } else {
                                SPQUA.setAlpha(spnDisabled);
                                SPMRK.setAlpha(spnDisabled);
                            }
                            if (night) btnColour = nightly[12];
                            buttonView.setButtonDrawable(Main.changeColor(Form.this, getResources(), btnDrawable, btnColour));
                            SPQUA.setEnabled(isChecked);
                            //if (!isChecked) SPQUA.setSelection(0);
                        }
                    });
                    final int L = l;
                    clMes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!disabled) getMess(L);
                        }
                    });
                }
                fLesBG = true;

                int buttonWH = (int) (mToolbar.getHeight() * 0.97), buttonHMar = (int) (buttonWH / 2), buttonVMar = (int) (buttonWH / 2.61);
                LinearLayout fButtons = new LinearLayout(Form.this);
                LinearLayout.LayoutParams fButtonsLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                fButtonsLP.topMargin = buttonVMar;
                fButtons.setGravity(Gravity.CENTER);
                fButtons.setOrientation(LinearLayout.HORIZONTAL);
                mLL.addView(fButtons, fButtonsLP);

                ImageView fGo = new ImageView(Form.this);
                LinearLayout.LayoutParams fGoLP = new LinearLayout.LayoutParams(buttonWH, buttonWH);
                fGo.setImageResource(R.drawable.true_1_orange_xml);
                fGo.setId(R.id.fGo);
                fButtons.addView(fGo, fGoLP);
                fGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!disabled) {
                            boolean cbs = true;
                            for (int c = 0; c < cbLesIds.size(); c++) {
                                CheckBox cb = findViewById(cbLesIds.get(c));
                                Spinner spn = (Spinner) ((ConstraintLayout) findViewById(quaIds.get(c))).getChildAt(spnChIndex);
                                if (cb.isChecked() && spn.getSelectedItemPosition() == 0)
                                    cbs = false;
                            }
                            if (cbs) {
                                AlertDialog.Builder ask = new AlertDialog.Builder(Form.this)
                                        .setMessage(R.string.fAsk)
                                        .setPositiveButton(R.string.yes1, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                getData();
                                                startDownload();
                                            }
                                        })
                                        .setNegativeButton(R.string.no1, null)
                                        .setCancelable(false);
                                ask.create().show();
                            } else
                                Toast.makeText(Form.this, R.string.error2, Toast.LENGTH_LONG).show();
                        }
                    }
                });

                ImageView fReset = new ImageView(Form.this);
                LinearLayout.LayoutParams fResetLP = new LinearLayout.LayoutParams(buttonWH, buttonWH);
                fResetLP.setMarginStart(buttonHMar);
                fReset.setImageResource(R.drawable.false_1_orange_xml);
                fReset.setId(R.id.fReset);
                fButtons.addView(fReset, fResetLP);
                fReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder ask = new AlertDialog.Builder(Form.this)
                                .setMessage(R.string.fAskReset)
                                .setPositiveButton(R.string.yes1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        reset();
                                    }
                                })
                                .setNegativeButton(R.string.no1, null)
                                .setCancelable(false);
                        if (!disabled) ask.create().show();
                    }
                });

                if (Main.perm != null) {
                    StringBuilder noPerm = new StringBuilder();
                    for (int j = 0; j < Main.perm.length(); j++) noPerm.append("-");
                    if (Main.perm != null && !Main.perm.equals("") && !Main.perm.equals(noPerm.toString())) {
                        String[] data = Main.perm.split("");
                        if (data.length > 0) {
                            for (int i = 0; i < cbLesIds.size(); i++) {
                                int num = -1;
                                boolean gotNum = false;
                                try {
                                    num = Integer.parseInt(data[i + 1]);
                                    if (num == 0 || num == 1 || num == 2 || num == 3)
                                        gotNum = true;
                                } catch (NumberFormatException ignored) {
                                }
                                if (gotNum) {
                                    CheckBox cb = findViewById(cbLesIds.get(i));
                                    cb.setChecked(num > 0);
                                    Spinner spn = (Spinner) ((ConstraintLayout) findViewById(quaIds.get(i))).getChildAt(spnChIndex);
                                    spn.setSelection(num);
                                }
                            }
                            disableAll();
                            Main.makeBasicMessage(Form.this, getResources().getString(R.string.fDisabled));
                        }
                    } else if (Main.perm.equals(noPerm.toString())) {
                        Main.makeBasicMessage(Form.this, getResources().getString(R.string.fNoPerm));
                    }
                }
            }
        });

        if (Main.name != null && !Main.name.equals(""))
            Toast.makeText(Form.this, getResources().getText(R.string.mWelcome) + Main.name +
                    getResources().getText(R.string.mWelcome2), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Main.user == null || Main.pass == null || Main.perm == null
                || Main.user.equals("") || Main.pass.equals("") || Main.perm.equals("")) {
            Toast.makeText(Form.this, R.string.error8, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Main.class));
        }

        ValueAnimator anLoad = Main.mVA(mMotor, "translationX", Main.anLoadDelay, 1f, 0f);
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
        if (queue != null) queue.cancelAll(srTag1);
        if (cfuQueue != null) cfuQueue.cancelAll(Main.srTag2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_tb, menu);
        myMenu = menu;

        myMenu.getItem(menuMessenger).setIcon(Main.changeColor(Form.this, getResources(), R.drawable.messenger_1_black,
                R.color.tbMessengerIcon));
        if (Main.mssg) {
            msgAlarm(myMenu.getItem(menuMessenger));
            alarming = true;
        }
        menu.getItem(menuNight).setChecked(night);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ftbMessenger:
                alarming = false;
                startActivity(new Intent(Form.this, Messenger.class)
                        .putExtra("user", Main.user)
                        .putExtra("pass", Main.pass)
                        .putExtra("type", "usr")
                        .putExtra("to", Main.admin)
                );
                return true;
            case R.id.ftbSelectAll:
                if (!disabled && cbLesIds != null) for (int c = 0; c < cbLesIds.size(); c++) {
                    CheckBox cb = findViewById(cbLesIds.get(c));
                    cb.setChecked(true);
                }
                return true;
            case R.id.ftbClearAll:
                if (!disabled && cbLesIds != null) for (int c = 0; c < cbLesIds.size(); c++) {
                    CheckBox cb = findViewById(cbLesIds.get(c));
                    cb.setChecked(false);
                }
                return true;
            case R.id.ftbNight:
                night = !night;//item.setChecked(night);
                night(night);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("night", night);
                editor.apply();
                return true;
            case R.id.ftbUpdate:
                if (!checkingFU && version != null) {
                    checkingFU = true;
                    RequestQueue cfuQueue = Volley.newRequestQueue(this);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, ourCloudFolder + Main.last_version,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    checkingFU = false;
                                    int curVer = -1, newVer = -1;
                                    if (response != null) {
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
                                            AlertDialog.Builder askUpdate = new AlertDialog.Builder(Form.this)
                                                    .setIcon(android.R.drawable.btn_dialog)
                                                    .setMessage(getResources().getString(R.string.askUpdate) +
                                                            updateVer1 + sNewVer + updateVer3 + version + updateVer2)
                                                    .setPositiveButton(R.string.yes1, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            if (pasted.exists())
                                                                pasted.delete();

                                                            if (Main.isExternalStorageWritable()) {
                                                                if (ContextCompat.checkSelfPermission(Form.this,
                                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                                        != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= 23) {

                                                                    if (ActivityCompat.shouldShowRequestPermissionRationale(Form.this,
                                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                                                        Toast.makeText(Form.this, R.string.updateExpln, Toast.LENGTH_LONG).show();
                                                                    } else
                                                                        ActivityCompat.requestPermissions(Form.this,
                                                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                                                permWriteExerStorUpdate);
                                                                } else
                                                                    new DownloadFileFromURL().execute(ourCloudFolder + Main.file_url);
                                                            } else
                                                                Main.makeBasicMessage(Form.this, getResources().getString(R.string.error15));
                                                        }
                                                    })
                                                    .setNegativeButton(R.string.no1, null)
                                                    .setCancelable(false);
                                            askUpdate.create().show();
                                        } else if (newVer != -1 && curVer != -1 && newVer == curVer) {
                                            Main.makeBasicMessage(Form.this, getResources().getString(R.string.upToDate) +
                                                    updateVer1 + sNewVer + updateVer2);
                                        } else
                                            Main.makeBasicMessage(Form.this, getResources().getString(R.string.error12) +
                                                    "\n\n" + response);
                                    } else
                                        Main.makeBasicMessage(Form.this, getResources().getString(R.string.error12));
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            checkingFU = false;
                            Main.makeBasicMessage(Form.this, getResources().getString(R.string.error12));
                        }
                    });
                    stringRequest.setTag(Main.srTag2);
                    cfuQueue.add(stringRequest);
                }
                return true;
            case R.id.ftbLogOut:
                AlertDialog.Builder ask = new AlertDialog.Builder(Form.this)
                        .setMessage(R.string.exitAcc)
                        .setPositiveButton(R.string.yes1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Main.signOut(sp);
                                startActivity(new Intent(Form.this, Main.class));
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
        if (exiting) {
            moveTaskToBack(true);//REQUIRED!
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } else {
            exiting = true;
            Toast.makeText(Form.this, getResources().getText(R.string.toExit), Toast.LENGTH_SHORT).show();
            ValueAnimator anExit = Main.mVA(mMotor, "translationY", Main.exitingDelay, 111f, 0f);
            anExit.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    exiting = false;
                }
            });
        }
        /*Main.exit(Form.this, new DialogInterface.OnClickListener() {//IK HOU VAN YE!!!
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    new DownloadFileFromURL().execute(ourCloudFolder + Main.file_url);
                else Main.makeBasicMessage(Form.this, getResources().getString(R.string.error16));
            }
        }
    }


    void night(boolean n) {
        if (myMenu != null) myMenu.getItem(menuNight).setChecked(night);
        int[] colours;
        if (n) {
            colours = nd[1];
            //mToolbar.setNavigationIcon(Main.changeColor(Form.this, getResources(), R.drawable.back_3_white, R.color.mTBNavN));
        } else {
            colours = nd[0];
            //mToolbar.setNavigationIcon(R.drawable.back_3_white);
        }
        mBody.setBackgroundColor(ContextCompat.getColor(Form.this, colours[3]));
        /*mToolbar.setTitleTextColor(ContextCompat.getColor(Form.this, colours[0]));
        mToolbar.setSubtitleTextColor(ContextCompat.getColor(Form.this, colours[7]));
        Drawable d = mToolbar.getOverflowIcon();
        if (d != null)
            d.setColorFilter(ContextCompat.getColor(this, colours[1]), PorterDuff.Mode.SRC_ATOP);*/
        //mToolbar.setPopupTheme(colours[2]);

        for (int m = 0; m < namIds.size(); m++) {
            int fLesBGWhich;
            if (fLesBG) fLesBGWhich = 8;
            else fLesBGWhich = 9;
            View lesBG = findViewById(lesBGIds.get(m));
            lesBG.setBackgroundColor(ContextCompat.getColor(Form.this, colours[fLesBGWhich]));
            fLesBG = !fLesBG;

            CheckBox cbLes = findViewById(cbLesIds.get(m));
            int btnDrawable = R.drawable.checkbox_1_black;
            if (cbLes.isChecked()) btnDrawable = R.drawable.checkbox_1_black_checked;
            cbLes.setButtonDrawable(Main.changeColor(Form.this, getResources(), btnDrawable, colours[12]));

            TextView tv = (TextView) ((LinearLayout) findViewById(namIds.get(m))).getChildAt(1);
            tv.setTextColor(ContextCompat.getColor(Form.this, colours[4]));

            ConstraintLayout clMes = findViewById(mesIds.get(m));
            clMes.setBackground(ContextCompat.getDrawable(Form.this, colours[11]));
            ImageView ivMes = (ImageView) clMes.getChildAt(0);
            ivMes.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Form.this, colours[10]),
                    PorterDuff.Mode.SRC_IN));

            ConstraintLayout clQua = (ConstraintLayout) findViewById(quaIds.get(m));
            Spinner spn = (Spinner) clQua.getChildAt(spnChIndex);
            int selected = spn.getSelectedItemPosition();
            ArrayAdapter<CharSequence> spQuaAdapter = ArrayAdapter.createFromResource(Form.this, R.array.buttons, colours[5]);
            spQuaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn.setAdapter(spQuaAdapter);
            spn.setSelection(selected);

            ImageView spnMark = (ImageView) clQua.getChildAt(spnMarkIndex);
            spnMark.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Form.this, colours[6]),
                    PorterDuff.Mode.SRC_IN));
            Drawable spnD = spn.getBackground();
            if (spnD != null)
                spnD.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Form.this, colours[6]),
                        PorterDuff.Mode.SRC_IN));
            spn.setBackground(spnD);
        }
        fLesBG = true;
    }

    void reset() {
        if (cbLesIds != null) for (int c = 0; c < cbLesIds.size(); c++) {
            CheckBox cb = findViewById(cbLesIds.get(c));
            cb.setChecked(false);
        }
        if (quaIds != null) for (int s = 0; s < quaIds.size(); s++) {
            Spinner spn = (Spinner) ((ConstraintLayout) findViewById(quaIds.get(s))).getChildAt(spnChIndex);
            spn.setSelection(0);
        }
        messages = new String[lessons.length];
    }

    void startDownload() {
        if (isOnline()) {
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, submitUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            finishDownloading();
                            if (response != null) {
                                switch (response) {
                                    case "signInFailed":
                                        downloadChanges(false);
                                        Main.signOut(sp);
                                        Toast.makeText(Form.this, R.string.error8, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Form.this, Main.class));
                                        break;
                                    case "alreadyReported":
                                        downloadChanges(false);
                                        disableAll();
                                        Main.makeBasicMessage(Form.this, getResources().getString(R.string.error10));
                                        break;
                                    case "done":
                                        downloadChanges(false);
                                        disableAll();
                                        Main.makeBasicMessage(Form.this, getResources().getString(R.string.fDone));
                                        break;
                                    default:
                                        /*if (response.substring(0, 5).equals("Error"))
                                            Toast.makeText(Form.this, R.string.error9, Toast.LENGTH_LONG).show();
                                        else*/
                                        errorDL(response);
                                        break;
                                }
                            } else errorDL(null);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    finishDownloading();
                    errorDL(null);
                }
            });
            stringRequest.setTag(srTag1);
            queue.add(stringRequest);
            submitting = true;
        } else Toast.makeText(Form.this, R.string.error3, Toast.LENGTH_LONG).show();
    }

    boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    void errorDL(String res) {
        errorLogin += 1;
        downloadChanges(false);
        if (errorLogin < Main.errorLoginMax) {
            getData();
            startDownload();
        } else {
            errorLogin = 0;
            String RES = "";
            if (res != null) RES = "\n\n" + res;
            Main.makeBasicMessage(Form.this, getResources().getString(R.string.error9) + RES);
        }
    }

    void getData() {
        downloadChanges(true);
        StringBuilder adt = new StringBuilder(), msgs = new StringBuilder();
        for (int cb = 0; cb < cbLesIds.size(); cb++) {
            boolean cbc = ((CheckBox) findViewById(cbLesIds.get(cb))).isChecked();
            if (cbc) {
                ConstraintLayout cl = (ConstraintLayout) findViewById(quaIds.get(cb));
                int pos = ((Spinner) cl.getChildAt(spnChIndex)).getSelectedItemPosition();
                adt.append(Integer.toString(pos));
            } else adt.append("0");
        }
        for (int m = 0; m < messages.length; m++)
            if (messages[m] != null)
                msgs.append(m).append("=").append(messages[m]).append(messSeparater);
        String msg = msgs.toString();
        if (msg.length() > 0) msg = msg.substring(0, msg.length() - 13);
        submitUrl = ourCloudFolder + "form.php?user=" + Main.user + "&pass=" + Main.pass + "&items="
                + adt.toString() + "&messages=" + msg;
    }

    void runPD() {
        PD = new ProgressDialog(Form.this);
        PD.setCancelable(false);
        PD.setMessage(getResources().getString(R.string.wait1));
        PD.setButton(getResources().getText(R.string.cancel1), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                downloadChanges(false);
                dialogInterface.dismiss();
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

    void downloadChanges(boolean start) {
        if (start) {
            runPD();
        } else {
            if (PD != null && PD.isShowing()) {
                PD.dismiss();
                PD = null;
            } else if (PD != null) PD = null;
        }
    }

    void finishDownloading() {
        if (queue != null) queue.cancelAll(srTag1);
        submitting = false;
    }

    void disableAll() {
        for (int i = 0; i < cbLesIds.size(); i++) {
            CheckBox cb = findViewById(cbLesIds.get(i));
            cb.setEnabled(false);
            Spinner spn = (Spinner) ((ConstraintLayout) findViewById(quaIds.get(i))).getChildAt(spnChIndex);
            spn.setEnabled(false);
            mSV.setAlpha(allDisabledAlpha);
            ConstraintLayout les = (ConstraintLayout) mLL.getChildAt(i);
            les.setBackgroundColor(ContextCompat.getColor(Form.this, R.color.transparent));
            les.setOnClickListener(null);
            ImageView go = findViewById(R.id.fGo);
            go.setImageResource(R.drawable.true_1_orange);
            ImageView rs = findViewById(R.id.fReset);
            rs.setImageResource(R.drawable.false_1_orange);
            ConstraintLayout mes = findViewById(mesIds.get(i));
            mes.setBackgroundColor(ContextCompat.getColor(Form.this, R.color.transparent));
            if (myMenu != null) myMenu.getItem(menuSelectAll).setEnabled(false);
            if (myMenu != null) myMenu.getItem(menuClearAll).setEnabled(false);
        }
        disabled = true;
    }

    public static ConstraintLayout messBuilder(Context c, Boolean Night, String text, int lines) {
        ConstraintLayout cl = new ConstraintLayout(c);
        ViewGroup.LayoutParams clLP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        cl.setPaddingRelative(33, 40, 33, 0);

        int ett, etv;
        if (Night) {
            ett = R.color.fMesETTN;
            etv = R.color.fMesETVN;
        } else {
            ett = R.color.fMesETT;
            etv = R.color.fMesETV;
        }
        EditText et = new EditText(c);
        ViewGroup.MarginLayoutParams etLP = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        et.setPaddingRelative(20, 10, 20, 10);
        et.setTextColor(ContextCompat.getColor(c, ett));
        et.setBackgroundColor(ContextCompat.getColor(c, etv));
        et.setTextDirection(View.TEXT_DIRECTION_RTL);
        et.setTextSize(18f);
        et.setLines(lines);
        et.setText(text);
        et.requestFocus();
        cl.addView(et, etLP);
        return cl;
    }

    void getMess(final int L) {
        String text = "";
        if (messages[L] != null) {
            try {
                text = URLDecoder.decode(messages[L], "utf-8");
            } catch (UnsupportedEncodingException e) {
                Toast.makeText(Form.this, R.string.fError2, Toast.LENGTH_LONG).show();
            }
        }
        final ConstraintLayout V = messBuilder(Form.this, night, text, 5);
        AlertDialog.Builder mess = new AlertDialog.Builder(Form.this)
                .setTitle(R.string.fMessTitle)
                .setView(V)
                .setPositiveButton(R.string.fMessYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText et = (EditText) V.getChildAt(0);
                        try {
                            messages[L] = URLEncoder.encode(et.getText().toString(), "utf-8");
                        } catch (UnsupportedEncodingException ignored) {
                            Toast.makeText(Form.this, R.string.fError1, Toast.LENGTH_LONG).show();
                            getMess(L);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel1, null)
                .setCancelable(false);
        mess.create().show();
    }

    void msgAlarm(final MenuItem mi) {
        ValueAnimator anAl = Main.mVA(mMotor, "translationY", tbMsgAlarmDur, 48f, 0f);
        anAl.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                mi.setIcon(Main.changeColor(Form.this, getResources(), R.drawable.messenger_1_black,
                        R.color.tbMsgAlarm));
                ValueAnimator anAl2 = Main.mVA(mMotor, "translationY", tbMsgAlarmDur, 48f, 0f);
                anAl2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mi.setIcon(Main.changeColor(Form.this, getResources(), R.drawable.messenger_1_black,
                                R.color.tbMessengerIcon));
                        if (alarming) msgAlarm(mi);
                    }
                });
            }
        });
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

            if (pasted.exists()) {
                Intent promptInstall = new Intent(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= 24) {
                    promptInstall.setDataAndType(FileProvider.getUriForFile(Form.this,
                            BuildConfig.APPLICATION_ID + ".provider", pasted),
                            "application/vnd.android.package-archive");
                    List<ResolveInfo> resInfoList = Form.this.getPackageManager().queryIntentActivities(promptInstall,
                            PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        Form.this.grantUriPermission(resolveInfo.activityInfo.packageName, Uri.parse(pasted.getPath()),
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    promptInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                } else {
                    promptInstall.setDataAndType(Uri.fromFile(pasted), "application/vnd.android.package-archive");
                    promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(promptInstall);
            } else Main.makeBasicMessage(Form.this, getResources().getString(R.string.error14));
        }

    }
}

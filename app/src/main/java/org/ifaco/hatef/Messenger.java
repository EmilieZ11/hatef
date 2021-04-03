package org.ifaco.hatef;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Messenger extends AppCompatActivity {
    ConstraintLayout sBody, sToolbars, sToolbar2, sBody2, sPostCl;
    View sMotor, sBG;
    ImageView sTB2Close, sTB2SelectAll, sTB2AvoidAll, sTB2Delete, sNewETAft;
    HorizontalScrollView sTB2HSV;
    LinearLayout sTB2LL, sLL;
    Toolbar sToolbar;
    ScrollView sSV;
    EditText sNewET;

    ActionBar sToolbarAB;
    Typeface tfYekan;
    SharedPreferences sp;
    boolean night = false, isAdmin = false, checking = false, posting = false, selecting = false;
    Menu myMenu;
    String ourCloudFolder = "", user = "", pass = "", type = "", to = "", cloudMessenger = "messenger.php", rqtCheck = "check",
            msgSep = " !!!Q.V.D.Z!!! ", inMsgSep = "---.q.v.d.z.---", postAdtSep = " &&& ", howCheck = "view", idSep = "qvdz";
    int menuNight = 1, erPost = 0, erDelete = 0, erMax1 = 3, vBGDur = 111;//recheckDelay = 1265
    int[] nightly = new int[]{R.color.mTBTTN, R.color.mTBIconsN, R.style.ThemeOverlay_AppCompat_Dark, R.color.mBody2N, R.color.mTBNavN,
            R.color.sNewETTN, R.color.sTvMess1TN, R.color.sTvMess2TN, R.color.sSeen0N}, daily = new int[]{R.color.mTBTT, R.color.mTBIcons,
            R.style.ThemeOverlay_AppCompat_Light, R.color.mBody2, R.color.mTBNav, R.color.sNewETT, R.color.sTvMess1T, R.color.sTvMess2T,
            R.color.sSeen0}, seenImgs = new int[]{R.drawable.seen_1_off, R.drawable.seen_1_on, R.drawable.waiting_1};
    int[][] nd = new int[][]{daily, nightly};
    //RequestQueue checkQueue, actionQueue;
    ArrayList<Msg> messages = new ArrayList<>();
    String[] admins, months;
    ArrayList<Integer> selection;
    float vBGAlpha = 0.48f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().containsKey("user") && getIntent().getExtras().containsKey("pass") &&
                        getIntent().getExtras().containsKey("to") && getIntent().getExtras().containsKey("to")) {
                    user = getIntent().getExtras().getString("user");
                    pass = getIntent().getExtras().getString("pass");
                    type = getIntent().getExtras().getString("type");
                    to = getIntent().getExtras().getString("to");
                } else onBackPressed();
            } else onBackPressed();
        } else onBackPressed();
        setContentView(R.layout.messenger);

        sBody = findViewById(R.id.sBody);
        sMotor = findViewById(R.id.sMotor);
        sToolbars = findViewById(R.id.sToolbars);
        sToolbar2 = findViewById(R.id.sToolbar2);
        sTB2Close = findViewById(R.id.sTB2Close);
        sTB2HSV = findViewById(R.id.sTB2HSV);
        sTB2LL = findViewById(R.id.sTB2LL);
        sTB2SelectAll = findViewById(R.id.sTB2SelectAll);
        sTB2AvoidAll = findViewById(R.id.sTB2AvoidAll);
        sTB2Delete = findViewById(R.id.sTB2Delete);
        sToolbar = findViewById(R.id.sToolbar);
        sBG = findViewById(R.id.sBG);
        sBody2 = findViewById(R.id.sBody2);
        sSV = findViewById(R.id.sSV);
        sLL = findViewById(R.id.sLL);
        sPostCl = findViewById(R.id.sPostCl);
        sNewET = findViewById(R.id.sNewET);
        sNewETAft = findViewById(R.id.sNewETAft);

        tfYekan = Typeface.createFromAsset(getAssets(), "b_yekan.ttf");
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ourCloudFolder = getResources().getString(R.string.ourCloudFolder);
        admins = getResources().getStringArray(R.array.mAdmnUsers);
        for (int a = 0; a < admins.length; a++) if (Main.user.equals(admins[a])) isAdmin = true;
        months = getResources().getStringArray(R.array.months);

        setSupportActionBar(sToolbar);
        sToolbarAB = getSupportActionBar();
        if (sToolbarAB != null) sToolbarAB.setDisplayHomeAsUpEnabled(true);
        for (int g = 0; g < sToolbar.getChildCount(); g++) {
            View getTitle = sToolbar.getChildAt(g);
            if (getTitle.getClass().getName().equalsIgnoreCase(Main.tvType) &&
                    ((TextView) getTitle).getText().toString().equals(getResources().getString(R.string.app_name))) {
                TextView title = ((TextView) getTitle);
                title.setTypeface(tfYekan, Typeface.BOLD);
            }
            if (getTitle.getClass().getName().equalsIgnoreCase(Main.tvType) &&
                    ((TextView) getTitle).getText().toString().equals(getResources().getString(R.string.stbSubtitleU))) {
                TextView title = ((TextView) getTitle);
                if (isAdmin) title.setText(getResources().getString(R.string.stbSubtitleA));
                title.setTypeface(tfYekan);
            }
        }

        ValueAnimator anLoad = Main.mVA(sMotor, "translationX", Main.anLoadDelay, 1f, 0f);
        anLoad.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                boolean getNight = sp.getBoolean("night", false);
                if (getNight != night) {
                    night = getNight;
                    night(night);
                }

                check();
            }
        });

        sToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sNewET.setTypeface(tfYekan);
        sNewETAft.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Messenger.this, R.color.sNewETAft),
                PorterDuff.Mode.SRC_IN));
        sNewETAft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sNewET.getText().toString().equals("")) {
                    action("post", sNewET.getText().toString());
                }
            }
        });

        sTB2Close.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Messenger.this, R.color.sTB2Icons),
                PorterDuff.Mode.SRC_IN));
        sTB2Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeTB2();
            }
        });
        for (int v = 0; v < sTB2LL.getChildCount(); v++) {
            ImageView i = (ImageView) sTB2LL.getChildAt(v);
            i.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Messenger.this, R.color.sTB2Icons),
                    PorterDuff.Mode.SRC_IN));
        }
        sTB2SelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selecting) {
                    selection = new ArrayList<>();
                    for (int v = 0; v < sLL.getChildCount(); v++) {
                        selection.add(v);
                        View bg = (View) ((ConstraintLayout) sLL.getChildAt(v)).getChildAt(0);
                        Main.mOA(bg, "alpha", vBGAlpha, vBGDur);
                    }
                }
            }
        });
        sTB2AvoidAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selecting) {
                    selection = new ArrayList<>();
                    for (int v = 0; v < sLL.getChildCount(); v++) {
                        View bg = (View) ((ConstraintLayout) sLL.getChildAt(v)).getChildAt(0);
                        Main.mOA(bg, "alpha", 0f, vBGDur);
                    }
                }
            }
        });
        sTB2Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selecting && selection != null && selection.size() > 0) {
                    StringBuilder prop = new StringBuilder();
                    for (int s = 0; s < selection.size(); s++) {
                        prop.append(messages.get(selection.get(s)).id);
                        if (s < selection.size() - 1) prop.append(idSep);
                    }
                    action("delete", prop.toString());
                    closeTB2();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Main.user == null || Main.pass == null || Main.user.equals("") || Main.pass.equals("")) {
            Toast.makeText(Messenger.this, R.string.error8, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Main.class));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        updateSV();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.messenger_tb, menu);
        myMenu = menu;
        myMenu.getItem(menuNight).setChecked(night);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stbCheck:
                check();
                return true;
            case R.id.stbNight:
                night = !night;
                night(night);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("night", night);
                editor.apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    void night(boolean n) {
        if (myMenu != null) myMenu.getItem(menuNight).setChecked(night);
        int[] colours;
        if (n) {
            colours = nd[1];
            sToolbar.setNavigationIcon(Main.changeColor(Messenger.this, getResources(), R.drawable.back_3_white, colours[4]));
        } else {
            colours = nd[0];
            sToolbar.setNavigationIcon(Main.changeColor(Messenger.this, getResources(), R.drawable.back_3_white, colours[4]));
        }
        sBody.setBackgroundColor(ContextCompat.getColor(Messenger.this, colours[3]));
        /*aToolbar.setTitleTextColor(ContextCompat.getColor(Admin.this, colours[0]));
        Drawable d = aToolbar.getOverflowIcon();
        if (d != null)
            d.setColorFilter(ContextCompat.getColor(this, colours[1]), PorterDuff.Mode.SRC_ATOP);*/
        //mToolbar.setPopupTheme(colours[2]);

        sNewET.setTextColor(ContextCompat.getColor(Messenger.this, colours[5]));
        for (int m = 0; m < messages.size(); m++) {
            ConstraintLayout cl = findViewById(messages.get(m).clId);
            LinearLayout llMess = (LinearLayout) ((ConstraintLayout) ((ConstraintLayout)
                    cl.getChildAt(1)).getChildAt(1)).getChildAt(0);
            LinearLayout llInfo = (LinearLayout) llMess.getChildAt(1);
            int tvc;
            if (messages.get(m).admn) {
                if (isAdmin) tvc = colours[6];
                else tvc = colours[7];
            } else {
                if (isAdmin) tvc = colours[7];
                else tvc = colours[6];
            }
            ((TextView) llMess.getChildAt(0)).setTextColor(ContextCompat.getColor(Messenger.this, tvc));
            if (llInfo.getChildCount() == 1)
                ((TextView) llInfo.getChildAt(0)).setTextColor(ContextCompat.getColor(Messenger.this, tvc));
            if (llInfo.getChildCount() == 2)
                ((TextView) llInfo.getChildAt(1)).setTextColor(ContextCompat.getColor(Messenger.this, tvc));
            View iv = llInfo.getChildAt(0);
            if (iv.getClass().getName().equalsIgnoreCase(Main.ivType))
                ((ImageView) iv).setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Messenger.this,
                        colours[8]), PorterDuff.Mode.SRC_IN));
        }
    }

    boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    ConstraintLayout makeItem(int id) {
        ConstraintLayout item = new ConstraintLayout(Messenger.this);
        LinearLayout.LayoutParams itemLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        item.setLayoutParams(itemLP);
        item.setId(id);
        return item;
    }

    int addMessage(boolean fPrsn, String text, final int id, String date, int seen) {
        int clMessMar = (int) (sToolbar.getHeight() / 8), tvDateMar = (int) (sToolbar.getHeight() / 4),
                tvMessMaxW = (int) (sToolbar.getWidth() * 0.8), tvMessMinW = (int) (sToolbar.getHeight() / 4),
                vMBW = (int) (sToolbar.getHeight() / 2), vMBH = (int) (sToolbar.getHeight() / 2.2),
                ivSeenWH = (int) (sToolbar.getHeight() / 3.3), llInfoMar = (int) (sToolbar.getHeight() / 7),
                mColour, mTColour, clIMPad = (int) (sToolbar.getHeight() / 5);
        int clMessId = View.generateViewId();
        float vMBRtt;//, tvMFS = 18f;
        if (fPrsn) {
            vMBRtt = 0f;
            mColour = R.color.clInMess1;
            if (!night) mTColour = R.color.sTvMess1T;
            else mTColour = R.color.sTvMess1TN;
        } else {
            vMBRtt = 180f;
            mColour = R.color.clInMess2;
            if (!night) mTColour = R.color.sTvMess2T;
            else mTColour = R.color.sTvMess2TN;
        }

        ConstraintLayout item = makeItem(id);
        sLL.addView(item);
        final int POS = sLL.getChildCount() - 1;

        View vMessBG = new View(Messenger.this);
        ConstraintLayout.LayoutParams vMessBGLP = new ConstraintLayout.LayoutParams(0, 0);//MUST BE ONLY 0!
        vMessBGLP.startToStart = id;
        vMessBGLP.topToTop = id;
        vMessBGLP.endToEnd = id;
        vMessBGLP.bottomToBottom = id;
        vMessBG.setAlpha(0f);
        vMessBG.setBackgroundColor(ContextCompat.getColor(Messenger.this, R.color.sMessBG));
        item.addView(vMessBG, vMessBGLP);

        ConstraintLayout clMess = new ConstraintLayout(Messenger.this);
        ConstraintLayout.LayoutParams clMessLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (fPrsn) clMessLP.startToStart = id;
        else clMessLP.endToEnd = id;
        clMessLP.topToTop = id;
        clMessLP.bottomToBottom = id;
        clMessLP.setMargins(0, clMessMar, 0, clMessMar);
        clMess.setId(clMessId);
        item.addView(clMess, clMessLP);

        View vMessBase = new View(Messenger.this);
        ConstraintLayout.LayoutParams vMessBaseLP = new ConstraintLayout.LayoutParams(vMBW, vMBH);
        if (fPrsn) vMessBaseLP.startToStart = clMessId;
        else vMessBaseLP.endToEnd = clMessId;
        vMessBaseLP.bottomToBottom = clMessId;
        vMessBase.setBackground(Main.changeColor(Messenger.this, getResources(), R.drawable.chat_mess_1_base, mColour));
        vMessBase.setRotationY(vMBRtt);
        clMess.addView(vMessBase, vMessBaseLP);


        ConstraintLayout clInMess = new ConstraintLayout(Messenger.this);
        ConstraintLayout.LayoutParams clInMessLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        clInMessLP.startToStart = clMessId;
        clInMessLP.topToTop = clMessId;
        clInMessLP.endToEnd = clMessId;
        clInMessLP.bottomToBottom = clMessId;
        clInMessLP.setMargins(0, 0, 0, (int) (vMBH / 11));
        if (fPrsn) clInMessLP.setMarginStart((int) (vMBW / 6));
        else clInMessLP.setMarginEnd((int) (vMBW / 6));
        clInMess.setBackgroundColor(ContextCompat.getColor(Messenger.this, mColour));
        clInMess.setPaddingRelative(clIMPad, clIMPad, clIMPad, clIMPad);
        clMess.addView(clInMess, clInMessLP);

        LinearLayout llMess = new LinearLayout(Messenger.this);
        ConstraintLayout.LayoutParams llMessLP = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llMess.setOrientation(LinearLayout.VERTICAL);
        if (fPrsn) llMess.setGravity(Gravity.START);
        else llMess.setGravity(Gravity.END);
        clInMess.addView(llMess, llMessLP);

        TextView tvMess = new TextView(new ContextThemeWrapper(Messenger.this, R.style.sTvMess), null, 0);
        LinearLayout.LayoutParams tvMessLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tvMess.setText(text);
        tvMess.setTypeface(tfYekan);
        tvMess.setMaxWidth(tvMessMaxW);
        tvMess.setMinWidth(tvMessMinW);
        tvMess.setTextColor(ContextCompat.getColor(Messenger.this, mTColour));
        llMess.addView(tvMess, tvMessLP);


        LinearLayout llInfo = new LinearLayout(Messenger.this);
        LinearLayout.LayoutParams llInfoLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llInfoLP.setMargins(0, llInfoMar, 0, 0);
        llInfo.setOrientation(LinearLayout.HORIZONTAL);
        llInfo.setGravity(Gravity.CENTER_VERTICAL);
        llMess.addView(llInfo, llInfoLP);

        if (fPrsn) {
            ImageView ivSeen = new ImageView(Messenger.this);
            LinearLayout.LayoutParams ivSeenLP = new LinearLayout.LayoutParams(ivSeenWH, ivSeenWH);
            ivSeenLP.setMarginEnd(tvDateMar);
            int seenImg = 2;
            if (seen == 0 || seen == 1) seenImg = seen;
            ivSeen.setImageResource(seenImgs[seenImg]);
            int scf;
            if (night) scf = nd[1][8];
            else scf = nd[0][8];
            ivSeen.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(Messenger.this, scf),
                    PorterDuff.Mode.SRC_IN));
            ivSeen.setAlpha(0.85f);
            llInfo.addView(ivSeen, ivSeenLP);
        }

        TextView tvDate = new TextView(new ContextThemeWrapper(Messenger.this, R.style.sTvDate), null, 0);
        LinearLayout.LayoutParams tvDateLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        String DAT = "";
        if (!date.equals("now")) {
            String[] D = date.split(" "), DATE = D[0].split("-"), TIME = D[1].split(":");
            String dt = "";
            try {
                dt = DATE[2] + " " + months[Integer.parseInt(DATE[1]) - 1];
            } catch (NumberFormatException ignored) {
            }
            DAT = dt + " - " + leadingZero(TIME[0], 2) + ":" + leadingZero(TIME[1], 2);
        } else DAT = getResources().getString(R.string.now);
        tvDate.setText(DAT);
        tvDate.setTypeface(tfYekan);
        tvDate.setTextColor(ContextCompat.getColor(Messenger.this, mTColour));
        tvDate.setMaxWidth(tvMessMaxW - (ivSeenWH + tvDateMar));
        llInfo.addView(tvDate, tvDateLP);


        item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //showPopupSelect(view);
                if (!selecting) {
                    selecting = true;
                    selection = new ArrayList<>();
                    selection.add(sLL.indexOfChild(view));
                    View bg = (View) ((ConstraintLayout) view).getChildAt(0);
                    Main.mVA(bg, "alpha", vBGDur, 0f, vBGAlpha);

                    sToolbarAB.hide();
                    sToolbar2.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selecting) {
                    if (selection == null) selection = new ArrayList<>();
                    View bg = (View) ((ConstraintLayout) view).getChildAt(0);
                    if (bg.getAlpha() != vBGAlpha) {
                        selection.add(sLL.indexOfChild(view));
                        Main.mVA(bg, "alpha", vBGDur, 0f, vBGAlpha);
                    } else {
                        for (int s = selection.size() - 1; s >= 0; s--)
                            if (selection.get(s) == sLL.indexOfChild(view)) selection.remove(s);
                        Main.mVA(bg, "alpha", vBGDur, vBGAlpha, 0f);
                    }
                }
            }
        });

        updateSV();
        return POS;
    }

    void updateSV() {
        ValueAnimator an = Main.mVA(sMotor, "translationY", 1, -1f, 0f);
        an.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (sLL.getHeight() > sSV.getHeight())
                    sSV.scrollTo(0, sLL.getHeight() - sSV.getHeight());
            }
        });
    }

    void check() {
        if (isOnline()) {
            StringBuilder prop = new StringBuilder();
            String act = "updt", PROP = "", fixProp, SEEN;
            if (howCheck.equals(act)) {
                for (int m = 0; m < messages.size(); m++) {
                    prop.append(messages.get(m).id);
                    prop.append(inMsgSep);
                    prop.append(messages.get(m).text);
                    prop.append(inMsgSep);
                    if (messages.get(m).seen) SEEN = "1";
                    else SEEN = "0";
                    prop.append(SEEN);
                    if (m < messages.size() - 1) prop.append(msgSep);
                }
                fixProp = prop.toString();
                try {
                    fixProp = URLEncoder.encode(fixProp, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                PROP = "&prop=" + fixProp;
            } else act = "view";

            RequestQueue checkQueue = Volley.newRequestQueue(Messenger.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ourCloudFolder + cloudMessenger +
                    "?user=" + Main.user + "&pass=" + Main.pass + "&type=" + type + "&contact=" + to + "&act=" + act + PROP,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            checking = false;
                            if (response != null) {
                                if (response.length() > (76 + 3) && response.substring(0, 62).equals(
                                        "<!doctype html><html><head><meta charset='utf-8'></head><body>")) {
                                    if (response.substring(62, 66).equals("ALL:")) {
                                        howCheck = "updt";
                                        String[] info = response.substring(66, response.length() - 14).split(msgSep);
                                        sLL.removeAllViews();
                                        messages = new ArrayList<>();
                                        for (int m = 0; m < info.length; m++) {
                                            String[] msgComs = info[m].split(inMsgSep);
                                            messages.add(new Msg(msgComs[0], Main.fixEncoding(msgComs[1]), msgComs[2].equals("1"),
                                                    msgComs[3], msgComs[4].equals("1"), View.generateViewId()));
                                            boolean fPrsn;
                                            if (isAdmin) fPrsn = messages.get(m).admn;
                                            else fPrsn = !messages.get(m).admn;
                                            int seen = 0;
                                            if (msgComs[4].equals("1")) seen = 1;
                                            addMessage(fPrsn, messages.get(m).text, messages.get(m).clId, msgComs[3], seen);
                                        }
                                    } else if (response.substring(62, 66).equals("UPD:")) {
                                        String[] allInfo = response.substring(66, response.length() - 14).split("DEL:");
                                        if (allInfo[0].equals("OK")) {
                                        } else {
                                            String[] info = allInfo[0].split(msgSep);
                                            for (int m = 0; m < messages.size(); m++) {
                                                for (int n = 0; n < info.length; n++) {
                                                    String[] nComps = info[n].split(inMsgSep);
                                                    if (messages.get(m).id.equals(nComps[0])) {
                                                        ConstraintLayout cl = findViewById(messages.get(m).clId);
                                                        LinearLayout llMess = (LinearLayout) ((ConstraintLayout) ((ConstraintLayout)
                                                                cl.getChildAt(1)).getChildAt(1)).getChildAt(0);

                                                        if (!messages.get(m).text.equals(nComps[1])) {
                                                            Msg nMsg = new Msg(messages.get(m).id, nComps[1], messages.get(m).admn,
                                                                    messages.get(m).time, messages.get(m).seen, messages.get(m).clId);
                                                            messages.remove(m);
                                                            messages.add(m, nMsg);
                                                            ((TextView) llMess.getChildAt(0)).setText(Main.fixEncoding(nComps[1]));
                                                        }
                                                        boolean nSeen = nComps[4].equals("1");
                                                        if (messages.get(m).seen != nSeen) {
                                                            Msg nMsg = new Msg(messages.get(m).id, messages.get(m).text, messages.get(m).admn,
                                                                    messages.get(m).time, nSeen, messages.get(m).clId);
                                                            messages.remove(m);
                                                            messages.add(m, nMsg);
                                                            View iv = ((LinearLayout) llMess.getChildAt(1)).getChildAt(0);
                                                            if (iv.getClass().getName().equalsIgnoreCase(Main.ivType)) {
                                                                int seenImg;
                                                                if (nSeen) seenImg = 1;
                                                                else seenImg = 0;
                                                                ((ImageView) iv).setImageResource(seenImgs[seenImg]);
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            for (int n = 0; n < info.length; n++) {
                                                String[] nComps = info[n].split(inMsgSep);
                                                boolean Found = false;
                                                for (int m = 0; m < messages.size(); m++) {
                                                    if (messages.get(m).id.equals(nComps[0]))
                                                        Found = true;
                                                }
                                                if (!Found) {
                                                    messages.add(new Msg(nComps[0], Main.fixEncoding(nComps[1]), nComps[2].equals("1"),
                                                            nComps[3], nComps[4].equals("1"), View.generateViewId()));
                                                    boolean fPrsn;
                                                    if (isAdmin)
                                                        fPrsn = messages.get(messages.size() - 1).admn;
                                                    else
                                                        fPrsn = !messages.get(messages.size() - 1).admn;
                                                    int seen = 0;
                                                    if (nComps[4].equals("1")) seen = 1;
                                                    addMessage(fPrsn, Main.fixEncoding(nComps[1]), messages.get(messages.size() - 1).clId,
                                                            nComps[3], seen);
                                                }
                                            }
                                        }

                                        if (allInfo[1].equals("OK")) {
                                        } else {
                                            String[] delInfo = allInfo[1].split(idSep);
                                            ArrayList<Integer> removal = new ArrayList<>();

                                            for (int m = 0; m < messages.size(); m++) {
                                                for (int d = 0; d < delInfo.length; d++)//IK HOU VAN YE!!!
                                                    if (messages.get(m).id.equals(delInfo[d]))
                                                        removal.add(m);
                                            }
                                            for (int r = removal.size() - 1; r >= 0; r--) {
                                                //sLL.removeView(findViewById(messages.get(removal.get(r)).clId));
                                                sLL.removeViewAt(removal.get(r));
                                                messages.remove((int) removal.get(r));
                                            }
                                        }
                                    }
                                } else if (response.equals("signInFailed")) {
                                    Toast.makeText(Messenger.this, R.string.error8, Toast.LENGTH_LONG).show();
                                    Main.signOut(sp);
                                    startActivity(new Intent(Messenger.this, Main.class));
                                } else if (response.equals("signIn2Failed")) {
                                    Toast.makeText(Messenger.this, R.string.sError3, Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                } else if (response.equals("empty")) {
                                    sLL.removeAllViews();
                                    Toast.makeText(Messenger.this, R.string.sEmpty, Toast.LENGTH_LONG).show();
                                } else if (response.equals("insufficientData")) {
                                    Toast.makeText(Messenger.this, R.string.sError2, Toast.LENGTH_LONG).show();
                                } else {//if (response.substring(0, 7).equals("Error: "))
                                    Main.makeBasicMessage(Messenger.this, response);
                                }
                            } else
                                Toast.makeText(Messenger.this, R.string.sError1, Toast.LENGTH_LONG).

                                        show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Messenger.this, R.string.sError1, Toast.LENGTH_LONG).show();
                    checking = false;
                }
            });
            stringRequest.setTag(rqtCheck);
            checkQueue.add(stringRequest);
            checking = true;
        } else Toast.makeText(Messenger.this, R.string.error3, Toast.LENGTH_SHORT).

                show();
    }

    /*void reCheck(int delay) {
        ValueAnimator an = Main.mVA(sCheckMotor, "translationX", delay, 0f, (float) (sBody.getWidth() - sCheckMotor.getWidth()));
        an.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                check();
            }
        });
    }*/

    void action(final String what, final String prop) {
        if (isOnline()) {
            final int ID = View.generateViewId();
            if (what.equals("post")) {
                addMessage(true, prop, ID, "now", -1);//messages.get(messages.size() - 1).clId
                sNewET.setText("");
            }

            String PROP = prop;
            try {
                PROP = URLEncoder.encode(prop, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            RequestQueue actionQueue = Volley.newRequestQueue(Messenger.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, ourCloudFolder + cloudMessenger +
                    "?user=" + Main.user + "&pass=" + Main.pass + "&type=" + type + "&contact=" + to + "&act=" + what + "&prop=" + PROP,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != null) {
                                if (response.substring(0, 4).equals("done")) {
                                    String[] adt;
                                    ConstraintLayout cl;

                                    switch (what) {
                                        case "post":
                                            actionEnd(what, true, null, null);
                                            adt = response.substring(4).split(postAdtSep);
                                            messages.add(new Msg(adt[0], prop, isAdmin, adt[1], false, ID));

                                            cl = findViewById(ID);
                                            LinearLayout llMess = (LinearLayout) ((ConstraintLayout) ((ConstraintLayout)
                                                    cl.getChildAt(1)).getChildAt(1)).getChildAt(0);
                                            LinearLayout llInfo = (LinearLayout) llMess.getChildAt(1);
                                            View iv = llInfo.getChildAt(0);
                                            if (iv.getClass().getName().equalsIgnoreCase(Main.ivType))
                                                ((ImageView) iv).setImageResource(seenImgs[0]);

                                            String[] D = adt[1].split(" "), DATE = D[0].split("-"), TIME = D[1].split(":");
                                            String dt = "";
                                            try {
                                                dt = DATE[2] + " " + months[Integer.parseInt(DATE[1]) - 1];
                                            } catch (NumberFormatException ignored) {
                                            }
                                            if (llInfo.getChildCount() == 1)
                                                ((TextView) llInfo.getChildAt(0))
                                                        .setText(dt + " - " + leadingZero(TIME[0], 2) + ":" + leadingZero(TIME[1], 2));
                                            if (llInfo.getChildCount() == 2)
                                                ((TextView) llInfo.getChildAt(1))
                                                        .setText(dt + " - " + leadingZero(TIME[0], 2) + ":" + leadingZero(TIME[1], 2));
                                            break;
                                        case "delete":
                                            adt = response.substring(4).split(idSep);
                                            for (int m = messages.size() - 1; m >= 0; m--) {
                                                for (int d = adt.length; d >= 0; d--) {
                                                    if (m < messages.size() && d < adt.length) {
                                                        if (messages.get(m).id.equals(adt[d])) {
                                                            cl = findViewById(messages.get(m).clId);
                                                            sLL.removeView(cl);
                                                            messages.remove(m);
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        case "edit":
                                            break;
                                    }
                                } else if (response.equals("fail")) {
                                    actionEnd(what, false, prop, getResources().getString(R.string.sError7));
                                } else if (response.equals("signInFailed")) {
                                    Toast.makeText(Messenger.this, R.string.error8, Toast.LENGTH_LONG).show();
                                    Main.signOut(sp);
                                    startActivity(new Intent(Messenger.this, Main.class));
                                } else if (response.equals("signIn2Failed")) {
                                    Toast.makeText(Messenger.this, R.string.sError3, Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                } else if (response.equals("insufficientData")) {
                                    actionEnd(what, false, prop, getResources().getString(R.string.sError2));
                                } else actionEnd(what, false, prop, response);
                            } else actionEnd(what, false, prop, null);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    actionEnd(what, false, prop, null);
                }
            });
            stringRequest.setTag(what);
            actionQueue.add(stringRequest);

            if (what.equals("post")) {
                anRotat1(sNewETAft);
                posting = true;
            }
        } else Toast.makeText(Messenger.this, R.string.error3, Toast.LENGTH_SHORT).show();
    }

    public static String leadingZero(String num, int length) {
        StringBuilder sb = new StringBuilder();
        for (int z = 0; z < length - num.length(); z++) {
            sb.append("0");
        }
        sb.append(num);
        return sb.toString();
    }

    void anRotat1(final View v) {
        ValueAnimator an = Main.mVA(v, "rotationY", 522, 0f, 180f);
        an.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (posting) anRotat1(v);
            }
        });
    }

    void actionEnd(String what, boolean success, String prop, String error) {
        switch (what) {
            case "post":
                if (!success) {
                    if (error != null)
                        Toast.makeText(Messenger.this, R.string.sError4, Toast.LENGTH_LONG).show();
                    else Toast.makeText(Messenger.this, error, Toast.LENGTH_LONG).show();
                    erPost += 1;
                    if (erPost <= erMax1) action(what, prop);
                    else {
                        erPost = 0;
                        posting = false;
                    }
                } else posting = false;
                break;
            case "delete":
                if (!success) {
                    if (error != null)
                        Toast.makeText(Messenger.this, R.string.sError6, Toast.LENGTH_LONG).show();
                    else Toast.makeText(Messenger.this, error, Toast.LENGTH_LONG).show();
                    erDelete += 1;
                    if (erDelete <= erMax1) action(what, prop);
                    else erDelete = 0;
                }
                break;
            case "edit":
                if (error != null)
                    Toast.makeText(Messenger.this, R.string.sError5, Toast.LENGTH_LONG).show();
                else Toast.makeText(Messenger.this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    void closeTB2() {
        selecting = false;
        selection = null;
        for (int v = 0; v < sLL.getChildCount(); v++) {
            View bg = (View) ((ConstraintLayout) sLL.getChildAt(v)).getChildAt(0);
            Main.mOA(bg, "alpha", 0f, vBGDur);
        }
        sToolbar2.setVisibility(View.INVISIBLE);
        sToolbarAB.show();
    }


    class Msg {
        final String id, text, time;
        final boolean admn, seen;
        final int clId;//, tvId;

        private Msg(String id, String text, boolean admn, String time, boolean seen, int clId) {//, int tvId
            this.id = id;
            this.text = text;
            this.admn = admn;
            this.time = time;
            this.seen = seen;
            this.clId = clId;
            //this.tvId = tvId;
        }
    }
}

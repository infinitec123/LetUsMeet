package infiniteloop.letusmeet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;


import com.appyvet.rangebar.IRangeBarFormatter;
import com.appyvet.rangebar.RangeBar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import infiniteloop.letusmeet.rules.DecisionService;

public class ControlPanelActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ControlPanelActivity";

    private static final String[] COUNTRIES = new String[]{
            "Fashion", "Mobiles", "Mobile Accessories", "TV"
    };
    View timeSelect;
    View offersSelect;
    CacheUtils cacheUtils;
    MultiAutoCompleteTextView offersSuggestions;
    int startTimeIndex = 9, endTimeIndex = 15;

    @OnClick(R.id.new_appvice_block)
    void controlAppVice() {
        Intent intent = new Intent(this, AppViceActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        DecisionService decisionService = DecisionService.getInstance(this);
        decisionService.init();
        cacheUtils = new CacheUtils(this);
        initViews();
        setTimeSeekBar();
        setMultiSearchView();
        setOnClickListeners();

        Log.d(TAG, decisionService.getModel("vibhulabs.shopperbuddy", "shipped sadas", false) + "");
        Log.d(TAG, decisionService.getModel("vibhulabs.shopperbuddy", "offer sadas", false) + "");
    }

    private void initViews() {
        if (cacheUtils.isInterestedInOffers()) {
            ((ImageView) findViewById(R.id.offers_icon)).setImageResource(R.drawable.offers_icon_big);
        } else {
            ((ImageView) findViewById(R.id.offers_icon)).setImageResource(R.drawable.offers_icon_big_disabled);
        }

        if (cacheUtils.isInterestedInTime()) {
            ((ImageView) findViewById(R.id.time_icon)).setImageResource(R.drawable.time_icon_big);
        } else {
            ((ImageView) findViewById(R.id.time_icon)).setImageResource(R.drawable.time_icon_big_disabled);
        }
        if (cacheUtils.isInterestedInLocation()) {
            ((ImageView) findViewById(R.id.location_icon)).setImageResource(R.drawable.location_icon_big);
        } else {
            ((ImageView) findViewById(R.id.location_icon)).setImageResource(R.drawable.location_icon_big_disabled);
        }

    }

    private void setTimeSeekBar() {
        cacheUtils.setInterestedInTime(false);
        timeSelect = findViewById(R.id.card_view_5);
        RangeBar rangeBar = (RangeBar) findViewById(R.id.rangebar);
        rangeBar.setRangePinsByIndices(36, 72);
        rangeBar.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String value) {
                Integer integer = Integer.parseInt(value);
                StringBuilder time = new StringBuilder();
                time = time.append((integer / 4)).append(":");
                switch (integer % 4) {
                    case 0:
                        time = time.append("00");
                        break;
                    case 1:
                        time = time.append("15");
                        break;
                    case 2:
                        time = time.append("30");
                        break;
                    case 3:
                        time = time.append("45");
                        break;
                }
                return time.toString();
            }
        });
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                /*Log.d(TAG,"left"+leftPinIndex);
                Log.d(TAG,"left"+rightPinIndex);*/
                startTimeIndex = leftPinIndex / 4;
                endTimeIndex = rightPinIndex / 4;
                cacheUtils.setStartTimeIndex(startTimeIndex);
                cacheUtils.setEndTimeIndex(endTimeIndex);
            }
        });
    }

    private void setMultiSearchView() {
        cacheUtils.setInterestedInOffers(false);
        offersSelect = findViewById(R.id.card_view_6);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        offersSuggestions = (MultiAutoCompleteTextView) findViewById(R.id.offersSuggestions);
        offersSuggestions.setAdapter(adapter);
        offersSuggestions.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        offersSuggestions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s))
                    cacheUtils.setOffersPreferences(s.toString());
            }
        });
        String offers = cacheUtils.getOffersPreferences();
        if (!TextUtils.isEmpty(offers)) {
            offersSuggestions.setText(offers);
        }
    }

    @Override
    protected void onPause() {

        super.onPause();

    }

    private void setOnClickListeners() {
        (findViewById(R.id.time_icon)).setOnClickListener(this);
        (findViewById(R.id.location_icon)).setOnClickListener(this);
        (findViewById(R.id.offers_icon)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time_icon:
                if (cacheUtils.isInterestedInTime()) {
                    timeSelect.setVisibility(View.GONE);
                    cacheUtils.setInterestedInTime(false);
                    ((ImageView) findViewById(R.id.time_icon)).setImageResource(R.drawable.time_icon_big_disabled);
                } else {
                    cacheUtils.setStartTimeIndex(startTimeIndex);
                    cacheUtils.setEndTimeIndex(endTimeIndex);
                    timeSelect.setVisibility(View.VISIBLE);
                    cacheUtils.setInterestedInTime(true);
                    ((ImageView) findViewById(R.id.time_icon)).setImageResource(R.drawable.time_icon_big);
                }
                break;
            case R.id.location_icon:
                //timeSelect.setVisibility(View.VISIBLE);
                break;
            case R.id.offers_icon:
                if (offersSelect.getVisibility() == View.VISIBLE) {
                    offersSelect.setVisibility(View.GONE);
                    cacheUtils.setInterestedInOffers(false);
                    ((ImageView) findViewById(R.id.offers_icon)).setImageResource(R.drawable.offers_icon_big_disabled);

                } else {
                    offersSelect.setVisibility(View.VISIBLE);
                    cacheUtils.setInterestedInOffers(true);
                    ((ImageView) findViewById(R.id.offers_icon)).setImageResource(R.drawable.offers_icon_big);

                }
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_block:
                startActivity(new Intent(this, BlockedNotificationsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package infiniteloop.letusmeet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import infiniteloop.letusmeet.rules.NotificationModel;

public class CacheUtils {

    private static final String TAG = "CacheUtils";

    /* Reference to shared preference */
    private SharedPreferences mSharedPreferences;


    private static String sTimePreferenceKey = "key_time_preference_key";
    private static String sLocationPreferenceKey = "key_location_preference_key";
    private static String sOffersPreferenceKey = "key_offer_preference_key";
    private static String sSocialPreferenceKey = "key_social_preference_key";
    private static String sOffersPreferencesKey = "key_offers_preference_key";
    private static String sStartTimeIndex = "key_start_time_preference_key";
    private static String sEndTimeIndex = "key_end_time_preference_key";


    public CacheUtils(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //*********************************************************************
    // APIs : Fields
    //*********************************************************************

    public boolean isInterestedInTime() {
        return mSharedPreferences.getBoolean(sTimePreferenceKey, false);
    }

    public void setInterestedInTime(final boolean value) {
        mSharedPreferences.edit().putBoolean(sTimePreferenceKey, value).apply();
    }


    public boolean isInterestedInLocation() {
        return mSharedPreferences.getBoolean(sLocationPreferenceKey, false);
    }

    public void setInterestedInLocation(final boolean value) {
        mSharedPreferences.edit().putBoolean(sLocationPreferenceKey, value).apply();
    }


    public boolean isInterestedInOffers() {
        return mSharedPreferences.getBoolean(sOffersPreferenceKey, false);
    }

    public void setInterestedInOffers(final boolean value) {
        mSharedPreferences.edit().putBoolean(sOffersPreferenceKey, value).apply();
    }


    public boolean isInterestedInSocialUpdates() {
        return mSharedPreferences.getBoolean(sLocationPreferenceKey, false);
    }

    public void setInterestedInSocialUpdates(final boolean value) {
        mSharedPreferences.edit().putBoolean(sLocationPreferenceKey, value).apply();
    }

    public String getOffersPreferences() {
        return mSharedPreferences.getString(sOffersPreferencesKey, "");
    }

    public void setOffersPreferences(final String value) {
        mSharedPreferences.edit().putString(sOffersPreferencesKey, value).apply();
    }

    public int getStartTimeIndex() {
        return mSharedPreferences.getInt(sStartTimeIndex, -1);
    }

    public void setStartTimeIndex(int start) {
        mSharedPreferences.edit().putInt(sStartTimeIndex, start).apply();
    }

    public int getEndTimeIndex() {
        return mSharedPreferences.getInt(sEndTimeIndex, -1);
    }

    public void setEndTimeIndex(int end) {
        mSharedPreferences.edit().putInt(sEndTimeIndex, end).apply();
    }


    private static String sBlockedPreferencesKey = "key_blocked_preference_key";

    public List<String> getBlockedNotifications() {
        String s = mSharedPreferences.getString(sBlockedPreferencesKey, "");
        if (TextUtils.isEmpty(s)) {
            return null;
        }

        String[] stringArray = s.split("##");
        ArrayList<String> notifications = new ArrayList<>();

        for (String s1 : stringArray) {
            String[] sss = s1.split("$");
            notifications.add(sss[0] + ":" + sss[1]);
        }

        return notifications;
    }


    public void saveIntoBlockedNotifications(String title, String desc) {

        String builder = mSharedPreferences.getString(sBlockedPreferencesKey, "");
        builder += "##" + title + "$" + desc;
        mSharedPreferences.edit().putString(sBlockedPreferencesKey, builder).apply();
    }

    //*********************************************************************
    // End of class
    //*********************************************************************

}

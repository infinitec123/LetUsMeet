package infiniteloop.letusmeet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CacheUtils {

    private static final String TAG = "CacheUtils";

    /* Reference to shared preference */
    private SharedPreferences mSharedPreferences;


    private static String sTimePreferenceKey = "key_time_preference_key";
    private static String sLocationPreferenceKey = "key_location_preference_key";
    private static String sOffersPreferenceKey = "key_offer_preference_key";
    private static String sSocialPreferenceKey = "key_social_preference_key";
    private static String sOffersPreferencesKey = "key_offers_preference_key";


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


    //*********************************************************************
    // End of class
    //*********************************************************************

}

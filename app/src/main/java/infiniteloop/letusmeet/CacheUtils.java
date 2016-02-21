package infiniteloop.letusmeet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class CacheUtils {

    private static final String TAG = "CacheUtils";

    /* Reference to shared preference */
    private SharedPreferences mSharedPreferences;

    /**
     * Keys
     */
    private static String sIsGcmRegistrationCompleteKey = "isGcmRegistrationComplete";
    private static String sUserNameKey = "user_name";
    private static String sUserProfilePicPath = "user_profile_pic_path";

    private static String sLastKnownLatitudeKey = "last_known_latitude";
    private static String sLastKnownLongitudeKey = "last_known_longitude";
    private static String sLoginComplete = "login_complete";

    private static String sCoverPicVersion = "cover_pic_version";
    private static String sLogoPicVersion = "logo_pic_version";
    private static String sProfilePicVersion = "profile_pic_version";


    public CacheUtils(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //*********************************************************************
    // APIs : Login related
    //*********************************************************************

    public boolean isUserLoginComplete() {
        return mSharedPreferences.getBoolean(sLoginComplete, false);
    }

    public void setUserLoginStatus(final boolean value) {
        mSharedPreferences.edit().putBoolean(sLoginComplete, value).apply();
    }

    //*********************************************************************
    // APIs : GCM Related
    //*********************************************************************


    public boolean isGCMRegistrationComplete() {
        return mSharedPreferences.getBoolean(sIsGcmRegistrationCompleteKey, false);
    }

    public void setGCMRegistrationStatus(final boolean value) {
        mSharedPreferences.edit().putBoolean(sIsGcmRegistrationCompleteKey, value).apply();
        Log.v(TAG, "Successfully set the registration setting");
    }

    //*********************************************************************
    // Registration Related
    //*********************************************************************

    public void saveUserName(String name) {
        mSharedPreferences.edit().putString(sUserNameKey, name).apply();
    }

    public String getUserName() {
        return mSharedPreferences.getString(sUserNameKey, "Unknown");
    }


    public void saveProfilePicPath(String path) {
        mSharedPreferences.edit().putString(sUserProfilePicPath, path).apply();
    }

    public String getProfilePicPath() {
        return mSharedPreferences.getString(sUserProfilePicPath, null);
    }


    //*********************************************************************
    // Location Related
    //*********************************************************************

    public void saveLastKnownLocation(double lat, double lng) {
        mSharedPreferences.edit().putLong(sLastKnownLatitudeKey, Double.doubleToRawLongBits(lat)).apply();
        mSharedPreferences.edit().putLong(sLastKnownLongitudeKey, Double.doubleToRawLongBits(lng)).apply();
    }

    public double getLastKnownLatitude() {
        return Double.longBitsToDouble(mSharedPreferences.getLong(sLastKnownLatitudeKey, Double.doubleToLongBits(0d)));
    }

    public double getLastKnownLongitude() {
        return Double.longBitsToDouble(mSharedPreferences.getLong(sLastKnownLongitudeKey, Double.doubleToLongBits(0d)));
    }

    //*********************************************************************
    // Cover Image cacheing related
    //*********************************************************************

    public Long getCoverPicVersion() {
        return mSharedPreferences.getLong(sCoverPicVersion, 1L);
    }

    public void incrementCoverPicVersion() {
        Long currentVersion = getCoverPicVersion();
        currentVersion++;
        mSharedPreferences.edit().putLong(sCoverPicVersion, currentVersion).apply();
    }

    public Long getLogoPicVersion() {
        return mSharedPreferences.getLong(sLogoPicVersion, 1L);
    }

    public void incrementLogoPicVersion() {
        Long currentVersion = getLogoPicVersion();
        currentVersion++;
        mSharedPreferences.edit().putLong(sLogoPicVersion, currentVersion).apply();
    }


    public Long getProfilePicVersion() {
        return mSharedPreferences.getLong(sProfilePicVersion, 1L);
    }

    public void incrementProfilePicVersion() {
        Long currentVersion = getProfilePicVersion();
        currentVersion++;
        mSharedPreferences.edit().putLong(sProfilePicVersion, currentVersion).apply();
    }

    //*********************************************************************
    // End of class
    //*********************************************************************

}

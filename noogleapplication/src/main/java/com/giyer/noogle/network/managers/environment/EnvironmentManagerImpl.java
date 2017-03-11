package com.giyer.noogle.network.managers.environment;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.view.Display;
import android.view.WindowManager;

import com.giyer.noogle.network.base.BaseManager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by giyer7 on 3/8/17.
 */

public class EnvironmentManagerImpl extends BaseManager implements EnvironmentManager {
    protected String applicationVersion;
    protected String applicationAddress;
    protected String deviceID;
    protected String deviceModel;
    protected String oSVersion;
    protected String formFactor;
    protected String platformOS;
    protected String deviceToken;
    protected String httpUserAgent;
    private boolean mIsInitialized = false;
    // Log Item information:
    private PackageInfo packageInfo;
    private int screenWidth;
    private int screenHeight;
    private String deviceCountry;
    private String deviceLanguage;
    private java.util.Locale locale;
    private String localeCode;
    private String timezoneOffset;

    public static EnvironmentManager getInstance() {
        return EnvironmentManagerHolder.INSTANCE;
    }

    /**
     * get current timestamp in seconds
     *
     * @return the current timestamp
     */
    public static Long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * Checks if the timestamp passed in is less than 4 seconds old
     *
     * @param timestamp the timestamp needing validation
     * @return true if the timestamp is less than 4 seconds old
     */
    public static boolean isValid(Long timestamp) {
        return isValid(timestamp, 4);
    }

    /**
     * Checks if the timestamp passed in is less than the seconds passed in
     *
     * @param timestamp the timestamp needing validation
     * @param seconds   how long the timestamp is valid for
     * @return true if the timestamp is less than the number of seconds passed in
     */
    public static boolean isValid(Long timestamp, int seconds) {
        return Math.abs(timestamp - getCurrentTimestamp()) < seconds;
    }


    // Prevents two instances when de-serializing
    protected Object readResolve() {
        return getInstance();
    }

    public java.util.Locale getLocale() {
        return locale;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void initialize(final Context context) {
        mIsInitialized = true;
        oSVersion = Build.VERSION.RELEASE;
        deviceID = getAndroidId(context);
        //replace all whitespace with empty strings
        deviceModel = Build.MANUFACTURER.replaceAll("\\s", "") + "_" + Build.MODEL.replaceAll("\\s", "");
        formFactor = "Phone";
        applicationAddress = context.getPackageName();
        platformOS = "Android";
        httpUserAgent = "mock";
        // Limiting to 5 chars since DB has a limit of 5 for Timezone Offset column in the
        // DEVICE_INFO table
        timezoneOffset = String.valueOf(calculateTimeZoneOffset()).length() > 5 ? String.valueOf(calculateTimeZoneOffset()).substring(0, 5) : String.valueOf(calculateTimeZoneOffset());

        // Logger related device information
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            mLogger.e("Package Name not found:");
        }

        final WindowManager windowManager = (WindowManager) context.getSystemService(android.content.Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        applicationVersion = packageInfo.versionName;
        locale = context.getResources().getConfiguration().locale;
        deviceCountry = context.getResources().getConfiguration().locale.getCountry();
        deviceLanguage = context.getResources().getConfiguration().locale.getLanguage();
        localeCode = deviceLanguage + "_" + deviceCountry;
    }

    public boolean isinitialized() {
        return mIsInitialized;
    }

    public String getDeviceCountry() {
        return deviceCountry;
    }

    public void setDeviceCountry(String deviceCountry) {
        this.deviceCountry = deviceCountry;
    }

    public String getDeviceLanguage() {
        return deviceLanguage;
    }

    public void setDeviceLanguage(String deviceLanguage) {
        this.deviceLanguage = deviceLanguage;
    }

    public String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = Build.ID + (androidId == null ? "" : androidId);
        if ("".equals(deviceId))
            return "EMULATOR";
        return String.valueOf(deviceId);
    }

    public static long calculateTimeZoneOffset() {
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        int mGMTOffset = mTimeZone.getRawOffset();
        System.out.printf("GMT offset is %s hours", TimeUnit.HOURS.convert(mGMTOffset, TimeUnit.MILLISECONDS));
        return TimeUnit.HOURS.convert(mGMTOffset, TimeUnit.MILLISECONDS);
    }

    public boolean ismIsInitialized() {
        return mIsInitialized;
    }

    public void setmIsInitialized(boolean mIsInitialized) {
        this.mIsInitialized = mIsInitialized;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(String timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public String getApplicationAddress() {
        return applicationAddress;
    }

    public void setApplicationAddress(String applicationAddress) {
        this.applicationAddress = applicationAddress;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getoSVersion() {
        return oSVersion;
    }

    public void setoSVersion(String oSVersion) {
        this.oSVersion = oSVersion;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public String getPlatformOS() {
        return platformOS;
    }

    public void setPlatformOS(String platformOS) {
        this.platformOS = platformOS;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    public void setHttpUserAgent(String httpUserAgent) {
        this.httpUserAgent = httpUserAgent;
    }

    private static class EnvironmentManagerHolder {
        public static final EnvironmentManagerImpl INSTANCE = new EnvironmentManagerImpl();
    }
}

package com.example.bendb.faceunlockdemo;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.PackageManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author bendb
 */
public class LockUtils {
    public static final String FACE_UNLOCK_PACKAGE = "com.android.facelock";

    private static final String LOCK_PATTERN_UTILS = "com.android.internal.widget.LockPatternUtils";
    private static final String GET_ACTIVE_PASSWORD_QUALITY = "getActivePasswordQuality";

    private static final Object sReflectionLock = new Object();
    private static Object sLockPatternUtils;
    private static Method sGetActivePasswordQuality;

    public static boolean hasFaceUnlock(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(FACE_UNLOCK_PACKAGE, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            return false;
        }

        if (getDevicePolicyManager(context).getCameraDisabled(null)) {
            return false;
        }

        int mode = getActivePasswordQuality(context);

        if (mode == DevicePolicyManager.PASSWORD_QUALITY_BIOMETRIC_WEAK) {
            return true;
        }

        return false;
    }

    private static DevicePolicyManager getDevicePolicyManager(Context context) {
        return (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    private static int getActivePasswordQuality(Context context) {
        Method method;
        Object target;
        synchronized (sReflectionLock) {
            if (sGetActivePasswordQuality == null || sLockPatternUtils == null) {
                Class<?> lockPatternUtils;
                try {
                    lockPatternUtils = Class.forName(LOCK_PATTERN_UTILS);
                    sGetActivePasswordQuality = lockPatternUtils.getDeclaredMethod(GET_ACTIVE_PASSWORD_QUALITY);
                    sLockPatternUtils = lockPatternUtils
                            .getConstructor(Context.class)
                            .newInstance(context.getApplicationContext());
                } catch (ClassNotFoundException e) {
                    return 0;
                } catch (NoSuchMethodException e) {
                    return 0;
                } catch (InvocationTargetException e) {
                    return 0;
                } catch (InstantiationException e) {
                    return 0;
                } catch (IllegalAccessException e) {
                    return 0;
                }
            }

            method = sGetActivePasswordQuality;
            target = sLockPatternUtils;
        }

        try {
            return (Integer) method.invoke(target);
        } catch (IllegalAccessException e) {
            return 0;
        } catch (InvocationTargetException e) {
            return 0;
        }
    }
}

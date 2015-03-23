package org.androidrubick.utils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * 
 * 生成Device ID的工具类。
 * 
 * @author yong01.yin
 * 
 * @see {@link org.androidrubick.utils.AndroidUtils#getDeviceId()}
 *
 */
public class DeviceUuidFactory {
	private final String LOG_TAG = DeviceUuidFactory.class.getSimpleName();
	protected static final String PREFS_FILE = "device_uuid_file";
	protected static final String PREFS_VALUE_DEVICE_ID = "device_uuid";
	protected static final String PREFS_VALUE_MID = "device_mid";

	protected volatile static String uuid = null;

	public DeviceUuidFactory(Context context) {
		synchronized (DeviceUuidFactory.class) {
			if (TextUtils.isEmpty(uuid)) {
				uuid = PreferenceUtils.getValue(context, PREFS_FILE, PREFS_VALUE_DEVICE_ID, null);

				if (TextUtils.isEmpty(uuid)) {
					final String androidId = this.getAndroidId(context);
					final String imei = this.getImei(context);
					final String imsi = this.getImsi(context);

					String firstId = null;
					String secondId = null;
					if (!TextUtils.isEmpty(androidId)) {
						firstId = androidId;
					} else {
						if (!TextUtils.isEmpty(imsi)) {
							firstId = imsi;
						}
					}
					if (!TextUtils.isEmpty(imei)) {
						secondId = imei;
					} else {
						if (TextUtils.isEmpty(androidId)) {
							final String macAddr = this
									.getWifiMacAddress(context);

							if (!TextUtils.isEmpty(macAddr)) {
								secondId = macAddr;
							}
						}
					}

					if (!TextUtils.isEmpty(firstId)
							|| !TextUtils.isEmpty(secondId)) {
						String fullId = firstId + secondId;
						try {
							uuid = UUID.nameUUIDFromBytes(
									fullId.getBytes("utf8")).toString();
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}

					if (TextUtils.isEmpty(uuid)) {
						Log.w(LOG_TAG,
								"Cannot find any unique ID for this device, try random ID...");
						uuid = UUID.randomUUID().toString();
					}
					
					PreferenceUtils.saveValue(context, PREFS_FILE, PREFS_VALUE_DEVICE_ID, uuid);
				}
			}
		}
	}

	/**
	 * Returns a unique UUID for the current android device. As with all UUIDs,
	 * this unique ID is "very highly likely" to be unique across all Android
	 * devices. Much more so than ANDROID_ID is.
	 * 
	 * The UUID is generated by using ANDROID_ID as the base key if appropriate,
	 * falling back on TelephonyManager.getDeviceID() if ANDROID_ID is known to
	 * be incorrect, and finally falling back on a random UUID that's persisted
	 * to SharedPreferences if getDeviceID() does not return a usable value.
	 * 
	 * In some rare circumstances, this ID may change. In particular, if the
	 * device is factory reset a new device ID may be generated. In addition, if
	 * a user upgrades their phone from certain buggy implementations of Android
	 * 2.2 to a newer, non-buggy version of Android, the device ID may change.
	 * Or, if a user uninstalls your app on a device that has neither a proper
	 * Android ID nor a Device ID, this ID may change on reinstallation.
	 * 
	 * Note that if the code falls back on using TelephonyManager.getDeviceId(),
	 * the resulting ID will NOT change after a factory reset. Something to be
	 * aware of.
	 * 
	 * Works around a bug in Android 2.2 for many devices when using ANDROID_ID
	 * directly.
	 * 
	 * @see http://code.google.com/p/android/issues/detail?id=10603
	 * 
	 * @return a UUID that may be used to uniquely identify your device for most
	 *         purposes.
	 */
	public String getDeviceUuid() {
		return uuid;
	}

	private String getAndroidId(Context context) {
		try {
			String androidId = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);
			// A bug in Android 2.2 for many devices have the same id of
			// '9774d56d682e549c'
			if (!"9774d56d682e549c".equals(androidId)) {
				return androidId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	private String getImei(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String deviceId = tm.getDeviceId();
			// The imei may be '000000000000000' when device is rooted or
			// unlocked
			if (!"000000000000000".equals(deviceId)) {
				return deviceId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	private String getImsi(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getSubscriberId();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	private String getWifiMacAddress(Context context) {
		try {
			WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wi = wm.getConnectionInfo();
			return wi.getMacAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @param context
	 *            手机唯一识别符
	 * @return
	 */
	private static String mid;
	public static String getMid(Context context) {
		if (TextUtils.isEmpty(mid)) {
			mid = PreferenceUtils.getValue(context, PREFS_FILE, PREFS_VALUE_MID, null);
			if (TextUtils.isEmpty(mid)) {
				mid = UUID.randomUUID().toString();
				PreferenceUtils.saveValue(context, PREFS_FILE, PREFS_VALUE_MID, mid);
			}
		}
		return mid;
	}

}

package li.imu.lockout;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {

@Override protected void
onPostCreate(Bundle savedInstanceState) {
	super.onPostCreate(savedInstanceState);
	if(!Util.preventSettings(getApplicationContext()))
		addPreferencesFromResource(R.xml.pref_general);
}

@Override protected void
onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
	intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
			new ComponentName(this, DeviceAdmin.class));
	intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
			"Lock Out needs this to lock the screen.");
	startActivityForResult(intent, 1);
}

private static Preference.OnPreferenceChangeListener
sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
	@Override public boolean
	onPreferenceChange(Preference preference, Object value) {
		String stringValue = value.toString();
		preference.setSummary(stringValue);
		return true;
	}
};


private static void
bindPreferenceSummaryToValue(Preference preference) {
	preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
	sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
			PreferenceManager
					.getDefaultSharedPreferences(preference.getContext())
					.getString(preference.getKey(), ""));
}

}

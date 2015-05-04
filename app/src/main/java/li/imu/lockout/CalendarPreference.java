package li.imu.lockout;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.preference.ListPreference;
import android.provider.CalendarContract.Calendars;
import android.util.AttributeSet;

public class CalendarPreference extends ListPreference {

public
CalendarPreference(Context context){
	this(context, null);
}

public
CalendarPreference(Context context, AttributeSet attrs){
	super(context, attrs);
}

@Override
protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
	this.loadCalendars();
	super.onSetInitialValue(restoreValue, defaultValue);
}

private static final String[] CalendarFields = new String[]{
		Calendars._ID,
		Calendars.CALENDAR_DISPLAY_NAME,
};
private static final int IdFieldIndex = 0;
private static final int DisplayFieldIndex = 1;

protected void
loadCalendars(){
	CharSequence[] lEntries;
	CharSequence[] lEntryValues;
	ContentResolver cr = getContext().getContentResolver();
	Cursor lCursor = cr.query(Calendars.CONTENT_URI, CalendarFields, "", null, null);

	lEntries = new CharSequence[lCursor.getCount()+1];
	lEntryValues = new CharSequence[lCursor.getCount()+1];
	lEntries[0] = "None";
	lEntryValues[0] = "-1";
	lCursor.moveToFirst();
	for(int i = 0; i < lCursor.getCount(); i++){
		lEntryValues[i+1] = Long.toString(lCursor.getInt(IdFieldIndex));
		lEntries[i+1] = lCursor.getString(DisplayFieldIndex);
		lCursor.moveToNext();
	}
	this.setEntries(lEntries);
	this.setEntryValues(lEntryValues);
}

}

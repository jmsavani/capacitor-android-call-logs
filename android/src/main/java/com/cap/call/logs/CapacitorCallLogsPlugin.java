package com.cap.call.logs;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;

@CapacitorPlugin(name = "CapacitorCallLogs")
public class CapacitorCallLogsPlugin extends Plugin {

    private CapacitorCallLogs implementation = new CapacitorCallLogs();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");
        JSObject ret = new JSObject();
        checkLogPermission();
        JSONArray a = getCallDetails();
        ret.put("calls", a);
        call.resolve(ret);
    }

    @PluginMethod
    public void checkPermission(PluginCall call) {
        if (
            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED ||
            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_DENIED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[] { Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG },
                    2
                );
            }
        }
        JSObject ret = new JSObject();
        ret.put("checkPermission", "Checking ...");
        call.resolve(ret);
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        boolean callPermission =
            ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED;
        if (callPermission) {
            ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG);
        }
        JSObject ret = new JSObject();
        ret.put("isEnabled", callPermission);
        call.resolve(ret);
    }


    @PluginMethod
    public void getCallLogs(PluginCall call) {
        JSObject ret = new JSObject();
        JSONArray a = getCallDetails();
        ret.put("calls", a);
        call.resolve(ret);
    }

    private JSONArray getCallDetails() {
        StringBuffer sb = new StringBuffer();
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date d = new Date();
        calendar.set(Calendar.DATE, d.getDate());
        String[] whereValue = { String.valueOf(calendar.getTimeInMillis()), String.valueOf(new Timestamp(d.getTime())) };
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Cursor managedCursor = getActivity()
                .getApplicationContext()
                .getContentResolver()
                .query(Calls.CONTENT_URI, null, android.provider.CallLog.Calls.DATE + " BETWEEN ? AND ?", whereValue, strOrder);
            int number = managedCursor.getColumnIndex(Calls.NUMBER);
            int type = managedCursor.getColumnIndex(Calls.TYPE);
            int date = managedCursor.getColumnIndex(Calls.DATE);
            int duration = managedCursor.getColumnIndex(Calls.DURATION);
            sb.append("Call Details :");
            JSONArray jsonArray = new JSONArray();
            while (managedCursor.moveToNext()) {
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;
                    case Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;
                    case Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                JSObject details = new JSObject();
                details.put("number", phNumber);
                details.put("callType", dir);
                details.put("date", callDayTime);
                details.put("duration", callDuration);
                jsonArray.put(details);
            }
            managedCursor.close();
            return jsonArray;
        } else {
            try (
                Cursor managedCursor = getActivity()
                    .managedQuery(
                        CallLog.Calls.CONTENT_URI,
                        null,
                        android.provider.CallLog.Calls.DATE + " BETWEEN ? AND ?",
                        whereValue,
                        strOrder
                    )
            ) {
                int number = managedCursor.getColumnIndex(Calls.NUMBER);
                int type = managedCursor.getColumnIndex(Calls.TYPE);
                int date = managedCursor.getColumnIndex(Calls.DATE);
                int duration = managedCursor.getColumnIndex(Calls.DURATION);
                sb.append("Call Details :");
                JSONArray jsonArray = new JSONArray();
                while (managedCursor.moveToNext()) {
                    String phNumber = managedCursor.getString(number);
                    String callType = managedCursor.getString(type);
                    String callDate = managedCursor.getString(date);
                    Date callDayTime = new Date(Long.valueOf(callDate));
                    String callDuration = managedCursor.getString(duration);
                    String dir = null;
                    int dircode = Integer.parseInt(callType);
                    switch (dircode) {
                        case Calls.OUTGOING_TYPE:
                            dir = "OUTGOING";
                            break;
                        case Calls.INCOMING_TYPE:
                            dir = "INCOMING";
                            break;
                        case Calls.MISSED_TYPE:
                            dir = "MISSED";
                            break;
                    }
                    JSObject details = new JSObject();
                    details.put("number", phNumber);
                    details.put("callType", dir);
                    details.put("date", callDayTime);
                    details.put("duration", callDuration);
                    jsonArray.put(details);
                }
                managedCursor.close();
                return jsonArray;
            }
        }
    }

    private void checkLogPermission() {
        if (
            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED ||
            ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_DENIED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[] { Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG },
                    2
                );
            }
        }
    }

    private void checkEnable() {}
}

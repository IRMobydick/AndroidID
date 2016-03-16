package com.pocketscience.androidid;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Method;

/**
 * The main activity, which displays a button that displays the device IDs after clicking.
 */
public class MainActivity extends AppCompatActivity {

    Button bt;
    TextView idView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        bt = (Button) findViewById(R.id.button1);
        idView = (TextView) findViewById(R.id.textView1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imeistring = null;
                String imsistring = null;

                TelephonyManager telephonyManager = (TelephonyManager
                        ) getSystemService(Context.TELEPHONY_SERVICE);

                imeistring = telephonyManager.getDeviceId();
                idView.append("IMEI No : " + imeistring + "\n");

                imsistring = telephonyManager.getSubscriberId();
                idView.append("IMSI No : " + imsistring + "\n");

                String hwID = android.os.SystemProperties.get("ro.serialno", "unknown");
                idView.append("hwID : " + hwID + "\n");

                try {
                    Class<?> c = Class.forName("android.os.SystemProperties");
                    Method get = c.getMethod("get", String.class, String.class);
                    String serialnum = (String) (get.invoke(c, "ro.serialno", "unknown"));
                    idView.append("serial : " + serialnum + "\n");
                } catch (Exception ignored) {
                }

                try {
                    Class myclass = Class.forName("android.os.SystemProperties");
                    Method[] methods = myclass.getMethods();
                    Object[] params = new Object[]{new String("ro.serialno"), new String(
                            "Unknown")};
                    String serialnum2 = (String) (methods[2].invoke(myclass, params));
                    idView.append("serial2 : " + serialnum2 + "\n");
                } catch (Exception ignored) {
                }

                String androidId = Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                idView.append("AndroidID : " + androidId + "\n");
            }
        });
    }
}

package r2d2.lee2345.com.phonecontroller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static r2d2.lee2345.com.phonecontroller.ControlDetails.CALL_DURATION;
import static r2d2.lee2345.com.phonecontroller.ControlDetails.CALL_LEFT;
import static r2d2.lee2345.com.phonecontroller.ControlDetails.CALL_USED;
import static r2d2.lee2345.com.phonecontroller.ControlDetails.KEY_PHONE_CONTROL;

public class MainActivity extends AppCompatActivity {

    public static long callDuration;
    private EditText callDuration_tView;

    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences(KEY_PHONE_CONTROL.toString(), 0);

        SharedPreferences.Editor edit = settings.edit();

        initCallLeftPanel();
        initCallDurationView();
    }

    private void initCallLeftPanel() {
        int callLeft = settings.getInt(CALL_LEFT.toString(), 300);
        int callUsed = settings.getInt(CALL_USED.toString(), 0);
        TextView callLeft_tView = (TextView) findViewById(R.id.callLeft_textView);
        TextView callUsed_tView = (TextView) findViewById(R.id.callUsed_textView);
        callLeft_tView.setText(String.valueOf(callLeft));
        callUsed_tView.setText(String.valueOf(callUsed));
    }

    private void initCallDurationView() {
        callDuration = settings.getLong(CALL_DURATION.toString(), 600000);
        callDuration_tView= (EditText)findViewById(R.id.callDuration_editText);
        callDuration_tView.setText(String.valueOf(changeToSec(callDuration)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveButtonClicked(View view) {
        callDuration = changeToMiliSec(
                Integer.valueOf(callDuration_tView.getText().toString()));
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(CALL_DURATION.toString(), callDuration);
        editor.commit();
        Toast.makeText(this, "Call Duration is now " + changeToSec(callDuration) + " seconds!" , Toast.LENGTH_SHORT).show();
    }


    private int changeToSec(long miliSec) {
        return (int)miliSec/1000;
    }

    private long changeToMiliSec(int sec) {
        return (long) sec * 1000;
    }

}

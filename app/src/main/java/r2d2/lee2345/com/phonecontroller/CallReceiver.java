package r2d2.lee2345.com.phonecontroller;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by leesuckgeun on 15/08/09.
 */
public class CallReceiver extends PhonecallReceiver {

    private boolean callEndSuccess = false;


    @Override
    protected void onIncomingCallStarted(final Context ctx, String number, Date start) {
        Toast.makeText(ctx, "Incoming Call Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onOutgoingCallStarted(final Context ctx, String number, Date start) {
        Toast.makeText(ctx, "Outgoing Call Started", Toast.LENGTH_SHORT).show();
        endCall(ctx);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx, "Incoming Call Ended", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx, "Outgoing Call Ended", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        Toast.makeText(ctx, "Missed Call", Toast.LENGTH_SHORT).show();
    }

    private void endCall(final Context ctx) {
        TimerTask task  = new TimerTask() {
            @Override
            public void run() {
                while(!callEndSuccess)
                    callEndSuccess = killCall(ctx);
            }
        };
        new Timer().schedule(task, MainActivity.callDuration);
    }

    private boolean killCall(Context context) {
        try {
            // Get the boring old TelephonyManager
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            // Get the getITelephony() method
            Class classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");

            // Ignore that the method is supposed to be private
            methodGetITelephony.setAccessible(true);

            // Invoke getITelephony() to get the ITelephony interface
            Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);

            // Get the endCall method from ITelephony
            Class telephonyInterfaceClass =
                    Class.forName(telephonyInterface.getClass().getName());
            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");

            // Invoke endCall()
            methodEndCall.invoke(telephonyInterface);

        } catch (Exception ex) { // Many things can go wrong with reflection calls
//            Log.d(TAG, "PhoneStateReceiver **" + ex.toString());
            return false;
        }
        return true;
    }





}
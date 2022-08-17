package manhnguyen.broadcastreceiver.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (isNetworkVisible(context)) {
                Toast.makeText(context, "Internet Connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Internet Disconnected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isNetworkVisible(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager==null){
            return false;
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            Network network=manager.getActiveNetwork();
            if (network==null){
                return false;
            }else {
                NetworkCapabilities networkCapabilities=manager.getNetworkCapabilities(network);
                return networkCapabilities!=null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
            }
        }else {
            NetworkInfo networkInfo=manager.getActiveNetworkInfo();
            return networkInfo!=null && networkInfo.isConnected();
        }
    }
}

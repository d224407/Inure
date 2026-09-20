package app.simple.inure.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import app.simple.inure.constants.IntentConstants
import app.simple.inure.constants.LicenseConstants

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == IntentConstants.ACTION_VERIFICATION_RESPONSE) {
            Log.i(TAG, "Received license status: ${intent.getIntExtra(IntentConstants.EXTRA_LICENSE, -1)}")
            when (intent.getIntExtra(IntentConstants.EXTRA_LICENSE, -1)) {
                LicenseConstants.LICENSED -> {
                    Log.i(TAG, "Licensed")
                }
                LicenseConstants.NOT_LICENSED -> {
                    Log.i(TAG, "Not Licensed")
                }
                LicenseConstants.ERROR -> {
                    Log.i(TAG, "Error")
                }
                LicenseConstants.UNSPECIFIED -> {
                    Log.i(TAG, "Invalid status")
                }
            }

            sendLocalBroadcast(context!!, intent.getIntExtra(IntentConstants.EXTRA_LICENSE, -1))
        }
    }

    private fun sendLocalBroadcast(context: Context, status: Int) {
        Intent().apply {
            action = IntentConstants.ACTION_VERIFICATION_RESPONSE
            putExtra(IntentConstants.EXTRA_LICENSE, status)
            flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        }.also {
            LocalBroadcastManager.getInstance(context).sendBroadcast(it)
            Log.i(TAG, "Local broadcast sent with status: $status")
        }
    }

    companion object {

    }
}

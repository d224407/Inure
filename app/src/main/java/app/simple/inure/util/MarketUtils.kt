package app.simple.inure.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object MarketUtils {

    /**
     * Opens app's page on FDroid
     *
     * @param context Context of the environment
     * @param packageName Package ID of the app
     */
    fun openAppOnFdroid(context: Context, packageName: String) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.f-droid.org/en/packages/$packageName/"))
        context.startActivity(webIntent)
    }
}

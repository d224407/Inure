package app.simple.inure.util

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import app.simple.inure.BuildConfig
import app.simple.inure.apk.utils.PackageUtils.getPackageInfo

@Suppress("KotlinConstantConditions")
object AppUtils {


    /** Play Store flavor is intentionally disabled in this Google-free build. */
    fun isPlayFlavor(): Boolean = false

    /**
     * Returns true if the flavor is fdroid or github
     */
    fun isGithubFlavor(): Boolean {
        return BuildConfig.FLAVOR == "github"
    }

    /**
     * Returns true if the flavor is beta
     */
    fun isBetaFlavor(): Boolean {
        return BuildConfig.FLAVOR == "beta"
    }

    /**
     * Returns true if DEBUG
     */
    fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }
}

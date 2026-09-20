package app.simple.inure.viewmodels.launcher

import android.app.Application
import app.simple.inure.extensions.viewmodels.WrappedViewModel

class LauncherViewModel(application: Application) : WrappedViewModel(application) {

    fun initCheck() {
        // No-op: app-signature / unlocker verification has been removed.
    }

    companion object {
        private const val TAG = "LauncherViewModel"
    }
}

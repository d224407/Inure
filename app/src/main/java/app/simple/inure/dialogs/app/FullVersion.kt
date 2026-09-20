package app.simple.inure.dialogs.app

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import app.simple.inure.R
import app.simple.inure.decorations.ripple.DynamicRippleTextView
import app.simple.inure.dialogs.miscellaneous.Warning.Companion.TAG
import app.simple.inure.extensions.fragments.ScopedBottomSheetFragment
import app.simple.inure.interfaces.fragments.WarningCallbacks
import app.simple.inure.ui.panels.Trial


    private lateinit var trialInfo: DynamicRippleTextView
    private lateinit var close: DynamicRippleTextView
    private var warningCallbacks: WarningCallbacks? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        trialInfo = view.findViewById(R.id.trial_info)
        close = view.findViewById(R.id.close)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startPostponedEnterTransition()

        trialInfo.setOnClickListener {
            dismiss()
            openFragmentSlide(Trial.newInstance(), Trial.TAG)
        }

        }

        close.setOnClickListener {
            dismiss()
            if (!requireActivity().isDestroyed) {
                warningCallbacks?.onWarningDismissed()
            }
        }
    }

        this.warningCallbacks = warningCallbacks
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        if (!requireActivity().isDestroyed) {
            warningCallbacks?.onWarningDismissed()
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        super.onSharedPreferenceChanged(sharedPreferences, key)
        when (key) {
                    requireActivity().runOnUiThread {
                        dismiss()
                    }
                }
            }
        }
    }

    companion object {
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

            val fragment = newInstance()
            try {
                fragment.show(this, TAG)
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                val transaction = beginTransaction()
                transaction.setReorderingAllowed(true)
                transaction.add(fragment, TAG)
                transaction.commitAllowingStateLoss()
            }
            return fragment
        }
    }
}

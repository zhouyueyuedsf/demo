package com.example.demo.views.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.Rect
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.demo.R
import com.example.demo.drawable.RoundBackgroundDrawable
import com.example.demo.utils.AndroidBug5497Workaround
import com.example.demo.utils.DeviceUtils
import com.example.demo.utils.StatusBarUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_ocr_result_edit.*

/**
 * BottomSheetDialog fragment that uses a custom 
 * theme which sets a rounded background to the dialog 
 * and doesn't dim the navigation bar
 */
open class RoundedBottomSheetDialogFragment : Fragment() {

    companion object {
        fun newInstance(): RoundedBottomSheetDialogFragment {
            return RoundedBottomSheetDialogFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_ocr_result_edit, container, false)
//        root.background = resources.getDrawable(R.drawable.md_corner_shape)
        val drawable = RoundBackgroundDrawable().apply { bounds = Rect(0, 0, DeviceUtils.getScreenWidth(), DeviceUtils.getScreenHeight())
        }
        root?.background = drawable
        return root
    }


//    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        tvFinish.setOnClickListener {
//            val v = dialog!!.findViewById<View>(R.id.svOriginTextContainer)
//            print("")
//        }
//        activity.window
        StatusBarUtil.setColor(activity, Color.WHITE)
    }

    override fun onStart() {
        super.onStart()

//        dialog?.let {
//            (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
//            val bottomSheet = it.findViewById<FrameLayout>(R.id.design_bottom_sheet)
//            bottomSheet?.layoutParams?.height = DeviceUtils.getScreenAvailableHeight(it.window)
//            AndroidBug5497Workaround.assistActivity(view)
    //            it.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//        }
    }

    override fun onResume() {
        super.onResume()
    }

}
package com.example.demo.views.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.example.demo.R
import com.example.demo.utils.AndroidBug5497Workaround
import com.example.demo.utils.DeviceUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * BottomSheetDialog fragment that uses a custom 
 * theme which sets a rounded background to the dialog 
 * and doesn't dim the navigation bar
 */
open class RoundedBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): RoundedBottomSheetDialogFragment {
            return RoundedBottomSheetDialogFragment()
        }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val root = View.inflate(context, R.layout.fragment_ocr_result_edit, null)
        dialog.setContentView(root)
//        (dialog as BottomSheetDialog).behavior?.let {
//            it.state = BottomSheetBehavior.STATE_EXPANDED
//            it.peekHeight = DeviceUtils.getScreenHeight()
//        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
            val bottomSheet = it.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = DeviceUtils.getScreenAvailableHeight(it.window)
//            AndroidBug5497Workaround.assistActivity(it.findViewById(android.R.id.content))
            it.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }
}
package com.cmoney.backend2.sample.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.cmoney.backend2.sample.R

class ProgressDialog : DialogFragment() {

    companion object {
        val TAG = ProgressDialog::class.java.simpleName

        fun newInstance() = ProgressDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        val rootView =
            LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_window_lock, null)
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(rootView)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }
}
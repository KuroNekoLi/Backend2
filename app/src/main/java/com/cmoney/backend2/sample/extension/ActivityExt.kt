package com.cmoney.backend2.sample.extension

import androidx.appcompat.app.AppCompatActivity
import com.cmoney.backend2.sample.view.ProgressDialog

fun AppCompatActivity.lockWindows() {
    val tag = ProgressDialog.TAG
    val fragment = supportFragmentManager.findFragmentByTag(tag)

    if (fragment != null && !fragment.isRemoving && fragment is ProgressDialog &&
        fragment.dialog != null && fragment.dialog!!.isShowing
    ) {
        return
    }
    ProgressDialog.newInstance().show(supportFragmentManager, tag)
}

fun AppCompatActivity.unlockWindows() {
    val tag = ProgressDialog.TAG
    val fragment = supportFragmentManager.findFragmentByTag(tag)

    if (fragment != null && !fragment.isRemoving && fragment is ProgressDialog &&
        fragment.dialog != null && fragment.dialog!!.isShowing
    ) {
        fragment.dismissAllowingStateLoss()
    }
}
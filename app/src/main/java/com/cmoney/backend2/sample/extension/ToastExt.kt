package com.cmoney.backend2.sample.extension

import android.content.Context
import android.widget.Toast

internal fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).apply { show() }

internal fun Context.longToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).apply { show() }
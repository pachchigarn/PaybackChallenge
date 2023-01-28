package com.nishantp.payback.utils

import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AlertDialog
import com.nishantp.payback.R

object DialogUtils {

    fun showDialogOkCancel(context: Context, title: String, message: String, okClickListener: OnClickListener) {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(context.getString(R.string.yes), okClickListener)
            setNegativeButton(context.getString(R.string.no)) { dialogInterface: DialogInterface, _ ->
                dialogInterface.dismiss()
            }

        }.create().show()
    }
}
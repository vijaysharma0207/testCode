package com.codebrew.kc.app_util


import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.codebrew.kc.R
import com.codebrew.kc.app_interface.OnDialogButtonClickListener


/**
 * contains Dialogs to be used in the application
 */
class DialogsUtil {


    fun openAlertDialog(context: Context, message: String, positiveBtnText: String, negativeBtnText: String, listener: OnDialogButtonClickListener) {


        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton(positiveBtnText) { dialog, which ->
            dialog.dismiss()
            listener.onPositiveButtonClicked()

        }

        builder.setNegativeButton(negativeBtnText) { dialog, which ->
            dialog.dismiss()
            listener.onNegativeButtonClicked()

        }

        builder.setTitle(context.resources.getString(R.string.app_name))
        builder.setMessage(message)
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setCancelable(false)
        builder.create().show()
    }


    fun yesNoDialog(context: Context, message: String, positiveBtnText: String, negativeBtnText: String,
                    hidebtn:Boolean, listener: OnDialogButtonClickListener
    ) {

        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton(positiveBtnText) { dialog, which ->
            dialog.dismiss()
            listener.onPositiveButtonClicked()

        }

        if(hidebtn)
        {
            builder.setNegativeButton(negativeBtnText) { dialog, which ->
                dialog.dismiss()
                listener.onNegativeButtonClicked()

            }
        }


        builder.setTitle(message)
        builder.setCancelable(false)
        builder.create().show()
    }



    fun showDialog(context: Context, layout: Int): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layout)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER

        dialog.window!!.attributes = lp
        dialog.setCancelable(true)
        dialog.window!!.decorView.setBackgroundResource(android.R.color.transparent)
        return dialog
    }

    fun showDialogFix(context: Context, layout: Int): Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(layout)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER

        dialog.window!!.attributes = lp
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }
}
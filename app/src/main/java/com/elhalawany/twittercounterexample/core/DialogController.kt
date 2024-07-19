package com.elhalawany.twittercounterexample.core

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import androidx.core.view.isVisible
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.elhalawany.twittercounterexample.databinding.DialogCancelResultBinding
import com.elhalawany.twittercounterexample.databinding.DialogLoadingBinding
import com.elhalawany.twittercounterexample.databinding.DialogSuccessResultBinding

class DialogController(lifecycleOwner: LifecycleOwner) {


    fun createLoadingDialog(context: Context, message: String? = null): DialogController {
        val tempDialog = getTempDialog(context) {
            val binding = DialogLoadingBinding.inflate(it, null, false)
            binding.tvLoadingMessage.text = message
            binding
        }
        replaceDialog(tempDialog)

        return this
    }

    fun createSuccessDialog(context: Context, message: String? = null, onOk: (() -> Unit)? = null): DialogController {
        val tempDialog = getTempDialog(context) {
            val binding = DialogSuccessResultBinding.inflate(it, null, false)

            binding.tvSuccessMessage.text = message
            binding.tvSuccessMessage.isVisible = message != null
            binding.btnOk.setOnClickListener {
                onOk?.invoke()
                dismiss()
            }

            binding
        }
        replaceDialog(tempDialog)

        return this
    }

    fun createErrorDialog(context: Context, message: String? = null, onOk: (() -> Unit)? = null): DialogController {
        val tempDialog = getTempDialog(context) {
            val binding = DialogCancelResultBinding.inflate(it, null, false)
            binding.tvErrorMessage.text = message
            binding.tvErrorMessage.isVisible = message != null
            binding.btnOk.setOnClickListener {
                onOk?.invoke()
                dismiss()
            }
            binding
        }
        replaceDialog(tempDialog)

        return this
    }


    fun dismiss() {
        dialog?.dismiss()
    }

    private fun getTempDialog(context: Context, layout: Int) = Dialog(context).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layout)
        window?.setBackgroundDrawable(null)
        setCancelable(false)
    }

    private fun <T : ViewBinding> getTempDialog(context: Context, binding: (LayoutInflater) -> T) = Dialog(context).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding(layoutInflater).root)
        window?.setBackgroundDrawable(null)
        setCancelable(false)
    }


    private fun replaceDialog(tempDialog: Dialog) {
        dismiss()
        dialog = tempDialog
        show()
    }

    init {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStop(owner: LifecycleOwner) {
                dismiss()
            }
        })
    }

    private fun show() {
        dialog?.show()
    }

    private var dialog: Dialog? = null

}
package io.ffem.iitk.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import io.ffem.iitk.R

class InputDialogFragment : DialogFragment() {
    private lateinit var listener: InputDialogListener

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val d = activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.fragment_input, null)
            val edit = dialogView.findViewById<EditText>(R.id.quantity_edit)

            builder.setView(dialogView)
                .setPositiveButton(
                    R.string.ok
                ) { _, _ ->
                    val quantity = edit!!.text.toString().toDouble()
                    listener = parentFragment as InputDialogListener
                    listener.onDialogPositiveClick(quantity)
                }
                .setNegativeButton(
                    android.R.string.cancel
                ) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        d.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        return d
    }

    interface InputDialogListener {
        fun onDialogPositiveClick(value: Double)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        try {
            listener = childFragment as InputDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement InputDialogListener")
            )
        }
    }
}
package io.ffem.iitk.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import io.ffem.iitk.R
import kotlinx.android.synthetic.main.fragment_result_treatment.*
import org.json.JSONObject
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


private const val ARG_RESULT_JSON = "resultJson"
private const val ARG_TREATMENT_TYPE = "treatmentType"

@Transient
private val symbols = DecimalFormatSymbols(Locale.US)

@Transient
private val milligramFormat = DecimalFormat("#.##", symbols)

@Transient
private val gramFormat = DecimalFormat("#.###", symbols)

class ResultTreatmentFragment : Fragment(), InputDialogFragment.InputDialogListener {
    private var quantity: Double = 1.0
    private var resultValue: String = ""
    private lateinit var resultJson: String
    private lateinit var treatmentType: TreatmentType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resultJson = it.getString(ARG_RESULT_JSON).toString()
            treatmentType = TreatmentType.valueOf(
                it.getString(
                    ARG_TREATMENT_TYPE
                ).toString()
            )
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = JSONObject(resultJson).getJSONArray("result")
        var value = result.getJSONObject(0).getString("value")
        value = value.replace("> ", "")
        resultValue = value.replace("> >", ">")
        val name = result.getJSONObject(0).getString("name")

        activity?.setTitle(R.string.treatment)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }

        textResult.text = resultValue
        textName.text = "$name (VI)"

        if (treatmentType == TreatmentType.NONE) {
//            infoLayout.visibility = INVISIBLE
        } else {
            calculateRecommendation()
        }

        water_quantity_btn.setOnClickListener { showInputDialog() }
    }

    private fun calculateRecommendation() {
        val resultValue = resultValue.replace(">", "").trim()
        val totalContamination = (quantity * resultValue.toDouble())
        var chemicalQty = totalContamination * 200 / 12

        var amount = "${milligramFormat.format(chemicalQty)} mg"
        if (chemicalQty > 999) {
            chemicalQty /= 1000
            amount = "${gramFormat.format(chemicalQty)} gm"
        }

        recommendation1Text.text = Html.fromHtml(
            getString(R.string.iron_sulphate_recommendation, amount, quantity.toInt())
        )

        val totalMinutes = (totalContamination * 120 / 12).toInt()
        val hours: Int = totalMinutes / 60
        val minutes: Int = totalMinutes % 60
        var time = "$hours hours $minutes minutes"
        if (hours < 1) {
            time = "$minutes minutes"
        } else if (minutes < 2) {
            time = "$hours hours"
        }

        recommendation2Text.text = Html.fromHtml(
            getString(R.string.electrocoagulation_recommendation, time, quantity.toInt())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result_treatment, container, false)
    }

    private fun showInputDialog() {
        val dialog = InputDialogFragment()
        dialog.show(childFragmentManager, "InputDialogFragment")
    }

    companion object {
        @JvmStatic
        fun newInstance(resultJson: String, treatmentType: TreatmentType) =
            ResultTreatmentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_RESULT_JSON, resultJson)
                    putString(ARG_TREATMENT_TYPE, treatmentType.toString())
                }
            }
    }

    override fun onDialogPositiveClick(value: Double) {
        quantity = value
        calculateRecommendation()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {

    }
}

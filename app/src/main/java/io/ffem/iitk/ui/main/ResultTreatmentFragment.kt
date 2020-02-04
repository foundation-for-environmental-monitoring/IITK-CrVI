package io.ffem.iitk.ui.main

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
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

class ResultTreatmentFragment : Fragment() {
    private lateinit var resultJson: String
    private lateinit var treatmentType: TreatmentType
    private var expandedPanel = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resultJson = it.getString(ARG_RESULT_JSON).toString()
            treatmentType = TreatmentType.valueOf(it.getString(ARG_TREATMENT_TYPE).toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = JSONObject(resultJson).getJSONArray("result")
        var value = result.getJSONObject(0).getString("value")
        value = value.replace("> ", "")
        value = value.replace("> >", ">")
        val name = result.getJSONObject(0).getString("name")

        activity?.setTitle(R.string.treatment)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }

        textResult.text = value
        textName.text = "$name (VI)"

        editResult.doOnTextChanged { _, _, _, _ ->
            calculateRecommendation(value)
        }

        expansionLayout.addListener { expansionLayout, expanded ->
            run {
                if (expanded) {
                    tv_header1.setTypeface(null, Typeface.BOLD)
                    tv_header1.setTextColor(Color.WHITE)
                    header1.setBackgroundColor(Color.GRAY)
                } else {
                    tv_header1.setTypeface(null, Typeface.NORMAL)
                    tv_header1.setTextColor(Color.rgb(140, 140, 140))
                    header1.setBackgroundColor(Color.rgb(250, 250, 250))
                }
            }
        }

        expansionLayout2.addListener { expansionLayout, expanded ->
            run {
                if (expanded) {
                    tv_header2.setTypeface(null, Typeface.BOLD)
                    tv_header2.setTextColor(Color.WHITE)
                    header2.setBackgroundColor(Color.GRAY)
                } else {
                    tv_header2.setTypeface(null, Typeface.NORMAL)
                    tv_header2.setTextColor(Color.rgb(140, 140, 140))
                    header2.setBackgroundColor(Color.rgb(250, 250, 250))
                }
            }
        }

        if (treatmentType == TreatmentType.NONE) {
//            infoLayout.visibility = INVISIBLE
        } else {
            calculateRecommendation(value)
        }
    }

    private fun calculateRecommendation(value: String) {
        if (!editResult.text.isNullOrEmpty()) {
            val quantity = editResult.text.toString().toDouble()
            val resultValue = value.replace(">", "").trim()
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
//            labelTreatmentMethod.visibility = VISIBLE
//            tabs.visibility = VISIBLE
//            if (tabs.selectedTabPosition == 0) {
//                recommendation1Text.visibility = VISIBLE
//                recommendation2Text.visibility = GONE
//            } else {
//                recommendation1Text.visibility = GONE
//                recommendation2Text.visibility = VISIBLE
//            }
        } else {
//            labelTreatmentMethod.visibility = GONE
//            tabs.visibility = GONE
//            recommendation1Text.visibility = GONE
//            recommendation2Text.visibility = GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result_treatment, container, false)
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
}

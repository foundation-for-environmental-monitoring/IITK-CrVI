package io.ffem.iitk.ui.main

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.ffem.iitk.R
import kotlinx.android.synthetic.main.fragment_result.*
import org.json.JSONObject

private const val ARG_RESULT_JSON = "resultJson"
private const val ARG_TREATMENT_TYPE = "treatmentType"

class ResultFragment : Fragment() {
    private lateinit var resultJson: String
    private lateinit var treatmentType: TreatmentType

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
        val value = result.getJSONObject(0).getString("value")
            .replace("> >", ">")
        val name = result.getJSONObject(0).getString("name")

        activity?.setTitle(R.string.result)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }

        textResult.text = value
        textTitle.text = name

        when (treatmentType) {
            TreatmentType.NONE -> {
                infoLayout.visibility = INVISIBLE
            }
            TreatmentType.IRON_SULPHATE -> {
                textSubtitle.text = getString(R.string.iron_sulphate)
                recommendation1Text.text =
                    getString(R.string.iron_sulphate_recommendation)
                recommendation2Text.text =
                    Html.fromHtml(getString(R.string.note_based_on_200mg_tablet))
            }
            TreatmentType.ELECTROCOAGULATION -> {
                textSubtitle.text = getString(R.string.electrocoagulation)
                recommendation1Text.text =
                    getString(R.string.electrocoagulation_recommendation)
                recommendation2Text.visibility = INVISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(resultJson: String, treatmentType: TreatmentType) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_RESULT_JSON, resultJson)
                    putString(ARG_TREATMENT_TYPE, treatmentType.toString())
                }
            }
    }
}

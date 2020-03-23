package io.ffem.iitk.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.ffem.iitk.ARG_RESULT_JSON
import io.ffem.iitk.R
import kotlinx.android.synthetic.main.fragment_result_treatment.*
import org.json.JSONObject

class ResultFragment : Fragment() {
    private var resultValue: String = ""
    private lateinit var resultJson: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resultJson = it.getString(ARG_RESULT_JSON).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = JSONObject(resultJson).getJSONArray("result")
        var value = result.getJSONObject(0).getString("value")
        value = value.replace("> ", "")
        resultValue = value.replace("> >", ">")
        val name = result.getJSONObject(0).getString("name")

        activity?.setTitle(R.string.result)
        if ((activity as AppCompatActivity).supportActionBar != null) {
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }

        text_result.text = resultValue
        text_name.text = "$name (VI)"
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val testInfo = ResultFragmentArgs.fromBundle(arguments!!).testInfo
//        text_title.text = testInfo.name
//        text_result.text = testInfo.result
//        text_unit.text = testInfo.unit
//        super.onViewCreated(view, savedInstanceState)
//    }

    companion object {
        @JvmStatic
        fun newInstance(resultJson: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_RESULT_JSON, resultJson)
                }
            }
    }
}

package io.ffem.iitk.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.ffem.iitk.R
import kotlinx.android.synthetic.main.fragment_result.*
import org.json.JSONObject

private const val ARG_RESULT_JSON = "resultJson"
private const val ARG_TREATMENT_TYPE = "treatmentType"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ResultFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {
    private lateinit var resultJson: String
    private lateinit var treatmentType: TreatmentType
    private var listener: OnFragmentInteractionListener? = null

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
        val name = result.getJSONObject(0).getString("name")

        activity?.setTitle(R.string.result)

        textResult.text = value
        textTitle.text = name

        when (treatmentType) {
            TreatmentType.NONE -> {
                infoLayout.visibility = INVISIBLE
            }
            TreatmentType.IRON_SULPHATE -> {
                recommendation1Text.text = "Recommendation information 1"
                recommendation2Text.text =
                    "Recommendation informatjon 2"
            }
            TreatmentType.ELECTROCOAGULATION -> {
                recommendation1Text.text =
                    "A current of 40 mA needs 2 hours to treat 6L of water contaminated with 2 mg/L of Cr(VI)."
                recommendation2Text.visibility = INVISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
//            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
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

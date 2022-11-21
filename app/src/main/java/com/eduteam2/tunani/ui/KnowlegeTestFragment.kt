package com.eduteam2.tunani.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.eduteam2.tunani.Extensions.toast
import com.eduteam2.tunani.R
import com.eduteam2.tunani.adapters.QuestionAdapter
import com.eduteam2.tunani.databinding.FragmentKnowlegeTestBinding
import com.eduteam2.tunani.datac.QuestionModel
import com.eduteam2.tunani.interfaces.OnQuestionClickListener


class KnowlegeTestFragment : Fragment() , OnQuestionClickListener {

    private var _binding: FragmentKnowlegeTestBinding? = null
    private val binding get() = _binding!!

    private  var question:ArrayList<QuestionModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentKnowlegeTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // access the items of the list
        val languages = arrayListOf("Topic","Google Cloud","Kotlin")

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
            binding.topicSpinner.adapter = adapter

        binding.topicSpinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                    requireActivity().toast(languages[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }


        question = ArrayList()
        question?.add(QuestionModel("Topic","this topic question?","topic answer"))
        question?.add(QuestionModel("Google Cloud","this cloud question?","cloud answer"))
        question?.add(QuestionModel("Kotlin","What is a ViewModel?","kotlin answer"))

        binding.recyclerViewQuestion.adapter = QuestionAdapter(question!!,this)




    }

    private var accuracy= 50
    override fun onQuestionClick(questionModel: QuestionModel) {

        val dialog = Dialog(requireActivity())

        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_answer)

        dialog.findViewById<TextView>(R.id.answerText).text = questionModel.answer

        dialog.findViewById<TextView>(R.id.iRemember).setOnClickListener {
            dialog.dismiss()
            accuracy+=10
            binding.recallAccuracyPercentage.text = accuracy.toString().plus("%")
        }
        dialog.findViewById<TextView>(R.id.iForgot).setOnClickListener {
            dialog.dismiss()
            accuracy-=10
            binding.recallAccuracyPercentage.text = accuracy.toString().plus("%")
        }
        dialog.show()


    }
}
package com.eduteam2.tunani.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eduteam2.tunani.databinding.ItemQuestionBinding
import com.eduteam2.tunani.datac.QuestionModel
import com.eduteam2.tunani.interfaces.OnQuestionClickListener

class QuestionAdapter (
    private val mList: ArrayList<QuestionModel>,private val onQuestionClickListener: OnQuestionClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ItemViewHolder(val binding: ItemQuestionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ItemViewHolder

        holder.binding.questionText.text = mList[position].question

        holder.binding.showAnswer.setOnClickListener {
            onQuestionClickListener.onQuestionClick(mList[position])
        }


    }

}
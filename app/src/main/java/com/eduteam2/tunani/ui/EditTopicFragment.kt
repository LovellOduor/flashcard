package com.eduteam2.tunani.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eduteam2.tunani.R



/**
 * A simple [Fragment] subclass.
 * Use the [EditTopicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditTopicFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_topic, container, false)
    }

}
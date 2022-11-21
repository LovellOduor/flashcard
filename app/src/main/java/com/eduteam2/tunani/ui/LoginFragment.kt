package com.eduteam2.tunani.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.eduteam2.tunani.Extensions.toast
import com.eduteam2.tunani.R
import com.eduteam2.tunani.databinding.FragmentLoginBinding
import com.eduteam2.tunani.utils.FirebaseUtils.firebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray: Array<EditText>

    /* check if there's a signed-in user*/

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            requireActivity().toast("welcome back")
            navigateToKnowledge()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInInputsArray = arrayOf(binding.etSignInEmail, binding.etSignInPassword)
        binding.btnCreateAccount2.setOnClickListener {
            navigateToCreate()
        }

        binding.btnSignIn.setOnClickListener {
            signInUser()
        }


    }

    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()


    private fun navigateToKnowledge() {
            findNavController().navigate(R.id.action_loginFragment_to_knowlegeTestFragment)
    }
    private fun navigateToCreate() {
            findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
    }

    private fun signInUser() {
        signInEmail = binding.etSignInEmail.text.toString().trim()
        signInPassword = binding.etSignInPassword.text.toString().trim()

        if (notEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        requireActivity().toast("signed in successfully")
                        navigateToKnowledge()
                    } else {
                        requireActivity().toast("sign in failed")
                    }
                }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
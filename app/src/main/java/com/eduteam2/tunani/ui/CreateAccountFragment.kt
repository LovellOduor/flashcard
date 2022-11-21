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
import com.eduteam2.tunani.databinding.FragmentCreateAccountBinding
import com.eduteam2.tunani.databinding.FragmentLoginBinding
import com.eduteam2.tunani.utils.FirebaseUtils.firebaseAuth
import com.eduteam2.tunani.utils.FirebaseUtils.firebaseUser


class CreateAccountFragment : Fragment() {
    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAccountInputsArray =
            arrayOf(binding.etEmail, binding.etPassword, binding.etConfirmPassword)
        binding.btnCreateAccount.setOnClickListener {
            signIn()
        }

        binding.btnSignIn2.setOnClickListener {
            requireActivity().toast("please sign into your account")
            navigateToLogin()
        }


    }

    private fun notEmpty(): Boolean = binding.etEmail.text.toString().trim().isNotEmpty() &&
            binding.etPassword.text.toString().trim().isNotEmpty() &&
            binding.etConfirmPassword.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            binding.etPassword.text.toString().trim() == binding.etConfirmPassword.text.toString()
                .trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        } else {
            requireActivity().toast("passwords are not matching !")
        }
        return identical
    }


    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_createAccountFragment_to_loginFragment)
    }

    private fun navigateToKnowledge() {
        findNavController().navigate(R.id.action_createAccountFragment_to_knowlegeTestFragment)
    }

    private fun signIn() {
        if (identicalPassword()) {
            // identicalPassword() returns true only  when inputs are not empty and passwords are identical
            userEmail = binding.etEmail.text.toString().trim()
            userPassword = binding.etPassword.text.toString().trim()

            /*create a user*/
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        requireActivity().toast("created account successfully !")
                        sendEmailVerification()
                        navigateToKnowledge()
                    } else {
                        requireActivity().toast("failed to Authenticate !")
                    }
                }
        }
    }

    /* send verification email to the new user. This will only
    *  work if the firebase user is not null.
    */

    private fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    requireActivity().toast("email sent to $userEmail")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.elhalawany.twittercounterexample.presentation

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elhalawany.twittercounterexample.core.AndroidServicesHelper
import com.elhalawany.twittercounterexample.core.DialogController
import com.elhalawany.twittercounterexample.core.DialogResource
import com.elhalawany.twittercounterexample.databinding.FragmentCreateTweetBinding

class CreateTweetFragment : Fragment() {

    private val binding by lazy { FragmentCreateTweetBinding.inflate(layoutInflater) }
    private lateinit var dialogController: DialogController
    private val vm by viewModels<CreateTweetViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialogController = DialogController(this)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addActions()
        updateButtonState()
        addPostButtonStyle()
    }

    override fun onStart() {
        super.onStart()
        dialogListener()

    }

    private fun dialogListener() {

        vm.createTweetLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DialogResource.Error -> dialogController.createErrorDialog(requireContext(), it.getMessage(requireContext())) {
                    vm.hideDialog()
                }

                is DialogResource.Loading -> dialogController.createLoadingDialog(requireContext())

                is DialogResource.Success -> dialogController.createSuccessDialog(requireContext()) {
                    vm.hideDialog()
                }

                else -> {
                    dialogController.dismiss()
                }
            }
        }
    }

    private fun addActions() {
        binding.ivBack.setOnClickListener {
            requireActivity().finishAffinity()
        }

        binding.btnClearText.setOnClickListener {
            binding.twitterCounterComponent.clearText()
        }

        binding.btnCopyText.setOnClickListener {
            AndroidServicesHelper.copyToClipboard(requireContext(), binding.twitterCounterComponent.getText())
        }

        binding.btnPostTweet.setOnClickListener {
            val tweetBody = binding.twitterCounterComponent.getText()
            vm.createTweet(tweetBody)
        }

        binding.twitterCounterComponent.setOnTextChangeListeners { updateButtonState() }
    }

    private fun updateButtonState() {
        val tweetText = binding.twitterCounterComponent.getText()

        binding.btnPostTweet.isEnabled = binding.twitterCounterComponent.isValidPost()
        binding.btnClearText.isEnabled = tweetText.isNotEmpty()
        binding.btnCopyText.isEnabled = tweetText.isNotEmpty()
    }

    private fun addPostButtonStyle() {
        val states = arrayOf(intArrayOf(-R.attr.state_enabled))
        val colors = intArrayOf(Color.parseColor("#e5e5e5"))
        val backgroundTintList = ColorStateList(states, colors)

        binding.btnPostTweet.backgroundTintList = backgroundTintList
        binding.btnCopyText.backgroundTintList = backgroundTintList
        binding.btnClearText.backgroundTintList = backgroundTintList
    }


}
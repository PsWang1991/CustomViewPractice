package com.example.customviews.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.customviews.R
import com.example.customviews.databinding.MainFragmentBinding
import com.example.customviews.utils.dp2px

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MainFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.chihuahua.animate()
//            .translationX(100.dp2px)
//            .translationY(200.dp2px).startDelay = 1000

//        val animator = ObjectAnimator.ofFloat(binding.chihuahua, "radius", 100.dp2px)
//        animator.startDelay = 1000
//        animator.start()

        val upperFlipAnimator =
            ObjectAnimator.ofFloat(binding.chihuahua, "cameraRotateXUpper", 60f).apply {
                startDelay = 1000
                duration = 2000
            }

        val flipRotationAnimator =
            ObjectAnimator.ofFloat(binding.chihuahua, "canvasRotate", 270f).apply {
                startDelay = 1500
                duration = 1500
            }

        val downerFlipAnimator =
            ObjectAnimator.ofFloat(binding.chihuahua, "cameraRotateXDowner", -60f).apply {
                startDelay = 1000
                duration = 2000
            }

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(upperFlipAnimator, flipRotationAnimator, downerFlipAnimator)
        animatorSet.start()

//        val upperFlipHolder = PropertyValuesHolder.ofFloat("cameraRotateXUpper", 60f)
//        val flipRotationHolder = PropertyValuesHolder.ofFloat("canvasRotate", 270f)
//        val downFlipHolder = PropertyValuesHolder.ofFloat("cameraRotateXDowner", -60f)
//        val holderAnimator = ObjectAnimator.ofPropertyValuesHolder(
//            binding.chihuahua,
//            upperFlipHolder,
//            flipRotationHolder,
//            downFlipHolder
//        )
//        holderAnimator.apply {
//            startDelay = 1000
//            duration = 2000
//            start()
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
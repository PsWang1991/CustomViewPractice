package com.example.customviews.ui.main

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import com.example.customviews.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = MainFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.chihuahua.animate()
//            .translationX(100.dp)
//            .translationY(200.dp).startDelay = 1000

//        val animator = ObjectAnimator.ofFloat(binding.chihuahua, "radius", 100.dp)
//        animator.startDelay = 1000
//        animator.start()

//        val upperFlipAnimator =
//            ObjectAnimator.ofFloat(binding.chihuahua, "upperFlipAngle", -45f).apply {
//                startDelay = 500
//                duration = 1000
//            }
//
//        val downerFlipAnimator =
//            ObjectAnimator.ofFloat(binding.chihuahua, "downerFlipAngle", 30f).apply {
//                startDelay = 300
//                duration = 1000
//            }
//
//        val flipRotationAnimator =
//            ObjectAnimator.ofFloat(binding.chihuahua, "flipRotationAngle", -270f).apply {
//                startDelay = 300
//                duration = 1500
//            }
//
//        val animatorSet = AnimatorSet()
//        animatorSet.playSequentially(upperFlipAnimator, flipRotationAnimator, downerFlipAnimator)
//        animatorSet.start()

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

//        val pointAnimator = ObjectAnimator.ofObject(binding.chihuahua,
//            "position",
//            PointTypeEvaluator(),
//            PointF(150.dp, 240.dp))
//        pointAnimator.apply {
//            startDelay = 1000
//            duration = 2000
//            start()
//        }

//        ObjectAnimator.ofObject(binding.chihuahua, "food", FoodEvaluator(), "刺身").apply {
//            duration = 10000
//            startDelay = 500
//            start()
//        }

        val bitmap = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888)
        bitmap.toDrawable(resources).toBitmap()


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
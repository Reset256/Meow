package com.llv.meow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.llv.meow.databinding.MeowFragmentBinding


class MeowFragment : Fragment() {

    private var _binding: MeowFragmentBinding? = null
    private val binding get() = _binding!!

    private val meowMap by lazy {
        MeowMap().apply {
            addRes(Mood.ANGRY, R.drawable.angry_cat, R.raw.angry_meow)
            addRes(Mood.GRUMPY, R.drawable.grumpy_cat, R.raw.grumpy_meow)
            addRes(Mood.SAD, R.drawable.sad_cat, R.raw.sad_meow)
            addRes(Mood.SMILEY, R.drawable.smiley_cat, R.raw.usual_meow)
            addRes(Mood.PURR, R.drawable.purr_purr, R.raw.purr)
        }
    }

    private val animationOnClick: (View) -> Unit = {
        val animator = it.animate()
        animator.scaleXBy(0.15f).scaleYBy(0.15f).setDuration(500L)
            .withEndAction { animator.scaleX(1f).scaleY(1f) }
    }

    private val playerPool: MediaPlayerPool by lazy {
        MediaPlayerPool(requireActivity(), 4)
    }

    private lateinit var vList: LinearLayout



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MeowFragmentBinding.inflate(inflater, container, false)
        vList = binding.act1
        showCatLayout(meowMap)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addMeowButton.setOnClickListener {
            findNavController().navigate(R.id.action_meow_to_meowLib)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showCatLayout(catMap: MeowMap) {
        val inflater = layoutInflater
        for (mood in Mood.values()) {
            val view = inflater.inflate(R.layout.cat_item, vList, false)
            val catImageView = view.findViewById<ImageView>(R.id.item_cat)
            catImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    catMap.getPicId(mood)
                )
            )
            val resId = catMap.getAudioId(mood) ?: 0
            catImageView.setOnClickListener {
                animationOnClick(it)
                playerPool.playSound(resId)
            }
            vList.addView(view)
        }
    }
}
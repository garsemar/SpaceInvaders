package com.garsemar.spaceinvader

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.garsemar.spaceinvader.model.GameView

class GameFragment : Fragment() {
    lateinit var gameView: GameView
    lateinit var fireButton: Button
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gameView = GameView(requireContext(), getScreenSize())

        val game: FrameLayout = FrameLayout(requireContext())
        val gameButtons: RelativeLayout = RelativeLayout(requireContext())
        fireButton = Button(requireContext());
        fireButton.text = "Fire"
        fireButton.setBackgroundColor(Color.RED)
        val b1 = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.FILL_PARENT,
            RelativeLayout.LayoutParams.FILL_PARENT
        )

        gameButtons.layoutParams = params
        gameButtons.addView(fireButton)
        b1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
        b1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        fireButton.layoutParams = b1
        (gameView.parent as? ViewGroup)?.removeView(gameView) // eliminar el padre de gameView
        game.addView(gameView)
        game.addView(gameButtons)

        return game
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fireButton.setOnClickListener {
            gameView.shot()
        }
    }


    private fun getScreenSize(): Size {
        val metrics = Resources.getSystem().displayMetrics
        return Size(metrics.widthPixels, metrics.heightPixels)
    }
}
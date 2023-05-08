package com.garsemar.spaceinvader.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.garsemar.spaceinvader.R
import kotlin.math.abs

class Player(context: Context, val screenX: Int, val screenY: Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ship)
    val width = 200f
    val height = 200f
    var positionX = screenX / 2
    var positionY = screenY-500
    var speed = 0

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }

    fun updatePlayer(){
        if((positionX < screenX-width && abs(speed) == speed) || (positionX > 0 && abs(speed) != speed)){
            positionX += speed
        }
    }
}

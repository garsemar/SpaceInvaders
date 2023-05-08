package com.garsemar.spaceinvader.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.garsemar.spaceinvader.R

class Enemy(context: Context, screenX: Int, screenY: Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.invader)
    val width = 120f
    val height = 150f
    var positionX = screenX / 2
    var speed = 0

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }

    fun updateEnemy(){
        positionX += speed
    }
}

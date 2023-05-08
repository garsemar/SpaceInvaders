package com.garsemar.spaceinvader.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.garsemar.spaceinvader.R

class Shot(context: Context, screenX: Int, screenY: Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bullet)
    val width = screenX / 10f
    val height = screenY / 10f
    var positionX = screenX / 2
    var positionY = screenY / 2
    var speed = -20

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }

    fun updateShot(){
        positionY += speed
    }
}

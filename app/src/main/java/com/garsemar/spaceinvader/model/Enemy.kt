package com.garsemar.spaceinvader.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.garsemar.spaceinvader.R

class Enemy(context: Context, screenX: Int, val screenY: Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.invader)
    val width = 120f
    val height = 150f
    var positionX = 0
    var positionY = 0
    var speed = 40

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }

    fun updateEnemy(): Boolean {
        return if(positionY<screenY-250){
            positionY += speed
            true
        } else{
            false
        }
    }
}

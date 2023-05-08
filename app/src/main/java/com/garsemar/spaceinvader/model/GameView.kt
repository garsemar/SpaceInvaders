package com.garsemar.spaceinvader.model

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.Size
import android.view.MotionEvent
import android.view.SurfaceView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("ViewConstructor")
class GameView(context: Context, private val size: Size) : SurfaceView(context) {
    val player = Player(context, size.width, size.height)
    val enemy = Enemy(context, size.width, size.height)
    val shot = Shot(context, size.width, size.height)
    val playing = true
    var shotAction = false
    val score = 0

    companion object{
        var canvas: Canvas = Canvas()
        val paint: Paint = Paint()
    }

    init {
        startGame()
    }

    fun shot(){
        shot.positionY = player.positionY
        shot.positionX = player.positionX
        shotAction = true
    }

    fun startGame(){
        CoroutineScope(Dispatchers.Main).launch{
            while(playing){
                draw()
                update()
                delay(10)
            }
        }
    }

    fun draw(){
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawColor(Color.BLACK)
            //SCORE

            //Shot
            if(shotAction){
                canvas.drawBitmap(shot.bitmap, shot.positionX.toFloat(), shot.positionY.toFloat(), paint)
            }
            //ENEMY
            canvas.drawBitmap(enemy.bitmap, enemy.positionX.toFloat(),0f, paint)
            //PLAYER
            canvas.drawBitmap(player.bitmap, player.positionX.toFloat(), player.positionY.toFloat(), paint)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun update(){
        enemy.updateEnemy()
        player.updatePlayer()
        if(shotAction){
            shot.updateShot()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when(event.action){
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    if (event.x in (player.positionX.toFloat()-25..player.positionX.toFloat()+25)){
                        player.speed = 0
                    }
                    else if(event.x>player.positionX){
                        player.speed = 20
                    }
                    else if (event.x<player.positionX){
                        player.speed = -20
                    }
                }
                MotionEvent.ACTION_UP -> {
                    player.speed = 0
                }
            }
        }
        return true
    }
}
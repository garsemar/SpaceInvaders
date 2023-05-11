package com.garsemar.spaceinvader.model

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Build
import android.util.Size
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.garsemar.spaceinvader.GameFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("ViewConstructor")
class GameView(context: Context, private val size: Size) : SurfaceView(context) {
    val player = Player(context, size.width, size.height)
    val enemy = mutableListOf<Enemy>()
    val playing = true
    var shotAction = mutableListOf<Shot>()
    val score = 0
    var enemyPosX = 30
    var enemyPosY = 0

    companion object{
        var canvas: Canvas = Canvas()
        val paint: Paint = Paint()
    }

    init {
        startGame()
        repeat(20){
            enemy.add(Enemy(context, size.width, size.height))
            if(enemyPosX > size.width-100){
                enemyPosY += 160
                enemyPosX = 0
            }

            enemy.last().positionX = enemyPosX
            enemy.last().positionY = enemyPosY

            enemyPosX += 160
        }
    }

    fun shot(){
        if(shotAction.size < 3){
            shotAction.add(Shot(context, size.width, size.height))
            shotAction.last().positionY = player.positionY
            shotAction.last().positionX = player.positionX
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
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
            shotAction.forEach {
                canvas.drawBitmap(it.bitmap, it.positionX.toFloat(), it.positionY.toFloat(), paint)
            }
            //ENEMY
            enemy.forEach {
                canvas.drawBitmap(it.bitmap, it.positionX.toFloat(),it.positionY.toFloat(), paint)
            }
            //PLAYER
            canvas.drawBitmap(player.bitmap, player.positionX.toFloat(), player.positionY.toFloat(), paint)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun update(){
        enemy.removeIf {
            shotAction.any { shotIt ->
                it.positionX < shotIt.positionX + shotIt.width &&
                        it.positionX + it.width > shotIt.positionX &&
                it.positionY < shotIt.positionY + height &&
                        it.positionY + it.height > shotIt.positionY
            }
        }
        enemy.forEach {
            if(it.updateEnemy()){
                val action = GameFragmentDirections.actionGameFragmentToResultFragment()
                findNavController().navigate(action)
            }
        }
        player.updatePlayer()
        shotAction.forEach{
            it.updateShot()
        }
        shotAction.removeIf { it.positionY in (-50 downTo -70) }
        println(shotAction.size)
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
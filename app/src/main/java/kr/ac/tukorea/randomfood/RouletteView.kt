package kr.ac.tukorea.randomfood

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import kotlin.collections.listOf
import kotlin.math.cos
import kotlin.math.sin

class RouletteView @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet? = null,
    defStyleAttr:Int = 0
) : View(context,attrs,defStyleAttr){

    private val strokePaint = Paint()
    private val fillPaint = Paint()
    private val textPaint = Paint()

    var rouletteSize = 4
    var rouletteData = mutableListOf<String>()


    private var centerX = 0f
    private var centerY = 0f


    init{

        strokePaint.apply{
            color = Color.BLACK
            style = Paint.Style.STROKE // 내부 비워진 원
            strokeWidth = 15f
            isAntiAlias = true // 부드럽게 원 그리기
        }
        fillPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true

        }
        textPaint.apply{
            color = Color.BLACK
            textSize=60f
            textAlign = Paint.Align.CENTER
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val rectLeft = left + paddingLeft + 20f
        val rectRight = right - paddingRight - 20f
        val rectTop = height / 2f - rectRight / 2f + paddingTop + 20f
        val rectBottom = height / 2f + rectRight / 2f - paddingRight - 20f


        val rectF = RectF(rectLeft, rectTop, rectRight, rectBottom)

        centerX = (rectF.left + rectF.right) / 2f
        centerY = (rectF.top + rectF.bottom) / 2f

        drawRoulette(canvas,rectF)

    }

    private fun drawRoulette(canvas:Canvas?, rectF:RectF){
        canvas?.drawArc(rectF,0f,360f,true,strokePaint)


        if (rouletteSize in 2..8){
            val sweepAngle = 360f / rouletteSize.toFloat()
            val radius = (rectF.right - rectF.left)/2 *0.5
            val colors = listOf("#005b96","#7bc043", "#f37735","#fed766","#fe4a49", "#2ab7ca", "#e6e6ea", "#f6abb6")

            for (i in 0 until rouletteSize){
                fillPaint.color = Color.parseColor(colors[i])

                val startAngle = if(i==0) 0f else sweepAngle*i
                canvas?.drawArc(rectF,startAngle,sweepAngle,true,fillPaint)

                val medianAngle = (startAngle + sweepAngle / 2f)* Math.PI / 180f
                val x = (centerX + (radius * cos(medianAngle))).toFloat()
                val y = (centerY + (radius * sin(medianAngle))).toFloat()+20f

                val text = if(i>rouletteData.size -1 ) "" else rouletteData[i]
                canvas?.drawText(text,x,y,textPaint)

            }


        }


    }

    private fun getRouletteRotateResult(degrees:Float):String{
        val moveDegrees = degrees %360
        val resultAngle = if(moveDegrees > 270) 360-moveDegrees + 270 else 270 - moveDegrees
        for(i in 1..rouletteSize){
            if(resultAngle < (360 / rouletteSize)*i){
                if(i-1 >= rouletteData.size){
                    return "empty"
                }

                return rouletteData[i-1]
            }
        }
        return ""
    }

    fun rotateRoulette(toDegrees:Float,duration:Long,rotateListener: RotateListener?){

        val animListener=object:Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
                rotateListener?.onRotateStart()
            }

            override fun onAnimationEnd(animation: Animation?) {
                rotateListener?.onRotateEnd((getRouletteRotateResult((toDegrees))))

            }
        }

        val rotateAnim = RotateAnimation(0f,toDegrees,
            Animation.RELATIVE_TO_SELF,0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        rotateAnim.duration = duration
        rotateAnim.fillAfter = true
        rotateAnim.setAnimationListener(animListener)

        startAnimation(rotateAnim)

    }




}
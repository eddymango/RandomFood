package kr.ac.tukorea.randomfood

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.ac.tukorea.randomfood.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding


    private val rouletteListener = object : RotateListener {
        override fun onRotateStart() {
            binding.rotateResultTv.text = "Result :  "
        }

        override fun onRotateEnd(result: String) {
            binding.rotateResultTv.text = "Result : $result"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        binding.btnPlus.setOnClickListener{
            var curSize = binding.roulette.rouletteSize
            if ((curSize<2 )|| (curSize>7)){
                Toast.makeText(this,"추가할 수 없습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                curSize+=
                    binding.roulette.rouletteSize++
                binding.roulette.invalidate()

            }
        }

        binding.btnMinus.setOnClickListener {
            var curSize = binding.roulette.rouletteSize
            if (curSize<3 ){
                Toast.makeText(this,"최소 크기입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                if(binding.roulette.rouletteData.lastIndex != -1) {
                    if(binding.roulette.rouletteData.size == binding.roulette.rouletteSize){
                        binding.roulette.rouletteData.removeAt(binding.roulette.rouletteData.lastIndex)
                        curSize -=
                            binding.roulette.rouletteSize--
                        binding.roulette.invalidate()
                    }
                    else{
                        curSize -=
                            binding.roulette.rouletteSize--
                        binding.roulette.invalidate()
                    }
                }
                else{
                    curSize -=
                        binding.roulette.rouletteSize--
                    binding.roulette.invalidate()

                }
            } }

        binding.editBtn.setOnClickListener{
            if(binding.roulette.rouletteData.size > binding.roulette.rouletteSize-1){ //
                Toast.makeText(this,"넣을 수 없습니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            else{
                if(binding.roulette.rouletteData.size>binding.roulette.rouletteSize-1){
                    Toast.makeText(this,"넣을 수 없습니다.",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener

                }
                //글자수 제한해야함
                else{
                    binding.roulette.rouletteData.add(binding.editMenu.text.toString())
                    binding.roulette.invalidate()
                }

            }
        }

    }
    fun rotateRoulette(){
        val toDegrees = (2000..10000).random().toFloat()
        binding.roulette.rotateRoulette(toDegrees,3000,rouletteListener)
    }


}
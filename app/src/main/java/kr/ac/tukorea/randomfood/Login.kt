package kr.ac.tukorea.randomfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.ac.tukorea.randomfood.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageView.setOnClickListener {
            Toast.makeText(this,"이미지 클릭",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.btn.setOnClickListener {
            Toast.makeText(this,"클릭 리스너 됩니다,",Toast.LENGTH_SHORT).show()
        }






    }
}
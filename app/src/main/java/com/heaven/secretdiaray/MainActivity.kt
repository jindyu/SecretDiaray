package com.heaven.secretdiaray

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPicker1 : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker2 : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker3 : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton : AppCompatButton by lazy {
        findViewById(R.id.openButton)
    }

    private val changePasswordButton : AppCompatButton by lazy {
        findViewById(R.id.chagePasswordButton)
    }

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openButton.setOnClickListener {

            if (changePasswordMode){
                Toast.makeText(this, " 비밀번호 변경 중입니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val password =  "${numberPicker1}${numberPicker2}${numberPicker3}"

            if(getSharedPreferences("password", Context.MODE_PRIVATE).getString("password", "000").equals(password)){
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                showErrorAlertDialog()
            }
        }

        changePasswordButton.setOnClickListener {

            val passwordPreference = getSharedPreferences("password", Context.MODE_PRIVATE)

            if(changePasswordMode){
                // 번호 저장
                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)

                passwordPreference.edit(true) {
                    val password =  "${numberPicker1}${numberPicker2}${numberPicker3}"
                    putString("password", password)
                }

            } else {
                // 비밀번호가 맞으면?
                val password =  "${numberPicker1}${numberPicker2}${numberPicker3}"

                if(passwordPreference.getString("password", "000").equals(password)){
                    // 모드 활성화
                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()

                    changePasswordButton.setBackgroundColor(Color.RED)
                } else {
                    showErrorAlertDialog()
                }
            }
        }
    }

    private fun showErrorAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘 못 되었습니다")
            .setPositiveButton("확인") { _, _ ->}
            .create()
            .show()
    }
}
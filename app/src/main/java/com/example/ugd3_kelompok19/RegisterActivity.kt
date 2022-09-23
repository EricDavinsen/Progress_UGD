package com.example.ugd3_kelompok19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ugd3_kelompok19.databinding.ActivityRegisterBinding
import com.example.ugd3_kelompok19.room.User
import com.example.ugd3_kelompok19.room.UserDB
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var inputusername: TextInputLayout
    private lateinit var inputpassword: TextInputLayout
    private lateinit var inputemail: TextInputLayout
    private lateinit var inputtanggalLahir: TextInputLayout
    private lateinit var inputnoTelp: TextInputLayout
    private lateinit var btnRegister: Button
    private lateinit var registerLayout: ConstraintLayout
    private lateinit var binding: ActivityRegisterBinding
    private var userId: Int = 0

    val db by lazy{ UserDB(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)


        var checkLogin = true

        binding.btnRegister.setOnClickListener(View.OnClickListener {
            val mBundle = Bundle()
            val intent = Intent(this, MainActivity::class.java)

            val username: String = binding.inputLayoutUsername.getEditText()?.getText().toString()
            val password: String = binding.inputLayoutPassword.getEditText()?.getText().toString()
            val email: String = binding.inputLayoutEmail.getEditText()?.getText().toString()
            val tanggalLahir: String =
                binding.inputLayoutTanggalLahir.getEditText()?.getText().toString()
            val noTelp: String = binding.inputLayoutNoTelp.getEditText()?.getText().toString()

            if (username.isEmpty()) {
                inputusername.setError("Username must be filled with text")
                checkLogin = false
            }

            if (password.isEmpty()) {
                inputpassword.setError("Password must be filled with text")
                checkLogin = false
            }

            if (email.isEmpty()) {
                inputemail.setError("Email must be filled with text")
                checkLogin = false
            }

            if (tanggalLahir.isEmpty()) {
                inputtanggalLahir.setError("Tangal Lahir must be filled with text")
                checkLogin = false
            }

            if (noTelp.isEmpty()) {
                inputnoTelp.setError("Nomor Telepon must be filled with text")
                checkLogin = false
            }

            if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !tanggalLahir.isEmpty() && !noTelp.isEmpty()) {
                checkLogin = true
            }



            if (inputusername.getEditText()?.getText() == null) {
                inputusername.getEditText()?.setText("")
            }

            if (inputpassword.getEditText()?.getText() == null) {
                inputpassword.getEditText()?.setText("")
            }

            if (checkLogin == true) {
                val moveRegister = Intent(this@RegisterActivity, MainActivity::class.java)
                mBundle.putString("Username", inputusername.getEditText()?.getText().toString())
                mBundle.putString("Password", inputpassword.getEditText()?.getText().toString())
                moveRegister.putExtra("register", mBundle)
                startActivity(moveRegister)

            }
            if (!checkLogin) return@OnClickListener



            CoroutineScope(Dispatchers.IO).launch {
                run {
                    db.userDao().addUser(
                        User(0, username, password, email, tanggalLahir, noTelp)
                    )
                    finish()
                }
            }
            intent.putExtra("Register", mBundle)
            intent.putExtra("intent_id", 0)
            startActivity(intent)


        })

        binding.btnReset.setOnClickListener {
            inputusername.editText?.setText("")
            inputpassword.editText?.setText("")
            inputemail.editText?.setText("")
            inputnoTelp.editText?.setText("")
            inputtanggalLahir.editText?.setText("")

            inputusername.setError("")
            inputpassword.setError("")
            inputemail.setError("")
            inputnoTelp.setError("")
            inputtanggalLahir.setError("")

        }

    }


}
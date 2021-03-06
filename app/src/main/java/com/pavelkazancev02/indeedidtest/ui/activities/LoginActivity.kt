package com.pavelkazancev02.indeedidtest.ui.activities

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.hardware.biometrics.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import com.pavelkazancev02.indeedidtest.R
import com.pavelkazancev02.indeedidtest.Utils
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.Executors

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_login)
        btn_sign_up.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        login_login_btn.setOnClickListener{
            loginWithCredentials()
        }
        val executor = Executors.newSingleThreadExecutor()
        val biometricPrompt = BiometricPrompt.Builder(this)
            .setTitle("Login by Fingerprint")
            .setNegativeButton("Cancel", executor, DialogInterface.OnClickListener {
                    dialogInterface, i ->
            }).build()
        val loginActivity = this
        login_fingerprint.setOnClickListener {
            biometricPrompt.authenticate(CancellationSignal(), executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    loginActivity.runOnUiThread(object : Runnable{
                        override fun run() {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }
                    })
                }
            })
        }
    }

    private fun updateUI(){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
    }

    private fun loginWithCredentials(){
        if(login_login.text.toString().isEmpty()){
            login_login.error = "Please, enter a login"
            login_login.requestFocus()
            return
        }

        if(login_password.text.toString().isEmpty()){
            login_password.error = "Please, enter a password"
            login_password.requestFocus()
            return
        }

        val loginString = login_login.text.toString()+" "+login_password.text.toString()
        val base64EncryptedLogin = Utils.getStringFromSP(this, loginString)
        if (base64EncryptedLogin!=null) {
            updateUI()
        }
        else {
            Toast.makeText(this, "User does not exists", Toast.LENGTH_LONG).show()
        }
    }
}

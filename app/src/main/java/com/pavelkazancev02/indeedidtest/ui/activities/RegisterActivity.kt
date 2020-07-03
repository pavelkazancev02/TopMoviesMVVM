package com.pavelkazancev02.indeedidtest.ui.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.pavelkazancev02.indeedidtest.R
import com.pavelkazancev02.indeedidtest.Utils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


class RegisterActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_register)
        register_login_btn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        register_register_btn.setOnClickListener {
            registerUser()
        }

        register_scan_qr.setOnClickListener{
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()

        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                val decodedUrlString = Base64.decode(result.contents, Base64.DEFAULT).toString(Charsets.UTF_8)
                val login = decodedUrlString.subSequence(decodedUrlString.indexOf("=")+1, decodedUrlString.indexOf("&"))
                val password = decodedUrlString.substringAfterLast("=")
                register_login.setText(login)
                register_password.setText(password)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun registerUser(){

        if(register_login.text.toString().isEmpty()){
            register_login.error = "Please, enter a login"
            register_login.requestFocus()
            return
        }

        if(register_password.text.toString().isEmpty()){
            register_password.error = "Please, enter a password"
            register_password.requestFocus()
            return
        }

        val registerString = register_login.text.toString()+" "+register_password.text


        val base64EncryptedCredentials = Utils.getStringFromSP(this, registerString)

        if (base64EncryptedCredentials==null) {
            storeLoginAndPassword(registerString)
        }
        else {
            Toast.makeText(this, "User already exists", Toast.LENGTH_LONG).show()
            return
        }
        Toast.makeText(this, "Registration Completed", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun storeLoginAndPassword(registerString: String){
        val secretKey = createKey()
        val cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" +
             KeyProperties.ENCRYPTION_PADDING_PKCS7)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val encryptionIv = cipher.getIV();
        val loginBytes = cipher.doFinal(registerString.toByteArray(Charsets.UTF_8))
        val encryptedLogin = Base64.encodeToString(loginBytes, Base64.DEFAULT)

        Utils.saveStringInSP(this, registerString, encryptedLogin);
        Utils.saveStringInSP(this, "encryptionIv", Base64.encodeToString(encryptionIv, Base64.DEFAULT))
    }

    private fun createKey() : SecretKey{
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        keyGenerator.init(KeyGenParameterSpec.Builder( "Key",
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .build());
        return keyGenerator.generateKey()
    }
}
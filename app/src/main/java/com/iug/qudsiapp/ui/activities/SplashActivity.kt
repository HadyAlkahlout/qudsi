package com.iug.qudsiapp.ui.activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.iug.qudsiapp.R
import com.iug.qudsiapp.util.Commons
import com.iug.qudsiapp.util.ConnectionLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {

    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Commons.setLocale("ar", this)
        when (Commons.getSharedPreferences(this).getBoolean(Commons.IS_NIGHT, false)) {
            false ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            true ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this@SplashActivity, { isNetworkAvailable ->
            if (isNetworkAvailable) {
                lifecycleScope.launch(Dispatchers.IO){
                    delay(3000)
                    withContext(Dispatchers.Main){
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                }
            } else {
                val builder = AlertDialog.Builder(this@SplashActivity)
                builder.setTitle("مشكلة في الانترنت")
                builder.setMessage("لا يوجد اتصال بالانترنت!!\n" +
                        "قم بالاتصال بشبكة الانترنت وحاول مجدداً")
                builder.setPositiveButton("حسناً") { _, _ -> finish() }
                val dialog = builder.create()
                dialog.show()
            }
        })

    }

}
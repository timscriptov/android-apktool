package com.mcal.example

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import brut.androlib.Androlib
import brut.androlib.ApkDecoder
import com.mcal.androlib.options.BuildOptions
import com.mcal.apktool.R
import com.mcal.example.utils.ApkToolHelper.decode
import com.mcal.example.utils.FileHelper.copyAssetsFile
import com.mcal.example.utils.FileHelper.getDecodeDir
import com.mcal.example.utils.FileHelper.getToolsDir
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPermission()
        installTools()
        val btnDecode = findViewById<Button>(R.id.decode)
        btnDecode.setOnClickListener {
            val apkPath =  findViewById<EditText>(R.id.apk_path).text.toString().trim()
            if (apkPath.isNotEmpty()) {
                val apkFile = File(apkPath)
                if (apkFile.exists()) {
                    val dialog = ProgressDialog(this).apply {
                        setMessage("Decoding...")
                        show()
                    }
                    btnDecode.isEnabled = false
                    CoroutineScope(Dispatchers.IO).launch {
                        val context = this@MainActivity
                        decode(apkFile, getDecodeDir(context), getToolsDir(context).path)
                        withContext(Dispatchers.Main) {
                            btnDecode.isEnabled = true
                            dialog.dismiss()
                            Toast.makeText(context, "Finished", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    @Throws(Exception::class)
    fun installTools() {
        val path = getToolsDir(this)
        copyAssetsFile(this, "android-framework.jar", File(path, "android-framework.jar"))
        copyAssetsFile(this, "android-framework.jar", File(path, "1.apk"))
        copyAssetsFile(this, "aapt", File(path, "aapt"))
        copyAssetsFile(this, "aapt2", File(path, "aapt2"))
    }

    private fun initPermission() {
        @SuppressLint("ObsoleteSdkInt")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Settings.ACTION_MANAGE_OVERLAY_PERMISSION) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                    ), 1
                )
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q && !Environment.isExternalStorageManager()) {
            val intent = Intent(
                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }
    }
}
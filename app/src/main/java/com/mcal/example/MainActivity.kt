package com.mcal.example

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
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
import android.widget.TextView
import android.widget.Toast
import com.mcal.androlib.utils.Logger
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
import java.util.logging.Level


class MainActivity : Activity(), Logger {
    private lateinit var logView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logView = findViewById(R.id.log)
        initPermission()
        installTools()
        val btnDecode = findViewById<Button>(R.id.decode)
        btnDecode.setOnClickListener {
            val apkPath = findViewById<EditText>(R.id.apk_path).text.toString().trim()
            if (apkPath.isNotEmpty()) {
                val apkFile = File(apkPath)
                if (apkFile.exists()) {
                    logView.text = ""
                    val dialog = ProgressDialog(this).apply {
                        setMessage("Decoding...")
                        show()
                    }
                    btnDecode.isEnabled = false
                    CoroutineScope(Dispatchers.IO).launch {
                        val context = this@MainActivity
                        val decodeDir = getDecodeDir(context)
                        decodeDir.deleteRecursively()
                        decode(apkFile, decodeDir, getToolsDir(context).path, context)
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

    @SuppressLint("SetTextI18n")
    private fun setText(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            logView.text = logView.text.toString() + "\n$message"
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

    private fun alertDialog(title: String, message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
        }.show()
    }

    override fun error(args: String?) {
        if (args != null) {
            alertDialog("Error", String.format("E: %s", args))
        }
    }

    override fun log(level: Level, format: String?, ex: Throwable?) {
        val ch = level.name[0]
        val fmt = "%c: %s"
        format?.let {
            setText(String.format(fmt, ch, format))
        }
        log(fmt, ch, ex)
    }

    private fun log(fmt: String, ch: Char, ex: Throwable?) {
        if (ex == null) {
            return
        }
        setText(String.format(fmt, ch, ex.message))
        for (ste in ex.stackTrace) {
            setText(String.format(fmt, ch, ste))
        }
        log(fmt, ch, ex.cause)
    }

    override fun fine(args: String?) {
        setText(String.format("F: %s", args))
    }

    override fun warning(args: String?) {
        setText(String.format("W: %s", args))
    }

    override fun info(args: String?) {
        setText(String.format("I: %s", args))
    }
}
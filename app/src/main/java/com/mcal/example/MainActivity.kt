package com.mcal.example

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.mcal.androlib.utils.Logger
import com.mcal.apktool.R
import com.mcal.apktool.databinding.ActivityMainBinding
import com.mcal.example.utils.ApktoolHelper
import com.mcal.example.utils.FileHelper.copyAssetsFile
import com.mcal.example.utils.FileHelper.getToolsDir
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.logging.Level


class MainActivity : Activity(), Logger {
    private val binding by lazy(LazyThreadSafetyMode.NONE) { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var framework: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        framework = File(getToolsDir(this), "android.jar")
        initPermission()
        installTools()
        setCustomFramework()
        decodeClick()
        buildClick()
        gitHubClick()
        telegramClick()
    }

    private fun gitHubClick() {
        binding.github.setOnClickListener {
            openUrl("https://github.com/timscriptov")
        }
    }

    private fun telegramClick() {
        binding.telegram.setOnClickListener {
            openUrl("https://t.me/apkeditorproofficial")
        }
    }

    private fun openUrl(url: String) {
        runCatching {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            })
        }
    }

    private fun buildClick() {
        val decodePathView = binding.decodePath
        val buildView = binding.build
        val decodeView = binding.decode
        buildView.setOnClickListener {
            val decodePath = decodePathView.text.toString().trim()
            if (decodePath.isNotEmpty()) {
                val context = this@MainActivity
                val decodeFolder = File(decodePath)
                if (decodeFolder.exists()) {
                    binding.log.text = ""
                    binding.progressbar.visibility = View.VISIBLE
                    buildView.isEnabled = false
                    decodeView.isEnabled = false
                    CoroutineScope(Dispatchers.IO).launch {
                        val decodeDir = File(decodePathView.text.toString())
                        val apkFile = File(binding.outApkPath.text.toString())
                        try {
                            ApktoolHelper.buildProject(
                                apkFile,
                                decodeDir,
                                getToolsDir(context).path,
                                framework,
                                context
                            )
                            withContext(Dispatchers.Main) {
                                buildView.isEnabled = true
                                decodeView.isEnabled = true
                                binding.progressbar.visibility = View.GONE
                                fine("Building finished.")
                            }
                        } catch (e: Exception) {
                            e.message?.let { it1 -> alertDialog("Error", it1) }
                        }
                    }
                }
            }
        }
    }

    private fun decodeClick() {
        val buildView = binding.build
        val decodeView = binding.decode
        decodeView.setOnClickListener {
            val apkPath = binding.apkPath.text.toString().trim()
            if (apkPath.isNotEmpty()) {
                val context = this@MainActivity
                val apkFile = File(apkPath)
                if (apkFile.exists()) {
                    binding.log.text = ""
                    binding.progressbar.visibility = View.VISIBLE
                    decodeView.isEnabled = false
                    buildView.isEnabled = false
                    CoroutineScope(Dispatchers.IO).launch {
                        val decodeDir = File(binding.outDirPath.text.toString())
                        decodeDir.deleteRecursively()
                        try {
                            ApktoolHelper.decode(
                                apkFile,
                                decodeDir,
                                framework,
                                context
                            )
                            withContext(Dispatchers.Main) {
                                decodeView.isEnabled = true
                                buildView.isEnabled = true
                                binding.progressbar.visibility = View.GONE
                                binding.decodePath.setText(decodeDir.path)
                                fine("Decoding finished.")
                            }
                        } catch (e: Exception) {
                            e.message?.let { it1 -> alertDialog("Error", it1) }
                        }
                    }
                }
            }
        }
    }

    private fun setCustomFramework() {
        findViewById<EditText>(R.id.fw_path).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    val file = File(s.toString())
                    if (file.exists()) {
                        framework = file
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setText(message: String) {
        runOnUiThread {
            val logView = binding.log
            logView.text = logView.text.toString() + "\n$message"
        }
    }

    private fun installTools() {
        kotlin.runCatching {
            val path = getToolsDir(this)
            copyAssetsFile(this, "android-framework.jar", File(path, "android.jar"), false)
            copyAssetsFile(this, "aapt", File(path, "aapt"), true)
            copyAssetsFile(this, "aapt2", File(path, "aapt2"), true)
        }
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
        runOnUiThread {
            AlertDialog.Builder(this).apply {
                setTitle(title)
                setMessage(message)
            }.show()
        }
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
package com.example.musicrecorder

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Build.VERSION.SDK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.os.Environment.getExternalStorageState
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.musicrecorder.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
     var mediaRecorder: MediaRecorder? = null
     var mediaPlayer: MediaPlayer? =null
    private var permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private lateinit var binding: ActivityMainBinding


    var REQUEST_CODE = 200
    private var dirPath=""
   private var filename=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        var binding :ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       dirPath = "${externalCacheDir?.absolutePath}/"
        var simpleDateFormat=SimpleDateFormat("yyyy.MM.DD.hh.mm.ss")
        var date:String = simpleDateFormat.format(Date())
        filename="audio_record_$date"



        if (checkPermission()){
            binding.buttonRecord.setOnClickListener { startRecording() }
            binding.buttonStop.setOnClickListener {stopRecording()  }
            binding.buttonPlay.setOnClickListener {startPlaying()  }
            binding.buttonPause.setOnClickListener { stopPlaying() }
        }else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.RECORD_AUDIO),REQUEST_CODE)
        }
    }

    private fun checkPermission():Boolean {
        return ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED
    }
    private fun startRecording(){
        try {
            mediaRecorder = MediaRecorder()
            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder?.setOutputFile("$dirPath$filename.mp3")
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()

        }catch(e:Exception){
            e.printStackTrace()
        }

    }
    private fun stopRecording(){
        mediaRecorder?.stop()
        mediaRecorder?.release()
        mediaRecorder = null
    }
    private fun startPlaying(){
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource("$dirPath$filename.mp3")
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }
    }
    private fun stopPlaying(){
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun getRecordingFilePath(): String {
        val contextWrapper:ContextWrapper = ContextWrapper(applicationContext)
        val musicDirectory:File = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)!!
        val file:File = File(musicDirectory,"testRecordingFile"+"mp3")
        return file.path
    }

    override fun onStop() {
        super.onStop()
        mediaRecorder?.release()
        mediaRecorder = null
        mediaPlayer?.release()
        mediaPlayer=null

    }
}
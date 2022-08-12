package com.example.musicrecorder

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Build.VERSION.SDK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.*
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
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
    lateinit var myService: MyService


    var REQUEST_CODE = 200
    //var requestCode = 100
   // private var dirPath=""
  // private var filename=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


     //  dirPath = "${externalCacheDir?.absolutePath}/"
      //  var simpleDateFormat=SimpleDateFormat("yyyy.MM.DD.hh.mm.ss")
    //    var date:String = simpleDateFormat.format(Date())
     //   filename="audio_record_$date"



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
           ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE),REQUEST_CODE)
         //   ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),requestCode)
        }
    }

    private fun checkPermission():Boolean {
        return ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
    }
    private fun startRecording(){
//        try {
//            mediaRecorder = MediaRecorder()
//            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
//            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//            mediaRecorder?.setOutputFile(getRecordingFilePath())
//            mediaRecorder!!.prepare()
//            mediaRecorder!!.start()
//
//        }catch(e:Exception){
//            e.printStackTrace()
//        }
        val intent  = Intent(this,MyService::class.java)
        startService(intent)


    }
    private fun stopRecording(){
        val intent  = Intent(this,MyService::class.java)
        stopService(intent)
//        myService= MyService()
//        myService.mediaRecorder?.stop()
//        myService.mediaRecorder?.release()
//        myService.mediaRecorder = null
    }
    private fun startPlaying(){
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(getRecordingFilePath())
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
       //val musicDirectory :String = Environment.getExternalStorageDirectory().
        val file:File = File(musicDirectory,"testRecordingFile"+".mp3")

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
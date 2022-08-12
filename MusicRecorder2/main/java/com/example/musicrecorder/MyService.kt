package com.example.musicrecorder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.io.File

class MyService : Service() {
    val name="MyChannel"
    val CHANNELID = "Mynotifcationchannel"
    var mediaRecorder: MediaRecorder? = null
    var mediaPlayer:MediaPlayer? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val myChannel = NotificationChannel(CHANNELID,name,NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(myChannel)

        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startRecording()
        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent:PendingIntent = PendingIntent.getActivity(this,0,intent,0)
        val notification = NotificationCompat.Builder(this,CHANNELID)
            .setContentTitle("VoiceRecorder")
            .setContentText("Voice recorder app is Running")
            .setSmallIcon(R.drawable.mic)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1,notification)
        return START_NOT_STICKY


    }
    public fun startRecording() {
        try {
            mediaRecorder = MediaRecorder()
            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder?.setOutputFile(getRecordingFilePath())
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    public fun getRecordingFilePath(): String {
        val contextWrapper: ContextWrapper = ContextWrapper(applicationContext)
        val musicDirectory: File = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)!!
        //val musicDirectory :String = Environment.getExternalStorageDirectory().
        val file: File = File(musicDirectory,"testRecordingFile"+".mp3")

        return file.path
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaRecorder?.stop()
        mediaRecorder?.release()
        mediaRecorder=null
    }
}
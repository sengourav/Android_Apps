package com.example.sensor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class MainActivity : AppCompatActivity(),SensorEventListener{
    lateinit var sensorManager: SensorManager
    lateinit var gyroSensor: Sensor
    lateinit var file : File
    lateinit var fileWriter: FileWriter
    lateinit var bufferedWriter: BufferedWriter
    lateinit var jsonArray: JSONArray
    lateinit var jsonObject: JSONObject
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        jsonArray = JSONArray()
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            changeActivity()
        }
        if(sensorManager!=null){
            gyroSensor= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager.registerListener(this,gyroSensor,SensorManager.SENSOR_DELAY_NORMAL)

        }
        else{
            Toast.makeText(this,"no sensor detected", LENGTH_SHORT)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
           if(event?.sensor?.type==Sensor.TYPE_GYROSCOPE){
              val gyroText:TextView=findViewById(R.id.Gyro_Sensor)
                gyroText.text = "Gyro Value \nx= ${event!!.values[0]}\n\n" + "y = ${event.values[1]}\n\n"+"z = ${event.values[2]}"}
        jsonObject= JSONObject()
        try {
            jsonObject.put("gyroX",event!!.values[0])
            jsonObject.put("gyroY",event!!.values[1])
            jsonObject.put("gyroZ",event!!.values[2])
        }catch (e:JSONException){
            e.printStackTrace()
        }
        jsonArray.put(jsonObject)
        file = File(applicationContext.externalCacheDir,"gyro.json")
        fileWriter = FileWriter(file)
        bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(jsonArray.toString())
        bufferedWriter.close()

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
    private fun changeActivity(){
        val intent: Intent = Intent(this,MainActivity2::class.java)
        startActivity(intent)

    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}


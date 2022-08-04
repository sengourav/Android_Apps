package com.example.sensor

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class MainActivity3 : AppCompatActivity(),SensorEventListener {
    lateinit var accelSensor :Sensor
    lateinit var sensorManager:SensorManager
    lateinit var file:File
    lateinit var fileWriter: FileWriter
    lateinit var bufferedWriter: BufferedWriter
    lateinit var jsonObject: JSONObject
    lateinit var jsonArray: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        jsonArray= JSONArray()
        val button: Button = findViewById(R.id.button3)
        button.setOnClickListener {
            changeActivity()

        }
        if(sensorManager!=null){
            accelSensor= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this,accelSensor,SensorManager.SENSOR_DELAY_NORMAL)
        } else Toast.makeText(this,"no sensor detected", Toast.LENGTH_SHORT)

    }

    override fun onSensorChanged(event: SensorEvent?) {
         if(event?.sensor?.type==Sensor.TYPE_ACCELEROMETER){
             val accelText: TextView =findViewById(R.id.Accel_Sensor)
            accelText.text = "Accel Value\n x = ${event!!.values[0]}\n" + "y = ${event.values[1]}\n"+"z = ${event.values[2]}\n"}
        jsonObject = JSONObject()
        try {
            jsonObject.put("AccelX",event!!.values[0])
            jsonObject.put("AccelY",event!!.values[1])
            jsonObject.put("AccelZ",event!!.values[2])
        }catch (e:JSONException){
            e.printStackTrace()
        }
        jsonArray.put(jsonObject)
        file=File(applicationContext.externalCacheDir,"accel.json")
        fileWriter =FileWriter(file)
        bufferedWriter=BufferedWriter(fileWriter)
        bufferedWriter.write(jsonArray.toString())
        bufferedWriter.close()

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
    private fun changeActivity(){
        val intent: Intent = Intent(this,MainActivity4::class.java)
        startActivity(intent)

    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}
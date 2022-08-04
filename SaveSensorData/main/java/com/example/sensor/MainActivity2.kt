package com.example.sensor

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.lang.NullPointerException
import org.json.JSONArray as JSONArray1

class MainActivity2 : AppCompatActivity(),SensorEventListener {
    lateinit var sensorManager: SensorManager
    lateinit var lightSensor: Sensor
     lateinit var jsonArray: JSONArray1
     lateinit var jsonObject: JSONObject
    lateinit var file : File
     lateinit var userString: String
    lateinit var filewriter:FileWriter
    lateinit var bufferedWriter: BufferedWriter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
       val button:Button = findViewById(R.id.button2)

        button.setOnClickListener {
            changeActivity()
        }
        jsonArray = JSONArray1()
        //getting sensor services
        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if(sensorManager!=null) {
            //registering listener the sensor
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL)

        }else  Toast.makeText(this,"no sensor detected", Toast.LENGTH_SHORT)

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor?.type == Sensor.TYPE_LIGHT) {
            val lightText: TextView = findViewById(R.id.light_Sensor)
            lightText.text = "Light Value  = ${p0!!.values[0]}"

             jsonObject = JSONObject()
            try {
                jsonObject.put("light value",p0!!.values[0])
            }catch (e :JSONException){
                e.printStackTrace()
            }
            jsonArray.put(jsonObject)
            var userString : String = jsonArray.toString()
            file = File(applicationContext.externalCacheDir, "user.json")
            filewriter = FileWriter(file)
            bufferedWriter = BufferedWriter(filewriter)
            bufferedWriter.write(userString)
            bufferedWriter.close()

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    private fun changeActivity() {
        val intent: Intent = Intent(this, MainActivity3::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
 }
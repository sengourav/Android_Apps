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

class MainActivity2 : AppCompatActivity(),SensorEventListener {
    lateinit var sensorManager: SensorManager
    lateinit var lightSensor: Sensor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val button:Button = findViewById(R.id.button2)
        button.setOnClickListener {
            changeActivity()
        }

        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if(sensorManager!=null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL)

        }else  Toast.makeText(this,"no sensor detected", Toast.LENGTH_SHORT)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor?.type == Sensor.TYPE_LIGHT){
            val lightText : TextView = findViewById(R.id.light_Sensor)
            lightText.text = "Light Value  = ${p0!!.values[0]}\n" }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
    private fun changeActivity(){
        val intent: Intent = Intent(this,MainActivity3::class.java)
        startActivity(intent)

    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}
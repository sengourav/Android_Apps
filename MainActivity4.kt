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

class MainActivity4 : AppCompatActivity(),SensorEventListener {
    lateinit var sensorManager: SensorManager
    lateinit var magnoSensor :Sensor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val button: Button = findViewById(R.id.button4)
        button.setOnClickListener {
            changeActivity()
        }
        if (sensorManager != null) {
            magnoSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
            sensorManager.registerListener(this, magnoSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type==Sensor.TYPE_MAGNETIC_FIELD){
            val magnetoText: TextView =findViewById(R.id.Magneto_Sensor)
            magnetoText.text = "Magno Value\n x = ${event!!.values[0]}\n\n" + "y = ${event.values[1]}\n\n"+"z = ${event.values[2]}\n"}

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
    private fun changeActivity(){
        val intent: Intent = Intent(this,MainActivity::class.java)
        startActivity(intent)

    }
    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}
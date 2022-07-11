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
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

class MainActivity : AppCompatActivity(),SensorEventListener{
    lateinit var sensorManager: SensorManager
   // lateinit var lightSensor: Sensor
    //lateinit var accelSensor :Sensor
    //lateinit var magnoSensor :Sensor
    lateinit var gyroSensor: Sensor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            changeActivity()
        }
        if(sensorManager!=null){
         //   lightSensor= sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
          //  sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL)
            //accelSensor= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            //sensorManager.registerListener(this,accelSensor,SensorManager.SENSOR_DELAY_NORMAL)
            //magnoSensor= sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
            //sensorManager.registerListener(this,magnoSensor,SensorManager.SENSOR_DELAY_NORMAL)
            gyroSensor= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager.registerListener(this,gyroSensor,SensorManager.SENSOR_DELAY_NORMAL)

        }
        else{
            Toast.makeText(this,"no sensor detected", LENGTH_SHORT)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
       // if (event?.sensor?.type == Sensor.TYPE_LIGHT){
       //val lightText :TextView = findViewById(R.id.light_Sensor)
       // lightText.text = "Light Value  = ${event!!.values[0]}\n" }
         //   else if(event?.sensor?.type==Sensor.TYPE_ACCELEROMETER){
        //val accelText:TextView=findViewById(R.id.Accel_Sensor)
        //accelText.text = "Accel Value\n x = ${event!!.values[0]}\n\n" + "y = ${event.values[1]}\n\n"+"z = ${event.values[2]}\n"}
           if(event?.sensor?.type==Sensor.TYPE_GYROSCOPE){
              val gyroText:TextView=findViewById(R.id.Gyro_Sensor)
                gyroText.text = "Gyro Value \nx= ${event!!.values[0]}\n\n" + "y = ${event.values[1]}\n\n"+"z = ${event.values[2]}"}
            //else if(event?.sensor?.type==Sensor.TYPE_MAGNETIC_FIELD){
              //  val magnetoText:TextView=findViewById(R.id.Magneto_Sensor)
                //magnetoText.text = "Magno Value\n x = ${event!!.values[0]}\n\n" + "y = ${event.values[1]}\n\n"+"z = ${event.values[2]}\n"}

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


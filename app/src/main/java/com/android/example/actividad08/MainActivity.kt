package com.android.example.actividad08

import android.annotation.SuppressLint
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var detalle: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private var existeSensorProximidad: Boolean = false
    private lateinit var listadoSensores: List<Sensor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        detalle = findViewById(R.id.txtDetalle)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    @SuppressLint("SetTextI18n")
    fun clickListado(view: View?){
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        listadoSensores = sensorManager.getSensorList(Sensor.TYPE_ALL)
        detalle.text="Lista de sensores del dispositivo"

        for(sensor in listadoSensores){
            detalle.text = "${detalle.text}\nNombre: ${sensor.name}\nVersion: ${sensor.version}"
        }
    }

    @SuppressLint("SetTextI18n")
    fun clickMagnetico(view: View?){
        if(sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            Toast.makeText(applicationContext,"El dispositivo tiene sensor mag",
                Toast.LENGTH_SHORT).show()
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!

            detalle.setBackgroundColor(Color.GRAY)
            detalle.text = "Propiedades del sensor Magnetico: \nNombre: ${sensor.name}"+
                    "\nVersion: ${sensor.version}\nFabricante: ${sensor.vendor}"
        }else {
            Toast.makeText(applicationContext, "El dispositivo no cuenta con sensor magnetico",
                Toast.LENGTH_SHORT).show()
        }
    }

    fun clickProximidad(view: View?) {
        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            val proximidadSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            existeSensorProximidad = true
            detalle.text = "El dispositivo tiene sensor: ${proximidadSensor!!.name}"
            detalle.setBackgroundColor(Color.GREEN)
        } else {
            detalle.text = "No se cuenta con sensor de proximidad"
            existeSensorProximidad = false
        }
    }

    fun clickGiroscopio(view: View?) {
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            val giroscopioSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            detalle.text = "El dispositivo tiene sensor de giroscopio: ${giroscopioSensor!!.name}"
            detalle.setBackgroundColor(Color.CYAN)
        } else {
            detalle.text = "No se cuenta con sensor de giroscopio"
        }
    }

    fun clickAcelerometro(view: View?) {
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            val acelerometroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            detalle.text = "El dispositivo tiene sensor de acelerómetro: ${acelerometroSensor!!.name}"
            detalle.setBackgroundColor(Color.YELLOW)
        } else {
            detalle.text = "No se cuenta con sensor de acelerómetro"
        }
    }

    fun clickPodometro(view: View?) {
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            val podometroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            detalle.text = "El dispositivo tiene sensor de podómetro: ${podometroSensor!!.name}"
            detalle.setBackgroundColor(Color.MAGENTA)
        } else {
            detalle.text = "No se cuenta con sensor de podómetro"
        }
    }

    override fun onSensorChanged(event: SensorEvent?){
        val valorCambio: Float
        if(existeSensorProximidad) {
            valorCambio = event!!.values[0]
            if(valorCambio < 1.0) {
                detalle.textSize = 30f
                detalle.setBackgroundColor(Color.BLUE)
                detalle.setTextColor(Color.WHITE)
                detalle.text = "\nCERCA $valorCambio"
            } else {
                detalle.textSize = 14f
                detalle.setBackgroundColor(Color.GREEN)
                detalle.setTextColor(Color.BLACK)
                detalle.text = "\nLEJOS $valorCambio"
            }
        } else {
            Toast.makeText(applicationContext, "Sin cambios", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }
}
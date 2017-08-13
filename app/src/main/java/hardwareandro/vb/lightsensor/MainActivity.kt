package hardwareandro.vb.lightsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() ,SensorEventListener {

    var sensor:Sensor ?=null
    var sensorManager:SensorManager?=null
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var isRunning =false

    override fun onSensorChanged(event: SensorEvent?) {

        //ışık değerini yakalıp eğer belirli bir parlıkltaysa
        //ve o an bir müzik çalmıyors ise başlatıyoruz
        if (event!!.values[0]>40 && isRunning==false){
            isRunning=true

            try {
                //sensorden gelen ışık değerine göre mp3 başlıyor
                var mp=MediaPlayer()
                mp.setDataSource("http://api.mp3lerbiz.net/mp3dinle/keApCsIWeoU.mp3")
                mp.prepare()
                mp.start()

            }catch (ex:Exception) {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sensor manager telefonun sensor hizmetlerini kullanmasını istiyorum
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        //cihazınızda sensor kontrolu
        if(sensor==null) Toast.makeText(this    ,"Hata",Toast.LENGTH_SHORT).show()

    }

    override fun onResume() {
        super.onResume()

        //app tekrar kullanmaya başladığında çalışan kısım
        //sensor dinleme yapıyor
        sensorManager!!.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST)

    }


    override fun onPause() {
        super.onPause()
        //app alta alındığında çalışan kısım
        //dinleme kesiliyor
        sensorManager!!.unregisterListener(this)
    }
}

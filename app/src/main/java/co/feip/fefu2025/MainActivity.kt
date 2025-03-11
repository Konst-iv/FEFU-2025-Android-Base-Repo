package co.feip.fefu2025

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

class NetworkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        if (networkCapabilities != null) {
            val isConnected = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            if (isConnected) {
                Log.d("NetworkReceiver", "Соединение доступно")
            } else {
                Log.d("NetworkReceiver", "Соединение недоступно")
            }
        } else {
            Log.d("NetworkReceiver", "Нет активного подключения")
        }
    }
}

class MainActivity : AppCompatActivity() {
    private var numberOfTaps = 0
    private lateinit var networkReceiver: NetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        // Регистрация BroadcastReceiver
        networkReceiver = NetworkReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)

        // Восстановление состояния
        if (savedInstanceState != null) {
            numberOfTaps = savedInstanceState.getInt("numberOfTaps", 0)
        }

        // Получение ссылки на TextView
        val tvv = findViewById<TextView>(R.id.tv)
        tvv.text = "$numberOfTaps Clicks"

        // Установка слушателя касаний
        tvv.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                numberOfTaps++ // Увеличиваем количество нажатий
                tvv.text = "$numberOfTaps Clicks" // Обновляем текст в TextView
            }
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("numberOfTaps", numberOfTaps)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
    }
}
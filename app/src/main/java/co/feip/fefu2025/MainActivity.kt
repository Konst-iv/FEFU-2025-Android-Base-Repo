package co.feip.fefu2025

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val anime = listOf(
        "Хорор" to Color.parseColor("#795548"),
        "Комедия" to Color.parseColor("#F44336"),
        "Исекай" to Color.parseColor("#FFc0cb"),
        "Повседневность" to Color.parseColor("#E91E63"),
        "Фэнтези" to Color.parseColor("#0000FF"),
        "История" to Color.parseColor("#4CAF50"),
        "Детектив" to Color.parseColor("#FF0000")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnClick = findViewById<Button>(R.id.btnClick)
        val flexLayout = findViewById<FlexBoxLayout>(R.id.flexLayout)
        btnClick.setOnClickListener {
            val (name, color) = anime.random()
            val genreView = Genre_veiw(this)
            genreView.setGenreName(name)
            genreView.setColor(color)
            flexLayout.addView(genreView)
        }
    }
}
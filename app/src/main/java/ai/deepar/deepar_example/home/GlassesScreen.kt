package ai.deepar.deepar_example.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ai.deepar.deepar_example.R
import android.annotation.SuppressLint
import android.content.Intent
import android.widget.ImageButton
import androidx.cardview.widget.CardView

class GlassesScreen : AppCompatActivity() {
  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_glasses_screen)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val backGlasses = findViewById<ImageButton>(R.id.actionBarBackArrowGlasses)
    backGlasses.setOnClickListener {
      finish()
    }

    // variable for card view
    val firstGlasses = findViewById<CardView>(R.id.glassesFirstCard)
    firstGlasses.setOnClickListener {
      startActivity(Intent(this@GlassesScreen, RayBanGlasses::class.java))
    }

    val secondGlasses = findViewById<CardView>(R.id.glassesSecondCard)
    secondGlasses.setOnClickListener {
      startActivity(Intent(this@GlassesScreen, AviatorGlasses::class.java))
    }
    val thirdGlasses = findViewById<CardView>(R.id.glassesThirdCard)
    thirdGlasses.setOnClickListener {
      startActivity(Intent(this@GlassesScreen, TitaniumGlasses::class.java))
    }
  }
}

package ai.deepar.deepar_example.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.camera.AviatorActivity
import ai.deepar.deepar_example.camera.MainActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class AviatorGlasses : AppCompatActivity() {
  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_aviator_glasses)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val backAviatorGlasses = findViewById<ImageButton>(R.id.actionBarBackArrowAviatorGlasses)
    backAviatorGlasses.setOnClickListener {
      finish()
    }
    val virtualTO1 = findViewById<Button>(R.id.virtualTryOn1)
    virtualTO1.setOnClickListener {
      val builder = AlertDialog.Builder(this)

      // Set the title and message for the dialog
      builder.setTitle("Virtual Try-On")
      builder.setMessage("Would you like to start a virtual try-on session?\n\nDISCLAIMER: Sizes may vary")

      // Set positive and negative buttons
      builder.setPositiveButton("Yes") { dialog, which ->
        // Handle the "Yes" click (e.g., start the virtual try-on activity)
        Toast.makeText(this, "Starting virtual try-on...", Toast.LENGTH_SHORT).show()
        // ... Your logic to start the virtual try-on
        startActivity(Intent(this@AviatorGlasses, AviatorActivity::class.java))
      }
      builder.setNegativeButton("No") { dialog, which ->
        // Handle the "No" click (e.g., close the dialog)
        dialog.dismiss()
      }

      // Create and show the dialog
      val dialog = builder.create()
      dialog.show()
    }
  }
}
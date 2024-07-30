package ai.deepar.deepar_example.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ai.deepar.deepar_example.R
import android.annotation.SuppressLint
import android.app.Notification
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class BottomNavigation : AppCompatActivity() {
  @SuppressLint("MissingInflatedId", "WrongViewCast")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_bottom_navigation)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val clickHome = findViewById<ConstraintLayout>(R.id.btnHome)
    val imgHome = findViewById<ImageView>(R.id.btnHomeImage)
    val txtHome = findViewById<TextView>(R.id.btnHomeText)

    val clickHelp = findViewById<ConstraintLayout>(R.id.btnHelp)
    val imgMyAppointments = findViewById<ImageView>(R.id.btnHelpImage)
    val txtMyAppointments = findViewById<TextView>(R.id.btnHelpText)

    val clickNotification = findViewById<ConstraintLayout>(R.id.btnNotification)
    val imgNotification = findViewById<ImageView>(R.id.btnNotificationImage)
    val txtNotification = findViewById<TextView>(R.id.btnNotificationText)

    val clickSettings = findViewById<ConstraintLayout>(R.id.btnSettings)
    val imgSettings = findViewById<ImageView>(R.id.btnSettingsImage)
    val txtSettings = findViewById<TextView>(R.id.btnSettingsText)

    imgHome.setColorFilter(ContextCompat.getColor(this, R.color.blue))
    txtHome.setTextColor(ContextCompat.getColor(this, R.color.blue))

    clickHome.setOnClickListener{
      imgHome.setColorFilter(ContextCompat.getColor(this, R.color.blue))
      txtHome.setTextColor(ContextCompat.getColor(this, R.color.blue))

      imgMyAppointments.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtMyAppointments.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      imgNotification.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtNotification.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      imgSettings.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtSettings.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      replaceFragment(HomePage())
    }
    clickHelp.setOnClickListener{
      imgHome.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtHome.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      imgMyAppointments.setColorFilter(ContextCompat.getColor(this, R.color.blue))
      txtMyAppointments.setTextColor(ContextCompat.getColor(this, R.color.blue))

      imgNotification.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtNotification.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      imgSettings.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtSettings.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      replaceFragment(Help())
    }
    clickNotification.setOnClickListener{
      imgHome.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtHome.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      imgMyAppointments.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtMyAppointments.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      imgNotification.setColorFilter(ContextCompat.getColor(this, R.color.blue))
      txtNotification.setTextColor(ContextCompat.getColor(this, R.color.blue))

      imgSettings.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtSettings.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      replaceFragment(ai.deepar.deepar_example.home.Notification())
    }
    clickSettings.setOnClickListener{
      imgHome.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtHome.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      imgMyAppointments.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtMyAppointments.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      imgNotification.setColorFilter(ContextCompat.getColor(this, R.color.bottom_nav_unselected))
      txtNotification.setTextColor(ContextCompat.getColor(this, R.color.bottom_nav_unselected))

      imgSettings.setColorFilter(ContextCompat.getColor(this, R.color.blue))
      txtSettings.setTextColor(ContextCompat.getColor(this, R.color.blue))

      replaceFragment(Settings())
    }
    replaceFragment1(HomePage())
  }
  private fun replaceFragment(fragment: Fragment) {
    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
    fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()
  }
  private fun replaceFragment1(fragment: Fragment) {
    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
    fragmentTransaction.commit()
  }

  }
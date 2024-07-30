package ai.deepar.deepar_example.patient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.home.BottomNavigation
import ai.deepar.deepar_example.home.HomePage
import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class PatientLoginActivity : AppCompatActivity() {
  private lateinit var btnRegister: Button
  private lateinit var btnLogin: Button
  private lateinit var editTxtLEmail: EditText
  private lateinit var editTxtLPassword: EditText
  private lateinit var user: FirebaseAuth

  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_patient_login)

    btnLogin = findViewById(R.id.btnDoctorLogin)
    btnRegister = findViewById(R.id.btnDoctorRegister)
    editTxtLEmail = findViewById(R.id.editTxtDoctorLEmail)
    editTxtLPassword = findViewById(R.id.editTxtDoctorLPassword)

    user = FirebaseAuth.getInstance()
    btnRegister.setOnClickListener {
      val intent = Intent(this, PatientRegisterActivity::class.java)
      startActivity(intent)
    }
    btnLogin.setOnClickListener {

      if (editTxtLEmail.text.toString() == "" || editTxtLPassword.text.toString() == "") {
        Toast.makeText(
          this,
          "Please Complete the Information",
          Toast.LENGTH_LONG
        ).show()
      } else {
        val LoginEmail = editTxtLEmail.text.toString()
        val LoginPassword = editTxtLPassword.text.toString()
        user.signInWithEmailAndPassword(LoginEmail, LoginPassword)
          .addOnCompleteListener(PatientLoginActivity()) { task ->
            if (task.isSuccessful) {
              Toast.makeText(
                this,
                "User Login Successfully",
                Toast.LENGTH_LONG
              ).show()
              val intent = Intent(this, BottomNavigation::class.java)
              startActivity(intent)
              finish()
            } else {
              Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
            }
          }
      }
    }
  }
}
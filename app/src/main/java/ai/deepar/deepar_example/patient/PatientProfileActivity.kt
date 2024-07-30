package ai.deepar.deepar_example.patient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.MainAppointmentActivity
import ai.deepar.deepar_example.patient.models.PatientData
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PatientProfileActivity : AppCompatActivity() {
  private lateinit var txtPName: TextView
  private lateinit var txtPSurname: TextView
  private lateinit var txtPAge: TextView
  private lateinit var txtPEmail: TextView
  private lateinit var btnDeleteAccount: Button
  private lateinit var btnEditProfile: Button
  private lateinit var imgPatientProfile : ImageView
  @SuppressLint("SetTextI18n")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_patient_profile)

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    txtPName = findViewById(R.id.txtPName)
    txtPSurname = findViewById(R.id.txtPSurname)
    txtPAge = findViewById(R.id.txtPAge)
    txtPEmail = findViewById(R.id.txtPEmail)
    btnDeleteAccount = findViewById(R.id.btnDeleteAccount)
    btnEditProfile = findViewById(R.id.btnEditProfile)
    imgPatientProfile = findViewById(R.id.imgPatientProfilePicture)

    if (user != null) {
      db.collection("patients").document(user.email!!)
        .get()
        .addOnSuccessListener { document ->
          if (document != null) {
            val client = document.toObject(PatientData::class.java)
            if (client != null) {
              txtPName.text = ("Name: " + client.first)
              txtPSurname.text = ("Surname: " + client.last)
              txtPAge.text = ("Age: " + client.age)
              txtPEmail.text = ("Email: " + client.email)
              Glide.with(this).load(client.image).into(imgPatientProfile)
            }
          } else {
            Log.d("DocumentSnapshot", "No Document")
          }
        }.addOnFailureListener { exception ->
          Log.d("get failed with ", exception.message.toString())
        }
    }
    btnDeleteAccount.setOnClickListener {
      AlertDialog.Builder(this)
        .setTitle("Delete Account")
        .setMessage("Do you want to Delete your Account?")
        .setPositiveButton("Yes") { _, _ ->
          val user = FirebaseAuth.getInstance().currentUser
          user?.delete()
            ?.addOnCompleteListener { task ->
              if (task.isSuccessful) {
                Log.d(
                  "FirebaseAuth",
                  "Patient Account Deleted"
                )
                val db = FirebaseFirestore.getInstance()
                db.collection("patients")
                  .document(user.email!!)
                  .delete()
                  .addOnSuccessListener {
                    Log.d(
                      "Firestore",
                      "The Document is Deleted Successfully!"
                    )
                  }
                  .addOnFailureListener { e ->
                    Log.w(
                      "Firestore",
                      "An Error Occurred",
                      e
                    )
                  }
                val intent = Intent(this, MainAppointmentActivity::class.java)
                startActivity(intent)
                finish()
              } else {
                Log.w(
                  "Firestore",
                  "An Error Occurred while Deleting the User",
                  task.exception
                )
              }
            }
        }
        .setNegativeButton("No", null)
        .show()
    }

    btnEditProfile.setOnClickListener {
      val intent = Intent(this, PatientProfileEditActivity::class.java)
      startActivity(intent)
      finish()
    }
  }

  override fun onResume() {
    super.onResume()
    updateData()
  }

  @SuppressLint("SetTextI18n")
  private fun updateData() {
    val userEmail = FirebaseAuth.getInstance().currentUser?.email
    val db = FirebaseFirestore.getInstance()

    db.collection("patients")
      .document(userEmail!!)
      .get()
      .addOnSuccessListener { document ->
        if (document != null && document.exists()) {
          val patientData = document.toObject(PatientData::class.java)
          txtPName.text = "Name: ${patientData?.first}"
          txtPSurname.text = "Surname: ${patientData?.last}"
          txtPAge.text = "Age: ${patientData?.age}"
          txtPEmail.text = "Email: ${patientData?.email}"
        }
      }
      .addOnFailureListener { e ->
        Log.e("Firestore", "Error Getting Client Data: ${e.message}", e)
      }
  }
}
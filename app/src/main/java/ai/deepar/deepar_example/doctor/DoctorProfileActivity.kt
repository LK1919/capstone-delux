package ai.deepar.deepar_example.doctor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.doctor.models.DoctorData
import ai.deepar.deepar_example.MainAppointmentActivity
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

class DoctorProfileActivity : AppCompatActivity() {
  lateinit var txtDName: TextView
  lateinit var txtDSurname: TextView
//  lateinit var txtDAge: TextView
  lateinit var txtDEmail: TextView
  lateinit var txtDField: TextView
  lateinit var btnDeleteDAccount: Button
  lateinit var btnEditProfile: Button
  lateinit var imgDoctorProfile : ImageView

  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_doctor_profile)

    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

    txtDName = findViewById(R.id.txtDName)
    txtDSurname = findViewById(R.id.txtDSurname)
//    txtDAge = findViewById(R.id.txtDAge)
    txtDEmail = findViewById(R.id.txtDEmail)
    txtDField = findViewById(R.id.txtDField)
    btnDeleteDAccount = findViewById(R.id.btnDeleteAccount)
    btnEditProfile = findViewById(R.id.btnEditProfile)
    imgDoctorProfile = findViewById(R.id.imgDoctorProfilePicture)

    if (user != null) {
      db.collection("doctors").document(user.email!!)
        .get()
        .addOnSuccessListener { document ->
          if (document != null) {
            val doctorData = document.toObject(DoctorData::class.java)
            if (doctorData != null) {
//              txtDName.text = ("Name: " + doctorData.first) ?: "N/A"
//              txtDSurname.text = ("Surname: " + doctorData.last) ?: "N/A"
////              txtDAge.text = ("Age: " + doctorData.age) ?: "N/A"
//              txtDEmail.text = ("Email: " + doctorData.email) ?: "N/A"
//              txtDField.text = ("Field: " + doctorData.field) ?: "N/A"
//              Glide.with(this).load(doctorData.image).into(imgDoctorProfile)
            }
          } else {
            Log.d("DocumentSnapshot", "No such document")
          }
        }.addOnFailureListener { exception ->
          Log.d("get failed with ", exception.message.toString())
        }
    }

    btnDeleteDAccount.setOnClickListener {
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
                  "Account has been Deleted"
                )
                val db = FirebaseFirestore.getInstance()
                db.collection("doctors")
                  .document(user.email!!)
                  .delete()
                  .addOnSuccessListener {
                    Log.d(
                      "Firestore",
                      "Document Deleted Successfully"
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
                  "An Error Occurred while Deleting the User.",
                  task.exception
                )
              }
            }
        }
        .setNegativeButton("No", null)
        .show()
    }

    btnEditProfile.setOnClickListener {
      val intent = Intent(this, DoctorProfileEditActivity::class.java)
      startActivity(intent)
      finish()
    }
  }

  override fun onResume() {
    super.onResume()
    updateData()
  }

  private fun updateData() {
    val userEmail = FirebaseAuth.getInstance().currentUser?.email
    val db = FirebaseFirestore.getInstance()

    db.collection("doctors")
      .document(userEmail!!)
      .get()
      .addOnSuccessListener { document ->
        if (document != null && document.exists()) {
          val doctorData = document.toObject(DoctorData::class.java)
//          txtDName.text = "Name: ${doctorData?.first}"
//          txtDSurname.text = "Surname: ${doctorData?.last}"
////          txtDAge.text = "Age: ${doctorData?.age}"
//          txtDEmail.text = "Email: ${doctorData?.email}"
//          txtDField.text = "Field: ${doctorData?.field}"
        }
      }
      .addOnFailureListener { e ->
        Log.e("Firestore", "Error getting doctor data: ${e.message}", e)
      }
  }
}
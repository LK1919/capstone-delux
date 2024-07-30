package ai.deepar.deepar_example.patient.services

import ai.deepar.deepar_example.doctor.models.DoctorData
import com.google.firebase.firestore.FirebaseFirestore

class DoctorService {
    fun getDoctors(callback: (List<DoctorData>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val doctorsRef = db.collection("doctors")

        doctorsRef.get().addOnSuccessListener { querySnapshot ->
            val doctorDataList = mutableListOf<DoctorData>()

            for (document in querySnapshot) {
                val doctorData = document.toObject(DoctorData::class.java)
                doctorDataList.add(doctorData)
            }
            callback(doctorDataList)
        }.addOnFailureListener {
            callback(emptyList())
        }
    }
}







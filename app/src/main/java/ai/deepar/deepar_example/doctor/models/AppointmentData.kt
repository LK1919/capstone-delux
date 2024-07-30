package ai.deepar.deepar_example.doctor.models

data class AppointmentData(
    val id : String? = null,
    val doctorEmail : String? = null,
    val patientEmail : String? = null,
    val patientName : String? = null,
    val patientImg : String? = null,
    val doctorName : String? = null,
    val doctorImg : String? = null,
    val doctorBranch : String? = null,
    val date : String? = null,
    val hour : String? = null
)

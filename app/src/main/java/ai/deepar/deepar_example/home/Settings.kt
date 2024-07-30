package ai.deepar.deepar_example.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.deepar.deepar_example.R
import android.annotation.SuppressLint
import android.app.Notification
import android.content.Intent
import android.widget.ImageButton
import android.widget.LinearLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings : Fragment() {
  // TODO: Rename and change types of parameters
  private var param1: String? = null
  private var param2: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
    arguments?.let {
      param1 = it.getString(ARG_PARAM1)
      param2 = it.getString(ARG_PARAM2)
    }
  }

  @SuppressLint("MissingInflatedId")
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_settings, container, false)

    val btnBack = view.findViewById<ImageButton>(R.id.actionBarBackArrowSettings)
    btnBack.setOnClickListener {
      requireActivity().supportFragmentManager.popBackStack()
    }

//    val btnSettingsHelp = view.findViewById<LinearLayout>(R.id.settingsHelp)
//    btnSettingsHelp.setOnClickListener {
//      startActivity(Intent(requireActivity(), Help::class.java))
//    }

    val btnSettingsLogout = view.findViewById<LinearLayout>(R.id.logoutSettings)
    btnSettingsLogout.setOnClickListener {
      startActivity(Intent(requireActivity(), SettingsLogoutScreen::class.java))
    }

//    val btnSettingsNotification = view.findViewById<LinearLayout>(R.id.settingsNotification)
//    btnSettingsNotification.setOnClickListener {
//      startActivity(Intent(requireActivity(), Notification::class.java))
//    }

    val btnSettingsAccount = view.findViewById<LinearLayout>(R.id.settingsAccount)
    btnSettingsAccount.setOnClickListener {
      startActivity(Intent(requireActivity(), SettingsAccountScreen::class.java))
    }

    return view
  }



  companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
      Settings().apply {
        arguments = Bundle().apply {
          putString(ARG_PARAM1, param1)
          putString(ARG_PARAM2, param2)
        }
      }
  }
}
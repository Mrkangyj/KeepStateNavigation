package cn.com.keepstatenavigation.ui.home

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.com.keepstatenavigation.R
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
//        root.applyPermission.setOnClickListener {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE
            )
            .onExplainRequestReason { scope, deniedList ->
                val message = "拍照功能需要您同意相册和定位权限"
                val ok = "确定"
                scope.showRequestReasonDialog(deniedList, message, ok)
            }
            .onForwardToSettings { scope, deniedList ->
                val message = "您需要去设置当中同意相册和定位权限"
                val ok = "确定"
                scope.showForwardToSettingsDialog(deniedList, message, ok)
            }
            .request { allGranted, grantedList, deniedList ->
//                    takePicture()
                if (allGranted) {
                    Toast.makeText(activity, "All permissions are granted", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(
                        activity,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
//        }
        return root
    }

    private fun takePicture() {
        Toast.makeText(activity, "开始拍照", Toast.LENGTH_SHORT).show()
    }

}
package com.baidu.ardemo

import org.json.JSONException
import org.json.JSONObject

import com.baidu.ar.ARFragment
import com.baidu.ar.constants.ARConfigKey
import com.baidu.ar.external.ARCallbackClient

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View

class ARActivity : FragmentActivity() {

    private var mARFragment: ARFragment? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        if (findViewById<View>(R.id.bdar_id_fragment_container) != null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // 准备调起AR的必要参数
            // AR_KEY:AR内容平台里申请的每个case的key
            // AR_TYPE:AR类型，目前0代表2D跟踪类型，5代表SLAM类型，后续会开放更多类型
            val arkey = intent.getStringExtra(ARConfigKey.AR_KEY) ?: ""
            val arType = intent.getIntExtra(ARConfigKey.AR_TYPE, 0)
            val data = Bundle()
            val jsonObj = JSONObject()
            try {
                jsonObj.put(ARConfigKey.AR_KEY, arkey)
                //   ar type写上   本地识图（AR Type为6）和云端识图（AR Type为7）功能  AR Key可以传空
                jsonObj.put(ARConfigKey.AR_TYPE, arType)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            data.putString(ARConfigKey.AR_VALUE, jsonObj.toString())
            mARFragment = ARFragment()
            mARFragment!!.arguments = data
            mARFragment!!.setARCallbackClient(object : ARCallbackClient {
                // 分享接口
                override fun share(title: String, content: String, shareUrl: String, resUrl: String, type: Int) {
                    // type = 1 视频，type = 2 图片
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.putExtra(Intent.EXTRA_TEXT, content)
                    shareIntent.putExtra(Intent.EXTRA_TITLE, title)
                    shareIntent.type = "text/plain"
                    // 设置分享列表的标题，并且每次都显示分享列表
                    startActivity(Intent.createChooser(shareIntent, "分享到"))
                }

                // 透传url接口：当AR Case中需要传出url时通过该接口传出url
                override fun openUrl(url: String) {
                    val intent = Intent()
                    intent.action = "android.intent.action.VIEW"
                    val contentUrl = Uri.parse(url)
                    intent.data = contentUrl
                    startActivity(intent)
                }

                // AR黑名单回调接口：当手机不支持AR时，通过该接口传入退化H5页面的url
                override fun nonsupport(url: String) {
                    val intent = Intent()
                    intent.action = "android.intent.action.VIEW"
                    val contentUrl = Uri.parse(url)
                    intent.data = contentUrl
                    startActivity(intent)
                    this@ARActivity.finish()
                }
            })
            //            mARFragment.setARCaptureResultCallback(new ARCaptureResultCallback() {
            //                @Override
            //                public void onPictureTaken(String filePath) {
            //                    Toast.makeText(ARActivity.this, "picture filepath=" + filePath, Toast.LENGTH_SHORT).show();
            //                }
            //
            //                @Override
            //                public void onVideoTaken(String filePath) {
            //                    Toast.makeText(ARActivity.this, "video filepath=" + filePath, Toast.LENGTH_SHORT).show();
            //                }
            //            });
            // 将trackArFragment设置到布局上
            fragmentTransaction.replace(R.id.bdar_id_fragment_container, mARFragment)
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    override fun onBackPressed() {
        var backFlag = false
        if (mARFragment != null) {
            backFlag = mARFragment!!.onFragmentBackPressed()
        }
        if (!backFlag) {
            super.onBackPressed()
        }
    }

}
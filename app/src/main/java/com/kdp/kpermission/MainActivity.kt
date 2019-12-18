package com.kdp.kpermission

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.kdp.permission.KPermission

class MainActivity : AppCompatActivity() {
    private lateinit var kPermission: KPermission
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        kPermission = KPermission(this)
    }

    fun requestAllPermissionClick(v: View){
        requestAllPermission()
    }
    fun requestEachPermissionClick(v: View){
        requestEachPermission()
    }

    /**
     * 请求单个或多个权限并返回每一个权限的请求结果
     */
    private fun requestEachPermission() {

        kPermission.requestEach(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
            .execute {
                    permission ->
                when {
                    permission.isGrant -> logger("你已经获取了${permission.name}权限")
                    permission.isShouldShowRequestPermission -> logger("你已经拒绝了${permission.name}权限")
                    else ->  logger("你已经禁止了${permission.name}权限，请手动在设置页面打开")
                }
            }
    }

    /**
     * 请求单个或多个权限并返回请求结果
     */
    private fun requestAllPermission(){

        kPermission.request(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .execute {
                    permission ->
                // name: 所有请求的权限
                // isGrant: 只要有一个权限被拒绝， 都返回false,否则返回true
                // isShouldShowRequestPermission: 只要有一个权限被拒绝且没有选择【不在询问】选项，都返回true,否则返回false

                when {
                    permission.isGrant -> logger("你已经获取了${permission.name}权限")
                    permission.isShouldShowRequestPermission -> {
                        if (!kPermission.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)){
                            logger("你已经拒绝了${Manifest.permission.READ_EXTERNAL_STORAGE}权限")
                        }

                        if (!kPermission.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                            logger("你已经拒绝了${Manifest.permission.WRITE_EXTERNAL_STORAGE}权限")
                        }
                    }
                    else -> {
                        if (!kPermission.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE) && !kPermission.shouldShowRequestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                            logger("你已经禁止了${Manifest.permission.READ_EXTERNAL_STORAGE}权限,请手动在设置页面打开")
                        }

                        if (!kPermission.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) && !kPermission.shouldShowRequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                            logger("你已经禁止了${Manifest.permission.WRITE_EXTERNAL_STORAGE}权限,请手动在设置页面打开")
                        }
                    }
                }
            }
    }


    private fun logger(msg:String){
        Log.d(MainActivity::class.java.simpleName,msg)
    }

}

package com.example.geonho.registerroadbook

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.view.View
import com.example.geonho.registerroadbook.DB.DBHandler_Anko

class SaveUserActivity : AppCompatActivity() {
    val mDBHandler = DBHandler_Anko(this)
    //외부 클래스로 요청한 작업에 대한 결과값 구분 변수
    val PICK_IMAGE:Int = 1010
    val REQ_PERMISSION = 1011
    var mSelectedImgId:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_user)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun onClickImage(view: View){
        //퍼미션 체크와 권한 요청
        val check = ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(check!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),REQ_PERMISSION)
        } else{ //권한이 허용되어 있는 경우의 처리
            startActivityForResult(Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI),PICK_IMAGE)
        }
    }

    //시스템 권한 요청 처리
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var notGranted = kotlin.arrayOfNulls<String>(permissions.size)
        when(requestCode){
            REQ_PERMISSION ->{
                var index:Int = 0
                for(i in 0 until permissions.size) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        val rationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])
                        if (!rationale) { //Show Dialog that explain to grant permission
                            val dialogBuild = AlertDialog.Builder(this).setTitle("권한 설정")
                                    .setMessage("이미지 썸네일을 만들기 위해서 저장권한이 필요합니다. 승인하지 않으면 이미지를 설정할 수 없습니다.")
                                    .setCancelable(true)
                                    .setPositiveButton("설정하러 가기") { _, _ ->
                                        showSetting()
                                    }
                            dialogBuild.create().show()
                        } else {
                            notGranted[index++] = permissions[i]
                        }
                    }
                }

                    if(notGranted.isNotEmpty()){
                        ActivityCompat.requestPermissions(this,notGranted,REQ_PERMISSION)
                    }
                }
             }
        }

    fun showSetting(){
        //사용자가 권한을 부여하도록 시스템 설정창으로 이동하기
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",packageName,null))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun onClickSaveBtn(view:View){

    }

}

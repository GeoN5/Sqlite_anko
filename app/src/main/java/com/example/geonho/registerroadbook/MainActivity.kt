package com.example.geonho.registerroadbook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.example.geonho.registerroadbook.DB.DBHandler_Anko

class MainActivity : AppCompatActivity() {

    private val FINSH_INTERVAL_TIME = 2000
    private var backPressedTime:Long = 0
    private var mAdapter:UserListAdapter? = null
    var mDBHandler:DBHandler_Anko = DBHandler_Anko(this)

    companion object {
        const val REQUEST_ADD_USER = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set toolbar
        val toolbar:Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //UserListAdapter 설정하기
        val newOne = mDBHandler.getUserAllWithCursor()
        if(newOne.count !=0){
            mAdapter = UserListAdapter(this,newOne)
            val listView = findViewById<ListView>(R.id.user_list)
            listView.adapter = mAdapter
        }
    }

    //뷰를 refresh해주기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            REQUEST_ADD_USER ->{
                val newOne = mDBHandler.getUserAllWithCursor()
                if(mAdapter==null){
                    mAdapter = UserListAdapter(applicationContext,newOne)
                    val listView = findViewById<ListView>(R.id.user_list)
                    listView.adapter = mAdapter
                }
                //새로운 커서로 변경하기
                mAdapter?.changeCursor(newOne)
                mAdapter?.notifyDataSetInvalidated()
            }
        }
    }

    //onClickDelete 함수
    fun onClickDelete(view: View){
        //Adapter에서 Delete Button View tag에 id 설정
        mDBHandler.deleteUser(view.tag as Long)
        //Adapter 재설정
        val newOne = mDBHandler.getUserAllWithCursor()
        mAdapter?.changeCursor(newOne)
    }

    override fun onDestroy() {
        super.onDestroy()
        //onDestory에서 커서 정리하기
        mAdapter?.cursor?.close()
        //onDestory에서 DB 정리하기
        mDBHandler.close()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_user ->{
                //Activity간 결과값을 받기 위해 startActivityForResult 사용하기
                startActivityForResult(Intent(this@MainActivity,SaveUserActivity::class.java), REQUEST_ADD_USER)
            }
            R.id.anko ->{
                startActivity(Intent(this,AnkoDSLActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime
        if (intervalTime in 0..FINSH_INTERVAL_TIME) {
            ActivityCompat.finishAffinity(this)
        } else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}

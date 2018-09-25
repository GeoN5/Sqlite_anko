package com.example.geonho.registerroadbook.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//데이터베이스에 저장될 구조체 설계
data class UserInfo(val name:String = "No Name",val age:String="0",val TelNum:String="No TelNum",val pic_path:String)


//enum index
enum class UserData(val index:Int){
    _id(0),Name(1),Age(2),TelNum(3),PicPath(4)
}

//SQLiteOpenHelper 상속 받아 사용하기
class DBHandler (context: Context): SQLiteOpenHelper(context,DB_Name,null,DB_Version) {

    companion object {
        val DB_Name = "user.db"
        val DB_Version = 1
    }

    val TABLE_NAME = "user"
    val ID = "_id"
    val NAME = "name"
    val AGE = "age"
    val TELNUM = "telnum"
    val PIC_PATH = "pic_path"

    //사용자 정보를 저장하는 테이블 쿼리문
    val TABLE_CREATE = "CREATE TABLE if not exits $TABLE_NAME ($ID integer PRIMARY KEY ,t, $NAME text, $AGE text, $TELNUM text, $PIC_PATH text )"

    //DB에 저장된 모든 정보를 가져오는 함수
    fun getUserAllWithCursor():Cursor{
        return readableDatabase.query(TABLE_NAME, arrayOf(ID,NAME,AGE,TELNUM,PIC_PATH), null,null,null,null,null)
    }

    //함수 인자로 받은 자료를 DB에 저장하는 함수
    fun addUser(user:UserInfo){
        var info = ContentValues()
        info.put(NAME,user.name)
        info.put(AGE,user.age)
        info.put(TELNUM,user.TelNum)
        info.put(PIC_PATH,user.pic_path)
        writableDatabase.insert(TABLE_NAME,null,info)
    }

    //자료 삭제
    fun deleteUser(id:Long){
        writableDatabase.execSQL("DELETE FROM $TABLE_NAME WHERE $ID = $id;")
    }

    //DB가 생성되면 호출되는 함수,이미 DB가 생성된 경우 onCreate 함수 호출 x
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}
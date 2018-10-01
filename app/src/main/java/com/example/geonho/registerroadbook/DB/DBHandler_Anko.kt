package com.example.geonho.registerroadbook.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.PRIMARY_KEY
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable

class DBHandler_Anko(context: Context) : SQLiteOpenHelper(context,DB_Name,null,DB_version){

    companion object {
        const val DB_Name = "user.db"
        const val DB_version = 1
    }

    object UserTable{
        const val TABLE_NAME = "user"
        const val ID = "_id"
        const val NAME = "name"
        const val AGE = "age"
        const val TELNUM = "telnum"
        const val PIC_PATH = "pic_path"
    }

    fun getUserAllWithCursor(): Cursor {
        return readableDatabase.query(UserTable.TABLE_NAME,
                arrayOf(UserTable.ID,UserTable.NAME,UserTable.AGE,UserTable.TELNUM,UserTable.PIC_PATH),
                null,null,null,null,null)
    }

    fun addUser(user:UserInfo){
        val info = ContentValues()
        info.put(UserTable.NAME,user.name)
        info.put(UserTable.AGE,user.age)
        info.put(UserTable.TELNUM,user.TelNum)
        info.put(UserTable.PIC_PATH,user.pic_path)

        //Anko use함수 사용하기 : DB 사용 후 알아서 DB를 닫아줌, 동시 다른 프로세스 접근 불가.
        writableDatabase.use{
            writableDatabase.insert(UserTable.TABLE_NAME,null,info)
        }
    }

    fun deleteUser(id:Long){
        writableDatabase.use{
            writableDatabase.execSQL("DELETE FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.ID} = $id;")
        }
    }

    //Anko의 Pair 클래스 사용하기 : 인자 2개를 받아서 String 으로 변경 String 쿼리문 대체.
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(UserTable.TABLE_NAME,true,
                Pair(UserTable.ID,INTEGER+PRIMARY_KEY),
                Pair(UserTable.NAME,TEXT),
                Pair(UserTable.AGE,TEXT),
                Pair(UserTable.TELNUM,TEXT),
                Pair(UserTable.PIC_PATH,TEXT))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}
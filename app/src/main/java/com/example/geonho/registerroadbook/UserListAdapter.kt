package com.example.geonho.registerroadbook

import android.content.Context
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.TextView

//ListView 구성 시 ViewHolder 패턴 사용하기
data class ViewHolder(val pic:ImageView,val name:TextView,val tel:TextView,val del:ImageView)

//CursorAdapter를 상속받기, 마지막 인자 : Adapter의 내용이 바뀌면 onContentChanged()호출,Adapter 사용 안할때는 커서를 Adapter에서 제거해줘야 함(메모리 누수).
class UserListAdapter(context: Context,cursor: Cursor?):CursorAdapter(context,cursor, FLAG_REGISTER_CONTENT_OBSERVER){

    val mCtx = context

    //Adapter에 뷰를 설정하기 위해서 호출되는 함수
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mainView = inflater.inflate(R.layout.layour_user_list,parent,false)
        var holder:ViewHolder = ViewHolder(mainView.findViewById(R.id.profile) as ImageView,)

    }

    override fun bindView(convertView: View, context: Context, cursor: Cursor) {

    }

    private fun getPicture(path:String): Drawable?{

    }

}
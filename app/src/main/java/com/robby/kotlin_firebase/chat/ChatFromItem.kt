package com.robby.kotlin_firebase.chat

import com.robby.kotlin_firebase.R
import com.robby.kotlin_firebase.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_from_row.view.*

class ChatFromItem(val text: String, val user: User): Item<GroupieViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textView_chat_from.text = text
        Picasso.get().load(user.profileImage).into(viewHolder.itemView.image_chat_from)
    }
}
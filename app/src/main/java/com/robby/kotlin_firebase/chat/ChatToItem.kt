package com.robby.kotlin_firebase.chat

import com.robby.kotlin_firebase.R
import com.robby.kotlin_firebase.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatToItem(val text: String, val user: User): Item<GroupieViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textView_chat_to.text = text
        Picasso.get().load(user.profileImage).into(viewHolder.itemView.image_chat_to)
    }
}
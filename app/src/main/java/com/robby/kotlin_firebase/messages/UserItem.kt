package com.robby.kotlin_firebase.messages

import com.robby.kotlin_firebase.R
import com.robby.kotlin_firebase.model.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class UserItem(val user: User): Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.urnm_userName.text = user.username
        Picasso.get().load(user.profileImage).into(viewHolder.itemView.urnm_image)
    }
}
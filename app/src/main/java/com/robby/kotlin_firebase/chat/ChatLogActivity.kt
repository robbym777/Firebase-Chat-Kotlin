package com.robby.kotlin_firebase.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.robby.kotlin_firebase.R
import com.robby.kotlin_firebase.messages.LatestMessagesActivity
import com.robby.kotlin_firebase.messages.NewMessageActivity
import com.robby.kotlin_firebase.model.User
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLogActivity : AppCompatActivity() {

    val adapter = GroupAdapter<GroupieViewHolder>()
    var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        recyclerview_chat_log.adapter = adapter
        user = intent.getParcelableExtra(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user?.username
        listenForMessages()
        send_chat_log.setOnClickListener {
            if(editText_chat_log.text.toString().isNotBlank()) {
                performSendMessage()
            }
        }
    }
    private fun listenForMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val toId = user?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/messages/$fromId/$toId")
        ref.addChildEventListener(object: ChildEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                if(chatMessage != null) {
                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val userFrom = LatestMessagesActivity.currentUser
                        adapter.add(ChatToItem(chatMessage.text, userFrom!!))
                    } else {
                        adapter.add(ChatFromItem(chatMessage.text, user!!))
                    }
                }
                recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
            }
        })
    }

    private fun performSendMessage() {
        val text = editText_chat_log.text.toString()
        val fromId = FirebaseAuth.getInstance().uid
        val toId = user?.uid
        val reference = FirebaseDatabase.getInstance().getReference("/messages/$fromId/$toId").push()
        val toReference = FirebaseDatabase.getInstance().getReference("/messages/$toId/$fromId").push()
        val chat = ChatMessage(reference.key!!, text, fromId!!, toId!!, System.currentTimeMillis() / 1000)
        toReference.setValue(chat).addOnSuccessListener {
            editText_chat_log.text.clear()
            recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
        }
        val latestMessage = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        latestMessage.setValue(chat)
        val latestToMessage = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
        latestToMessage.setValue(chat)
    }
}
package one.kotlin.choongyul.kotlintest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import kotlinx.android.synthetic.main.activity_chat.*
import android.R.attr.key
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ChildEventListener




class ChatActivity : AppCompatActivity() {

    val TAG : String = "ChatActivity"

    lateinit var myEmail : String
    lateinit var myName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        myEmail = intent.getStringExtra("email")
        myName = intent.getStringExtra("name")

        sendMessage()

    }

    fun sendMessage() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        val childUpdates = HashMap<String, String>()
        childUpdates.put("message", "asd")

//        myRef.setValue("Hello, World!")
        myRef.updateChildren(childUpdates as Map<String, String>)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                chatTitle.text = value!!
                Log.d(TAG, "Value is: " + value!!)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        myRef.child("message").addChildEventListener(object :ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, msg: String?) {
                val chatData = dataSnapshot.getValue(ChatData::class.java)  // chatData를 가져오고
                adapter.add(chatData.getUserName() + ": " + chatData.getMessage())  // adapter에 추가합니다.
            }

            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })




        myRef.child("message").addChildEventListener(object : ChildEventListener {  // message는 child의 이벤트를 수신합니다.
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val chatData = dataSnapshot.getValue<ChatData>(ChatData::class.java!!)  // chatData를 가져오고
                adapter.add(chatData!!.getUserName() + ": " + chatData!!.getMessage())  // adapter에 추가합니다.
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}

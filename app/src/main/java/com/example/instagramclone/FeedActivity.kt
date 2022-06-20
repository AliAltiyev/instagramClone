package com.example.instagramclone

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.adapter.PostAdapter
import com.example.instagramclone.databinding.ActivityFeedBinding
import com.example.instagramclone.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var postList: ArrayList<Post>
    private lateinit var postAdapter: PostAdapter
    private lateinit var fireStore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()

        postList = ArrayList<Post>()

        binding.recyclerViewInFeedActivity.layoutManager = LinearLayoutManager(this)

        postAdapter = PostAdapter(postList)

        binding.recyclerViewInFeedActivity.adapter = postAdapter

        getDate()
    }


    private fun getDate() {
        fireStore.collection("posts").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {

                    Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()


                } else {
                    if (value != null) {
                        if (!value.isEmpty) {

                            val doc = value.documents
                            postList.clear()
                            for (documents in doc) {

                                val comment = documents.get("commet") as String
                                val email = documents.get("email") as String
                                val uri = documents.get("uri") as String

                                val post = Post(comment = comment, email, uri)
                                postList.add(post)
                            }

                            postAdapter.notifyDataSetChanged()
                        }

                    }
                }
            }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.insta_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_new_post) {
            startActivity(Intent(this, UploudActivity::class.java))
        }
        if (item.itemId == R.id.sign_out) {

            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }

        return super.onOptionsItemSelected(item)

    }

}
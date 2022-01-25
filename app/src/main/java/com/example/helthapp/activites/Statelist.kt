package com.example.helthapp.activites

import androidx.appcompat.widget.SearchView
import android.app.VoiceInteractor
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.helthapp.R
import com.example.helthapp.data.Statelistadapter
import com.example.helthapp.model.State
import com.google.firebase.database.core.Tag
import kotlinx.android.synthetic.main.activity_statelist.*
import kotlinx.android.synthetic.main.activity_statelist.view.*
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*
import kotlin.collections.ArrayList

class Statelist : AppCompatActivity() {


    private lateinit var mRecyclerView: RecyclerView

    lateinit var statelist: ArrayList<State>
    private lateinit var tempArrayList: ArrayList<State>
    lateinit var stateadapter : Statelistadapter
    lateinit var volleyRequest : RequestQueue
    lateinit var layoutManager:RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statelist)

        statelist = ArrayList()
        tempArrayList = ArrayList<State>()
        stateadapter = Statelistadapter(statelist,this)
        volleyRequest = Volley.newRequestQueue(this)

        mRecyclerView = findViewById(R.id.RecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)


        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        getstatename(url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu)

        var item = menu?.findItem(R.id.search_sectionID)


        var searchview = item?.actionView as SearchView
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (newText.isNotEmpty()){
                    statelist.forEach {
                        if (it.loc.toLowerCase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }

                    mRecyclerView.adapter!!.notifyDataSetChanged()


                }else {
                    tempArrayList.clear()
                    tempArrayList.addAll(statelist)
                    mRecyclerView.adapter!!.notifyDataSetChanged()
                }
                return true

            }


        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var iteml = R.id.logoutmenue

        if (item.itemId == iteml){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    fun getstatename(URL: String){
        val stateRequest = JsonObjectRequest(Request.Method.GET,URL, {
                             response: JSONObject ->
             try {

                 val resultArray =response.getJSONObject("data")
                 var regional = resultArray.getJSONArray("regional")

                 for(i in 0..regional.length()){

                     var location = regional.getJSONObject(i).getString("loc")
                     var totalConfirmed = regional.getJSONObject(i).getInt("totalConfirmed")

                     var state = State(totalConfirmed,location)

                     statelist.add(state)
                     tempArrayList.addAll(statelist)

                     mRecyclerView.adapter = Statelistadapter(tempArrayList,this)
//                     mRecyclerView.adapter = Statelistadapter(tempArrayList)


                 }


                 stateadapter?.notifyDataSetChanged()

                 mRecyclerView.adapter = Statelistadapter(tempArrayList,this)



             }catch (e : JSONException){e.printStackTrace()}
        }, {
                error: VolleyError? ->
            try {
                Log.d("Error",error.toString())
            }catch (e:JSONException){e.printStackTrace()}
        })

        volleyRequest?.add(stateRequest)

    }


}



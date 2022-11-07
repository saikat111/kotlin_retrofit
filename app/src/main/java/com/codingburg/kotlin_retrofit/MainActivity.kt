package com.codingburg.kotlin_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com/"
class MainActivity : AppCompatActivity() {
    lateinit var rc : RecyclerView
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rc = findViewById(R.id.rc)
        rc.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        rc.layoutManager = linearLayoutManager
        getMyData()
    }

    private fun getMyData() {
        val  retrofitbulder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface:: class.java)
        val retrofitData = retrofitbulder.getData()
        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
                val reponseBody = response.body()!!

              /*  val myStruingBulder = StringBuffer()
                for(myData in reponseBody){
                    myStruingBulder.append(myData.id)
                    myStruingBulder.append("\n")
                }
                txt.text = myStruingBulder*/

                myAdapter = MyAdapter(baseContext, reponseBody)
                myAdapter.notifyDataSetChanged()
                rc.adapter = myAdapter


            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {

            }
        })
    }
}
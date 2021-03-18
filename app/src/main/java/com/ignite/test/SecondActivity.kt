package com.ignite.test

import adapter.BookAdapter
import adapter.CategoryAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager

import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ignite.test.Extra.CustomLoader
import com.ignite.test.Extra.EndPoints
import com.ignite.test.Extra.Utils.Companion.isConnectedToNetwork
import com.ignite.test.Model.BookModel

import kotlinx.android.synthetic.main.activity_second.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap


//array
lateinit var array: ArrayList<BookModel>

//loader
lateinit var loader: CustomLoader

class SecondActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        rlSearch.setOnClickListener(this)
        ivBack.setOnClickListener(this)

        //initialize
        array = ArrayList()

        recyclerBooks.setLayoutManager(GridLayoutManager(this, 3))

        searchView.queryHint = "Search"




        //loader

        loader = CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)


        //check internet connection
        if (this.isConnectedToNetwork()) {
            loader.show()

            val topic: String = intent.getStringExtra("topic").toString()


            //set header text
            tvCategory.text = topic

            //hit webservices
            API_GET_Books(topic, this)
        } else {
            Toast.makeText(this, "No Network Available", Toast.LENGTH_SHORT).show()
        }


    }

    //web services
    fun API_GET_Books(topic: String, ctx: Context) {

        //RequestQueue initialized
        var mRequestQueue = Volley.newRequestQueue(ctx)

        var mStringRequest = object : StringRequest(
            Request.Method.POST,
            EndPoints.URL_BOOK,
            Response.Listener { response ->

                val obj = JSONObject(response)
                var model: BookModel

                var jsonArray: JSONArray = obj.getJSONArray("results")
                for (i in 0 until jsonArray.length()) {
                    model = BookModel()
                    var jsonobject: JSONObject = jsonArray.getJSONObject(i)

                    model.title = jsonobject.getString("title")
                    var jArray: JSONArray = jsonobject.getJSONArray("authors")
                    for (j in 0 until jArray.length()) {
                        model.name = jArray.getJSONObject(j).getString("name")
                    }
                    var jsonObjec = jsonobject.getJSONObject("formats")

                    model.imageJpeg = jsonObjec.getString("image/jpeg")

                    if (jsonObjec.has("text/plain")) {

                        model.textPlain = jsonObjec.getString("text/plain")
                    }
                    if (jsonObjec.has("text/html; charset=utf-8")) {
                        model.textPlain = jsonObjec.getString("text/html; charset=utf-8")
                    }
                    if (jsonObjec.has("application/pdf")) {
                        model.textPlain = jsonObjec.getString("application/pdf")
                    }

                    array.add(model)
                    loader.dismiss()
                }

                //set adapter
                val adapter = BookAdapter(this, array)
                recyclerBooks.adapter = adapter
                loader.dismiss()
            },
            Response.ErrorListener { error ->
                Log.i("This is the error", "Error :" + error.toString())
            }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params2 = HashMap<String, String>()
                params2.put("topic", topic);
                return params2
            }

        }
        mRequestQueue!!.add(mStringRequest!!)
    }


    fun back()
    {

        startActivity(Intent(this, MainActivity::class.java))
    }


    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {
                R.id.ivBack ->back()
                else -> { // Note the block

                }
            }
        }
    }
}
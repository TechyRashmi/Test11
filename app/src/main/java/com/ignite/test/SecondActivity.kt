package com.ignite.test

import adapter.BookAdapter
import adapter.CategoryAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ignite.test.Extra.EndPoints
import com.ignite.test.Extra.Utils.Companion.isConnectedToNetwork
import com.ignite.test.Model.BookModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap

lateinit var array: ArrayList<BookModel>
class SecondActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        rlSearch.setOnClickListener(this)

        //initialize
        array= ArrayList()

        recyclerBooks.setLayoutManager(GridLayoutManager(this, 3))

        searchView.queryHint = "Search"

        //check internet connection
        if (this.isConnectedToNetwork()) {
           // loader.show()
               //hit webservices
            API_GET_Books("fiction",this)
        } else {
            Toast.makeText(this, "No Network Available", Toast.LENGTH_SHORT).show()
        }



    }
    //web services
    fun API_GET_Books(topic: String, ctx: Context) {

        //RequestQueue initialized
        var mRequestQueue = Volley.newRequestQueue(ctx)
        //String Request initialized
        var mStringRequest = object : StringRequest(
            Request.Method.POST,
            EndPoints.URL_BOOK,
            Response.Listener { response ->
              //  Log.e("ddddd",response)
                val obj = JSONObject(response)
                var model: BookModel

                var jsonArray: JSONArray = obj.getJSONArray("results")
                for (i in 0 until jsonArray.length()) {
                    model= BookModel()
                    var jsonobject: JSONObject = jsonArray.getJSONObject(i)

                    //Log.e("testss",""+jsonObjec)

                    model.title = jsonobject.getString("title")
                    var jArray: JSONArray = jsonobject.getJSONArray("authors")
                    for (j in 0 until jArray.length()) {
                        model.name = jArray.getJSONObject(j).getString("name")
                    }

                    var jsonObjec=jsonobject.getJSONObject("formats")


                    model.imageJpeg = jsonObjec.getString("image/jpeg")

                  if(jsonObjec.has("text/plain"))
                  {
                      model.textPlain = jsonObjec.getString("text/plain")
                  }
                    if(jsonObjec.has("text/html;charset=utf-8"))
                    {
                        model.textPlain = jsonObjec.getString("text/html; charset=utf-8")
                    }
                    if(jsonObjec.has("application/pdf"))
                    {
                        model.textPlain = jsonObjec.getString("application/pdf")
                    }
//
//                    model.textHtmlCharsetUtf8 = jsonObjec.getString("textHtmlCharsetUtf8")
//                    model.applicationPdf = jsonObjec.getString("applicationPdf")


                    array.add(model)


                }

                //set adapter
                val adapter = BookAdapter(this,array)
                recyclerBooks.adapter=adapter
               // loaderr.cancel()
            },
            Response.ErrorListener { error ->
                Log.i("This is the error", "Error :" + error.toString())
            })
        {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>  {
                val params2 = HashMap<String, String>()
                params2.put("topic", topic);
                return params2
            }

        }
        mRequestQueue!!.add(mStringRequest!!)
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.searchView -> rlSearch.setBackgroundResource(R.drawable.rectangle_blue)
                else -> { // Note the block
                    print("x is neither 1 nor 2")
                }
            }
        }
    }
}
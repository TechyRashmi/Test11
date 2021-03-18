package adapter


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ignite.test.Model.BookModel
import com.ignite.test.R
import kotlinx.android.synthetic.main.alert.*
import kotlinx.android.synthetic.main.book_items.view.*
import kotlinx.android.synthetic.main.category_items.view.*
import java.util.ArrayList


class BookAdapter : RecyclerView.Adapter<Holder> {

    lateinit var image: Array<Int>
    lateinit var array: ArrayList<BookModel>
    lateinit var ctx:Context

    companion object{
    }


    constructor(ctx: Context,array: ArrayList<BookModel>)
    {
        this.ctx=ctx
        this.array=array
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
                LayoutInflater.from(parent.context).inflate(R.layout.book_items, parent, false)
        )
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {

        with(holder.itemView)
        {
            tvBookAuthor.text=array.get(position).name
            tvBookTitle.text=array.get(position).title

            //set image
            Glide.with(holder.itemView)
                .load(array.get(position).imageJpeg)
                .centerCrop()
                .into(ivBookImage)


            holder.itemView.setOnClickListener{

                if(!array.get(position).applicationPdf.isNullOrBlank())
                {
                    open_file(array.get(position).applicationPdf.toString())
                }
                else if(!array.get(position).textHtmlCharsetUtf8.isNullOrBlank())
                {
                    open_file(array.get(position).textHtmlCharsetUtf8.toString())
                }
                else if(!array.get(position).textPlain.isNullOrBlank())
                {
                    open_file(array.get(position).textPlain.toString())
                }
                else
                {
                    showAlertDialog()
                }
            }
        }

    }
    override fun getItemCount(): Int {
        return array.size
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }


    fun showAlertDialog() {
        val dialog = Dialog(ctx)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert)
        dialog.tvAlerttext.text="No viewable version available"

        dialog.tvOk.setOnClickListener{
            dialog.cancel()
        }

        dialog.show()

    }

    fun open_file(url:String)
    {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        ctx.startActivity(browserIntent)
    }
}









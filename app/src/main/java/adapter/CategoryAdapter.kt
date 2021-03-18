package adapter


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import com.ignite.test.R
import com.ignite.test.SecondActivity
import kotlinx.android.synthetic.main.category_items.view.*


class CategoryAdapter : RecyclerView.Adapter<Holder> {

    lateinit var image: Array<Int>
    lateinit var array: Array<String>
    lateinit var ctx:Context

    companion object{
     }

    constructor(image: Array<Int>,ctx: Context, array: Array<String>) {
        this.image = image
        this.array = array
        this.ctx=ctx
    }

    constructor(ctx: Context)
    {
        this.ctx=ctx
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
                LayoutInflater.from(parent.context).inflate(R.layout.category_items, parent, false)
        )
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {

        with(holder.itemView)
        {
            ivCategory.setImageResource(image[position])
            btnCategory.setText(array[position])


            holder.itemView.setOnClickListener{
                val i = Intent(ctx, SecondActivity::class.java
                ).putExtra("topic",array[position])
                ctx.startActivity(i)
            }
        }


    }
    override fun getItemCount(): Int {
        return image.size
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
}


class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}






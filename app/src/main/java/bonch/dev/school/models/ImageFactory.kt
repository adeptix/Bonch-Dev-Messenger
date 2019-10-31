package bonch.dev.school.models

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.content.ContextCompat
import bonch.dev.school.R

class ImageFactory(context: Context) {

    private val imageList: List<Drawable>

    init {
        val userImage = ContextCompat.getDrawable(context, R.drawable.img_example)
        imageList = listOf(userImage!!)
    }


    fun initDrawables(context: Context) {

    }

    fun setRandomImage(tv: TextView) {
        tv.setCompoundDrawables(imageList[0], null, null, null)
    }
}
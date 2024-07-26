import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_shoptest.R
import com.example.e_shoptest.databinding.ViewholderBrandBinding
import com.example.e_shoptest.ui.model.BrandModel

class BrandAdapter(
    val items: MutableList<BrandModel>,
    private val onBrandSelected: (String?) -> Unit //Markanın seçildiğini bildirmek için bir lambda fonksiyonu ekledik
) : RecyclerView.Adapter<BrandAdapter.ViewHolder>() {
    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderBrandBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleofpic.text = item.title
        Glide.with(holder.itemView.context).load(item.picUrl).into(holder.binding.pic)

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = if (selectedPosition == position) -1 else position // Aynı markaya tıklanırsa seçim iptal ediliyor
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            onBrandSelected(if (selectedPosition != -1) item.title else null) // Marka seçildiğinde veya seçim iptal edildiğinde çağrılır
        }

        holder.binding.titleofpic.setTextColor(context.resources.getColor(R.color.white))
        if (selectedPosition == position) {
            holder.binding.pic.setBackgroundResource(0)
            holder.binding.viewholderBrandLayout.setBackgroundResource(R.drawable.purple_bg)
            ImageViewCompat.setImageTintList(holder.binding.pic, ColorStateList.valueOf(context.getColor(R.color.white)))
            holder.binding.titleofpic.visibility = View.VISIBLE
        } else {
            holder.binding.pic.setBackgroundResource(R.drawable.gray_bg)
            holder.binding.viewholderBrandLayout.setBackgroundResource(0)
            ImageViewCompat.setImageTintList(holder.binding.pic, ColorStateList.valueOf(context.getColor(R.color.black)))
            holder.binding.titleofpic.visibility = View.GONE
        }
    }

    inner class ViewHolder(val binding: ViewholderBrandBinding) : RecyclerView.ViewHolder(binding.root)
}

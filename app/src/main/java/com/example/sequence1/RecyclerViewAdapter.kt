package com.example.sequence1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(
    private val profilListeToDo: ProfilListeToDo,
    private val mContext: Context) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()
{

    // Retient les layouts des listes pour les imbriquer dans le recycler view
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val parentLayout: RelativeLayout
        init {

            textView = view.findViewById(R.id.textliste) //retient le texte
            parentLayout = view.findViewById(R.id.parent_layout) //retient le layout
        }

        fun bind(s: String) {
            textView.text = s

        }
    }
    // S'occupe d'inflate les views
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_liste, viewGroup, false)
        return ViewHolder(view)
    }
    //Passage vers ShowListActivity quand on clique sur un parent_layout de liste
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (holder) {
            is ViewHolder -> {
                holder.parentLayout.setOnClickListener {
                    val intentShow: Intent
                    intentShow = Intent(mContext, ShowListActivity::class.java)

                    //on transmet la position pour que les items appartiennent bien à la liste
                    intentShow.putExtra("position", position)
                    //on transmet le pseudo pour que les items appartiennent bien au pseudo de la liste
                    intentShow.putExtra("pseudo", profilListeToDo.getLogin())

                    mContext.startActivity(intentShow)
                }
                // Get element from the dataset at this position, replace the contents with that element
                holder.bind(profilListeToDo.getMesListeToDo()[position].getTitreListToDo())
            }

        }
    }

    override fun getItemCount(): Int {
        return profilListeToDo.getMesListeToDo().size
    }

}
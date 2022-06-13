package com.example.sequence1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ShowListActivity: AppCompatActivity(), View.OnClickListener {

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var okButton3: Button? = null
    private var champItem: EditText? = null

    private var listeDeProfilsJson : String? = null
    private val listeDeProfilsJsonType = object : TypeToken<MutableList<ProfilListeToDo>>() {}.type
    private var profilList : ProfilListeToDo? = null
    private var listDeProfilList : MutableList<ProfilListeToDo>? = null
    private var dataSet: ListeToDo? = null

    private var position: Int? = null
    private var pseudo: String? = null

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.`activity_show`)

        //insertion de la toolbar comme barre d'action de l'activité
        val toolbar: Toolbar =findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        toolbar.title="Show List Activity"

        okButton3 = findViewById(R.id.okbutton3)
        champItem = findViewById(R.id.champitem)

        okButton3!!.setOnClickListener(this)
        champItem!!.setOnClickListener(this)

        pref = PreferenceManager.getDefaultSharedPreferences(this)
        val smartCastSp = pref
        if (smartCastSp != null){
            editor = smartCastSp.edit()
        }

        val bdl = this.intent.extras

        pseudo = bdl?.getString("pseudo")
        position = bdl?.getInt("position")
        listeDeProfilsJson = pref!!.getString("profilList", "[]")
        profilList  = ProfilListeToDo()
        listDeProfilList = Gson().fromJson(listeDeProfilsJson, listeDeProfilsJsonType)
        dataSet = null

        var i = 0
        listDeProfilList?.forEach {
            if (it.getLogin() == pseudo){
                //On prend les liste de todolist de la personne connectée
                profilList = it
                i++
            }
        }
        if (i == 0){
            listDeProfilList?.add(ProfilListeToDo(pseudo!!))
            profilList = listDeProfilList?.last()
        }


        //On crée le dataset
        dataSet = profilList?.getMesListeToDo()?.get(position!!)

        val showlistRecyclerView: RecyclerView = findViewById(R.id.showlist_recyclerview)
        val adapter: ShowListRecyclerViewAdapter = ShowListRecyclerViewAdapter(dataSet!!, this)
        showlistRecyclerView.adapter = adapter
        showlistRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

    }

    override fun onStart() {
        super.onStart()


    }

    // Crée les actions de la toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }
    // Appelle SettingsActivity lors du clic de l'item 3points
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings->{
                val intentSettingsActivity : Intent = Intent(this, SettingsActivity::class.java)
                startActivity(intentSettingsActivity)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.okbutton3 -> {


                dataSet?.getLesItems()?.add(ItemToDo(champItem!!.text.toString(), false))

                profilList?.getMesListeToDo()?.set(position!!, dataSet!!)

                var indexAModifier = 0

                for ((index, value) in listDeProfilList!!.withIndex()){
                    if(value.getLogin() == pseudo){
                        indexAModifier = index
                    }
                }
                listDeProfilList?.set(indexAModifier, profilList!!)


                editor!!.putString("profilList", listDeProfilList.toString())
                editor!!.commit()

                val showlistRecyclerView: RecyclerView = findViewById(R.id.showlist_recyclerview)
                val adapter: ShowListRecyclerViewAdapter = ShowListRecyclerViewAdapter(dataSet!!, this)
                showlistRecyclerView.adapter = adapter
                showlistRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
            }

        }

    }


}

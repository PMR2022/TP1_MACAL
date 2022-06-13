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


class ChoixListActivity : AppCompatActivity(), View.OnClickListener {

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var okButton2: Button? = null
    private var champListe: EditText? = null


    private var listeDeProfilsJson : String? = null
    private val listeDeProfilsJsonType = object : TypeToken<MutableList<ProfilListeToDo>>() {}.type
    private var profilList : ProfilListeToDo? = null
    private var listDeProfilList : MutableList<ProfilListeToDo>? = null
    private var dataSet: MutableList<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix)

        //insertion de la toolbar comme barre d'action de l'activité
        val toolbar: Toolbar =findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        toolbar.title="Choix List Activity"

        val bundle = this.intent.extras
        val pseudo = bundle?.getString("pseudo") //on récupère le pseudo
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        val smartCastSp = pref
        if (smartCastSp != null){
            editor = smartCastSp.edit()
        }

        listeDeProfilsJson = pref!!.getString("profilList", "[]")
        profilList  = ProfilListeToDo()
        listDeProfilList = Gson().fromJson(listeDeProfilsJson, listeDeProfilsJsonType)
        dataSet = mutableListOf()

        
        val recyclerView: RecyclerView = findViewById(R.id.liste)
        val adapter: RecyclerViewAdapter = RecyclerViewAdapter(profilList!!, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)


        champListe = findViewById(R.id.champliste)
        okButton2 = findViewById(R.id.okbutton2)
        okButton2!!.setOnClickListener(this)

        var i = 0  // sert à compter combien de listes appartiennent au pseudo récupéré
        listDeProfilList?.forEach {
            if (it.getLogin() == pseudo){
                profilList = it
                i++
            }
        }
        /*
        //Si le pseudo n'a pas de liste, on en crée une
        if (i == 0){

            listDeProfilList?.add(ProfilListeToDo(pseudo!!))
            profilList = listDeProfilList?.last()
        }

         */

        profilList!!.getMesListeToDo().forEach{
            dataSet!!.add(it.getTitreListToDo())
        }
    }


    override fun onStart(){
        super.onStart()
        //Log.i(CAT, "onStart")
        val profilListJson = pref!!.getString("profilList", "")

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

    override fun onClick(v: View) {
        when (v.id){
            R.id.okbutton2 -> {
                val bdl = this.intent.extras
                val pseudo = bdl?.getString("pseudo")

                listeDeProfilsJson = pref!!.getString("profilList", "[]")

                profilList  = ProfilListeToDo()

                listDeProfilList = Gson().fromJson(listeDeProfilsJson, listeDeProfilsJsonType)
                dataSet = mutableListOf()


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
                profilList!!.getMesListeToDo().forEach{
                    dataSet!!.add(it.getTitreListToDo())
                }


                val addedToDo = ListeToDo(champListe!!.text.toString())
                profilList!!.ajouteListe(addedToDo)
                listDeProfilList?.add(profilList!!)
                dataSet!!.add(profilList!!.getMesListeToDo().last().getTitreListToDo())

                var indexAModifier = 0

                for ((index, value) in listDeProfilList!!.withIndex()){
                    if(value.getLogin() == pseudo){
                        indexAModifier = index
                    }
                }

                listDeProfilList?.set(indexAModifier, profilList!!)

                editor!!.putString("profilList", listDeProfilList.toString())
                editor!!.commit()




                val recyclerView: RecyclerView = findViewById(R.id.liste)
                val adapter: RecyclerViewAdapter = RecyclerViewAdapter(profilList!!, this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
            }
        }
    }


}
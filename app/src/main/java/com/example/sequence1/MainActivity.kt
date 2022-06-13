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

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var okButton: Button? = null
    private var champPseudo: EditText? = null
    private lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //insertion de la toolbar comme barre d'action de l'activité
        val toolbar: Toolbar =findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        toolbar.title="Main Activity"

        okButton = findViewById(R.id.okbutton)
        champPseudo = findViewById(R.id.champpseudo)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)


        val smartCastBtn = okButton
        if(smartCastBtn != null){
            smartCastBtn.setOnClickListener(this)
        }
    }

    //Lit la valeur pseudo de SettingsActivity
    //Ecrit cette valeur dans champpseudo
    override fun onStart() {
        super.onStart()
        @Suppress("DEPRECATION")
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        //Vérifier d'abord que l'utilisateur est d'accord
        if (prefs.getBoolean("remember", true)) {
            val pseudo = prefs.getString("pseudo","Emilien")
           champPseudo?.setText(pseudo)
        }
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

    override fun onClick(v: View){
        when (v.id){
            R.id.okbutton -> {
                //Passe la valeur du Pseudo renseigné à ChoixListActivity
                val bundle = Bundle()
                bundle.putString("pseudo", champPseudo!!.text.toString())
                //Passe à ChoixListActivity
                val intentChoix: Intent
                intentChoix = Intent(this@MainActivity, ChoixListActivity::class.java)
                intentChoix.putExtras(bundle)
                startActivity(intentChoix)
            }

        }
    }
}
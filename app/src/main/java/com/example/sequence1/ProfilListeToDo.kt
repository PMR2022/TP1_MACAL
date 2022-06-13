package com.example.sequence1

class ProfilListeToDo (
    private var login: String = "",
    private var mesListeToDo : MutableList<ListeToDo> = mutableListOf<ListeToDo>(ListeToDo(""))
)
{
    fun getMesListeToDo(): MutableList<ListeToDo> {
        return this.mesListeToDo
    }

    fun setMesListeToDO(mesListeToDo: MutableList<ListeToDo>){
        this.mesListeToDo = mesListeToDo
    }

    fun ajouteListe(uneListe: ListeToDo){
        this.mesListeToDo.add(uneListe)
    }

    fun getLogin(): String{
        return this.login
    }

    fun setLogin(unLogin: String){
        this.login = unLogin
    }

    override fun toString(): String {
        return "{\"login\": \"${login}\", \"mesListeToDo\": ${mesListeToDo}}"
    }

}
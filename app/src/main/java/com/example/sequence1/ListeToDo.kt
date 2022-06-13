package com.example.sequence1

class ListeToDo (
    private var titreListToDo: String ="Liste 1",
    private var lesItems: MutableList<ItemToDo> = mutableListOf<ItemToDo>(ItemToDo())
)
{

    fun setTitreListToDo(titre: String) {
        this.titreListToDo = titre
    }

    fun getTitreListToDo(): String {
        return this.titreListToDo
    }

    fun setLesItems(lesItems: MutableList<ItemToDo>) {
        this.lesItems = lesItems
    }

    fun getLesItems(): MutableList<ItemToDo> {
        return this.lesItems
    }

    fun rechercherItem(descriptionItem: String): String {
        //TODO
        return ""
    }

    override fun toString(): String {
        return "{\"titreListToDo\": \"${titreListToDo}\", \"lesItems\": ${lesItems}}"
    }

}
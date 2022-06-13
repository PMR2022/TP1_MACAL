package com.example.sequence1


class ItemToDo(
    private var description: String = "Item 1",
    private var fait: Boolean = false
)
{

    fun setDescription(description: String){
        this.description = description
    }

    fun getDescription(): String{
        return this.description
    }

    fun setFait(fait: Boolean){
        this.fait = fait
    }

    fun getFait(): Boolean{
        return this.fait
    }

    override fun toString(): String {
        return "{\"description\": \"${description}\", \"fait\": ${fait.toString()}}"
    }

}
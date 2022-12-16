open class Items(var name: String, var amount: Int) {

    var strength: Int = 0


    constructor(name: String, amount: Int, strength: Int): this(name, amount){
        this.name = name
        this.amount = amount
        this.strength = strength
    }

    //function below is being override'n
    open fun itemEffect(hero: Character){

    }

}

//Each Item-Instance and their respective class/effect----------------------
var potion = Potion_Heal("Potion", 3, 100)
var potionAP = Potion_AP("Ether-Potion", 2, 50)
var atkUP = BuffItem("Atk-up", 1)

//Inventory list
val inventory = mutableListOf(potion, potionAP, atkUP)


//Class for every potion that heals HP
class Potion_Heal(name: String, amount: Int, strength: Int): Items(name, amount, strength){

    override fun itemEffect(hero: Character){

        hero.GainHP(strength)
        amount--
        println("${hero.name} recovered $strength HP\n")

    }
}


//Class for every potion that restores AP
class Potion_AP(name: String, amount: Int, strength: Int): Items(name, amount,strength){

    override fun itemEffect(hero: Character) {

        hero.ap += strength
        amount--
        println("${hero.name} recovered $strength AP\n")
    }
}


//Item for buffing ATK
class BuffItem(name: String, amount: Int): Items(name, amount) {

    override fun itemEffect(hero: Character) {

        hero.atkBuff = true
        hero.atkBuffSTR = 1.1
        amount--
        println("${hero.name} attacks increased by 10%\n")
    }
}
//---------------------------------------------------------------------------
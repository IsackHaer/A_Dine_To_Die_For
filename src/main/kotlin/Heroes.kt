import java.lang.Exception

open class Heroes(
    name: String,
    hp: Int,
    maxHP: Int,
    ap: Int,
) : Character(name, hp, maxHP, ap) {

    //Chance to Crit
    fun Crit(atk: Int): Double {
        val chance = (1..5).random()        //decides the chance of a CRIT
        var damage: Double                      //damage is what the function RETURNS
        when (chance) {
            5 -> {
                println("IT'S A CRITICAL HIT")
                damage = 2.0 * atk
            }

            4 -> {
                println("It's a critical hit")
                damage = 1.2 * atk
            }

            else -> damage = atk.toDouble()
        }
        return damage
    }

    //AP calculator
    fun CostAP(cost: Int) {
        ap -= cost
    }


}


// IF Boss + Add is in play--------------------------------------
fun multipleChoice(opponentList: MutableList<Character>): Int {
    var opponentChoice: Int = 0
    var check: Boolean
    if (opponentList.size > 1) {
        do {
            //Prints out the current enemies as choice
            for (i in 0 until opponentList.size) {
                println("   [${i + 1}] ${opponentList[i].name}")
            }
            println("   [0] cancel")

            try {
                opponentChoice = readln().toInt()         //Choose your opponent
                opponentChoice -= 1
                check = true
                if (opponentChoice > opponentList.size - 1) {
                    println("Invalid input")
                    check = false
                } else if (opponentChoice < 0) {
                    opponentChoice = -1
                }
            } catch (e: Exception) {
                println("Invalid input \n")
                check = false
            }
        } while (!check)
    }
    return opponentChoice


}

// ITEM/INVENTORY handling section-------------------------------
fun UseItem(heroList: MutableList<Character>): Boolean {

    var itemChoice: Int = 0 //Used for later
    var heroChoice: Int = 0 //Used for later

    var endCheck = true     //endCheck is what THIS function RETURNS
    //In the carrot/broccoli,tomato classes a check is needed to proceed or break their fun Attack() loop
    //if false = returns player to ability selection, else if true = breaks the fun Attack() loop and the game continues

    do {
        var check = true                        // check is the condition for the do {} while loop

        //prints the item on the Console-----------------------
        for (i in 0 until inventory.size) {
            println("   [${i + 1}] ${inventory[i].name} x${inventory[i].amount}")
        }
        println("   [0] cancel")


        //First input STAGE : Item choice

        itemChoice = selectionPhase(inventory.size)  //selectionPhase function at the bottom
        if (itemChoice < 0) {
            break
        }

        //------------------------------------------------------


        //prints the Heroes to the console
        for (i in 0 until heroList.size) {
            println("       [${i + 1}] ${heroList[i].name}")
        }
        println("       [0] cancel")


        //Second input stage : Use Item on selected Hero
        heroChoice = selectionPhase(heroList.size)   //selectionPhase function at the bottom
        if (heroChoice < 0) {
            check = false
        }

        //-------------------------------------------------------

    } while (!check)



    //Once conditions are met, the Item gets used on the selected Hero
    if (itemChoice < 0) {
        endCheck = false
    } else {
        inventory[itemChoice].itemEffect(heroList[heroChoice])

        //removes the item from the inventory if the amount falls to 0
        if (inventory[itemChoice].amount == 0) {
            inventory.remove(inventory[itemChoice])
        }
    }

    return endCheck
}

//Select the item and hero: Returns INT = used for the list Index
fun selectionPhase(listsize: Int): Int {
    var check: Boolean      //check is the condition for the do {} while-loop
    var choice: Int         //choice is what this function returns
    do {
        try {
            choice = readln().toInt() - 1
            check = true
        } catch (e: Exception) {
            println("Invalid input \n")
            choice = 0
            check = false
        }

        if (choice < 0) {
            break
        } else if (choice > listsize - 1) {     //if choice is greater than the list.size = invalid input
            println("Invalid input \n")
            check = false
        }
    } while (!check)
    return choice
}
//---------------------------------------------------------------
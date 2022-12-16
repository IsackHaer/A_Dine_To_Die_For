import java.lang.Exception

class Broccoli(
    name: String,
    hp: Int,
    maxHP: Int,
    ap: Int,
) : Heroes(name, hp, maxHP, ap) {


    //Ability section --------------------------------------
    val atkName = listOf("Stem-slap","Heal-Bud","Leaves-Recovery")
    val atkDamage = listOf(120..140, 75..110, 50..80)
    val atkAP = listOf(" ","10","20", "0")
    // -----------------------------------------------------

    var opponentChoice: Int = 0                 // opponentChoice is used later to select which opponent to attack
    var critDamage: Double = 0.0              //critDamage is Used later to hold damage value

    override fun Attack(opponentList: MutableList<Character>, heroesList: MutableList<Character>) {

        do {
            println("${this.name}'s turn:") // Tells the player which hero's turn it is
            if (atkBuff) {
                println("^^atk-up^^") // Tells player if this Hero has atk-up buff or not
            }
            var check = true                 // check is for do {} while-loop condition


            // prints the abilities and their AP cost
            for (i in 0..2){
                println("[${i+1}] ${atkName[i].padEnd(25,' ')} AP:${atkAP[i]}")
            }
            println("[4] Item")


            var input = try {
                readln().toInt()                 //Choose your attack
            } catch (e: Exception) {
                check = false
            }

            when (input) {
                1 -> {  // Damage-process
                    check = singleTgtAtk(opponentList, atkName[0], atkAP[3],atkDamage[0])
                }

                2 -> {  // Single-Heal-process
                    check = singleHeal(heroesList,atkName[1],atkAP[1],atkDamage[1])
                }

                3 -> {  // AOE-Healing-process
                    check = aoeHeal(heroesList, atkName[2],atkAP[2],atkDamage[2])
                }

                4 -> {
                    check = UseItem(heroesList)              //Parameters = Heroes
                }

                else -> {
                    println("Invalid input\n")
                    check = false
                }
            }
        } while (!check)
    }

    fun singleTgtAtk(opponentList: MutableList<Character>, atkName: String, atkAP: String, atkDamage:IntRange): Boolean{
        var check = true
        do {

            //checks if Hero has enough AP to use this attack
            if (this.ap < atkAP.toInt()) {
                println("Not enough AP \n")
                check = false
            } else {

                opponentChoice = multipleChoice(opponentList)       //if multiple opponents are present

                if (opponentChoice < 0) {                           // Allows back button to be present
                    check = false
                    break

                }

                //Damage & AP check/calculation
                CostAP(atkAP.toInt())
                critDamage = Crit(atkDamage.random())
                if (this.atkBuff) {
                    critDamage *= atkBuffSTR
                }

                // Damage-Process endresult
                opponentList[opponentChoice].LoseHP(critDamage.toInt())
                println("$name used ${atkName}: for ${critDamage.toInt()} damage\n")

                if (opponentList[opponentChoice].hp <= 0) {
                    println("${opponentList[opponentChoice].name} is KO\n")
                    opponentList[opponentChoice].isKO = true
                } else {
                    println("${opponentList[opponentChoice].name} has ${opponentList[opponentChoice].hp} HP left \n")
                }
            }
        } while (ap > 1000)
        return check
    }

    fun singleHeal(heroesList: MutableList<Character>, atkName: String, atkAP: String, atkDamage: IntRange): Boolean{
        var check = true

        do {
            //checks if Hero has enough AP to use this action
            if (this.ap < atkAP.toInt()) {
                println("Not enough AP \n")
                check = false
            } else {

                opponentChoice = multipleChoice(heroesList)       //if multiple opponents are present

                if (opponentChoice < 0) {                           // Allows back button to be present
                    check = false
                    break
                }

                //heal & AP calculation
                CostAP(atkAP.toInt())
                critDamage = Crit(atkDamage.random())

                // Single-Heal-Process endresult
                heroesList[opponentChoice].GainHP(critDamage.toInt())
                println("$name used ${atkName}: ${heroesList[opponentChoice].name} recovered ${critDamage.toInt()} HP\n")

            }
        } while (ap > 1000)

        return check
    }

    fun aoeHeal(heroesList: MutableList<Character>, atkName: String, atkAP: String, atkDamage: IntRange): Boolean{
        var check = true

        //checks if Hero has enough AP to use this attack
        if (this.ap < atkAP.toInt()) {
            println("Not enough AP \n")
            check = false
        } else {

            //heal & AP calculation
            CostAP(atkAP.toInt())
            critDamage = Crit(atkDamage.random())

            // Healing-Process endresult
            for (i in heroesList){
                i.GainHP(critDamage.toInt())
            }
            println("$name used ${atkName}: and healed the party for ${critDamage.toInt()} HP\n ")
        }

        return check
    }
}



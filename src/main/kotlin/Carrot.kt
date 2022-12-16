import java.lang.Exception

class Carrot(
    name: String,
    hp: Int,
    maxHP: Int,
    ap: Int,
) : Heroes(name, hp, maxHP, ap) {


    //Ability section --------------------------------------
    val atkName = listOf("Surprise from beneath","Root Jump","Karrot Pierce")
    val atkDamage = listOf(80..100, 100..150, 150..200)
    val atkAP = listOf(" ","40","60","-20")
    // -----------------------------------------------------

    var opponentChoice: Int = 0                // opponentChoice is used later to select which opponent to attack
    var critDamage: Double = 0.0              //critDamage is Used later to hold damage value


    override fun Attack(opponentList: MutableList<Character>, heroesList: MutableList<Character>) {

        do {
            println("${this.name}'s turn:") // Tells the player which hero's turn it is
            if (atkBuff) {
                println("^^atk-up^^") // Tells player if this Hero has atk-up buff or not
            }

            var check = true                // check is for do {} while-loop condition


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

                2 -> {  // Damage-process
                    check = singleTgtAtk(opponentList, atkName[1],atkAP[1],atkDamage[1])
                }

                3 -> {  // Damage-process
                    check = singleTgtAtk(opponentList, atkName[2],atkAP[2],atkDamage[2])
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

                opponentChoice =
                    multipleChoice(opponentList)       //if multiple opponents are present, multipleChoice() can be found in class Heroes

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
}


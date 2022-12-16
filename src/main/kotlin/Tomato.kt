import java.lang.Exception

class Tomato(
    name: String,
    hp: Int,
    maxHP: Int,
    ap: Int,
) : Heroes(name, hp, maxHP, ap) {

    //Ability section --------------------------------------
    val atkName = listOf("Grow and Roll","Seed-gun","Redsplosion")
    val atkDamage = listOf(80..120, 30..40, 300..350)
    val atkAP = listOf(" ","20","90000", "0")
    // -----------------------------------------------------

    var opponentChoice: Int = 0                 // opponentChoice is used later to select which opponent to attack
    var critDamage: Double = 0.0              //critDamage is Used later to hold damage value

    override fun Attack(opponentList: MutableList<Character>, heroesList: MutableList<Character>) {

        do {
            println("${this.name}'s turn:") // Tells the player which hero's turn it is
            if (atkBuff) {
                println("^^atk-up^^") // Tells player if this Hero has atk-up buff or not
            }
            var check = true                // check is for do {} while-loop condition


            //prints the abilities to the console
            for (i in 0..1){
                println("[${i+1}] ${atkName[i].padEnd(25,' ')} AP : ${atkAP[i]}")
            }
            println("[3] ${atkName[2].padEnd(25,' ')} AP : 9001... IT'S OVER 9000!!")
            println("[4] Item")


            var input = try {
                readln().toInt()                 //Choose your attack
            } catch (e: Exception) {
                check = false
            }

            when (input) {
                1 -> {  // AOE-Damage-process
                    check = aoeAtk(opponentList,atkName[0], atkDamage[0])
                }

                2 -> {  // Multiple hit-Damage-process
                    check = multipleHitAtk(opponentList,atkName[1], atkAP[1], atkDamage[1])
                }

                3 -> {  // AOE-Damage-process
                    check = redSplosion(opponentList, atkName[2],atkDamage[2])
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

    fun multipleHitAtk(opponentList: MutableList<Character>, atkName: String, atkAP: String, atkDamage: IntRange): Boolean{
        var check = true

        do {
            //checks if Hero has enough AP to use this attack
            if (this.ap < atkAP.toInt()) {
                println("Not enough AP \n")
                check = false
            } else {
                val randomNumber = (2..10).random()  // Random number for the loop to repeat - this decides how many times the attack will repeat

                opponentChoice = multipleChoice(opponentList)       //if multiple opponents are present

                if (opponentChoice < 0) {                           // Allows back button to be present
                    check = false
                    break

                }

                //damage & ap calculations phase
                CostAP(atkAP.toInt())

                //Start of the multiple hit loop-------------------------------------------
                for (i in 1..randomNumber) {
                    critDamage = Crit(atkDamage.random())
                    if (this.atkBuff) {
                        critDamage *= atkBuffSTR
                    }

                    // Multiple hit-Damage-Process endresult
                    opponentList[opponentChoice].LoseHP(critDamage.toInt())
                    println("$name used $atkName: for ${critDamage.toInt()} damage")
                } //End of the loop---------------------------------------------
                println() //This one is to make it look pretty in the console

                if (opponentList[opponentChoice].hp <= 0) {
                    println("${opponentList[opponentChoice].name} is KO\n")
                    opponentList[opponentChoice].isKO = true
                } else {
                    println("${opponentList[opponentChoice].name} has ${opponentList[opponentChoice].hp} HP left \n")
                }
            }
        } while (ap > 10000)

        return check
    }

    fun redSplosion(opponentList: MutableList<Character>, atkName: String, atkDamage:IntRange): Boolean{
        var check = true

        critDamage = Crit(atkDamage.random())
        if (this.atkBuff) {
            critDamage *= atkBuffSTR
        }

        // AOE-Damage-Process endresult
        for (i in opponentList) {
            i.LoseHP(critDamage.toInt())
            this.isKO = true
        }
        println("$name used ${atkName}: for ${critDamage.toInt()} damage\n")
        println("$name is KO....\n")

        for (i in opponentList) {
            if (i.hp <= 0) {
                println("${i.name} is KO\n")
                i.isKO = true
            } else {
                println("${i.name} has ${i.hp} HP left \n")
            }
        }

        return check
    }

    fun aoeAtk(opponentList: MutableList<Character>, atkName: String, atkDamage:IntRange): Boolean{
        var check = true

        critDamage = Crit(atkDamage.random())
        if (this.atkBuff) {
            critDamage *= atkBuffSTR
        }

        // AOE-Damage-Process endresult
        for (i in opponentList) {
            i.LoseHP(critDamage.toInt())
        }
        println("$name used ${atkName}: for ${critDamage.toInt()} damage\n")

        for (i in opponentList) {
            if (i.hp <= 0) {
                println("${i.name} is KO\n")
                i.isKO = true
            } else {
                println("${i.name} has ${i.hp} HP left \n")
            }
        }

        return check
    }
}



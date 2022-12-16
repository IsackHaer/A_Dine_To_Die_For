class Boss(
    name: String,
    hp: Int,
    maxHP: Int
) : Character(name, hp, maxHP) {

    var enrageTimer = 1         //Counts the turns until enrage
    var enrageCheck = false     //Enrage check to break the BossAttack loop and focus on countdown

    //Boss Abilities section------------------------------------
    val atkName = mutableListOf("Big Bite", "Stamp", "High Jump", "Eye-Wink", "Summon-Sniffer")
    val atkDamage = listOf(175..180, 58..65, 64..70)
    val enrage = listOf("SHARPEN TEETH")
    //----------------------------------------------------------


    override fun BossAttack(opponentList: MutableList<Character>, bossAlly: Character) {

        do {

            var bossAtkDamage = 0       // to save the attack value in order to print it properly in the console

            if (enrageCheck)
                break

            println("${this.name}'s turn:\n")       //Tells player who's turn it is

            var randomAtk = atkName.random()        //Gets random atk from Boss
            var randomHero = opponentList.random()  //Gets random Hero

            when (randomAtk) {
                "Big Bite" -> {
                    bossAtkDamage = atkDamage[0].random()
                    randomHero.LoseHP(bossAtkDamage)
                    println("$name used Big Bite on ${randomHero.name}: took $bossAtkDamage damage\n")

                    if (randomHero.hp <= 0) {
                        randomHero.isKO = true
                        println("${randomHero.name} is KO\n")
                    }
                }

                "Stamp" -> {
                    bossAtkDamage = atkDamage[1].random()
                    for (i in opponentList) {
                        i.LoseHP(bossAtkDamage)
                        if (i.hp <= 0)
                            i.isKO = true
                    }
                    println("$name used Stamp: the entire party took $bossAtkDamage damage\n")
                    for (i in opponentList) {
                        if (i.isKO)
                            println("${i.name} is KO\n")
                    }
                }

                "High Jump" -> {
                    bossAtkDamage = atkDamage[2].random()
                    for (i in opponentList) {
                        i.LoseHP(bossAtkDamage)
                        if (i.hp <= 0)
                            i.isKO = true
                    }
                    println("$name used High Jump: the entire party took $bossAtkDamage damage\n")
                    for (i in opponentList) {
                        if (i.isKO)
                            println("${i.name} is KO\n")
                    }
                }

                "Eye-Wink" -> {
                    if (randomHero.hp > randomHero.maxHP * 0.4 && !randomHero.hasDOT) {
                        randomHero.hasDOT = true
                        println("$name used Eye-Wink on ${randomHero.name}: damage over time is applied\n")
                    } else if (randomHero.hasDOT || randomHero.hp < randomHero.maxHP * 0.4){
                        println("$name used Eye-Wink on ${randomHero.name}:.....It had no effect....")
                    }
                }

                "Summon-Sniffer" -> {
                    atkName.remove("Summon-Sniffer")
                    bossAlly.summon = true
                    println("$name summoned ${bossAlly.name} into the battle\n")

                }
            }
        } while (enrageCheck)


        //v-- Enrage in effect --v
        if (enrageTimer == 7){
            println("$name used $enrage COUNTER : 3!!\n")
            enrageCheck = true
        } else if (enrageTimer == 8){
            println("                               COUNTER : 2!!!\n")
        } else if (enrageTimer == 9) {
            println("                               COUNTER : 1!!!!!!!!!\n")
        } else if (enrageTimer == 10) {
            for (i in opponentList){
                i.isKO = true
            }
            println("$name used $enrage\n")
        }

        enrageTimer++

    }


}
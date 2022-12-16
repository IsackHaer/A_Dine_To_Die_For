class BossADD(
    name: String,
    hp: Int,
) : Character(name, hp)  {

    //Ability section-------------------------------------------
    val atkName = listOf("Sniff-IN","Sniff-OUT","Smoosh","Nose-Wiggle")
    val atkDamage = listOf(100..110, 50..60, 110..120, 500..600)
    //----------------------------------------------------------

    override fun BossAttack(opponentList: MutableList<Character>, bossAlly: Character) {

        var bossAtkDamage = 0       // to save the attack value and to be able to print it properly in the console

        var randomAtk = atkName.random()        //gets random atk
        var randomHero = opponentList.random()  //gets random hero

        // IF main boss hp is below 40%, heal the main boss instantly
        if (bossAlly.hp < bossAlly.maxHP * 0.4){
            bossAtkDamage = atkDamage[3].random()
            bossAlly.GainHP(bossAtkDamage)
            println("$name used Nose-Wiggle and healed ${bossAlly.name} for $bossAtkDamage HP\n")
        } else {
            when (randomAtk) {
                "Sniff-IN" -> {
                    bossAtkDamage = atkDamage[0].random()
                    randomHero.LoseHP(bossAtkDamage)
                    println("$name used Sniff-IN on ${randomHero.name}: took $bossAtkDamage damage\n")

                    if (randomHero.hp <= 0) {
                        randomHero.isKO = true
                        println("${randomHero.name} is KO\n")
                    }
                }

                "Sniff-OUT" -> {
                    bossAtkDamage = atkDamage[1].random()
                    for (i in opponentList) {
                        i.LoseHP(bossAtkDamage)
                        if (i.hp <= 0)
                            i.isKO = true
                    }
                    println("$name used Sniff-OUT: the entire party took $bossAtkDamage damage\n")
                    for (i in opponentList) {
                        if (i.isKO)
                            println("${i.name} is KO\n")
                    }
                }

                "Smoosh" -> {
                    bossAtkDamage = atkDamage[2].random()
                    randomHero.LoseHP(bossAtkDamage)
                    println("$name used Smoosh on ${randomHero.name}: took $bossAtkDamage damage\n")

                    if (randomHero.hp <= 0) {
                        randomHero.isKO = true
                        println("${randomHero.name} is KO\n")
                    }
                }

                "Nose-Wiggle" -> {                      //Is a heal
                    bossAtkDamage = atkDamage[3].random()
                    bossAlly.GainHP(bossAtkDamage)
                    println("$name used Nose-Wiggle and healed ${bossAlly.name} for $bossAtkDamage HP\n")
                }
            }
        }

    }

}
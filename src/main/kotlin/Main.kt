fun main() {
    //Heroes
    val carrot = Carrot("Carrot the Orangegoon", 250, 250, 100)
    val broccoli = Broccoli("Broccoli the Greener", 275, 275, 100)
    val tomato = Tomato("Tomato the Redsplosion", 240, 240, 100)

    //Bosses
    val rabbit = Boss("Rabbit the Ripper", 3000, 3000)
    val sniffer = BossADD("Rabbits-Fluffy-Sniffer", 1000)

    //Lists
    val heroesList: MutableList<Character> = mutableListOf(carrot,broccoli,tomato)
    val bossList: MutableList<Character> = mutableListOf(rabbit)


// Start of Game
    introduction()

    // Start of Fight--------------------------------------------------------

    var turnCounter = 1

    do {



        //prints the TURN counter... hehe..
        println("""
            |
            |
            |==============================================
            |||                  TURN $turnCounter                  ||
            |==============================================
            |
        """.trimMargin())

        //prints the current HP of the Bosses
        for (i in bossList){
            println("${i.name.padEnd(25,' ')} HP:${i.hp} ")
        }


        //Carrots turn:
        if (!carrot.isKO) {
            heroStatus(carrot, broccoli, tomato)
            carrot.Attack(bossList, heroesList)
        }
        koCheck(bossList, heroesList)

        if (bossList.isEmpty()) {           //After each Hero-turn its checked if any bosses are still alive.
            break
        }

        //Broccolis turn:
        if (!broccoli.isKO) {
            heroStatus(carrot, broccoli, tomato)
            broccoli.Attack(bossList, heroesList)
        }
        koCheck(bossList, heroesList)

        if (bossList.isEmpty()) {
            break
        }

        //Tomatos turn:
        if (!tomato.isKO) {
            heroStatus(carrot, broccoli, tomato)
            tomato.Attack(bossList, heroesList)
        }
        koCheck(bossList, heroesList)

        if (bossList.isEmpty()) {
            break
        }


        //Rabbits turn:
        if (!rabbit.isKO) {
            println("===================================BOSS=====================================\n")
            rabbit.BossAttack(heroesList, sniffer)
        }
        koCheck(bossList,heroesList)

        //Sniffers turn:
        if (bossList.contains(sniffer)){
            if (!sniffer.isKO){
                println("===================================BOSS=====================================\n")
                sniffer.BossAttack(heroesList,rabbit)
            }
        }
        koCheck(bossList,heroesList)

        //If sniffer gets summoned----------------
        if (sniffer.summon){
            bossList.add(sniffer)
            sniffer.summon = false
        }
        //----------------------------------------

        //DOT-damage is applied on heroes at the end of each turn
        for (i in heroesList){
            if (i.hasDOT && i.hp > i.maxHP * 0.4){
                i.LoseHP((30..50).random())
                println("${i.name} is in love and self-hurt for 30 damage")
            }
            //If conditions are met the DOT-damage will stop
            else if(i.hasDOT && i.hp <= i.maxHP * 0.4){
                i.hasDOT = false
                println("${i.name} is no longer in love" )
            }
        }

        koCheck(bossList, heroesList)   //Last KoCheck

        //Turn counter increase
        turnCounter++

        println("================================END OF TURN=================================\n")


        //Loss condition
        if (carrot.isKO && broccoli.isKO && tomato.isKO){
            break
        }


        //while Rabbit or Sniffer is still alive, repeat loop and start the next turn
    } while (!bossList.isEmpty())


    //Win/loss Menu
    if (bossList.isEmpty()) {
        println("""
            |  €€\    €€\ €€€€€€\  €€€€€€\ €€€€€€€€\  €€€€€€\  €€€€€€€\ €€\     €€\
            |  €€ |   €€ |\_€€  _|€€  __€€\\__€€  __|€€  __€€\ €€  __€€\\€€\   €€  |
            |  €€ |   €€ |  €€ |  €€ /  \__|  €€ |   €€ /  €€ |€€ |  €€ |\€€\ €€  /
            |  \€€\  €€  |  €€ |  €€ |        €€ |   €€ |  €€ |€€€€€€€  | \€€€€  /
            |   \€€\€€  /   €€ |  €€ |        €€ |   €€ |  €€ |€€  __€€<   \€€  /
            |    \€€€  /    €€ |  €€ |  €€\   €€ |   €€ |  €€ |€€ |  €€ |   €€ |
            |     \€  /   €€€€€€\ \€€€€€€  |  €€ |    €€€€€€  |€€ |  €€ |   €€ |
            |      \_/    \______| \______/   \__|    \______/ \__|  \__|   \__|
        """.trimMargin())
    } else {
        println("""
            |   /€€€€€€€  /€€€€€€€€ /€€€€€€€€ /€€€€€€€€  /€€€€€€  /€€€€€€€€
            |  | €€__  €€| €€_____/| €€_____/| €€_____/ /€€__  €€|__  €€__/
            |  | €€  \ €€| €€      | €€      | €€      | €€  \ €€   | €€
            |  | €€  | €€| €€€€€   | €€€€€   | €€€€€   | €€€€€€€€   | €€
            |  | €€  | €€| €€__/   | €€__/   | €€__/   | €€__  €€   | €€
            |  | €€  | €€| €€      | €€      | €€      | €€  | €€   | €€
            |  | €€€€€€€/| €€€€€€€€| €€      | €€€€€€€€| €€  | €€   | €€ /€€ /€€ /€€
            |  |_______/ |________/|__/      |________/|__/  |__/   |__/|__/|__/|__/
        """.trimMargin())
    }


    //End of fight & small surprise---------------------------------------------------
    println("""
            
            press "enter" to continue
        """.trimIndent())

    val lastInput = readln()

    end()

    //End of game----------------------------------------------------------------------

}

//checks each Hero and Boss, and if isKO = true { removes them from their List }
fun koCheck(opponentList: MutableList<Character>, heroList: MutableList<Character>){

    if (opponentList.size > 0) {
        if (opponentList.first().isKO) {
            opponentList.removeFirst()
        }
    }
    if (opponentList.size > 1) {
        if (opponentList.last().isKO) {
            opponentList.removeLast()
        }
    }

    if (heroList.size > 0) {
        if (heroList.first().isKO) {
            heroList.removeFirst()
        }
    }
    if (heroList.size > 2){
        if (heroList[1].isKO){
            heroList.removeAt(1)
        }
    }
    if (heroList.size > 1) {
        if (heroList.last().isKO) {
            heroList.removeLast()
        }
    }
}

//prints the current HP & AP of Heroes and makes sure that once KO the HP & AP stays at 0
fun heroStatus(hero1: Carrot, hero2: Broccoli, hero3: Tomato,){

    //sets the HP and AP to 0 if hero is KO
    val heroList = listOf(hero1,hero2,hero3)
    if (hero1.hp <= 0 || hero1.isKO) {
        hero1.hp = 0
        hero1.ap = 0
    } else if (hero1.hp >= hero1.maxHP){
        hero1.hp = hero1.maxHP
    }
    if (hero2.hp <= 0 || hero2.isKO) {
        hero2.hp = 0
        hero2.ap = 0
    } else if (hero2.hp >= hero2.maxHP){
        hero2.hp = hero2.maxHP
    }
    if (hero3.hp <= 0 || hero3.isKO) {
        hero3.hp = 0
        hero3.ap = 0
    } else if (hero3.hp >= hero3.maxHP){
        hero3.hp = hero3.maxHP

    }

    //prints the status of all heroes
    println("==============================================")
    for (hero in heroList){
        println("${hero.name.padEnd(25,' ')} " +
                "HP:${hero.hp}/${hero.maxHP.toString().padEnd(5,' ')} " +
                "AP:${hero.ap.toString().padEnd(5,' ')}")
    }
    println("==============================================")
}

//Menu and introductions-Story
fun introduction(){
    println("""
  /€€€€€€        /€€€€€€€  /€€€€€€ /€€   /€€ /€€€€€€€€       /€€€€€€€€ /€€€€€€        /€€€€€€€  /€€€€€€ /€€€€€€€€       /€€€€€€€€ /€€€€€€  /€€€€€€€
 /€€__  €€      | €€__  €€|_  €€_/| €€€ | €€| €€_____/      |__  €€__//€€__  €€      | €€__  €€|_  €€_/| €€_____/      | €€_____//€€__  €€| €€__  €€
| €€  \ €€      | €€  \ €€  | €€  | €€€€| €€| €€               | €€  | €€  \ €€      | €€  \ €€  | €€  | €€            | €€     | €€  \ €€| €€  \ €€
| €€€€€€€€      | €€  | €€  | €€  | €€ €€ €€| €€€€€            | €€  | €€  | €€      | €€  | €€  | €€  | €€€€€         | €€€€€  | €€  | €€| €€€€€€€/
| €€__  €€      | €€  | €€  | €€  | €€  €€€€| €€__/            | €€  | €€  | €€      | €€  | €€  | €€  | €€__/         | €€__/  | €€  | €€| €€__  €€
| €€  | €€      | €€  | €€  | €€  | €€\  €€€| €€               | €€  | €€  | €€      | €€  | €€  | €€  | €€            | €€     | €€  | €€| €€  \ €€
| €€  | €€      | €€€€€€€/ /€€€€€€| €€ \  €€| €€€€€€€€         | €€  |  €€€€€€/      | €€€€€€€/ /€€€€€€| €€€€€€€€      | €€     |  €€€€€€/| €€  | €€
|__/  |__/      |_______/ |______/|__/  \__/|________/         |__/   \______/       |_______/ |______/|________/      |__/      \______/ |__/  |__/

    """.trimIndent())
    println("Press \"enter\"\n")

    readln()

    val one = ("""
        |“The Realm of Hortensia” has always been a quiet idyllic realm where residents
        |from all over the world gathered and lived peacefully together.
        | 
        |press "enter" to continue the story
        |
        """.trimMargin())


    val two = ("""
        |Here onions can be found next to cabbage, radishes next to potatoes and cucumbers 
        |next to the admittedly exotic-looking chili peppers.
        |
        """.trimMargin())


    val three = ("""
        |Peace and quiet reigned all spring. 
        |
        """.trimMargin())


    val four = ("""Hortensia shone with new splendor and was in full bloom, 
        |ready to enjoy the first warm rays of the sun. 
        |
        """.trimMargin())


    val five = ("""
        |Just as the Strawberry-guards were about to relieve the Petal sentries,
        |...A loud knock shook the ground of the entire realm...
        |
          """.trimMargin())


    val six = ("""Terrified, the inhabitants of the kingdom stared up at the maker of the noise...
        |
    """.trimMargin())


    val seven = ("""He was called [The Ripper] and he was Hortensia's greatest terror.
        |
    """.trimMargin())


    val eight = ("""Countless times he had trampled the realm under his big, hairy feet, 
        |gnawed on leaves and even eaten the citizens of the realm!
        |
        """.trimMargin())


    val nine = ("""But Hortensia vowed, that this time they will not let him get away so easily...
        |
    """.trimMargin())


    val ten = (""" ...and so it came about that the 3 best warriors of the realm 
        |Carrot the Orangegoon 
        |Broccoli the Greener
        |Tomato the Redsplosion
        |were sent to stop [The Ripper] and scare this great evil away from Hortensia forever...
        | 
        | press "enter" to start the battle
        | 
         """.trimMargin())

    val story = listOf(one, two,three,four,five,six,seven,eight,nine,ten)

    for (sentance in story){
        sentance.forEach { c -> print(c); Thread.sleep(20) }
        readln()
    }
}

//Last bit of the story - ENDING
fun end(){
    println("...Rabbit the Rippers Mum enters the vegetable garden...\n")

    val one = ("Mum - \"Pookie! are you playing with your food again?\" \n")
    one.forEach { c -> print(c); Thread.sleep(20)  }
    val two = ("Pookie - \"Sorry Mum...\"\n")
    two.forEach { c -> print(c); Thread.sleep(20)  }
    val three = ("Mum - \"Eat up your vegetables and join your siblings with their activities.\"\n")
    three.forEach { c -> print(c); Thread.sleep(20)  }
    val four = ("Pookie - \"Ok Mum!\"\n")
    four.forEach { c -> print(c); Thread.sleep(20)  }
    val five = ("""
        
        
        THE END
    """.trimIndent())
    five.forEach { c -> print(c); Thread.sleep(20)  }
}







// Move UseItem() to Heroes class

// Exchange += hp in UseItem to GainHP()

// Implement AP usage

// Create Broccoli class ---v
// ^---and rework abilities + balancing
// name Bud-sprinkle is not understood as an aoe-heal ability = change the name
// make the healing abilities heal the heroes

// Create Tomato class ---v
// ^---and rework abilities + balancing

// Start programming the Boss and miniBoss - Create new class

// Create the gameplay

// maybe try to optimize code to get rid of duplicates/copy-paste code (if laziness doesn't kick in)

// Playtest, find bugs and Balance abilities

// Add Intro story and introduce the player to the games Story

// Add "End-credits" / End-Story once the game is over: Winn/Loss



                                                                                                                                                    
                                                                                                                                                    
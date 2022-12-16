open class Character(
    var name: String,
    var hp: Int,

) {

    var hasDOT: Boolean = false
    var summon: Boolean = false

    var isKO: Boolean = false
    var atkBuff: Boolean = false
    var atkBuffSTR = 0.0

    var maxHP = 0
    var ap = 0

    //Secondary constructor for adding maxHP and AP to the heroes
    constructor(name: String, hp: Int, maxHP: Int, ap: Int): this(name, hp){
        this.ap = ap
        this.maxHP = maxHP
    }

    //Secondary(third) constructor for adding maxHP to the Boss
    constructor(name: String, hp: Int, maxHP: Int): this(name, hp){
        this.maxHP = maxHP
    }


    fun LoseHP(loss: Int) {
        hp -= loss
    }

    fun GainHP(gain: Int) {
        hp += gain
    }

    open fun Attack(opponentList: MutableList<Character>, heroesList: MutableList<Character>) {

    }

    open fun BossAttack(opponentList: MutableList<Character>, bossAlly: Character) {

    }




}
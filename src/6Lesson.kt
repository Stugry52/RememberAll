import kotlin.random.Random

// Базовый Класс - его важно сделать open чтобы его смогли наследовать
open class Character(
    val name: String,
    maxHealth: Int,
    baseAttack: Int,
    level: Int
){
    // protected - доступен дял использования в классе и наследниках этого класса
    protected var _health = maxHealth.coerceAtLeast(1)
    protected var _maxHealth = maxHealth.coerceAtLeast(1)
    protected var _attack = baseAttack.coerceAtLeast(1)
    protected var _level = level.coerceAtLeast(1)

    // open - разрешаем переопределения метода в наследниках класса
    open val health: Int
        get() = _health

    open val maxHealth: Int
        get() = _maxHealth

    open val attack: Int
        get() = _attack

    open val level: Int
        get() = _level

    open val isAlive: Boolean
        get() = _health > 0

    init {
        println("Создан персонаж: $name")
    }

    open fun takeDamage(damage: Int){
        if (!isAlive) return

        val actualDamage = damage.coerceAtLeast(0)
        _health -= actualDamage
        println("$name получает $actualDamage урона! Осталось: ${_health}")

        if (_health <= 0){
            println("$name busted")
        }
    }

    open fun heal(amount: Int){
        if (!isAlive) return

        val healAmount = amount.coerceAtLeast(0)
        _health = (_health + healAmount).coerceAtLeast(_maxHealth)
        println("$name похилился на $healAmount")
    }

    open fun attack(target: Character){
        if (!isAlive || !target.isAlive) return

        val damage = calculateDamage(_attack)
        println("$name атакует ${target.name}!")
        target.takeDamage(damage)
        levelUp()
    }

    open fun printStatus(){
        val status = if (isAlive) "Жив" else "Нежив"
        println("$name: $_health/$_maxHealth HP, ATK: $_attack ($status)")
    }

    open fun levelUp(){
        if(!isAlive) return

        if(attack > 1){
            _level += 1
            _maxHealth +=2
            _attack += 1
        }
    }
}

// класс наследник Warrior (Дочерний класс)
// : Character(name, maxHealth, baseAttack) - наследование и вызо конструктора родителя
class Warrior(name: String, maxHealth: Int, baseAttack: Int, level: Int) : Character(name, maxHealth, baseAttack, level){

    // Дополнительное свойство, спецефичное для Класса Воина
    var armor: Int = 5

    // Переопределение метода получения урона с учетом брони
    override fun takeDamage(damage: Int) {
        if (!isAlive) return

        val reducedDamage = (damage - armor).coerceAtLeast(0)
        println("Броня $name поглощает $armor урона")
        // super - вызов метода родительского класса
        super.takeDamage(reducedDamage)

    }

    // Уникальный способность воина (Уникальный метод)
    fun powerfullStrike(target: Character){
        if (!isAlive) return

        val cost = 10
        if (_health > cost){
            _health -= cost // Тратим здоровье для усиленой атаки
            val damage = calculateDamage(_attack * 2) // Удвоенный урон
            println("$name использует ульту")
            target.takeDamage(damage)
        }else{
            println("у $name недостаточно НР дял мощной атаки")
            attack(target)
        }
        levelUp()
    }
}

class Mage(name: String, maxHealth: Int, baseAttack: Int, level: Int) : Character(name, maxHealth, baseAttack, level){

    var mana: Int = 100
    val maxMana: Int = 100

    override fun attack(target: Character) {
        if (!isAlive || !target.isAlive) return

        if(mana >= 10){
            mana -= 10
            val damage = calculateDamage(_attack + 5) // Бонус к базовой
            println("$name атакует магическим посохом и тратит 10 маны")
            target.takeDamage(damage)
            println("Осталось маны: $mana/$maxMana")
        }else{
            // Обычная атака, если маны
            println("У $name недостаточно маны")
            super.attack(target) // Вызов базовой (не измененной) реализации в атаки
        }
        levelUp()
    }

    fun caltFireball(target: Character){
        if (!isAlive) return

        val cost = 30
        if (mana >= 30){
            mana -= cost
            val damage = calculateDamage(_attack * 3) // Тройной урона Fireballs
            println("$name кастуем fireball и тратит $cost маны")
            target.takeDamage(damage)
        }else{
            attack(target)
        }
        levelUp()
    }

    override fun printStatus() {
        val status = if (isAlive) "Живчик" else "Нежить"
        println("$name: $_health/$_maxHealth HP, Mana: $mana/$maxMana, ATK: $_attack ($status)")
    }
}

class Archer(name: String, maxHealth: Int, baseAttack: Int, level: Int) : Character(name, maxHealth, baseAttack, level){
    var agility = 30
    val maxAgility = 30

    override fun attack(target: Character) {
        if (!isAlive) return

        val chance = Random.nextInt(1, 100)
        if(chance <= 30){
            agility -= 5
            val krit =(_attack * 30) / 100
            val damage = calculateDamage(_attack + krit)
            println("$name уклонился от урона и наносит усилиный урон на $krit, нанесен $damage урона")
            target.takeDamage(damage)
            println("Осталось сил: $agility/$maxAgility")
        }else{
            val damage = calculateDamage(_attack)
            println("Был нанесен обычный урон $damage")
            target.takeDamage(damage)
            super.attack(target)
        }
        levelUp()
    }
}

fun main(){
    println("=== Бой с системой классов персонажей ===")

    val warrior = Warrior("Варио", 120, 16, 1)
    val mage = Mage("Извинись", 80, 10, 1)
    val enemy = Warrior("ОРОРок", 100, 14, 5)
    val archer = Archer("Арчер", 70,18, 1)

    println(">>> Начало боя <<<")
    warrior.powerfullStrike(enemy)
    mage.caltFireball(enemy)
    enemy.attack(warrior)
    archer.attack(enemy)

    println(" --- Статусы персонажей  ---")
    warrior.printStatus()
    mage.printStatus()
    enemy.printStatus()
    archer.printStatus()

    warrior.takeDamage(20)
    mage.attack(enemy)
}
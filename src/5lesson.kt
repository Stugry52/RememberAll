import kotlin.random.Random

// Улучшенный клас Character - с конструктором
// (...) Параметры в скобках - параметры главного конструктора
class Character (
    // val - параметр автоматически становится свойствами класса
    val name: String,
    // Без val это просто параметр конструктора, это не свойство класса
    maxHealth: Int,
    baseAttack: Int
){
    // Инкапсуляция - он делает внутринние свойства приватными (private)
    // private - модификатор доступа. Означает что свойство доступно для использования только внутри класса
    private  var _health = maxHealth.coerceAtLeast(1) // coerceAtLeast(1) - гарантирует минимальное здоровье = 1

    // Публичное своство health только для чтения (val)
    // Другие классы могут узнать количество здоровье, но не могут изменить
    val health: Int
        get() = _health // get() - геттер, возвращает только значение приватного _health

    private  val _maxHealth = maxHealth.coerceAtLeast(1)
    val maxHealth: Int
        get() = _maxHealth

    private val _attack = baseAttack.coerceAtLeast(1)
    val attack: Int
        get() = _attack

    // Вычисляймое своство - свойство которое не хранится, а вычисляется при каждом обращении
    val isAlive: Boolean
        get() = _health > 0

    // Блок init выполняется при создании объекта (при инициализации свойство)
    init {
        println("Создан новый персонаж: $name (HP: $_health/$_maxHealth, ATK: $_attack)")
    }

    // Методы с проверками (инкапсуляция в деле)
    fun takeDamage(damage: Int){
        if (!isAlive){
            println("$name уже мертв и не может получить дамаге")
            return
        }

        val actualDamage = damage.coerceAtLeast(0) // Урон не может быть отрицательным
        _health -= actualDamage
        println("$name получает $actualDamage дамага! Осталось здоровья: $_health")

        if (_health <= 0){
            println("$name пал в бою с бубсами")
        }
    }

    fun heal(amount: Int){
        if (!isAlive){
            println("$name уже мертв и не может получить дамаге")
            return
        }

        val healAmount = amount.coerceAtLeast(0)

        _health = (_health + healAmount).coerceAtMost(_maxHealth)
        println("$name восстанавливает себе $healAmount здоровье, теперь у него: $_health/$_maxHealth HP")
    }

    fun attack(target: Character){
        if (!isAlive){
            println("$name уже мертв и не может получить дамаге")
            return
        }

        if (!target.isAlive){
            println("${target.name} уже мертв, что дальше?")
            return
        }

        val damage = calculateDamage(_attack)
        println("$name атакует ${target.name}")
        target.takeDamage(damage)
    }

    fun printStatus(){
        val status = if (isAlive) "Жив" else "Нежив"
        println("$name: $_health/$_maxHealth HP, ATK: $_attack ($status)")
    }


}

fun main(){
    println("==== Улучшенная система создания наших персонажей ====")
    val player = Character("Oleg", 80, 5 )
    val monster = Character ("Игорь", 40, 10)
    player.attack(monster)
    monster.attack(player)

    println("--- Состоние после раунда 1---")
    player.printStatus()
    monster.printStatus()

    // Как делать нельзя (расстрел)
    //player.health = 100 //  Ошибка!! health - val (только для чтения)
    //player._health = 100 // Ошибка!! _health - private
    //Инкапсуляция - защищает нас, чтобы не сделать что-то неправильно в программе

    val healer = Character("Жрец", 60, 3)
    val warrior = Character("Щитовидный железный", 95, 8)

    warrior.attack(monster)
    healer.heal(warrior.health)
}


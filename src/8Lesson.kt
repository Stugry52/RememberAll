import kotlin.random.Random

class Item(val name: String, val description: String, val useEffect: (Player) -> Unit){
    fun use(player: Player){
        println("Используется: $name")
        useEffect(player)
    }
}
// Класс Локации
class Location(val name: String, val description: String){
    val enemies = mutableListOf<Character>()
    val items = mutableListOf<Item>()

    fun addEnemy(enemy: Character){
        enemies.add(enemy)
    }

    fun addItem(item: Item){
        items.add(item)
    }

    fun describe(){
        println("=== $name ===")
        println(description)

        if (enemies.isNotEmpty()){
            println("Враги присутсвуют в этой локации")
            // forEachIndexed - перебирает список(массив), предоставляя индексы и элементы под этими индексами
            enemies.forEachIndexed { index, enemy ->
                println("${index + 1}. ${enemy.name} (HP: ${enemy.health})")
            }
        }

        if (items.isNotEmpty()){
            println("Предметы которые можно найти в этой локации")
            items.forEachIndexed { index, item ->
                println("${index + 1}. ${item.name} - ${item.description}")
            }
        }
    }
}

// Класс игрового мира
class GameWorld {
    private val locations = mutableListOf<Location>()
    private var currentLocationIndex = 0
    val gameInput = GameInput()
    private val inventure = mutableListOf<Item>()
//    private var currentInventureIndex = 0

//    val currentInventure: Item
//        get() = inventure[currentInventureIndex]

    // Вычисляемое свойство для текущей локации
    val currentLocation: Location
        get() = locations[currentLocationIndex]

    fun createWorld() {
        val forest = Location("Темный лес", "Густой темный лес, здесь сташно, не ходи сюда")
        val cave = Location("Пещера темная", "Темная пещера, не такая страшная как лес, но я бы сюда не совался")
        val village = Location("Деревня", "Мирная зона, здесь кайфово, пока не придет голем го-го-го")

        // Создаем врагов
        val nikita = Character("Никита", 40, 8)
        val wolf = Character("Волк", 30, 10)
        val wildGolem = Character("Дикий кукуруз", 200, 15)

        // Создаем предметы локации
        val healPotion = Item("Зелье здоровье", "Востанавливает от -1 до 1 HP", { player ->
            println("${player.name} восстанавливает ... HP")
        })
        val attackPotion = Item("Зелье силы", "Увеличивает атаку на 5", { player ->
            println("${player.name} усиливает себя на 5")
        })

        // Распределение объектов (врагов и придметов) по локации
        forest.addEnemy(wolf)
        forest.addEnemy(nikita)
        forest.addItem(healPotion)

        cave.addEnemy(wildGolem)
        cave.addEnemy(nikita)
        cave.addItem(attackPotion)

        village.addItem(healPotion)

        //Добавим локации в наш мир
        locations.add(forest)
        locations.add(cave)
        locations.add(village)
    }


    fun StartGame(player: Player) {
        println("Добро пожаловать в игру! ${player.name}")

        var gameRunning = true

        while (gameRunning && player.isAlive) {
            // Отображение текущую локацию
            currentLocation.describe()

            // Показываем доступные действия игрока
            println("\n>>> ДОСТУПНЫЕ ДЕЙСТВИЯ<<<")
            println("1. Осмотреться")
            println("2. Атаковать врага")
            println("3. Взять предмет")
            println("4. Перейти на следущую локацию")
            println("5. Использовать зелье")
            println("6. Посмотреть инвентарь")
            println("7. Выйти из игры")

            val choice = gameInput.getNumberInput("Выберете действие", 1, 6)
            when(choice){
                1 -> currentLocation.describe()
                2 -> combatMenu(player)
                3 -> takeItemMenu(player)
                4 -> moveToNextLocation()
                5 -> player.usePotions()
                6 -> checkInventure()
                7 -> gameRunning = false
            }

            if (!player.isAlive){
                println("Игра окончена ${player.name} пал в бою, GG gl hf")
            }
        }
    }

    private fun combatMenu(player: Player){
        if (currentLocation.enemies.isEmpty()){
            println("Здесь нет врагов чтобы с ними файтиться, остуди пыл")
            return
        }

        println("\n Выберите цель для атаки: ")
        currentLocation.enemies.forEachIndexed { index, enemy ->
            println("${index + 1}. ${enemy.name} (HP: ${enemy.health})")
        }
        println("${currentLocation.enemies.size + 1}. Отмена")

        val choice = gameInput.getNumberInput("Ваш выбор: ", 1, currentLocation.enemies.size + 1)
        if(choice <= currentLocation.enemies.size){
            val target = currentLocation.enemies[choice - 1]
            player.attack(target)

            // Удаление врага если он умер
            if (!target.isAlive){
                currentLocation.enemies.remove(target)
                println("${target.name} повержен и удален из локации!")
            }
            // Атака врага в ответ (провокация)
            currentLocation.enemies.forEach { enemy ->
                if (enemy.isAlive){
                    enemy.attack(player)
                }
            }
        }
    }

    private fun takeItemMenu(player: Player){
        if (currentLocation.items.isEmpty()){
            println("Здесь нет предметов для лутинга")
            return
        }

        println("\n Выберите предмет для лутинга: ")

        currentLocation.items.forEachIndexed { index, item ->
            println("${index + 1}. ${item.name}")
        }

        val choice = gameInput.getNumberInput("Ваш выбор: ", 1, currentLocation.items.size)
        val item = currentLocation.items[choice - 1]

        println("Вы взяли: ${item.name}")
        // Здесь в реальной игре надо положить предмет в инвентарь игрока

        //item.use(player)
        //currentLocation.items.remove(item)
    }

    private  fun  moveToNextLocation(){
        if (currentLocationIndex < locations.size - 1){
            currentLocationIndex++
            println("Вы переместились на локацию: ${currentLocation.name}")

            if (currentLocation.enemies.isNotEmpty()){
                println("Вы слышите что-то жуткое")
            }
        }else{
            println("Это последняя локация, дальше пути нет!")
        }
    }

    private fun checkInventure(){
        if (inventure.isEmpty()){
            println("У вас нет предметов")
            return
        }
        println("\n Выберите предмет: ")

        inventure.forEachIndexed { index, item ->
            println("${index + 1}. ${item.name}")
        }
    }
}

fun main(){
    println("=== Создание игрового мира ===")

    val gameWorld = GameWorld()
    gameWorld.createWorld()

    val player = Player("Иннокентий", 100, 15)
    gameWorld.StartGame(player)
}







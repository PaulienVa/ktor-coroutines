package nl.openvalue.paulienvanalst.kotlin.ktor.coroutines

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.recipeEndpoint() {
    route("api/") {
        get("recipes/") {
            val recipes = listOf(pancakes, scrambledEggs, fondue)
            call.respond(recipes)
        }
    }
}

private val pancakes = Recipe(
    "pancakes",
    20,
    listOf("2 eggs", "250gr flower", "500mL milk", "bit of salt"),
    "Mix everything and bake pancakes"
)
private val scrambledEggs = Recipe(
    "scrambled eggs",
    10,
    listOf("4 eggs", "2 tbsp milk", "bit of salt"),
    "Mix everything and bake and fold the eggs over"
)
private val fondue = Recipe(
    "fondue",
    45,
    listOf("cheese", "wine", "kirsch", "garlic"),
    "Cook the wine, add the cheese, add the kirsch and garlic"
)

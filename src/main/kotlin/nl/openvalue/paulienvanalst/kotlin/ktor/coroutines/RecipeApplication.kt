package nl.openvalue.paulienvanalst.kotlin.ktor.coroutines

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {

    Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

    embeddedServer(
        Netty,
        port = 8080,
        module = Application::recipes
    ).start(wait = true)
}


fun Application.recipes() {
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    install(Routing) {
        recipeEndpoint()
    }

    initDatabase()
}


fun initDatabase() {
    transaction {
        SchemaUtils.createMissingTablesAndColumns(RecipeRow)
    }


    transaction {
        RecipeRow.insert {
            it[name] = "pancakes"
            it[duration] = 10
        }
    }
}




class RecipeRepository {
    suspend fun getRecipes(): List<Recipe> = suspendedTransactionAsync {
        RecipeRow.selectAll().map { toRecipe(it) }
    }.await()

    private fun toRecipe(row: ResultRow): Recipe {
        return Recipe(row[RecipeRow.name], row[RecipeRow.duration], listOf(), "")
    }
}

object RecipeRow : Table("recipe_row") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val duration = integer("duration")
    override val primaryKey = PrimaryKey(id)
}

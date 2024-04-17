package xyz.wingio.hellish.domain.manager

import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.wingio.hellish.domain.manager.base.BasePreferenceManager
import xyz.wingio.hellish.domain.manager.base.stringPreference

class RecentSearchManager(
    context: Context,
    private val json: Json
): BasePreferenceManager(context.getSharedPreferences("search", Context.MODE_PRIVATE)) {

    private val demonSearches = mutableStateMapOf<String, SearchRecord<SearchDemon>>()
    private var demonSearchPreference by stringPreference("demon_searches", "{}")

    init {
        // Preload cached search records
        val cachedDemonSearches = json.decodeFromString<Map<String, SearchRecord<SearchDemon>>>(demonSearchPreference)
        demonSearches.putAll(cachedDemonSearches)
    }

    // Demon Search

    /**
     * Gets a list of previous demon searches filtered by time and frequency
     *
     * @param query Query to filter searches
     */
    fun getFrecentDemonQueries(query: String = "") = demonSearches.values
        .filter { it.query.startsWith(query, ignoreCase = true) }
        .sortedByDescending { it.lastSearched }
        .sortedByDescending { it.count }

    /**
     * Saves a demon search query
     *
     * @param query The query to save, if [demon] is not null then this will be overwritten with the [demon]'s name
     * @param demon Demon suggested to the user while typing
     */
    fun saveDemonSearch(query: String, demon: SearchDemon? = null) {
        val q = demon?.name ?: query

        demonSearches[q] =
            demonSearches[q]?.apply {
                count += 1
                lastSearched = System.currentTimeMillis()

                if (demon != null) {
                    data = demon
                    type = SearchRecord.Type.DEMON
                }
            } ?: SearchRecord(
                count = 1,
                lastSearched = System.currentTimeMillis(),
                query = q,
                data = demon,
                type = if (demon != null) SearchRecord.Type.DEMON else SearchRecord.Type.QUERY
            )

        demonSearchPreference = json.encodeToString(demonSearches.toMap()) // Save so restarts don't clear searches
    }

}

/**
 * Record of a search made by the user
 *
 * @param count Number of times the [query] has been searched
 * @param lastSearched Timestamp in millis for when this [query] was last searched
 * @param query The query entered by the user
 * @param data Additional metadata provided by rich suggestions
 * @param type The type of record
 */
@Serializable
data class SearchRecord<T>(
    var count: Int,
    var lastSearched: Long,
    val query: String,
    var data: T? = null,
    var type: Type = Type.QUERY
) {

    enum class Type {
        QUERY,
        DEMON,
        PLAYER
    }

}

/**
 * Partial demon object for storing the minimum amount of information necessary
 */
@Serializable
data class SearchDemon(
    val name: String,
    val publisher: String,
    val id: Int,
    val thumbnail: String
)
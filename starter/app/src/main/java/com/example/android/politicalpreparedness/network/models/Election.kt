package com.example.android.politicalpreparedness.network.models

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "election_table")
data class Election(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @JsonDate @ColumnInfo(name = "electionDay") val electionDay: Date,
    @ColumnInfo(name = "division") @Json(name = "ocdDivisionId") val division: String,
)

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class JsonDate

class DateJsonAdapterFactory : JsonAdapter.Factory {

    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi,
    ): JsonAdapter<*>? {
        if (type != Date::class.java) return null
        Types.nextAnnotations(annotations, JsonDate::class.java) ?: return null

        return JsonDateAdapter().nullSafe()
    }

    private class JsonDateAdapter : JsonAdapter<Date>() {

        @SuppressLint("SimpleDateFormat")
        override fun fromJson(reader: JsonReader): Date? {
            return SimpleDateFormat("yyyy-MM-dd").parse(reader.nextString())
        }

        override fun toJson(writer: JsonWriter, value: Date?) {
            writer.value(value?.time ?: 0)
        }
    }
}
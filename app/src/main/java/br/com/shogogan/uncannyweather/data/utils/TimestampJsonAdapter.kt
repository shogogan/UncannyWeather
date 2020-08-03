package br.com.shogogan.uncannyweather.data.utils

import com.squareup.moshi.*
import java.util.*

class TimestampJsonAdapter: JsonAdapter<Date>() {
    override fun fromJson(reader: JsonReader): Date? {
        return Date(reader.nextLong())
    }

    override fun toJson(writer: JsonWriter, value: Date?) {
        writer.value(value?.time)
    }
}

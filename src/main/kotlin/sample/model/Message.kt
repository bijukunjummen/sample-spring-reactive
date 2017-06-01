package sample.model

import com.fasterxml.jackson.annotation.JsonProperty

class Message(var id: String?,
              var payload: String,
              @field:JsonProperty("throw_exception") var throwException: Boolean,
              @field:JsonProperty("delay_by") var delayBy: Long
) {
    constructor() : this(null, "", false, 0)
}

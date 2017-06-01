package sample.model

class MessageAcknowledgement(
        var id: String?,
        var received: String,
        var payload: String
) {
    constructor() : this("", "", "")
}
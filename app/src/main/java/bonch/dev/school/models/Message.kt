package bonch.dev.school.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@Parcelize
data class Message (
    val messageId: Int,
    val messageText: String,
    val sentDate: Date,
    val isUser: Boolean
) : Parcelable

class MessageFactory {
    val messageList: MutableList<Message>

    init {
        messageList = mutableListOf()


        for (i in 0..25) {
            val isUser = Random.nextBoolean()
            val message = Message(i, if(isUser) "My message" else "Other message", Date(Random.nextLong()), isUser)
            messageList.add(message)
        }
    }
}
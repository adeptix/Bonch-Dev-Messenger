package bonch.dev.school.models

import android.os.Parcelable
import com.google.firebase.database.PropertyName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@Parcelize
data class Message (
    val messageId: Int = 0,
    val messageText: String = "",
    val sentDate: Date = Date(),
    @get:PropertyName("isUser")
    val isUser: Boolean = false
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
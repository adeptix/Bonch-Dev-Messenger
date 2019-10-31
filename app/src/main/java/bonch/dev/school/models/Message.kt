package bonch.dev.school.models

import com.mooveit.library.Fakeit
import java.util.*
import kotlin.random.Random

data class Message(
    val messageId: Int,
    val messageText: String,
    val sentDate: Date,
    val isUser: Boolean
)

class MessageFactory {
    val messageList: MutableList<Message>

    init {
        messageList = mutableListOf()
      //  Fakeit.initWithLocale("ru")


        for (i in 0..25) {
            val message = Message(i, "Message sjdhaoiajsdkajslkdjaslkjdkalsjdlkajsldkjaskldjlakjsdkas", Date(1000000), Random.nextBoolean())
            messageList.add(message)
        }
    }
}
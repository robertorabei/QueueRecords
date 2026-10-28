package adapters.out

import java.io.FileReader
import java.io.BufferedReader
import domain.{QueueTime, QueueTimeRepository}
import scala.collection.mutable.ListBuffer
import java.io.FileWriter

class csvQueueRepository(filename: String) extends QueueTimeRepository:
    private val queueTimes: ListBuffer[QueueTime] = ListBuffer.empty[QueueTime]
    private val fileReader = new BufferedReader(new FileReader(filename))

    def handleRead(line: String): Unit = 
        val values = line.split(",")
        queueTimes += new QueueTime(
            rideId    = values(0).toInt,
            rideName  = values(1),
            isOpen    = values(2).toBoolean,
            waitTime  = values(3).toInt,
            date      = values(4),
            timeStamp = values(5)
        )

        val newLine = fileReader.readLine()
        if (newLine != null)
            handleRead(newLine)

    def load(): Unit =
        fileReader.readLine()
        handleRead(fileReader.readLine())
        fileReader.close()


    def findAll(): List[QueueTime] = queueTimes.toList

    //To delete if saving queueTimes is not necessary
    def save(queueTime: QueueTime): Unit = 
        val newLine: String = 
            s"${queueTime.rideId.toString()}" +
            s",${queueTime.rideName}" +
            s",${queueTime.isOpen.toString().toUpperCase()}" + 
            s",${queueTime.waitTime.toString()}" + 
            s",${queueTime.date}" +
            s",${queueTime.timeStamp}"

        val fileWriter: FileWriter = new FileWriter(filename)
        fileWriter.write(newLine)
        fileWriter.close()



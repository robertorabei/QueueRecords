package adapters.out

import domain.{QueueTime, QueueTimeRepository}
import scala.collection.mutable.ListBuffer
import java.io.FileWriter
import com.github.tototoshi.csv._ 
import java.io.File


class csvQueueRepository(filename: String) extends QueueTimeRepository:
    private val queueTimes: ListBuffer[QueueTime] = ListBuffer.empty[QueueTime]
    private val reader = CSVReader.open(new File(filename))

    private def load(): List[QueueTime] = 
        val values = reader.all().drop(1).map {values =>
            QueueTime(
                rideId    = values(0).toInt,
                rideName  = values(1),
                isOpen    = values(2).toBoolean,
                waitTime  = values(3).toInt,
                date      = values(4),
                timeStamp = values(5)
            )
        }
        queueTimes.addAll(values)
        queueTimes.toList

    def findAll(): List[QueueTime] = load()


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



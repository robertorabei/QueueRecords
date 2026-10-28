package adapters.out

import domain.{QueueTime, QueueTimeRepository}
import scala.collection.mutable.ListBuffer

class InMemoryQueueRepository extends QueueTimeRepository:
    private val queueTimes = ListBuffer(
        QueueTime(0, "Space Mountain", true, 0, "2026-10-22", "08:00"),
        QueueTime(1, "Aerosmith", true, 0, "2026-10-22", "08:00"),
    )

    def findAll(): List[QueueTime]= queueTimes.toList

    //To delete if saving queueTimes is not necessary
    def save(queueTime: QueueTime): Unit= 
        queueTimes += queueTime
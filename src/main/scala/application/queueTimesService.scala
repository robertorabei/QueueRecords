package application

import domain.{QueueTime, QueueTimeRepository}

// 1) Determine the current state of EACH ride from latest record by date and timestamp 
class QueueTimeService(repo: QueueTimeRepository):
    private def filterQueueTimes(queueTimes: List[QueueTime]): List[QueueTime]=
        val sortedQueueTimes: List[QueueTime] = queueTimes.sortBy(x => (x.date, x.timeStamp)).reverse
        val currentExample = sortedQueueTimes(0)
        sortedQueueTimes.filter(_.date == currentExample.date).filter(_.timeStamp == currentExample.timeStamp).distinct

    def litsCurrentStates(): List[QueueTime] = 
        val currentQueueTimes: List[QueueTime] = filterQueueTimes(repo.findAll())
        currentQueueTimes.sortBy(_.rideId)



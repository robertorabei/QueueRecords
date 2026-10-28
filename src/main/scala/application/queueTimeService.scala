package application

import domain.{QueueTime, QueueTimeRepository}

// 1) Determine the current state of EACH ride from latest record by date and timestamp 
class QueueTimeService(repo: QueueTimeRepository):
    private def filterQueueTimes(queueTimes: List[QueueTime]): List[QueueTime]=
        queueTimes.maxByOption(_.date) match
            case Some(latest) => 
                queueTimes
                    .filter(q => q.date == latest.date && q.timeStamp == latest.timeStamp)
                    .distinct
            case None => 
                List.empty[QueueTime]

    def listCurrentStates(): List[QueueTime] = 
        val currentQueueTimes: List[QueueTime] = filterQueueTimes(repo.findAll())
        currentQueueTimes.sortBy(_.rideId)



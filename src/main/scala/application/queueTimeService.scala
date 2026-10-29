package application

import domain.{QueueTime, QueueTimeRepository}

// 1) Determine the current state of EACH ride from latest record by date and timestamp 
class QueueTimeService(repo: QueueTimeRepository):
    private def filterQueueTimes(queueTimes: List[QueueTime]): List[QueueTime]=
        queueTimes.maxByOption(q => (q.date, q.timeStamp)) match
            case Some(latest) => 
                queueTimes
                    .filter(q => q.date == latest.date && q.timeStamp == latest.timeStamp)
                    .distinct
            case None => 
                List.empty[QueueTime]
    
    def printInfo(ride: QueueTime): Unit =
        println(s"[${if ride.isOpen then "OPEN" else "CLOSED"}] ${ride.rideId} ${ride.rideName} ${ride.date} ${ride.timeStamp}. Waiting time: ${if ride.waitTime >= 0 then ride.waitTime else "UNKNOWN"}")

    def exists(requestedId: Int): Boolean = 
        listCurrentStates().exists(_.rideId == requestedId)

    def listCurrentStates(): List[QueueTime] = 
        val currentQueueTimes: List[QueueTime] = filterQueueTimes(repo.findAll())
        currentQueueTimes.sortBy(_.rideId)

    def findCurrentState(requestedId: Int): QueueTime =
        val currentQueueTimes: List[QueueTime] = listCurrentStates()
        currentQueueTimes.filter(q => q.rideId == requestedId).head

    def recommendRides(): List[QueueTime] = 
        val currentQueueTimes: List[QueueTime] = listCurrentStates()
        val openRides: List[QueueTime] = currentQueueTimes.filter(q => q.isOpen == true && q.waitTime >= 0)
        val sortedWaitTimes = openRides.sortBy(_.waitTime)
        openRides.length match {
            case n if n >= 3 => sortedWaitTimes.take(3)
            case 2           => sortedWaitTimes.take(2)
            case 1           => sortedWaitTimes.take(1) 
            case 0           => List.empty[QueueTime]
        }




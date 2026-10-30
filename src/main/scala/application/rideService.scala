package application

import domain.{Ride, RideRepository}

// 1) Determine the current state of EACH ride from latest record by date and timestamp --> listCurrentStates()
// 2) Find current state of requested ride with ID --> findCurrentState(requestedId: Int)
// 3) Recommend at most 3 open rides based on waitTime and groupedBy id --> recommendRides()

class RideService(repo: RideRepository):
    private def filterRides(rides: List[Ride]): List[Ride]=
        rides.maxByOption(q => (q.date, q.timeStamp)) match
            case Some(latest) => 
                rides
                    .filter(q => q.date == latest.date && q.timeStamp == latest.timeStamp)
                    .distinct
            case None => 
                List.empty[Ride]
    
    def printInfo(ride: Ride): Unit =
        println(s"[${if ride.isOpen then "OPEN" else "CLOSED"}] ${ride.rideId} ${ride.rideName} ${ride.date} ${ride.timeStamp}. Waiting time: ${if ride.waitTime >= 0 then ride.waitTime else "UNKNOWN"}")

    def exists(requestedId: Int): Boolean = 
        listCurrentStates().exists(_.rideId == requestedId)

    def listCurrentStates(): List[Ride] = 
        val currentRides: List[Ride] = filterRides(repo.findAll())
        currentRides.sortBy(_.rideId)

    def findCurrentState(requestedId: Int): Ride =
        val currentRides: List[Ride] = listCurrentStates()
        currentRides.filter(q => q.rideId == requestedId).head

    def recommendRides(): List[Ride] = 
        val currentRides: List[Ride] = listCurrentStates()
        val openRides: List[Ride] = currentRides.filter(q => q.isOpen == true && q.waitTime >= 0)
        val sortedWaitTimes = openRides.sortBy(_.waitTime)
        openRides.length match {
            case n if n >= 3 => sortedWaitTimes.take(3)
            case 2           => sortedWaitTimes.take(2)
            case 1           => sortedWaitTimes.take(1) 
            case 0           => List.empty[Ride]
        }




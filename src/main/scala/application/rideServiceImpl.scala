package application

import domain.{Ride, RideRepository}

// 1) Determine the current state of EACH ride from latest record by date and timestamp --> listCurrentStates()
// 2) Find current state of requested ride with ID --> findCurrentState(requestedId: Int)
// 3) Recommend at most 3 open rides based on waitTime and groupedBy id --> recommendRides()

class RideServiceImpl(repo: RideRepository) extends RideService:
    private def filterRides(rides: List[Ride]): List[Ride]=
        rides.maxByOption(q => (q.date, q.timeStamp)) match
            case Some(latest) => 
                rides
                    .filter(q => q.date == latest.date && q.timeStamp == latest.timeStamp)
                    .distinct
            case None => 
                List.empty[Ride]
    
    def printInfo(ride: Ride): String =
        s"[${if ride.isOpen then "OPEN" else "CLOSED"}] ${ride.rideId} ${ride.rideName} ${ride.date} ${ride.timeStamp}. Waiting time: ${if ride.waitTime >= 0 then ride.waitTime else "UNKNOWN"}"

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
        val openRides: List[Ride] = currentRides.filter(q => q.isOpen && q.waitTime >= 0)
        
        openRides.sortBy(_.waitTime).take(3)




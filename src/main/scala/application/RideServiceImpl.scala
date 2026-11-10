package application

import domain.{Ride, RideRepository}

class RideServiceImpl(repo: RideRepository) extends RideService:
    private def filterRides(rides: List[Ride]): List[Ride]=
        rides.maxByOption(q => (q.date, q.timeStamp)) match
            case Some(latest) => 
                rides
                    .filter(q => q.date == latest.date && q.timeStamp == latest.timeStamp)
                    .distinct
            case None => 
                List.empty[Ride]

    def exists(requestedId: Int): Boolean = 
        listCurrentRides().exists(_.rideId == requestedId)

    def listCurrentRides(): List[Ride] =
        val allRides: List[Ride] = repo.findAll()
        val currentRides: List[Ride] = filterRides(allRides)
        currentRides.sortBy(_.rideId)

    def findRide(requestedId: Int): Ride =
        val currentRides: List[Ride] = listCurrentRides()
        currentRides.filter(q => q.rideId == requestedId).head

    def recommendRides(): List[Ride] = 
        val currentRides: List[Ride] = listCurrentRides()
        val openRides: List[Ride] = currentRides.filter(q => q.isOpen && q.waitTime >= 0)
        
        openRides.sortBy(_.waitTime).take(3)




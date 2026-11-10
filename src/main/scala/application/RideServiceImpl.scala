package application

import domain.{Ride, RideRepository}

class RideServiceImpl(repo: RideRepository) extends RideService:
    // Filters rides using the latest date and timestamp
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

    // Fetches all rides with repo, filters them to get only current ones and sorts by ID for readability
    def listCurrentRides(): List[Ride] =
        val allRides: List[Ride] = repo.findAll()
        val currentRides: List[Ride] = filterRides(allRides)
        currentRides.sortBy(_.rideId)

    def findRide(requestedId: Int): Ride =
        val currentRides: List[Ride] = listCurrentRides()
        currentRides.filter(q => q.rideId == requestedId).head

    // Fetches current rides, filters with isOpen and waitTime and then sorts by waitTime and THEN rideID
    def recommendRides(): List[Ride] = 
        val currentRides: List[Ride] = listCurrentRides()
        val openRides: List[Ride] = currentRides.filter(q => q.isOpen && q.waitTime >= 0)

        openRides.sortBy(ride => (ride.waitTime, ride.rideId)).take(3)




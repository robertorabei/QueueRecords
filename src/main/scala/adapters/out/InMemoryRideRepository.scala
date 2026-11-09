package adapters.out

import domain.{Ride, RideRepository}
import scala.collection.mutable.ListBuffer

class InMemoryRideRepository extends RideRepository:
    private val rides = ListBuffer[Ride]()
    def findAll(): List[Ride]= rides.toList

    // Used in testing 
    def save(newRide: Ride): Unit= 
        rides += newRide
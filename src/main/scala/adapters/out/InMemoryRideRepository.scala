package adapters.out

import domain.{Ride, RideRepository}
import scala.collection.mutable.ListBuffer

class InMemoryRideRepository extends RideRepository:
    private val rides = ListBuffer[Ride]()

    def findAll(): List[Ride]= rides.toList

    // Used to add rides used in tests
    def save(newRide: Ride): Unit= 
        rides += newRide
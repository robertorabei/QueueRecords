package adapters.out

import domain.{Ride, RideRepository}
import scala.collection.mutable.ListBuffer

class InMemoryRideRepository extends RideRepository:
    private val rides = ListBuffer(
        Ride(rideId = 2702, rideName = "Adventure Isle", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 7306, rideName = "Indiana Jones and the Temple of Peril Single Rider", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 2, rideName = "Indiana Jones™ and the Temple of Peril", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 1, rideName = "La Cabane des Robinson", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 2703, rideName = "Le Passage Enchanté d'Aladdin", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 2705, rideName = "Pirate Galleon", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 3, rideName = "Pirates of the Caribbean", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 2704, rideName = "Pirates' Beach", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 4, rideName ="Autopia, presented by Avis", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 5, rideName = "Buzz Lightyear Laser Blast", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 2706, rideName = "Disneyland Railroad Discoveryland Station", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 6, rideName = "Les Mystères du Nautilus", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 2707, rideName = "Mickey’s PhilharMagic", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00"),
        Ride(rideId = 7, rideName = "Orbitron®", isOpen = false, waitTime = 0, date = "2025-10-29", timeStamp = "08:00")
    )
    def findAll(): List[Ride]= rides.toList

    //To delete if saving rides is not necessary
    def save(newRide: Ride): Unit= 
        rides += newRide
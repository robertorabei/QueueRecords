package domain 

trait RideRepository:
    def findAll(): List[Ride]
    def save(ride: Ride): Unit 
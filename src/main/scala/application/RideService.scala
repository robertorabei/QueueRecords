package application

import domain.Ride


trait RideService {
  def listCurrentRides(): List[Ride]
  def exists(id: Int): Boolean
  def findRide(id: Int): Ride
  def recommendRides(): List[Ride]
}
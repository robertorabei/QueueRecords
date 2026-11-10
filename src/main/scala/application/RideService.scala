package application

import domain.Ride


trait RideService {
  def listCurrentStates(): List[Ride]
  def exists(id: Int): Boolean
  def findCurrentState(id: Int): Ride
  def recommendRides(): List[Ride]
}
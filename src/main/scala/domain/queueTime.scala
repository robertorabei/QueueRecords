package domain

case class QueueTime(rideId: Int, rideName: String, isOpen: Boolean, waitTime: Int, date: String, timeStamp: String):
    
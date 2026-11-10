package adapters.in

import application.RideService
import domain.Ride

class CLIAdapter(service: RideService):
    private def formatRide(ride: Ride): String =
        s"[${if ride.isOpen then "OPEN" else "CLOSED"}] ${ride.rideId} ${ride.rideName} ${ride.date} ${ride.timeStamp}. Waiting time: ${if ride.waitTime >= 0 then ride.waitTime else "UNKNOWN"}\n"

    def run(): Unit=
        var running = true
        while running do 
            println("1. List current rides")
            println("2. Find a ride (requires ID)")
            println("3. Recommend three rides")
            println("9. Exit")
            print("> ")
            scala.io.StdIn.readLine() match
                case "1" => 
                    val rides = service.listCurrentRides()
                    if rides.isEmpty then println("No rides currently\n")
                    else rides.foreach(ride => println(formatRide(ride)))
                case "2" =>
                    print("ID: ")
                    val requestedRideID: Int = scala.io.StdIn.readInt() 
                    if (service.exists(requestedRideID))
                        val requestedRide = service.findRide(requestedRideID)
                        println(formatRide(requestedRide))
                    else 
                        println(s"This ride: ${requestedRideID} does not exist.\n")
                
                case "3" =>
                    println("Here are rides we recommend:")
                    val recommendedRides = service.recommendRides()
                    if recommendedRides.isEmpty then println("No open rides currently.\n")
                    else recommendedRides.foreach(ride => println(formatRide(ride)))

                case "9" => 
                    println("Exiting...")
                    running = false
                case _ =>
                    println("Invalid Option\n\n")


package adapters.in

import application.RideService

class ConsoleController(service: RideService):
    def run(): Unit= 
        var running = true
        while running do 
            println("\n1. List current rides")
            println("\n2. Find a ride (requires ID)")
            println("\n3. Recommend three rides")
            println("\n9. Exit")
            print(">")
            scala.io.StdIn.readLine() match
                case "1" => 
                    val rides = service.listCurrentStates()
                    if rides.isEmpty then print("No rides currently")
                    else rides.foreach(ride => service.printInfo(ride))
                case "2" =>
                    println("ID:")
                    val requestedRideID: Int = scala.io.StdIn.readInt() 
                    if (service.exists(requestedRideID))
                        val requestedRide = service.findCurrentState(requestedRideID)
                        service.printInfo(requestedRide)
                    else 
                        println(s"This ride: ${requestedRideID} does not exist.")
                
                case "3" =>
                    println("Here are 3 rides we recommend:")
                    val recommendedRides = service.recommendRides()
                    if recommendedRides.isEmpty then print("No open rides currently.")
                    else recommendedRides.foreach(ride => service.printInfo(ride))

                case "9" => 
                    running = false
                case _ => 
                    println("Invalid Option")


package adapters.in

import application.QueueTimeService

class ConsoleController(service: QueueTimeService):
    def run(): Unit= 
        var running = true
        while running do 
            println("\n1. List current rides states")
            println("\n2. Find a ride (with ID)")
            println("\n3. Recommend three rides")
            println("\n9. Exit")
            print(">")
            scala.io.StdIn.readLine() match
                case "1" => 
                    val queueTimes = service.listCurrentStates()
                    if queueTimes.isEmpty then print("No rides currently")
                    else queueTimes.foreach(ride => service.printInfo(ride))
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


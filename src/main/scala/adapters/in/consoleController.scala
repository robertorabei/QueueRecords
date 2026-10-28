package adapters.in

import application.QueueTimeService

class ConsoleController(service: QueueTimeService):
    def run(): Unit= 
        var running = true
        while running do 
            println("\n1. List current rides states")
            println("\n2. Exit")
            print(">")
            scala.io.StdIn.readLine() match
                case "1" => 
                    val queueTimes = service.listCurrentStates()
                    if queueTimes.isEmpty then print("No rides currently")
                    else queueTimes.foreach(t => println(s"[${if t.isOpen then "x" else " "}] ${t.rideId}. ${t.waitTime}"))
                case "2" => 
                    running = false


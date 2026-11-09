import domain.{Ride, RideRepository}
import application.{RideService, RideServiceImpl}
import adapters.out.InMemoryRideRepository
import org.scalatest.funsuite.AnyFunSuite

object RecommendationTestData {
    val ridesTest1: List[Ride] = List(
        Ride(1, "Ride number 1", false, 0, "2026-09-25", "21:00"),
        Ride(2, "Ride number 2", false, 0, "2026-09-25", "21:00"),
        Ride(3, "Ride number 3", false, 0, "2026-09-25", "21:00"),
        Ride(4, "Ride number 4", false, 0, "2026-09-25", "21:00"),
        Ride(5, "Ride number 5", false, 0, "2026-09-25", "21:00")
    )

    val ridesTest2: List[Ride] = List(
        Ride(1, "Ride number 1", true, -1, "2026-09-25", "21:00"),
        Ride(2, "Ride number 2", true, -1, "2026-09-25", "21:00"),
        Ride(3, "Ride number 3", true, -1, "2026-09-25", "21:00"),
        Ride(4, "Ride number 4", true, -1, "2026-09-25", "21:00"),
        Ride(5, "Ride number 5", true, -1, "2026-09-25", "21:00")
    )

    val ridesTest3: List[Ride] = List(
        Ride(1, "Ride number 1", true, 10, "2026-09-25", "16:00"),
        Ride(2, "Ride number 2", true, 10, "2026-09-25", "14:00"),
        Ride(3, "Ride number 3", true, 0, "2026-09-25", "14:00"),
        Ride(4, "Ride number 4", true, 5, "2026-09-24", "17:00")
    )

    val ridesTest4: List[Ride] = List(
        Ride(1, "Ride number 1", true, 10, "2026-09-25", "16:00"),
        Ride(2, "Ride number 2", true, 10, "2026-09-25", "16:00"),
        Ride(3, "Ride number 3", true, 15, "2026-09-25", "16:00"),
        Ride(4, "Ride number 3", true, 5, "2026-09-25", "16:00"),
        Ride(5, "Ride number 3", true, 0, "2026-09-25", "16:00"),
        Ride(6, "Ride number 4", true, 5, "2026-09-25", "16:00")
    )

    val expectationTest4: List[Ride] = List(
        Ride(5, "Ride number 3", true, 0, "2026-09-25", "16:00"),
        Ride(4, "Ride number 3", true, 5, "2026-09-25", "16:00"),
        Ride(6, "Ride number 4", true, 5, "2026-09-25", "16:00"),
    )
}

class RecommendationSuite extends AnyFunSuite {
    import RecommendationTestData._

    test("recommended rides should all be open") {
        val repo: RideRepository = new InMemoryRideRepository()
        val rideService: RideService = new RideServiceImpl(repo)

        ridesTest1.foreach(repo.save)
        assert(rideService.recommendRides().isEmpty)
    }

    test("recommended rides should all have known waiting times") {
        val repo: RideRepository = new InMemoryRideRepository()
        val rideService: RideService = new RideServiceImpl(repo)

        ridesTest2.foreach(repo.save)
        assert(rideService.recommendRides().isEmpty)
    }

    test("recommended rides should be current") {
        val repo: RideRepository = new InMemoryRideRepository()
        val rideService: RideService = new RideServiceImpl(repo)

        ridesTest3.foreach(repo.save)
        val result: List[Ride] = rideService.recommendRides()
        assert(result.length == 1 &&
                result.head == Ride(1, "Ride number 1", true, 10, "2026-09-25", "16:00"))
    }

    test("recommended rides should be grouped by waiting time and then by IDs") {
        val repo: RideRepository = new InMemoryRideRepository()
        val rideService: RideService = new RideServiceImpl(repo)

        ridesTest4.foreach(repo.save)
        val result: List[Ride] = rideService.recommendRides()
        result.foreach(println)
        assert(result == expectationTest4)
    }
}





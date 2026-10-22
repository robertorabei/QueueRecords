package domain 

trait QueueTimeRepository:
    def findAll(): List[QueueTime]
    def save(queueTime: QueueTime): Unit
package com.demo.springshedlock

import com.mongodb.reactivestreams.client.MongoClient
import net.javacrumbs.shedlock.core.LockAssert
import net.javacrumbs.shedlock.provider.mongo.reactivestreams.ReactiveStreamsMongoLockProvider
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@SpringBootApplication
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "10s")
class SpringshedlockApplication

fun main(args: Array<String>) {
    runApplication<SpringshedlockApplication>(*args)
}


@Configuration
class ShedlockConfiguration {
    @Bean
    fun mongoLockProvider(mongoClient: MongoClient) =
            ReactiveStreamsMongoLockProvider(mongoClient.getDatabase("testdb").getCollection("testcoll"))
}

@Component
class ShedLockTask(
        @Value("\${app.instance.name}")
        val instanceName: String,
) {
    @Scheduled(cron = "*/10 * * * * *")
    @SchedulerLock(name = "ShedLockTask_someTask", lockAtMostFor = "9s", lockAtLeastFor = "9s")
    fun someTask() {
        LockAssert.assertLocked()
        println("Running someTask in instance - $instanceName at ${LocalDateTime.now()}")
    }
}

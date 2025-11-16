package com.example.domain

import com.example.application.TrackingDataPoint
import com.example.application.TrackingSummaryResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class TrackingServiceImplTest {

    @Test
    fun `getTrackingSummary should return summary from repository`() = runBlocking {
        // Given
        val userTrackingRepository = mockk<UserTrackingRepository>()
        val trackingService = TrackingServiceImpl(userTrackingRepository)
        val range = "day"
        val date = "2025-01-01T00:00:00Z"
        val expectedData = listOf(
            TrackingDataPoint("2025-01-01T00:00:00Z", 100),
            TrackingDataPoint("2024-12-31T00:00:00Z", 200)
        )
        coEvery { userTrackingRepository.getSummaryByRange(range, date) } returns expectedData

        // When
        val result = trackingService.getTrackingSummary(range, date)

        // Then
        assertEquals(TrackingSummaryResponse(range, expectedData), result)
    }
}

package com.work.activitytracker.core.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.work.activitytracker.core.data.local.SportRecordDatabase
import com.work.activitytracker.core.data.local.entity.SportRecordEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SportRecordDaoTest {

    private lateinit var database: SportRecordDatabase
    private lateinit var dao: SportRecordDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SportRecordDatabase::class.java,
        ).allowMainThreadQueries().build()
        dao = database.sportRecordDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndObserveAll_returnsInsertedRecord() = runTest {
        val record = fakeEntity("1")
        dao.insert(record)

        val result = dao.observeAll().first()

        assertEquals(1, result.size)
        assertEquals(record, result.first())
    }

    @Test
    fun insertMultiple_returnsOrderedByCreatedAtDesc() = runTest {
        dao.insert(fakeEntity("1", createdAt = 1000L))
        dao.insert(fakeEntity("2", createdAt = 3000L))
        dao.insert(fakeEntity("3", createdAt = 2000L))

        val result = dao.observeAll().first()

        assertEquals(listOf("2", "3", "1"), result.map { it.id })
    }

    @Test
    fun deleteById_removesOnlyTargetRecord() = runTest {
        dao.insert(fakeEntity("1"))
        dao.insert(fakeEntity("2"))

        dao.deleteById("1")

        val result = dao.observeAll().first()
        assertEquals(1, result.size)
        assertEquals("2", result.first().id)
    }

    @Test
    fun insertDuplicate_replacesExistingRecord() = runTest {
        dao.insert(fakeEntity("1", name = "Old name"))
        dao.insert(fakeEntity("1", name = "New name"))

        val result = dao.observeAll().first()

        assertEquals(1, result.size)
        assertEquals("New name", result.first().name)
    }

    @Test
    fun observeAll_emptyDatabase_returnsEmptyList() = runTest {
        val result = dao.observeAll().first()
        assertTrue(result.isEmpty())
    }

    private fun fakeEntity(
        id: String,
        name: String = "Run",
        createdAt: Long = System.currentTimeMillis(),
    ) = SportRecordEntity(
        id = id,
        name = name,
        location = "Park",
        durationMinutes = 30,
        createdAt = createdAt,
        userId = "user1",
    )
}

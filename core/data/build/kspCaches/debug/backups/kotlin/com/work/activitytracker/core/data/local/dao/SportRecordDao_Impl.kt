package com.work.activitytracker.core.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.work.activitytracker.core.`data`.local.entity.SportRecordEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SportRecordDao_Impl(
  __db: RoomDatabase,
) : SportRecordDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSportRecordEntity: EntityInsertAdapter<SportRecordEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSportRecordEntity = object : EntityInsertAdapter<SportRecordEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `sport_records` (`id`,`name`,`location`,`durationMinutes`,`createdAt`,`userId`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SportRecordEntity) {
        statement.bindText(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.location)
        statement.bindLong(4, entity.durationMinutes.toLong())
        statement.bindLong(5, entity.createdAt)
        statement.bindText(6, entity.userId)
      }
    }
  }

  public override suspend fun insert(record: SportRecordEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfSportRecordEntity.insert(_connection, record)
  }

  public override fun observeAll(): Flow<List<SportRecordEntity>> {
    val _sql: String = "SELECT * FROM sport_records ORDER BY createdAt DESC"
    return createFlow(__db, false, arrayOf("sport_records")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfLocation: Int = getColumnIndexOrThrow(_stmt, "location")
        val _columnIndexOfDurationMinutes: Int = getColumnIndexOrThrow(_stmt, "durationMinutes")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "createdAt")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "userId")
        val _result: MutableList<SportRecordEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SportRecordEntity
          val _tmpId: String
          _tmpId = _stmt.getText(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpLocation: String
          _tmpLocation = _stmt.getText(_columnIndexOfLocation)
          val _tmpDurationMinutes: Int
          _tmpDurationMinutes = _stmt.getLong(_columnIndexOfDurationMinutes).toInt()
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUserId: String
          _tmpUserId = _stmt.getText(_columnIndexOfUserId)
          _item =
              SportRecordEntity(_tmpId,_tmpName,_tmpLocation,_tmpDurationMinutes,_tmpCreatedAt,_tmpUserId)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteById(id: String) {
    val _sql: String = "DELETE FROM sport_records WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}

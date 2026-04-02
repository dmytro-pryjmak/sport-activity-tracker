package com.work.activitytracker.core.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.work.activitytracker.core.`data`.local.dao.SportRecordDao
import com.work.activitytracker.core.`data`.local.dao.SportRecordDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SportRecordDatabase_Impl : SportRecordDatabase() {
  private val _sportRecordDao: Lazy<SportRecordDao> = lazy {
    SportRecordDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "85a8fd7709c5df295725ed79ca8ee596", "fa75d81346916e792f284ba35949cc8b") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `sport_records` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `location` TEXT NOT NULL, `durationMinutes` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `userId` TEXT NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '85a8fd7709c5df295725ed79ca8ee596')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `sport_records`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsSportRecords: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSportRecords.put("id", TableInfo.Column("id", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSportRecords.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSportRecords.put("location", TableInfo.Column("location", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSportRecords.put("durationMinutes", TableInfo.Column("durationMinutes", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSportRecords.put("createdAt", TableInfo.Column("createdAt", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSportRecords.put("userId", TableInfo.Column("userId", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSportRecords: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSportRecords: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSportRecords: TableInfo = TableInfo("sport_records", _columnsSportRecords,
            _foreignKeysSportRecords, _indicesSportRecords)
        val _existingSportRecords: TableInfo = read(connection, "sport_records")
        if (!_infoSportRecords.equals(_existingSportRecords)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |sport_records(com.work.activitytracker.core.data.local.entity.SportRecordEntity).
              | Expected:
              |""".trimMargin() + _infoSportRecords + """
              |
              | Found:
              |""".trimMargin() + _existingSportRecords)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "sport_records")
  }

  public override fun clearAllTables() {
    super.performClear(false, "sport_records")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(SportRecordDao::class, SportRecordDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun sportRecordDao(): SportRecordDao = _sportRecordDao.value
}

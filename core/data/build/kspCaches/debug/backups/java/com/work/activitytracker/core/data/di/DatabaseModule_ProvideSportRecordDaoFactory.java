package com.work.activitytracker.core.data.di;

import com.work.activitytracker.core.data.local.SportRecordDatabase;
import com.work.activitytracker.core.data.local.dao.SportRecordDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class DatabaseModule_ProvideSportRecordDaoFactory implements Factory<SportRecordDao> {
  private final Provider<SportRecordDatabase> dbProvider;

  private DatabaseModule_ProvideSportRecordDaoFactory(Provider<SportRecordDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SportRecordDao get() {
    return provideSportRecordDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSportRecordDaoFactory create(
      Provider<SportRecordDatabase> dbProvider) {
    return new DatabaseModule_ProvideSportRecordDaoFactory(dbProvider);
  }

  public static SportRecordDao provideSportRecordDao(SportRecordDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSportRecordDao(db));
  }
}

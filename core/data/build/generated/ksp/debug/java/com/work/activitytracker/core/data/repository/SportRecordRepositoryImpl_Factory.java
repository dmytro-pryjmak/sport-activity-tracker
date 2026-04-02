package com.work.activitytracker.core.data.repository;

import com.work.activitytracker.core.data.local.dao.SportRecordDao;
import com.work.activitytracker.core.data.remote.source.FirestoreRecordSource;
import com.work.activitytracker.core.domain.session.UserSession;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SportRecordRepositoryImpl_Factory implements Factory<SportRecordRepositoryImpl> {
  private final Provider<SportRecordDao> daoProvider;

  private final Provider<FirestoreRecordSource> remoteSourceProvider;

  private final Provider<UserSession> userSessionProvider;

  private SportRecordRepositoryImpl_Factory(Provider<SportRecordDao> daoProvider,
      Provider<FirestoreRecordSource> remoteSourceProvider,
      Provider<UserSession> userSessionProvider) {
    this.daoProvider = daoProvider;
    this.remoteSourceProvider = remoteSourceProvider;
    this.userSessionProvider = userSessionProvider;
  }

  @Override
  public SportRecordRepositoryImpl get() {
    return newInstance(daoProvider.get(), remoteSourceProvider.get(), userSessionProvider.get());
  }

  public static SportRecordRepositoryImpl_Factory create(Provider<SportRecordDao> daoProvider,
      Provider<FirestoreRecordSource> remoteSourceProvider,
      Provider<UserSession> userSessionProvider) {
    return new SportRecordRepositoryImpl_Factory(daoProvider, remoteSourceProvider, userSessionProvider);
  }

  public static SportRecordRepositoryImpl newInstance(SportRecordDao dao,
      FirestoreRecordSource remoteSource, UserSession userSession) {
    return new SportRecordRepositoryImpl(dao, remoteSource, userSession);
  }
}

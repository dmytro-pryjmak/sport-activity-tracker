package com.work.activitytracker.feature.add;

import com.work.activitytracker.core.domain.session.UserSession;
import com.work.activitytracker.core.domain.usecase.SaveRecordUseCase;
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
public final class AddViewModel_Factory implements Factory<AddViewModel> {
  private final Provider<SaveRecordUseCase> saveRecordUseCaseProvider;

  private final Provider<UserSession> userSessionProvider;

  private AddViewModel_Factory(Provider<SaveRecordUseCase> saveRecordUseCaseProvider,
      Provider<UserSession> userSessionProvider) {
    this.saveRecordUseCaseProvider = saveRecordUseCaseProvider;
    this.userSessionProvider = userSessionProvider;
  }

  @Override
  public AddViewModel get() {
    return newInstance(saveRecordUseCaseProvider.get(), userSessionProvider.get());
  }

  public static AddViewModel_Factory create(Provider<SaveRecordUseCase> saveRecordUseCaseProvider,
      Provider<UserSession> userSessionProvider) {
    return new AddViewModel_Factory(saveRecordUseCaseProvider, userSessionProvider);
  }

  public static AddViewModel newInstance(SaveRecordUseCase saveRecordUseCase,
      UserSession userSession) {
    return new AddViewModel(saveRecordUseCase, userSession);
  }
}

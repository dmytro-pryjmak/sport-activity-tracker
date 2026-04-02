package com.work.activitytracker.feature.list;

import com.work.activitytracker.core.domain.usecase.DeleteRecordUseCase;
import com.work.activitytracker.core.domain.usecase.GetRecordsUseCase;
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
public final class ListViewModel_Factory implements Factory<ListViewModel> {
  private final Provider<GetRecordsUseCase> getRecordsUseCaseProvider;

  private final Provider<DeleteRecordUseCase> deleteRecordUseCaseProvider;

  private ListViewModel_Factory(Provider<GetRecordsUseCase> getRecordsUseCaseProvider,
      Provider<DeleteRecordUseCase> deleteRecordUseCaseProvider) {
    this.getRecordsUseCaseProvider = getRecordsUseCaseProvider;
    this.deleteRecordUseCaseProvider = deleteRecordUseCaseProvider;
  }

  @Override
  public ListViewModel get() {
    return newInstance(getRecordsUseCaseProvider.get(), deleteRecordUseCaseProvider.get());
  }

  public static ListViewModel_Factory create(Provider<GetRecordsUseCase> getRecordsUseCaseProvider,
      Provider<DeleteRecordUseCase> deleteRecordUseCaseProvider) {
    return new ListViewModel_Factory(getRecordsUseCaseProvider, deleteRecordUseCaseProvider);
  }

  public static ListViewModel newInstance(GetRecordsUseCase getRecordsUseCase,
      DeleteRecordUseCase deleteRecordUseCase) {
    return new ListViewModel(getRecordsUseCase, deleteRecordUseCase);
  }
}

package com.work.activitytracker.core.data.di;

import com.google.firebase.firestore.FirebaseFirestore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class FirebaseModule_Companion_ProvideFirestoreFactory implements Factory<FirebaseFirestore> {
  @Override
  public FirebaseFirestore get() {
    return provideFirestore();
  }

  public static FirebaseModule_Companion_ProvideFirestoreFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseFirestore provideFirestore() {
    return Preconditions.checkNotNullFromProvides(FirebaseModule.Companion.provideFirestore());
  }

  private static final class InstanceHolder {
    static final FirebaseModule_Companion_ProvideFirestoreFactory INSTANCE = new FirebaseModule_Companion_ProvideFirestoreFactory();
  }
}

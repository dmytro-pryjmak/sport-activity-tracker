package com.work.activitytracker.core.data.remote.source;

import com.google.firebase.firestore.FirebaseFirestore;
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
public final class FirestoreRecordSource_Factory implements Factory<FirestoreRecordSource> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private FirestoreRecordSource_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public FirestoreRecordSource get() {
    return newInstance(firestoreProvider.get());
  }

  public static FirestoreRecordSource_Factory create(
      Provider<FirebaseFirestore> firestoreProvider) {
    return new FirestoreRecordSource_Factory(firestoreProvider);
  }

  public static FirestoreRecordSource newInstance(FirebaseFirestore firestore) {
    return new FirestoreRecordSource(firestore);
  }
}

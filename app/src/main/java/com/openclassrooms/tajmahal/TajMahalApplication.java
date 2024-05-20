package com.openclassrooms.tajmahal;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

/**
 * The main application class for the TajMahal project.
 *
 *La classe TajMahalApplication étend la classe Application dans Android
  pour effectuer une configuration et une gestion à l'échelle de l'application.
 *
 * <p>
 * This class is annotated with {@code @HiltAndroidApp}, marking it as the entry point
 * for Dagger-Hilt dependency injection. By extending the {@code Application} class
 * and annotating it with {@code @HiltAndroidApp}, Hilt will automatically generate
 * and initialize the necessary DI components and modules required for the application.
 * </p>
 * <p>
 * Developers should be cautious when adding logic to this class, as it affects the
 * entire lifecycle of the application. Generally, this class should only be used for
 * app-wide configurations and initializations.
 * </p>
 *
 * <strong>Note for beginners:</strong> Hilt is a dependency injection library for Android
 * that reduces the boilerplate code to do manual dependency injection in your project.
 * Dependency injection allows objects to receive other objects (dependencies)
 * that they need from an external source rather than creating them internally.
 *
 * @see <a href="https://developer.android.com/training/dependency-injection/hilt-android">Hilt's official documentation</a>
 */
@HiltAndroidApp
public class TajMahalApplication extends Application {
}

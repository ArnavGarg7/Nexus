# Retrofit
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keep class com.nexus.app.core.data.remote.dto.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }

# Room
-keep class androidx.room.** { *; }

# Moshi
-keep class com.squareup.moshi.** { *; }

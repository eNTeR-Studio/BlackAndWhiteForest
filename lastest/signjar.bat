"%JAVA_HOME%\bin\jarsigner.exe" -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore key.keystore BlackAndWhiteForest-android.apk key.keystore
"%JAVA_HOME%\bin\jarsigner.exe" -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore key.keystore BlackAndWhiteForest-desktop.jar key.keystore
pause
gradlew clean desktop:dist android:assembleRelease ios:assemble html:dist --offline
copy .\android\key.keystore %JAVA_HOME%\bin\ /Y
copy .\android\build\outputs\apk\android-release-unsigned.apk %JAVA_HOME%\bin\ /Y
"%JAVA_HOME%\bin\jarsigner.exe" -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore key.keystore android-release-unsigned.apk key.keystore

copy %JAVA_HOME%\bin\android-release-unsigned.apk .\snapshots\BlackAndWhiteForest-android.apk /Y
copy .\desktop\build\libs\desktop-1.0.jar .\snapshots\BlackAndWhiteForest-desktop.jar /Y
copy .\html\build\dist\*.* .\snapshots\html\ /Y
copy .\ios\build\libs\ios-1.0.jar .\snapshots\ /Y
pause
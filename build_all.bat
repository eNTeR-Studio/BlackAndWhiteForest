set BAWFVersion=1.0
gradlew clean desktop:dist android:assembleRelease
copy .\android\build\outputs\apk\android-release.apk .\lastest\BlackAndWhiteForest-android.apk /Y
copy .\desktop\build\libs\desktop-%BAWFVersion%.jar .\lastest\BlackAndWhiteForest-desktop.jar /Y
pause
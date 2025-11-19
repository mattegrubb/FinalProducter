@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem
@rem SPDX-License-Identifier: Apache-2.0
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi
+
+rem Resolve CD for wrapper detection
+set "START_DIR=%CD%"

 @rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
-if not defined DEFAULT_JVM_OPTS set "DEFAULT_JVM_OPTS=-Xmx1g -Xms64m"
-set "DEFAULT_JVM_OPTS=-Xmx64m -Xms64m"
 rem set "AGENT_JAR=%APP_HOME%\lib\agents\gradle-instrumentation-agent-9.2.1.jar"
 rem if exist "%AGENT_JAR%" (
 rem     set "DEFAULT_JVM_OPTS=%DEFAULT_JVM_OPTS% -javaagent:\"%AGENT_JAR%\""
 rem )
 rem Prefer wildcard lookup for the agent jar so version changes don't break the script
 for %%A in ("%APP_HOME%\lib\agents\gradle-instrumentation-agent-*.jar") do set "AGENT_JAR=%%~fA"
 if defined AGENT_JAR (
     set "DEFAULT_JVM_OPTS=%DEFAULT_JVM_OPTS% -javaagent:\"%AGENT_JAR%\""
 )
 
 @rem Find java.exe
 if defined JAVA_HOME goto findJavaFromJavaHome
 
 set JAVA_EXE=java.exe
 %JAVA_EXE% -version >NUL 2>&1
 if %ERRORLEVEL% equ 0 goto execute
 
 echo. 1>&2
 echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH. 1>&2
 echo. 1>&2
 echo Please set the JAVA_HOME variable in your environment to match the 1>&2
 echo location of your Java installation. 1>&2
 
 goto fail
 
:findJavaFromJavaHome
 set JAVA_HOME=%JAVA_HOME:"=%
 set JAVA_EXE=%JAVA_HOME%\bin\java.exe
 
 if exist "%JAVA_EXE%" goto execute
 
 echo. 1>&2
 echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME% 1>&2
 echo. 1>&2
 echo Please set the JAVA_HOME variable in your environment to match the 1>&2
 echo location of your Java installation. 1>&2
 
 goto fail
 
:execute
 @rem Setup the command line
 
+rem If there is a gradle wrapper (gradlew.bat) in current dir or a parent, delegate to it.
+set "CURDIR=%START_DIR%"
+:find_wrapper
+if exist "%CURDIR%\gradlew.bat" (
+    rem Delegate to the wrapper found in the project tree
+    "%CURDIR%\gradlew.bat" %*
+    exit /b %ERRORLEVEL%
+)
+for %%p in ("%CURDIR%\..") do set "PARENT_DIR=%%~fp"
+if "%PARENT_DIR%"=="%CURDIR%" goto wrapper_not_found
+set "CURDIR=%PARENT_DIR%"
+goto find_wrapper
+:wrapper_not_found
+
+rem Autodetect Gradle CLI JAR (try APP_HOME lib, GRADLE_HOME lib, then PATH)
 set "GRADLE_CLI="
-for %%F in ("%APP_HOME%\lib\gradle-cli-*.jar") do if not defined GRADLE_CLI set "GRADLE_CLI=%%~fF"
-if not defined GRADLE_CLI for %%F in ("%APP_HOME%\lib\gradle-launcher-*.jar") do if not defined GRADLE_CLI set "GRADLE_CLI=%%~fF"
-if not defined GRADLE_CLI for %%F in ("%APP_HOME%\lib\gradle-*.jar") do if not defined GRADLE_CLI set "GRADLE_CLI=%%~fF"
-if not defined GRADLE_CLI set "GRADLE_CLI=%APP_HOME%\lib\gradle-gradle-cli-main-9.2.1.jar"
+for %%F in ("%APP_HOME%\lib\gradle-cli-*.jar") do if not defined GRADLE_CLI set "GRADLE_CLI=%%~fF"
+if not defined GRADLE_CLI for %%F in ("%APP_HOME%\lib\gradle-launcher-*.jar") do if not defined GRADLE_CLI set "GRADLE_CLI=%%~fF"
+if not defined GRADLE_CLI for %%F in ("%APP_HOME%\lib\gradle-*.jar") do if not defined GRADLE_CLI set "GRADLE_CLI=%%~fF"
+if not defined GRADLE_CLI (
+    rem Try GRADLE_HOME if set
+    if defined GRADLE_HOME (
+        for %%F in ("%GRADLE_HOME%\lib\gradle-cli-*.jar") do if not defined GRADLE_CLI set "GRADLE_CLI=%%~fF"
+        if not defined GRADLE_CLI for %%F in ("%GRADLE_HOME%\lib\gradle-launcher-*.jar") do if not defined GRADLE_CLI set "GRADLE_CLI=%%~fF"
+        if not defined GRADLE_CLI for %%F in ("%GRADLE_HOME%\lib\gradle-*.jar") do if not defined GRADLE_CLI set "GRADLE_CLI=%%~fF"
+    )
+)
 
-if not exist "%GRADLE_CLI%" (
-    echo. 1>&2
-    echo ERROR: Could not locate Gradle CLI jar. Checked: %GRADLE_CLI% 1>&2
-    echo. 1>&2
-    goto fail
-)
+if not defined GRADLE_CLI (
+    rem Fall back: is gradle available on PATH?
+    where gradle >NUL 2>&1
+    if %ERRORLEVEL% equ 0 (
+        rem Run gradle from PATH
+        call gradle %*
+        exit /b %ERRORLEVEL%
+    )
+    rem Nothing found: output a helpful error and fail
+    echo. 1>&2
+    echo ERROR: Could not locate Gradle CLI jar (searched APP_HOME, GRADLE_HOME) and gradle is not on PATH. 1>&2
+    echo. 1>&2
+    echo Please install Gradle, set GRADLE_HOME, add 'gradle' to PATH or ensure the wrapper 'gradlew.bat' is present in your project. 1>&2
+    echo. 1>&2
+    goto fail
+)
 
 if not exist "%GRADLE_CLI%" (
     echo. 1>&2
     echo ERROR: Could not locate Gradle CLI jar. Checked: %GRADLE_CLI% 1>&2
     echo. 1>&2
     goto fail
 )
 
 rem Run using the discovered CLI jar
 "%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -jar "%GRADLE_CLI%" %*
 
 :end
 @rem End local scope for the variables with windows NT shell
 if %ERRORLEVEL% equ 0 goto mainEnd
 
 :fail
 rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
 rem the _cmd.exe /c_ return code!
 set EXIT_CODE=%ERRORLEVEL%
 if %EXIT_CODE% equ 0 set EXIT_CODE=1
 if not ""=="%GRADLE_EXIT_CONSOLE%" exit %EXIT_CODE%
 exit /b %EXIT_CODE%
 
 :mainEnd
 if "%OS%"=="Windows_NT" endlocal
 
 :omega

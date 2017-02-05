# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

add_library(opencv-core STATIC IMPORTED)
set_target_properties(opencv-core PROPERTIES IMPORTED_LOCATION ${CMAKE_HOME_DIRECTORY}/libs/${ANDROID_ABI}/libopencv_core.a)
include_directories(${CMAKE_HOME_DIRECTORY}/include)

add_library(opencv-highgui STATIC IMPORTED)
set_target_properties(opencv-highgui PROPERTIES IMPORTED_LOCATION ${CMAKE_HOME_DIRECTORY}/libs/${ANDROID_ABI}/libopencv_highgui.a)

add_library(opencv-imgproc STATIC IMPORTED)
set_target_properties(opencv-imgproc PROPERTIES IMPORTED_LOCATION ${CMAKE_HOME_DIRECTORY}/libs/${ANDROID_ABI}/libopencv_imgproc.a)

add_library(opencv-imgcodecs STATIC IMPORTED)
set_target_properties(opencv-imgcodecs PROPERTIES IMPORTED_LOCATION ${CMAKE_HOME_DIRECTORY}/libs/${ANDROID_ABI}/libopencv_imgcodecs.a)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds it for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             # Associated headers in the same location as their source
             # file are automatically included.
             src/main/cpp/native-lib.cpp
             src/main/cpp/NativeImageBuffer.cpp
             src/main/cpp/events/SetBackgroundEvent.cpp
             src/main/cpp/events/SetDisplaySizeEvent.cpp
             src/main/cpp/renderlib/RenderService.cpp)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because system libraries are included in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

find_library(z-lib z)
find_library(jpg-lib jpeg)
find_library(png-lib png)
find_library(tbb-lib tbb)
find_library(tiff-lib tiff)
find_library(jasper-lib jasper)
find_library(ilm-lib IlmImf)


# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in the
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib}
                       ${z-lib}
                       ${jpg-lib}
                       ${png-lib}
                       ${tiff-lib}
                       ${tbb-lib}
                       ${jasper-lib}
                       ${ilm-lib}
                       opencv-core
                       opencv-highgui
                       opencv-imgproc
                       opencv-imgcodecs)

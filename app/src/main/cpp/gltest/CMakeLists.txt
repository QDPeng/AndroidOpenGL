
cmake_minimum_required(VERSION 3.4.1)

# build native_app_glue as a static lib
add_library(glue STATIC
            ${ANDROID_NDK}/sources/android/native_app_glue/android_native_app_glue.c)

# Set common compiler options
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11 -Wall")
add_definitions("-DGLM_FORCE_SIZE_T_LENGTH -DGLM_FORCE_RADIANS")

# Import the CMakeLists.txt for the glm library
add_subdirectory(glm)

# now build app's shared lib
add_library(gltest SHARED
            android_main.cpp
            )

target_include_directories(gltest PRIVATE
                           ${CMAKE_CURRENT_SOURCE_DIR}
                           #${CMAKE_CURRENT_SOURCE_DIR}/data
                           ${ANDROID_NDK}/sources/android/native_app_glue)

# add lib dependencies
target_link_libraries(gltest
                      android
                      glue
                      atomic
                      EGL
                      GLESv2
                      glm-test
                      log
                      OpenSLES)

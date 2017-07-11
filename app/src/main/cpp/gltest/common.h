//
// Created by peng on 2017/7/11.
//

#ifndef ANDROIDOPENGL_COMMON_H
#define ANDROIDOPENGL_COMMON_H
extern "C" {
#include <EGL/egl.h>
#include <GLES2/gl2.h>
#include <jni.h>
#include <errno.h>
#include <android/sensor.h>
#include <android/log.h>
#include <android_native_app_glue.h>
#include <unistd.h>
#include <stdlib.h>
}
#include "glm/glm.hpp"
#include "glm/gtc/type_ptr.hpp"
#include "glm/gtc/matrix_transform.hpp"
#define DEBUG_TAG "GL_Test"
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, DEBUG_TAG, __VA_ARGS__))
#define BUFFER_OFFSET(i) ((char*)NULL + (i))
// Our keycodes:
#define OURKEY_UP 0
#define OURKEY_RIGHT 1
#define OURKEY_DOWN 2
#define OURKEY_LEFT 3
#define OURKEY_ENTER 4
#define OURKEY_ESCAPE 5
#define OURKEY_COUNT 6 // how many keycodes there are

#endif //ANDROIDOPENGL_COMMON_H

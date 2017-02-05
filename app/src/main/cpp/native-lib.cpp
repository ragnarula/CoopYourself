#include <jni.h>
#include <string>
#include "NativeImageBuffer.h"
#include "renderlib/RenderService.h"
#include "handle.h"

extern "C"
jstring
Java_com_lostwatchtheatre_coopyourself_WelcomeActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}



JNIEXPORT void JNICALL
Java_com_lostwatchtheatre_coopyourself_ImageBuffer_init(JNIEnv *env, jobject instance) {
    NativeImageBuffer *native_inst = new NativeImageBuffer(env, instance);
    setHandle(env, instance, native_inst);
}

JNIEXPORT void JNICALL
Java_com_lostwatchtheatre_coopyourself_RenderService_init(JNIEnv *env, jobject instance,
                                                          jobject imageBufferView) {
    NativeImageBuffer *nativeImageBuffer = getHandle<NativeImageBuffer>(env, imageBufferView);
    RenderService *renderService = new RenderService(nativeImageBuffer);
    setHandle(env, instance, renderService);
}

JNIEXPORT void JNICALL
Java_com_lostwatchtheatre_coopyourself_RenderService_setBackground(JNIEnv *env, jobject instance,
                                                                   jstring path_) {
    const std::string path(env->GetStringUTFChars(path_, 0));
    RenderService *renderService = getHandle<RenderService>(env, instance);
    renderService->setBackground(std::string(path));
    env->ReleaseStringUTFChars(path_, env->GetStringUTFChars(path_, 0));
}

JNIEXPORT void JNICALL
Java_com_lostwatchtheatre_coopyourself_RenderService_setDisplaySize(JNIEnv *env, jobject instance,
                                                                    jint w, jint h) {

    RenderService *renderService = getHandle<RenderService>(env, instance);
    renderService->setDisplaySize((int) w, (int) h);
}
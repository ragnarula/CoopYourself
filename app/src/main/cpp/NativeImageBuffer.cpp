//
// Created by Raghav Narula on 24/01/2017.
//

#include "NativeImageBuffer.h"

NativeImageBuffer::NativeImageBuffer(JNIEnv * env, jobject obj) : env(env), obj(obj) {
    clazz = env->GetObjectClass(obj);
    allocateMethod = env->GetMethodID(clazz,"allocate","(I;I;)Z");
    getBufferMethod = env->GetMethodID(clazz, "getBuffer","()Ljava/nio/ByteBuffer;");
    onFrameReadyMethod = env->GetMethodID(clazz,"onFrameReady","()V");
}

bool NativeImageBuffer::allocate(int w, int h) {
    jboolean ok = env->CallBooleanMethod(obj, allocateMethod, (jint) w, (jint) h);
    return ok;
}

uint8_t *NativeImageBuffer::getBuffer() {
    jobject bufferObject = env->CallObjectMethod(obj, getBufferMethod);
    jbyte* jbuffer = (jbyte*)env->GetDirectBufferAddress(bufferObject);
    return (uint8_t*) jbuffer;
}

void NativeImageBuffer::onFrameReady() {
    env->CallVoidMethod(obj, onFrameReadyMethod);
}






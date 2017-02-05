//
// Created by Raghav Narula on 24/01/2017.
//

#ifndef COOPYOURSELF_NATIVEBUFFER_H
#define COOPYOURSELF_NATIVEBUFFER_H


#include <jni.h>
#include <cstdint>
#include "renderlib/ImageBuffer.h"

class NativeImageBuffer : public ImageBuffer {
    JNIEnv * env;
    jobject obj;
    jclass clazz;

    jmethodID allocateMethod;
    jmethodID getBufferMethod;
    jmethodID onFrameReadyMethod;

public:
    NativeImageBuffer(JNIEnv * env, jobject obj);
    virtual ~NativeImageBuffer(){};
    virtual bool allocate(int w, int h);
    virtual uint8_t * getBuffer();
    virtual void onFrameReady();
};


#endif //COOPYOURSELF_NATIVERENDERSERVICE_H

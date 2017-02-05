//
// Created by Raghav Narula on 24/01/2017.
//

#ifndef COOPYOURSELF_IMAGEBUFFER_H
#define COOPYOURSELF_IMAGEBUFFER_H

#include <cstdint>

class ImageBuffer {
public:
    virtual ~ImageBuffer(){};
    virtual bool allocate(int w, int h) = 0;
    virtual uint8_t * getBuffer() = 0;
    virtual void onFrameReady() = 0;
};
#endif //COOPYOURSELF_IMAGEBUFFER_H

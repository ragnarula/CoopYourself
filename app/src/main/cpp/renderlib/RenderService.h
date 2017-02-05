//
// Created by Raghav Narula on 23/01/2017.
//

#ifndef COOPYOURSELF_COOPIMAGEEDITOR_H
#define COOPYOURSELF_COOPIMAGEEDITOR_H

#include <string>
#include <vector>
#include <queue>
#include <mutex>
#include "Dots.h"
#include "opencv2/core/mat.hpp"
#include "../events/SetBackgroundEvent.h"
#include "../events/SetDisplaySizeEvent.h"
#include "ImageBuffer.h"

class RenderService {
    volatile bool running;
    volatile int eventCounter;
    cv::Mat bg;
    std::mutex lock;
    std::queue<SetBackgroundEvent> backgroundEvents;
    std::queue<SetDisplaySizeEvent> displayEvents;

    ImageBuffer *buffer;

public:
    RenderService(ImageBuffer *buffer) : running(true), eventCounter(0), buffer(buffer){};
    void setBackground(const std::string& bg);
    void setDisplaySize(int x, int y);
    void run();
};


#endif //COOPYOURSELF_COOPIMAGEEDITOR_H

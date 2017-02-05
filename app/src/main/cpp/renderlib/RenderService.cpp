//
// Created by Raghav Narula on 23/01/2017.
//
#include "RenderService.h"
#include "opencv2/core.hpp"
#include "opencv2/highgui.hpp"
#include "opencv2/imgproc.hpp"

using namespace std;
using namespace cv;
void RenderService::setBackground(const std::string &bg) {
    lock.lock();
    eventCounter++;
    backgroundEvents.emplace(SetBackgroundEvent(bg));
    this->bg = imread(bg, CV_LOAD_IMAGE_COLOR);
    lock.unlock();
}

void RenderService::setDisplaySize(int x, int y) {
    lock.lock();
    eventCounter++;
    displayEvents.emplace(SetDisplaySizeEvent(x, y));
    Size s(x, y);
    Mat out;
    resize(this->bg, out, s);
    if(buffer->allocate(x, y)){
        uint8_t * pixels = buffer->getBuffer();
        memcpy(pixels, out.data, x * y * 4);
        buffer->onFrameReady();
    }
    lock.unlock();
}

void RenderService::run() {
    queue<SetBackgroundEvent> newBgEvents;
    queue<SetDisplaySizeEvent> newDispEvents;
    while(running){
        lock.lock();
        if(eventCounter > 0){
            if(!backgroundEvents.empty()){
                SetBackgroundEvent e = backgroundEvents.front();
                backgroundEvents.pop();
                newBgEvents.push(e);
                eventCounter--;
            }
            if(!displayEvents.empty()){
                SetDisplaySizeEvent e = displayEvents.front();
                displayEvents.pop();
                newDispEvents.push(e);
                eventCounter--;
            }
        }
        lock.unlock();

    }
}






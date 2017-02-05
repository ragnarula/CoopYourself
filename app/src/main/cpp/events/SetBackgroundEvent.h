//
// Created by Raghav Narula on 24/01/2017.
//

#ifndef COOPYOURSELF_SETBACKGROUNDEVENT_H
#define COOPYOURSELF_SETBACKGROUNDEVENT_H


#include <string>

class SetBackgroundEvent {
const std::string path;
public:

    SetBackgroundEvent(const std::string &path) : path(path) { }

    const std::string &getPath() const {
        return path;
    }
};


#endif //COOPYOURSELF_SETBACKGROUNDEVENT_H

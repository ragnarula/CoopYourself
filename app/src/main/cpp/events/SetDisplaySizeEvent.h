//
// Created by Raghav Narula on 24/01/2017.
//

#ifndef COOPYOURSELF_SETDISPLAYSIZEEVENT_H
#define COOPYOURSELF_SETDISPLAYSIZEEVENT_H


class SetDisplaySizeEvent {
    int x;
    int y;
public:

    SetDisplaySizeEvent(int x, int y) : x(x), y(y) { }

    int getX() const {
        return x;
    }

    int getY() const {
        return y;
    }
};


#endif //COOPYOURSELF_SETDISPLAYSIZEEVENT_H

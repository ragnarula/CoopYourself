//
// Created by Raghav Narula on 23/01/2017.
//

#ifndef COOPYOURSELF_DOTS_H
#define COOPYOURSELF_DOTS_H


class Dots {
    int x;
    int y;
    int w;
    int h;
    float r;
    float s;
public:
    Dots(int x, int y, int w = 140, int h = 60, double r = 0.0, double s = 1.0) : x(x), y(y), w(w), h(h), r(r), s(s) { }

    int getX() const {
        return x;
    }

    void setX(int x) {
        Dots::x = x;
    }

    int getY() const {
        return y;
    }

    void setY(int y) {
        Dots::y = y;
    }

    int getW() const {
        return w;
    }

    void setW(int w) {
        Dots::w = w;
    }

    int getH() const {
        return h;
    }

    void setH(int h) {
        Dots::h = h;
    }

    float getR() const {
        return r;
    }

    void setR(float r) {
        Dots::r = r;
    }

    float getS() const {
        return s;
    }

    void setS(float s) {
        Dots::s = s;
    }
};


#endif //COOPYOURSELF_DOTS_H

#include "Contour/TScene.h"
#include "Contour/AutoRecognition.h"

#define CV_PIXEL(buff, width, step, x, y, a) ((char) (buff + y * width))+ x * a)

    struct Rectangle {
        int max_y;
        int min_y;
        int max_x;
        int min_x;
    } *rects2;

    unsigned char *ppix;
    TScene *Scene;
    int numer_etalon;
    Param_Recogn Param_Rec;
    char NameFileEtalonScene;
    int W_H0 = 10;
    int step;
    int Width;


    int OtsuThreshold(unsigned char *image, int size);

    int BinarizationImage(unsigned char *pPixels, int W_H, int threshold);

    int buff_to_ppix(unsigned char *buff, int W_H0);

    int Recognition_area(unsigned char *buff, int w0,int h0) ;


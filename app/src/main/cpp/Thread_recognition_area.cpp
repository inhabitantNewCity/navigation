//---------------------------------------------------------------------------

#pragma hdrstop

#include "Thread_recognition_area.h"
#include "Contour/TShtrihPicture.h"
#include "Contour/TScene.h"
#include "Contour/ContourModel.h"
#include "Contour/AutoRecognition.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)

//---------------------------------------------------------------------------
int OtsuThreshold(unsigned char *image, int size)
{
   int min = image[0];
   int max = image[0];
   for (int i = 1; i < size; i++)
   {
    int value = image[i];
    if (value < min)
     min = value;
    if (value > max)
     max = value;
   }
   int histSize = max - min + 1;
   int* hist = new int[histSize];
   for (int t = 0; t < histSize; t++)
     hist[t] = 0;
   for (int i = 0; i < size; i++)
     hist[image[i] - min]++;
   int m = 0;
   int n = 0;
   for (int t = 0; t <= max - min; t++)
   {
    m += t * hist[t];
    n += hist[t];
   }
   float maxSigma = -1;
   int threshold = 0;
   int alpha1 = 0;
   int beta1 = 0;
   for (int t = 0; t < max - min; t++)
   {
    alpha1 += t * hist[t];
    beta1 += hist[t];
    float w1 = (float)beta1 / n;
    // a = a1 - a2, ??? a1, a2 - ??????? ?????????????? ??? ??????? 1 ? 2
    float a = (float)alpha1 / beta1 - (float)(m - alpha1) / (n - beta1);
    // ???????, ??????? sigma
    float sigma = w1 * (1 - w1) * a * a;
    if (sigma > maxSigma)
    {
     maxSigma = sigma;
     threshold = t;
    }
   }
   threshold += min;
   return threshold;
}
//---------------------------------------------------------------------------
int  BinarizationImage (unsigned char *pPixels, int W_H, int threshold)
 {
            for (int vi = 0; vi < W_H; vi++)
	    {
             if (pPixels [vi] <= threshold)
				{
				  pPixels[vi] = 1;

				}
				else
				{
				  pPixels[vi] = 0;
				}
            }
		return 0;
 }
//---------------------------------------------------------------------------
int buff_to_ppix(unsigned char *buff,int W_H0)
{
 unsigned char grayL;
 int step_vi;
 ppix = new unsigned char [W_H0+1];
 int i0 = -1;
 for (int y = rects2->max_y-1; y >= rects2->min_y; y--)
 for (int x = rects2->min_x; x < rects2->max_x; x++)
 {
  i0++;
  //grayL = (char)(0.2125 * CV_PIXEL(buff, Width, step, x, y, 0) + 0.7154 * CV_PIXEL(buff, Width, step, x, y, 1) + 0.0721 * CV_PIXEL(buff,Width,step,x,y,2));
     grayL = 0;
     ppix[i0] = grayL;
  step_vi = step*i0;
 }
}
//---------------------------------------------------------------------------
int Recognition_area(unsigned char *buff, int w0,int h0) // ,int rects_min_x,int rects_min_y, int rects_max_x,int rects_max_y
{
 int threshold = 0;
 double dt;
 char NameFileBMP_0[256];
 int im7=0;

 im7++;
 //-------------------------------------------------------------
 threshold = OtsuThreshold (buff, W_H0);

 BinarizationImage (buff, W_H0, threshold);


  //-------------------------------------------------------------
 Scene = new TScene ();

      TShtrihPicture *shtrpic = new TShtrihPicture (w0,h0);

	  shtrpic->ConvertToShtrih (buff);

	  FindListContour  (shtrpic, Scene);
        char NameFileEtalonScene[5];
   	  numer_etalon = AutoRecognition (Scene, NameFileEtalonScene, NULL,128,128,false, false, Param_Rec);

  delete shtrpic;
  delete Scene;
  delete []ppix;
  return 1;
}
//---------------------------------------------------------------------------------------------


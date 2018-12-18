#ifndef Auto_Rec
#define Auto_Rec
#pragma once
#include "TContour.h"
#include "TScene.h"
//#include "b_file_size.h"
struct ParmAffineEtal
{
	int Ne,Np,Inet;
	unsigned long Adr;
	double Sq,Sp,Qm,Em,Xn,Yn,Xk,Yk,Xc,Yc,Se,DW,DX,DY,RXY;
};
struct RecognizedObject
{
	int No, Num[10];
	double Xc[10],Yc[10],gam[10],bet[10];
};
struct ObjToEtal
{
 int Num,n;
 unsigned long Xmin,Ymin,Xmax,Ymax;
};   // EtalImage[4]
struct Param_Recogn
{
 int N;
 double gam[10],bet[10]; // ,Xc[10],Yc[10]
};
struct ParmAffine
{
 int Np; double Sp,betta,gamma,DWo,Mxoxo,Myoyo,Mxoyo,
	  DW,Mxx,Myy,Mxy, Mxox,Mxoy,Myox,Myoy,SnQ,CsQ,KQ,Re,erm;
};
int AutoRecognition(TScene *SceneIn, char NameFileEtalon[],long *AllKmpObj, double stepxin, double stepyin,bool useKmpObj,bool thread,Param_Recogn &Param_Rec);
//int Read_etalon(char NameFileEtalon[],long *AllKmpObj, double stepxin, double stepyin,bool useKmpObj);
//int recognition_scene(TScene *SceneIn);
int Save_KML (char* sFileNameEtal, char* sFileNameKM, double stepxin, double stepyin);
#endif
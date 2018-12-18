//#include "AutoRecognition.h"
//============ формирование описания эталона и вычисление моментов ==========
//------------ входные данные ---------
// NumEtal - текущий номер эталона
// ParmEtalon -структура описания параметров эталонного контура
// Xo[],Yo[] - метрическое описание эталона
//------------ выходные данные ---------
// So[] - длины ребер эталонного многоугольника 
// ParmEtalon -структура описания с вычесленными параметрами эталона
// Adr - адрес записи метрического описания следующего эталона
//---------------------------------------------------------------------------
#pragma warn -pck
#pragma option -a1
//---------------------------------------------------------------------------
struct ParmEtalon {int No,Np; unsigned long Adr;
						  double Sp,Se,Xoc,Yoc,Dw,Dx,Dy,Rxy;};
#include <math.h>
void GetDescribeMomentsEtalons(int CurEtal,ParmEtalon *ParmEtals,double *Xo,double *Yo,double *So)
  {
  int j,N;
  unsigned long Adr;
  double Dx,Dy,Vx,Vy,Vxx,Vyy,Sj,St,Se,*XTo,*YTo,*STo;
  double Xoc,Yoc,Mxoxo,Myoyo,Mxoyo;

  N=ParmEtals[CurEtal].No;
  Adr=ParmEtals[CurEtal].Adr;
  XTo=&Xo[Adr];  YTo=&Yo[Adr]; STo=&So[Adr];
  for(j=0; j<N-1; j++)
    {
    Dx=XTo[j+1]-XTo[j];
    Dy=YTo[j+1]-YTo[j];
    Sj=sqrt(Dx*Dx+Dy*Dy);
    STo[j]=Sj;
    Se+=Sj;
    }

  Xoc=0.;     Yoc=0.;
  Vx=XTo[0];   Vy=YTo[0];   Sj=STo[0];
  for(j=1; j<N; j++)
    {
    Vxx=XTo[j];  Vyy=YTo[j];
    Xoc+=(Vx+Vxx)*Sj;
    Yoc+=(Vy+Vyy)*Sj;
    Sj=STo[j];
    Vx=Vxx;   Vy=Vyy;
    }
  St=Se+Se;
  Xoc/=St;   Yoc/=St;
  
  ParmEtals[CurEtal].Se=Se;
  ParmEtals[CurEtal].Xoc=Xoc;
  ParmEtals[CurEtal].Yoc=Yoc;
//  XNoc=Xoc+XEmin; YNoc=Yoc+YEmin;

 Mxoxo=0.;  Myoyo=0.;  Mxoyo=0.;
  Vx=XTo[0];  Vy=YTo[0];  Sj=STo[0];
  for(j=1; j<N; j++)
    {
    Vxx=XTo[j];  Vyy=YTo[j];
    Mxoxo+=(Vx*Vx+Vxx*Vxx+Vx*Vxx)*Sj;
    Myoyo+=(Vy*Vy+Vyy*Vyy+Vy*Vyy)*Sj;
    Mxoyo+=(2*(Vx*Vy+Vxx*Vyy)+Vx*Vyy+Vxx*Vy)*Sj;
    Vx=Vxx;  Vy=Vyy;  Sj=STo[j];
    }
  St+=Se;
  Mxoxo/=St;   Myoyo/=St;    Mxoyo/=(St+St);

  Mxoxo-=(Xoc*Xoc);   Myoyo-=(Yoc*Yoc);   Mxoyo-=(Xoc*Yoc);

  ParmEtals[CurEtal].Dw=Mxoxo+Myoyo;
  ParmEtals[CurEtal].Dx=Mxoxo;
  ParmEtals[CurEtal].Dy=Myoyo;
  ParmEtals[CurEtal].Rxy=Mxoyo;

  return;
  }
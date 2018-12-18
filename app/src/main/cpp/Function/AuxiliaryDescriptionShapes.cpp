#include "AutoRecognition.h"
//============ формирование вспомогательных описаний ==========
//------------ входные данные ---------
// NumEtal - текущий номер эталона
// ParmEtalon -структура описания параметров эталонного контура
// Xo[],Yo[],So[] - метрическое описание эталона
// Xd[],Yd[],Sg[] - метрическое описание объекта (Sg[]=Sd[]*(Se/Sr_pr))
//------------ выходные данные ---------
// XXo[],YYo[], Ct[] - вспомогательное метрическое описание эталона
// XX[],YY[], Ct[] - вспомогательное метрическое описание объекта
// NM - количество точек в вспомогательных описаниях
//---------------------------------------------------------------------------
#pragma warn -pck
#pragma option -a1
//---------------------------------------------------------------------------
// extern
struct ParmEtalon {int No,Np; unsigned long Adr;
						  double Sp,Se,Xoc,Yoc,Dw,Dx,Dy,Rxy;};
int AuxiliaryDescriptionShapes(int NumEtal,ParmEtalon *ParmEtals,double *Xo,double *Yo,double *So,double *Xd,double *Yd,double *Sg,double *XXo,double *YYo,double *XX,double *YY,double *Ct)
  {
  #define EPS 1.e-5
  int in,jn,NM,nt,jt,ii,Ne,Adr;
  double sp,spo,sg,*XTo,*YTo,*STo;

  Ne=ParmEtals[NumEtal].No;
  Adr=ParmEtals[NumEtal].Adr;
  XTo=&Xo[Adr]; YTo=&Yo[Adr]; STo=&So[Adr];
  in=1; jn=1; NM=1;
  sp=Sg[0];
  XXo[0]=XTo[0]; YYo[0]=YTo[0];
  XX[0]=Xd[0];  YY[0]=Yd[0];
  spo=STo[0];
  while (in<Ne)
    { ii=in%Ne;
    if(sp<EPS) { sp+=Sg[jn]; jn++; }
    if(spo>=EPS)
      {
      if(spo<=sp)
        { nt=NM-1;
		XXo[NM]=XTo[ii]; YYo[NM]=YTo[ii];
        jt=jn; sg=spo/sp;
        XX[NM]=XX[nt]*(1.-sg)+Xd[jt]*sg;
        YY[NM]=YY[nt]*(1.-sg)+Yd[jt]*sg;
        Ct[nt]=spo; sp-=spo; spo=STo[ii];
        NM++; in++;
        }
      else
        { nt=NM-1; jt=jn;
        XX[NM]=Xd[jt]; YY[NM]=Yd[jt];
        sg=sp/spo;
        XXo[NM]=XXo[nt]*(1.-sg)+XTo[ii]*sg;
        YYo[NM]=YYo[nt]*(1.-sg)+YTo[ii]*sg;
        Ct[nt]=sp; spo-=sp; sp=Sg[jt];
        NM++; jn++;
          }
        }
      else { spo+=STo[ii]; in++; }
      }
    return(NM);
    }

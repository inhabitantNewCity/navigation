//==========  вычисление оценки близости  ============
//------------ входные данные ---------
// Nt - количество точек в вспомогательных описаниях контуров
// XXo[],YYo[], Ct[] - вспомогательное метрическое описание эталона
// XX[],YY[], Ct[] - вспомогательное метрическое описание объекта
// Se - длина контура эталона
//------------ выходные данные ---------
//  RXXo,RXYo,RYYo,RYXo - корреляционные моменты cov(Xo,X),cov(Yo,X),
//                                                   cov(Yo,Y),cov(Xo,Y) соответственно
// sn,cs - мастабированные тригонометрические функции
// re - оценка близости (среднекваднатическое отклонение при наложении контуров)
//---------------------------------------------------------------------------
#pragma warn -pck
#pragma option -a1
//---------------------------------------------------------------------------
struct ParmEtalon {int No,Np; unsigned long Adr;
						  double Sp,Se,Xoc,Yoc,Dw,Dx,Dy,Rxy;};
void matching(int Nt,int NumEtal,ParmEtalon *ParmEtals,double RXXo,double RXYo,double RYYo,double RYXo,double sn,double cs,double  re,double *XXo,double *YYo,double *XX,double *YY,double *Ct)
  {
  double px,ppx,py,ppy,xp,yp,zx,zy,zzx,zzy,xz,yz,sd,Se,Se6,st;
  int j;
  Se=ParmEtals[NumEtal].Se;
  Se6=Se*6.;
  px=XXo[0]; py=YYo[0];
//  zx=X[0]; zy=Y[0];
  zx=0.; zy=0.;
  RXYo=0.; RYYo=0.; RXXo=0.; RYXo=0.;

  for(j=1; j<Nt; j++)
    { ppx=XXo[j]; ppy=YYo[j]; st=Ct[j-1];
    xp=px+ppx; yp=py+ppy;
    zzx=XX[j]-XX[0]; zzy=YY[j]-YY[0];
    xz=zx+zzx; yz=zy+zzy;
    sd=xp*xz; sd=sd+sd;
    RXXo+=(sd-px*zzx-ppx*zx)*st;
    sd=xp*yz; sd=sd+sd;
    RYXo+=(sd-px*zzy-ppx*zy)*st;
    sd=yp*xz; sd=sd+sd;
    RXYo+=(sd-py*zzx-ppy*zx)*st;
    sd=yp*yz; sd=sd+sd;
    RYYo+=(sd-py*zzy-ppy*zy)*st;
    px=ppx; py=ppy;
    zx=zzx; zy=zzy;
    }
  RXXo/=Se6; RYYo/=Se6;
  RYXo/=Se6; RXYo/=Se6;
  sn=(RYXo-RXYo);
  cs=(RXXo+RYYo);
  re= sn*sn+cs*cs;
  return;
  }

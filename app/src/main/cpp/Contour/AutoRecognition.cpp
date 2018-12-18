#include "AutoRecognition.h"
// #include "Similarities_Thread.h"
#include "TPoint.h"
// #include "TContour.h"
#include "data.h"
//#include "../realize/longMinv.cpp"
//#include "../realize/CompressPointsDescribe.cpp"
//#include "../realize/data.h"
#define M_PI 3.14
// #include <vcl/inifiles.hpp>
#define M_PI 3.14
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <time.h>
#include <fcntl.h>
#include <sys\stat.h>
#include <iostream>
// #include <Classes.hpp>
//#include <System.Classes.hpp>
//#include "work_file.cpp"
 #ifdef _WIN32
//#include <vcl/inifiles.hpp>
   #include <io.h>
   #include <excpt.h>
   #include <Windows.h>
 #endif //_WIN32
//--------------------------------------------------------------------------
//---------------------------------------------------------------------------
int Error_View(char* Title,char* Message)
{
 #ifdef _WIN32
 char Error_text[250];
 sprintf(Error_text,Message);
 MessageBoxA(0,LPCSTR(Error_text),LPCSTR(Title),MB_OK);
 #endif
 return 0;
}
//---------------------------------------------------------------------------------------------------------
int CompressPointsDescribe(long  *Pin, int N, int d, long *Pout);
void longMinv(long double *a, int n, long double *det, int *l, int *m);

// bool SelectData;
// int mPT;
//? long double *V,detV,Px,Py,Zt,Mpg[64],B[8],A[8],PXoYo[20],PXeYe[20];
//double Scl,Mxx,Myy,Mxy
// double Xc,Yc,Xoc,Yoc; // ,Sj,sg,st,Sp,Em

//? struct DataRecObj *DiscreteObjects;
 // int Np;,Nt
// double sn,cs;
 //double See; re,SE1,SimPor
 //? Np_opt int gam_n,bet_n,d_gam,d_bet,i_cor;



//---------------------------------------------------------------------
//======= ������ �������������� ��������� �������� ������� =======
//------------ ������� ������ ---------
// N - �������� ���������� ����� � �������� �������
// Pin - �������� �������� ������� (������ �����)
//------------ �������� ������ ---------
// Pout - �������� ������� ����� ������ (������ �����)
// m - ���������� ����� � �������� ������� ����� ������
//---------------------------------------------------------------------------
#pragma warn -pck
#pragma option -a1
//---------------------------------------------------------------------------
int CompressDescribeEtalon(long  *Pin, int N, int d, long *Pout)
  {
  long xn,yn,xk,yk,xz,yz,xp,yp;
  int m,j,k,i,jt,jp,jk,mp;
  long xt,yt,d2,e,s,st,rn,rk;
  __int64 e2,p;
  bool Ibreak;
  union {long XY; unsigned short Ptr[2];} CR;
  
  CR.XY=Pin[0];
  xn=CR.Ptr[0]; yn=CR.Ptr[1];
//  xn=Pin[0]; yn=Pin[1];
//  Pout[0]=xn;   Pout[1]=yn;
  CR.Ptr[0]=xn; CR.Ptr[1]=yn;
  Pout[0]=CR.XY;
  if(N==1) return(N);

  m=1; i=1; k=0; j=1;
  d2=(long)d*d;
  for(;j<N;)
    {
//    j2=j+j;
    CR.XY=Pin[j]; xk=CR.Ptr[0]; yk=CR.Ptr[1];  Ibreak=false;
    if(k>0)
      {
      xp=xk-xn; yp=yk-yn;
      s=(xp*xp+yp*yp);
      p=(__int64)s; p*=d2;
      for(jt=0; jt<k; jt++)
		{ jp=i+jt; //jp2=jp+jp;
        CR.XY=Pin[jp]; xt=CR.Ptr[0]-xn; yt=CR.Ptr[1]-yn;
//        xt=Pin[jp2++]-xn; yt=Pin[jp2]-yn;
        e=xt*yp-yt*xp;
        e2=(__int64)e*(__int64)e;
        if(e2>p) Ibreak=true;
        else
          { st=xp*xt+yp*yt;
          if(st<=s && st>0) continue;
          else
            {
            if(st<=0)
              { rn=xt*xt+yt*yt;
              if(rn<=d2) continue;
              else Ibreak=true;
              }
            else
              { xt-=xp; yt-=yp;
              rk=xt*xt+yt*yt;
              if(rk<=d2) continue;
              else Ibreak=true;
              }
            }
          }
        if(Ibreak==true) break;
        }
      }
    if(Ibreak==false) { k++; j++; xz=xk; yz=yk; continue;}
    else
      { i+=k; xn=xz; yn=yz;   // mp=m+m;
      CR.Ptr[0]=xz; CR.Ptr[1]=yz; Pout[m]=CR.XY;
//      Pout[mp]=xz; Pout[mp+1]=yz;
      k=0; m++; j=i;
      }
    }
  CR.Ptr[0]=xz; CR.Ptr[1]=yz;  Pout[m]=CR.XY;
//  mp=m+m; Pout[mp]=xz; Pout[mp+1]=yz;
  m++;

  if(m==2)
    {
    if(Pout[0]==Pout[1]) m=1;
//    if(Pout[0]==Pout[2]&&Pout[1]==Pout[3]) m=1;
    }

  return (m);
  }

//---------------------------------------------------------------------
//==========  ������������ �������� �������� �������  ==========
//------------ ������� ������ ---------
//  N - ���������� ����� � �������� �������� ������� (P:N=P:1);  N1=N-1
// Np, Sp - ����� ����� � � �������� �� ��� ��� ��������� �������������� ���������
//                ����� � ������������ �������� ��������  �������
// Se[Adr],Ye[Adr],Se[Adr] - �������� �������� �������
// See - ����� �������
//------------ �������� ������ ---------
// Xd[],Yd[],Sd[] - ����������� ������� ��������  �������
// n -  ���������� ����� � ����������� ������� �������� �������
//---------------------------------------------------------------------------
#pragma warn -pck
#pragma option -a1
//---------------------------------------------------------------------------
int CurrentDecribeEtalon(int CurEtal,int Np, double &Sp,ParmAffineEtal *ParmAffineEtals,double *Xe, double *Ye, double *Se,double *Xd,double *Yd,double *Sd)
  {    // ParmAffineEtals  Xd Yd Sd
  int i,j,n,i1,i2,N1,Adr;
  double sg,Sfr,st,*XTo,*YTo,*STo,See;

  Adr=ParmAffineEtals[CurEtal].Adr;
  XTo=&Xe[Adr]; YTo=&Ye[Adr]; STo=&Se[Adr];
  N1=ParmAffineEtals[CurEtal].Ne-1;
  See=ParmAffineEtals[CurEtal].Se;

  if(STo[Np-1]<Sp) return(-1);
  i=Np;
  i2=i%N1; i1=(i-1)%N1;
  sg=Sp/STo[i1];
  Xd[0]=XTo[i1]*(1.-sg)+XTo[i2]*sg;
  Yd[0]=YTo[i1]*(1.-sg)+YTo[i2]*sg;
  Sfr=STo[i1]-Sp;
  st=Sfr;
  n=1;
  for(;;)
	{ i2=i%N1; i1=(i-1)%N1;
    if(Sfr>=See)
      {
      sg=Sfr-See;
      Sd[n-1]=st-sg;
      sg/=st;
	  Xd[n]=XTo[i1]*sg+XTo[i2]*(1.-sg);
      Yd[n]=YTo[i1]*sg+YTo[i2]*(1.-sg);
      n++;
      break;
      }

	Xd[n]=XTo[i2]; Yd[n]=YTo[i2]; Sd[n-1]=st;
    st=STo[i2]; Sfr+=st;
    i++; n++;
	}

  return(n);
  }
//---------------------------------------------------------------------
//============ ������������ ��������������� �������� ==========
//------------ ������� ������ ---------
// CurEtal - ������� ����� �������
// ParmEtalon -��������� �������� ���������� ���������� �������
// Xb[],Yb[],Sb[] - ����������� �������� �������� �������
// Xd[],Yd[],Sd[] - ����������� �������� �������
//------------ �������� ������ ---------
// XXo[],YYo[], Ct[] - ��������������� ����������� �������� �������
// XX[],YY[], Ct[] - ��������������� ����������� �������� �������
// NM - ���������� ����� � ��������������� ���������
//---------------------------------------------------------------------------
#pragma warn -pck
#pragma option -a1
//---------------------------------------------------------------------------
int AuxiliaryDescriptionBase(int CurEtal,double *Xb,double *Yb,double *Sb,double *Xd,double *Yd,double *Sd,double *XXo,double *YYo,double *XX,double *YY,double *Ct,int Nb)
  {
  #define EPS 1.e-5
  int in,jn,NM,nt,jt,ii,Ne,Adr;
  double sp,spo,sg;

  in=1; jn=1; NM=1;
  sp=Sd[0];
  XXo[0]=Xb[0]; YYo[0]=Yb[0];
  XX[0]=Xd[0];  YY[0]=Yd[0];
  spo=Sb[0];
  while (in<Nb)
	{ ii=in%Nb;
    if(sp<EPS) { sp+=Sd[jn]; jn++; }
    if(spo>=EPS)
      {
      if(spo<=sp)
        { nt=NM-1;
		XXo[NM]=Xb[ii]; YYo[NM]=Yb[ii];
        jt=jn; sg=spo/sp;
		XX[NM]=XX[nt]*(1.-sg)+Xd[jt]*sg;
        YY[NM]=YY[nt]*(1.-sg)+Yd[jt]*sg;
		Ct[nt]=spo; sp-=spo; spo=Sb[ii];
        NM++; in++;
        }
      else
        { nt=NM-1; jt=jn;
        XX[NM]=Xd[jt]; YY[NM]=Yd[jt];
        sg=sp/spo;
		XXo[NM]=XXo[nt]*(1.-sg)+Xb[ii]*sg;
        YYo[NM]=YYo[nt]*(1.-sg)+Yb[ii]*sg;
		Ct[nt]=sp; spo-=sp; sp=Sd[jt];
        NM++; jn++;
          }
        }
      else { spo+=Sb[ii]; in++; }
      }
    return(NM);
    }


//---------------------------------------------------------------------
//============ ������������ ��������������� �������� ==========
//------------ ������� ������ ---------
// CurEtal - ������� ����� �������
// ParmEtalon -��������� �������� ���������� ���������� �������
// Xo[],Yo[],So[] - ����������� �������� �������
// Xd[],Yd[],Sg[] - ����������� �������� ������� (Sg[]=Sd[]*(Se/Sr_pr))
//------------ �������� ������ ---------
// XXo[],YYo[], Ct[] - ��������������� ����������� �������� �������
// XX[],YY[], Ct[] - ��������������� ����������� �������� �������
// NM - ���������� ����� � ��������������� ���������
//---------------------------------------------------------------------------
#pragma warn -pck
#pragma option -a1
//---------------------------------------------------------------------------
int AuxiliaryDescriptionFigures(int CurEtal,ParmAffineEtal *ParmAffineEtals,double *Xd,double *Yd,double *Sg,double *Xe, double *Ye, double *Se,double *XXo,double *YYo,double *XX,double *YY,double *Ct)
  {
  #define EPS 1.e-5
  int in,jn,NM,nt,jt,ii,Ne,Adr;
  double sp,spo,sg,*XTo,*YTo,*STo;

  Ne=ParmAffineEtals[CurEtal].Ne;
  Adr=ParmAffineEtals[CurEtal].Adr;
  XTo=&Xe[Adr]; YTo=&Ye[Adr]; STo=&Se[Adr];
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

//---------------------------------------------------------------------
//======= ���������� �������������� �������� =========
//------------ ������� ������ ---------
// N - ���������� ����� � �������� �������
// Xpr[], Ypr[] - �������� ������� �������� ��������� ����� �� ���� X, Y ��������������
//------------ �������� ������ ---------
// Spr[] - ������ ����������� �������� ���� ����� ����� ���������
// Sr_pr - ����������� ����� �������
// Xc_pr, Yc_pr - ����������� ������� ����� ������� (����������� 1-�� �������)
// Mxx_pr,Mxy_pr - ����������� ��������� ������� �� ���� X, Y ��������������
// Myy_pr -  ����������� ��������� �������������� ������
//---------------------------------------------------------------------------
#pragma warn -pck
#pragma option -a1
//---------------------------------------------------------------------------

#include <math.h>
//---------------------------------------------------------------------
//==========  ���������� ������ ��������  ============
//------------ ������� ������ ---------
// Nt - ���������� ����� � ��������������� ��������� ��������
// XXo[],YYo[], Ct[] - ��������������� ����������� �������� �������
// XX[],YY[], Ct[] - ��������������� ����������� �������� �������
// Se - ����� ������� �������
//------------ �������� ������ ---------
//  RXXo,RXYo,RYYo,RYXo - �������������� ������� cov(Xo,X),cov(Yo,X),
//                                                   cov(Yo,Y),cov(Xo,Y) ��������������
// sn,cs - ��������������� ������������������ �������
// re - ������ �������� (�������������������� ���������� ��� ��������� ��������)
//---------------------------------------------------------------------------
#pragma warn -pck
#pragma option -a1
//---------------------------------------------------------------------------
void proximity(int Nt,double *XXo,double *YYo,double *XX,double *YY,double *Ct,int CurEtal,ParmAffineEtal *ParmAffineEtals,double &sn,double &cs,double &re)
{
  double px,ppx,py,ppy,xp,yp,zx,zy,zzx,zzy,xz,yz,sd,See,Se6,st;
  double RXXo,RXYo,RYYo,RYXo;
  int j;
  See=ParmAffineEtals[CurEtal].Se;
  Se6=See*6.;
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
//--------------------------------------------------------------------------
#include <math.h>
#define EPS 1.e-5
// NumEtal - ���������� ��������
// ParmEtals - ���������, ����������� ������
// StepX - dpi
// StepY - dpi
// SE - ����� �������� �������
// �������� ���������:
// Xe - �������� �������
// Ye
// Se
// See - ����� �������
// ����� ������� ���������� � ������� = 20
void ReadEtalons(int NumEtal,struct ParmAffineEtal *ParmEtals,ParmAffineEtal *ParmAffineEtals,
				 double StepEtalX, double StepEtalY, double SE1,int Ny,double *Sg,double *Xd,double *Yd,double *Sd,long *ZME,int ThrEtal,
				 double *XXo,double *YYo,double *XX,double *YY,double *Ct,
				 double *Xe, double *Ye, double *Se,long &NbPk,double &See)
 {
  int i,j,n;
  long NbP,NbPi,NbPj,Adr,AdrCur, Xmax,Xmin, Ymax,Ymin,ix,iy;
  int ix_d,iy_d;
  double Xt,Yt,Xp,Yp,St,Sq,MX,MY,MX2,MY2,MXY,DX,DY,DW,DWo,RXY,Xa,Ya;
  union {unsigned int XY; unsigned short Ptr[2];} CR;
  double Xr[1025],Yr[1025], Xbv[2050],Ybv[2050],
		 Xv[2050],Yv[2050],Sv[2050]; // ,Sr[1025]
  long XYEtal[16384];
  double Ds,Em,Sp,Re,Cs,Sn,Emin[21],Qmin[21],Qmax[21];
  int Nt,Np,Ns,Npr,Nmin,Nmax,Class;
 // double Xd[1024],Yd[1024],Sd[1024];
  double Xb[10],Yb[10],Sb[10];
  double DWb;
  double Area;
  double sn,cs,re;
  union { long ll; float fl; unsigned short Sh[2];} ul;
  int CurEtal;
  int Nb=4;
  // �������� �������� ������� (����������� ��������)
  ul.ll=0xBFD55555;  Xb[0]=Xb[1]=Xb[3]=ul.fl;
  ul.ll=0x40555555;  Xb[2]=ul.fl;
  ul.ll=0xC0855555;  Yb[0]=Yb[3]=ul.fl;
  ul.ll=0x40200000;  Yb[1]=Yb[2]=ul.fl;
  ul.ll=0x40D55555;  Sb[0]=ul.fl;
  ul.ll=0x40A00000;  Sb[1]=ul.fl;
  ul.ll=0x41055555;  Sb[2]=ul.fl;
  ul.ll=0x40F471C6;  DWb=ul.fl;
  CapEtalons *CEM;
  NbPk=0;  NbP=0;  NbPi=0;  Adr=0;
  CEM=(struct CapEtalons *)ZME;
  for(i=0; i<NumEtal; i++)
  {
	CurEtal=i;
	n=CEM[NbPi].N;
	Class=CEM[NbPi].Class;
    NbPi++;
	for(j=0; j<n; j++)XYEtal[j]=ZME[NbPi+j];
	XYEtal[n]=XYEtal[0];  Ns=n+1;
	j=CompressDescribeEtalon(XYEtal, Ns, ThrEtal, XYEtal);
    if(j<3) return;
    else Ns=j;
	See=0.;
    for(j=0; j<Ns; j++)
	{
	  AdrCur=Adr+j;
	  CR.XY=XYEtal[j];            // CR.XY=ZME[NbPi+j];
      ix=CR.Ptr[0]; iy=CR.Ptr[1];
    //  ix_d = ix;
    //  iy_d = iy;
      Xt=StepEtalX*ix;
      Yt=StepEtalY*iy;
	  Xe[AdrCur]=Xt;
      Ye[AdrCur]=Yt;
      if(j==0)
	  {
		Xmax=ix; Xmin=ix;  Nmin=0;
		Ymax=iy; Ymin=iy;
	  }
	  else
	  {
		if(Xmax<ix) Xmax=ix;
		if(Xmin>ix) { Xmin=ix; Nmin=j;}
		if(Ymax<iy) Ymax=iy;
		if(Ymin>iy) Ymin=iy;
		// ���������� ���������� ����� �������
		Se[AdrCur-1]=sqrt((Xt-Xp)*(Xt-Xp)+(Yt-Yp)*(Yt-Yp));
		// ����� �� � ���
		See+=Se[AdrCur-1];
      }
      Xp=Xt; Yp=Yt;
	 }
//============ ����������� ����������� ������ ������� =========
	Area=Ye[Adr]*(Xe[Adr+1]-Xe[Adr+Ns-2]);
    for(j=1; j<Ns-2; j++)
	{
	 AdrCur=Adr+j;
	 Area+=Ye[AdrCur]*(Xe[AdrCur+1]-Xe[AdrCur-1]);
	}
	Area+=Ye[Adr+Ns-2]*(Xe[Adr]-Xe[Adr+Ns-3]);
	Area/=2.;
	if(Area>0.)  ParmAffineEtals[i].Inet=0;
	else
	{  //========== ��������� ������ ������� =======
	  ParmAffineEtals[i].Inet=1;
	  for(j=0; j<Ns/2; j++)
	  {
	   AdrCur=Adr+Ns-j-1;
	   Xp=Xe[Adr+j]; Yp=Ye[Adr+j];   St=Se[Adr+j];
	   Xe[Adr+j]=Xe[AdrCur]; Ye[Adr+j]=Ye[AdrCur]; Se[Adr+j]=Se[AdrCur-1];
	   Xe[AdrCur]=Xp; Ye[AdrCur]=Yp;  Se[AdrCur-1]=St;
	  }
	 }
   /*
	Xp=Xe[Adr+Nmin+1]-Xe[Adr+Nmin];  Yp=Ye[Adr+Nmin+1]-Ye[Adr+Nmin];
	for(j=1; j<Ns; j++)
	  {  Nt=(Nmin+j)/Ns;
	  Xt=Xe[Adr+Nt]-Xe[Adr+Nmin]; Yt=Ye[Adr+Nt]-Ye[Adr+Nmin];
	  if(Xt!=0.)  break;
	  }
	if(Xt*Yp-Yt*Xp>0.)  ParmAffineEtals[i].Inet=0;
	else
	  {  //========== ��������� ������ ������� =======
	  ParmAffineEtals[i].Inet=1;
	  for(j=0; j<Ns/2; j++)
		{ AdrCur=Adr+Ns-j-1;

		Xp=Xe[Adr+j]; Yp=Ye[Adr+j];   St=Se[Adr+j];
		Xe[Adr+j]=Xe[AdrCur]; Ye[Adr+j]=Ye[AdrCur]; Se[Adr+j]=Se[AdrCur-1];
		Xe[AdrCur]=Xp; Ye[AdrCur]=Yp;  Se[AdrCur-1]=St;
		}
	  }
   */
/*
	EtalImage[CurEtal].Num=CurEtal;
	EtalImage[CurEtal].n=Ns;
	EtalImage[CurEtal].Xmin=Xmin;
	EtalImage[CurEtal].Ymin=Ymin;
	EtalImage[CurEtal].Xmax=Xmax;
	EtalImage[CurEtal].Ymax=Ymax;
*/
// ���������� �������������� ������������� �������
	MX=0.;  MY=0.;
	MX2=0.; MY2=0.;  MXY=0.;
	Xp=Xe[Adr]; Yp=Ye[Adr];
	for(j=1; j<Ns; j++)
	{
	  AdrCur=Adr+j;
	  Xt=Xe[AdrCur]; Yt=Ye[AdrCur]; St=Se[AdrCur-1];
	  Xa=Xt+Xp; Ya=Yt+Yp;
	  MX+=Xa*St; MY+=Ya*St;
	  MX2+=(Xa*Xa-Xt*Xp)*St; MY2+=(Ya*Ya-Yt*Yp)*St;
	  MXY+=(2*Xa*Ya-Xp*Yt-Xt*Yp)*St;
	  Xp=Xt; Yp=Yt;
	}
	MX/=(2.*See); MY/=(2.*See);
	MX2/=(3.*See); MY2/=(3.*See);   MXY/=(6.*See);
	DX=MX2-MX*MX;
	DY=MY2-MY*MY;
	RXY=MXY-MX*MY;
	DW=DX+DY;
	Sq=SE1/See;          // ����������� ��������������� ������� �� SE1=20.
	DWo=DW*Sq*Sq;      // �������� ��������� ������������ �������

	ParmAffineEtals[i].Adr=Adr;
	ParmAffineEtals[i].Ne=Ns;
	ParmAffineEtals[i].Se=See*Sq;
	ParmAffineEtals[i].Sq=Sq;
	ParmAffineEtals[i].DW=DWo;
	ParmAffineEtals[i].DX=DX*Sq*Sq;
	ParmAffineEtals[i].DY=DY*Sq*Sq;
	ParmAffineEtals[i].RXY=RXY*Sq*Sq;
	ParmAffineEtals[i].Xc=MX*Sq;
	ParmAffineEtals[i].Yc=MY*Sq;
	ParmAffineEtals[i].Xn=Xmin*StepEtalX*Sq;
	ParmAffineEtals[i].Xk=Xmax*StepEtalX*Sq;           // �������� ��������
	ParmAffineEtals[i].Yn=Ymin*StepEtalY*Sq;
	ParmAffineEtals[i].Yk=Ymax*StepEtalY*Sq;

	for(j=0; j<Ns; j++)
	{
	  AdrCur=Adr+j;
	  Xe[AdrCur]-=MX; Ye[AdrCur]-=MY;
	  Xe[AdrCur]*=Sq; Ye[AdrCur]*=Sq; Se[AdrCur]*=Sq;
	}
 //======== ���������� ����������� =========
	Em=DWo;   Ds=SE1/Ny;
	Np=1;  Sp=-Ds;
	St=Se[Adr];

	for(j=0; j<Ny; j++)
	{
	  Sp+=Ds;
	  for(;St<Sp;) {Sp-=St; St=Se[Np+Adr]; Np++; }
	  Npr=CurrentDecribeEtalon(CurEtal,Np,Sp,ParmAffineEtals,Xe,Ye,Se,Xd,Yd,Sd);  // Xe[]->Xd[]
	  Nt=AuxiliaryDescriptionBase(CurEtal,Xb,Yb,Sb,Xd,Yd,Sd,XXo,YYo,XX,YY,Ct,Nb);  // ���� Xb[],Xd[] -> ����� XXo[],XX[]
	  proximity(Nt,XXo,YYo,XX,YY,Ct,CurEtal,ParmAffineEtals,sn,cs,re); // ���� XXo[],XX[] -> ����� sn,cs,re
	  Emin[j]=DWb-re/DWo;
	}
	Nmin=0;  Nmax=0;
	if(Emin[0]<Emin[Ny-1]&&Emin[0]<=Emin[1])
	{
	 Qmin[Nmin]=0.; Nmin++;
	}
	if(Emin[0]>Emin[Ny-1]&&Emin[0]>=Emin[1])
	{
	 Qmax[Nmax]=0.; Nmax++;
	}
	for(j=1; j<Ny-1; j++)
	{
	 if(Emin[j]<Emin[j-1]&&Emin[j]<=Emin[j+1])
	 {
	  Qmin[Nmin]=j*Ds; Nmin++;
	 }
	 if(Emin[j]>Emin[j-1]&&Emin[j]>=Emin[j+1])
	 {
	  Qmax[Nmax]=j*Ds; Nmax++;
	 }
	}
	if(Emin[Ny-1]<Emin[Ny-2]&&Emin[Ny-1]<=Emin[0])
	{
	 Qmin[Nmin]=(Ny-1)*Ds; Nmin++;
	}
	if(Emin[Ny-1]>Emin[Ny-2]&&Emin[Ny-1]>=Emin[0])
	{
	 Qmax[Nmax]=(Ny-1)*Ds; Nmax++;
	}
//========= ����� ���������� �������� ===========
	Nt=Qmin[0]/Ds+0.5;
	Em=Emin[Nt];
	ParmAffineEtals[i].Qm=Qmin[0];
	for(j=1; j<Nmin; j++)
	{
	  Nt=Qmin[j]/Ds+0.5;
	  if(Emin[Nt]<Em)
	  {
		Em=Emin[Nt];
		ParmAffineEtals[i].Qm=Qmin[j];
	  }
	}
   /*
		   if(Em>Emin[j])
		{ Em=Emin[j];
		ParmAffineEtals[i].Np=Np;
		ParmAffineEtals[i].Sp=Sp;
		}
	 */

//======= ����������� ������ ��� ������ ������������� �������� =======
	Np=0;  Sp=Ds;
	for(j=0; j<Ns; j++)
	{
	  Np=j+1;
	  if(Se[Adr+j]>Sp) break;
	  Sp-=Se[Adr+j];
	}
	Nt=CurrentDecribeEtalon(CurEtal,Np,Sp,ParmAffineEtals,Xe,Ye,Se,Xd,Yd,Sd);          // Xe[]->Xd[]
	for(j=0; j<Nt; j++) Sg[j]=Sd[j];
	Nt=AuxiliaryDescriptionFigures(CurEtal,ParmAffineEtals,Xd,Yd,Sg,Xe,Ye,Se,XXo,YYo,XX,YY,Ct);
	proximity(Nt,XXo,YYo,XX,YY,Ct,CurEtal,ParmAffineEtals,sn,cs,re);
	Em=DWo-re/DWo;
	ParmAffineEtals[i].Em=Em;
//====================================================================
	Adr+=Ns;
	NbP+=n;
	NbPi+=n;
  }
  NbPk=NbP;
  return;
 }
//---------------------------------------------------------------------------
class Thread_Similarities : public TThread
{
 private:
 protected:
		void __fastcall Execute();
 public:
		__fastcall Thread_Similarities(bool CreateSuspended);
		__fastcall ~Thread_Similarities();
	   //	int CurEtal;
	 //	double DWo;
	//	int gamma1;
	//	int betta;
   //     int betta_opt,gamma_opt,i_opt,i_gamma,i_betta;
		int d_gam;
		int gam_n;
		int K_gam;
		int K_bet;
		int bet_n;
		int d_bet;
		int CurentEtal;
	  //	double fgamma;
		ParmAffine* evaluation_of_similarities_g_b(int CurEtal,int gamma1,double fgamma,int i_gamma,int i_betta,int N,double DWo,double *Xf,double *Yf,double *Xpr,double *Ypr,double *Spr,double &erm,double See);
		void evaluation_of_similarities(int CurEtal,int N,double DWo,int d_gam,int gam_n,int K_gam,int K_bet,double *Xf,double *Yf,double *Xpr,double *Ypr,double *Spr,double &erm,double See);
		int CurrentDecribeObject(int N1,int Np, double Sp,double *Xpr,double *Ypr,double *Spr,double *Xd,double *Yd,double *Sd);  // ,double &Sr_pr
		void CalculateMomentsObject(int N,double *Xpr,double *Ypr,double *Spr,double &Mxx_pr,double &Myy_pr,double &Mxy_pr,double &Xc_pr,double &Yc_pr);
		int evaluation_object(int ii);
		bool useKmpObj;
		int ii;
		TScene *SceneIn;
		long LP;
		ParmAffineEtal *ParmAffineEtals;
		RecognizedObject *RecObj;
		ParmAffine *ParmTrans;
		long* PaspKmp;
		double *Xe;
		double *Ye;
		double *Se;
		double StepObjX,StepObjY;
		int Ny;
		int ThrObj;
		int NumEtal;
		int KetImage;
		double *XXo;
		double *YYo;
		double *XX;
		double *YY;
		double *Ct;
		double *Erq;
		double *Sg;
	  //	double *Xf;
	  //	double *Yf;  //?  ,Sf[1025]
		double *Xd;
		double *Yd;
		double *Sd;
	  //	double *Sg;
	  // double *Xpr,*Ypr,*Spr;
		double Sr_pr;
		double er;
		long XDmax,XDmin,YDmax,YDmin;
		long XRmax,XRmin,YRmax,YRmin;
	  //	double Xc_pr,Yc_pr;
		int Pmax,Pmin;
		int NumParm;
		double st; // Ds,
		int OutMin,InMin;
		int ErrorCode;
	  // double See;
	  //	double sn,cs,re;
		long *MetrKmp;
		bool MyTerminated;
};
//--------------------------------------------------------------------------
__fastcall Thread_Similarities::Thread_Similarities(bool CreateSuspended)
		: TThread(CreateSuspended)
{
  MyTerminated = false;
  CurentEtal = -1;
}
//---------------------------------------------------------------------------
__fastcall Thread_Similarities::~Thread_Similarities()
{

}
//---------------------------------------------------------------------------
void __fastcall Thread_Similarities::Execute()
{
 evaluation_object(ii); // Thread_Similarities::
 MyTerminated = true;
}
#pragma warn -pck
#pragma option -a1
//---------------------------------------------------------------------
//==========  ������������ �������� �������� �������  ==========
//------------ ������� ������ ---------
//  N - ���������� ����� � �������� �������� ������� (P:N=P:1);  N1=N-1
// Np, Sp - ����� ����� � � �������� �� ��� ��� ��������� �������������� ���������
//                ����� � ������������ �������� ��������  �������
// Xpr[],Ypr[],Spr[] - �������� �������� �������
// Sr_pr - ����� �������
//------------ �������� ������ ---------
// Xd[],Yd[],Sd[] - ����������� ������� ��������  �������
// n -  ���������� ����� � ����������� ������� �������� �������
//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
int Thread_Similarities::CurrentDecribeObject(int N1,int Np, double Sp,double *Xpr,double *Ypr,double *Spr,double *Xd,double *Yd,double *Sd)
{
  int i,j,n,i1,i2;
  double sg,Sfr,st;

  if(Spr[Np-1]<Sp) return(-1);
  i=Np;
  i2=i%N1; i1=(i-1)%N1;
  sg=Sp/Spr[i1];
  Xd[0]=Xpr[i1]*(1.-sg)+Xpr[i2]*sg;
  Yd[0]=Ypr[i1]*(1.-sg)+Ypr[i2]*sg;
  Sfr=Spr[i1]-Sp;
  st=Sfr;
  n=1;
  for(;;)
	{ i2=i%N1; i1=(i-1)%N1;
	if(Sfr>=Sr_pr)
	  {
	  sg=Sfr-Sr_pr;
	  Sd[n-1]=st-sg;
	  sg/=st;
	  Xd[n]=Xpr[i1]*sg+Xpr[i2]*(1.-sg);
	  Yd[n]=Ypr[i1]*sg+Ypr[i2]*(1.-sg);
	  n++;
	  break;
	  }

	Xd[n]=Xpr[i2]; Yd[n]=Ypr[i2]; Sd[n-1]=st;
	st=Spr[i2]; Sfr+=st;
	i++; n++;
	}

  return(n);
}
//---------------------------------------------------------------------------                                                        // ,double &Sr_pr
void Thread_Similarities::CalculateMomentsObject(int N,double *Xpr,double *Ypr,double *Spr,double &Mxx_pr,double &Myy_pr,double &Mxy_pr,double &Xc_pr,double &Yc_pr)
  {
  int j;
  double Dx,Dy,Sj,Vx,Vy,Vxx,Vyy, Sr2,Sr3;
  Sr_pr=0.;
  for(j=0; j<N-1; j++)
    {
	Dx=(double)(Xpr[j+1]-Xpr[j]);
    Dy=(double)(Ypr[j+1]-Ypr[j]);
	Sj=sqrt(Dx*Dx+Dy*Dy);
    Spr[j]=Sj;
	Sr_pr+=Sj;
    }

  Xc_pr=0.;     Yc_pr=0.;
  Vx=(double)Xpr[0];   Vy=(double)Ypr[0];   Sj=Spr[0];
  for(j=1; j<N; j++)
    {
	Vxx=(double)Xpr[j];  Vyy=(double)Ypr[j];
    Xc_pr+=(Vx+Vxx)*Sj;
	Yc_pr+=(Vy+Vyy)*Sj;
    Sj=Spr[j];
    Vx=Vxx;   Vy=Vyy;
    }
  Sr2=Sr_pr+Sr_pr;
  Xc_pr/=Sr2;   Yc_pr/=Sr2;

  Mxx_pr=0.;  Myy_pr=0.;  Mxy_pr=0.;
  Vx=(double)Xpr[0];  Vy=(double)Ypr[0]; Sj=Spr[0];
  for(j=1; j<N; j++)
    {
    Vxx=(double)Xpr[j];  Vyy=(double)Ypr[j];
    Mxx_pr+=(Vx*Vx+Vxx*Vxx+Vx*Vxx)*Sj;
    Myy_pr+=(Vy*Vy+Vyy*Vyy+Vy*Vyy)*Sj;
    Mxy_pr+=(2*(Vx*Vy+Vxx*Vyy)+Vx*Vyy+Vxx*Vy)*Sj;
    Vx=Vxx;  Vy=Vyy;  Sj=Spr[j];
    }
  Sr3=Sr2+Sr_pr;
  Mxx_pr/=Sr3;
  Myy_pr/=Sr3;
  Mxy_pr/=(Sr3+Sr3);

  Mxx_pr-=(Xc_pr*Xc_pr);
  Myy_pr-=(Yc_pr*Yc_pr);
  Mxy_pr-=(Xc_pr*Yc_pr);


  return;
  }
//---------------------------------------------------------------------------
ParmAffine* Thread_Similarities::evaluation_of_similarities_g_b(int CurEtal,int gamma1,double fgamma,int i_gamma,int i_betta,int N,double DWo,double *Xf,double *Yf,double *Xpr,double *Ypr,double *Spr,double &erm,double See)
{
  double Sn,Cs,Re;
  double s0,s1,s2,sc,e0,e1,e2,w1,w2,DW;
  double fbetta,sg;
  double Sj,Ds;
  int Np_cor,Np;
  double DW_opt,Sp_opt;
  double Sp_cor;
  int j;
  int betta;
  int i_cor;
  double err;
  double sn,cs,re;
  int Npr,Nt;
  double Sp;
  double Mxx_pr,Myy_pr,Mxy_pr,Xc_pr,Yc_pr;
  //? ErrGB[20][20],
  betta=bet_n+i_betta*d_bet;
  fbetta=(double)betta*M_PI/(double)180;
		  for(int j=0; j<N; j++)
		  {
		   Xpr[j]=Xf[j]*cos(fgamma)+Yf[j]*sin(fbetta)*sin(fgamma);
		   Ypr[j]=Yf[j]*cos(fbetta);
		  }                                                      // ,Sr_pr
		  CalculateMomentsObject(N,Xpr,Ypr,Spr,Mxx_pr,Myy_pr,Mxy_pr,Xc_pr,Yc_pr);     //����  Xpr;  �����  Xc_pr,Sr_pr,Mxx_pr
		  DW=Mxx_pr+Myy_pr;
		  err=DWo;
		  Ds=Sr_pr/Ny;  Sp=-Ds; Sj=Spr[0]; Np=1;
		  for (int i=0; i<Ny; i++)
		  {
		   Sp+=Ds;
		   for(;Sj<Sp;) {Sp-=Sj; Sj=Spr[Np]; Np++; }
		   Npr=CurrentDecribeObject(N-1,Np,Sp,Xpr,Ypr,Spr,Xd,Yd,Sd);  // Xpr[]->Xd[]
		   if(Sr_pr!=0)sg=See/Sr_pr;else sg=1;
		   for(int j=0; j<Npr-1; j++)  Sg[j]=Sd[j]*sg;
		   Nt=AuxiliaryDescriptionFigures(CurEtal,ParmAffineEtals,Xd,Yd,Sg,Xe,Ye,Se,XXo,YYo,XX,YY,Ct);  // ���� Xo[],Xd[] -> ����� XXo[],XX[]
		   proximity(Nt,XXo,YYo,XX,YY,Ct,CurEtal,ParmAffineEtals,sn,cs,re); // ���� XXo[],XX[] -> ����� sn,cs,re
		   er=DWo-re/DW;
		   Erq[i]=er;
		   if(er<err)
		   {
			err=er; i_cor=i; Np_cor=Np; Sp_cor=Sp; Sn=sn; Cs=cs; Re=re;
		   }
		  }
//============ ��������� ������������� �������� ===========
		  for(int jtt=0; jtt<3; jtt++)
		  {
			switch(jtt)
			  {
			  case 0: //? ErrGB[i_gamma][i_betta]=err;    //������ ��� ���������
					  Erq[Ny]=Erq[0];  Erq[Ny+1]=Erq[1];
					  if(i_cor==0) j=Ny; else j=i_cor;
					  s1=j*Ds; s0=s1-Ds; s2=s1+Ds;
					  e1=Erq[j]; e0=Erq[j-1]; e2=Erq[j+1];
					  Sp=st=s1+(e0-e2)/(e0+e2-2.*e1)*(Ds/2.);
					  break;
			  case 1: if(er>e1)             //������ ��� ���������
						{
						if(st>s1) { s2=st; e2=er;}
						else {s0=st; e0=er;}
						}
					  else
						{
						if(st>s1) { s0=s1; e0=e1; }
						else { s2=s1; e2=e1; }
						s1=st; e1=err;
						}
					  w1=(e0-e1)*(s2-s1); w2=(e2-e1)*(s1-s0);
					  Sp=st=s1+(w1*(s2-s1)-w2*(s1-s0))/(w1+w2)/2.;
					  break;
			  case 2: if(er>e1)             //������ ��� ���������
						{
						if(st>s1) { s2=st; e2=er;}
						else {s0=st; e0=er;}
						}
					  else
						{
						if(st>s1) { s0=s1; e0=e1; }
						else { s2=s1; e2=e1; }
						s1=st; e1=err;
						}
					  w1=(e0-e1)*(s2-s1); w2=(e2-e1)*(s1-s0);
					  Sp=st=s1+(w1*(s2-s1)-w2*(s1-s0))/(w1+w2)/2.;
					  break;
			  default: if(NumParm>0) ErrorCode = 1;
					   else
						 {
						  Error_View("Error","������ � ���������");
						 }
					   break;
			  }

			if(Sp>=Sr_pr) Sp-=Sr_pr;
			Sj=Spr[0]; Np=1;
			for(;Sj<Sp;) {Sp-=Sj; Sj=Spr[Np]; Np++; }
			Npr=CurrentDecribeObject(N-1,Np,Sp,Xpr,Ypr,Spr,Xd,Yd,Sd);  // Xpr[]->Xd[]
			sg=See/Sr_pr;
			for(int j=0; j<Npr-1; j++)  Sg[j]=Sd[j]*sg;
			Nt=AuxiliaryDescriptionFigures(CurEtal,ParmAffineEtals,Xd,Yd,Sg,Xe,Ye,Se,XXo,YYo,XX,YY,Ct);  // ���� Xo[],Xd[] -> ����� XXo[],XX[]
			proximity(Nt,XXo,YYo,XX,YY,Ct,CurEtal,ParmAffineEtals,sn,cs,re); // ���� XXo[],XX[] -> ����� sn,cs,re
			er=DWo-re/DW;
			if(err>er)
			{
			 err=er;
		   //?	 ErrGB[i_gamma][i_betta]=err;
			 Np_cor=Np; Sp_cor=Sp;
			 Sn=sn; Cs=cs; Re=re;
			}
		   }
		   if(err<erm)
			{
			 erm=err;
		   //?	 betta_opt=betta;
		   //?	 gamma_opt=gamma1;
		   //?	 i_opt=i_cor;
		   //?	 Np_opt=Np_cor;
			 Sp_opt=Sp_cor;
			 DW_opt=DW;
			 ParmTrans[CurEtal].Np=Np_cor;  ParmTrans[CurEtal].Sp=Sp_cor;
			 ParmTrans[CurEtal].betta=betta; ParmTrans[CurEtal].gamma=gamma1;
			 ParmTrans[CurEtal].DW=DW;
		  //?	 ParmTrans[CurEtal].Mxx=Mxx;
		  //?	 ParmTrans[CurEtal].Myy=Myy;
		  //?	 ParmTrans[CurEtal].Mxox=RXXo; ParmTrans[CurEtal].Mxoy=RYXo;
		  //?	 ParmTrans[CurEtal].Myox=RXYo; ParmTrans[CurEtal].Myoy=RYYo;
			 ParmTrans[CurEtal].SnQ=Sn; ParmTrans[CurEtal].CsQ=Cs;
			 ParmTrans[CurEtal].Re=Re; ParmTrans[CurEtal].erm=erm;
			}
 return ParmTrans;
}
//---------------------------------------------------------------------------
void Thread_Similarities::evaluation_of_similarities(int CurEtal,int N,double DWo,int d_gam,int gam_n,int K_gam,int K_bet,double *Xf,double *Yf,double *Xpr,double *Ypr,double *Spr,double &erm,double See)
{
   int gamma1;
   double fgamma;
   for(int i_gamma=0; i_gamma<K_gam; i_gamma++)
   {
	 gamma1=gam_n+i_gamma*d_gam;
	 fgamma=(double)gamma1*M_PI/(double)180;
	 for(int i_betta=0; i_betta<K_bet; i_betta++)
	 {
	  evaluation_of_similarities_g_b(CurEtal,gamma1,fgamma,i_gamma,i_betta,N,DWo,Xf,Yf,Xpr,Ypr,Spr,erm,See);
	 }    // ���� �� betta
   }
 return;
}
//---------------------------------------------------------------------------
int Thread_Similarities::evaluation_object(int ii)
{
// int OutMin,InMin;
// OutMin=InMin=Pmin;
 Erq = new double [1024];
 Sg=new double[1024];
 XXo=new double[2048];
 YYo=new double[2048];
 XX=new double[2048];
 YY=new double[2048];
 Xd=new double[1024];
 Yd=new double[1024];
 Sd=new double[1024];
 Ct=new double[1024];
 ParmTrans= new ParmAffine[NumEtal+1];
 double Rm,XNc,YNc;
 int i,j;
 int CurEtal = 0;
 long Lobj,NumObj;
 int N;
 int Nln=0;
 int SMDObj = 0;
 TContour *wContour;
 T_Point maxXY;
 T_Point minXY;
 long XOmax;long XOmin;long YOmax;long YOmin;
 long AP;
 int Thr;
 long MoveObj;
 double Xc,Yc;
 T_Point wPoint;
 int jt;
 double Eq;
 double Rq;
 double erm;
 double See;
 double Mxx_pr,Myy_pr,Mxy_pr;
 double DWo;
 double Xc_pr,Yc_pr;
// double S[1024];
//    if(Nstop==ii+1)
//      { jt=ii;
//      }
  if(!useKmpObj)
	{
 //   itoa(ii+1, Txt, 10);
	NumObj = ii;
	wContour = SceneIn->GetContour(ii);
	maxXY = wContour->FindMaxXY();
	minXY = wContour->FindMinXY();
	XOmin = minXY.GetX();
	YOmin = minXY.GetY();
	XOmax = maxXY.GetX();
	YOmax = maxXY.GetY();
	Lobj=wContour->GetNumPoints()*2;        // ���������� ���������
	if(XOmin<XDmin) return 1;// continue;
	if(XOmax>XDmax) return 1;// continue;
	if(YOmin<YDmin) return 1;// continue;
	if(YOmax>YDmax) return 1;// continue;
	 N=Lobj/2;
//===== ��������� ��������  �� �������������� =====
	if(maxXY.GetX()>32767||maxXY.GetY()>32767) return 1;// continue;
	if(Lobj>2048)Nln=1;
	//============= �������� ��������������� �������� =============
	if(XOmax-XOmin>Pmax||YOmax-YOmin>Pmax||Nln==1)  return 1;// continue;
//================ ��������� �� ��������� ===============
	if(XOmax-XOmin<Pmin&&YOmax-YOmin<Pmin)
	  {
	  if(wContour->GetTypeContour() == 0)
		{ if(XOmax-XOmin<OutMin&&YOmax-YOmin<OutMin) return 1;// continue;
		}
      else
		{ if(XOmax-XOmin<InMin&&YOmax-YOmin<InMin) return 1;// continue;
        }
	  }
	SMDObj+=Lobj;
   }
  else
  {
//  if(Nstop==ii+1)
//    { jt=ii;
//    }
	AP=ii*LP;
	NumObj=PaspKmp[AP+4l];
	XOmin=PaspKmp[AP+5l];
    YOmin=PaspKmp[AP+7l];
	XOmax=PaspKmp[AP+6l];
    YOmax=PaspKmp[AP+8l];
	Lobj=PaspKmp[AP+3l];
	N=Lobj/2;
//===== ��������� �� �� ��������� � ������������� =====
	if(XOmin<XDmin||XOmin<XRmin) return 1;// continue;
	if(XOmax>XDmax||XOmax>XRmax) return 1;// continue;
	if(YOmin<YDmin||YOmin<YRmin) return 1;// continue;
	if(YOmax>YDmax||YOmax>YRmax) return 1;// continue;
//===== ��������� ���������� �������� =====
	if(NumObj<0) return 1;// continue;
//===== ��������� �� ���������� ����� � ������� ������� =====
	if(Lobj>2048)    Nln=1;
//============= ��������� ��������������� �������� =============
	if(XOmax-XOmin>Pmax||YOmax-YOmin>Pmax||Nln==1)  return 1;// continue;
//================ ��������� �� ��������� ===============
	if(XOmax-XOmin<Pmin&&YOmax-YOmin<Pmin)
      {
      if(PaspKmp[AP+11l]>0)
		{ if(XOmax-XOmin<OutMin&&YOmax-YOmin<OutMin) return 1;// continue;
        }
      else
		{ if(XOmax-XOmin<InMin&&YOmax-YOmin<InMin) return 1;// continue;
        }
      }
	SMDObj+=Lobj;
  }
//*/
 double *X = new double[N+1];
 double *Y = new double[N+1];
 long *XYObj = new long[Lobj+5];
 double *Xf = new double[N+1];
 double *Yf = new double[N+1];
 double *Xpr  = new double[N+1];
 double *Ypr  = new double[N+1];
 double *Spr  = new double[N+1];
//===== ��������� ��������  �� �������������� =====


	Thr=(XOmax-XOmin)/50;
	j=(YOmax-YOmin)/50;
    if(Thr<j) Thr=j;
	if(Thr>ThrObj)  Thr=ThrObj;
	Xc=(XOmax+XOmin)*StepObjX/2.;
	Yc=(YOmax+YOmin)*StepObjY/2.;

   if(useKmpObj)
     {
	 MoveObj=(PaspKmp[AP+1l]-1)*4096l+(PaspKmp[AP+2l]-1);
	 for(j=0; j<Lobj; j++) XYObj[j]=MetrKmp[MoveObj+j];
	 }
   else
	{
	for(int j=0; j<N; j++)
	  {
	  wPoint = wContour->GeT_Point(j);
	  XYObj[j*2] = wPoint.GetX()-XOmin;
	  XYObj[j*2 + 1] = wPoint.GetY()-YOmin;
	  }
    }
	jt=N+N;
	XYObj[jt]=XYObj[0];
	XYObj[jt+1]=XYObj[1];
    N++;
	i=CompressPointsDescribe(XYObj,N,ThrObj,XYObj);
    if(i>0) N=i;
    else
	  {
	  // MessageBox::Show("", "", MessageBoxButtons::OK, MessageBoxIcon::Information);
		  Error_View("���������� ������","������ ��� ������ ���������� �������");
		  return -1;
	  }
    if(useKmpObj)
	  {
	  if(PaspKmp[AP+11l]>0)
//    if(wContour->GetTypeContour() == 0)
		{ j=0;
		for(int i=0; i<N; i++)
		  { X[i]=XYObj[j++]; Y[i]=XYObj[j++];
		  }
        }
      else
		{ j=0;
		for(int i=0; i<N; i++)
		  { jt=N-i-1;  X[jt]=XYObj[j++]; Y[jt]=XYObj[j++];
		  }
        }
      }
	else
      {
      if(wContour->GetTypeContour() == '0')
		{
	j=0;
		for(int i=0; i<N; i++)   { X[i]=XYObj[j++]; Y[i]=XYObj[j++]; }
		}
      else
        {
	j=0;
		for(int i=0; i<N; i++)
          { jt=N-i-1;
		  X[jt]=XYObj[j++]; Y[jt]=XYObj[j++];
          }
        }
	  }
	for(int j=0; j<N; j++)
      {
	  Xpr[j]=Xf[j]=X[j]*StepObjX; Ypr[j]=Yf[j]=Y[j]*StepObjY;
	  }                                                    // ,Sr_pr
	CalculateMomentsObject(N,Xpr,Ypr,Spr,Mxx_pr,Myy_pr,Mxy_pr,Xc_pr,Yc_pr);    //����  Xpr;  �����  Xc_pr,Sr_pr,Mxx_pr
	Xc=Xc_pr;  Yc=Yc_pr;
//?	Sr=Sr_pr;
//?	Scl=See/Sr;
//?    for(j=0; j<N; j++)  Sf[j]=Spr[j];
//?	Mxx=Mxx_pr;   Myy=Myy_pr;  Mxy=Mxy_pr;
/*
    jt=ii+ii;
	PXoYo[jt++]=(long double)XOmin+Xc;
    PXoYo[jt++]=(long double)YOmin+Yc;
    XNc=Xc+XOmin;  YNc=Yc+YOmin;
*/
/*
	jt=ii+ii;
	PXoYo[jt++]=Xc;
	PXoYo[jt++]=Yc;
*/
	XNc=Xc;  YNc=Yc;
	for(int j=0; j<N; j++)  { Xf[j]-=Xc; Yf[j]-=Yc; }
  //======= ���������� �������� �������� ������ �������� =======
	for(int CurEtal=0; CurEtal<KetImage; CurEtal++)
	{
	//?  betta_opt=0;
	//?  gamma_opt=0;
	  erm=DWo=ParmAffineEtals[CurEtal].DW;
	  See=ParmAffineEtals[CurEtal].Se;
	  evaluation_of_similarities(CurEtal,N,DWo,d_gam,gam_n,K_gam,K_bet,Xf,Yf,Xpr,Ypr,Spr,erm,See);
	  j=CurEtal;
	}        // ���� �� ��������
	  Rm=0.;
	  CurEtal = 0;
	  for(int j=0; j<NumEtal; j++)
	  {
	   Eq=ParmTrans[j].erm;
	   Rq=1./(1+Eq/3.0);
	   if(Rm<Rq) { Rm=Rq; CurEtal=j;}
	  }
	  if(Rm>=0.975)
	  {
	   CurentEtal = CurEtal;
	   j=RecObj[CurEtal].No;
	   if(j<10)
	   {
		RecObj[CurEtal].Num[j]=ii+1;
		RecObj[CurEtal].Xc[j]=Xc;
		RecObj[CurEtal].Yc[j]=Yc;
		RecObj[CurEtal].gam[j]=ParmTrans[CurEtal].gamma;
		RecObj[CurEtal].bet[j]=ParmTrans[CurEtal].betta;
		RecObj[CurEtal].No++;
	   }
	  }
 delete []Erq;
 delete []Sg;
 delete []Xf;
 delete []Yf;
 delete []Xpr;
 delete []Ypr;
 delete []Spr;
 delete []XYObj;
 delete []X;
 delete []Y;
 delete []Xd;
 delete []Yd;
 delete []Sd;
 delete []XXo;
 delete []YYo;
 delete []XX;
 delete []YY;
 delete []ParmTrans;
 delete []Ct;
 MyTerminated = true;
}
//---------------------------------------------------------------------------
Thread_Similarities **My_Threadi;
Thread_Similarities *My_Thread;
int RecognitionObject(TScene *SceneIn, long KObj, int NumEtal,struct ParmAffineEtal *ParmEtals,ParmAffineEtal *ParmAffineEtals,
					  double StepX,double StepY,double SE1,double *Sg,double *Xd, double *Yd,double *Sd,double *Xe,
					  double *Ye,double *Se,bool useKmpObj,RecognizedObject *RecObj,ParmAffine *ParmTrans,double StepEtalX,
					  double StepEtalY,double StepObjX,double StepObjY,int Ny,int ThrObj,int KetImage,double *XXo,double *YYo,
					  double *XX,double *YY,double *Ct,long* PaspKmp,long XDmax,long XDmin,long YDmax,long YDmin,int Pmax,int Pmin,int NumParm,long *MetrKmp,bool thread,Param_Recogn &Param_Rec)
  {
  int n,k,jt,Nt,Nsm,Np,Ne,Thn,LP=16,Nstop;
  long NbP; //,XOmax,XOmin, YOmax,YOmin;
  double Xt,Yt,Xp,Yp,St,Sq,MX,MY,MX2,MY2,DX,DY,DW,DWo,DE,Xa,Ya;
  long ii;  // ,Lobj
  double Qmin[21],Emin[21],Qmax[21],Sp,Qt; //Em, Sn,Cs,Re,
  double Snq,Csq,Snm,Csm,Hg,Wd;
  int Nmin,Nmax,NEtal;
  double Xr[1025],Yr[1025], Xbv[2050],Ybv[2050],
		 Xv[2050],Yv[2050],Sv[2050];
  char Txt[256];
  long XRmax,XRmin,YRmax,YRmin;
  int gam_n,bet_n,d_gam,d_bet;
  int K_gam;
  int K_bet;
  int CurEtal;
 // Param_Recogn Param_Rec;
//======= ���������� ��������� �������� ������� ==========
  double Er,emax=0.5,Emax,step=0.5;  // s0,s1,s2,sc,e0,e1,e2,,w1,w2
  int ol;
  Nstop=165;
 // T_Point maxXY;
 // T_Point minXY;
  int Thr;
	//? SMDObj=0; KDObj=0; KSmObj=0; SMSmObj=0;
  d_gam=5; gam_n=0; K_gam=90/d_gam;
  d_bet=5; bet_n=0; K_bet=90/d_bet;
  Thr=Pmin;
//======= ��������������� ����������� �� �������� =====
  for(CurEtal=0; CurEtal<NumEtal; CurEtal++)
    {
	Sq=ParmAffineEtals[CurEtal].Sq;
	jt=(ParmAffineEtals[CurEtal].Xk-ParmAffineEtals[CurEtal].Xn)/Sq/StepEtalX+0.5;
	if(Thn>jt) Thn=jt;
	if(Thr<jt) Thr=jt;
	jt=(ParmAffineEtals[CurEtal].Yk-ParmAffineEtals[CurEtal].Yn)/Sq/StepEtalY+0.5;
    if(Thr<jt) Thr=jt;
	if(Thn>jt) Thn=jt;
	}
  Thr*=1.1;
  Thn*=0.9;
  if(Pmax>Thr)  Pmax=Thr;
//  Pmin=15; Pmax=25;
  XRmin=280; XRmax=430;
  YRmin=270;  YRmax=370;
//=====================================================
  My_Threadi = new Thread_Similarities*[KObj+1];
  for(ii=0; ii<KObj; ii++)
  {
   My_Threadi[ii] = new Thread_Similarities(true);
   My_Thread = My_Threadi[ii];
 //  My_Thread->Nln = Nln;
   My_Thread->useKmpObj = useKmpObj;
   My_Thread->ii = ii;
   My_Thread->SceneIn = SceneIn;
   My_Thread->LP = LP;
   My_Thread->d_gam = d_gam;
   My_Thread->gam_n = gam_n;
   My_Thread->bet_n = bet_n;
   My_Thread->d_bet = d_bet;
   My_Thread->K_gam = K_gam;
   My_Thread->K_bet = K_bet;
   My_Thread->ParmAffineEtals = ParmAffineEtals;
   My_Thread->RecObj = RecObj;
   My_Thread->Xe = Xe;
   My_Thread->Ye = Ye;
   My_Thread->Se = Se;
   My_Thread->StepObjX = StepObjX;
   My_Thread->StepObjY = StepObjY;
   My_Thread->Ny = Ny;
   My_Thread->ThrObj = ThrObj;
   My_Thread->NumEtal = NumEtal;
   My_Thread->KetImage = KetImage;
   // My_Thread->Ct = Ct;
   // My_Thread->ParmTrans = ParmTrans;
   My_Thread->XDmax = XDmax;
   My_Thread->XDmin = XDmin;
   My_Thread->YDmax = YDmax;
   My_Thread->YDmin = YDmin;
   My_Thread->XRmax = XRmax;
   My_Thread->XRmin = XRmin;
   My_Thread->YRmax = YRmax;
   My_Thread->YRmin = YRmin;
   My_Thread->Pmax = Pmax;
   My_Thread->Pmin = Pmin;
   My_Thread->OutMin=My_Thread->InMin=Pmin;
   My_Thread->NumParm = NumParm;
   My_Thread->MetrKmp = MetrKmp;
   My_Thread->PaspKmp = PaspKmp;
   if(thread)
	My_Thread->Resume();
   else
	My_Thread->evaluation_object(ii);
  }
// /*
 if(thread)
 {
  bool flag_thread;
  do
  {
   flag_thread = true;
   for(ii=0; ii<KObj; ii++)
   {
	if(!My_Threadi[ii]->MyTerminated)flag_thread = false;
   }
  }
  while (!flag_thread);
 }
 // */
 /*?
  for(ii=0; ii<KObj; ii++)
  {
   delete My_Threadi[ii];
  }
  delete []My_Threadi;
 */
   CurEtal = 0;
   for(int j=0;  j<NumEtal; j++)
   {
	if(RecObj[j].No>0)  CurEtal++;
   }
  // if(double(CurEtal0)/double(NumEtal)>0.5)
   {
	sprintf(Txt,"������� %i �� %i ��������",CurEtal,NumEtal);
	Error_View("���������� �������",Txt);
  //  MessageBox::Show(Txt, "", MessageBoxButtons::OK, MessageBoxIcon::Information);
   }
   Param_Rec.N = CurEtal;
   Param_Rec.bet[0] = RecObj[0].bet[0];
   Param_Rec.gam[0] = RecObj[0].gam[0];
//===== ����������� ������������ �������������� ======
//  if(CurEtal>3)
//    {
//    jt=0;
//    for(j=0;  j<NumEtal; j++)
//      {
//      if(RecObj[j].No>0)
//        {
//        PXeYe[jt]=ParmAffineEtals[j].Xc;
//        PXoYo[jt++]=RecObj[j].Xc[0];
//        PXeYe[jt]=ParmAffineEtals[j].Yc;
//        PXoYo[jt++]=RecObj[j].Yc[0];
//        }
//      if(jt>7) break;
//      }
//
//
//    mPT=8;
//    mPT2=mPT*mPT;
//    V=new long double [mPT2];
//    for(i=0; i<4; i++)
//      { j=i*mPT; ii=i+i;
//      Mpg[j++]=PXoYo[ii]; Mpg[j++]=PXoYo[ii+1];
//      Mpg[j++]=(long double)1;
//      Mpg[j++]=-(PXoYo[ii]*PXeYe[ii]); Mpg[j]=-(PXoYo[ii+1]*PXeYe[ii]);
//      B[i]=PXeYe[ii];
//      jt=i+4; j=jt*mPT+3;
//      Mpg[j++]=-(PXoYo[ii]*PXeYe[ii+1]); Mpg[j++]=-(PXoYo[ii+1]*PXeYe[ii+1]);
//      Mpg[j++]=PXoYo[ii]; Mpg[j++]=PXoYo[ii+1];
//      Mpg[j]=(long double)1;
//      B[jt]=PXeYe[ii+1];
//      }
//    for(i=0; i<mPT2; i++)  V[i]=Mpg[i];
//    longMinv(V,mPT,&detV,L,M);
//
//    if(detV!=0.)
//      {
//      int k;
//      long double IE[64];
//      for(i=0; i<mPT; i++)
//        {
//        for(j=0; j<mPT; j++)
//          {  jt=i*mPT;
//          IE[jt+j]=(long double)0;
//          for(k=0; k<mPT; k++)  IE[jt+j]+=V[jt+k]*Mpg[k*mPT+j];
//          }
//        }
//      for(i=0; i<mPT; i++)
//        { jt=i*mPT;
//        A[i]=(long double)0;
//        for(j=0; j<mPT; j++) A[i]+=V[jt+j]*B[j];
//        }
//      }
////======= ���������� �������� � ������� ��������� ������� ===
//    for(CurEtal=0; CurEtal<NumEtal; CurEtal++)
////    for(ii=0; ii<KObj; ii++)
//      {
//      for(i=0; i<RecObj[CurEtal].No; i++)
//        { ii=RecObj[CurEtal].Num[i]-1;
//        AP=ii*LP;
//        Lobj=PaspKmp[AP+3l];
//        N=Lobj/2;
//        MoveObj=(PaspKmp[AP+1l]-1)*4096l+(PaspKmp[AP+2l]-1);
//        for(j=0; j<Lobj; j++) XYObj[j]=MetrKmp[MoveObj+j];
//        for(j=0; j<N; j++)
//          { jt=j+j;
//          Px=(long double)XYObj[jt]; Py=(long double)XYObj[jt+1];
//          Px*=StepObjX; Py*=StepObjY;
//          Zt=A[3]*Px+A[4]*Py+(long double)1;
//          Xt=(A[0]*Px+A[1]*Py+A[2])/Zt;
//          Yt=(A[5]*Px+A[6]*Py+A[7])/Zt;
//          XYRest[jt]=Xt/StepEtalX+0.5;
//          XYRest[jt+1]=Yt/StepEtalY+0.5;
//          }
//        //------- ������ ���������������� ������� ---------
//        }
//      }
//    }
   return 0;
  }
//---------------------------------------------------------------------------
// ������� ������� �������������
int AutoRecognition (TScene *SceneIn, char NameFileEtalon[],long *AllKmpObj,
					 double DpiSceneX,double DpiSceneY,bool useKmpObj,bool thread,Param_Recogn &Param_Rec)
 {
//  int SMBObj;
  long NbPk;
  double *XXo,*YYo,*XX,*YY;
  double *Sg;
  CapEtalons *CEM;
  long *ZME;
  FILE* HederFileEtalon;
  long LenFile;
  int Lp=16;
  int KetImage=4;
  int Ny=20;
  int num_obj=-1;
  long Mn,Mk,KObj,KsvObj;
  long NumPoints;
  long KPMetr,SNE,SPE,NbPi;
  T_Point maxXY;
  T_Point minXY;
  TContour *wContour;
  int NumberFileEtalon;
  FILE *pFileEtalons;
  char *Ptr;
  double StepX,StepY;
  ParmAffineEtal *ParmAffineEtals;
  RecognizedObject *RecObj;
  ParmAffine *ParmTrans;
  double *Xe,*Ye,*Se;
  double *Xd,*Yd,*Sd;
  double *Ct;
  double StepEtalX,StepEtalY,StepObjX,StepObjY; //,StepRestX,StepRestY;
  long *AllEtal,*AllKmp,*CapKmp;
  long XDmax,XDmin,YDmax,YDmin;
  long N_Dmax;
  long SizeFileEtal;
  int TypeScene;
  int ThrEtal;
  int ThrObj;
  int NumEtal;
  char Txt[256];
  double SE1;
  long *PaspKmp;
  long i,AP,Dx,Dy,N,G;
  SE1=20.;
  //? SimPor=0.95;
  int Pmax,Pmin;
  double See;
 // int SMDObj;
  int KDObj;
  Pmax=1000; Pmin=5;    // ��������
 // double Ds;
  int ErrorCode, NumParm;
  long *MetrKmp;
  StepObjX=25.4/DpiSceneX;
  StepObjY=25.4/DpiSceneY;     //  ��� ������������, ������ �������
  //StepX=stepxin;
  //StepY=stepyin;
  //StepX=25.4/300.; StepY=25.4/300.;
  //? SelectData=false;
  ThrEtal=2;
  ThrObj=1;
 //? NObj=0;
  TypeScene=0;
  if(strlen(NameFileEtalon)==0)
	{
  /*
    pFileEtalons = fopen("D:\\PCL\\DF\\Lebedev\\ShapeRecognition\\3D-Navigation\\ChooseKadr\\table\\NoSmoke.etl", "rb");
    NumberFileEtalon = fileno(pFileEtalons);
	SizeEtalons=filelength(NumberFileEtalon);
	NumPoints=SizeEtalons/sizeof(unsigned int);
    if(NumPoints>0)
      {
	  if(fread(ZME, sizeof(int), KPMetr, pFileEtalons) != SizeEtalons / sizeof(unsigned int))
        {
        if(NumParm>0) ErrorCode = 1;
        else
          {
		  strcpy(Txt,"������ ������ ������� ��������");
          }
        goto END;
        }
      }
   */
     Error_View("��������� �����","�� ������ ��������� ���� �����");
     goto END;
    }
  else
    {
  //  HederFileEtalon=open(NameFileEtalon,O_BINARY|O_RDWR);
  //  if(HederFileEtalon<1)
      HederFileEtalon = NULL;
	  HederFileEtalon=fopen(NameFileEtalon,"r");
	  if(HederFileEtalon==NULL)
	  {
	 // strcpy(Txt,);
	  Error_View("��������� �����","�� ������ ��������� ����");
	  // MessageBox::Show(Txt, "", MessageBoxButtons::OK, MessageBoxIcon::Information);
      return(1);
      }
	strcpy(Txt,NameFileEtalon);
	Ptr=strrchr(Txt,'.');
    Ptr++;
       if(strcmp(Ptr,"etl")==0) TypeScene=1;
       else
       {
        if(strcmp(Ptr,"kmp")==0) TypeScene=2;
        else
        { 
			if(strcmp(Ptr,"etm")==0) TypeScene=3;
            else TypeScene==-1;
        }
       }
      if(TypeScene==-1)
      {
    //  strcpy(Txt,);
	   Error_View("��������� �����","�� ����� ��� ��������� ����� �����");
	  // MessageBox::Show(Txt, "повторите сеанс", MessageBoxButtons::OK, MessageBoxIcon::Information);
       return(1);
      }
      if(TypeScene==1)
      { StepEtalX=25.4/300.;  StepEtalY=25.4/300.; 
		SizeFileEtal=b_filesize(NameFileEtalon); // SizeFileEtal=filelength(HederFileEtalon);
		NumPoints=SizeFileEtal/sizeof(unsigned int);
	 //?	SMEtal=NumPoints;
		KPMetr=NumPoints; //+SMDObj/2+KDObj;
		ZME= new long[KPMetr];
        if(ZME==NULL)
		{
         if(NumParm>0) ErrorCode = 1;
         else
		  {
		   Error_View("Error","��� ������ ��� �������");
          }
         goto END;
        }
	 // LenFile=read(HederFileEtalon,ZME,SizeFileEtal);
	 LenFile = fread(ZME,sizeof(long),SizeFileEtal,HederFileEtalon);
   /* ?
	  if(LenFile!=SizeFileEtal)
		{
	   // strcpy(Txt,);
		 Error_View("��������� �����","������ ��� ������ ��������� ����� �����");
		// MessageBox::Show(Txt, "повторите сеанс", MessageBoxButtons::OK, MessageBoxIcon::Information);
		 return(1);
		}
	*/
      }
      else
      {
	   fseek(HederFileEtalon,0l,SEEK_SET);
	  // SizeFileEtal=filelength(HederFileEtalon);
	   SizeFileEtal=b_filesize(NameFileEtalon);
	   AllEtal=new long [SizeFileEtal/4+1];
       if(AllEtal==NULL)
        {
       //  strcpy(Txt,);
		  Error_View("��������� �����","��� ������ ��� ��������� ���� �����");
         // MessageBox::Show(Txt, "повторите сеанс", MessageBoxButtons::OK, MessageBoxIcon::Information);
		 return(1);
        }
	  // LenFile=read(HederFileEtalon,AllEtal,SizeFileEtal);
	   LenFile=fread(AllEtal,sizeof(long),SizeFileEtal,HederFileEtalon);
	   if(LenFile!=SizeFileEtal)
        {
       //  strcpy(Txt,);
		  Error_View("��������� �����","������ ��� ������ ��������� ����� �����");
         // MessageBox::Show(Txt, "повторите сеанс", MessageBoxButtons::OK, MessageBoxIcon::Information);
         return(1);
        }
      }
    }

	if(TypeScene==0||TypeScene==1)
	{

	 NumEtal=0;  NbPk=0; NbPi=0;
	 CEM=(struct CapEtalons *)ZME;
	 for(;;)
	 {
	  NbPk+=CEM[NbPi].N;
	  NbPi+=CEM[NbPi].N+1;
	  NumEtal++;
	  if(NbPi>=NumPoints) break;
	 }
	 KetImage=NumEtal;
//======== ��������� ������ ��� ������� � ������� ===========
	 SNE=NumEtal; //+KDObj;
	 SPE=KPMetr+NumEtal; //+SMDObj+KDObj+KDObj;
	 ParmAffineEtals=new struct ParmAffineEtal[SNE];
	 if(ParmAffineEtals==NULL)
	 {
	  if(NumParm>0) ErrorCode = 1;
	  else
	  {
	   Error_View("Error","��� ������ ��� ��������� ��������");
	  }
	  goto END;
	 }
	 ParmTrans= new struct ParmAffine[SNE];
	 RecObj=new struct RecognizedObject[SNE];
	 for(int i7=0;i7<SNE;i7++)
		RecObj[i7].No=0;
	 if(RecObj==NULL)   // ParmTrans==NULL||
	 {
	  if(NumParm>0) ErrorCode = 1;
	   else
	   {
		Error_View("Error","��� ������ ��� ��������� ������������ ��������");
	   }
	   goto END;
	 }
	 Xd=new double[1024];
	 Yd=new double[1024];
	 Sd=new double[1024];
	 Ct=new double[2024];
	 Xe=new double[SPE+1];
	 Ye=new double[SPE+1];
	 Se=new double[SPE+1];
	 XXo=new double[2048];
	 YYo=new double[2048];
	 XX=new double[2048];
	 YY=new double[2048];
	 Sg=new double[1024];
	if(Xe==NULL||Ye==NULL||Se==NULL)
	  {
	   if(NumParm>0) ErrorCode = 1;
		else
		{
		 Error_View("Error","��� ������ ��� ������� ��������");
		}
	   goto END;
	  }
   ReadEtalons(NumEtal,ParmAffineEtals,ParmAffineEtals,StepEtalX,StepEtalY,SE1,Ny,Sg,Xd,Yd,Sd,ZME,ThrEtal,XXo,YYo,XX,YY,Ct,Xe,Ye,Se,NbPk,See);
  }
// Xmin, Xmax, Ymin, Ymax растра
  if(useKmpObj)
  {
   AllKmp=AllKmpObj;
   CapKmp=AllKmp;
   PaspKmp=&AllKmp[256];
   KsvObj=CapKmp[35];
   MetrKmp=&AllKmpObj[256+KsvObj*256];
   XDmin=CapKmp[15]; XDmax=CapKmp[16];
   YDmin=CapKmp[17]; YDmax=CapKmp[18];
 //? XRmin=XDmin; XRmax=XDmax;
 //? YRmin=YDmin; YRmax=YDmax;
   KObj=CapKmp[36];
  }
  else
  {
   KObj = SceneIn->GetNumObjects();
   maxXY = SceneIn->FindMaxXY();
   minXY = SceneIn->FindMinXY();
   XDmin=minXY.GetX(); XDmax = maxXY.GetX();
   YDmin=minXY.GetY(); YDmax = maxXY.GetY();
  }

  Mn=1; Mk=KObj;

//?  KPObj=0; SMPObj=0;  KBObj=0; SMBObj=0;


	for(i=0; i<Mk; i++)
	{
	 if(useKmpObj)
	 {
	  AP=i*Lp;
	  Dx=PaspKmp[AP+6l]-PaspKmp[AP+5l];
	  Dy=PaspKmp[AP+8l]-PaspKmp[AP+7l];
	  N=PaspKmp[AP+3l];
	 }
	 else
	 {
	  wContour = SceneIn->GetContour(i);
	  maxXY = wContour->FindMaxXY();
	  minXY = wContour->FindMinXY();
	  Dx=maxXY.GetX() - minXY.GetX();
	  Dy=maxXY.GetY() - minXY.GetY();
	  N=wContour->GetNumPoints();
	 }
//-------- выбираем контур с макс числом точек --------
//=========== G - габарит  ==========
//======= KDObj - количество дискретных объектов =======
//======= SMDObj - к-во точек описания дискретных объектов =======
	if(N_Dmax<N) N_Dmax=N;
	if(Dx>Dy) G=Dx;
	else G=Dy;
	if((G>=Pmin)&&(G<=Pmax)&&(N<2048))
	{
	 KDObj++;
  //?	 SMDObj+=N;
	}
   }
 //? DiscreteObjects=new struct DataRecObj[KDObj+1];
 // BigObjects=new unsigned int[SMBObj*5/4+4*KBObj+1];
 // XYObj=new long[N_Dmax];
 // RecognitionObject(SceneIn, KObj);
  RecognitionObject(SceneIn, KObj,NumEtal,ParmAffineEtals,ParmAffineEtals,StepObjX,StepObjY,SE1,Sg,Xd,Yd,Sd,Xe,Ye,Se,useKmpObj,RecObj,ParmTrans,StepEtalX,StepEtalY,StepObjX,StepObjY,Ny,ThrObj,KetImage,XXo,YYo,XX,YY,Ct,PaspKmp,XDmax,XDmin,YDmax,YDmin,Pmax,Pmin,NumParm,MetrKmp,thread,Param_Rec);


END:
 // if(BigObjects!=NULL) delete BigObjects;
//?  if(DiscreteObjects!=NULL) delete DiscreteObjects;
 // fclose(pFileEtalons);
//  if(SMEtal<1) unlink(NameFileEtalons);
  if(ZME!=NULL) delete[] ZME;
 // if(NumSmall5!=NULL) delete NumSmall5;
//  if(XYObj!=NULL) delete XYObj;
  if(ParmAffineEtals!=NULL) delete[] ParmAffineEtals;
  if(RecObj!=NULL) delete[] RecObj;
  if(ParmTrans!=NULL) delete[] ParmTrans;
  if(Xe!=NULL) delete[] Xe;
  if(Ye!=NULL) delete[] Ye;
  if(Se!=NULL) delete[] Se;
  delete[] Ct;
  delete[] Xd;
  delete[] Yd;
  delete[] Sd;
  delete[] XXo;
  delete[] YYo;
  delete[] XX;
  delete[] YY;
  delete[] Sg;
//?  if(AllKmp!=NULL) delete AllKmp;
  return 0;
  }
//---------------------------------------------------------------------------
  int Save_KML (char* sFileNameEtal, char* sFileNameKM, double stepxin, double stepyin)
  {
 /*
  int NumEtal; // Lp=16,
//  long Mn,Mk,KObj;
  long NumPoints;
  double *Xe,*Ye,*Se;
//  union { long ll; float fl; unsigned short Sh[2];} ul;
  struct ParmAffineEtal *ParmEtals;
  long KPMetr,NbPi,SNE,SPE;
//  TPoint maxXY;
//  TPoint minXY;
//;  TContour *wContour;
  int NumberFileEtalon;
  FILE *pFileEtalons;
  double StepX,StepY;

  
 

  Nb=4; SE1=20.;
 // SimPor=0.95;
 // Pmax=1000; Pmin=5;    // габариты
  StepX=25.4/stepxin;
  StepY=25.4/stepyin;
//  OutMin=InMin=Pmin;
//  SelectData=false;
//  ThrDO=1;
//  ThrBO=2;
//  AdrBig=1;
  // NObj=0;

  pFileEtalons = fopen(sFileNameEtal, "rb");


  NumberFileEtalon = fileno(pFileEtalons);
  SizeEtalons=filelength(NumberFileEtalon);
  NumPoints=SizeEtalons/sizeof(unsigned int);
  SMEtal=NumPoints;
  KPMetr=NumPoints; //+SMDObj/2+KDObj;
  ZME= new long[KPMetr];
  if(ZME==NULL)
    {
	if(NumParm>0) ErrorCode = 1;
    else
      {
		  strcpy(Txt,"Нет памяти под эталоны");
      }
    goto END;
    }

  NumEtal=0;  NbPk=0; NbPi=0;
  KPMetr=NumPoints;
  if(NumPoints>0)
    {

	if(fread(ZME, sizeof(int), KPMetr, pFileEtalons) != SizeEtalons / sizeof(unsigned int))
      {
	  if(NumParm>0) ErrorCode = 1;
      else
        {
		strcpy(Txt,"Ошибка чтения метрики эталонов");
        }
      goto END;
      }

	CEM=(struct CapEtalons *)ZME;
    for(;;)
      {
	  NbPk+=CEM[NbPi].N;
	  NbPi+=CEM[NbPi].N+1;
      NumEtal++;
      if(NbPi>=NumPoints) break;
      }
    }


//======== отведение памяти под объекты и эталоны ===========
  SNE=NumEtal+KDObj;
  SPE=KPMetr; //+SMDObj+KDObj+KDObj;
  ParmEtals=new struct ParmAffineEtal[SNE];
  if(ParmEtals==NULL)
    {
    if(NumParm>0) ErrorCode = 1;
    else
      {
	  strcpy(Txt,"Нет памяти под параметры эталонов");
      }
    goto END;
    }
	 ParmAffineEtals=new struct ParmAffineEtal[SNE];
     if(ParmAffineEtals==NULL)
     {
	  if(NumParm>0) ErrorCode = 1;
	   else
       {
		strcpy(Txt,"Нет памяти под параметры эталонов");
       }
      goto END;
     }
  Xe=new double[SPE];
  Ye=new double[SPE]; 
  Se=new double[SPE];
  if(Xe==NULL||Ye==NULL||Se==NULL)
    {
	if(NumParm>0) ErrorCode = 1;
     else
     {
      strcpy(Txt,"Нет памяти под метрику эталонов");
     }
     goto END;
    }
  DiscreteObjects=new struct DataRecObj[KDObj+1];
  //BigObjects=new unsigned int[SMBObj*5/4+4*KBObj+1];
  //XYObj=new long[N_Dmax];

  ReadEtalons(NumEtal,ParmEtals,StepX,StepY,SE1,Xe,Ye,Se);





  FILE *pHeaderKM;

  pHeaderKM = fopen(sFileNameKM, "wb");
  if (pHeaderKM == NULL) return -1;
  
  int na = ParmEtals[NumEtal-1].Adr+ParmEtals[NumEtal-1].Ne;
  fwrite (&NumEtal, sizeof(int), 1, pHeaderKM);
  fwrite (ParmEtals, sizeof(ParmEtal), NumEtal, pHeaderKM);
  fwrite (Xe, sizeof(double), na, pHeaderKM);
  fwrite (Ye, sizeof(double), na, pHeaderKM);
  fwrite (Se, sizeof(double), na, pHeaderKM);





END:
 // if(BigObjects!=NULL) delete BigObjects;
 // if(DiscreteObjects!=NULL) delete DiscreteObjects;
  fclose(pHeaderKM);
  fclose(pFileEtalons);
 // if(SMEtal<1) unlink(NameFileEtalons);
  if(ZME1!=NULL) delete ZME1;
 // if(NumSmall5!=NULL) delete NumSmall5;
  if(XYObj!=NULL) delete XYObj;
  if(ParmEtals!=NULL) delete ParmEtals;
  if(Xe!=NULL) delete Xe;
  if(Ye!=NULL) delete Ye;
  if(Se!=NULL) delete Se;
 // if(AllKmp!=NULL) delete AllKmp;
*/
  return 0;
  }
//---------------------------------------------------------------------------

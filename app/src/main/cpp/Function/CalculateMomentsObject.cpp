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
extern double Xpr[],Ypr[],Spr[];
extern double Xc_pr,Yc_pr,Sr_pr,Mxx_pr,Mxy_pr,Myy_pr;
#include <math.h>
void CalculateMomentsObject(int N)
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
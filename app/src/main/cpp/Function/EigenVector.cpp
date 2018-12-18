//==========  вычисление собственных векторов и значений ===============
//---------------------------------------------------------------------------
#pragma warn -pck
#pragma option -a1
//---------------------------------------------------------------------------
#include <math.h>
void  EigenVector(int n, double *MAC, float *MSBEV, bool GetEigen)
  {
//------------ входные данные ---------
// n - размерность матрицы
// MAC - матрица автокорреляции (верняя треугольная часть - код 1). В прцессе 
//               счета разрушается и на диагоналальных элементах будут находиться 
//               собственные значения (СЗ)  
// GetEigen - режим вычисления собственных значений:  да/нет=true/(!true)

//------------ выходные данные ---------
// MAC - на диагоналальных элементах будут находиться собственные значения
// MSBEV - массив собственных векторов в порядке убывания СЗ 

  int i,j,k,iq,jq,ij,m,nn,ind,l,mq,lq,lm,ll,mm,ilq,imq,im,il;
  int ilr,imr,ur,mr4,ur4;
  float z;
  double range=1.0e-6,x,y,anorm,anrmx,thr,sinx,cosx,sinx2,cosx2,sincs;
  if(GetEigen==true)
    { iq=-n; i=-1;
    while(++i<n)
      {	iq+=n; j=-1;
      while(++j<n)  { ij=iq+j; MSBEV[ij]=0.; if(i==j) MSBEV[ij]=1.0; }
      }
    }
  anorm=0.0; i=-1;
  while(++i<n)
    { j=i;
    while(++j<n) { ij=i+(j*(j+1))/2; anorm+=MAC[ij]*MAC[ij];}
    }
  if(anorm>0.)
    {  /* открыта до 165 */
    anorm=1.414*sqrt(anorm);
    anrmx=anorm*range/(double)n;
    ind=0; thr=anorm;
    do
      { thr/=(double)n;
      do
        { ind=0; l=0;
        do
          { l++; m=l;
          do
            { m++;
            mq=(m*m-m)/2; lq=(l*l-l)/2; lm=l+mq-1;
            if(fabs(MAC[lm])>=thr)
              { /* открыта до 130 */
              ind=1; ll=l+lq-1; mm=m+mq-1;
              x=0.5*(MAC[ll]-MAC[mm]);
              y=-MAC[lm]/sqrt(MAC[lm]*MAC[lm]+x*x);
              if(x<0.) y=-y;
              sinx=y/sqrt(2.0*(1.0+sqrt(1.0-y*y)));
              sinx2=sinx*sinx;
              cosx=sqrt(1.0-sinx2);
              cosx2=cosx*cosx; sincs=sinx*cosx;
              ilq=n*(l-1); imq=n*(m-1); i=-1;
              while(++i<n)
                {  /*  цикл до 125 */
                iq=(i*i+i)/2;
                if((i+1)!=l)
                  { /* открыта до 115 */
                  if((i+1)!=m)
                    { /* открыта до 115 */
                    if(m>i) im=i+mq; else im=m+iq-1;
                    if(l>i) il=i+lq; else il=l+iq-1;
                    x=MAC[il]*cosx-MAC[im]*sinx;
                    MAC[im]=MAC[il]*sinx+MAC[im]*cosx;
                    MAC[il]=x;
                    }
                  }
                if(GetEigen==true)
                  { ilr=ilq+i; imr=imq+i;
                  x=MSBEV[ilr]*cosx-MSBEV[imr]*sinx;
                  MSBEV[imr]=MSBEV[ilr]*sinx+MSBEV[imr]*cosx;
                  MSBEV[ilr]=x;
                  }
                }
              x=2.0*MAC[lm]*sincs;
              y=MAC[ll]*cosx2+MAC[mm]*sinx2-x;
              x+=MAC[ll]*sinx2+MAC[mm]*cosx2;
              MAC[lm]=(MAC[ll]-MAC[mm])*sincs+MAC[lm]*(cosx2-sinx2);
              MAC[ll]=y; MAC[mm]=x;
              }
            }
          while(m!=n);
          }
        while(l!=(n-1));
        }
      while(ind==1);
      }
    while(thr>anrmx);
    }
  iq=-n; i=-1;
  while(++i<n)
    { iq+=n; ll=i+(i*i+i)/2; jq=n*(i-1); j=i-1;
    while(++j<n)
      { jq+=n; mm=j+(j*j+j)/2;
      if(MAC[ll]<MAC[mm])
        { x=MAC[ll]; MAC[ll]=MAC[mm]; MAC[mm]=x;
        if(GetEigen==true)
          { k=-1;
          while(++k<n)
            { ilr=iq+k; imr=jq+k; x=MSBEV[ilr]; MSBEV[ilr]=MSBEV[imr];
            MSBEV[imr]=x;
            }
          }
        }
      }
    }
  }
//---------------------------------------------------------------------------

#ifndef TPRO_HH
#define TPRO_HH
#pragma once
#include <stdio.h>
#include <stdlib.h>
#include "TShtrih.h"
#include <list>
#include <__clang_cuda_cmath.h>

using namespace std;

class TPRO {

	
public:
	list<TShtrih> *ListShtrih_PRO;          // ������ ������� ���
    int NumShtrih;                         // ���������� ������� � ���

public:
    TPRO();
	TPRO (const TPRO &obj);
    ~TPRO();
    // ���������� ������ ������� � ����� ���
	void AddListShtrih(list<TShtrih> *addlistshtrih);   
    // ���������� � ����� ������ �������
	void AddShtrih(TShtrih &addshtrih);       
	// ��������� ���
    //void DrawPROOnPicture(Bitmap imagein, int zoom, Color color;  
	// �������� ����������� �� ����� ���
    bool CheckPRO_Shtrih(TShtrih &shtrih);  
    // ������� ����������� ���
	void Clear();

};
#endif

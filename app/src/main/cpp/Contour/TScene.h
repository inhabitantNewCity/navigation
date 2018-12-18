#ifndef TScene_HH
#define TScene_HH
#pragma once
#include <stdio.h>
#include <vector>
#include "TContour.h"
//#include "b_file_size.h"

// ����� �����
class TScene {

private:
	int numObj;                             // ���������� ��������
	vector <TContour> *Contours;              // ������ ��������

public:
	TScene ();
	// ����������� �����������
	TScene (const TScene &obj);
	~TScene();

	// �������� � ����� ������
	int AddContour (TContour *addContour);
	// ���������� �����
  //	void DrawScene (Graphics ^gr, int Height);
	// �������� ������� ��� �������
	int WriteContours (char *pFileName);
	// ������� ������� �� ����� ��������
	int ReadContours (char *pFileName);
	// ��������� �������
	TContour *GetContour (int nContour);
	// �������� �� ������� �����
	bool isEmpty ();
	// ���������� ���������� �������� � �����
	int GetNumObjects ();
	// ���������� �������� ����� � �������������� ������� ���������
	void toMathCoord (int height);
	// ���������� ������� ������� � ��������� ������� ���������
	void toLocalSysCoord ();
	// ����� max �� X � Y
	T_Point FindMaxXY ();
	// ����� min �� X � Y
	T_Point FindMinXY ();

};
#endif

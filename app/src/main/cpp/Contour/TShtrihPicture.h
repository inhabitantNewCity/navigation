#pragma once
#include <list>
#include <fstream>
#include "TShtrih.h"

using namespace std;

class TShtrihPicture {

public:
    list<TShtrih> *MasShtrih;
    int Width;
    int Height;

public:
    TShtrihPicture();
	TShtrihPicture(int width, int height);
	TShtrihPicture (const TShtrihPicture &Obj);

    ~TShtrihPicture();
    void ClearShtihPicture();
	// ������������ �������
    int ConvertToShtrih (unsigned char* mas);
	// ����������� ������������ ����� ������
    void Get_SWp_SWs_Shtrih(TShtrih &shtrih, int &SWs, int &SWp);
	// ������ ������� � ����
	void SaveShtrihToFile (char *name);
	// ��������� ������ �� ������
    TShtrih * GetShtrFromList (int NumStr, int Index);
	// ��������� ������ ������ ������ � ���������� ������
	TShtrih* GetTopLeftShtrih (TShtrih *pShtr);
    // ��������� ������ ������ ������ � ��������� ������
	TShtrih* GetBottomLeftShtrih (TShtrih *pShtr);
	// ��������� ������ ������� ������ � ���������� ������
	TShtrih* GetTopRightShtrih (TShtrih *pShtr);
	// ��������� ������ ������� ������ � ��������� ������
	TShtrih* GetBottomRightShtrih (TShtrih *pShtr);
	// ����������� ����������� ������ �� ������� �������
    int GetDirectionInExtContour (TShtrih *pShtr, int prev, int &next);
	// ��������� ����������� ������ � ������
	TShtrih* GetPrevShtr (TShtrih *pShtr);
	// ��������� ���������� ������ � ������
	TShtrih* GetNextShtr (TShtrih *pShtr);
	// ����������� ����������� ������ �� ���������� �������
	int GetDirectionInterContour (TShtrih *pShtr, int prev, int &next);
};
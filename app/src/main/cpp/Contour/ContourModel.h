#ifndef ContourModel_HH
#define ContourModel_HH
#pragma once
#include "TScene.h"
#include "TShtrihPicture.h"

// ������� ������� ������������� ��������
void FindListContour (TShtrihPicture *shtrpic, TScene *SceneIn);
// ������������� �������� �������
void FindExternalContour (TShtrihPicture *shtrpic, TContour *extcontour, int NumStr, int NumShtr);
// ������������� ����������� �������
void FindInternalContour (TShtrihPicture *shtrpic, TContour *intercontour, int NumStr, int NumShtr);
// �������� �� ���������� ����� � ������ ��������
bool CheckPointInListContour (TScene *wSceneIn, int x, int y);
#endif

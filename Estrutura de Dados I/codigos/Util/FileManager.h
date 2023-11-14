#ifndef FILEMANAGER_H
#define FILEMANAGER_H

#ifdef __linux__
    #define DIRECTORY_NAME "./Data/"
#endif // _WIN32

#ifdef _WIN32
    #define DIRECTORY_NAME ".\\Data\\"
#endif

#define NUMBER_FILE_NAME "numberlist"
#define TIME_FILE_NAME "exectime"
#define FILE_EXTENSION ".dat"

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "NumberVector.h"

int getFileSize(char* fileName);
char* getFilePathFromVectorSize(int vectorSize, char* fileName);
numberVector_t readNumberBinaryFile(int vectorSize);
void saveTimeToFile(int vectorSize, double time, int nExecutions);

#endif // FILEMANAGER_H

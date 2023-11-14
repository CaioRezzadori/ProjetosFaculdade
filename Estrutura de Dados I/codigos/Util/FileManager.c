#include "FileManager.h"

int getNDigits(int number)
{
    int nDigits = 0;
    while (number != 0) {
        number /= 10;
        ++nDigits;
    }
    return nDigits;
}

int getFileSize(char* fileName)
{
    FILE* pFile;

    pFile = fopen(fileName, "rb");

    // checking if the file exist or not
    if (pFile == NULL) {
        printf("File Not Found!\n");
        return -1;
    }

    fseek(pFile, 0, SEEK_END);

    // calculating the size of the file
    long int fileSize = ftell(pFile);
    fclose(pFile);
    return fileSize;
}

char* getFilePathFromVectorSize(int vectorSize, char* fileName)
{
    char *filePath, *sizeAsString;
    int filePathSize, nDigitsInSize;
    nDigitsInSize = getNDigits(vectorSize);
    filePathSize = strlen(DIRECTORY_NAME) + strlen(fileName) +
        nDigitsInSize + strlen(FILE_EXTENSION) + 1;

    sizeAsString = (char*) malloc((sizeof(char)*nDigitsInSize)+1);
    sprintf(sizeAsString, "%d", vectorSize);

    filePath = (char*) malloc(sizeof(char)*filePathSize);

    //Directory + Base File Name + Vector Size + Extension
    strcpy(filePath, DIRECTORY_NAME);
    strcat(filePath, fileName);
    strcat(filePath, sizeAsString);
    strcat(filePath, FILE_EXTENSION);

    #ifdef DEBUG
        printf("Number file Path: %s\n", filePath);
    #endif

    return filePath;
}

numberVector_t readNumberBinaryFile(int vectorSize)
{
    numberVector_t numberVector;
    FILE* pFile;
    char *fileName;

    fileName = getFilePathFromVectorSize(vectorSize, NUMBER_FILE_NAME);

    numberVector.vectorSize = getFileSize(fileName)/sizeof(int);

    pFile = fopen(fileName, "rb");
    size_t result;

    // checking if the file exist or not
    if (pFile == NULL) {
        printf("File Not Found!\n");
        exit(EXIT_FAILURE);
    }

    numberVector.numbers = (int*) malloc(sizeof(int)*numberVector.vectorSize);

    result = fread(numberVector.numbers, sizeof(int), numberVector.vectorSize, pFile);
    if (result != numberVector.vectorSize)
        printf("Reading error\n");
    // closing the file
    fclose(pFile);
    return numberVector;
}

void saveTimeToFile(int vectorSize, double time, int nExecutions)
{
    FILE *pFile;
    char *fileName;

    fileName = getFilePathFromVectorSize(nExecutions, TIME_FILE_NAME);

    pFile = fopen(fileName, "a+b");

    // checking if the file exist or not
    if (pFile == NULL) {
        printf("File Not Found!\n");
        exit(EXIT_FAILURE);
    }

    fprintf(pFile, "%d %f\n", vectorSize, time);
    fclose (pFile);
    return;
}

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <limits.h>

#include "NumberVector.h"
#include "StopWatch.h"
#include "FileManager.h"

int buscaSequencial(numberVector_t numberVector, int elementoProcurado){
    int N = numberVector.vectorSize;
    for(int i = 0; i < N; i++){
        if(numberVector.numbers[i] == elementoProcurado){
            return i;
        }
    }
    return -1; //Erro na busca: nada foi encontrado
}

int main()
{
    numberVector_t numberVector;
    int nExecutions, vectorSize, indiceProcurado;
    double averageTime = 0.0;
    clock_t startTime, endTime;

    scanf("%d %d", &vectorSize, &nExecutions);

    numberVector = readNumberBinaryFile(vectorSize);

    for(int i = 0; i < nExecutions; ++i)
    {
        startTime = clock();
        indiceProcurado = buscaSequencial(numberVector, -32000); //Procurando elemento que não está no vetor para medir o pior caso do algoritmo
        endTime = clock();
        #ifdef DEBUG
            printf("The largest number is: %d\n", largestNumber);
        #else
            averageTime+=getExecutionTime(startTime, endTime);
        #endif
    }

    #ifndef DEBUG
        averageTime = averageTime/(double)nExecutions;
        saveTimeToFile(vectorSize, averageTime, nExecutions);
    #endif

    free(numberVector.numbers);

}

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <limits.h>

#include "NumberVector.h"
#include "StopWatch.h"
#include "FileManager.h"

numberVector_t inverterVetor(numberVector_t numberVector){
    numberVector_t vetorInvertido = numberVector;
    int N = numberVector.vectorSize;
    int aux;
    for(int i = 0; i < N/2; ++i){
        aux = vetorInvertido.numbers[i];
        vetorInvertido.numbers[i] = vetorInvertido.numbers[N - 1 - i];
        vetorInvertido.numbers[N - 1 - i] = aux;
    }

    return vetorInvertido;
}

int main()
{
    numberVector_t numberVector, vetorInvertido;
    int nExecutions, vectorSize;
    double averageTime = 0.0;
    clock_t startTime, endTime;

    scanf("%d %d", &vectorSize, &nExecutions);

    numberVector = readNumberBinaryFile(vectorSize);

    for(int i = 0; i < nExecutions; ++i)
    {
        startTime = clock();
        vetorInvertido = inverterVetor(numberVector);
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

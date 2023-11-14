#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <limits.h>
#include <math.h>

#include "NumberVector.h"
#include "StopWatch.h"
#include "FileManager.h"

//Código auxiliar para ordenar o vetor
void swap(int *a, int *b){ 
    int temp = *a; 
    *a = *b; 
    *b = temp; 
}

//Código para ordenar o vetor
void bubbleSort(int *v, int n){ 
    if (n < 1)return; 
 
    for (int i=0; i<n; i++) 
        if (v[i] > v[i+1]) 
            swap(&v[i], &v[i+1]);  
    bubbleSort(v, n-1); 
} 

//Busca binária iterativa
int buscaBinariaIterativa(numberVector_t vetorOrdenado, int elementoProcurado){
    int N = vetorOrdenado.vectorSize;
    int L = 0;
    int R = N - 1;
    int M;
    while(L <= R){
        M = (L + R)/2;
        if(vetorOrdenado.numbers[M] > elementoProcurado){
            R = M - 1;
        }
        else if(vetorOrdenado.numbers[M] < elementoProcurado){
            L = M + 1;
        }
        else{
            return M; //Elemento encontrado
        }
    }
    return -1; //Erro na busca: nada foi encontrado
}


int main()
{
    numberVector_t numberVector;
    int nExecutions, vectorSize, indiceProcurado;

    long double averageTime = 0.0;
    clock_t startTime, endTime;

    scanf("%d %d", &vectorSize, &nExecutions);

    numberVector = readNumberBinaryFile(vectorSize);
 
    //Ordenando o vetor
    bubbleSort(numberVector.numbers, numberVector.vectorSize);
    int aux = numberVector.numbers[numberVector.vectorSize - 2]; //Auxiliar para medir pior caso
    for(int i = 0; i < nExecutions; ++i)
    {
        startTime = clock();
        indiceProcurado = buscaBinariaIterativa(numberVector, aux); //Procurando elemento que não está no vetor para medir o pior caso do algoritmo
        endTime = clock();
        #ifdef DEBUG
            printf("The largest number is: %d\n", largestNumber);
        #else
            averageTime+=getExecutionTime(startTime, endTime);
        #endif
    }

    #ifndef DEBUG
        averageTime = pow(10, 6)*averageTime;
        averageTime = averageTime/(double)nExecutions;
        saveTimeToFile(vectorSize, averageTime, nExecutions);
    #endif

    free(numberVector.numbers);

}

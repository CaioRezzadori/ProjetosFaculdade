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

//Busca binária recursiva
int buscaBinariaRecursiva(numberVector_t vetorOrdenado, int elementoProcurado, int inicio, int fim){
    if(inicio <= fim){
        int meio =(inicio + fim)/2;

        if(vetorOrdenado.numbers[meio] > elementoProcurado){
          return buscaBinariaRecursiva(vetorOrdenado, elementoProcurado, inicio, meio - 1);
        }
        
        else if(vetorOrdenado.numbers[meio] < elementoProcurado){
            return buscaBinariaRecursiva(vetorOrdenado, elementoProcurado, meio + 1, fim);
        }
        else{
            return meio;
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
        indiceProcurado = buscaBinariaRecursiva(numberVector, aux, 0, numberVector.vectorSize - 1); //Procurando elemento que não está no vetor para medir o pior caso do algoritmo
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

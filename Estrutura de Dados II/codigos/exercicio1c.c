/*

Projeto 2 ED II

Gabriel Sanches da Silva - N.USP: 11884693
Caio Assumpcao Rezzadori - N.USP: 11810481
Joao Marcelo R. Junior- N.USP: 8531118

*/

#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Definição das variaveis que controlam a medição de tempo
clock_t _ini, _fim;

// Definição do tipo booleano
unsigned char typedef bool;
#define TRUE 1
#define FALSE 0

int *ler_inteiros(const char *arquivo, const int n) {
  FILE *f = fopen(arquivo, "r");

  int *inteiros = (int *)malloc(sizeof(int) * n);

  for (int i = 0; !feof(f); i++)
    fscanf(f, "%d\n", &inteiros[i]);

  fclose(f);

  return inteiros;
}

void inicia_tempo() {
  srand(time(NULL));
  _ini = clock();
}

double finaliza_tempo() {
  _fim = clock();
  return ((double)(_fim - _ini)) / CLOCKS_PER_SEC;
}

int busca_sequencial_transposicao(int arr[], int n, int chave) {
  int i;
  for (i = 0; i < n; i++) {
    if (arr[i] == chave) {
      if (i != 0) {
        // Realocação do item encontrado para uma posição anterior
        int temp = arr[i];
        arr[i] = arr[i - 1];
        arr[i - 1] = temp;
      }
      return i;
    }
  }
  return -1;
}

int main(int argc, char const *argv[]) {
  const int N = 50000;

  int *entradas = ler_inteiros("inteiros_entrada.txt", N);
  int *consultas = ler_inteiros("inteiros_busca.txt", N);

  // realiza busca sequencial
  unsigned encontrados = 0;
  inicia_tempo();
  for (int i = 0; i < N; i++) {
    // buscar o elemento consultas[i] na entrada
    encontrados += (busca_sequencial_transposicao(entradas, N, consultas[i]) != -1) ? 1 : 0;
  }
  double tempo_busca = finaliza_tempo();
  printf("Tempo de busca    :\t%fs\n", tempo_busca);
  printf("Itens encontrados :\t%d\n", encontrados);
  return 0;
}

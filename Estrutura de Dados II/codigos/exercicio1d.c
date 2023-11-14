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

typedef struct {
  int chave;
  int posicao;
} indices;

typedef struct {
  int tamanho;
  indices *entradas;
} tabela_indices;

typedef struct {
  int tamanho;
  int *valores;
} lista;


void troque(int *a, int *b) {
  int temp = *a;
  *a = *b;
  *b = temp;
}

//Funcao para  ordenar vetor de entradas (metodo de busca funciona apenas para listas ordenadas)
void heapify(int arr[], int n, int i) {
  int maior = i;
  int esquerda = 2 * i + 1;
  int direita = 2 * i + 2;

  if (esquerda < n && arr[esquerda] > arr[maior])
    maior = esquerda;

  if (direita < n && arr[direita] > arr[maior])
    maior = direita;

  if (maior != i) {
    troque(&arr[i], &arr[maior]);
    heapify(arr, n, maior);
  }
}

void heapSort(int arr[], int n) {
  for (int i = n / 2 - 1; i >= 0; i--)
    heapify(arr, n, i);

  for (int i = n - 1; i >= 0; i--) {
    troque(&arr[0], &arr[i]);

    heapify(arr, i, 0);
  }
}


tabela_indices criar_tabela_indices(int n, int s) {
  tabela_indices tabela;
  tabela.tamanho = n / s;
  tabela.entradas = (indices *)malloc(tabela.tamanho * sizeof(indices));
  return tabela;
}

void inserir_entrada(tabela_indices *tabela, int indice, int chave, int posicao) {
  tabela->entradas[indice].chave = chave;
  tabela->entradas[indice].posicao = posicao;
}

int busca_sequencial(lista *l, int chave) {
  int i;
  for (i = 0; i < l->tamanho; i++) {
    if (l->valores[i] == chave) {
      return i; // Retorna o índice do elemento encontrado
    }
  }
  return -1; // Retorna -1 se o elemento não for encontrado
}

int busca_indexada(tabela_indices *tabela, lista *l, int chave, int tamanho) {
  int i;
  for (i = 0; i < tabela->tamanho; i++) {
    if (tabela->entradas[i].chave <= chave) {
      int posicao = tabela->entradas[i].posicao;
      int resultado = busca_sequencial(l + posicao, chave);
      if (resultado != -1) {
        return resultado + (posicao * tamanho); // Retorna o índice global do elemento encontrado
      }
    }
  }
  return -1; // Retorna -1 se o elemento não for encontrado
}

int main(int argc, char const *argv[]) {
  const int N = 50000;
  const int tamanho_indice = 10000;
  
  unsigned encontrados = 0;
  int *entradas = ler_inteiros("inteiros_entrada.txt", N);
  int *consultas = ler_inteiros("inteiros_busca.txt", N);

  // ordenando tabelas entrada
  heapSort(entradas, N);


  // criando tabela de indice
  tabela_indices tabela_indices = criar_tabela_indices(N, tamanho_indice);
  lista *l = (lista *)malloc(tabela_indices.tamanho * sizeof(lista));

  for (int i = 0; i < tabela_indices.tamanho; i++) {
    l[i].tamanho = tamanho_indice;
    l[i].valores = (int *)malloc(tamanho_indice * sizeof(int));

    for (int j = 0; j < tamanho_indice; j++) {
      l[i].valores[j] = entradas[(i * tamanho_indice) + j];
    }

    inserir_entrada(&tabela_indices, i, l[i].valores[0], i);
  }

  // realizar consultas na tabela de indices
  inicia_tempo();
  for (int i = 0; i < N; i++) {
    // buscar o elemento consultas[i] na entrada
    encontrados += (busca_indexada(&tabela_indices, l, consultas[i], tamanho_indice) != -1) ? 1 : 0;
  }
  double tempo_busca = finaliza_tempo();
  printf("Tempo de busca    :\t%fs\n", tempo_busca);
  printf("Itens encontrados :\t%d\n", encontrados);
  return 0;

  for (int i = 0; i < tabela_indices.tamanho; i++) {
    free(l[i].valores);
  }
  free(l);
  free(tabela_indices.entradas);

  return 0;
}

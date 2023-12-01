/*

Projeto 2 ED II

Gabriel Sanches da Silva - N.USP: 11884693
Caio Assumpcao Rezzadori - N.USP: 11810481
Joao Marcelo R. Junior- N.USP: 8531118

*/


#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <assert.h>
#include <string.h>
#include <math.h>

// Definição das variaveis que controlam a medição de tempo
clock_t _ini, _fim;

// Definição do tipo booleano
typedef unsigned char bool;
#define TRUE  1
#define FALSE 0

// Definição do tipo string
typedef char * string;

#define MAX_STRING_LEN 20


unsigned converter(string s) {
   unsigned h = 0;
   for (int i = 0; s[i] != '\0'; i++) 
      h = h * 256 + s[i];
   return h;
}

string* ler_strings(const char * arquivo, const int n)
{
    FILE* f = fopen(arquivo, "r");
    
    string* strings = (string *) malloc(sizeof(string) * n);

    for (int i = 0; !feof(f); i++) {
        strings[i] = (string) malloc(sizeof(char) * MAX_STRING_LEN);
        fscanf(f, "%s\n", strings[i]);
    }

    fclose(f);

    return strings;
}

void inicia_tempo()
{
    srand(time(NULL));
    _ini = clock();
}

double finaliza_tempo()
{
    _fim = clock();
    return ((double) (_fim - _ini)) / CLOCKS_PER_SEC;
}



//Funcoes hash
struct tabela_hash{
    unsigned *chaves;
};

typedef struct tabela_hash hash;

void criar_hash(hash *t, unsigned B){
    t->chaves = (unsigned *) malloc(sizeof(unsigned) * B);
    for(int i = 0; i < B; i++){
        t->chaves[i] = -1; //Indica posicao vazia e nunca utilizada
    }
    return;
}

void destruir_hash(hash *t){
    free(t->chaves);
    return;
}

int inserir_hash(hash *t, unsigned x, unsigned (*funcao_hash) (unsigned, unsigned, unsigned), unsigned B){
    unsigned i = 0;
    unsigned posicao;
    while(i < B){
        posicao = (*funcao_hash)(x, i, B);
        if(t->chaves[posicao] == x){
            return -1; //Elemento já inserido
        }
        if((int) t->chaves[posicao] < 0){ //Avalia se a posicao eh valida para insercao (-1: Espaco nunca ocupado, -2: Elemento removido)
            t->chaves[posicao] = x;
            return i; //Retorna numero de colisoes
        }
        i++;
    }
    return -1; //Falha na insercao
}


int remover_hash(hash *t, unsigned x, unsigned (*funcao_hash) (unsigned, unsigned, unsigned), unsigned B){
    unsigned i = 0;
    unsigned posicao = (*funcao_hash)(x, i, B);
    while(i < B){
        if(t->chaves[posicao] == -1){
            return 1; //Elemento nunca foi inserido
        }
        else if(t->chaves[posicao] == x){ //Avalia se o elemento foi encontrado
            t->chaves[posicao] = -2;
            return 0;
        }
        i++;
        posicao = (*funcao_hash)(x, i, B);
    }
    return 2; //Tabela cheia
}

int buscar_hash(hash t, unsigned x, unsigned (*funcao_hash) (unsigned, unsigned, unsigned), unsigned B){
    unsigned i = 0;
    unsigned posicao = (*funcao_hash)(x, i, B);
    while(i < B && (int) t.chaves[posicao] != -1){
        if(t.chaves[posicao] == x){ //Avalia se a posicao eh valida para insercao (-1: Espaco nunca ocupado, -2: Elemento removido)
            return posicao;
        }
        posicao = (*funcao_hash)(x, i, B);
        i++;
    }
    return -1; //Falha na insercao
}

unsigned h_div(unsigned x, unsigned i, unsigned B)
{
    return ((x % B) + i) % B;
}

unsigned h_mul(unsigned x, unsigned i, unsigned B)
{
    const double A = 0.6180;
    return  ((int) ((fmod(x * A, 1) * B) + i)) % B;
}

unsigned h(unsigned x, unsigned i, unsigned B){
    return (h_mul(x, i, B) + i*h_div(x, i, B))%B;
}



int main(int argc, char const *argv[])
{
    const int N = 50000;
    const int M = 70000;
    const int B = 150001;

    unsigned colisoes = 0;
    unsigned encontrados = 0;

    string* insercoes = ler_strings("strings_entrada.txt", N);
    string* consultas = ler_strings("strings_busca.txt", M);


    //////////////////////////////////////////////////////


    // cria tabela hash com hash por hash duplo
    hash tabela;
    criar_hash(&tabela, B);

    // inserção dos dados na tabela hash
    inicia_tempo();
    for (int i = 0; i < N; i++) {
        // inserir insercoes[i] na tabela hash
        int aux = inserir_hash(&tabela, converter(insercoes[i]), h, B);
        colisoes += (aux != -1) ? aux : 0;
    }
    double tempo_insercao = finaliza_tempo();

    // busca dos dados na tabela hash
    inicia_tempo();
    for (int i = 0; i < M; i++) {
        // buscar consultas[i] na tabela hash
        encontrados += (buscar_hash(tabela, converter(consultas[i]), h, B) != -1) ? 1 : 0; 
    }
    double tempo_busca = finaliza_tempo();

    destruir_hash(&tabela);


    //////////////////////////////////////////////////////


    printf("Colisões na inserção: %d\n", colisoes);
    printf("Tempo de inserção   : %fs\n", tempo_insercao);
    printf("Tempo de busca      : %fs\n", tempo_busca);
    printf("Itens encontrados   : %d\n", encontrados);

    return 0;
}

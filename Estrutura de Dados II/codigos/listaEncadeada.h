typedef struct no_st no;
typedef struct lista_st lista;

struct lista_st{
    no *inicio;
    no *fim;
    long tamanho;
};

void criar_lista(lista *l);
void destruir_lista(lista *l);
void inserir_fim_lista(lista *l, unsigned elemento);
int remover_lista(lista *l, unsigned elemento);
void imprimir_lista(lista l);
int buscar_lista(lista l, unsigned elemento);
int tamanho_lista(lista l);
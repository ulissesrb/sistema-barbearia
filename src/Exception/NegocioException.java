package Exception;

// Uma exceção customizada para erros que são culpa das regras de negócio
public class NegocioException extends Exception {
    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
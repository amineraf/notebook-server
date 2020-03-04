import com.oracle.proof.model.Code;

public abstract class AbstractFactoryResponse<T> {
    abstract public String performResponse(T t, Code code, String language);
}

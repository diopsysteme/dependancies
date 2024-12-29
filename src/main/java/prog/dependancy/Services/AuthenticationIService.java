package prog.dependancy.Services;

public interface    AuthenticationIService<T,D,L>{
    public T signup(D input);
    public T authenticate(L input);
}

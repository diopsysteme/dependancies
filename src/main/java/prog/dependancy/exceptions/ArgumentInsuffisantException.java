package prog.dependancy.exceptions;

public class ArgumentInsuffisantException extends RuntimeException {
    public ArgumentInsuffisantException(String message) {
        super(message);
    }

  public ArgumentInsuffisantException(String message, Throwable cause) {
    super(message, cause);
  }
}

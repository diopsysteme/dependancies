package prog.dependancy.services.interfaces;

public interface IOtpService {
    public void ensureRedisConnection();
    public boolean verifyOtp(String telephone, String otp);
    public  String generateOtp(String telephone);
    public void sendOtp(String telephone, String otp);
    public boolean hasExistingOtp(String telephone);
    public void deleteOtp(String telephone);
}

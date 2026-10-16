package id.pjj.sitagihseragild.exception;

public class ValidasiException extends Exception {
    public ValidasiException(String pesan) { super(pesan); }
    public ValidasiException(String pesan, Throwable sebab) { super(pesan, sebab); }
}

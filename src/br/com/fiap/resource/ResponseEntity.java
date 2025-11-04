package br.com.fiap.resource;

/**
 * Classe auxiliar para encapsular resposta HTTP
 * Simula comportamento de ResponseEntity do Spring
 * 
 * @param <T> tipo do corpo da resposta
 */
public class ResponseEntity<T> {
    private T body;
    private int statusCode;
    private String message;
    
    public ResponseEntity(T body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
        this.message = getDefaultMessage(statusCode);
    }
    
    public ResponseEntity(T body, int statusCode, String message) {
        this.body = body;
        this.statusCode = statusCode;
        this.message = message;
    }
    
    public T getBody() {
        return body;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public String getMessage() {
        return message;
    }
    
    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }
    
    private String getDefaultMessage(int statusCode) {
        switch (statusCode) {
            case 200: return "OK";
            case 201: return "Created";
            case 204: return "No Content";
            case 400: return "Bad Request";
            case 404: return "Not Found";
            case 500: return "Internal Server Error";
            default: return "Unknown Status";
        }
    }
    
    @Override
    public String toString() {
        return "ResponseEntity{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", body=" + body +
                '}';
    }
}

